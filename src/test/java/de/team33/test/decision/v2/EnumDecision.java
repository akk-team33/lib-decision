package de.team33.test.decision.v2;

import de.team33.test.decision.shared.Input;

import java.util.function.Function;
import java.util.function.Predicate;

import static de.team33.libs.decision.v2.Choice.*;
import static de.team33.test.decision.v2.EnumDecision.Condition.*;

public enum EnumDecision implements Function<Input, String> {

    CASE_000_(when(D_IS_0).then("0000").orElse("0001")),
    CASE_001_(when(D_IS_0).then("0010").orElse("0011")),
    CASE_010_(when(D_IS_0).then("0100").orElse("0101")),
    CASE_011_(when(D_IS_0).then("0110").orElse("0111")),
    CASE_101_(when(D_IS_0).then("1010").orElse("1011")),
    CASE_1_0_(when(D_IS_0).then("1_00").orElse("1_01")),
    CASE_111_(when(D_IS_0).then("1110").orElse("1111")),

    CASE_00__(when(C_IS_0).then(CASE_000_).orElse(CASE_001_)),
    CASE_01__(when(C_IS_0).then(CASE_010_).orElse(CASE_011_)),
    CASE_1_1_(when(B_IS_0).then(CASE_101_).orElse(CASE_111_)),
    CASE_0___(when(B_IS_0).then(CASE_00__).orElse(CASE_01__)),
    CASE_1___(when(C_IS_0).then(CASE_1_0_).orElse(CASE_1_1_)),
    CASE_____(when(A_IS_0).then(CASE_0___).orElse(CASE_1___));

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

    @FunctionalInterface
    interface Condition extends Predicate<Input> {

        Condition A_IS_0 = input -> 0 == input.a;
        Condition B_IS_0 = input -> 0 == input.b;
        Condition C_IS_0 = input -> 0 == input.c;
        Condition D_IS_0 = input -> 0 == input.d;
    }
}
