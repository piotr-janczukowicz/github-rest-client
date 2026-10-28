package io.github.piotr_janczukowicz.github.api.interactions;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireBetweenIfPresent;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/**
 * Update pull request creation cap for an org.
 *
 * @param enabled Whether the pull request creation cap is enabled
 * @param maxOpenPullRequests The maximum number of open pull requests a user can have at one time
 * @param includeDrafts Whether draft pull requests count toward the pull request creation cap
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema({
    "operations/interactions/update-pull-request-creation-cap-for-org/requestBody",
    "operations/interactions/update-pull-request-creation-cap-for-repo/requestBody"
})
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public record GitHubUpdatePullRequestCreationCapForOrgPayload(
        boolean enabled,
        @Nullable Integer maxOpenPullRequests,
        @Nullable Boolean includeDrafts) {

    public GitHubUpdatePullRequestCreationCapForOrgPayload {
        requireBetweenIfPresent(maxOpenPullRequests, 1, 1000, "maxOpenPullRequests");
    }
}
