package de.team33.libs.decision.v2;

import java.util.Optional;
import java.util.function.Predicate;

public interface Choice<I, R> {

    static <I, R> Choice<I, R> tail(final Choice<I, R> preCondition, final Case postCase, final R result) {
        throw new UnsupportedOperationException("not yet implemented");
    }

    static <I, R> Choice<I, R> mean(final Choice<I, R> preCondition, final Case postCase, final Predicate<I> nextCondition) {
        throw new UnsupportedOperationException("not yet implemented");
    }

    static <I, R> Choice<I, R> head(final Predicate<I> nextCondition) {
        throw new UnsupportedOperationException("not yet implemented");
    }

    Choice<I, R> getPreCondition();

    Case getCase();

    Optional<Predicate<I>> getNextCondition();

    Optional<R> getResult();
}
