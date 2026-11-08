# Мастер-трекер задач (Master Task Tracker)

Статусы задач:
- `Not started` — Не начата
- `Research` — Исследование / Архитектура
- `In progress` — В разработке
- `Blocked` — Заблокирована
- `Implemented` — Реализована
- `Tested` — Протестирована
- `Released` — Выпущена

---

## 1. Фундамент и инструментарий (Shared Foundation)

| ID | Компонент / Задача | Статус | Зависимости | Описание / Примечания |
|---|---|---|---|---|
| F-01 | Архитектура и анализ версий (Phase A) | `Tested` | - | Документирован выбор MC 1.20.1 Forge 47.2+ в ARCHITECTURE.md |
| F-02 | Инвентарь модов и аудит лицензий | `Tested` | F-01 | Аудит лицензий (LGPLv3 Ice and Fire, проприетарные ограничения Xaero) |
| F-03 | Русскоязычный глоссарий терминов | `Tested` | - | Стандартизация терминологии RPG для всего интерфейса |
| F-04 | Автоматический аудит локализации | `Tested` | F-03 | Скрипт `tools/audit_localization.py` и юнит-тесты |
| F-05 | Схема единой конфигурации и валидатор | `Tested` | F-01 | JSON-schema настроек с защитой построек и профилями |
| F-06 | Интерактивная книга знаний (Guidebook) | `Tested` | F-01, F-04 | Стартовая выдача, динамические разделы, русское руководство |
| F-07 | Выделенный кошелек для монет (Coin Pouch) | `Implemented` | F-05, F-06 | Стартовая выдача, автосбор монет, автоконвертация без потерь, сохранение при смерти |
| F-08 | Внутриигровой центр конфигурации (In-Game Config) | `Implemented` | F-05 | Категории RPG, графика, спавн, драконы, профили и живая валидация |

---

## 2. Геймплейные модули (Gameplay Modules)

