package io.github.piotr_janczukowicz.github.api.organizations;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.List;

/**
 * List hosted compute network configurations response.
 *
 * @param totalCount Total count
 * @param networkConfigurations Network configurations
 */
@CatalogSchema("operations/hosted-compute/list-network-configurations-for-org/200")
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public record GitHubNetworkConfigurationsPage(int totalCount, List<GitHubNetworkConfiguration> networkConfigurations) {

    public GitHubNetworkConfigurationsPage {
        requireNonNull(networkConfigurations, "networkConfigurations");
    }
}
