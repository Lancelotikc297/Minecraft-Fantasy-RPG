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
| F-06 | Интерактивная книга знаний (Guidebook) | `Tested` | F-01, F-04 | `GuidebookManager` со стартовыми страницами и командами |
| F-07 | Система бэкапов и безопасности сохранений | `Research` | F-01 | Предупреждения и автоматические копии миров |

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
| M-07 | Dragon Overhaul (Ice and Fire) | `In progress` | Ice and Fire Fork/Patch | Огонь, лед, молния; защита баз по умолчанию (`protectPlayerBuilds = true`) |
| M-08 | Amphithere Expansion | `In progress` | Ice and Fire Submodule | Приручение, маневренный полет, легкая сбруя |
| M-09 | NPC, Villager & Social | `Research` | MCA Reborn Integration | Диалоги на русском, профессии, семьи (без эротики) |
| M-10 | Settlement Generation | `Research` | ChoiceTheorem's Overhauled | Разнообразные города и деревни под биомы |
| M-11 | Player Kingdom Management | `Research` | MineColonies (RPG-адаптация) | Постройка города из чертежей, жители-строители |
| M-12 | Factions & Reputation | `Research` | Кастомный bridge | Отношение фракций, влияние выполненных заданий |
| M-13 | Quest & Adventure Framework | `Research` | FTB Quests / Heracles | Сюжетные цепочки, сайд-квесты, журнал приключений |
| M-14 | Dungeons & Bosses | `Research` | When Dungeons Arise / Cataclysm | Подземелья без лабиринтов, многофазные боссы |
| M-15 | World Generation & Biomes | `Research` | Terralith / Tectonic | Живописный рельеф ванильного масштаба |
| M-16 | Dimensions & Magical Worlds | `Research` | Twilight Forest / Blue Skies | Отобранные качественные измерения |
| M-17 | Seasons & Climate | `Research` | Serene Seasons | 4 времени года, погода без удушающего микроменеджмента |
| M-18 | Mounts, Travel & Navigation | `Research` | Xaero's Minimap & World Map | Миникарта, карта мира, путевые точки, скакуны |
| M-19 | Building Tools & Schematics | `Research` | Macaw's, Chipped, HT's TreeChop | Мебель, чертежи, быстрая рубка дерева с учетом прочности |
| M-20 | Storage & Crafting QoL | `Research` | Sophisticated Backpacks, JEI, Jade | Модульные рюкзаки, рецепты, тултипы блоков/мобов |
| M-21 | Food & Cooking | `Research` | Farmer's Delight | Кулинария с полезными эффектами, сытные блюда |
| M-22 | Economy & Universal Currency | `Tested` | FantasyRPG Core (`CoinPurse`) | Медные, серебряные, золотые монеты (100 меди = 1 серебро, 100 серебра = 1 золото) |
| M-23 | World Events & Raids | `Research` | Кастомные ивенты RPG Core | Нападения на поселения, мировые события с таймерами |
| M-24 | Audio & Ambient Systems | `Research` | AmbientSounds, C418 Baseline | Музыка по биомам, звуки окружения |
| M-25 | Visuals, Lighting & Shaders | `Research` | Embeddium + Oculus / Complementary | Динамический свет факелов, стилизация, профили |
| M-26 | Achievements & Journal | `Research` | Advancement Plaques / Кастом | Журнал открытий и наград |
| M-27 | Multiplayer & Server Rules | `Research` | Server Configs & Permissions | Защита построек, авторитет сервера при телепортации |
| M-28 | Russian Launcher & Pack Manager | `In progress` | Python / Electron UI | Лаунчер с русским GUI, профилями и бекапами |
| M-29 | Add-on SDK / Content Packs | `Not started` | RPG Core DataPack/Mod SDK | Документация и шаблоны для пользовательских модов |

---

## 3. Фазы разработки (Phases Timeline)

- **Phase A (Исследования и Архитектура):** `Tested` — Выбор платформы MC 1.20.1 Forge, аудит лицензий, глоссарий, дорожная карта.
- **Phase B (Стабильный технический фундамент):** `Tested` — Мод `FantasyRPG Core`, схемы конфигураций, аудит локализации, юнит-тесты.
- **Phase C (Геймплейное ролевое ядро):** `Tested` — Ролевые классы, навыки, магия 5 разрешенных школ, боевой движок, монетный двор, книга знаний.
- **Phase D (Мир и приключения):** `Planned`
- **Phase E (NPC, экономика и королевство):** `Planned`
- **Phase F (Переработка драконов и амфиптерий):** `Planned`
- **Phase G (Аудио, графика и профили):** `Planned`
- **Phase H (Лаунчер и релизная сборка):** `Planned`
- **Phase I (Экосистема расширений):** `Planned`
