package de.team33.libs.decision.v1;

import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * Represents a case that can lead to an end result of a function or a further case distinction.
 *
 * @param <I> The type of parameters to be expected
 * @param <R> The type of result to be expected
 */
public interface Case<I, R> {

    /**
     * Returns the precondition for this {@link Case}.
     * <p>
     * In order to {@link #getCondition() clarify whether a certain case applies}, its precondition must apply.
     * <p>
     * Within a decision chain or a decision tree, exactly one case typically has no real precondition.
     * Such a case should return the pseudo-case {@link Cases#initial()}.
     */
    Case<I, R> getPreCondition();

    /**
     * Provides {@link Optional (indirectly)} a {@link Predicate condition} that (in addition to the
     * {@link #getPreCondition() precondition}) must be fulfilled for this {@link Case} to apply if such a condition
     * exists. This implies the {@link Cases#not(Case) opposite case}, in which the same precondition applies but this
     * condition does exactly not apply.
     * <p>
     * If no such condition exists (i.e. the result is {@link Optional#empty()}), this means that only the
     * {@link #getPreCondition() precondition} must be fulfilled for this case to apply. This fact does not imply an
     * opposite case (or an {@link Cases#not(Case) opposite case} that can never apply).
     */
    Optional<Predicate<I>> getCondition();

    /**
     * Provides {@link Optional (indirectly)} a {@link Function method} that can deliver the final result for the
     * appliance of this case, if no further case distinction is necessary.
     * <p>
     * Results in {@link Optional#empty()} if at least one further case distinction has to be made in order to arrive
     * at the final result.
     */
    Optional<R> getResult();
}
