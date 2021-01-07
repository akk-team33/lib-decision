package de.team33.test.decision.v2;

import java.util.function.Function;
import java.util.function.Predicate;

import static de.team33.libs.decision.v2.Choice.*;

public enum Signum implements Function<Integer, Integer> {

    NEGATIVE(any -> -1),

    POSITIVE(any -> 1),

    NON_ZERO(when(Condition.POSITIVE).then(POSITIVE).orElse(NEGATIVE)),

    ANY(when(Condition.ZERO).then(0).orElse(NON_ZERO));

    private final Function<Integer, Integer> backing;

    Signum(final Function<Integer, Integer> backing) {
        this.backing = backing;
    }

    public static int map(final int input) {
        return ANY.apply(input);
    }

    @Override
    public Integer apply(final Integer input) {
        return backing.apply(input);
    }

    interface Condition extends Predicate<Integer> {

        Condition ZERO = input -> input == 0;
        Condition POSITIVE = input -> input > 0;
    }
}
