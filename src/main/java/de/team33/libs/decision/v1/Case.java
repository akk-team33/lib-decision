package de.team33.libs.decision.v1;

import java.util.Optional;

public interface Case<R> {

    String name();

    default Optional<R> getResult() {
        return Optional.empty();
    }
}
