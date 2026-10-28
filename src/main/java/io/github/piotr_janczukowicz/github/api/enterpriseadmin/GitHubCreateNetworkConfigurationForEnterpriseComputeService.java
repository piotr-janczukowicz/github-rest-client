package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/** The hosted compute service to use for the network configuration. */
@CatalogSchema(
        "operations/hosted-compute/create-network-configuration-for-enterprise/requestBody/properties/compute_service")
@Flavors(GitHubFlavor.GHEC)
public enum GitHubCreateNetworkConfigurationForEnterpriseComputeService {
    /** None. */
    NONE("none"),
    /** Actions. */
    ACTIONS("actions");

    private static final EnumUtils.WireIndex<GitHubCreateNetworkConfigurationForEnterpriseComputeService> WIRE_INDEX =
            EnumUtils.index(
                    GitHubCreateNetworkConfigurationForEnterpriseComputeService.class,
                    GitHubCreateNetworkConfigurationForEnterpriseComputeService::wireValue);

    private final String wireValue;

    GitHubCreateNetworkConfigurationForEnterpriseComputeService(final String wireValue) {
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
     * Value from a wire string.
     *
     * @param value JSON value
     * @return matching value
     * @throws NullPointerException if {@code value} is {@code null}
     * @throws IllegalArgumentException if {@code value} is not a known value
     */
    @JsonCreator
    public static GitHubCreateNetworkConfigurationForEnterpriseComputeService fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX
                .fromWire(value)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Unknown GitHub create network configuration for enterprise compute service: " + value));
    }
}
