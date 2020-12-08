package de.team33.libs.decision.v5;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Stream;

import static de.team33.libs.decision.v5.Cases.pending;
import static java.util.Collections.unmodifiableMap;

/**
 * A Decision is finally a {@link Function} that generates a result from an input parameter by walking through
 * {@link Case}s and {@link Choice}s.
 * <p>
 * Here is a simple use case:
 * <pre>
 *
 * import de.team33.libs.decision.v5.Case;
 * import de.team33.libs.decision.v5.Choice;
 * import de.team33.libs.decision.v5.Decision;
 *
 * import java.util.function.Supplier;
 *
 * import static de.team33.libs.decision.v5.Cases.not;
 * import static de.team33.libs.decision.v5.Cases.pending;
 * import static de.team33.libs.decision.v5.Choice.stage;
 *
 * public enum Signum implements Case&lt;Integer&gt;, Supplier&lt;Choice&lt;Integer, Integer>> {
 *
 *     ZERO(stage(pending(), input -&gt; input == 0, 0)),
 *     POSITIVE(stage(not(ZERO), input -&gt; input &gt; 0, 1, -1));
 *
 *     <b>private static final Decision&lt;Integer, Integer&gt; DECISION = Decision.build(values())</b>;
 *
 *     private final Choice.Stage&lt;Integer, Integer&gt; backing;
 *
 *     Signum(final Choice.Stage&lt;Integer, Integer&gt; backing) {
 *         this.backing = backing;
 *     }
 *
 *     public static int apply(final int input) {
 *         return DECISION.apply(input);
 *     }
 *
 *     &#64;Override
 *     public Choice&lt;Integer, Integer&gt; get() {
 *         return backing.build(this);
 *     }
 * }
 * </pre>
 */
public final class Decision<I, R> implements Function<I, R> {

    private final Map<Case<R>, Choice<I, R>> choices;

    private Decision(final Builder<I, R> builder) {
        choices = unmodifiableMap(builder.postConditions);
    }

    @SafeVarargs
    public static <I, R> Decision<I, R> build(final Supplier<Choice<I, R>>... choices) {
        return Stream.of(choices)
                     .map(Supplier::get)
                     .collect(() -> new Builder<I, R>(pending()), Builder::add, Builder::addAll)
                     .build();
    }

    private static <I, R> Case<R> nextCase(final Choice<I, R> choice, final I input) {
        return choice.getCondition().test(input) ? choice.getPositive() : choice.getNegative();
    }

    @Override
    public final R apply(final I input) {
        return apply(pending(), input);
    }

    private R apply(final Case<R> base, final I input) {
        return base.result().orElseGet(() -> applyPost(base, input));
    }

    private R applyPost(final Case<R> base, final I input) {
        return apply(nextCase(choices.get(base), input), input);
    }

    private static final class Builder<I, R> {

        private final Map<Case<R>, Choice<I, R>> postConditions = new HashMap<>(0);
        private final Set<Case<R>> defined = new HashSet<>(0);
        private final Set<Case<R>> used = new HashSet<>(0);

        private Builder(final Case<R> pending) {
            used.add(pending);
        }

        private void add(final Choice<I, R> choice) {
            final Case<R> pre = choice.getPreCondition();
            postConditions.put(pre, choice);
            defined.add(pre);
            addUsed(choice.getPositive());
            addUsed(choice.getNegative());
        }

        private void addUsed(final Case<R> consequence) {
            used.add(consequence);
            if (consequence.result().isPresent()) {
                defined.add(consequence);
            }
        }

        private void addAll(final Builder<I, R> ignored) {
            throw new UnsupportedOperationException("shouldn't be necessary here");
        }

        public Decision<I, R> build() {
            final Set<Object> undefined = new HashSet<>(used);
            undefined.removeAll(defined);
            if (!undefined.isEmpty()) {
                throw new UndefinedException(undefined);
            }

            final Set<Object> unused = new HashSet<>(defined);
            unused.removeAll(used);
            if (!unused.isEmpty()) {
                throw new UnusedException(unused);
            }

            return new Decision<>(this);
        }
    }
}
