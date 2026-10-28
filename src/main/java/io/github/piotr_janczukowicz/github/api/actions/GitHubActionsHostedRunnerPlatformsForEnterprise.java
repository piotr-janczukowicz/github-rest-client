package io.github.piotr_janczukowicz.github.api.actions;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.List;

/**
 * Platforms available for GitHub-hosted runners of an enterprise.
 *
 * @param totalCount Total number of platforms
 * @param platforms The platforms
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("operations/actions/get-hosted-runners-platforms-for-enterprise/200")
@Flavors(GitHubFlavor.GHEC)
public record GitHubActionsHostedRunnerPlatformsForEnterprise(int totalCount, List<String> platforms) {

    public GitHubActionsHostedRunnerPlatformsForEnterprise {
        requireNonNull(platforms, "platforms");
    }
}
