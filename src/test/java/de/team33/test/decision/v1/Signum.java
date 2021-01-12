package de.team33.test.decision.v1;

import de.team33.libs.decision.v1.Choice;

import java.util.function.Function;
import java.util.function.Predicate;

public enum Signum implements Function<Integer, Integer> {

    POSITIVE(any -> 1),

    NON_ZERO(Choice.on(Condition.IS_POSITIVE).apply(POSITIVE).orReply(-1)),

    ANY(Choice.on(Condition.IS_ZERO).reply(0).orApply(NON_ZERO));

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

        Condition IS_ZERO = input -> input == 0;
        Condition IS_POSITIVE = input -> input > 0;
    }
}
