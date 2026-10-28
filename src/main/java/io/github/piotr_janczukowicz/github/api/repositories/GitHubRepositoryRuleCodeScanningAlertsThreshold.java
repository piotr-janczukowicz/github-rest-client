package io.github.piotr_janczukowicz.github.api.repositories;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/** Code scanning alerts threshold for repository rule parameters. */
@CatalogSchema("components/schemas/repository-rule-params-code-scanning-tool/properties/alerts_threshold")
public enum GitHubRepositoryRuleCodeScanningAlertsThreshold {
    NONE("none"),
    ERRORS("errors"),
    ERRORS_AND_WARNINGS("errors_and_warnings"),
    ALL("all");

    private static final EnumUtils.WireIndex<GitHubRepositoryRuleCodeScanningAlertsThreshold> WIRE_INDEX =
            EnumUtils.index(
                    GitHubRepositoryRuleCodeScanningAlertsThreshold.class,
                    GitHubRepositoryRuleCodeScanningAlertsThreshold::wireValue);

    private final String wireValue;

    GitHubRepositoryRuleCodeScanningAlertsThreshold(final String wireValue) {
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
     * Code scanning alerts threshold for repository rule parameters from a wire value.
     *
     * @param value JSON value
     * @return matching value
     * @throws NullPointerException if {@code value} is {@code null}
     * @throws IllegalArgumentException if {@code value} is not a known value
     */
    @JsonCreator
    public static GitHubRepositoryRuleCodeScanningAlertsThreshold fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX
                .fromWire(value)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Unknown GitHub repository rule code scanning alerts threshold: " + value));
    }
}
