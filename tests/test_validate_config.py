#!/usr/bin/env python3
"""
tests/test_validate_config.py

Tests for tools/validate_config.py and modules/rpg-core/config_schema.json
"""

import unittest
from tools.validate_config import validate_config_data, validate_config_file


class TestValidateConfig(unittest.TestCase):

    def setUp(self):
        self.schema = {
            "required": ["version", "general", "dragons", "magic", "survival", "backup"],
            "properties": {
                "version": {"enum": ["1.20.1"]}
            }
        }
        self.valid_config = {
            "version": "1.20.1",
            "general": {
                "language": "ru_ru",
                "performance_profile": "balanced",
                "give_starter_guidebook": True
            },
            "dragons": {
                "protect_player_builds": True,
                "destruction_mode": "limited",
                "enable_amphithere_mounts": True
            },
            "magic": {
                "enabled_schools": ["fire", "ice", "dark"],
                "forbidden_schools": ["illusion", "spatial_teleport"]
            },
            "survival": {
                "keep_inventory_on_death": True,
                "tree_felling_axe_durability": True,
                "universal_coin_economy": True
            },
            "backup": {
                "auto_backup_before_mod_changes": True,
                "max_backup_count": 10
            }
        }

    def test_valid_config_passes(self):
        errors = validate_config_data(self.valid_config, self.schema)
        self.assertEqual(errors, [])

    def test_rejects_missing_required_section(self):
        bad_config = dict(self.valid_config)
        del bad_config["dragons"]
        errors = validate_config_data(bad_config, self.schema)
        self.assertIn("Missing required section: 'dragons'", errors)

    def test_rejects_non_russian_language(self):
        bad_config = dict(self.valid_config)
        bad_config["general"] = dict(self.valid_config["general"])
        bad_config["general"]["language"] = "en_us"
        errors = validate_config_data(bad_config, self.schema)
        self.assertTrue(any("ru_ru" in err for err in errors))

    def test_rejects_disabled_player_build_protection(self):
        bad_config = dict(self.valid_config)
        bad_config["dragons"] = dict(self.valid_config["dragons"])
        bad_config["dragons"]["protect_player_builds"] = False
        errors = validate_config_data(bad_config, self.schema)
        self.assertTrue(any("protect_player_builds must be true" in err for err in errors))

    def test_rejects_forbidden_magic_schools(self):
        bad_config = dict(self.valid_config)
        bad_config["magic"] = dict(self.valid_config["magic"])
        bad_config["magic"]["enabled_schools"] = ["fire", "illusion"]
        errors = validate_config_data(bad_config, self.schema)
        self.assertTrue(any("illusion" in err for err in errors))

    def test_file_validation_with_real_assets(self):
        valid, errors = validate_config_file("config/default_settings.json", "modules/rpg-core/config_schema.json")
        self.assertTrue(valid, f"Expected default config to be valid, got errors: {errors}")
        self.assertEqual(len(errors), 0)


if __name__ == "__main__":
    unittest.main()
