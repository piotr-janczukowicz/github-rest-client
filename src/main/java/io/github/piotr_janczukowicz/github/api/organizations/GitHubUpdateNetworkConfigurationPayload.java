package io.github.piotr_janczukowicz.github.api.organizations;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireMaxLengthIfPresent;
import static io.github.piotr_janczukowicz.util.ValidationUtils.requireSizeAtMost;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.List;
import org.jspecify.annotations.Nullable;

/**
 * Update hosted compute network configuration request body.
 *
 * @param name Name of the network configuration
 * @param computeService The hosted compute service to use for the network configuration
 * @param networkSettingsIds Network settings resource identifiers
 * @param failoverNetworkSettingsIds Failover network settings resource identifiers
 * @param failoverNetworkEnabled Indicates whether the failover network resource is enabled
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("operations/hosted-compute/update-network-configuration-for-org/requestBody")
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public record GitHubUpdateNetworkConfigurationPayload(
        @Nullable String name,
        @Nullable GitHubCreateNetworkConfigurationComputeService computeService,
        @Nullable List<String> networkSettingsIds,
        @Nullable List<String> failoverNetworkSettingsIds,
        @Nullable Boolean failoverNetworkEnabled) {

    private static final String PARAM_NAME = "name";
    private static final String PARAM_NETWORK_SETTINGS_IDS = "networkSettingsIds";
    private static final String PARAM_FAILOVER_NETWORK_SETTINGS_IDS = "failoverNetworkSettingsIds";

    public GitHubUpdateNetworkConfigurationPayload {
        name = requireMaxLengthIfPresent(name, 100, PARAM_NAME);
        if (networkSettingsIds != null) {
            networkSettingsIds = List.copyOf(requireSizeAtMost(networkSettingsIds, 1, PARAM_NETWORK_SETTINGS_IDS));
        }
        if (failoverNetworkSettingsIds != null) {
            failoverNetworkSettingsIds =
                    List.copyOf(requireSizeAtMost(failoverNetworkSettingsIds, 1, PARAM_FAILOVER_NETWORK_SETTINGS_IDS));
        }
    }
}
