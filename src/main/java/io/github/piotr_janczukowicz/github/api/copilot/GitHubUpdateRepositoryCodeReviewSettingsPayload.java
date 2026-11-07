package io.github.piotr_janczukowicz.github.api.copilot;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/**
 * Update repository code review settings payload.
 *
 * @param reviewEffort The review effort for the repository. Use {@code inherit} to clear the repository's value. Only
 *     efforts listed in the settings' {@code available_review_efforts} are accepted, and a rejected effort's error
 *     message lists them. For more information, see [Review effort
 *     level](https://docs.github.com/enterprise-cloud@latest/copilot/concepts/agent-capabilities/agent-powered-workflows-and-operations/code-review#review-effort-level).
 * @param severityFilter Which review comment severities Copilot code review leaves out. Can only be set when the
 *     severity filter is available for the repository.
 * @param customInstructionsEnabled Whether reviews use the repository's custom instructions.
 * @param mcpEnabled Whether reviews can use MCP servers.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("operations/copilot/update-repository-code-review-settings/requestBody")
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public record GitHubUpdateRepositoryCodeReviewSettingsPayload(
        @Nullable String reviewEffort,
        @Nullable GitHubUpdateRepositoryCodeReviewSettingsSeverityFilter severityFilter,
        @Nullable Boolean customInstructionsEnabled,
        @Nullable Boolean mcpEnabled) {}
