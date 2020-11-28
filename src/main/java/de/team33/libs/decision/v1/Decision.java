package de.team33.libs.decision.v1;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Stream;

import static de.team33.libs.decision.v1.Event.pending;
import static de.team33.libs.decision.v1.Event.not;
import static java.util.Collections.unmodifiableMap;

public final class Decision<I, R> implements Function<I, R> {

    private final Map<Event<I, R>, Event<I, R>> postConditions;

    private Decision(final Builder<I, R> builder) {
        postConditions = unmodifiableMap(builder.postConditions);
    }

    @SafeVarargs
    public static <I, R> Decision<I, R> build(final Event<I, R>... events) {
        return Stream.of(events)
                     .collect(() -> new Builder<I, R>(pending()), Builder::add, Builder::addAll)
                     .build();
    }

    @Override
    public final R apply(final I input) {
        return apply(pending(), input);
    }

    private R apply(final Event<I, R> base, final I input) {
        return base.getResult().orElseGet(() -> applyPost(base, input));
    }

    private R applyPost(final Event<I, R> base, final I input) {
        return apply(nextCase(postConditions.get(base), input), input);
    }

    private static <I, R> Event<I, R> nextCase(final Event<I, R> subject, final I input) {
        return subject.getCondition()
                      .map(condition -> condition.test(input) ? subject : not(subject))
                      .orElse(subject);
    }

    private static final class Builder<I, R> {

        private final Map<Event<I, R>, Event<I, R>> postConditions = new HashMap<>(0);
        private final Set<Object> defined = new HashSet<>(0);
        private final Set<Object> used = new HashSet<>(0);

        private Builder(final Event<Object, Object> none) {
            used.add(none);
        }

        private void add(final Event<I, R> next) {
            final Event<I, R> pre = next.getPreCondition();
            postConditions.put(pre, next);
            defined.add(pre);
            if (next.getResult().isPresent()) {
                defined.add(next);
            }
            used.add(next);
            if (next.getCondition().isPresent()) {
                used.add(not(next));
            }
        }

        private void addAll(final Builder<I, R> other) {
            throw new UnsupportedOperationException("shouldn't be necessary here");
        }

        public Decision<I, R> build() {
            final Set<Object> undefined = new HashSet<>(used);
            undefined.removeAll(defined);
            if (!undefined.isEmpty()) {
                throw new UndefinedException(undefined);
            }

            final Set<Object> unused = new HashSet<>(defined);
            unused.removeAll(used);
            if (!unused.isEmpty()) {
                throw new UnusedException(unused);
            }

            return new Decision<I, R>(this);
        }
    }
}
