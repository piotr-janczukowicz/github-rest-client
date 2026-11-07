package io.github.piotr_janczukowicz.github.api.copilot;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;

/**
 * Set organization code review policy payload.
 *
 * @param state The organization's Copilot code review policy.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("operations/copilot/set-organization-code-review-policy/requestBody")
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public record GitHubSetOrganizationCodeReviewPolicyPayload(GitHubSetOrganizationCodeReviewPolicyState state) {
    public GitHubSetOrganizationCodeReviewPolicyPayload {
        requireNonNull(state, "state");
    }
}
