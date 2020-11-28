package de.team33.test.decision.v4;

import de.team33.libs.decision.v4.Case;
import de.team33.libs.decision.v4.Decision;
import de.team33.libs.decision.v4.Stage;
import de.team33.test.decision.shared.Input;

import java.util.function.Supplier;

import static de.team33.libs.decision.v4.Cases.not;

enum EnumDecision implements Case, Supplier<Stage<Input, String>> {

    CASE_0___(Stage.of(Case.PENDING, input -> 0 == input.a)),
    CASE_00__(Stage.of(CASE_0___, input -> 0 == input.b)),
    CASE_000_(Stage.of(CASE_00__, input -> 0 == input.c)),

    CASE_1_0_(Stage.of(not(CASE_0___), input -> 0 == input.c)),
    CASE_101_(Stage.of(not(CASE_1_0_), input -> 0 == input.b)),
    CASE_010_(Stage.of(not(CASE_00__), input -> 0 == input.c)),

    CASE_0000(Stage.of(CASE_000_, input -> 0 == input.d, "0000", "0001")),
    CASE_0100(Stage.of(CASE_010_, input -> 0 == input.d, "0100", "0101")),
    CASE_1_00(Stage.of(CASE_1_0_, input -> 0 == input.d, "1_00", "1_01")),
    CASE_1010(Stage.of(CASE_101_, input -> 0 == input.d, "1010", "1011")),

    CASE_0010(Stage.of(not(CASE_000_), input -> 0 == input.d, "0010")),
    CASE_0011(Stage.of(not(CASE_0010), "0011")),
    CASE_0110(Stage.of(not(CASE_010_), input -> 0 == input.d, "0110")),
    CASE_0111(Stage.of(not(CASE_0110), "0111")),
    CASE_1110(Stage.of(not(CASE_101_), input -> 0 == input.d, "1110")),
    CASE_1111(Stage.of(not(CASE_1110), "1111"));

    private static final Decision<Input, String> CHOICES = Decision.build(values());

    private final Stage<Input, String> stage;

    EnumDecision(final Stage<Input, String> stage) {
        this.stage = stage;
    }

    static String map(final Input input) {
        return CHOICES.apply(input);
    }

    @Override
    public final Stage<Input, String> get() {
        return stage.setPositive(this);
    }
}
