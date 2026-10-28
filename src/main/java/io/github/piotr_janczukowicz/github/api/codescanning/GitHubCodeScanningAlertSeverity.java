package io.github.piotr_janczukowicz.github.api.codescanning;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/** Severity of a code scanning alert. */
@CatalogSchema("components/schemas/code-scanning-alert-severity")
public enum GitHubCodeScanningAlertSeverity {
    CRITICAL("critical"),
    HIGH("high"),
    MEDIUM("medium"),
    LOW("low"),
    WARNING("warning"),
    NOTE("note"),
    ERROR("error");

    private static final EnumUtils.WireIndex<GitHubCodeScanningAlertSeverity> WIRE_INDEX =
            EnumUtils.index(GitHubCodeScanningAlertSeverity.class, GitHubCodeScanningAlertSeverity::wireValue);

    private final String wireValue;

    GitHubCodeScanningAlertSeverity(final String wireValue) {
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
     * Resolves code scanning alert severity from its wire value.
     *
     * @param value JSON value
     * @return matching GitHubCodeScanningAlertSeverity
     * @throws NullPointerException if {@code value} is {@code null}
     * @throws IllegalArgumentException if {@code value} is not a known code scanning alert severity
     */
    @JsonCreator
    public static GitHubCodeScanningAlertSeverity fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX
                .fromWire(value)
                .orElseThrow(
                        () -> new IllegalArgumentException("Unknown GitHub code scanning alert severity: " + value));
    }
}