| № | Модуль | Статус | Мод-базис / Кастомная реализация | Описание ключевых механик |
|---|---|---|---|---|
| M-01 | RPG Character & Progression | `Tested` | FantasyRPG Core (`PlayerRPGData`) | Уровни, опыт $XP = 100 \times Lvl^{1.5}$, атрибуты (Сила, Ловкость, Интеллект, Выносливость) |
| M-02 | Classes, Skills & Attributes | `Tested` | FantasyRPG Core (`SkillRegistry`) | 6 классов, 18 классовых способностей, требования уровней и классов |
| M-03 | Magic Framework & Content | `Tested` | FantasyRPG Core (`SpellRegistry`) | Заклинания Огня, Льда, Молнии, Тьмы, Света; запрет иллюзий и порталов |
| M-04 | Combat & Animations | `Tested` | FantasyRPG Core (`CombatEngine`) | Расчет урона по формуле силы, шанс крита от ловкости, снижение броней и блок |
| M-05 | Equipment & Rarity | `Tested` | FantasyRPG Core (`ItemRarity`) | 6 градаций качества (Обычный -> Артефактный) с множителями статов |
| M-06 | Creatures, Wildlife & AI | `Research` | Alex's Mobs (селективно) | Дикая природа, лимиты сущностей, умный ненавязчивый AI |
| M-07 | Dragon Overhaul (Ice and Fire) | `Tested` | Dragon Overhaul (`modules/dragon-overhaul`) | Огонь, лед, молния; защита баз по умолчанию (`protect_player_builds = true`), стадии 1-5, команды |
| M-08 | Amphithere Expansion | `Tested` | Dragon Overhaul (`AmphithereData`) | Приручение, маневренный полет (1.6x), седло, статус |
| M-09 | NPC, Villager & Social | `Tested` | Kingdom and NPCs (`modules/kingdom-and-npcs`) | Диалоги на русском, профессии, отношения (без эротики) |
| M-10 | Settlement Generation | `Research` | ChoiceTheorem's Overhauled | Разнообразные города и деревни под биомы |
| M-11 | Player Kingdom Management | `Tested` | Kingdom and NPCs (`KingdomSettlementManager`) | Постройка города из чертежей, жители-строители, стража |
| M-12 | Factions & Reputation | `Tested` | Kingdom and NPCs (`FactionManager`) | 4 статуса отношений, влияние квестов и действий игрока |
| M-13 | Quest & Adventure Framework | `Tested` | Kingdom and NPCs (`QuestManager`) | Сюжетные цепочки, сайд-квесты, награды опытом и монетами |
| M-14 | Dungeons & Bosses | `Research` | When Dungeons Arise / Cataclysm | Подземелья без лабиринтов, многофазные боссы |
| M-15 | World Generation & Biomes | `Research` | Terralith / Tectonic | Живописный рельеф ванильного масштаба |
| M-16 | Dimensions & Magical Worlds | `Research` | Twilight Forest / Blue Skies | Отобранные качественные измерения |
| M-17 | Seasons & Climate | `Research` | Serene Seasons | 4 времени года, погода без удушающего микроменеджмента |
| M-18 | Mounts, Travel & Navigation | `Research` | Xaero's Minimap & World Map | Миникарта, карта мира, путевые точки, скакуны |
| M-19 | Building Tools & Schematics | `Research` | Macaw's, Chipped, HT's TreeChop | Мебель, чертежи, быстрая рубка дерева с учетом прочности |
| M-20 | Storage & Crafting QoL | `Research` | Sophisticated Backpacks, JEI, Jade | Модульные рюкзаки, рецепты, тултипы блоков/мобов |
| M-21 | Food & Cooking | `Research` | Farmer's Delight | Кулинария с полезными эффектами, сытные блюда |
| M-22 | Economy & Universal Currency | `Tested` | FantasyRPG Core (`CoinPurse`, `CoinPouch`) | Кошелек в инвентаре, авто-конвертация меди, серебра и золота |
| M-23 | World Events & Raids | `Research` | Кастомные ивенты RPG Core | Нападения на поселения, мировые события с таймерами |
| M-24 | Audio & Ambient Systems | `Research` | AmbientSounds, C418 Baseline | Музыка по биомам, звуки окружения |
| M-25 | Visuals, Lighting & Shaders | `Research` | Embeddium + Oculus / Complementary | Профили: Ноутбук, Сбалансированный, Кинематографичный |
| M-26 | Achievements & Journal | `Research` | Advancement Plaques / Кастом | Журнал открытий и наград |
| M-27 | Multiplayer & Server Rules | `Research` | Server Configs & Permissions | Защита построек, авторитет сервера при телепортации |
| M-28 | In-Game Configuration Center | `Tested` | RPGConfigCenter (`modules/fantasyrpg-core`) | Комплексный русскоязычный конфигурационный центр в игре |
| M-29 | Add-on SDK / Content Packs | `Not started` | RPG Core DataPack/Mod SDK | Документация и шаблоны для пользовательских модов |

---

## 3. Фазы разработки (Phases Timeline)

- **Phase A (Исследования и Архитектура):** `Tested` — Выбор платформы MC 1.20.1 Forge, аудит лицензий, глоссарий, дорожная карта.
- **Phase B (Стабильный технический фундамент):** `Tested` — Мод `FantasyRPG Core`, схемы конфигураций, аудит локализации, юнит-тесты.
- **Phase C (Геймплейное ролевое ядро):** `Tested` — Ролевые классы, навыки, магия 5 разрешенных школ, боевой движок, монетный двор, книга знаний.
- **Phase D (Мир и приключения):** `Tested` — Интеграция ролевых предметов, экрана персонажа и жизненного цикла.
- **Phase E (NPC, экономика и королевство):** `Tested` — Диалоги жителей, система репутации фракций, квесты, строительство поселения, выделенный кошелек.
- **Phase F (Переработка драконов и амфиптерий):** `Tested` — Реализован `DragonProtectionEngine`, защита баз по умолчанию, 3 вида драконов, 5 стадий роста, амфиптерии, русский статус.
- **Phase G (Аудио, графика и профили):** `In progress` — Исключительно графика и оптимизация: пресеты (Ноутбук, Сбалансированный, Кинематографичный), куллинг сущностей.
- **Phase H (Внутриигровой центр конфигурации):** `Tested` — Комплексный конфигуратор настроек прямо в игре (замена отдельного десктопного лаунчера согласно уточнению).
- **Phase I (Экосистема расширений):** `Planned`
