package de.team33.libs.decision.v4;

import java.util.function.Predicate;

class Choice<I, R> {

    private final Predicate<I> condition;
    private final CaseEx<R> positive;
    private final CaseEx<R> negative;

    Choice(final Predicate<I> condition, final CaseEx<R> positive, final CaseEx<R> negative) {
        this.condition = condition;
        this.positive = positive;
        this.negative = negative;
    }
}
