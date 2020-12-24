package de.team33.test.decision.v3;

import de.team33.libs.decision.v3.Distinction;
import de.team33.libs.decision.v3.Case;
import de.team33.test.decision.shared.Input;

import java.util.function.Predicate;

import static de.team33.libs.decision.v3.Case.of;
import static de.team33.test.decision.v3.EnumDecision.Condition.*;

public enum EnumDecision implements Case<Input, String> {

    CASE_000_(of(D_IS_0, "0000", "0001")),
    CASE_001_(of(D_IS_0, "0010", "0011")),
    CASE_010_(of(D_IS_0, "0100", "0101")),
    CASE_011_(of(D_IS_0, "0110", "0111")),
    CASE_101_(of(D_IS_0, "1010", "1011")),
    CASE_1_0_(of(D_IS_0, "1_00", "1_01")),
    CASE_111_(of(D_IS_0, "1110", "1111")),

    CASE_00__(of(C_IS_0, CASE_000_, CASE_001_)),
    CASE_01__(of(C_IS_0, CASE_010_, CASE_011_)),
    CASE_1_1_(of(B_IS_0, CASE_101_, CASE_111_)),
    CASE_0___(of(B_IS_0, CASE_00__, CASE_01__)),
    CASE_1___(of(C_IS_0, CASE_1_0_, CASE_1_1_)),
    CASE_____(of(A_IS_0, CASE_0___, CASE_1___));

    private static final Distinction<Input, String> DISTINCTION = Distinction.of(values());

    private final Case<Input, String> backing;

    EnumDecision(final Case<Input, String> backing) {
        this.backing = backing;
    }

    interface Condition extends Predicate<Input> {

        Condition A_IS_0 = input -> 0 == input.a;
        Condition B_IS_0 = input -> 0 == input.b;
        Condition C_IS_0 = input -> 0 == input.c;
        Condition D_IS_0 = input -> 0 == input.d;
    }
}
