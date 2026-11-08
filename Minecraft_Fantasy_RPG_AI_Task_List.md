# Minecraft Fantasy RPG Sandbox --- AI Development Task List

## Master Specification and Modular Implementation Plan

**Document language:** English (for the AI development agent)\
**Required player-facing language:** Russian (ru-RU) everywhere in the
game and launcher\
**Project type:** A modular Minecraft RPG sandbox / heavily expanded
modpack, with custom mods, a launcher, compatibility tooling, and future
expansion support.

------------------------------------------------------------------------

# 0. Mission, scope, and non-negotiable rules

Build a large, cohesive fantasy RPG sandbox on top of Minecraft.
Preserve the recognizable Minecraft world, block-based building, core
survival identity, and approximate vanilla world scale, while
substantially expanding exploration, RPG progression, magic, creatures,
settlements, dragons, building, and atmosphere.

This is **not** one monolithic mod. Create a collection of independent,
clearly scoped mods/add-ons connected through a shared technical core
and common configuration/API conventions. Each subsystem must be
independently testable, documented, configurable, and---where
safe---disableable.

The AI agent must: 1. Research mod availability, licenses, source
access, supported Minecraft versions, loaders, dependencies, and
multiplayer compatibility before selecting components. 2. Prefer a
coherent, maintainable mod ecosystem over blindly targeting the newest
Minecraft release. Target MC 26.1 only if the required ecosystem
genuinely supports it; otherwise choose the closest suitable version,
never below 1.19.2 without explicit approval. Explain and document the
final version/loader choice. 3. Select a loader
(Forge/NeoForge/Fabric/etc.) based on the actual ecosystem and project
requirements; do not assume Forge if the selected version/mods make
another loader better. 4. Use existing mods where they are high-quality
and compatible. Create custom mods for missing features, integration,
substantial rewrites, or features that cannot be achieved cleanly with
existing mods. 5. Avoid duplicate mechanics and redundant content. Merge
useful capabilities where possible; choose the best implementation when
features overlap. 6. Never silently sacrifice major functionality to
solve a compatibility issue. Try configuration, compatibility patches,
source fixes (where legally permitted), or a custom
replacement/analogue. Report any unavoidable loss and offer
alternatives. 7. Do not promise perfect compatibility, unlimited FPS, or
zero bugs. Use automated checks plus actual in-game testing; explicitly
identify unverified areas. 8. Keep the project staged, maintain
changelogs, backups, reproducible builds, and a stable known-good
release. 9. Keep all **player-visible text in Russian**: launcher,
menus, item/block/entity names, descriptions, tooltips, recipes, quests,
NPC dialogue, abilities, effects, guidebook, settings, warnings, and
help. Generate missing translations and audit for leftover English.
Internal source code identifiers, file names, technical logs, and
developer documentation may remain English. 10. At world creation, give
the player a Russian guidebook/manual. It must be searchable by mod,
category, mechanic, item, and recipe; explain controls, systems,
recipes, and progression; and update automatically when content/add-ons
are added.

------------------------------------------------------------------------

# 1. Required modular architecture

Create separate modules rather than a single giant mod. Suggested
boundaries (names are provisional; choose names after repository setup):

## 1.1 Shared foundation

-   **RPG Core / Common API:** shared registries, configuration
    conventions, compatibility hooks, event interfaces, data formats,
    common utilities, and version checks.
-   **Localization & Content Audit:** translation resources,
    missing-string detection, Russian terminology glossary, and
    automated player-facing English-string audit.
-   **Configuration Hub:** one central Russian settings interface plus
    each mod's own detailed settings; safe toggles, presets, dependency
    warnings, and configuration validation.
-   **Performance & Diagnostics:** profiling, memory/FPS/load
    diagnostics, entity caps, spawn controls, performance presets, and
    reports.
-   **Save Safety:** world/player backup integration, pre-update
    backups, crash recovery guidance, integrity checks, and restore
    workflow.
