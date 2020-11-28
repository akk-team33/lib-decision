package de.team33.libs.decision.v1;

import java.util.Optional;
import java.util.function.Predicate;

final class Initial<I, R> implements Event<I, R> {

    @SuppressWarnings("rawtypes")
    static final Event INSTANCE = new Initial();

    private Initial() {
    }

    @Override
    public final Event<I, R> getPreCondition() {
        return this;
    }

    @Override
    public final Optional<Predicate<I>> getCondition() {
        return Optional.of(input -> true);
    }

    @Override
    public final Optional<R> getResult() {
        return Optional.empty();
    }

    @Override
    public final String toString() {
        return "NONE";
    }
}
