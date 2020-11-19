package de.team33.libs.decision.v2;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Stream;

public class Choices<I, R> implements Function<I, R> {

    private final Node<I, R> root = new Node<>();

    @SafeVarargs
    public static <I, R> Choices<I, R> build(final Choice<I, R>... choices) {
        return Stream.of(choices)
                     .collect(() -> new Builder<I, R>(), Builder::add, Builder::addAll)
                     .build();
    }

    @Override
    public final R apply(final I input) {
        throw new UnsupportedOperationException("not yet implemented");
    }

    private R apply(final Choice<I, R> choice, final I input) {
        return choice.getResult()
                     .orElseGet(() -> applyNext(choice, input));
    }

    private R applyNext(final Choice<I, R> choice, final I input) {
        final Node<I, R> node = root.get(choice);
        final Node<I, R> next = choice.getNextCondition()
                                      .orElseThrow(IllegalStateException::new)
                                      .test(input) ? node.positive : node.negative;
        return apply(next.choice, input);
    }

    private static class Consequence<I, R> {
    }

    private static class Builder<I, R> {

        private final Map<Choice<I, R>, Map<Case, Choice<I, R>>> backing = new HashMap<>(0);
        private final Set<Object> defined = new HashSet<>(0);
        private final Set<Object> used = new HashSet<>(0);

        private void add(final Choice<I, R> choice) {
            backing.computeIfAbsent(choice.getPreCondition(), ignored -> new HashMap<>(2))
                   .put(choice.getCase(), choice);
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

        private final Choice<I, R> choice;
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
