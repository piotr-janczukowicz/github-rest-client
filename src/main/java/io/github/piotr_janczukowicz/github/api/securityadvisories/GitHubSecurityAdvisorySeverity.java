package io.github.piotr_janczukowicz.github.api.securityadvisories;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/** The severity of the advisory. */
@CatalogSchema({
    "components/schemas/global-advisory/properties/severity",
    "operations/security-advisories/list-global-advisories/parameters/4"
})
public enum GitHubSecurityAdvisorySeverity {
    CRITICAL("critical"),
    HIGH("high"),
    MEDIUM("medium"),
    LOW("low"),
    UNKNOWN("unknown");

    private static final EnumUtils.WireIndex<GitHubSecurityAdvisorySeverity> WIRE_INDEX =
            EnumUtils.index(GitHubSecurityAdvisorySeverity.class, GitHubSecurityAdvisorySeverity::wireValue);

    private final String wireValue;

    GitHubSecurityAdvisorySeverity(final String wireValue) {
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
     * Resolves security advisory severity from its wire value.
     *
     * @param value JSON value
     * @return matching GitHubSecurityAdvisorySeverity
     * @throws NullPointerException if {@code value} is {@code null}
     * @throws IllegalArgumentException if {@code value} is not a known security advisory severity
     */
    @JsonCreator
    public static GitHubSecurityAdvisorySeverity fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX
                .fromWire(value)
                .orElseThrow(() -> new IllegalArgumentException("Unknown GitHub security advisory severity: " + value));
    }
}
