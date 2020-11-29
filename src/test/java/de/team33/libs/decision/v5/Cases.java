package de.team33.libs.decision.v5;

public class Cases {

    @SuppressWarnings("rawtypes")
    private static final Case PENDING = () -> "PENDING";

    public static <R> Case<R> pending() {
        //noinspection unchecked
        return PENDING;
    }

    public static <R> Case<R> not(final Case<R> original) {
        return Opposite.of(original);
    }

    public static <R> Case<R> definite(final Case<R> original, final R result) {
        return Definite.of(original, result);
    }
}
