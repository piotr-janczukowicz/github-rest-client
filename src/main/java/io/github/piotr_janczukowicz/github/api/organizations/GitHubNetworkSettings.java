package io.github.piotr_janczukowicz.github.api.organizations;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/**
 * Hosted compute network settings resource.
 *
 * @param id The unique identifier of the network settings resource
 * @param networkConfigurationId The identifier of the network configuration that is using this settings resource
 * @param name The name of the network settings resource
 * @param subnetId The subnet this network settings resource is configured for
 * @param region The location of the subnet this network settings resource is configured for
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/network-settings")
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public record GitHubNetworkSettings(
        String id, @Nullable String networkConfigurationId, String name, String subnetId, String region) {

    public GitHubNetworkSettings {
        requireNonNull(id, "id");
        requireNonNull(name, "name");
        requireNonNull(subnetId, "subnetId");
        requireNonNull(region, "region");
    }
}
