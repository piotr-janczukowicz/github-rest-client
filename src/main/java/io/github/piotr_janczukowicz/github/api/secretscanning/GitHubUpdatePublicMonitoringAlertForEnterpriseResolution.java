package io.github.piotr_janczukowicz.github.api.secretscanning;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/** The reason for resolving the alert. */
@Flavors(GitHubFlavor.GHEC)
@CatalogSchema(
        "operations/secret-scanning/update-public-monitoring-alert-for-enterprise/requestBody/properties/resolution")
public enum GitHubUpdatePublicMonitoringAlertForEnterpriseResolution {
    DISMISSED("dismissed"),
    FIXED("fixed");

    private static final EnumUtils.WireIndex<GitHubUpdatePublicMonitoringAlertForEnterpriseResolution> WIRE_INDEX =
            EnumUtils.index(
                    GitHubUpdatePublicMonitoringAlertForEnterpriseResolution.class,
                    GitHubUpdatePublicMonitoringAlertForEnterpriseResolution::wireValue);

    private final String wireValue;

    GitHubUpdatePublicMonitoringAlertForEnterpriseResolution(final String wireValue) {
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
    public static GitHubUpdatePublicMonitoringAlertForEnterpriseResolution fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX
                .fromWire(value)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Unknown GitHub update public monitoring alert for enterprise resolution: " + value));
    }
}
