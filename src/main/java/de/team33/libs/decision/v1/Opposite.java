package de.team33.libs.decision.v1;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Predicate;

final class Opposite<I, R> implements Event<I, R> {

    @SuppressWarnings("rawtypes")
    private static final Map<Event, Opposite> CACHE = new ConcurrentHashMap<>(0);
    @SuppressWarnings("rawtypes")
    private static final Predicate NEVER = input -> false;

    private final Event<I, R> original;
    private final Predicate<I> predicate;

    private Opposite(final Event<I, R> original) {
        this.original = original;
        predicate = original.getCondition()
                            .map(Opposite::inverse)
                            .orElseGet(Opposite::never);
    }

    static <I, R> Event<I, R> of(final Event<I, R> original) {
        //noinspection unchecked
        return (original instanceof Opposite)
                ? ((Opposite<I, R>) original).original
                : CACHE.computeIfAbsent(original, Opposite::new);
    }

    private static <I> Predicate<I> inverse(final Predicate<I> predicate) {
        return input -> !predicate.test(input);
    }

    @SuppressWarnings("unchecked")
    private static <I> Predicate<I> never() {
        return NEVER;
    }

    @Override
    public final Event<I, R> getPreCondition() {
        return original.getPreCondition();
    }

    @Override
    public final Optional<Predicate<I>> getCondition() {
        return Optional.of(predicate);
    }

    @Override
    public final Optional<R> getResult() {
        return Optional.empty();
    }

    @Override
    public final String toString() {
        return "not(" + original + ")";
    }
}
