# Minecraft Fantasy RPG Sandbox

Модульная ролевая песочница на базе Minecraft 1.20.1 Forge, расширяющая исследование мира, RPG-прогрессию, магию, существ и драконов, систему поселений и экономику. Полная локализация на русский язык (`ru_RU`).

---

## 🧭 Назначение и ключевые принципы

1. **Модульность:** Проект строится как комплекс независимых, конфигурируемых и тестируемых модулей вокруг ядра `FantasyRPG Core`.
2. **Язык интерфейса:** 100% русскоязычный интерфейс игрока (книга знаний, подсказки, диалоги, настройки, квесты).
3. **Безопасность построек игрока:** Дома и поселения защищены от разрушения драконами по умолчанию (`protect_player_builds = true`).
4. **Сбалансированная магия:** Школы огня, льда, молнии, тьмы; запрещены школы телепортации и иллюзий.
5. **Единая экономика:** Универсальные монеты (медь, серебро, золото) взамен «изумрудов за палки».

---

## 📁 Структура проекта

```text
├── docs/                                # Архитектура, лицензии, глоссарий, журнал решений
│   ├── ARCHITECTURE.md                  # Технический дизайн, обоснование выбора MC 1.20.1 Forge
│   ├── PHASE_B_RESEARCH.md              # Сравнительный анализ версий MC и модлоадеров
│   ├── TASK_TRACKER.md                  # Мастер-трекер 29 модулей и фаз разработки
│   ├── MOD_INVENTORY_AND_LICENSES.md   # Аудит лицензий (LGPLv3, MIT, проприетарные)
│   ├── DECISION_LOG_AND_RISKS.md        # Журнал решений (ADR) и реестр рисков
│   └── LOCALIZATION_GLOSSARY_RU.md      # Стандарты терминологии и переводов
├── modules/                             # Модули системы
│   ├── rpg-core/                        # Центральные схемы конфигураций и метаданные
│   │   └── config_schema.json           # Схема централизованных настроек
│   └── fantasyrpg-core/                 # Скомпилированный рабочий мод FantasyRPG Core (Forge 1.20.1)
│       ├── build.gradle                 # Конфигурация сборки Gradle
│       ├── src/main/java/               # Исходный код ядра мода (классы, статы, команды)
│       ├── src/main/resources/          # mods.toml и русскоязычные lang-файлы ru_ru.json
│       └── build/libs/                  # Готовый скомпилированный JAR
├── config/                              # Конфигурационные файлы
│   └── default_settings.json            # Базовые проверенные настройки
├── tools/                               # Утилиты разработки и CI
│   ├── audit_localization.py            # Автоматический аудит строк на русский язык
│   └── validate_config.py               # Проверка конфигураций на соответствие правилам
├── tests/                               # Набор автоматизированных тестов Python
│   ├── test_audit_localization.py       # Тесты инструмента локализации
│   └── test_validate_config.py          # Тесты валидатора конфигураций
├── launcher/                            # Исходный код и ресурсы русскоязычного лаунчера
└── Minecraft_Fantasy_RPG_AI_Task_List.md# Мастер-спецификация проекта
```

---

## 🛠️ Сборка мода FantasyRPG Core и запуск тестов

### 1. Сборка JAR-файла мода:
```bash
cd modules/fantasyrpg-core
gradle clean test jar
```
Собранный JAR создается в: `modules/fantasyrpg-core/build/libs/fantasyrpg-core-1.0.0.jar`.

### 2. Запуск Java тестов мода:
```bash
cd modules/fantasyrpg-core
gradle test
```

### 3. Запуск системных тестов Python:
```bash
python3 -m unittest discover tests
```

### 4. Проверка локализации мода:
```bash
python3 tools/audit_localization.py \
  --ru modules/fantasyrpg-core/src/main/resources/assets/fantasyrpg_core/lang/ru_ru.json \
  --en modules/fantasyrpg-core/src/main/resources/assets/fantasyrpg_core/lang/en_us.json \
  --glossary docs/LOCALIZATION_GLOSSARY_RU.md
```

### 5. Проверка единых настроек:
```bash
python3 tools/validate_config.py --config config/default_settings.json --schema modules/rpg-core/config_schema.json
```
