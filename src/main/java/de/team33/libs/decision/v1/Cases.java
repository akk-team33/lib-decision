package de.team33.libs.decision.v1;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Predicate;

public final class Cases {

    @SuppressWarnings("rawtypes")
    private static final Map<Case, Opposite> OPPOSITE_CACHE = new ConcurrentHashMap<>(0);
    @SuppressWarnings("rawtypes")
    private static final Predicate NEVER = input -> false;

    /**
     * Returns the opposite of a given {@link Case}.
     * It has the following properties:
     * <ul>
     *     <li>it has the same precondition as the original</li>
     *     <li>it has the inverse condition of the original
     *     (if the original has no condition it makes no sense to get its opposite)</li>
     *     <li>it never leads directly to a result</li>
     * </ul>
     * <p>
     * The opposite of a {@link Case} is not a negation in the strictly boolean sense.
     * In fact, the opposite of a case has the same precondition as the original case.
     * Only the case-specific condition is negated.
     */
    public static <I, R> Case<I, R> not(final Case<I, R> original) {
        return (original instanceof Opposite)
                ? ((Opposite<I, R>) original).original
                : OPPOSITE_CACHE.computeIfAbsent(original, Opposite::new);
    }

    /**
     * Returns the {@link Case} that no decision has yet been made in the context of a decision chain or a decision
     * tree, i.e. the initial {@link Case}. It has the following properties:
     *
     * <ul>
     *     <li>it is itself its own precondition</li>
     *     <li>it is always fulfilled</li>
     *     <li>it never leads directly to a result</li>
     * </ul>
     */
    public static <I, R> Case<I, R> initial() {
        //noinspection unchecked
        return Initial.INSTANCE;
    }

    public static <I, R> Case<I, R> simple(final Case<I, R> preCondition, final Predicate<I> condition) {
        return new Simple<>(preCondition, condition, null);
    }

    public static <I, R> Case<I, R> simple(final Case<I, R> preCondition, final R result) {
        return new Simple<>(preCondition, null, result);
    }

    public static <I, R> Case<I, R> simple(final Case<I, R> preCondition,
                                           final Predicate<I> condition,
                                           final R result) {
        return new Simple<>(preCondition, condition, result);
    }

    private static final class Opposite<I, R> implements Case<I, R> {

        private final Case<I, R> original;
        private final Predicate<I> predicate;

        private Opposite(final Case<I, R> original) {
            this.original = original;
            predicate = original.getCondition()
                                .map(Opposite::inverse)
                                .orElseGet(Opposite::never);
        }

        private static <I> Predicate<I> inverse(final Predicate<I> predicate) {
            return input -> !predicate.test(input);
        }

        @SuppressWarnings("unchecked")
        private static <I> Predicate<I> never() {
            return NEVER;
        }

        @Override
        public final Case<I, R> getPreCondition() {
            return original.getPreCondition();
        }

        @Override
        public final Optional<Predicate<I>> getCondition() {
            return Optional.of(predicate);
        }

        @Override
        public final Optional<R> getResult() {
            return Optional.empty();
        }

        @Override
        public final String toString() {
            return "not(" + original + ")";
        }
    }

    private static final class Initial<I, R> implements Case<I, R> {

        @SuppressWarnings("rawtypes")
        private static final Case INSTANCE = new Initial();

        private Initial() {
        }

        @Override
        public final Case<I, R> getPreCondition() {
            return this;
        }

        @Override
        public final Optional<Predicate<I>> getCondition() {
            return Optional.of(input -> true);
        }

        @Override
        public final Optional<R> getResult() {
            return Optional.empty();
        }

        @Override
        public final String toString() {
            return "NONE";
        }
    }

    private static final class Simple<I, R> implements Case<I, R> {

        private static final AtomicInteger INDEX = new AtomicInteger(0);

        private final Case<I, R> preCondition;
        private final Predicate<I> condition;
        private final R result;
        private final int index = INDEX.incrementAndGet();

        private Simple(final Case<I, R> preCondition, final Predicate<I> condition, final R result) {
            this.preCondition = preCondition;
            this.condition = condition;
            this.result = result;
        }

        @Override
        public final Case<I, R> getPreCondition() {
            return preCondition;
        }

        @Override
        public final Optional<Predicate<I>> getCondition() {
            return Optional.ofNullable(condition);
        }

        @Override
        public final Optional<R> getResult() {
            return Optional.ofNullable(result);
        }

        @Override
        public final String toString() {
            return getClass().getSimpleName() + "#" + index;
        }
    }
}
