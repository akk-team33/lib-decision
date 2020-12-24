package de.team33.test.decision.v3;

import de.team33.libs.decision.v3.Case;
import de.team33.libs.decision.v3.Distinction;

public enum Signum implements Case<Integer, Integer> {

    NON_ZERO(Case.of(input -> input > 0, 1, -1)),
    PENDING(Case.of(input -> input == 0, 0, NON_ZERO));

    private static final Distinction<Integer, Integer> DISTINCTION = Distinction.of(values());

    private final Case<Integer, Integer> backing;

    Signum(final Case<Integer, Integer> backing) {
        this.backing = backing;
    }
}
