package de.team33.libs.decision.v1;

import java.util.Optional;
import java.util.function.Predicate;

import static de.team33.libs.decision.v1.Cases.not;

/**
 * A {@link Choice} consists of a precondition, an actual condition and two possible outcomes. The latter are not
 * necessarily final results of the {@link Choice} or an underlying {@link Distinction}. These can also be
 * preconditions for further {@link Choice}s.
 * <p>
 * In particular, both the precondition and the outcomes are represented as {@link Case}s. The actual condition
 * represents the core of a {@link Choice} and is treated as a {@link Predicate}.
 * <p>
 * It is noticeable that the attributes described above are not reflected in public properties or methods of the
 * {@link Choice} class itself. However, they are determined by a preliminary {@link Stage Stage}.
 *
 * @param <P> The parameter type of an underlying {@link Distinction}
 * @param <R> The result type of an underlying {@link Distinction}
 */
public final class Choice<P, R> {

    private final Case<R> preCondition;
    private final Predicate<P> condition;
    private final Case<R> positive;
    private final Case<R> negative;

    private Choice(final Stage<P, R> stage) {
        this.preCondition = stage.preCondition;
        this.condition = stage.condition;
        this.positive = Optional.ofNullable(stage.positiveResult)
                                .map(result -> Cases.setValueOf(stage.positiveCase, result))
                                .orElse(stage.positiveCase);
        this.negative = Optional.ofNullable(stage.negativeResult)
                                .map(result -> Cases.setValueOf(stage.negativeCase, result))
                                .orElse(stage.negativeCase);
    }

    /**
     * Prepares a {@link Choice} based on a precondition and the actual condition. The {@link Stage result} must then
     * be supplemented with the possible resulting {@link Case}s in order to ultimately make a {@link Choice}.
     */
    public static <P, R> Stage<P, R> prepare(final Case<R> preCondition, final Predicate<P> condition) {
        return new Stage<>(preCondition, condition);
    }

    /**
     * Prepares a {@link Choice} based on a precondition, the actual condition and a final result for the (positive)
     * {@link Case} that the actual condition is met. The {@link Stage result} must then be supplemented with the
     * possible resulting {@link Case}s in order to ultimately make a {@link Choice}.
     */
    public static <P, R> Stage<P, R> prepare(final Case<R> preCondition, final Predicate<P> condition,
                                             final R positive) {
        return new Stage<>(preCondition, condition).setPositiveResult(positive);
    }

    /**
     * Prepares a {@link Choice} based on a precondition, the actual condition and a final result for both possible
     * resulting {@link Case}s. The {@link Stage result} must then be supplemented with those {@link Case}s in order to
     * ultimately make a {@link Choice}.
     */
    public static <P, R> Stage<P, R> prepare(final Case<R> preCondition, final Predicate<P> condition,
                                             final R positive, final R negative) {
        return new Stage<>(preCondition, condition).setPositiveResult(positive)
                                                   .setNegativeResult(negative);
    }

    final Case<R> getPreCondition() {
        return preCondition;
    }

    final Predicate<P> getCondition() {
        return condition;
    }

    final Case<R> getPositive() {
        return positive;
    }

    final Case<R> getNegative() {
        return negative;
    }

    /**
     * Represents a preliminary stage of a {@link Choice}.
     */
    public static final class Stage<P, R> {

        private final Case<R> preCondition;
        private final Predicate<P> condition;

        private R positiveResult;
        private R negativeResult;
        private Case<R> positiveCase;
        private Case<R> negativeCase;

        private Stage(final Case<R> preCondition, final Predicate<P> condition) {
            this.preCondition = preCondition;
            this.condition = condition;
        }

        final Stage<P, R> setPositiveResult(final R result) {
            this.positiveResult = result;
            return this;
        }

        final Stage<P, R> setNegativeResult(final R result) {
            this.negativeResult = result;
            return this;
        }

        final Stage<P, R> setPositiveCase(final Case<R> value) {
            this.positiveCase = value;
            return this;
        }

        final Stage<P, R> setNegativeCase(final Case<R> value) {
            this.negativeCase = value;
            return this;
        }

        final Choice<P, R> build() {
            return new Choice<>(this);
        }

        /**
         * Builds a choice by adding the possible positive {@link Case}.
         * The possible negative {@link Case} is implied using the {@link Cases#not(Case) opposite} of the positive
         * {@link Case}.
         */
        public final Choice<P, R> build(final Case<R> positive) {
            return setPositiveCase(positive).setNegativeCase(not(positive)).build();
        }
    }
}
