package de.team33.test.decision.v1;

import de.team33.libs.decision.v1.Case;
import de.team33.libs.decision.v1.Cases;
import de.team33.libs.decision.v1.Choices;

import java.util.Optional;
import java.util.function.Predicate;

enum DynamicChoices implements Case<Input, String> {

    CASE_0___(Cases.initial(), input -> 0 == input.a),
    CASE_00__(CASE_0___, input -> 0 == input.b),
    CASE_000_(CASE_00__, input -> 0 == input.c),

    CASE_1_0_(Cases.not(CASE_0___), input -> 0 == input.c),
    CASE_101_(Cases.not(CASE_1_0_), input -> 0 == input.b),
    CASE_010_(Cases.not(CASE_00__), input -> 0 == input.c),

    CASE_0000(CASE_000_, input -> 0 == input.d, "0000"),
    CASE_0001(Cases.not(CASE_0000), "0001"),
    CASE_0100(CASE_010_, input -> 0 == input.d, "0100"),
    CASE_0101(Cases.not(CASE_0100), "0101"),
    CASE_1_00(CASE_1_0_, input -> 0 == input.d, "1_00"),
    CASE_1_01(Cases.not(CASE_1_00), "1_01"),
    CASE_1010(CASE_101_, input -> 0 == input.d, "1010"),
    CASE_1011(Cases.not(CASE_1010), "1011"),

    CASE_0010(Cases.not(CASE_000_), input -> 0 == input.d, "0010"),
    CASE_0011(Cases.not(CASE_0010), "0011"),
    CASE_0110(Cases.not(CASE_010_), input -> 0 == input.d, "0110"),
    CASE_0111(Cases.not(CASE_0110), "0111"),
    CASE_1110(Cases.not(CASE_101_), input -> 0 == input.d, "1110"),
    CASE_1111(Cases.not(CASE_1110), "1111");

    private static final Choices<Input, String> CHOICES = Choices.build(values());

    private final Case<Input, String> preCondition;
    private final Predicate<Input> predicate;
    private final String result;

    DynamicChoices(final Case<Input, String> preCondition, final Predicate<Input> predicate) {
        this(preCondition, predicate, null);
    }

    DynamicChoices(final Case<Input, String> preCondition, final String result) {
        this(preCondition, null, result);
    }

    @SuppressWarnings("AssignmentToNull")
    DynamicChoices(final Case<Input, String> preCondition,
                   final Predicate<Input> predicate,
                   final String result) {
        this.preCondition = preCondition;
        this.predicate = predicate;
        this.result = result;
    }

    static String map(final Input input) {
        return CHOICES.apply(input);
    }

    public final Case<Input, String> getPreCondition() {
        return preCondition;
    }

    @Override
    public Optional<Predicate<Input>> getCondition() {
        return Optional.ofNullable(predicate);
    }

    @Override
    public Optional<String> getResult() {
        return Optional.ofNullable(result);
    }
}
