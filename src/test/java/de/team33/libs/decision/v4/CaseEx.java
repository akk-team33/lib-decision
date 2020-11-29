package de.team33.libs.decision.v4;

class CaseEx<R> {

    private final Case aCase;
    private final R result;

    CaseEx(final Case aCase, final R result) {
        this.aCase = aCase;
        this.result = result;
    }
}
