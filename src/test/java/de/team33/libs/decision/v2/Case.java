package de.team33.libs.decision.v2;

import java.util.Optional;

public interface Case<I, R> {

    Optional<R> getResult();

    Optional<Choice<I, R>> getChoice();
}
