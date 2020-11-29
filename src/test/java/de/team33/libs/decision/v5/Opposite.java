package de.team33.libs.decision.v5;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

final class Opposite<R> implements Case<R> {

    @SuppressWarnings("rawtypes")
    private static final Map<Case, Opposite> CACHE = new ConcurrentHashMap<>(0);

    private final Case<R> original;

    private Opposite(final Case<R> original) {
        this.original = original;
    }

    static <R> Case<R> of(final Case<R> original) {
        //noinspection unchecked
        return (original instanceof Opposite)
                ? ((Opposite<R>) original).original
                : CACHE.computeIfAbsent(original, Opposite::new);
    }

    @Override
    public final String name() {
        return "~" + original.name();
    }

    @Override
    public final String toString() {
        return name();
    }
}
