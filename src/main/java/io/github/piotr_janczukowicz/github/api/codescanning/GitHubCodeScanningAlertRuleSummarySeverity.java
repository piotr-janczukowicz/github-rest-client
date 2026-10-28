package io.github.piotr_janczukowicz.github.api.codescanning;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/** The severity of the alert. */
@CatalogSchema("components/schemas/code-scanning-alert-rule-summary/properties/severity")
public enum GitHubCodeScanningAlertRuleSummarySeverity {
    NONE("none"),
    NOTE("note"),
    WARNING("warning"),
    ERROR("error");

    private static final EnumUtils.WireIndex<GitHubCodeScanningAlertRuleSummarySeverity> WIRE_INDEX = EnumUtils.index(
            GitHubCodeScanningAlertRuleSummarySeverity.class, GitHubCodeScanningAlertRuleSummarySeverity::wireValue);

    private final String wireValue;

    GitHubCodeScanningAlertRuleSummarySeverity(final String wireValue) {
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
     * Resolves code scanning alert rule summary severity from its wire value.
     *
     * @param value JSON value
     * @return matching GitHubCodeScanningAlertRuleSummarySeverity
     * @throws NullPointerException if {@code value} is {@code null}
     * @throws IllegalArgumentException if {@code value} is not a known code scanning alert rule summary severity
     */
    @JsonCreator
    public static GitHubCodeScanningAlertRuleSummarySeverity fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX
                .fromWire(value)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Unknown GitHub code scanning alert rule summary severity: " + value));
    }
}
