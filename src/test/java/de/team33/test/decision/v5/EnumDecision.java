package de.team33.test.decision.v5;

import de.team33.libs.decision.v5.Case;
import de.team33.libs.decision.v5.Decision;
import de.team33.test.decision.shared.Input;

import java.util.Optional;
import java.util.function.Predicate;

import static de.team33.libs.decision.v5.Case.head;
import static de.team33.libs.decision.v5.Case.mean;
import static de.team33.libs.decision.v5.Case.not;
import static de.team33.libs.decision.v5.Case.tail;
import static de.team33.test.decision.v5.EnumDecision.Condition.*;

enum EnumDecision implements Case<Input, String> {

    CASE_0___(head(A_IS_0)),
    CASE_00__(mean(CASE_0___, B_IS_0)),
    CASE_000_(mean(CASE_00__, C_IS_0)),

    CASE_1_0_(mean(not(CASE_0___), C_IS_0)),
    CASE_101_(mean(not(CASE_1_0_), B_IS_0)),
    CASE_010_(mean(not(CASE_00__), C_IS_0)),

    CASE_0000(mean(CASE_000_, D_IS_0, "0000")),
    CASE_0001(tail(not(CASE_0000), "0001")),
    CASE_0100(mean(CASE_010_, D_IS_0, "0100")),
    CASE_0101(tail(not(CASE_0100), "0101")),
    CASE_1_00(mean(CASE_1_0_, D_IS_0, "1_00")),
    CASE_1_01(tail(not(CASE_1_00), "1_01")),
    CASE_1010(mean(CASE_101_, D_IS_0, "1010")),
    CASE_1011(tail(not(CASE_1010), "1011")),

    CASE_0010(mean(not(CASE_000_), D_IS_0, "0010")),
    CASE_0011(tail(not(CASE_0010), "0011")),
    CASE_0110(mean(not(CASE_010_), D_IS_0, "0110")),
    CASE_0111(tail(not(CASE_0110), "0111")),
    CASE_1110(mean(not(CASE_101_), D_IS_0, "1110")),
    CASE_1111(tail(not(CASE_1110), "1111"));

    private static final Decision<Input, String> DECISION = Decision.build(values());

    private final Case<Input, String> backing;

    EnumDecision(final Case<Input, String> backing) {
        this.backing = backing;
    }

    static String map(final Input input) {
        return DECISION.apply(input);
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

    interface Condition extends Predicate<Input> {

        Condition A_IS_0 = input -> 0 == input.a;
        Condition B_IS_0 = input -> 0 == input.b;
        Condition C_IS_0 = input -> 0 == input.c;
        Condition D_IS_0 = input -> 0 == input.d;
    }
}
