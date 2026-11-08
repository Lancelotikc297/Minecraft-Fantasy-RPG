# Minecraft Fantasy RPG (1.20.1 Forge)

Готовая к установке модульная ролевая песочница на базе Minecraft 1.20.1 Forge: драконы, классы, магия, живые поселения, универсальная монетная экономика и 100% русская локализация.

---

## ⚡ Быстрая установка в 1 шаг

1. Скачайте готовый архив **`dist/Minecraft_Fantasy_RPG_1.20.1.zip`**.
2. Распакуйте его содержимое прямо в папку `.minecraft`.
3. Запустите Minecraft с профилем **Forge 1.20.1** (версия 47.2.20+) и наслаждайтесь игрой!

---

## 📦 Скомпилированные модули в комплекте

1. **`fantasyrpg-core-1.0.0.jar`:**
   - 6 классов, уровни, 18 навыков, 16 заклинаний (Огонь, Лед, Молния, Тьма, Свет).
   - Выделенный несгораемый кошелек (Coin Pouch) с авто-конвертацией 100:1.
   - Внутриигровой конфигурационный центр (`/rpg config`) и профили графики (Laptop, Balanced, Cinematic).
2. **`dragon-overhaul-1.0.0.jar`:**
   - **Защита построек игрока по умолчанию (`protect_player_builds = true`)!**
   - 3 вида драконов, 5 стадий роста, амфиптерии (1.6x скорость полета).
3. **`kingdom-and-npcs-1.0.0.jar`:**
   - Социум жителей (5 профессий), 4 фракции с репутацией, квесты и стройка поселения.
4. **`world-and-survival-1.0.0.jar`:**
   - Рубка дерева целиком со списанием прочности топора.
   - 4 сезона года без удушающих штрафов.
   - Модульные рюкзаки ультра-вместимости до 16384 предметов на слот.
   - Многофазные боссы и интеграция путевых точек карты.

---

## 🛠️ Сборка из исходников и тестирование

```bash
# Сборка всех 4 модулей:
cd modules/fantasyrpg-core && gradle clean test jar
cd ../dragon-overhaul && gradle clean test jar
cd ../kingdom-and-npcs && gradle clean test jar
cd ../world-and-survival && gradle clean test jar

# Проверка тестов и локализации:
python3 -m unittest discover tests
python3 tools/validate_config.py --config config/default_settings.json --schema modules/rpg-core/config_schema.json
```
