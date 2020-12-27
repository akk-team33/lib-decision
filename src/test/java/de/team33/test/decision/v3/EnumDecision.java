package de.team33.test.decision.v3;

import de.team33.test.decision.shared.Input;

import java.util.function.Function;
import java.util.function.Predicate;

import static de.team33.libs.decision.v3.Choice.choice;
import static de.team33.test.decision.v3.EnumDecision.Condition.*;

public enum EnumDecision implements Function<Input, String> {

    CASE_000_(choice(D_IS_0, "0000", "0001")),
    CASE_001_(choice(D_IS_0, "0010", "0011")),
    CASE_010_(choice(D_IS_0, "0100", "0101")),
    CASE_011_(choice(D_IS_0, "0110", "0111")),
    CASE_101_(choice(D_IS_0, "1010", "1011")),
    CASE_1_0_(choice(D_IS_0, "1_00", "1_01")),
    CASE_111_(choice(D_IS_0, "1110", "1111")),

    CASE_00__(choice(C_IS_0, CASE_000_, CASE_001_)),
    CASE_01__(choice(C_IS_0, CASE_010_, CASE_011_)),
    CASE_1_1_(choice(B_IS_0, CASE_101_, CASE_111_)),
    CASE_0___(choice(B_IS_0, CASE_00__, CASE_01__)),
    CASE_1___(choice(C_IS_0, CASE_1_0_, CASE_1_1_)),
    CASE_____(choice(A_IS_0, CASE_0___, CASE_1___));

    private final Function<Input, String> backing;

    EnumDecision(final Function<Input, String> backing) {
        this.backing = backing;
    }

    public static String map(final Input input) {
        return CASE_____.apply(input);
    }

    @Override
    public String apply(final Input input) {
        return backing.apply(input);
    }

    interface Condition extends Predicate<Input> {

        Condition A_IS_0 = input -> 0 == input.a;
        Condition B_IS_0 = input -> 0 == input.b;
        Condition C_IS_0 = input -> 0 == input.c;
        Condition D_IS_0 = input -> 0 == input.d;
    }
}
