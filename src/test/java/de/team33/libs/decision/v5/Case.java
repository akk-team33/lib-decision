package de.team33.libs.decision.v5;

import java.util.Optional;

public interface Case<R> {

    String name();

    default Optional<R> result() {
        return Optional.empty();
    }
}
