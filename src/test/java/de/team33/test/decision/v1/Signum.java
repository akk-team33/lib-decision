package de.team33.test.decision.v1;

import de.team33.libs.decision.v5.Case;
import de.team33.libs.decision.v5.Choice;
import de.team33.libs.decision.v5.Distinction;

import java.util.function.Supplier;

import static de.team33.libs.decision.v5.Cases.not;
import static de.team33.libs.decision.v5.Cases.pending;
import static de.team33.libs.decision.v5.Choice.prepare;

public enum Signum implements Case<Integer>, Supplier<Choice<Integer, Integer>> {

    ZERO(Choice.prepare(pending(), input -> input == 0, 0)),
    POSITIVE(prepare(not(ZERO), input -> input > 0, 1, -1));

    private static final Distinction<Integer, Integer> DISTINCTION = Distinction.of(values());

    private final Choice.Stage<Integer, Integer> backing;

    Signum(final Choice.Stage<Integer, Integer> backing) {
        this.backing = backing;
    }

    public static int apply(final int input) {
        return DISTINCTION.apply(input);
    }

    @Override
    public Choice<Integer, Integer> get() {
        return backing.build(this);
    }
}
