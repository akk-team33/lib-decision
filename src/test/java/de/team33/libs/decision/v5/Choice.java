package de.team33.libs.decision.v5;

import java.util.Optional;
import java.util.function.Predicate;

import static de.team33.libs.decision.v5.Cases.*;

public final class Choice<I, R> {

    private final Case<R> preCondition;
    private final Predicate<I> condition;
    private final Case<R> positive;
    private final Case<R> negative;

    private Choice(final Stage<I, R> stage) {
        this.preCondition = stage.preCondition;
        this.condition = stage.condition;
        this.positive = Optional.ofNullable(stage.positiveResult)
                                .map(result -> definite(stage.positiveCase, result))
                                .orElse(stage.positiveCase);
        this.negative = Optional.ofNullable(stage.negativeResult)
                                .map(result -> definite(stage.negativeCase, result))
                                .orElse(stage.negativeCase);
    }

    public static <I, R> Stage<I, R> stage(final Case<R> preCondition, final Predicate<I> condition) {
        return new Stage<>(preCondition, condition);
    }

    public static <I, R> Stage<I, R> stage(final Case<R> preCondition, final Predicate<I> condition, final R positive) {
        return new Stage<>(preCondition, condition).setPositiveResult(positive);
    }

    public static <I, R> Stage<I, R> stage(final Case<R> preCondition, final Predicate<I> condition,
                                           final R positive, final R negative) {
        return new Stage<>(preCondition, condition).setPositiveResult(positive)
                                                   .setNegativeResult(negative);
    }

    final Case<R> getPreCondition() {
        return preCondition;
    }

    final Predicate<I> getCondition() {
        return condition;
    }

    final Case<R> getPositive() {
        return positive;
    }

    final Case<R> getNegative() {
        return negative;
    }

    @SuppressWarnings({"FieldHasSetterButNoGetter", "WeakerAccess"})
    public static final class Stage<I, R> {

        private final Case<R> preCondition;
        private final Predicate<I> condition;

        private R positiveResult;
        private R negativeResult;
        private Case<R> positiveCase;
        private Case<R> negativeCase;

        private Stage(final Case<R> preCondition, final Predicate<I> condition) {
            this.preCondition = preCondition;
            this.condition = condition;
        }

        public final Stage<I, R> setPositiveResult(final R result) {
            this.positiveResult = result;
            return this;
        }

        public final Stage<I, R> setNegativeResult(final R result) {
            this.negativeResult = result;
            return this;
        }

        public final Stage<I, R> setPositiveCase(final Case<R> value) {
            this.positiveCase = value;
            return this;
        }

        public final Stage<I, R> setNegativeCase(final Case<R> value) {
            this.negativeCase = value;
            return this;
        }

        public final Choice<I, R> build(final Case<R> positive) {
            return setPositiveCase(positive).setNegativeCase(not(positive)).build();
        }

        public final Choice<I, R> build() {
            return new Choice<>(this);
        }
    }
}
