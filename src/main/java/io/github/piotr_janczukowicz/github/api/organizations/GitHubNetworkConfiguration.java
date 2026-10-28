package io.github.piotr_janczukowicz.github.api.organizations;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.time.Instant;
import java.util.List;
import org.jspecify.annotations.Nullable;

/**
 * Hosted compute network configuration.
 *
 * @param id The unique identifier of the network configuration
 * @param name The name of the network configuration
 * @param computeService The hosted compute service the network configuration supports
 * @param networkSettingsIds The unique identifier of each network settings in the configuration
 * @param failoverNetworkSettingsIds The unique identifier of each failover network settings in the configuration
 * @param failoverNetworkEnabled Indicates whether the failover network resource is enabled
 * @param createdOn The time at which the network configuration was created, in ISO 8601 format
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/network-configuration")
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public record GitHubNetworkConfiguration(
        String id,
        String name,
        @Nullable GitHubNetworkConfigurationComputeService computeService,
        @Nullable List<String> networkSettingsIds,
        @Nullable List<String> failoverNetworkSettingsIds,
        @Nullable Boolean failoverNetworkEnabled,
        @Nullable Instant createdOn) {

    public GitHubNetworkConfiguration {
        requireNonNull(id, "id");
        requireNonNull(name, "name");
    }
}