-   **Guidebook / Encyclopedia:** in-game searchable manual,
    mod/mechanic/item/recipe index, controls, progression explanations,
    and dynamic updates.
-   **Integration Layer:** inter-mod compatibility, unified events,
    common UI conventions, cross-mod recipes, and feature bridges.

## 1.2 Gameplay modules

1.  **RPG Character & Progression**
2.  **Classes, Skills & Attributes**
3.  **Magic Framework and Magic Content**
4.  **Combat, Weapons & Animations**
5.  **Equipment, Rarity, Artifacts & Accessories**
6.  **Creatures, Wildlife & Creature AI**
7.  **Dragon Overhaul (Ice and Fire rewrite/extension)**
8.  **Amphithere Expansion**
9.  **NPC, Villager & Social Systems**
10. **Settlement Generation & Architecture**
11. **Player Kingdom / Settlement Management**
12. **Factions, Reputation & Relations**
13. **Quest & Adventure Framework**
14. **Dungeons, Structures & Loot**
15. **World Generation, Biomes & Terrain**
16. **Dimensions & Magical Worlds**
17. **Seasons, Climate, Weather & Atmosphere**
18. **Mounts, Travel & Map Integration**
19. **Building Tools, Schematics & Construction**
20. **Storage, Inventory & Crafting QoL**
21. **Food & Cooking**
22. **Economy, Currency & Trading**
23. **World Events, Raids & Boss Encounters**
24. **Audio, Music & Ambient Systems**
25. **Visuals, Lighting, Models, Textures & Effects**
26. **Achievements, Journal & Discovery**
27. **Multiplayer/Server Support and Rules**
28. **Launcher, Pack Manager & Update System**
29. **Optional Add-on SDK / Content Packs**

Every module should have a README, feature list, dependencies,
configuration reference, Russian localization checklist, test plan,
compatibility status, and changelog.

------------------------------------------------------------------------

# 2. World, terrain, exploration, and generation

-   Preserve the approximate vanilla world size (roughly the normal
    Minecraft world scale); do not radically shrink or enlarge it.
-   Improve vanilla terrain: mountains, valleys, forests, rivers, lakes,
    waterfalls, coastlines, islands, and more convincing landscapes.
-   Add a broad mix of realistic and fantasy biomes, with biome-specific
    visual styles and atmosphere.
-   Add many useful and varied animals/creatures, especially wildlife
    that enriches the world. Avoid excessive meaningless mob spam or
    heavy simulation.
-   Add several **high-quality** dimensions rather than dozens of
    shallow ones. Include magical worlds, sky/island realms, dark
    dimensions, unique resources, and unique bosses.
-   Add underwater biomes, ruins, creatures, and treasure.
-   Improve structures, ruins, villages, settlements, castles, cities,
    and fortresses. Make them varied and coherent with biome/culture.
-   Include rare discoveries, secrets, treasures, minibosses, bosses,
    dynamic events, and random adventure opportunities.
-   Exploration should be possible from the beginning, while some
    rare/special content can naturally require progression.
-   Keep generation performant and avoid excessive structures/entities.

# 3. RPG character and progression

-   Character setup: selectable race, sex/gender presentation,
    appearance, class/specialization, attributes, and equipment
    appearance/customization.
-   Race differences should be meaningful but primarily manageable stat
    differences; avoid an excessive race catalog or complex racial
    simulation.
-   Include classes/specializations such as necromancer, paladin,
    assassin, archer, mage, and warrior, with room for future classes.
-   Progression combines XP from kills/quests, skills learned through
    use, manually allocated points, and other appropriate systems.
-   Skill tree should be medium-small and readable, not an enormous
    branching maze. Support several progression approaches without
    excessive build complexity.
-   Include RPG levels, attributes, skills, rare abilities, and
    class-specific mechanics.
-   Preserve a configurable, balanced difficulty: slightly harder than
    vanilla by default, with adjustable progression and event
    difficulty.
-   Avoid grind and useless progression layers. Each system must have a
    clear purpose.
