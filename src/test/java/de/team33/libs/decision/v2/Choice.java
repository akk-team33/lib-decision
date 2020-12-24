package de.team33.libs.decision.v2;

import java.util.function.Predicate;

public class Choice<P> {

    final Case<P> preCondition;
    final Predicate<P> condition;
    final Case<P> positive;
    final Case<P> negative;

    private Choice(final Case<P> preCondition, final Predicate<P> condition,
                   final Case<P> positive, final Case<P> negative) {
        this.preCondition = preCondition;
        this.condition = condition;
        this.positive = positive;
        this.negative = negative;
    }

    public static <P> Stage<P> stage(final Case<P> preCondition, final Predicate<P> condition) {
        return new Stage<>(preCondition, condition);
    }

    public static final class Stage<P> {

        private final Case<P> preCondition;
        private final Predicate<P> condition;

        private Stage(final Case<P> preCondition, final Predicate<P> condition) {
            this.preCondition = preCondition;
            this.condition = condition;
        }

        public final Choice<P> build(final Case<P> positive) {
            return new Choice<>(preCondition, condition, positive, Case.not(positive));
        }
    }
}
