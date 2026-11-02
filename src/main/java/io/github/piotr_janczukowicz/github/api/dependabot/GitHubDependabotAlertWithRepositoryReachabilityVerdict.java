package io.github.piotr_janczukowicz.github.api.dependabot;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/**
 * The reachability verdict for the alert. - {@code reachable}: a path from the repository's code reaches a vulnerable
 * function. - {@code unreachable}: no path from the repository's code reaches a vulnerable function. -
 * {@code potentially_reachable}: reachability could not be determined with confidence; review manually. -
 * {@code unavailable}: no verdict has been computed for the alert yet. - {@code disabled}: reachability analysis is not
 * enabled for the repository.
 */
@CatalogSchema("components/schemas/dependabot-alert-with-repository/properties/reachability/properties/verdict")
public enum GitHubDependabotAlertWithRepositoryReachabilityVerdict {
    REACHABLE("reachable"),
    UNREACHABLE("unreachable"),
    POTENTIALLY_REACHABLE("potentially_reachable"),
    UNAVAILABLE("unavailable"),
    DISABLED("disabled"),
    /** A value this library version does not know. */
    UNRECOGNIZED("unrecognized");

    private static final EnumUtils.WireIndex<GitHubDependabotAlertWithRepositoryReachabilityVerdict> WIRE_INDEX =
            EnumUtils.index(
                    GitHubDependabotAlertWithRepositoryReachabilityVerdict.class,
                    GitHubDependabotAlertWithRepositoryReachabilityVerdict::wireValue);

    private final String wireValue;

    GitHubDependabotAlertWithRepositoryReachabilityVerdict(final String wireValue) {
        this.wireValue = wireValue;
    }

    /**
     * Value GitHub sends and expects in JSON.
     *
     * @return wire string
     */
    @JsonValue
    public String wireValue() {
        return wireValue;
    }

    /**
     * Resolves the constant from its wire value.
     *
     * @param value JSON value
     * @return matching constant, or {@link #UNRECOGNIZED} when the value is not known
     * @throws NullPointerException if {@code value} is {@code null}
     */
    @JsonCreator
    public static GitHubDependabotAlertWithRepositoryReachabilityVerdict fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX.fromWire(value, UNRECOGNIZED);
    }
}