-   Keep health close to vanilla hearts, with configurable effects such
    as bleeding/poison and manageable status mechanics.
-   Death rules must be configurable, including keeping inventory,
    losing some items/XP, and respawning at home or a chosen point.
    Default should preserve inventory (or make this an easy explicit
    choice).

# 4. Magic

Build a coherent, independently configurable magic system rather than
unrelated spell mods glued together.

Include: - Elemental schools: fire, ice, lightning. - Dark magic. -
Staff and spellbook casting. - Rituals and alchemy. - Magic crafting,
runes, accessories, artifacts, and unique abilities. - Magic items with
useful effects and clear descriptions. - Different abilities by class,
race, item, or progression where appropriate. - Good combat feedback and
beautiful but performance-conscious effects. - **Do not include illusion
magic or spatial/portal magic schools.** - Avoid duplicate spells and
redundant magic items. Balance, recipes, cooldowns, and progression must
be configurable.

# 5. Combat, weapons, bosses, and loot

-   Add weapons, attack animations, dodge mechanics, special attacks,
    and readable combat feedback.
-   Improve vanilla enemy AI intelligently without turning every mob
    into a complex simulation or making ordinary combat excessively
    difficult.
-   Include large unique bosses, minibosses, world bosses, and
    multi-phase encounters with distinctive models, animations, arenas,
    mechanics, and story/context.
-   Do not rely on simple vanilla mobs with inflated health as the main
    boss content.
-   Support cooperative multiplayer boss fights and dungeons.
-   Add rarity tiers, unique effects, set bonuses, active artifact
    powers, legendary gear, accessories, and meaningful armor/weapons.
-   Keep item stats relatively understandable. Avoid item spam and
    endless near-identical loot.
-   Armor visual direction: classic fantasy, medieval, Dark Souls/Elden
    Ring-inspired, and compatible mixed styles. Avoid hyper-realistic
    over-detail, especially on weapons/armor.
-   Gear should be valuable and distinctive, not merely bigger numbers.

# 6. Ice and Fire --- substantial dragon-focused rewrite/overhaul

Do not simply install the unmodified Ice and Fire and call it complete.
Investigate its source/license and legal redistribution/forking
conditions. If source modification or redistribution is not allowed, use
a legally permitted integration, compatibility layer, or original
replacement implementation.

## 6.1 Retain and improve

-   Retain all original dragon types: fire, ice, lightning.
-   Retain age/growth stages and visual variants; improve proportions,
    models, textures, flight, combat, and animations.
-   Retain dragon nests/logos, improve generation, diversity, treasure,
    visibility, and reduce empty nests.
-   Retain dragon skeletons and improve their models/textures.
-   Retain amphitheres; remove or heavily reduce other Ice and Fire
    creatures (cyclops, hydra, troll, gorgon, undead, fairies/sirens,
    etc.) unless a specific one is later approved.
-   Preserve the recognizable core dragon mechanics while improving
    balance, usability, and configuration.

## 6.2 Dragon destruction and player protection

-   Dragon destruction should be configurable: original-like
    destruction, limited block destruction, destruction only during
    attacks, or fully disabled.
-   **Player-built structures must be protected by default.** Do not let
    a fleeing/roaming dragon accidentally destroy the player's house or
    settlement.
-   Support protected player buildings/settlements and explicit
    protected zones/flags. Protection should be reliable and easy to
    configure.
-   Allow players/server owners to permit destruction selectively; do
    not silently bypass protection.

## 6.3 Dragon behavior and combat

-   Preserve original behavior as the baseline but improve it modestly:
    more convincing patrol, rest, hunting, threat response,
    treasure/nest behavior, and animations.
-   Dragons may attack large creatures and NPC settlements;
    aggressiveness and event behavior must be configurable.
-   Improve dragon combat AI. Avoid uncontrolled destruction or
    aggressive spawns near the player's home.
-   Support dragon-vs-dragon and large-creature encounters where
    appropriate.

## 6.4 Taming, commands, storage, flight

