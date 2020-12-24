package de.team33.libs.decision.v2;

public interface Case<P> {

    static <P> Case<P> pending() {
        //noinspection unchecked
        return Pending.INSTANCE;
    }

    static <P> Case<P> not(final Case<P> positive) {
        throw new UnsupportedOperationException("not yet implemented");
    }

    String name();

    final class Pending<P> implements Case<P> {

        private static final Pending INSTANCE = new Pending();

        private Pending() {
        }

        @Override
        public final String name() {
            return "PENDING";
        }
    }
}
