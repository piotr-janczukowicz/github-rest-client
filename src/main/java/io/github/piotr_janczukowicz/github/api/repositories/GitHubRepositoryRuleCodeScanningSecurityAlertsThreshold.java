package io.github.piotr_janczukowicz.github.api.repositories;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/** Code scanning security alerts threshold for repository rule parameters. */
@CatalogSchema("components/schemas/repository-rule-params-code-scanning-tool/properties/security_alerts_threshold")
public enum GitHubRepositoryRuleCodeScanningSecurityAlertsThreshold {
    NONE("none"),
    CRITICAL("critical"),
    HIGH_OR_HIGHER("high_or_higher"),
    MEDIUM_OR_HIGHER("medium_or_higher"),
    ALL("all");

    private static final EnumUtils.WireIndex<GitHubRepositoryRuleCodeScanningSecurityAlertsThreshold> WIRE_INDEX =
            EnumUtils.index(
                    GitHubRepositoryRuleCodeScanningSecurityAlertsThreshold.class,
                    GitHubRepositoryRuleCodeScanningSecurityAlertsThreshold::wireValue);

    private final String wireValue;

    GitHubRepositoryRuleCodeScanningSecurityAlertsThreshold(final String wireValue) {
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
     * Code scanning security alerts threshold for repository rule parameters from a wire value.
     *
     * @param value JSON value
     * @return matching value
     * @throws NullPointerException if {@code value} is {@code null}
     * @throws IllegalArgumentException if {@code value} is not a known value
     */
    @JsonCreator
    public static GitHubRepositoryRuleCodeScanningSecurityAlertsThreshold fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX
                .fromWire(value)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Unknown GitHub repository rule code scanning security alerts threshold: " + value));
    }
}