-   Tamed dragons can protect/follow the player, stay, guard a location,
    return home, attack a target, roam/fly freely, and switch behavior
    modes.
-   Include a command system and a "do not attack unless ordered"
    option.
-   Provide convenient dragon storage outside the active world where
    technically safe (stasis/roster/recall system); explain limitations
    and protect entity data.
-   Improve rider controls, takeoff/landing, multiple flight speeds,
    aerial attacks, passenger carrying, cargo carrying, and balance
    restrictions.
-   Allow individual flight features to be disabled.
-   Add dragon inventory, saddle/armor, cargo, passenger transport,
    dedicated equipment slots, and visible equipment models.

## 6.5 Dragon growth, death, breeding, and genetics

-   Keep original growth stages, with smoother growth transitions.
-   Retain meaningful dragon death. Death should be final by
    default/according to original rules, but an egg may be obtainable
    where appropriate; do not automatically make every dragon immortal.
-   Separate settings for wild and tamed dragons.
-   Improve breeding compatibility and genetics: inherited colors,
    traits/characteristics, and rare unique variants. Keep the system
    understandable and not excessively complicated.
-   Include dragon-specific abilities by species and age.

## 6.6 Amphitheres

-   Improve models, textures, and animations.
-   Make them tameable, breedable, rideable, and easy to control.
-   Give them unique abilities and their own equipment.
-   Make them faster but generally weaker than dragons, easier to
    maintain, and a useful alternative flying mount---not a simple
    dragon clone.

## 6.7 Dragon gear and content cleanup

-   Keep original dragon armor; improve its textures.
-   Improve dragon weapons; add dragon accessories and dragon
    armor/equipment.
-   Display equipped gear visually.
-   Simplify overly tedious recipes while retaining progression/material
    value.
-   Remove redundant blocks, decorative items, recipes, effects,
    clutter, and unnecessary content. Keep core dragon gameplay.

## 6.8 Dragon information

-   Provide a Russian dragon status panel: health, age/growth stage,
    species, abilities, traits, equipment, and relevant stats.
-   Add a detailed dragon encyclopedia entry/guidebook.

# 7. Creatures, mounts, pets, and AI

-   Add many animals and useful wildlife, but keep spawning and
    simulation controlled.
-   Add neutral, hostile, rare, mythical, and intelligent fantasy
    races/NPCs where useful.
-   Pets can be tamed, leveled, bred, and gain combat abilities;
    breeding/genetics should be meaningful without needless complexity.
-   Improve horses: stats, control, unique variants, inventory,
    breeding, and useful movement.
-   Add ground and flying mounts with easy controls, inventory where
    suitable, and balanced abilities.
-   Make AI behavior more intelligent but not computationally excessive.
-   Use entity caps, spawn controls, and performance profiles.

# 8. NPCs, villagers, social systems, and factions

-   Improve villager models, textures, animations, clothing, and visual
    diversity. Use modular variation rather than unique heavy models for
    every NPC.
-   NPCs have gender/appearance, relationships, dialogue, jobs, and
    schedules where useful; keep simulation lightweight.
-   Social mechanics inspired by MCA Reborn are acceptable:
    relationships, marriage/family/children, but **no erotic content**.
-   Include interaction and dialogue systems; all dialogue in Russian.
-   Include relations with individual NPCs, reputation/relationships,
    faction alliances/rivalries, and settlement relations.
-   Factions/races should have distinctive cultures, territories,
    settlements, and relationships.
-   Politics should be understandable and not turn the game into a
    complex grand strategy.
-   NPC death/heirs can exist where compatible, but avoid heavy
    simulation.

# 9. Settlements, cities, kingdom, and construction

## 9.1 World settlements

-   Generate many kinds of villages, towns, cities, capitals, castles,
    fortresses, and race-specific settlements.
-   Architecture should vary by biome, culture, and style: medieval
    European, fantasy, Japanese, and other coherent styles.
-   Improve vanilla villages and add substantial variety.

## 9.2 Player kingdom

