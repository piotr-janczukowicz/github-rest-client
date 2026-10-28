package io.github.piotr_janczukowicz.github.api.actions;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.List;

/**
 * Self-hosted runners that can be members of a runner group of an enterprise.
 *
 * @param runners List of runner IDs to add to the runner group
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("operations/enterprise-admin/set-self-hosted-runners-in-group-for-enterprise/requestBody")
@Flavors({GitHubFlavor.GHEC, GitHubFlavor.GHES})
public record GitHubSetSelfHostedRunnersInGroupForEnterprisePayload(List<Long> runners) {

    public GitHubSetSelfHostedRunnersInGroupForEnterprisePayload {
        runners = List.copyOf(requireNonNull(runners, "runners"));
    }
}
