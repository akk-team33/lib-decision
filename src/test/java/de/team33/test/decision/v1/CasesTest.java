package de.team33.test.decision.v1;

import de.team33.libs.decision.v5.Case;
import de.team33.libs.decision.v5.Cases;
import org.junit.Test;

import java.util.Date;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Stream;

import static de.team33.libs.decision.v5.Cases.not;
import static de.team33.libs.testing.v1.Attempts.tryParallel;
import static java.util.Collections.singleton;
import static java.util.Collections.synchronizedList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

public class CasesTest {

    @Test
    public final void pending() {
        final Set<Case<?>> cases = new HashSet<>(0);
        cases.add(Cases.<String>pending());
        cases.add(Cases.<Integer>pending());
        cases.add(Cases.<Date>pending());
        cases.add(Cases.<Function<String, Integer>>pending());
        cases.add(Cases.<Map<String, List<String>>>pending());
        assertEquals(singleton(Cases.pending()), cases);
        assertEquals("PENDING", Cases.pending().name());
    }

    @Test
    public final void opposite() {
        Stream.of(MyCase.values()).forEach(value -> {
            // The opposite of an opposite should always be identical to the original ...
            assertSame(value, not(not(value)));

            // The opposite of a certain case should always be the same, even if it is determined several times,
            // even in parallel ...
            final List<Object> results = synchronizedList(new LinkedList<>());
            tryParallel(100, () -> results.add(not(value)));
            assertEquals(100, results.size());
            final Case<String> expected = not(value);
            results.forEach(result -> assertSame(expected, result));
        });
    }

    private enum MyCase implements Case<String> {
        CASE_0, CASE_1, CASE_2, CASE_3, CASE_4, CASE_5, CASE_6, CASE_7, CASE_8, CASE_9, CASE_10
    }
}
