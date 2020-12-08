package de.team33.libs.decision.v3;

import org.omg.CORBA.INITIALIZE;

import java.util.Optional;
import java.util.function.Predicate;

final class Initial<I, R> implements Case<I, R> {

    @SuppressWarnings("rawtypes")
    static final Initial INSTANCE = new Initial();

    private Initial() {
    }

    @Override
    public final Case<I, R> getPreCondition() {
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