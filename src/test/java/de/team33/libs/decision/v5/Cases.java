package de.team33.libs.decision.v5;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import static java.lang.String.format;


/**
 * A tool to deal with {@link Case}s.
 */
public final class Cases {

    @SuppressWarnings("rawtypes")
    private static final Case PENDING = () -> "PENDING";
    private static final String ALREADY_DEFINED = "Case <%s> ...%n" +
            "... is mentioned to be associated with result <%s>%n" +
            "... but was already associated with result <%s>";
    @SuppressWarnings("rawtypes")
    private static final Map<Case, Opposite> OPPOSITES = new ConcurrentHashMap<>(0);
    @SuppressWarnings("rawtypes")
    private static final Map<Case, Object> VALUES = new ConcurrentHashMap<>(0);

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
     * The opposite of a {@link Case} is not a negation in the strictly Boolean sense. Indeed, another {@link Case}
     * serves as precondition and leads to a decision, which in turn leads to either a {@link Case} or its opposite.
     * A {@link Case} and its opposite therefore have the same precondition, but behave in the opposite way with regard
     * to the immediately preceding decision.
     */
    public static <R> Case<R> not(final Case<R> original) {
        //noinspection unchecked
        return (original instanceof Opposite)
                ? ((Opposite<R>) original).original
                : OPPOSITES.computeIfAbsent(original, Opposite::new);
    }

    static <R> Optional<R> valueOf(final Case<R> rCase) {
        //noinspection unchecked
        return Optional.ofNullable((R) VALUES.get(rCase));
    }

    static <R> Case<R> setValueOf(final Case<R> rCase, final R value) {
        final Object already = VALUES.putIfAbsent(rCase, value);
        if ((null == already) || already.equals(value)) {
            return rCase;
        } else {
            throw new IllegalStateException(format(ALREADY_DEFINED, rCase, value, already));
        }
    }

    private static final class Opposite<R> implements Case<R> {

        private final Case<R> original;

        private Opposite(final Case<R> original) {
            this.original = original;
        }

        @Override
        public final String name() {
            return "~" + original.name();
        }

        @Override
        public final String toString() {
            return name();
        }
    }
}
