package de.team33.libs.decision.v5;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiFunction;

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
    private static final Map<Case, Definite> DEFINITES = new ConcurrentHashMap<>(0);

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

    /**
     * Creates a {@link Case} that is associated with a final result from a {@link Case} that is not.
     */
    public static <R> Case<R> definite(final Case<R> original, final R result) {
        return original.result()
                       .map(present -> checked(present, result, original))
                       .orElseGet(() -> mapped(original, result));
    }

    private static <R> Case<R> mapped(final Case<R> original, final R result) {
        return compute(original, remapping(result));
    }

    private static <R> BiFunction<Case<R>, Definite<R>, Definite<R>> remapping(final R result) {
        return (original, found) -> {
            if (null == found)
                return new Definite<>(original, result);
            else {
                return checked(found.result().orElse(null), result, found);
            }
        };
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    private static <R> Definite<R> compute(final Case original, final BiFunction remapping) {
        return DEFINITES.compute(original, remapping);
    }

    private static <R, C extends Case<R>> C checked(final R present, final R desired, final C subject) {
        if (desired.equals(present)) {
            return subject;
        } else {
            throw new IllegalArgumentException(
                    format(ALREADY_DEFINED, subject, desired, present));
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

    private static final class Definite<R> implements Case<R> {

        private final Case<R> original;
        private final R result;

        private Definite(final Case<R> original, final R result) {
            this.original = original;
            this.result = result;
        }

        @Override
        public final Optional<R> result() {
            return Optional.of(result);
        }

        @Override
        public final String name() {
            return original.name();
        }

        @Override
        public final String toString() {
            return name();
        }
    }
}
