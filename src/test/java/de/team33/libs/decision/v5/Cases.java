package de.team33.libs.decision.v5;

/**
 * A tool to deal with {@link Case}s.
 */
public final class Cases {

    @SuppressWarnings("rawtypes")
    private static final Case PENDING = () -> "PENDING";

    private Cases() {
    }

    /**
     * Returns the {@link Case} that no distinction has yet been made.
     * This is always at the beginning of a case distinction.
     */
    public static <R> Case<R> pending() {
        //noinspection unchecked
        return PENDING;
    }

    /**
     * Returns the opposite of a {@link Case}.
     * <p>
     * A {@link Case} leads to a decision, a decision leads to a {@link Case} or its opposite.
     * <p>
     * The opposite of a {@link Case} is not a negation in the strictly Boolean sense. Indeed, a {@link Case}
     * serves as precondition and leads to a decision, which in turn leads to either a {@link Case} or its opposite.
     * A {@link Case} and its opposite therefore have the same precondition, but behave in the opposite way with regard
     * to the immediately preceding decision.
     */
    public static <R> Case<R> not(final Case<R> original) {
        return Opposite.of(original);
    }

    static <R> Case<R> definite(final Case<R> original, final R result) {
        return Definite.of(original, result);
    }
}
