package io.github.piotr_janczukowicz.github.api.copilot;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.List;
import org.jspecify.annotations.Nullable;

/**
 * Set organization code review runner payload.
 *
 * @param runnerType The type of runner. {@code standard} uses GitHub-hosted runners. {@code labeled} uses runners that
 *     match the labels or runner group.
 * @param runnerLabels The runner labels for a labeled runner.
 * @param runnerGroupName The runner group for a labeled runner.
 * @param allowRepositoryCustomization Whether repositories can use their own runner configuration.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("operations/copilot/set-organization-code-review-runner/requestBody")
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public record GitHubSetOrganizationCodeReviewRunnerPayload(
        GitHubSetOrganizationCodeReviewRunnerRunnerType runnerType,
        @Nullable List<String> runnerLabels,
        @Nullable String runnerGroupName,
        @Nullable Boolean allowRepositoryCustomization) {
    public GitHubSetOrganizationCodeReviewRunnerPayload {
        requireNonNull(runnerType, "runnerType");
        if (runnerLabels != null) {
            runnerLabels = List.copyOf(runnerLabels);
        }
    }
}
