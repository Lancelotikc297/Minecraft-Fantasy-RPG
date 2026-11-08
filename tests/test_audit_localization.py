#!/usr/bin/env python3
"""
tests/test_audit_localization.py

Test suite for tools/audit_localization.py
"""

import unittest
from tools.audit_localization import (
    contains_cyrillic,
    extract_format_specifiers,
    find_untranslated_words,
    audit_language_data,
    load_glossary,
    DEFAULT_ALLOWED_WORDS
)


class TestAuditLocalization(unittest.TestCase):

    def test_contains_cyrillic(self):
        self.assertTrue(contains_cyrillic("Огненный меч"))
        self.assertTrue(contains_cyrillic("Уровень 5 (XP)"))
        self.assertFalse(contains_cyrillic("Fire Dragon Sword"))
        self.assertFalse(contains_cyrillic("12345!@#"))

    def test_extract_format_specifiers(self):
        text = "Игрок %s нанес %d единиц урона в {biome}!"
        specs = extract_format_specifiers(text)
        self.assertEqual(specs, ["%s", "%d", "{biome}"])

    def test_find_untranslated_words(self):
        text = "Этот Sword наносит 10 урона"
        untrans = find_untranslated_words(text, DEFAULT_ALLOWED_WORDS)
        self.assertIn("Sword", untrans)

        # Allowed technical term
        text_allowed = "Интерфейс GUI и показатель FPS"
        untrans_allowed = find_untranslated_words(text_allowed, DEFAULT_ALLOWED_WORDS)
        self.assertEqual(len(untrans_allowed), 0)

    def test_audit_language_data_clean(self):
        en_data = {
            "item.rpg.fire_sword": "Fire Sword",
            "stat.rpg.strength": "Strength",
            "message.rpg.welcome": "Welcome %s!"
        }
        ru_data = {
            "item.rpg.fire_sword": "Огненный меч",
            "stat.rpg.strength": "Сила",
            "message.rpg.welcome": "Добро пожаловать, %s!"
        }
        issues = audit_language_data(ru_data, en_data)
        for cat, errs in issues.items():
            self.assertEqual(errs, [], f"Expected no errors in {cat}, found: {errs}")

    def test_audit_language_data_detects_missing_key(self):
        en_data = {"item.rpg.fire_sword": "Fire Sword", "item.rpg.ice_sword": "Ice Sword"}
        ru_data = {"item.rpg.fire_sword": "Огненный меч"}
        issues = audit_language_data(ru_data, en_data)
        self.assertEqual(len(issues["missing_keys"]), 1)
        self.assertIn("item.rpg.ice_sword", issues["missing_keys"][0])

    def test_audit_language_data_detects_empty_value(self):
        ru_data = {"item.rpg.empty": "   "}
        issues = audit_language_data(ru_data)
        self.assertEqual(len(issues["empty_values"]), 1)

    def test_audit_language_data_detects_english_only(self):
        ru_data = {"item.rpg.dragonslayer": "Dragonslayer Crossbow"}
        issues = audit_language_data(ru_data)
        self.assertEqual(len(issues["missing_cyrillic"]), 1)

    def test_audit_language_data_detects_mismatched_placeholders(self):
        en_data = {"msg.kill": "%s killed %s with %d damage"}
        ru_data = {"msg.kill": "%s убил врага!"}  # missing %s and %d
        issues = audit_language_data(ru_data, en_data)
        self.assertEqual(len(issues["mismatched_placeholders"]), 1)

    def test_load_glossary_from_markdown(self):
        glossary = load_glossary("docs/LOCALIZATION_GLOSSARY_RU.md")
        self.assertIn("strength", glossary)
        self.assertEqual(glossary["strength"], "сила")
        self.assertIn("fire dragon", glossary)
        self.assertEqual(glossary["fire dragon"], "огненный дракон")


if __name__ == "__main__":
    unittest.main()
