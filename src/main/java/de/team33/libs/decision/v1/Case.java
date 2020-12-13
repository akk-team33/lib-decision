package de.team33.libs.decision.v1;


/**
 * Represents a case within a case distinction.
 * <p>
 * An implementation is best (but not necessarily) as {@code enum}, so that all relevant cases of the
 * case distinction are covered by that enum.
 * <p>
 * Note: the definition of a case usually also implies the {@link Cases#not(Case) opposite case}, which then
 * does not have to be defined explicitly.
 *
 * @param <R> The potential result type.
 */
public interface Case<R> {

    /**
     * In the context of all possible cases of a case distinction, a case has a unique name that identifies it.
     * <p>
     * If the cases are implemented as {@code enum}, this method is implemented "automatically".
     */
    String name();
}
