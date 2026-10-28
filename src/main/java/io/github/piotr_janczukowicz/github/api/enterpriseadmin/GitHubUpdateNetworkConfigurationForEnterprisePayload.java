package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

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
@CatalogSchema("operations/hosted-compute/update-network-configuration-for-enterprise/requestBody")
@Flavors(GitHubFlavor.GHEC)
public record GitHubUpdateNetworkConfigurationForEnterprisePayload(
        @Nullable String name,
        @Nullable GitHubUpdateNetworkConfigurationForEnterpriseComputeService computeService,
        @Nullable List<String> networkSettingsIds,
        @Nullable List<String> failoverNetworkSettingsIds,
        @Nullable Boolean failoverNetworkEnabled) {

    public GitHubUpdateNetworkConfigurationForEnterprisePayload {
        if (networkSettingsIds != null) {
            networkSettingsIds = List.copyOf(requireSizeAtMost(networkSettingsIds, 1, "networkSettingsIds"));
        }
        if (failoverNetworkSettingsIds != null) {
            failoverNetworkSettingsIds =
                    List.copyOf(requireSizeAtMost(failoverNetworkSettingsIds, 1, "failoverNetworkSettingsIds"));
        }
    }
}
