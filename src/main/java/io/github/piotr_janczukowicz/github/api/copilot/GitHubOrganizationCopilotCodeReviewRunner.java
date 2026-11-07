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
 * The effective runner configuration that Copilot code review uses for the organization.
 *
 * @param runnerType The type of runner. {@code standard} uses GitHub-hosted runners. {@code labeled} uses runners that
 *     match the configured labels or runner group.
 * @param runnerLabels The runner labels used for a labeled runner.
 * @param runnerGroupName The runner group used for a labeled runner, or null when none is set.
 * @param allowRepositoryCustomization Whether repositories can use their own runner configuration.
 * @param configuredBy Which configuration the values come from. {@code code_review} means the organization has saved a
 *     Copilot code review runner configuration. {@code coding_agent} means it has not, and the values are inherited
 *     from the organization's Copilot coding agent runner configuration.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/organization-copilot-code-review-runner")
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public record GitHubOrganizationCopilotCodeReviewRunner(
        GitHubOrganizationCopilotCodeReviewRunnerRunnerType runnerType,
        List<String> runnerLabels,
        @Nullable String runnerGroupName,
        boolean allowRepositoryCustomization,
        GitHubOrganizationCopilotCodeReviewRunnerConfiguredBy configuredBy) {
    public GitHubOrganizationCopilotCodeReviewRunner {
        requireNonNull(runnerType, "runnerType");
        requireNonNull(runnerLabels, "runnerLabels");
        requireNonNull(configuredBy, "configuredBy");
    }
}
