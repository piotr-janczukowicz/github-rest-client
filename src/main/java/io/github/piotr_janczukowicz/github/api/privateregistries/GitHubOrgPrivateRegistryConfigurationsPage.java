package io.github.piotr_janczukowicz.github.api.privateregistries;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.List;

/**
 * List organization private registry configurations response.
 *
 * @param totalCount Total count
 * @param configurations Configurations
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("operations/private-registries/list-org-private-registries/200")
public record GitHubOrgPrivateRegistryConfigurationsPage(
        int totalCount, List<GitHubOrgPrivateRegistryConfiguration> configurations) {

    public GitHubOrgPrivateRegistryConfigurationsPage {
        requireNonNull(configurations, "configurations");
    }
}
