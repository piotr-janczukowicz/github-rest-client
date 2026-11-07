package io.github.piotr_janczukowicz.github.api.copilot;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.List;

/**
 * The organization's Copilot code review settings.
 *
 * @param defaultReviewEffort The default review effort for repositories in the organization that do not choose their
 *     own. For more information, see [Review effort
 *     level](https://docs.github.com/enterprise-cloud@latest/copilot/concepts/agent-capabilities/agent-powered-workflows-and-operations/code-review#review-effort-level).
 * @param availableReviewEfforts The review efforts that can currently be set for this organization, in display order.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/organization-copilot-code-review-settings")
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public record GitHubOrganizationCopilotCodeReviewSettings(
        GitHubOrganizationCopilotCodeReviewSettingsDefaultReviewEffort defaultReviewEffort,
        List<String> availableReviewEfforts) {
    public GitHubOrganizationCopilotCodeReviewSettings {
        requireNonNull(defaultReviewEffort, "defaultReviewEffort");
        requireNonNull(availableReviewEfforts, "availableReviewEfforts");
    }
}
