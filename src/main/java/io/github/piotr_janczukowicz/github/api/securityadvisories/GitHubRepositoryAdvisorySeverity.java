package io.github.piotr_janczukowicz.github.api.securityadvisories;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/** The severity of the advisory. */
@CatalogSchema({
    "components/schemas/repository-advisory/properties/severity",
    "components/schemas/repository-advisory-create/properties/severity",
    "components/schemas/repository-advisory-update/properties/severity",
    "components/schemas/private-vulnerability-report-create/properties/severity"
})
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public enum GitHubRepositoryAdvisorySeverity {
    CRITICAL("critical"),
    HIGH("high"),
    MEDIUM("medium"),
    LOW("low");

    private static final EnumUtils.WireIndex<GitHubRepositoryAdvisorySeverity> WIRE_INDEX =
            EnumUtils.index(GitHubRepositoryAdvisorySeverity.class, GitHubRepositoryAdvisorySeverity::wireValue);

    private final String wireValue;

    GitHubRepositoryAdvisorySeverity(final String wireValue) {
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
     * Resolves repository advisory severity from its wire value.
     *
     * @param value JSON value
     * @return matching GitHubRepositoryAdvisorySeverity
     * @throws NullPointerException if {@code value} is {@code null}
     * @throws IllegalArgumentException if {@code value} is not a known repository advisory severity
     */
    @JsonCreator
    public static GitHubRepositoryAdvisorySeverity fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX
                .fromWire(value)
                .orElseThrow(
                        () -> new IllegalArgumentException("Unknown GitHub repository advisory severity: " + value));
    }
}