-   Allow building/developing a settlement or kingdom from scratch.
-   Recruit/bring residents, assign jobs, and have NPCs construct
    buildings autonomously from blueprints.
-   Include jobs, defense, attacks/raids, technologies, building
    upgrades, expansion, factions, and ruler identity.
-   Include economy, army/guard, taxes/resources, territory, and
    development as optional/usable systems.
-   **Do not emphasize strategy gameplay.** Kingdom mechanics support
    the RPG sandbox and player building, not a separate heavy
    RTS/grand-strategy game.
-   Allow free placement, ready/editable blueprints, large castles, and
    automatic upgrades where suitable.

## 9.3 Building tools

-   Decorative blocks, furniture, interiors, diverse materials,
    functional doors/windows, improved textures/tools.
-   Blueprint/schematic save/load, copy/paste, mass placement, editor,
    improved block placement, and automated construction.
-   Keep vanilla building viable and intuitive.
-   Ensure construction tools are configurable and
    multiplayer/server-safe.

# 10. Economy, currency, traders, and quests

## 10.1 Currency and trade

-   Keep vanilla trading recognizable, but replace the emerald-only
    economy with a **universal coin/currency layer**.
-   NPCs and settlement traders should use the same currency
    consistently. Do not make the economy merely "sticks for emeralds."
-   Coins can be minted/crafted from gold (or another balanced source),
    earned through trade/quests, and spent with merchants.
-   Include prices, rare goods, varied NPC merchants, and player-owned
    settlement shops/traders.
-   Keep economy configurable and avoid inflation/exploits.

## 10.2 Quests and relations

-   Random tasks, NPC quests, settlement quests, and story quest chains.
-   Quests should support rewards, dialogue, progression, and optional
    markers.
-   Include relations with individual NPCs; faction and settlement
    relations can connect to quests and trading.
-   Avoid repetitive grind and mandatory quest spam.

# 11. Dungeons, structures, and world events

-   Large multi-level dungeons with clear entrances/exits, thematic
    styles, secrets, treasure rooms, and unique bosses.
-   **No mazes**: layouts should be navigable and understandable.
-   Add unique world bosses, settlement attacks, and configurable
    events.
-   Event frequency, intensity, and difficulty must be adjustable or
    disableable.
-   Preserve freedom to play sandbox without a mandatory ending.
-   Endgame can include hard bosses, rare loot, special dimensions, and
    continuing sandbox play.

# 12. Survival, seasons, food, and climate

-   Keep vanilla survival recognizable and only slightly harder by
    default.
-   Four seasons, vegetation changes, temperature, climate zones, and
    regional weather.
-   Rain, thunderstorms, snow, blizzards, fog, and atmospheric weather
    effects.
-   Configurable intensity and performance impact.
-   Avoid harsh survival penalties: hunger/cold should not become an
    oppressive micromanagement system.
-   Keep vanilla food baseline, add many meaningful dishes/recipes, and
    give food bonuses without complex nutrition spreadsheets.
-   No excessive food/item clutter.

# 13. Inventory, storage, crafting, and QoL

-   Expand inventory and improve sorting, search, auto-pickup, item
    handling, and configurable controls.
-   Large storage containers, storage search across systems,
    auto-sorting, linked storage, and optional magical storage.
-   Modular backpacks inspired by Sophisticated Backpacks:
    crafting/workbench/furnace/etc. modules and very large stack
    capacity (thousands per slot where feasible and balanced).
-   Improve recipes, recipe search, mass crafting, and production
    automation while preserving vanilla crafting.
-   JEI-like recipe viewer and clear Russian tooltips.
-   WTHIT-like block/entity tooltip: show useful block/entity
    information with near feature parity.
-   Tree-felling mechanic: break one base log block and fell the whole
    tree; receive combined wood/sticks/saplings/other tree drops. Axe
    durability must be calculated as if all tree blocks were broken.
    Make the feature toggleable.
-   Avoid too many ores: add only useful recipe materials and a limited
    set of distinctive RPG ores; any additional ore must have clear
    purpose.
