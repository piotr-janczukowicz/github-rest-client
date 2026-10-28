package io.github.piotr_janczukowicz.github.api.actions;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.List;

/**
 * Repositories that can access a self-hosted runner group.
 *
 * @param selectedRepositoryIds List of repository IDs that can access the runner group
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("operations/actions/set-repo-access-to-self-hosted-runner-group-in-org/requestBody")
public record GitHubSetRepoAccessToSelfHostedRunnerGroupInOrgPayload(List<Long> selectedRepositoryIds) {

    public GitHubSetRepoAccessToSelfHostedRunnerGroupInOrgPayload {
        selectedRepositoryIds = List.copyOf(requireNonNull(selectedRepositoryIds, "selectedRepositoryIds"));
    }
}
