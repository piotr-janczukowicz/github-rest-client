package io.github.piotr_janczukowicz.github.api.secretscanning;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/** The method used to attribute the alert to the enterprise. */
@Flavors(GitHubFlavor.GHEC)
@CatalogSchema("components/schemas/public-monitoring-alert-attribution-method")
public enum GitHubPublicMonitoringAlertAttributionMethod {
    MEMBER_ACTIVITY("member_activity"),
    VERIFIED_DOMAIN("verified_domain"),
    /** A value this library version does not know. */
    UNRECOGNIZED("unrecognized");

    private static final EnumUtils.WireIndex<GitHubPublicMonitoringAlertAttributionMethod> WIRE_INDEX = EnumUtils.index(
            GitHubPublicMonitoringAlertAttributionMethod.class,
            GitHubPublicMonitoringAlertAttributionMethod::wireValue);

    private final String wireValue;

    GitHubPublicMonitoringAlertAttributionMethod(final String wireValue) {
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
     * @return matching constant, or {@link #UNRECOGNIZED} when the value is not known
     * @throws NullPointerException if {@code value} is {@code null}
     */
    @JsonCreator
    public static GitHubPublicMonitoringAlertAttributionMethod fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX.fromWire(value, UNRECOGNIZED);
    }
}
