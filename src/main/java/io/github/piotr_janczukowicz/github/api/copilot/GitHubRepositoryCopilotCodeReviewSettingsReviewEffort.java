package io.github.piotr_janczukowicz.github.api.copilot;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/**
 * The review effort for the repository. For more information, see [Review effort
 * level](https://docs.github.com/enterprise-cloud@latest/copilot/concepts/agent-capabilities/agent-powered-workflows-and-operations/code-review#review-effort-level).
 *
 * @param configured The review effort stored for the repository. Null means the repository inherits the default, or has
 *     stored a value that is not currently available.
 * @param effective The review effort reviews use when the requester and the pull request do not choose one. When that
 *     effort is not currently available for the repository, this reports the effort reviews would use without it.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/repository-copilot-code-review-settings/properties/review_effort")
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public record GitHubRepositoryCopilotCodeReviewSettingsReviewEffort(
        @Nullable String configured, String effective) {
    public GitHubRepositoryCopilotCodeReviewSettingsReviewEffort {
        requireNonNull(effective, "effective");
    }
}
