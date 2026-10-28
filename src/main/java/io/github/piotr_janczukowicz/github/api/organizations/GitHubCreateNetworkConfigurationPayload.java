package io.github.piotr_janczukowicz.github.api.organizations;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireMaxLength;
import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;
import static io.github.piotr_janczukowicz.util.ValidationUtils.requireSizeAtMost;
import static io.github.piotr_janczukowicz.util.ValidationUtils.requireSizeExactly;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.List;
import org.jspecify.annotations.Nullable;

/**
 * Create hosted compute network configuration request body.
 *
 * @param name Name of the network configuration
 * @param networkSettingsIds Network settings resource identifiers
 * @param computeService The hosted compute service to use for the network configuration
 * @param failoverNetworkSettingsIds Failover network settings resource identifiers
 * @param failoverNetworkEnabled Indicates whether the failover network resource is enabled
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("operations/hosted-compute/create-network-configuration-for-org/requestBody")
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public record GitHubCreateNetworkConfigurationPayload(
        String name,
        List<String> networkSettingsIds,
        @Nullable GitHubCreateNetworkConfigurationComputeService computeService,
        @Nullable List<String> failoverNetworkSettingsIds,
        @Nullable Boolean failoverNetworkEnabled) {

    private static final String PARAM_NAME = "name";
    private static final String PARAM_NETWORK_SETTINGS_IDS = "networkSettingsIds";
    private static final String PARAM_FAILOVER_NETWORK_SETTINGS_IDS = "failoverNetworkSettingsIds";

    public GitHubCreateNetworkConfigurationPayload {
        requireNonNull(name, "name");
        requireNonNull(networkSettingsIds, PARAM_NETWORK_SETTINGS_IDS);
        name = requireMaxLength(name, 100, PARAM_NAME);
        networkSettingsIds = List.copyOf(requireSizeExactly(networkSettingsIds, 1, PARAM_NETWORK_SETTINGS_IDS));
        if (failoverNetworkSettingsIds != null) {
            failoverNetworkSettingsIds =
                    List.copyOf(requireSizeAtMost(failoverNetworkSettingsIds, 1, PARAM_FAILOVER_NETWORK_SETTINGS_IDS));
        }
    }
}
