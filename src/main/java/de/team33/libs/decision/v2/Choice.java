package de.team33.libs.decision.v2;

import java.util.function.Function;
import java.util.function.Predicate;

/**
 * A functional implementation of a simple decision.
 *
 * Typically used to implement multiple choices
 *
 * @param <P>
 * @param <R>
 */
public class Choice<P, R> implements Function<P, R> {

    private final Predicate<P> condition;
    private final Function<P, R> positive;
    private final Function<P, R> negative;

    private Choice(final Predicate<P> condition, final Function<P, R> positive, final Function<P, R> negative) {
        this.condition = condition;
        this.positive = positive;
        this.negative = negative;
    }

    public static <P> Condition<P> on(final Predicate<P> condition) {
        return new Condition<P>(condition);
    }

    @Override
    public final R apply(final P parameter) {
        return (condition.test(parameter) ? positive : negative).apply(parameter);
    }

    @FunctionalInterface
    public interface Consequence<P, R> {

        Choice<P, R> orApply(Function<P, R> negative);

        default Choice<P, R> orReply(final R negative) {
            return orApply(any -> negative);
        }
    }

    public static class Condition<P> {

        private final Predicate<P> condition;

        private Condition(final Predicate<P> condition) {
            this.condition = condition;
        }

        public final <R> Consequence<P, R> apply(final Function<P, R> positive) {
            return negative -> new Choice<>(condition, positive, negative);
        }

        public final <R> Consequence<P, R> reply(final R positive) {
            return apply(any -> positive);
        }
    }
}
