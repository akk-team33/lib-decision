package de.team33.test.decision.v5;

import de.team33.libs.testing.v1.Attempts;
import org.junit.Test;

import java.util.Random;

import static org.junit.Assert.*;

public class SignumTest {

    private final Random random = new Random();

    @Test
    public final void positive() {
        Attempts.trySerial(100, () -> {
            assertEquals(1, Signum.apply(1 + random.nextInt(0x10000)));
        });
    }

    @Test
    public final void negative() {
        Attempts.trySerial(100, () -> {
            assertEquals(-1, Signum.apply(-1 - random.nextInt(0x10000)));
        });
    }

    @Test
    public final void zero() {
        Attempts.trySerial(100, () -> {
            assertEquals(0, Signum.apply(0));
        });
    }
}
