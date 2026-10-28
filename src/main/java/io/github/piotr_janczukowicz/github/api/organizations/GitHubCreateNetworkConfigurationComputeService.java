package io.github.piotr_janczukowicz.github.api.organizations;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/** Hosted compute service for create and update network configuration request bodies. */
@CatalogSchema("operations/hosted-compute/create-network-configuration-for-org/requestBody/properties/compute_service")
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public enum GitHubCreateNetworkConfigurationComputeService {
    NONE("none"),
    ACTIONS("actions");

    private static final EnumUtils.WireIndex<GitHubCreateNetworkConfigurationComputeService> WIRE_INDEX =
            EnumUtils.index(
                    GitHubCreateNetworkConfigurationComputeService.class,
                    GitHubCreateNetworkConfigurationComputeService::wireValue);

    private final String wireValue;

    GitHubCreateNetworkConfigurationComputeService(final String wireValue) {
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
     * @throws IllegalArgumentException if {@code value} is not a known compute service
     */
    @JsonCreator
    public static GitHubCreateNetworkConfigurationComputeService fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX
                .fromWire(value)
                .orElseThrow(() ->
                        new IllegalArgumentException("Unknown create network configuration compute service: " + value));
    }
}