-   Do not add redundant item analogues. Every new item/block/mechanic
    must have meaningful recipes, uses, or gameplay purpose.

# 14. Map, travel, and navigation

-   Integrate **Xaero's Minimap and Xaero's World Map functionality as
    closely as legally/technically possible**, including minimap, full
    world map, zoom, waypoints, coordinates, explored areas, cave maps
    where supported, and map usability.
-   Preserve map teleportation functionality where available, subject to
    world/server permissions and configuration. Do not misrepresent
    teleport as a separate transport system.
-   Keep vanilla controls as baseline; allow key remapping, interface
    changes, and improved mount/flight controls.
-   Additional transport (carriages, magical travel, etc.) is optional
    and should only be added if useful, balanced, and not redundant.

# 15. UI, guidebook, achievements, and player feedback

-   Beautiful, coherent HUD, inventory, info panels, menus, minimap,
    recipe viewer, and entity/block tooltip.
-   Preserve vanilla achievements and add RPG achievements, rewards,
    hidden achievements, and an adventure journal.
-   A Russian guidebook must be provided at world creation and
    accessible later from a menu.
-   Guidebook search/filter by mod, category, mechanic, item, recipe,
    and system.
-   Explain controls, abilities, progression, mechanics, crafting, NPC
    systems, kingdom management, dragons, and add-ons.
-   Guidebook must update automatically as new mods/content are
    installed.
-   Use clear, readable, non-overloaded UI. Allow interface
    customization.

# 16. Audio, atmosphere, graphics, and visual direction

## 16.1 Audio

-   Preserve C418 vanilla music as the foundation.
-   Add contextual music for biomes, cities, dungeons, bosses, and
    special events.
-   Provide independent volume/frequency controls; music should be
    moderate, not constant/noisy.
-   Add ambient sounds, weather sounds, NPC/creature sounds, equipment
    and magic sounds.
-   Keep audio atmosphere rich but not excessive.

## 16.2 Visuals

-   Improve vanilla textures coherently, not with hyper-detailed
    clutter.
-   Use stylized/high-quality models, textures, and animations.
-   Improve lighting, soft shadows, dynamic light from torches/magic,
    and optional shaders.
-   Effects for magic, combat, weather, and environment must be
    individually disableable.
-   Graphics direction: blend dark fantasy, fairy tale, colorful
    adventure, and vanilla-like beautification; different biomes may
    have different styles.
-   Resource packs: base pack plus several swappable packs; support
    model/sound/shader options.
-   Improve Ice and Fire dragon/amphithere models, textures, animations,
    proportions, skeletons, and gear.
-   Keep armor/weapons modestly detailed, not hyper-realistic or
    excessively 3D-detailed.

## 16.3 Performance profiles

Provide: - Maximum FPS / low-effects profile. - Balanced profile. -
Maximum visuals while maintaining a stable 60 FPS target where hardware
allows. - Toggleable heavy graphics/effects. - Automatic hardware
profile selection plus manual override. - Diagnostics and clear
performance reports. - Laptop-friendly optimization, controlled entity
counts, spawn rates, memory use, and world-generation load. - Do not
guarantee a stable 60 FPS on every machine; measure and report actual
results.

# 17. Multiplayer, server, and save safety

-   Full singleplayer and multiplayer support; friends' server and
    dedicated server compatibility.
-   Cooperative bosses/dungeons, shared construction, shared kingdom,
    server settings.
-   Respect server authority for teleportation, destruction, raids,
    progression, and cheats.
-   Backups: automatic and manual, before updates, crash recovery,
    restore, and save-integrity protection.
-   Safe mod disabling where possible. Warn clearly when disabling a mod
    can corrupt or alter a world; offer backups before risky changes.
-   Updates must be compatibility-checked and not applied blindly to
    existing saves.

# 18. Russian launcher and pack management

