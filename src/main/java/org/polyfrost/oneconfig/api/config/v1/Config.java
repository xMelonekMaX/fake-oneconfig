package org.polyfrost.oneconfig.api.config.v1;

/**
 * Minimal stand-in for OneConfig's {@code Config} API.
 *
 * <p>It keeps the exact binary surface that Polydium links against, but does no
 * registration, serialization or UI work.</p>
 */
public abstract class Config {
    public final String id;
    public final String title;
    public final String iconPath;
    public final Category category;

    protected Tree tree;

    protected Config(String id, String iconPath, String title, Category category) {
        this.id = id;
        this.iconPath = iconPath;
        this.title = title;
        this.category = category == null ? Category.OTHER : category;
    }

    public Config(String id, String title, Category category) {
        this(id, null, title, category);
    }

    /**
     * Builds the config tree. Kept so subclasses can override it, but nothing
     * consumes the result in this fake implementation.
     */
    protected Tree makeTree() {
        return Tree.tree(id);
    }

    protected void initialize(boolean byConfigManager) {
    }

    /**
     * Called by mods after construction. A no-op here, matching the point where
     * OneConfig would normally register the config.
     */
    public void preload() {
    }

    public Tree getTree() {
        return tree;
    }

    public void save() {
    }

    /** Same shape as OneConfig's config categories. */
    public static final class Category {
        public static final Category COMBAT = new Category("oneconfig.combat", 1);
        public static final Category QOL = new Category("oneconfig.qol", 2);
        public static final Category HYPIXEL = new Category("oneconfig.hypixel", 3);
        public static final Category OTHER = new Category("oneconfig.other", 4);
        public static final Category PERFORMANCE = new Category("oneconfig.performance", 5);
        public static final Category VISUALS = new Category("oneconfig.visuals", 6);
        public static final Category HUD = new Category("oneconfig.hud", 7);
        public static final Category UTILITY = new Category("oneconfig.utility", 8);

        private final String name;
        private final byte id;

        private Category(String name, int id) {
            this.name = name;
            this.id = (byte) id;
        }

        public String getName() {
            return name;
        }

        public byte getId() {
            return id;
        }

        @Override
        public String toString() {
            return name;
        }

        @Override
        public int hashCode() {
            return id;
        }
    }
}
