package de.team33.libs.decision.v6;

import java.util.function.Predicate;

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
public class Choice<P, R> {

    /**
     * Prepares a {@link Choice} based on a precondition and the actual condition. The {@link Stage result} must then
     * be supplemented with the possible resulting {@link Case}s in order to ultimately make a {@link Choice}.
     */
    public static <P, R> Stage<P, R> prepare(final Case<R> preCondition, final Predicate<P> condition) {
        throw new UnsupportedOperationException("not yet implemented");
    }

    /**
     * Prepares a {@link Choice} based on a precondition, the actual condition and a final result for the (positive)
     * {@link Case} that the actual condition is met. The {@link Stage result} must then be supplemented with the
     * possible resulting {@link Case}s in order to ultimately make a {@link Choice}.
     */
    public static <P, R> Stage<P, R> prepare(final Case<R> preCondition, final Predicate<P> condition,
                                             final R positiveResult) {
        throw new UnsupportedOperationException("not yet implemented");
    }

    /**
     * Prepares a {@link Choice} based on a precondition, the actual condition and a final result for both possible
     * resulting {@link Case}s. The {@link Stage result} must then be supplemented with those {@link Case}s in order to
     * ultimately make a {@link Choice}.
     */
    public static <P, R> Stage<P, R> prepare(final Case<R> preCondition, final Predicate<P> condition,
                                             final R positiveResult, final R negativeResult) {
        throw new UnsupportedOperationException("not yet implemented");
    }

    /**
     * Represents a preliminary stage of a {@link Choice}.
     */
    public static final class Stage<P, R> {

        /**
         * Builds a choice by adding the possible positive {@link Case}.
         * The possible negative {@link Case} is implied using the {@link Cases#not(Case) opposite} of the positive
         * {@link Case}.
         */
        public final Choice<P, R> build(final Case<R> positive) {
            throw new UnsupportedOperationException("not yet implemented");
        }
    }
}
