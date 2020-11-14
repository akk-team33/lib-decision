package de.team33.libs.decision.v1;

import java.util.Set;

public class UnusedException extends IllegalStateException {

    public UnusedException(final Set<Object> unused) {
        super("Some cases are defined but not used: " + unused);
    }
}
