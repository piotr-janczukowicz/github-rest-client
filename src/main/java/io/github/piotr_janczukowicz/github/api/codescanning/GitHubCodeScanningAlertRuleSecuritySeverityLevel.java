package io.github.piotr_janczukowicz.github.api.codescanning;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/** The security severity of the alert. */
@CatalogSchema("components/schemas/code-scanning-alert-rule/properties/security_severity_level")
public enum GitHubCodeScanningAlertRuleSecuritySeverityLevel {
    LOW("low"),
    MEDIUM("medium"),
    HIGH("high"),
    CRITICAL("critical"),
    /** A value this library version does not know. */
    UNKNOWN("unknown");

    private static final EnumUtils.WireIndex<GitHubCodeScanningAlertRuleSecuritySeverityLevel> WIRE_INDEX =
            EnumUtils.index(
                    GitHubCodeScanningAlertRuleSecuritySeverityLevel.class,
                    GitHubCodeScanningAlertRuleSecuritySeverityLevel::wireValue);

    private final String wireValue;

    GitHubCodeScanningAlertRuleSecuritySeverityLevel(final String wireValue) {
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
     * Resolves code scanning alert rule security severity level from its wire value.
     *
     * @param value JSON value
     * @return matching GitHubCodeScanningAlertRuleSecuritySeverityLevel
     * @throws NullPointerException if {@code value} is {@code null}
     */
    @JsonCreator
    public static GitHubCodeScanningAlertRuleSecuritySeverityLevel fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX.fromWire(value, UNKNOWN);
    }
}
