package de.team33.libs.decision.v5;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

final class Definite<R> implements Case<R> {

    @SuppressWarnings("rawtypes")
    private static final Map<Case, Definite> CACHE = new ConcurrentHashMap<>(0);
    private static final String ALREADY_DEFINED =
            "Case <%s> should be associated with result <%s> but is already associated with result <%s>";

    private final Case<R> original;
    private final R result;

    private Definite(final Case<R> original, final R result) {
        this.original = original;
        this.result = result;
    }

    static <R> Case<R> of(final Case<R> original, final R result) {
        //noinspection unchecked
        return original.result()
                       .map(check(original, result))
                       .orElseGet(() -> CACHE.computeIfAbsent(original, newDefinite(result)));
    }

    private static <R> Function<R, Case<R>> check(final Case<R> original, final R result) {
        return oriResult -> {
            if (result.equals(oriResult)) {
                return original;
            } else {
                throw new IllegalArgumentException(String.format(ALREADY_DEFINED, original, result, oriResult));
            }
        };
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    private static <R> Function<Case, Definite> newDefinite(final R result) {
        return original -> new Definite(original, result);
    }

    @Override
    public final Optional<R> result() {
        return Optional.of(result);
    }

    @Override
    public final String name() {
        return original.name();
    }

    @Override
    public final String toString() {
        return name();
    }
}
