package de.team33.libs.decision.v6;

import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Stream;

/**
 * A {@link Distinction} is primarily a {@link Function} that applies a series of conditions to its parameter in order
 * to successively arrive at a result.
 * <p>
 * Since the number of conditions is finite, the result set is also finite.
 * <p>
 * In the simplest case, a single condition is checked as part of a {@link Distinction}. Typically, it then has two
 * possible results. Only a single regular result is conceivable, and alternatively an exception. Whereby the exception
 * can also be seen as a result, so there are two again.
 * <p>
 * Usually the result of a {@link Distinction} will depend on several conditions, whereby not all possible conditions
 * must in fact be considered under any circumstances. Instead, the relevance of a condition may depend on the result
 * of checking a precondition.
 * <p>
 * Here is a small example of how a {@link Distinction} can be used in conjunction with {@link Choice}s and
 * {@link Case}s:
 * <pre>
 *
 * public enum Signum implements Case&lt;Integer&gt;, Supplier&lt;Choice&lt;Integer, Integer&gt;&gt; {
 *
 *     ZERO(prepare(pending(), input -&gt; input == 0, 0)),
 *     POSITIVE(prepare(not(ZERO), input -&gt; input &gt; 0, 1, -1));
 *
 *     private static final Distinction&lt;Integer, Integer&gt; DISTINCTION = Distinction.of(values());
 *
 *     private final Choice.Stage&lt;Integer, Integer&gt; backing;
 *
 *     Signum(final Choice.Stage&lt;Integer, Integer&gt; backing) {
 *         this.backing = backing;
 *     }
 *
 *     public static int apply(final int input) {
 *         return DISTINCTION.apply(input);
 *     }
 *
 *     &#64;Override
 *     public final Choice&lt;Integer, Integer&gt; get() {
 *         return backing.build(this);
 *     }
 * }
 * </pre>
 *
 * @param <P> The parameter type
 * @param <R> The result type
 */
public class Distinction<P, R> implements Function<P, R> {

    /**
     * A {@link Distinction} results from a number of {@link Choice}s, each being made up of a precondition,
     * an actual condition and two possible outcomes. The latter are not necessarily final results of the
     * {@link Distinction}. These can also be preconditions for further {@link Choice}s.
     * <p>
     * This implementation expects a number of prepared {@link Choice}s in the form of appropriate {@link Supplier}s
     * as parameter.
     *
     * @param <P> The parameter type
     * @param <R> The result type
     */
    @SafeVarargs
    public static <P, R> Distinction<P, R> of(final Supplier<Choice<P, R>>... choices) {
        return of(Stream.of(choices)
                        .map(Supplier::get));
    }

    /**
     * A {@link Distinction} results from a number of {@link Choice}s, each being made up of a precondition,
     * an actual condition and two possible outcomes. The latter are not necessarily final results of the
     * {@link Distinction}. These can also be preconditions for further {@link Choice}s.
     *
     * @param <P> The parameter type
     * @param <R> The result type
     */
    public static <R, P> Distinction<P, R> of(final Stream<Choice<P, R>> choices) {
        throw new UnsupportedOperationException("not yet implemented");
    }

    @Override
    public final R apply(final P input) {
        throw new UnsupportedOperationException("not yet implemented");
    }
}
