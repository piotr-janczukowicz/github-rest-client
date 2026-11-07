package io.github.piotr_janczukowicz.github.api.copilot;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/**
 * Update organization code review settings payload.
 *
 * @param defaultReviewEffort The default review effort for repositories that do not choose their own. Use
 *     {@code inherit} to clear the organization's value. Only efforts listed in the settings'
 *     {@code available_review_efforts} are accepted, and a rejected effort's error message lists them. For more
 *     information, see [Review effort
 *     level](https://docs.github.com/enterprise-cloud@latest/copilot/concepts/agent-capabilities/agent-powered-workflows-and-operations/code-review#review-effort-level).
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("operations/copilot/update-organization-code-review-settings/requestBody")
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public record GitHubUpdateOrganizationCodeReviewSettingsPayload(
        @Nullable String defaultReviewEffort) {}
