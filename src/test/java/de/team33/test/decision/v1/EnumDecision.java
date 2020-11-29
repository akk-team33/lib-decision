package de.team33.test.decision.v1;

import de.team33.libs.decision.v1.Case;
import de.team33.libs.decision.v1.Cases;
import de.team33.libs.decision.v1.Choice;
import de.team33.libs.decision.v1.Decision;
import de.team33.libs.decision.v1.Event;
import de.team33.test.decision.shared.Input;

import java.util.Optional;
import java.util.function.Predicate;
import java.util.function.Supplier;

import static de.team33.libs.decision.v1.Cases.not;
import static de.team33.test.decision.v1.EnumDecision.Condition.*;

enum EnumDecision implements Case<String>, Supplier<Choice<Input, String>> {

    CASE_0___(Choice.builder(Cases.pending(), A_IS_0)),
    CASE_00__(Choice.builder(CASE_0___, B_IS_0)),
    CASE_000_(Choice.builder(CASE_00__, C_IS_0)),

    CASE_1_0_(Choice.builder(not(CASE_0___), C_IS_0)),
    CASE_101_(Choice.builder(not(CASE_1_0_), B_IS_0)),
    CASE_010_(Choice.builder(not(CASE_00__), C_IS_0)),

    CASE_0000(Choice.builder(CASE_000_, D_IS_0, "0000", "0001")),
    CASE_0100(Choice.builder(CASE_010_, D_IS_0, "0100", "0101")),
    CASE_1_00(Choice.builder(CASE_1_0_, D_IS_0, "1_00", "1_01")),
    CASE_1010(Choice.builder(CASE_101_, D_IS_0, "1010", "1011")),

    CASE_0010(Choice.builder(not(CASE_000_), D_IS_0, "0010", "0011")),
    CASE_0110(Choice.builder(not(CASE_010_), D_IS_0, "0110", "0111")),
    CASE_1110(Choice.builder(not(CASE_101_), D_IS_0, "1110", "1111"));

    private static final Decision<Input, String> DECISION = Decision.build(values());

    private final Choice.Builder<Input, String> backing;

    EnumDecision(final Choice.Builder<Input, String> backing) {
        this.backing = backing;
    }

    static String map(final Input input) {
        return DECISION.apply(input);
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
