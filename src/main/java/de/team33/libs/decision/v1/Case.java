package de.team33.libs.decision.v1;

import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * Represents a case that can lead to an end result of a function or a further case distinction.
 * <p>
 * Typically, cases in a specific application context are defined as ENUM and summarized as CHOICES
 * <p>
 * <i>Example:</i>
 * <pre>
 *
 * package ...;
 *
 * import de.team33.libs.decision.v1.Case;
 * import de.team33.libs.decision.v1.Choices;
 *
 * import java.util.Optional;
 * import java.util.function.Predicate;
 *
 * import static de.team33.libs.decision.v1.Case.head;
 * import static de.team33.libs.decision.v1.Case.mean;
 * import static de.team33.libs.decision.v1.Case.not;
 * import static de.team33.libs.decision.v1.Case.tail;
 *
 * public enum Signum implements Case&lt;Integer, Integer&gt; {
 *
 *     NEGATIVE(head(input -&gt; input &lt; 0, -1)),
 *     POSITIVE(mean(not(NEGATIVE), input -&gt; input &gt; 0, 1)),
 *     ZERO(tail(not(POSITIVE), 0));
 *
 *     private static final Choices&lt;Integer, Integer&gt; CHOICES = Choices.build(values());
 *
 *     private final Case&lt;Integer, Integer&gt; backing;
 *
 *     Signum(final Case&lt;Integer, Integer&gt; backing) {
 *         this.backing = backing;
 *     }
 *
 *     public static int apply(final int input) {
 *         return CHOICES.apply(input);
 *     }
 *
 *     &#64;Override
 *     public final Case&lt;Integer, Integer&gt; getPreCondition() {
 *         return backing.getPreCondition();
 *     }
 *
 *     &#64;Override
 *     public final Optional&lt;Predicate&lt;Integer&gt;&gt; getCondition() {
 *         return backing.getCondition();
 *     }
 *
 *     &#64;Override
 *     public final Optional&lt;Integer&gt; getResult() {
 *         return backing.getResult();
 *     }
 * }
 * </pre>
 *
 * @param <I> The type of parameters to be expected
 * @param <R> The type of result to be expected
 */
public interface Case<I, R> {

    /**
     * Represents the {@link Case} where no decision has yet been made in the context of a decision tree or a decision
     * chain. It has the following properties:
     *
     * <ul>
     *     <li>Its {@link #getPreCondition() precondition} is irrelevant, respectively always met.</li>
     *     <li>Its {@link #getCondition() condition} condition is irrelevant, respectively always met.</li>
     *     <li>It never represents a final result</li>
     * </ul>
     */
    static <I, R> Case<I, R> pending() {
        return Cases.initial();
    }

    /**
     * Returns the opposite of an original {@link Case}. It has the following properties:
     * <ul>
     *     <li>It has the same {@link #getPreCondition() precondition} as the original.</li>
     *     <li>It has the inverse {@link #getCondition() condition} of the original.</li>
     *     <li>It never represents a final result</li>
     * </ul>
     * <p>
     * The opposite of a {@link Case} is not a negation in the strictly boolean sense.
     * In fact, the opposite of a case has the same precondition as the original case.
     * Only the case-specific condition is negated.
     */
    public static <I, R> Case<I, R> not(final Case<I, R> original) {
        return Cases.not(original);
    }

    /**
     * Returns a {@link Case} whose {@link #getPreCondition() precondition} is {@link #pending()} and which does not
     * represent a final result. Such a {@link Case} implies an {@link #not(Case) opposite}.
     */
    static <I, R> Case<I, R> head(final Predicate<I> condition) {
        return Cases.simple(Cases.initial(), condition, null);
    }

    /**
     * Returns a {@link Case} whose {@link #getPreCondition() precondition} is {@link #pending()} and which represents
     * a final result. Such a {@link Case} implies an {@link #not(Case) opposite}.
     */
    static <I, R> Case<I, R> head(final Predicate<I> condition, final R result) {
        return Cases.simple(Cases.initial(), condition, result);
    }

    static <I, R> Case<I, R> mean(final Case<I, R> preCondition, final Predicate<I> condition) {
        return Cases.simple(preCondition, condition, null);
    }

    static <I, R> Case<I, R> mean(final Case<I, R> preCondition, final Predicate<I> condition, final R result) {
        return Cases.simple(preCondition, condition, result);
    }

    static <I, R> Case<I, R> tail(final Case<I, R> preCondition, final R result) {
        return Cases.simple(preCondition, null, result);
    }

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