Create a separate launcher/application (not just a mod menu) with: -
Russian UI. - One-click launch. - Mod catalog with installed mods,
descriptions, mechanics, settings, search/filter, dependencies,
compatibility/performance information. - Mod/category toggles and
settings. - Graphics profiles, versions, server management, updates,
restoration, backups. - User add-ons and future content packs. - Update
checks and changelog. - Automatic compatibility checks before
installing/updating. - Ability to restore a known-good version. -
Internal catalog of custom and third-party mods, with licensing and
provenance information. - Never install/update silently in a way that
risks a user's world.

# 19. Compatibility, licensing, testing, and quality gates

For every component: - Record source, license, redistribution/fork
permissions, version, loader, dependencies, and maintenance status. -
Avoid copying protected assets or code without permission. Create
original replacements when needed. - Check duplicate features, API
conflicts, mixin conflicts, world-generation conflicts, registry
collisions, client/server mismatches, and dependency/version issues. -
Run automated build, launch, crash, configuration, localization, and
compatibility tests. - Test new world generation, existing saves,
multiplayer, server startup, and entity/structure behavior. - Profile
startup, memory, FPS, world generation, exploration, entity-heavy areas,
combat, and dimensions. - Test Russian localization completeness and
search for visible English strings. - Run regression tests before every
release/update. - Test dragon protection against player builds;
specifically verify dragons cannot destroy protected homes/settlements
during flight, escape, or combat. - Test save/restore and safe
disable/enable paths. - Maintain a compatibility matrix and known-issues
list. - When automated tests cannot prove behavior, label it **requires
in-game/manual verification**.

# 20. Development phases and deliverables

## Phase A --- Research and architecture

-   Select Minecraft version and loader based on ecosystem support.
-   Audit existing mods, licenses, dependencies, source availability,
    and feature overlap.
-   Create module map, dependency graph, design decisions, and risk
    register.
-   Define Russian localization standards and terminology.
-   Produce initial technical architecture and a realistic roadmap.

**Deliverables:** architecture document, mod inventory, license
inventory, compatibility matrix, development roadmap.

## Phase B --- Stable technical foundation

-   Build RPG Core, configuration conventions, localization audit,
    diagnostics, save safety, guidebook infrastructure, and integration
    layer.
-   Establish CI/build/test workflow and a reproducible baseline pack.

**Deliverables:** stable bootable baseline, automated checks, Russian
settings shell, backup system.

## Phase C --- Core gameplay

-   RPG progression, classes/skills, combat, equipment, magic,
    creatures, and QoL.
-   Preserve vanilla survival/building and avoid redundant systems.

**Deliverables:** playable RPG core with configuration and guidebook.

## Phase D --- World and adventure

-   Terrain/biomes, structures, settlements, dungeons, dimensions,
    bosses, events, seasons/weather.
-   Verify world generation, exploration performance, and save
    compatibility.

**Deliverables:** exploration/adventure release.

## Phase E --- NPCs, economy, and kingdom

-   NPC/social systems, faction relations, quests, universal coin
    economy, settlements, kingdom development, and autonomous
    construction.
-   Keep strategy mechanics secondary to sandbox/RPG.

**Deliverables:** living-world and kingdom systems.

## Phase F --- Dragon overhaul

-   Research legally available Ice and Fire source and assets.
-   Implement dragon/amphithere overhaul, protected building rules,
    flight/commands, breeding/genetics, gear, UI, content cleanup.
-   Test all dragon types, ages, combat, nests, breeding, storage,
    death, multiplayer, and protected zones.

**Deliverables:** standalone Dragon Overhaul module/add-on with Russian
guidebook and tests.

## Phase G --- Visual/audio and performance

-   Resource packs, models, animations, lighting, shaders, music,
    ambient audio, and performance profiles.
-   Test on low-end laptop profile and high-visual profile.

**Deliverables:** graphics/audio profiles and performance report.

## Phase H --- Launcher, integration, and release

-   Launcher, mod catalog, updates, settings, backups, server
    management, and user add-ons.
-   Integrate all modules, run regression and compatibility tests,
    prepare stable release.

**Deliverables:** ready-to-install pack, launcher, documentation,
changelog, known issues, restore instructions.

