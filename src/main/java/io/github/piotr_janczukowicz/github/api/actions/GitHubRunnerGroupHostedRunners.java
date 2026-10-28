package io.github.piotr_janczukowicz.github.api.actions;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.openapi.model.SuppressSchemaTypeMismatch;
import java.util.List;

/**
 * GitHub-hosted runners of a runner group.
 *
 * @param totalCount Total number of hosted runners
 * @param runners The hosted runners
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("operations/actions/list-github-hosted-runners-in-group-for-org/200")
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public record GitHubRunnerGroupHostedRunners(
        @SuppressSchemaTypeMismatch("dump declares number; total_count is an integer count")
        int totalCount,

        List<GitHubActionsHostedRunner> runners) {

    public GitHubRunnerGroupHostedRunners {
        requireNonNull(runners, "runners");
    }
}
