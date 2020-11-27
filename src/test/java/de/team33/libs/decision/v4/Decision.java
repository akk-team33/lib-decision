package de.team33.libs.decision.v4;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Stream;

public final class Decision<I, R> implements Function<I, R> {

    private final Map<Case, Choice<I, R>> backing;

    private Decision(final Map<Case, Choice<I, R>> backing) {
        this.backing = Collections.unmodifiableMap(backing);
    }

    @SafeVarargs
    public static <I, R> Decision<I, R> build(final Supplier<Stage<I, R>>... stages) {
        final Builder<I, R> builder = Stream.of(stages)
                                            .map(Supplier::get)
                                            .collect(Builder::new, Builder::add, Builder::addAll);
        return new Decision<>(builder.backing);
    }

    @Override
    public final R apply(final I input) {
        throw new UnsupportedOperationException("not yet implemented");
    }

    private static class Builder<I, R> {

        private final Map<Case, Choice<I, R>> backing = new HashMap<>(0);

        private void add(final Stage<I, R> stage) {
            throw new UnsupportedOperationException("not yet implemented");
        }

        private void addAll(final Builder<I, R> other) {
            throw new UnsupportedOperationException("not yet implemented");
        }
    }
}
