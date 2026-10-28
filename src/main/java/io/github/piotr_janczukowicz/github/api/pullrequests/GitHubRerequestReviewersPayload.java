package io.github.piotr_janczukowicz.github.api.pullrequests;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.List;
import org.jspecify.annotations.Nullable;

/**
 * Re-request reviews from reviewers of a pull request.
 *
 * @param reviewers An array of user {@code login}s whose reviews will be rerequested
 * @param teamReviewers An array of team {@code slug}s whose reviews will be rerequested
 */
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@RecordBuilder
@CatalogSchema("operations/pulls/rerequest-reviewers/requestBody")
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public record GitHubRerequestReviewersPayload(
        @Nullable List<String> reviewers, @Nullable List<String> teamReviewers) {

    public GitHubRerequestReviewersPayload {
        final boolean hasReviewers = reviewers != null && !reviewers.isEmpty();
        final boolean hasTeamReviewers = teamReviewers != null && !teamReviewers.isEmpty();

        if (!hasReviewers && !hasTeamReviewers) {
            throw new IllegalArgumentException("at least one of reviewers or teamReviewers is required");
        }

        reviewers = hasReviewers ? List.copyOf(reviewers) : List.of();
        teamReviewers = hasTeamReviewers ? List.copyOf(teamReviewers) : List.of();
    }
}
