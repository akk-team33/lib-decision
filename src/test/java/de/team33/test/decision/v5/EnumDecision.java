package de.team33.test.decision.v5;

import de.team33.libs.decision.v5.Case;
import de.team33.libs.decision.v5.Choice;
import de.team33.libs.decision.v5.Distinction;
import de.team33.test.decision.shared.Input;

import java.util.function.Predicate;
import java.util.function.Supplier;

import static de.team33.libs.decision.v5.Cases.*;
import static de.team33.libs.decision.v5.Choice.*;
import static de.team33.test.decision.v5.EnumDecision.Condition.*;

enum EnumDecision implements Case<String>, Supplier<Choice<Input, String>> {

    CASE_0___(stage(pending(), A_IS_0)),
    CASE_00__(stage(CASE_0___, B_IS_0)),
    CASE_000_(stage(CASE_00__, C_IS_0)),

    CASE_1_0_(stage(not(CASE_0___), C_IS_0)),
    CASE_101_(stage(not(CASE_1_0_), B_IS_0)),
    CASE_010_(stage(not(CASE_00__), C_IS_0)),

    CASE_0000(stage(CASE_000_, D_IS_0, "0000", "0001")),
    CASE_0100(stage(CASE_010_, D_IS_0, "0100", "0101")),
    CASE_1_00(stage(CASE_1_0_, D_IS_0, "1_00", "1_01")),
    CASE_1010(stage(CASE_101_, D_IS_0, "1010", "1011")),

    CASE_0010(stage(not(CASE_000_), D_IS_0, "0010", "0011")),
    CASE_0110(stage(not(CASE_010_), D_IS_0, "0110", "0111")),
    CASE_1110(stage(not(CASE_101_), D_IS_0, "1110", "1111"));

    private static final Distinction<Input, String> DISTINCTION = Distinction.of(values());

    private final Choice.Stage<Input, String> backing;

    EnumDecision(final Choice.Stage<Input, String> backing) {
        this.backing = backing;
    }

    static String map(final Input input) {
        return DISTINCTION.apply(input);
    }

    @Override
    public Choice<Input, String> get() {
        return backing.build(this);
    }

    interface Condition extends Predicate<Input> {

        Condition A_IS_0 = input -> 0 == input.a;
        Condition B_IS_0 = input -> 0 == input.b;
        Condition C_IS_0 = input -> 0 == input.c;
        Condition D_IS_0 = input -> 0 == input.d;
    }
}
