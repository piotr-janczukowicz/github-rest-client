package io.github.piotr_janczukowicz.github.api.actions;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.List;

/**
 * GitHub-hosted runners of an enterprise.
 *
 * @param totalCount Total number of hosted runners
 * @param runners The hosted runners
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("operations/actions/list-hosted-runners-for-enterprise/200")
@Flavors(GitHubFlavor.GHEC)
public record GitHubActionsHostedRunnersForEnterprise(int totalCount, List<GitHubActionsHostedRunner> runners) {

    public GitHubActionsHostedRunnersForEnterprise {
        requireNonNull(runners, "runners");
    }
}
