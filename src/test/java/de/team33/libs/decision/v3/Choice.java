package de.team33.libs.decision.v3;

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

    public static <P, R> Function<P, R> definite(final R value) {
        return parameter -> value;
    }

    public static <P, R> Function<P, R> choice(final Predicate<P> condition, final R positive, final R negative) {
        return choice(condition, Choice.<P, R>definite(positive), Choice.<P, R>definite(negative));
    }

    public static <P, R> Function<P, R> choice(final Predicate<P> condition,
                                               final R positive, final Function<P, R> negative) {
        return new Choice<>(condition, definite(positive), negative);
    }

    public static <P, R> Function<P, R> choice(final Predicate<P> condition,
                                               final Function<P, R> positive, final R negative) {
        return new Choice<>(condition, positive, definite(negative));
    }

    public static <P, R> Function<P, R> choice(final Predicate<P> condition,
                                               final Function<P, R> positive, final Function<P, R> negative) {
        return new Choice<>(condition, positive, negative);
    }

    @Override
    public final R apply(final P parameter) {
        return (condition.test(parameter) ? positive : negative).apply(parameter);
    }
}
