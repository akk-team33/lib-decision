package de.team33.libs.decision.v2;

import java.util.function.Function;
import java.util.function.Predicate;

public class Choice<P, R> implements Function<P, R> {

    private final Predicate<P> condition;
    private final Function<P, R> positive;
    private final Function<P, R> negative;

    private Choice(final Predicate<P> condition, final Function<P, R> positive, final Function<P, R> negative) {
        this.condition = condition;
        this.positive = positive;
        this.negative = negative;
    }

    public static <P, R> Choice<P, R> of(final Predicate<P> condition, final R positive, final R negative) {
        return when(condition).then(positive).orElse(negative);
    }

    public static <P, R> Choice<P, R> of(final Predicate<P> condition,
                                         final R positive, final Function<P, R> negative) {
        return when(condition).then(positive).orElse(negative);
    }

    public static <P, R> Choice<P, R> of(final Predicate<P> condition,
                                         final Function<P, R> positive, final R negative) {
        return when(condition).then(positive).orElse(negative);
    }

    public static <P, R> Choice<P, R> of(final Predicate<P> condition,
                                         final Function<P, R> positive, final Function<P, R> negative) {
        return when(condition).then(positive).orElse(negative);
    }

    @Override
    public final R apply(final P parameter) {
        return (condition.test(parameter) ? positive : negative).apply(parameter);
    }

    public static <P> Condition<P> when(final Predicate<P> condition) {
        return new Condition<P>(condition);
    }

    public static class Condition<P> {

        private final Predicate<P> condition;

        private Condition(final Predicate<P> condition) {
            this.condition = condition;
        }

        public final <R> Consequence<P, R> then(final Function<P, R> positive) {
            return negative -> new Choice<>(condition, positive, negative);
        }

        public final <R> Consequence<P, R> then(final R positive) {
            return then(any -> positive);
        }
    }

    public interface Consequence<P, R> {

        Choice<P, R> orElse(Function<P, R> negative);

        default Choice<P, R> orElse(final R negative) {
            return orElse(any -> negative);
        }
    }
}
