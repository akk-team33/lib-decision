package de.team33.test.decision.v5;

import de.team33.libs.decision.v5.Case;
import de.team33.test.decision.shared.Input;
import org.junit.Test;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.stream.Stream;

import static de.team33.libs.decision.v5.Case.not;
import static de.team33.libs.testing.v1.Attempts.tryParallel;
import static de.team33.libs.testing.v1.Attempts.trySerial;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

public class CaseTest {

    private final Random random = new Random();

    @Test
    public final void opposite() {
        Stream.of(EnumDecision.values()).forEach(choice -> {
            // The opposite of an opposite should always be identical to the original ...
            assertSame(choice, not(not(choice)));

            // The opposite of a certain case should always be the same, even if it is determined several times,
            // even in parallel ...
            final List<Object> results = Collections.synchronizedList(new LinkedList<>());
            tryParallel(100, () -> results.add(not(choice)));
            assertEquals(100, results.size());
            results.forEach(expected -> results.forEach(result -> assertSame(expected, result)));

            // If a case matches a parameter, the opposite should not match and vice versa ...
            trySerial(100, () -> {
                final Input input = new Input(random.nextInt());
                assertEquals(!isMatching(choice, input), isMatching(not(choice), input));
            });
        });
    }

    private static boolean isMatching(final Case<Input, String> choice, final Input input) {
        return choice.getCondition()
                     .map(condition -> condition.test(input))
                     .orElse(true);
    }
}
