package de.team33.libs.decision.v1;

import java.util.function.Predicate;

public interface Choice<I, R> {

    static <I, R> Builder<I, R> builder(Case<R> preCondition, Predicate<I> condition) {
        throw new UnsupportedOperationException("not yet implemented");
    }

    static <I, R> Builder<I, R> builder(Case<R> preCondition, Predicate<I> condition, R positive) {
        return builder(preCondition, condition).setPositiveResult(positive);
    }

    static <I, R> Builder<I, R> builder(Case<R> preCondition, Predicate<I> condition, R positive, R negative) {
        return builder(preCondition, condition).setPositiveResult(positive)
                                               .setNegativeResult(negative);
    }

    Case<R> getPreCondition();

    Predicate<I> getCondition();

    Case<R> getPositive();

    Case<R> getNegative();

    class Builder<I, R> {

        public Builder<I, R> setPositiveResult(final R positiveResult) {
            throw new UnsupportedOperationException("not yet implemented");
        }

        public Builder<I, R> setNegativeResult(final R negativeResult) {
            throw new UnsupportedOperationException("not yet implemented");
        }

        public Builder<I, R> setPositiveCase(final Case<R> positiveCase) {
            throw new UnsupportedOperationException("not yet implemented");
        }

        public Builder<I, R> setNegativeCase(final Case<R> negativeCase) {
            throw new UnsupportedOperationException("not yet implemented");
        }

        public Choice<I, R> build(final Case<R> positive) {
            return setPositiveCase(positive)
                    .setNegativeCase(Cases.not(positive))
                    .build();
        }

        public Choice<I, R> build() {
            throw new UnsupportedOperationException("not yet implemented");
        }
    }
}
