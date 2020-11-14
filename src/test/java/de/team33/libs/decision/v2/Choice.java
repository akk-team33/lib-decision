package de.team33.libs.decision.v2;

import java.util.function.Predicate;

public interface Choice<I, R> {

    Case<I, R> getPreCondition();

    Predicate<I> getCondition();

    Case<I, R> getPositive();

    Case<I, R> getNegative();
}
