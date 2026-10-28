package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.api.organizations.GitHubNetworkConfiguration;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.List;

/**
 * List hosted compute network configurations response.
 *
 * @param totalCount Total count
 * @param networkConfigurations Network configurations
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("operations/hosted-compute/list-network-configurations-for-enterprise/200")
@Flavors(GitHubFlavor.GHEC)
public record GitHubEnterpriseNetworkConfigurationsPage(
        int totalCount, List<GitHubNetworkConfiguration> networkConfigurations) {

    public GitHubEnterpriseNetworkConfigurationsPage {
        requireNonNull(networkConfigurations, "networkConfigurations");
    }
}
