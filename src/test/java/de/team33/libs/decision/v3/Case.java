package de.team33.libs.decision.v3;

import java.util.function.Predicate;

public interface Case<P, R> {

    static <P, R> Case<P, R> of(final Predicate<P> condition, final Case<P, R> positive, final Case<P, R> negative) {
        throw new UnsupportedOperationException("not yet implemented");
    }

    static <P, R> Case<P, R> of(final Predicate<P> condition, final R positive, final Case<P, R> negative) {
        return of(condition, Case.<P, R>of(positive), negative);
    }

    static <P, R> Case<P, R> of(final Predicate<P> condition, final Case<P, R> positive, final R negative) {
        return of(condition, positive, Case.<P, R>of(negative));
    }

    static <P, R> Case<P, R> of(final Predicate<P> condition, R positive, final R negative) {
        return of(condition, Case.<P, R>of(positive), Case.<P, R>of(negative));
    }

    static <P, R> Case<P, R> of(final R result) {
        throw new UnsupportedOperationException("not yet implemented");
    }
}
