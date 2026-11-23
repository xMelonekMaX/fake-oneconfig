package org.polyfrost.oneconfig.api.notifications.v1;

/**
 * Minimal stand-in for OneConfig's {@code Notifications} object. Notifications
 * are accepted and discarded instead of being drawn.
 */
public final class Notifications {
    public static final Notifications INSTANCE = new Notifications();

    private Notifications() {
    }

    public static Notification info(String title, String message) {
        return info(title, message, Notification.DEFAULT_DURATION);
    }

    public static Notification info(String title, String message, float duration) {
        return new Notification();
    }
}