## Phase I --- Expansion workflow

-   Add new mods/features as separate modules or add-ons.
-   Run compatibility/localization/performance checks before inclusion.
-   Preserve stable releases and provide rollback.

**Deliverables:** repeatable expansion process and add-on
SDK/documentation.

------------------------------------------------------------------------

# 21. Acceptance criteria (definition of done)

The project is not "done" merely because it launches once. A release is
accepted only when:

-   It launches in the selected Minecraft version/loader without known
    blocking crashes.
-   All modules have documented dependencies and compatibility status.
-   Player-visible content is Russian; remaining untranslated strings
    are reported and fixed.
-   The guidebook is available at world creation and later, searchable,
    and updated.
-   The player can explore, fight, level, use classes/magic, acquire
    gear, enter dungeons/dimensions, and continue sandbox play.
-   Settlements/NPCs/quests/economy work without excessive simulation or
    strategy complexity.
-   Player kingdom building and NPC construction work in a tested
    scenario.
-   Dragons retain the selected types and core mechanics; player-built
    structures are protected by default.
-   Dragon commands, flight, gear, growth, breeding, death, and
    amphithere systems are tested to the extent implemented.
-   Multiplayer/server behavior is tested and limitations documented.
-   Backups and rollback are demonstrated.
-   Performance is measured in representative scenarios; profiles and
    actual results are documented.
-   No obvious duplicate items/mechanics or useless content remain
    without a reason.
-   A changelog, known-issues list, installation instructions, and
    Russian player manual are included.
-   The project is modular: a failure or update in one feature should
    not require rebuilding unrelated systems unnecessarily.

------------------------------------------------------------------------

# 22. Required working style for the AI agent

1.  **Do not attempt to build the entire project in one giant step.**
2.  First return a feasibility report, version/loader recommendation,
    module map, licensing risks, and staged plan.
3.  Then implement one module at a time, with a working build and tests
    after each milestone.
4.  Keep each mod's scope narrow and its dependencies explicit.
5.  Use shared APIs instead of hard-coded cross-mod coupling.
6.  Keep all player-facing UI and content Russian from the
    beginning---not as a final afterthought.
7.  Ask the user only about major irreversible design decisions, legal
    limitations, or unavoidable feature trade-offs. Make routine
    technical choices autonomously and document them.
8.  Never claim a feature is complete until it is built and tested.
    Clearly separate implemented, partially implemented, planned, and
    unverified features.
9.  Maintain a master task tracker with statuses: `Not started`,
    `Research`, `In progress`, `Blocked`, `Implemented`, `Tested`,
    `Released`.
10. Maintain a decision log, compatibility matrix, changelog, backups,
    and reproducible build artifacts.
11. Prioritize stability, performance, compatibility, meaningful
    content, atmosphere, and player freedom.
12. Preserve the user's intent: a large, rich fantasy RPG Minecraft
    sandbox---not an overloaded, grind-heavy, laggy collection of
    unrelated mods.

------------------------------------------------------------------------

# 23. First tasks to execute immediately

1.  Create project repository/workspace and master task tracker.
2.  Research current Minecraft versions and mod ecosystem; choose
    version and loader with evidence.
3.  Build a feature-to-existing-mod/custom-mod matrix for every module.
4.  Audit licenses and source access, especially Ice and Fire and Xaero
    functionality; do not copy code/assets without permission.
5.  Identify the minimum stable baseline and test it.
6.  Propose exact module names, boundaries, dependencies, and build
    order.
7.  Create Russian localization glossary and automated
    untranslated-string audit.
8.  Produce the detailed technical plan and risks before writing large
    amounts of code.
9.  Begin with the shared foundation and a small playable vertical
    slice.
10. Expand only after the slice builds, launches, is localized, and
    passes tests.

**Final instruction:** Treat this document as the master requirements
baseline. Preserve every requirement unless a real technical, legal, or
compatibility constraint prevents it. If something must change, document
the reason, impact, and closest acceptable alternative.
