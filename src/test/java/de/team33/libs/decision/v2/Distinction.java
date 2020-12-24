package de.team33.libs.decision.v2;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Stream;

public class Distinction<P> implements Function<P, Case<P>> {

    private final Map<Case<P>, Choice<P>> choices;

    private Distinction(final Map<Case<P>, Choice<P>> choices) {
        this.choices = Collections.unmodifiableMap(new HashMap<>(choices));
    }

    public static <P> Distinction<P> of(final Supplier<Choice<P>> ... choices) {
        return of(Stream.of(choices)
                        .map(Supplier::get));
    }

    public static <P> Distinction<P> of(final Stream<Choice<P>> choices) {
        return choices.collect(() -> new Builder<P>(Case.pending()), Builder::add, Builder::addAll)
                      .build();
    }

    @Override
    public final Case<P> apply(final P parameter) {
        return apply(Case.pending(), parameter);
    }

    private Case<P> apply(final Case<P> origin, final P parameter) {
        return Optional.ofNullable(choices.get(origin))
                       .map(choice -> choice.condition.test(parameter) ? choice.positive : choice.negative)
                       .map(next -> apply(next, parameter))
                       .orElse(origin);
    }

    private static class Builder<P> {

        private final Map<Case<P>, Choice<P>> choices = new HashMap<>();

        Builder(final Case<P> pending) {
            throw new UnsupportedOperationException("not yet implemented");
        }

        private Distinction<P> build() {
            return new Distinction<>(choices);
        }

        private void add(final Choice<P> choice) {
            choices.put(choice.preCondition, choice);
        }

        private void addAll(final Builder<P> other) {
            throw new UnsupportedOperationException("not expected to be used here");
        }
    }
}
