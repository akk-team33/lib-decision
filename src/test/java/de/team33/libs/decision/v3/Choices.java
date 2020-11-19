package de.team33.libs.decision.v3;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Stream;

public class Choices<I, R> implements Function<I, R> {

    private final Node<I, R> root = new Node<>();

    @SafeVarargs
    public static <I, R> Choices<I, R> build(Supplier<Choice<I, R>>... choices) {
        return Stream.of(choices)
                     .collect(() -> new Builder<I, R>(), Builder::add, Builder::addAll)
                     .build();
    }

    @Override
    public final R apply(final I input) {
        throw new UnsupportedOperationException("not yet implemented");
    }

    private R apply(final Case<I, R> subject, final I input) {
        return Case.asFinal(subject)
                   .map(Case.Final::getResult)
                   .orElseGet(() -> applyChoice(subject, input));
    }

    private R applyChoice(final Case<I, R> subject, final I input) {
        return Case.asMean(subject)
                   .map(Case.Mean::getChoice)
                   .map(choice -> apply(choice, input))
                   .orElseThrow(IllegalStateException::new);
    }

    private R apply(final Choice<I, R> choice, final I input) {
        final Case<I, R> next = choice.getCondition().test(input) ? choice.getPositive() : choice.getNegative();
        return apply(next, input);
    }

    private static class Builder<I, R> {

        private final Map<Case<I, R>, Choice<I, R>> backing = new HashMap<>(0);
        private final Set<Object> defined = new HashSet<>(0);
        private final Set<Object> used = new HashSet<>(0);

        private void add(final Supplier<Choice<I, R>> choice) {
            backing.put(choice.get().getPreCondition(), choice.get());
            defined.add(choice.getPreCondition());
            used.add(choice);
            if (choice.getResult().isPresent())
                defined.add(choice);
            throw new UnsupportedOperationException("not yet implemented");
        }

        private void addAll(final Builder<I, R> other) {
            throw new UnsupportedOperationException("not yet implemented");
        }

        private Choices<I, R> build() {
            throw new UnsupportedOperationException("not yet implemented");
        }
    }

    private static class Node<I, R> {

        private final de.team33.libs.decision.v2.Choice<I, R> choice;
        private final Node<I, R> positive;
        private final Node<I, R> negative;

        private Node() {
            choice = null;
            positive = null;
            negative = null;
        }

        private Node<I, R> get(final Choice<I, R> choice) {
            throw new UnsupportedOperationException("not yet implemented");
        }
    }
}
