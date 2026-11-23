package org.polyfrost.oneconfig.api.config.v1.collect.impl;

import org.polyfrost.oneconfig.api.config.v1.Tree;

/**
 * Minimal stand-in for OneConfig's reflective option collector.
 *
 * <p>Polydium creates one while building its config tree. Since the fake
 * OneConfig does not have a settings UI, collecting is intentionally a no-op.</p>
 */
public class OneConfigCollector {
    public OneConfigCollector() {
    }

    public void handle(Tree tree, Object src, int depth) {
    }
}
