package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

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
 * @param computeService The hosted compute service to use for the network configuration
 * @param networkSettingsIds Network settings resource identifiers
 * @param failoverNetworkSettingsIds Failover network settings resource identifiers
 * @param failoverNetworkEnabled Indicates whether the failover network resource is enabled
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("operations/hosted-compute/create-network-configuration-for-enterprise/requestBody")
@Flavors(GitHubFlavor.GHEC)
public record GitHubCreateNetworkConfigurationForEnterprisePayload(
        String name,
        @Nullable GitHubCreateNetworkConfigurationForEnterpriseComputeService computeService,
        List<String> networkSettingsIds,
        @Nullable List<String> failoverNetworkSettingsIds,
        @Nullable Boolean failoverNetworkEnabled) {

    public GitHubCreateNetworkConfigurationForEnterprisePayload {
        requireNonNull(name, "name");
        networkSettingsIds = List.copyOf(
                requireSizeExactly(requireNonNull(networkSettingsIds, "networkSettingsIds"), 1, "networkSettingsIds"));
        if (failoverNetworkSettingsIds != null) {
            failoverNetworkSettingsIds =
                    List.copyOf(requireSizeAtMost(failoverNetworkSettingsIds, 1, "failoverNetworkSettingsIds"));
        }
    }
}
