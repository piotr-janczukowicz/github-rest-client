package io.github.piotr_janczukowicz.github.api.copilot;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/**
 * The organization's configured and effective Copilot code review policy.
 *
 * @param configuredState The policy stored for the organization. Null means the organization has not configured a
 *     policy.
 * @param effectiveState The policy that applies to the organization after enterprise policy is considered.
 * @param editable Whether organization administrators can update the policy.
 * @param managedBy Whether the enterprise or the organization controls the policy.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/organization-copilot-code-review-policy")
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public record GitHubOrganizationCopilotCodeReviewPolicy(
        @Nullable GitHubOrganizationCopilotCodeReviewPolicyConfiguredState configuredState,
        GitHubOrganizationCopilotCodeReviewPolicyEffectiveState effectiveState,
        boolean editable,
        GitHubOrganizationCopilotCodeReviewPolicyManagedBy managedBy) {
    public GitHubOrganizationCopilotCodeReviewPolicy {
        requireNonNull(effectiveState, "effectiveState");
        requireNonNull(managedBy, "managedBy");
    }
}
