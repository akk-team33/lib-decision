package de.team33.libs.decision.v4;

import java.util.function.Predicate;

import static de.team33.libs.decision.v4.Cases.not;

public class Stage<I, R> {

    private final Case preCondition;
    private final Predicate<I> condition;
    private final R positive;
    private final R negative;
    private Case positiveCase;
    private Case negativeCase;

    private Stage(final Case preCondition, final Predicate<I> condition, final R positive, final R negative) {
        this.preCondition = preCondition;
        this.condition = condition;
        this.positive = positive;
        this.negative = negative;
    }

    public static <I, R> Stage<I, R> of(final Case preCondition, final Predicate<I> condition) {
        return new Stage<>(preCondition, condition, null, null);
    }

    public static <I, R> Stage<I, R> of(final Case preCondition, final Predicate<I> condition, final R positive) {
        return new Stage<>(preCondition, condition, positive, null);
    }

    public static <I, R> Stage<I, R> of(final Case preCondition, final Predicate<I> condition,
                                        final R positive, final R negative) {
        return new Stage<>(preCondition, condition, positive, negative);
    }

    public static <I, R> Stage<I, R> of(final Case preCondition, final R positive) {
        return new Stage<>(preCondition, null, positive, null);
    }

    public final Stage<I, R> setPositive(final Case positive) {
        this.positiveCase = positive;
        this.negativeCase = not(positive);
        return this;
    }

    final Case toPreCondition() {
        return preCondition;
    }

    final Choice<I, R> toChoice() {
        return new Choice<>(condition, new CaseEx<R>(positiveCase, positive), new CaseEx<R>(negativeCase, negative));
    }
}
