package io.github.piotr_janczukowicz.github.api.actions;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.List;

/**
 * Platforms available for GitHub-hosted runners.
 *
 * @param totalCount Total number of platforms
 * @param platforms The platforms
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("operations/actions/get-hosted-runners-platforms-for-org/200")
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public record GitHubActionsHostedRunnerPlatforms(int totalCount, List<String> platforms) {

    public GitHubActionsHostedRunnerPlatforms {
        requireNonNull(platforms, "platforms");
    }
}
