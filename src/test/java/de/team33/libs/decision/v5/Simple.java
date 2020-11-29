package de.team33.libs.decision.v5;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Predicate;

final class Simple<I, R> implements Case<I, R> {

    private static final AtomicInteger INDEX = new AtomicInteger(0);

    private final Case<I, R> preCondition;
    private final Predicate<I> condition;
    private final R result;
    private final int index = INDEX.incrementAndGet();

    Simple(final Case<I, R> preCondition, final Predicate<I> condition, final R result) {
        this.preCondition = preCondition;
        this.condition = condition;
        this.result = result;
    }

    @Override
    public final Case<I, R> getPreCondition() {
        return preCondition;
    }

    @Override
    public final Optional<Predicate<I>> getCondition() {
        return Optional.ofNullable(condition);
    }

    @Override
    public final Optional<R> getResult() {
        return Optional.ofNullable(result);
    }

    @Override
    public final String toString() {
        return getClass().getSimpleName() + "#" + index;
    }
}
