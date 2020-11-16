package de.team33.test.decision.v1;

import de.team33.libs.decision.v1.Case;
import de.team33.libs.decision.v1.Choices;

import java.util.Optional;
import java.util.function.Predicate;

import static de.team33.libs.decision.v1.Case.head;
import static de.team33.libs.decision.v1.Case.mean;
import static de.team33.libs.decision.v1.Case.not;
import static de.team33.libs.decision.v1.Case.tail;

enum DynamicChoices implements Case<Input, String> {

    CASE_0___(head(input -> 0 == input.a)),
    CASE_00__(mean(CASE_0___, input -> 0 == input.b)),
    CASE_000_(mean(CASE_00__, input -> 0 == input.c)),

    CASE_1_0_(mean(not(CASE_0___), input -> 0 == input.c)),
    CASE_101_(mean(not(CASE_1_0_), input -> 0 == input.b)),
    CASE_010_(mean(not(CASE_00__), input -> 0 == input.c)),

    CASE_0000(mean(CASE_000_, input -> 0 == input.d, "0000")),
    CASE_0001(tail(not(CASE_0000), "0001")),
    CASE_0100(mean(CASE_010_, input -> 0 == input.d, "0100")),
    CASE_0101(tail(not(CASE_0100), "0101")),
    CASE_1_00(mean(CASE_1_0_, input -> 0 == input.d, "1_00")),
    CASE_1_01(tail(not(CASE_1_00), "1_01")),
    CASE_1010(mean(CASE_101_, input -> 0 == input.d, "1010")),
    CASE_1011(tail(not(CASE_1010), "1011")),

    CASE_0010(mean(not(CASE_000_), input -> 0 == input.d, "0010")),
    CASE_0011(tail(not(CASE_0010), "0011")),
    CASE_0110(mean(not(CASE_010_), input -> 0 == input.d, "0110")),
    CASE_0111(tail(not(CASE_0110), "0111")),
    CASE_1110(mean(not(CASE_101_), input -> 0 == input.d, "1110")),
    CASE_1111(tail(not(CASE_1110), "1111"));

    private static final Choices<Input, String> CHOICES = Choices.build(values());

    private final Case<Input, String> backing;

    DynamicChoices(final Case<Input, String> backing) {
        this.backing = backing;
    }

    static String map(final Input input) {
        return CHOICES.apply(input);
    }

    @Override
    public final Case<Input, String> getPreCondition() {
        return backing.getPreCondition();
    }

    @Override
    public Optional<Predicate<Input>> getCondition() {
        return backing.getCondition();
    }

    @Override
    public Optional<String> getResult() {
        return backing.getResult();
    }
}
