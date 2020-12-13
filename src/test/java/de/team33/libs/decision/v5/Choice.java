package de.team33.libs.decision.v5;

import java.util.Optional;
import java.util.function.Predicate;

import static de.team33.libs.decision.v5.Cases.definite;
import static de.team33.libs.decision.v5.Cases.not;

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
                                .map(result -> definite(stage.positiveCase, result))
                                .orElse(stage.positiveCase);
        this.negative = Optional.ofNullable(stage.negativeResult)
                                .map(result -> definite(stage.negativeCase, result))
                                .orElse(stage.negativeCase);
    }

    /**
     * Prepares a choice that is preceded by a specific case and that tests a specific condition.
     */
    public static <P, R> Stage<P, R> prepare(final Case<R> preCondition, final Predicate<P> condition) {
        return new Stage<>(preCondition, condition);
    }

    /**
     * Prepares a choice preceded by a specific case, testing a specific condition,
     * and determining an end result for the positive case.
     */
    public static <P, R> Stage<P, R> prepare(final Case<R> preCondition, final Predicate<P> condition,
                                             final R positive) {
        return new Stage<>(preCondition, condition).setPositiveResult(positive);
    }

    /**
     * Prepares a choice preceded by a particular case, testing a particular condition,
     * and determining an end result for the positive and negative cases.
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
     * Represents a preliminary stage to a {@link Choice}.
     */
    @SuppressWarnings({"FieldHasSetterButNoGetter", "WeakerAccess"})
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

        /**
         * Sets a positive result for this Stage.
         */
        public final Stage<P, R> setPositiveResult(final R result) {
            this.positiveResult = result;
            return this;
        }

        /**
         * Sets a negative result for this Stage.
         */
        public final Stage<P, R> setNegativeResult(final R result) {
            this.negativeResult = result;
            return this;
        }

        /**
         * Sets a positive {@link Case} for this Stage.
         * <p>
         * If a {@link #setPositiveResult(Object) positive result} is defined, the case defined here will be associated
         * with that result.
         */
        public final Stage<P, R> setPositiveCase(final Case<R> value) {
            this.positiveCase = value;
            return this;
        }

        /**
         * Sets a negative {@link Case} for this Stage.
         * <p>
         * If a {@link #setNegativeResult(Object) negative result} is defined, the case defined here will be associated
         * with that result.
         */
        public final Stage<P, R> setNegativeCase(final Case<R> value) {
            this.negativeCase = value;
            return this;
        }

        /**
         * Creates a {@link Choice} from this Stage.
         */
        public final Choice<P, R> build() {
            return new Choice<>(this);
        }

        /**
         * Sets a positive {@link Case} for this Stage, implies a negative {@link Case} and creates a {@link Choice}
         * from it.
         */
        public final Choice<P, R> build(final Case<R> positive) {
            return setPositiveCase(positive).setNegativeCase(not(positive)).build();
        }
    }
}
