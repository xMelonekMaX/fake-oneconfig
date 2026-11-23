package org.polyfrost.oneconfig.api.config.v1;

/**
 * Minimal stand-in for OneConfig's {@code Tree}. Only the factory methods used
 * by mods are present; the tree itself stores nothing.
 */
public class Tree {
    private final String id;

    protected Tree(String id) {
        this.id = id;
    }

    public static Tree tree() {
        return new Tree(null);
    }

    public static Tree tree(String id) {
        return new Tree(id);
    }

    public static Tree tree(Tree src) {
        return new Tree(src == null ? null : src.getID());
    }

    public String getID() {
        return id;
    }

    public String getTitle() {
        return null;
    }
}
