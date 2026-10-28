package io.github.piotr_janczukowicz.github.api.pullrequests;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.List;
import org.jspecify.annotations.Nullable;

/**
 * Request reviewers for a pull request.
 *
 * @param reviewers User logins to request
 * @param teamReviewers Team slugs to request
 */
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@RecordBuilder
@CatalogSchema("operations/pulls/request-reviewers/requestBody")
public record GitHubRequestReviewersPayload(
        @Nullable List<String> reviewers, @Nullable List<String> teamReviewers) {

    public GitHubRequestReviewersPayload {
        final boolean hasReviewers = reviewers != null && !reviewers.isEmpty();
        final boolean hasTeamReviewers = teamReviewers != null && !teamReviewers.isEmpty();

        if (!hasReviewers && !hasTeamReviewers) {
            throw new IllegalArgumentException("at least one of reviewers or teamReviewers is required");
        }

        reviewers = hasReviewers ? List.copyOf(reviewers) : List.of();
        teamReviewers = hasTeamReviewers ? List.copyOf(teamReviewers) : List.of();
    }
}
