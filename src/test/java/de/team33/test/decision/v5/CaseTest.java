package de.team33.test.decision.v5;

import org.junit.Test;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.stream.Stream;

import static de.team33.libs.decision.v5.Cases.not;
import static de.team33.libs.testing.v1.Attempts.tryParallel;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

public class CaseTest {

    private final Random random = new Random();

    @Test
    public final void opposite() {
        Stream.of(EnumDecision.values()).forEach(value -> {
            // The opposite of an opposite should always be identical to the original ...
            assertSame(value, not(not(value)));

            // The opposite of a certain case should always be the same, even if it is determined several times,
            // even in parallel ...
            final List<Object> results = Collections.synchronizedList(new LinkedList<>());
            tryParallel(100, () -> results.add(not(value)));
            assertEquals(100, results.size());
            results.forEach(expected -> results.forEach(result -> assertSame(expected, result)));
        });
    }
}
