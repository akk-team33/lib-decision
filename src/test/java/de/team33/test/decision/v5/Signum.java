package de.team33.test.decision.v5;

import de.team33.libs.decision.v5.Case;
import de.team33.libs.decision.v5.Choice;
import de.team33.libs.decision.v5.Decision;

import java.util.function.Supplier;

import static de.team33.libs.decision.v5.Cases.not;
import static de.team33.libs.decision.v5.Cases.pending;
import static de.team33.libs.decision.v5.Choice.stage;

public enum Signum implements Case<Integer>, Supplier<Choice<Integer, Integer>> {

    ZERO(stage(pending(), input -> input == 0, 0)),
    POSITIVE(stage(not(ZERO), input -> input > 0, 1, -1));

    private static final Decision<Integer, Integer> DECISION = Decision.build(values());

    private final Choice.Stage<Integer, Integer> backing;

    Signum(final Choice.Stage<Integer, Integer> backing) {
        this.backing = backing;
    }

    public static int apply(final int input) {
        return DECISION.apply(input);
    }

    @Override
    public Choice<Integer, Integer> get() {
        return backing.build(this);
    }
}
