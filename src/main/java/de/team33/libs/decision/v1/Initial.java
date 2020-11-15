package de.team33.libs.decision.v1;

import java.util.Optional;
import java.util.function.Predicate;

final class Initial<I, R> implements Case<I, R> {

    @SuppressWarnings("rawtypes")
    private static final Case INSTANCE = new Initial();

    private Initial() {
    }

    @SuppressWarnings("unchecked")
    static <R, I> Case<I, R> instance() {
        return INSTANCE;
    }

    @Override
    public final Case<I, R> getPreCondition() {
        return this;
    }

    @Override
    public Optional<Predicate<I>> getCondition() {
        return Optional.of(input -> true);
    }

    @Override
    public Optional<R> getResult() {
        return Optional.empty();
    }

    @Override
    public final String toString() {
        return "NONE";
    }
}
