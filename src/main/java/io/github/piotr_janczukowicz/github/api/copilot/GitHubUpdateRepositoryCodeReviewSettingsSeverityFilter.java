package io.github.piotr_janczukowicz.github.api.copilot;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/**
 * Which review comment severities Copilot code review leaves out. Can only be set when the severity filter is available
 * for the repository.
 */
@CatalogSchema("operations/copilot/update-repository-code-review-settings/requestBody/properties/severity_filter")
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public enum GitHubUpdateRepositoryCodeReviewSettingsSeverityFilter {
    NO_FILTER("no_filter"),
    LOW("low"),
    LOW_AND_MEDIUM("low_and_medium");

    private static final EnumUtils.WireIndex<GitHubUpdateRepositoryCodeReviewSettingsSeverityFilter> WIRE_INDEX =
            EnumUtils.index(
                    GitHubUpdateRepositoryCodeReviewSettingsSeverityFilter.class,
                    GitHubUpdateRepositoryCodeReviewSettingsSeverityFilter::wireValue);

    private final String wireValue;

    GitHubUpdateRepositoryCodeReviewSettingsSeverityFilter(final String wireValue) {
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
     * @return matching constant
     * @throws NullPointerException if {@code value} is {@code null}
     * @throws IllegalArgumentException if {@code value} is not a known value
     */
    @JsonCreator
    public static GitHubUpdateRepositoryCodeReviewSettingsSeverityFilter fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX
                .fromWire(value)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Unknown GitHub update repository code review settings severity filter: " + value));
    }
}
