package io.github.piotr_janczukowicz.github.api.codescanning;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/** The security severity of the alert. */
@CatalogSchema("components/schemas/code-scanning-alert-rule-summary/properties/security_severity_level")
public enum GitHubCodeScanningAlertRuleSummarySecuritySeverityLevel {
    LOW("low"),
    MEDIUM("medium"),
    HIGH("high"),
    CRITICAL("critical"),
    /** A value this library version does not know. */
    UNKNOWN("unknown");

    private static final EnumUtils.WireIndex<GitHubCodeScanningAlertRuleSummarySecuritySeverityLevel> WIRE_INDEX =
            EnumUtils.index(
                    GitHubCodeScanningAlertRuleSummarySecuritySeverityLevel.class,
                    GitHubCodeScanningAlertRuleSummarySecuritySeverityLevel::wireValue);

    private final String wireValue;

    GitHubCodeScanningAlertRuleSummarySecuritySeverityLevel(final String wireValue) {
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
     * Resolves code scanning alert rule summary security severity level from its wire value.
     *
     * @param value JSON value
     * @return matching GitHubCodeScanningAlertRuleSummarySecuritySeverityLevel
     * @throws NullPointerException if {@code value} is {@code null} severity level
     */
    @JsonCreator
    public static GitHubCodeScanningAlertRuleSummarySecuritySeverityLevel fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX.fromWire(value, UNKNOWN);
    }
}
