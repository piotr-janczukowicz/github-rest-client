package io.github.piotr_janczukowicz.github.api.copilot;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/**
 * The default review effort for repositories in the organization that do not choose their own. For more information,
 * see [Review effort
 * level](https://docs.github.com/enterprise-cloud@latest/copilot/concepts/agent-capabilities/agent-powered-workflows-and-operations/code-review#review-effort-level).
 *
 * @param configured The review effort stored for the organization. Null means the organization inherits the default, or
 *     has stored a value that is not currently available.
 * @param effective The review effort that repositories without their own setting use. When that effort is not currently
 *     available for the organization, this reports the fallback effort instead.
 * @param githubDefault The review effort the organization inherits when it does not configure one. This is the
 *     enterprise default when one applies, otherwise GitHub's default.
 * @param editable Whether organization administrators can update the setting.
 * @param managedBy Whether the enterprise or the organization controls the setting.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/organization-copilot-code-review-settings/properties/default_review_effort")
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public record GitHubOrganizationCopilotCodeReviewSettingsDefaultReviewEffort(
        @Nullable String configured,
        String effective,
        String githubDefault,
        boolean editable,
        GitHubOrganizationCopilotCodeReviewSettingsDefaultReviewEffortManagedBy managedBy) {
    public GitHubOrganizationCopilotCodeReviewSettingsDefaultReviewEffort {
        requireNonNull(effective, "effective");
        requireNonNull(githubDefault, "githubDefault");
        requireNonNull(managedBy, "managedBy");
    }
}
