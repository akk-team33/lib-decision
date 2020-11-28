package de.team33.test.decision.v1;

import de.team33.libs.decision.v1.Event;
import de.team33.libs.decision.v1.Decision;

import java.util.Optional;
import java.util.function.Predicate;

import static de.team33.libs.decision.v1.Event.head;
import static de.team33.libs.decision.v1.Event.mean;
import static de.team33.libs.decision.v1.Event.not;
import static de.team33.libs.decision.v1.Event.tail;

public enum Signum implements Event<Integer, Integer> {

    NEGATIVE(head(input -> input < 0, -1)),
    POSITIVE(mean(not(NEGATIVE), input -> input > 0, 1)),
    ZERO(tail(not(POSITIVE), 0));

    private static final Decision<Integer, Integer> DECISION = Decision.build(values());

    private final Event<Integer, Integer> backing;

    Signum(final Event<Integer, Integer> backing) {
        this.backing = backing;
    }

    public static int apply(final int input) {
        return DECISION.apply(input);
    }

    @Override
    public final Event<Integer, Integer> getPreCondition() {
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
