package io.github.piotr_janczukowicz.github.api.securityadvisories;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/** The type of credit the user is receiving. */
@CatalogSchema("components/schemas/security-advisory-credit-types")
public enum GitHubSecurityAdvisoryCreditType {
    ANALYST("analyst"),
    FINDER("finder"),
    REPORTER("reporter"),
    COORDINATOR("coordinator"),
    REMEDIATION_DEVELOPER("remediation_developer"),
    REMEDIATION_REVIEWER("remediation_reviewer"),
    REMEDIATION_VERIFIER("remediation_verifier"),
    TOOL("tool"),
    SPONSOR("sponsor"),
    OTHER("other");

    private static final EnumUtils.WireIndex<GitHubSecurityAdvisoryCreditType> WIRE_INDEX =
            EnumUtils.index(GitHubSecurityAdvisoryCreditType.class, GitHubSecurityAdvisoryCreditType::wireValue);

    private final String wireValue;

    GitHubSecurityAdvisoryCreditType(final String wireValue) {
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
     * Resolves security advisory credit type from its wire value.
     *
     * @param value JSON value
     * @return matching GitHubSecurityAdvisoryCreditType
     * @throws NullPointerException if {@code value} is {@code null}
     * @throws IllegalArgumentException if {@code value} is not a known security advisory credit type
     */
    @JsonCreator
    public static GitHubSecurityAdvisoryCreditType fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX
                .fromWire(value)
                .orElseThrow(
                        () -> new IllegalArgumentException("Unknown GitHub security advisory credit type: " + value));
    }
}
