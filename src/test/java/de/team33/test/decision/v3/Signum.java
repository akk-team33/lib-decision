package de.team33.test.decision.v3;

import java.util.function.Function;

import static de.team33.libs.decision.v3.Choice.*;

public enum Signum implements Function<Integer, Integer> {

    NEGATIVE(definite(-1)),

    POSITIVE(definite(1)),

    NON_ZERO(of(input -> input > 0, POSITIVE, NEGATIVE)),

    ANY(of(input -> input == 0, 0, NON_ZERO));

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
}
