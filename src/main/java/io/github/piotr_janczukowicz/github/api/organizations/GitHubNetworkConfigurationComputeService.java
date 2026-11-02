package io.github.piotr_janczukowicz.github.api.organizations;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/** Hosted compute service for a network configuration response. */
@CatalogSchema("components/schemas/network-configuration/properties/compute_service")
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public enum GitHubNetworkConfigurationComputeService {
    NONE("none"),
    ACTIONS("actions"),
    CODESPACES("codespaces"),
    /** A value this library version does not know. */
    UNRECOGNIZED("unrecognized");

    private static final EnumUtils.WireIndex<GitHubNetworkConfigurationComputeService> WIRE_INDEX = EnumUtils.index(
            GitHubNetworkConfigurationComputeService.class, GitHubNetworkConfigurationComputeService::wireValue);

    private final String wireValue;

    GitHubNetworkConfigurationComputeService(final String wireValue) {
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
     * Compute service from a wire value.
     *
     * @param value JSON value
     * @return matching compute service
     * @throws NullPointerException if {@code value} is {@code null}
     */
    @JsonCreator
    public static GitHubNetworkConfigurationComputeService fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX.fromWire(value, UNRECOGNIZED);
    }
}
