package de.team33.libs.decision.v4;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public final class Cases {

    private static final Map<Case, Opposite> CACHE = new ConcurrentHashMap<>(0);

    private Cases() {
    }

    public static Case not(final Case original) {
        return (original instanceof Opposite)
                ? ((Opposite) original).original
                : CACHE.computeIfAbsent(original, Opposite::new);
    }

    private static final class Opposite implements Case {

        private final Case original;

        private Opposite(final Case original) {
            this.original = original;
        }
    }
}
