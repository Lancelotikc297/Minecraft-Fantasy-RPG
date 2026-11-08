#!/usr/bin/env python3
"""
tools/validate_config.py

Validates settings JSON files against the RPG Core schema and rules.
Enforces non-negotiable rules such as protect_player_builds=True, ru_ru language,
and forbidden magic schools.
"""

import json
import os
import sys
from typing import Dict, Any, List, Tuple


def validate_config_data(config: Dict[str, Any], schema: Dict[str, Any]) -> List[str]:
    errors = []

    # Check top-level required keys
    for req in schema.get("required", []):
        if req not in config:
            errors.append(f"Missing required section: '{req}'")

    if errors:
        return errors

    # Check Minecraft version
    valid_versions = schema["properties"]["version"].get("enum", [])
    if config.get("version") not in valid_versions:
        errors.append(f"Invalid version '{config.get('version')}'. Allowed: {valid_versions}")

    # Check language
    lang = config.get("general", {}).get("language")
    if lang != "ru_ru":
        errors.append(f"Language must be 'ru_ru' per project specification, got: '{lang}'")

    # Check player build protection rule (Non-negotiable rule in Section 6.2)
    dragons = config.get("dragons", {})
    if dragons.get("protect_player_builds") is not True:
        errors.append("Rule violation: dragons.protect_player_builds must be true to protect player homes.")

    # Check magic schools (Section 4: No illusion or spatial/portal magic)
    magic = config.get("magic", {})
    enabled_schools = magic.get("enabled_schools", [])
    if "illusion" in enabled_schools:
        errors.append("Rule violation: 'illusion' magic school is explicitly forbidden in Section 4.")
    if "spatial_teleport" in enabled_schools:
        errors.append("Rule violation: 'spatial_teleport' magic school is explicitly forbidden in Section 4.")

    return errors


def validate_config_file(config_path: str, schema_path: str) -> Tuple[bool, List[str]]:
    if not os.path.exists(config_path):
        return (False, [f"Config file not found: {config_path}"])
    if not os.path.exists(schema_path):
        return (False, [f"Schema file not found: {schema_path}"])

    with open(config_path, "r", encoding="utf-8") as f:
        config = json.load(f)
    with open(schema_path, "r", encoding="utf-8") as f:
        schema = json.load(f)

    errors = validate_config_data(config, schema)
    return (len(errors) == 0, errors)


def main():
    import argparse
    parser = argparse.ArgumentParser(description="Validate RPG config against schema and core rules.")
    parser.add_argument("--config", default="config/default_settings.json", help="Path to config file")
    parser.add_argument("--schema", default="modules/rpg-core/config_schema.json", help="Path to schema file")
    args = parser.parse_args()

    valid, errors = validate_config_file(args.config, args.schema)
    if valid:
        print(f"[+] Configuration '{args.config}' is VALID and compliant with all project constraints.")
        sys.exit(0)
    else:
        print(f"[-] Configuration '{args.config}' is INVALID:")
        for err in errors:
            print(f"  - {err}")
        sys.exit(1)


if __name__ == "__main__":
    main()
