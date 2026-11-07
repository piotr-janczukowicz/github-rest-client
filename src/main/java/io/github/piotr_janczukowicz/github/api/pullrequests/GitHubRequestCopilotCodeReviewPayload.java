package io.github.piotr_janczukowicz.github.api.pullrequests;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/**
 * Request copilot code review payload.
 *
 * @param reviewEffort The effort level to use for the Copilot code review. Omit to use the default effort level. Only
 *     effort levels available for the repository and actor are accepted. For more information, see [Review effort
 *     level](https://docs.github.com/enterprise-cloud@latest/copilot/concepts/agent-capabilities/agent-powered-workflows-and-operations/code-review#review-effort-level).
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("operations/pulls/request-copilot-code-review/requestBody")
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public record GitHubRequestCopilotCodeReviewPayload(
        @Nullable String reviewEffort) {}
