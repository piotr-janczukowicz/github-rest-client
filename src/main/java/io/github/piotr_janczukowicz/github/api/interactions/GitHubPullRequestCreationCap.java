package io.github.piotr_janczukowicz.github.api.interactions;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireBetween;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/**
 * Pull request creation cap.
 *
 * @param enabled Whether the pull request creation cap is enabled
 * @param maxOpenPullRequests The maximum number of open pull requests a user can have at one time
 * @param includeDrafts Whether draft pull requests count toward the pull request creation cap
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@CatalogSchema({
    "operations/interactions/get-pull-request-creation-cap-for-org/200",
    "operations/interactions/update-pull-request-creation-cap-for-org/200",
    "operations/interactions/get-pull-request-creation-cap-for-repo/200",
    "operations/interactions/update-pull-request-creation-cap-for-repo/200"
})
@RecordBuilder
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public record GitHubPullRequestCreationCap(
        boolean enabled, int maxOpenPullRequests, @Nullable Boolean includeDrafts) {

    public GitHubPullRequestCreationCap {
        requireBetween(maxOpenPullRequests, 1, 1000, "maxOpenPullRequests");
    }
}
