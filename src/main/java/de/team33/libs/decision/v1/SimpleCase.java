package de.team33.libs.decision.v1;

import java.util.Optional;
import java.util.function.Predicate;

public class SimpleCase<I, R> implements Case<I, R> {

    private final Case<I, R> preCondition;
    private final Predicate<I> condition;
    private final R result;

    public SimpleCase(final Case<I, R> preCondition, final Predicate<I> condition, final R result) {
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
}
