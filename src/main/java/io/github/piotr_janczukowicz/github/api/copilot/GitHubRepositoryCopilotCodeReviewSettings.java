package io.github.piotr_janczukowicz.github.api.copilot;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.List;

/**
 * The repository's Copilot code review settings. These settings tune reviews; they do not enable Copilot code review.
 *
 * @param reviewEffort The review effort for the repository. For more information, see [Review effort
 *     level](https://docs.github.com/enterprise-cloud@latest/copilot/concepts/agent-capabilities/agent-powered-workflows-and-operations/code-review#review-effort-level).
 * @param availableReviewEfforts The review efforts that can currently be set for this repository, in display order.
 * @param severityFilter Which review comment severities Copilot code review leaves out. {@code no_filter} keeps every
 *     comment, {@code low} leaves out low-severity comments, and {@code low_and_medium} leaves out low- and
 *     medium-severity comments.
 * @param customInstructionsEnabled Whether reviews use the repository's custom instructions.
 * @param mcpEnabled Whether reviews can use MCP servers.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/repository-copilot-code-review-settings")
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public record GitHubRepositoryCopilotCodeReviewSettings(
        GitHubRepositoryCopilotCodeReviewSettingsReviewEffort reviewEffort,
        List<String> availableReviewEfforts,
        GitHubRepositoryCopilotCodeReviewSettingsSeverityFilter severityFilter,
        boolean customInstructionsEnabled,
        boolean mcpEnabled) {
    public GitHubRepositoryCopilotCodeReviewSettings {
        requireNonNull(reviewEffort, "reviewEffort");
        requireNonNull(availableReviewEfforts, "availableReviewEfforts");
        requireNonNull(severityFilter, "severityFilter");
    }
}
