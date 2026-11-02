package io.github.piotr_janczukowicz.github.api.secretscanning;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/** The attribution method to filter by. */
@Flavors(GitHubFlavor.GHEC)
@CatalogSchema("operations/secret-scanning/list-public-monitoring-alerts-for-enterprise/parameters/8")
public enum GitHubListPublicMonitoringAlertsForEnterpriseAttribution {
    MEMBER_ACTIVITY("member_activity"),
    VERIFIED_DOMAIN("verified_domain");

    private static final EnumUtils.WireIndex<GitHubListPublicMonitoringAlertsForEnterpriseAttribution> WIRE_INDEX =
            EnumUtils.index(
                    GitHubListPublicMonitoringAlertsForEnterpriseAttribution.class,
                    GitHubListPublicMonitoringAlertsForEnterpriseAttribution::wireValue);

    private final String wireValue;

    GitHubListPublicMonitoringAlertsForEnterpriseAttribution(final String wireValue) {
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
    public static GitHubListPublicMonitoringAlertsForEnterpriseAttribution fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX
                .fromWire(value)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Unknown GitHub list public monitoring alerts for enterprise attribution: " + value));
    }
}
