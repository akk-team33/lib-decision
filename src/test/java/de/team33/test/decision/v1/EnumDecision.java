package de.team33.test.decision.v1;

import de.team33.test.decision.shared.Input;

import java.util.function.Function;
import java.util.function.Predicate;

import static de.team33.libs.decision.v1.Choice.*;
import static de.team33.test.decision.v1.EnumDecision.Condition.*;

public enum EnumDecision implements Function<Input, String> {

    CASE_000_(on(D_IS_0).reply("0000").orReply("0001")),
    CASE_001_(on(D_IS_0).reply("0010").orReply("0011")),
    CASE_010_(on(D_IS_0).reply("0100").orReply("0101")),
    CASE_011_(on(D_IS_0).reply("0110").orReply("0111")),
    CASE_101_(on(D_IS_0).reply("1010").orReply("1011")),
    CASE_1_0_(on(D_IS_0).reply("1_00").orReply("1_01")),
    CASE_111_(on(D_IS_0).reply("1110").orReply("1111")),

    CASE_00__(on(C_IS_0).apply(CASE_000_).orApply(CASE_001_)),
    CASE_01__(on(C_IS_0).apply(CASE_010_).orApply(CASE_011_)),
    CASE_1_1_(on(B_IS_0).apply(CASE_101_).orApply(CASE_111_)),
    CASE_0___(on(B_IS_0).apply(CASE_00__).orApply(CASE_01__)),
    CASE_1___(on(C_IS_0).apply(CASE_1_0_).orApply(CASE_1_1_)),
    CASE_____(on(A_IS_0).apply(CASE_0___).orApply(CASE_1___));

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
