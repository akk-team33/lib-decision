package de.team33.libs.decision.v4;

import java.util.function.Predicate;

public class Stage<I, R> {

    public static <I, R> Stage<I, R> of(final Case preCondition, final Predicate<I> condition) {
        throw new UnsupportedOperationException("not yet implemented");
    }

    public static <I, R> Stage<I, R> of(final Case preCondition, final Predicate<I> condition, final R positive) {
        throw new UnsupportedOperationException("not yet implemented");
    }

    public static <I, R> Stage<I, R> of(final Case preCondition, final Predicate<I> condition,
                                        final R positive, final R negative) {
        throw new UnsupportedOperationException("not yet implemented");
    }

    public static <I, R> Stage<I, R> of(final Case preCondition, final R positive) {
        throw new UnsupportedOperationException("not yet implemented");
    }

    public final Stage<I, R> setPositive(final Case positive) {
        throw new UnsupportedOperationException("not yet implemented");
    }
}
