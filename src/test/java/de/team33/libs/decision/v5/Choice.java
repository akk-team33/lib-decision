package de.team33.libs.decision.v5;

import java.util.Optional;
import java.util.function.Predicate;

import static de.team33.libs.decision.v5.Cases.definite;
import static de.team33.libs.decision.v5.Cases.not;

/**
 * Represents a decision option, in other words a choice.
 * <p>
 * Based on a precondition (a case that has already been confirmed), a decision based on a condition either leads to a
 * (positive) case or to its (negative) opposite.
 * <pre>
 *
 *     pre-condition
 *        (Case)
 *          |
 *          ˅
 *      condition
 *     (Predicate)
 *      /       \
 *     ˅         ˅
 *  positive  negative
 *   (Case)    (Case)
 * </pre>
 */
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

    /**
     * Prepares a choice that is preceded by a specific case and that tests a specific condition.
     */
    public static <I, R> Stage<I, R> stage(final Case<R> preCondition, final Predicate<I> condition) {
        return new Stage<>(preCondition, condition);
    }

    /**
     * Prepares a choice preceded by a specific case, testing a specific condition,
     * and determining an end result for the positive case.
     */
    public static <I, R> Stage<I, R> stage(final Case<R> preCondition, final Predicate<I> condition, final R positive) {
        return new Stage<>(preCondition, condition).setPositiveResult(positive);
    }

    /**
     * Prepares a choice preceded by a particular case, testing a particular condition,
     * and determining an end result for the positive and negative cases.
     */
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

    /**
     * Represents a preliminary stage to a {@link Choice}.
     */
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

        /**
         * Sets a positive result for this Stage.
         */
        public final Stage<I, R> setPositiveResult(final R result) {
            this.positiveResult = result;
            return this;
        }

        /**
         * Sets a negative result for this Stage.
         */
        public final Stage<I, R> setNegativeResult(final R result) {
            this.negativeResult = result;
            return this;
        }

        /**
         * Sets a positive {@link Case} for this Stage.
         * <p>
         * If a {@link #setPositiveResult(Object) positive result} is defined, the case defined here will be associated
         * with that result.
         */
        public final Stage<I, R> setPositiveCase(final Case<R> value) {
            this.positiveCase = value;
            return this;
        }

        /**
         * Sets a negative {@link Case} for this Stage.
         * <p>
         * If a {@link #setNegativeResult(Object) negative result} is defined, the case defined here will be associated
         * with that result.
         */
        public final Stage<I, R> setNegativeCase(final Case<R> value) {
            this.negativeCase = value;
            return this;
        }

        /**
         * Creates a {@link Choice} from this Stage.
         */
        public final Choice<I, R> build() {
            return new Choice<>(this);
        }

        /**
         * Sets a positive {@link Case} for this Stage, implies a negative {@link Case} and creates a {@link Choice}
         * from it.
         */
        public final Choice<I, R> build(final Case<R> positive) {
            return setPositiveCase(positive).setNegativeCase(not(positive)).build();
        }
    }
}
