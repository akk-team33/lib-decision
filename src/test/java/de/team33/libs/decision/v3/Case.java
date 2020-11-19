package de.team33.libs.decision.v3;

import java.util.Optional;

public interface Case<I, R> {

    @SuppressWarnings({"ClassReferencesSubclass", "unchecked"})
    static <I, R> Optional<Final<I, R>> asFinal(final Case<I, R> subject) {
        return Optional.of(subject)
                       .filter(Final.class::isInstance)
                       .map(Final.class::cast);
    }

    @SuppressWarnings({"ClassReferencesSubclass", "unchecked"})
    static <I, R> Optional<Mean<I, R>> asMean(final Case<I, R> subject) {
        return Optional.of(subject)
                       .filter(Mean.class::isInstance)
                       .map(Mean.class::cast);
    }

    interface Final<I, R> extends Case<I, R> {
        R getResult();
    }

    interface Mean<I, R> extends Case<I, R> {

        Choice<I, R> getChoice();
    }
}
