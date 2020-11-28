package de.team33.test.decision.v1;

import de.team33.libs.decision.v1.Event;
import de.team33.libs.decision.v1.Decision;
import de.team33.test.decision.shared.Input;
import org.junit.Test;

import java.util.Random;
import java.util.function.Function;

import static de.team33.libs.decision.v1.Event.not;
import static de.team33.libs.testing.v1.Attempts.tryParallel;
import static de.team33.libs.testing.v1.Attempts.trySerial;
import static org.junit.Assert.assertEquals;

public class DecisionTest {

    private static final Event<Integer, Function<Integer, String>> POSITIVE =
            Event.head(input -> input > 0, String::valueOf);
    private static final Event<Integer, Function<Integer, String>> NEGATIVE =
            Event.tail(not(POSITIVE), input -> String.valueOf(-input));

    private final Random random = new Random();

    @Test
    public final void fixed_vs_intern() {
        trySerial(100, () -> {
            final Input input = new Input(random.nextInt());
            assertEquals(input.toString(), StaticDecision.map(input));
        });
    }

    @Test
    public final void fixed_vs_variable() {
        trySerial(100, () -> {
            final Input input = new Input(random.nextInt());
            assertEquals(StaticDecision.map(input), EnumDecision.map(input));
        });
    }

    @Test
    public final void checkDualParallel() {
        final Decision<Integer, Function<Integer, String>> subject = Decision.build(POSITIVE, NEGATIVE);
        tryParallel(100, () -> {
            final int input = random.nextInt();
            final String result = subject.apply(input).apply(input);
            assertEquals(String.valueOf(Math.abs(input)), result);
        });
    }
}
