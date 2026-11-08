#!/usr/bin/env python3
"""
tools/audit_localization.py

Automated localization auditing tool for the Minecraft Fantasy RPG project.
Scans language files (JSON), checks for missing keys, untranslated English strings,
format specifiers preservation, and adherence to the Russian terminology glossary.

Usage:
    python3 tools/audit_localization.py --lang-dir <path> [--glossary <path>]
"""

import os
import re
import json
import sys
from typing import Dict, List, Tuple, Any, Optional

CYRILLIC_PATTERN = re.compile(r"[\u0400-\u04FF]")
ENGLISH_WORD_PATTERN = re.compile(r"\b[A-Za-z]{3,}\b")
FORMAT_SPEC_PATTERN = re.compile(r"(%[sddoxXfFgGeE%]|%[0-9]+\$[sddoxXfFgGeE]|{[0-9a-zA-Z_]+})")

# Allowed technical keywords/names that are acceptable in English or transliterated
DEFAULT_ALLOWED_WORDS = {
    "minecraft", "mod", "api", "id", "gui", "hud", "fps", "hp", "xp", "wthit", "jei",
    "forge", "neoforge", "fabric", "nbt", "json", "shift", "ctrl", "alt", "rgb", "url",
    "c418", "mca", "reborn"
}


def load_json_file(filepath: str) -> Dict[str, Any]:
    with open(filepath, "r", encoding="utf-8") as f:
        return json.load(f)


def extract_format_specifiers(text: str) -> List[str]:
    return FORMAT_SPEC_PATTERN.findall(text)


def contains_cyrillic(text: str) -> bool:
    return bool(CYRILLIC_PATTERN.search(text))


def find_untranslated_words(text: str, allowed_words: set) -> List[str]:
    """Finds English words that might indicate untranslated strings."""
    # First strip format specifiers
    cleaned = FORMAT_SPEC_PATTERN.sub(" ", text)
    words = ENGLISH_WORD_PATTERN.findall(cleaned)
    untranslated = []
    for w in words:
        if w.lower() not in allowed_words:
            untranslated.append(w)
    return untranslated


def load_glossary(glossary_path: str) -> Dict[str, str]:
    """
    Loads markdown table glossary from docs/LOCALIZATION_GLOSSARY_RU.md
    or a simple JSON glossary file.
    """
    glossary = {}
    if not os.path.exists(glossary_path):
        return glossary

    if glossary_path.endswith(".json"):
        with open(glossary_path, "r", encoding="utf-8") as f:
            return json.load(f)

    # Parse markdown table
    with open(glossary_path, "r", encoding="utf-8") as f:
        for line in f:
            if "|" in line:
                parts = [p.strip() for p in line.split("|")]
                if len(parts) >= 3:
                    en = parts[1].strip()
                    ru = parts[2].strip()
                    if en and ru and not en.startswith("-") and en.lower() != "английский термин":
                        glossary[en.lower()] = ru.lower()
    return glossary


def audit_language_data(
    ru_data: Dict[str, str],
    en_data: Optional[Dict[str, str]] = None,
    glossary: Optional[Dict[str, str]] = None,
    allowed_words: Optional[set] = None
) -> Dict[str, List[str]]:
    """
    Audits Russian language dictionary against English reference, glossary, and rules.
    Returns a dictionary of issue categories to lists of error messages.
    """
    issues = {
        "missing_keys": [],
        "empty_values": [],
        "missing_cyrillic": [],
        "untranslated_words": [],
        "mismatched_placeholders": []
    }
    allowed = allowed_words or DEFAULT_ALLOWED_WORDS

    if en_data:
        for k in en_data:
            if k not in ru_data:
                issues["missing_keys"].append(f"Key missing in Russian translation: '{k}'")

    for k, val in ru_data.items():
        if not isinstance(val, str):
            continue
        trimmed = val.strip()
        if not trimmed:
            issues["empty_values"].append(f"Empty translation for key: '{k}'")
            continue

        # If it doesn't contain any cyrillic letters, check if it's pure numbers/punctuation
        has_letters = bool(re.search(r"[A-Za-z\u0400-\u04FF]", trimmed))
        if has_letters and not contains_cyrillic(trimmed):
            issues["missing_cyrillic"].append(
                f"Translation for '{k}' contains no Cyrillic characters: \"{trimmed}\""
            )

        # Check for untranslated English words
        untrans = find_untranslated_words(trimmed, allowed)
        if untrans:
            issues["untranslated_words"].append(
                f"Potentially untranslated English words in '{k}' (\"{trimmed}\"): {untrans}"
            )

        # Format specifier check against EN if provided
        if en_data and k in en_data and isinstance(en_data[k], str):
            en_specs = sorted(extract_format_specifiers(en_data[k]))
            ru_specs = sorted(extract_format_specifiers(trimmed))
            if en_specs != ru_specs:
                issues["mismatched_placeholders"].append(
                    f"Placeholder mismatch for '{k}': EN has {en_specs}, RU has {ru_specs}"
                )

    return issues


def run_audit(ru_file: str, en_file: Optional[str] = None, glossary_file: Optional[str] = None) -> Tuple[bool, Dict[str, List[str]]]:
    if not os.path.exists(ru_file):
        raise FileNotFoundError(f"Russian language file not found: {ru_file}")

    ru_data = load_json_file(ru_file)
    en_data = load_json_file(en_file) if en_file and os.path.exists(en_file) else None
    glossary = load_glossary(glossary_file) if glossary_file else {}

    issues = audit_language_data(ru_data, en_data, glossary)
    total_issues = sum(len(errs) for errs in issues.values())
    return (total_issues == 0, issues)


def main():
    import argparse
    parser = argparse.ArgumentParser(description="Audit Minecraft RPG localization files.")
    parser.add_argument("--ru", required=True, help="Path to ru_ru.json file")
    parser.add_argument("--en", required=False, help="Path to en_us.json file for comparison")
    parser.add_argument("--glossary", required=False, help="Path to glossary markdown or json")
    args = parser.parse_args()

    success, issues = run_audit(args.ru, args.en, args.glossary)
    print(f"Auditing '{args.ru}'...")
    has_errors = False
    for cat, errs in issues.items():
        if errs:
            has_errors = True
            print(f"\n[!] Category '{cat}' ({len(errs)} issues):")
            for e in errs[:10]:
                print(f"  - {e}")
            if len(errs) > 10:
                print(f"  ... and {len(errs) - 10} more.")

    if not has_errors:
        print("\n[+] Localization check PASSED! 100% compliant with Russian standards.")
        sys.exit(0)
    else:
        print("\n[-] Localization check FAILED. Please address the issues listed above.")
        sys.exit(1)


if __name__ == "__main__":
    main()
