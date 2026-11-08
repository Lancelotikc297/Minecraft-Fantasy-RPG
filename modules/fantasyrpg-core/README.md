# FantasyRPG Core — Модуль ролевого ядра (Minecraft 1.20.1 Forge)

Минимальный рабочий мод **FantasyRPG Core** (`fantasyrpg_core`), реализующий ролевую систему, систему классов, опыт, атрибуты, защиту баз игрока от драконов и русскоязычную локализацию интерфейса.

---

## 🚀 Возможности модуля

1. **Русская локализация (`ru-RU`):** 100% перевод всех сообщений, классов, ошибок и подсказок.
2. **Конфигурируемые параметры RPG:**
   - Защита построек игрока от разрушения драконами по умолчанию (`protectPlayerBuilds = true`, требования раздела 6.2).
   - Запрет запрещенных школ магии (иллюзии и порталы/телепортация, требования раздела 4).
   - Профили производительности (Ноутбук, Сбалансированный, Кинематографичный).
3. **Ролевая система игрока:**
   - Уровни и масштабируемый опыт ($XP = 100 \times Level^{1.5}$).
   - 6 классов персонажа: Воин, Паладин, Маг, Некромант, Ассасин, Лучник.
   - 4 базовых атрибута: Сила, Ловкость, Интеллект, Выносливость.
   - Мана и дополнительное здоровье в зависимости от атрибутов.
4. **Внутриигровые команды:**
   - `/rpg info` — Осмотр характеристик персонажа.
   - `/rpg setclass <класс>` — Выбор ролевой специализации.
   - `/rpg addxp <число>` — Добавление опыта и проверка повышения уровня.

---

## 🛠️ Сборка JAR-файла (Build Instructions)

Требования:
- Java JDK 17 или 21
- Gradle 8+

Команда сборки:
```bash
cd modules/fantasyrpg-core
gradle clean test jar
```

Собранный JAR появится по пути:
`modules/fantasyrpg-core/build/libs/fantasyrpg-core-1.0.0.jar`

---

## 📦 Установка на клиент и сервер (Installation Instructions)

### 1. Клиентская установка
1. Установите Minecraft 1.20.1 с загрузчиком **Minecraft Forge 47.2.20+**.
2. Поместите скомпилированный файл `fantasyrpg-core-1.0.0.jar` в папку `.minecraft/mods/`.
3. Запустите игру. В меню модов появится **FantasyRPG Core**.

### 2. Серверная установка
1. Разверните сервер Forge 1.20.1 (47.2.20+).
2. Скопируйте `fantasyrpg-core-1.0.0.jar` в каталог `mods/` на сервере.
3. Запустите сервер:
   ```bash
   java -Xmx4G -Xms2G -jar forge-1.20.1-47.2.20-shim.jar nogui
   ```

---

## 🧪 Тестирование модуля

Запуск JUnit 5 тестов:
```bash
cd modules/fantasyrpg-core
gradle test
```

Запуск проверки локализации:
```bash
python3 tools/audit_localization.py \
  --ru modules/fantasyrpg-core/src/main/resources/assets/fantasyrpg_core/lang/ru_ru.json \
  --en modules/fantasyrpg-core/src/main/resources/assets/fantasyrpg_core/lang/en_us.json \
  --glossary docs/LOCALIZATION_GLOSSARY_RU.md
```
