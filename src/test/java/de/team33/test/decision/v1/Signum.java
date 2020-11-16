package de.team33.test.decision.v1;

import de.team33.libs.decision.v1.Case;
import de.team33.libs.decision.v1.Choices;

import java.util.Optional;
import java.util.function.Predicate;

import static de.team33.libs.decision.v1.Case.head;
import static de.team33.libs.decision.v1.Case.mean;
import static de.team33.libs.decision.v1.Case.not;
import static de.team33.libs.decision.v1.Case.tail;

public enum Signum implements Case<Integer, Integer> {

    NEGATIVE(head(input -> input < 0, -1)),
    POSITIVE(mean(not(NEGATIVE), input -> input > 0, 1)),
    ZERO(tail(not(POSITIVE), 0));

    private static final Choices<Integer, Integer> CHOICES = Choices.build(values());

    private final Case<Integer, Integer> backing;

    Signum(final Case<Integer, Integer> backing) {
        this.backing = backing;
    }

    public static int apply(final int input) {
        return CHOICES.apply(input);
    }

    @Override
    public final Case<Integer, Integer> getPreCondition() {
        return backing.getPreCondition();
    }

    @Override
    public final Optional<Predicate<Integer>> getCondition() {
        return backing.getCondition();
    }

    @Override
    public final Optional<Integer> getResult() {
        return backing.getResult();
    }
}
