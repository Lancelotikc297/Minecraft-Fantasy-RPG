package org.fantasyrpg.core.guidebook;

import org.fantasyrpg.core.data.RPGClass;
import org.fantasyrpg.core.magic.SpellRegistry;
import org.fantasyrpg.core.skill.SkillRegistry;

import java.util.*;

/**
 * Внутриигровое руководство и энциклопедия RPG на русском языке.
 * Разделы динамически индексируют классы, заклинания, механику драконов и экономику.
 * Согласовано с разделом 15 мастер-спецификации.
 */
public class GuidebookManager {

    private static final GuidebookManager INSTANCE = new GuidebookManager();
    private final Map<String, GuidebookPage> pages = new LinkedHashMap<>();

    private GuidebookManager() {
        buildDefaultPages();
    }

    public static GuidebookManager getInstance() {
        return INSTANCE;
    }

    private void buildDefaultPages() {
        pages.put("welcome", new GuidebookPage("welcome", "Введение в мир",
                "Добро пожаловать в мир Fantasy RPG! Это модульная песочница, сохраняющая строительство и выживание, но добавляющая глубину ролевой прогрессии, классы, заклинания, драконов и живые поселения."));

        pages.put("classes", new GuidebookPage("classes", "Классы и специализации",
                "В мире доступно 6 основных классов: Воин (танкование и щиты), Паладин (священная магия и защита), Маг (стихии огня, льда и молнии), Некромант (темная магия и призыв скелетов), Ассасин (скрытность и яды) и Лучник (дальний бой)."));

        pages.put("magic", new GuidebookPage("magic", "Школы магии",
                "Изучайте заклинания Огня, Льда, Молнии, Тьмы и Священного Света. Внимание: школы пространственной магии и иллюзий запрещены древним равновесием мира."));

        pages.put("dragons", new GuidebookPage("dragons", "Драконы и защита баз",
                "В мире обитают Огненные, Ледяные и Громовые драконы. Все постройки игрока надежно защищены от случайного разрушения пламенем драконов по умолчанию! Прирученные драконы подчиняются командам и могут нести всадника и грузы."));

        pages.put("economy", new GuidebookPage("economy", "Универсальная валюта",
                "Экономика базируется на чеканных монетах: 100 медных монет равны 1 серебряной монете, 100 серебряных монет равны 1 золотой монете. Все торговцы мира принимают единую валюту."));
    }

    public GuidebookPage getPage(String id) {
        if (id == null) return null;
        return pages.get(id.toLowerCase());
    }

    public Collection<GuidebookPage> getAllPages() {
        return Collections.unmodifiableCollection(pages.values());
    }

    public static class GuidebookPage {
        private final String id;
        private final String title;
        private final String content;

        public GuidebookPage(String id, String title, String content) {
            this.id = id;
            this.title = title;
            this.content = content;
        }

        public String getId() {
            return id;
        }

        public String getTitle() {
            return title;
        }

        public String getContent() {
            return content;
        }
    }
}
