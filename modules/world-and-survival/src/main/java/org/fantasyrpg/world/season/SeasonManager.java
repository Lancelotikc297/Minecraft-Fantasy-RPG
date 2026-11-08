package org.fantasyrpg.world.season;

/**
 * Менеджер сезонов и климата (Раздел 12 спецификации):
 * «Four seasons, vegetation changes, temperature, climate zones, and regional weather.
 * Avoid harsh survival penalties: hunger/cold should not become an oppressive micromanagement system.»
 */
public class SeasonManager {

    public enum Season {
        SPRING("spring", "Весна", "Цветение растений, мягкая температура, ускоренный рост культур"),
        SUMMER("summer", "Лето", "Теплый климат, частые грозы, обильный урожай"),
        AUTUMN("autumn", "Осень", "Золотая листва, туманы, сбор созревших плодов"),
        WINTER("winter", "Зима", "Снегопады в горах, замерзание озер, северное сияние");

        private final String id;
        private final String russianName;
        private final String description;

        Season(String id, String russianName, String description) {
            this.id = id;
            this.russianName = russianName;
            this.description = description;
        }

        public String getId() {
            return id;
        }

        public String getRussianName() {
            return russianName;
        }

        public String getDescription() {
            return description;
        }
    }

    private static final SeasonManager INSTANCE = new SeasonManager();
    private Season currentSeason = Season.SPRING;
    private int dayInSeason = 1;
    public static final int DAYS_PER_SEASON = 7; // 7 игровых дней на сезон

    private SeasonManager() {}

    public static SeasonManager getInstance() {
        return INSTANCE;
    }

    public Season getCurrentSeason() {
        return currentSeason;
    }

    public int getDayInSeason() {
        return dayInSeason;
    }

    public void advanceDay() {
        dayInSeason++;
        if (dayInSeason > DAYS_PER_SEASON) {
            dayInSeason = 1;
            cycleSeason();
        }
    }

    private void cycleSeason() {
        this.currentSeason = switch (currentSeason) {
            case SPRING -> Season.SUMMER;
            case SUMMER -> Season.AUTUMN;
            case AUTUMN -> Season.WINTER;
            case WINTER -> Season.SPRING;
        };
    }

    public String renderStatus() {
        return String.format("Текущий сезон: %s (День %d/%d). %s",
                currentSeason.getRussianName(), dayInSeason, DAYS_PER_SEASON, currentSeason.getDescription());
    }
}
