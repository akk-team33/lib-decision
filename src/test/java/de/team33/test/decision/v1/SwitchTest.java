package de.team33.test.decision.v1;

import de.team33.libs.decision.v1.Case;
import de.team33.libs.decision.v1.Switch;
import org.junit.Test;

import java.util.Random;
import java.util.function.Function;

import static de.team33.libs.decision.v1.Cases.initial;
import static de.team33.libs.decision.v1.Cases.not;
import static de.team33.libs.decision.v1.Cases.simple;
import static de.team33.libs.testing.v1.Attempts.tryParallel;
import static de.team33.libs.testing.v1.Attempts.trySerial;
import static org.junit.Assert.assertEquals;

public class SwitchTest {

    private static final Case<Integer, Function<Integer, String>> POSITIVE =
            simple(initial(), input -> input > 0, String::valueOf);
    private static final Function<Integer, String> NEGATIVE_FUNCTION =
            input -> String.valueOf(-input);
    private static final Case<Integer, Function<Integer, String>> NEGATIVE =
            simple(not(POSITIVE), NEGATIVE_FUNCTION);

    private final Random random = new Random();

    @Test
    public final void fixed_vs_intern() {
        trySerial(100, () -> {
            final Input input = new Input(random.nextInt());
            assertEquals(input.toString(), StaticChoices.map(input));
        });
    }

    @Test
    public final void fixed_vs_variable() {
        trySerial(100, () -> {
            final Input input = new Input(random.nextInt());
            assertEquals(StaticChoices.map(input), DynamicChoices.map(input));
        });
    }

    @Test
    public final void checkDualParallel() {
        final Switch<Integer, Function<Integer, String>> subject = Switch.build(POSITIVE, NEGATIVE);
        tryParallel(100, () -> {
            final int input = random.nextInt();
            final String result = subject.apply(input).apply(input);
            assertEquals(String.valueOf(Math.abs(input)), result);
        });
    }
}
