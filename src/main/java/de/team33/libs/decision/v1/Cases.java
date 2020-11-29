package de.team33.libs.decision.v1;

public class Cases {

    private static final Case PENDING = () -> "PENDING";

    public static <R> Case<R> pending() {
        //noinspection unchecked
        return PENDING;
    }

    public static <R> Case<R> not(final Case<R> original) {
        throw new UnsupportedOperationException("not yet implemented");
    }
}
