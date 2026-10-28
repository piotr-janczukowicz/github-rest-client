package io.github.piotr_janczukowicz.github.api.pullrequests;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.List;
import org.jspecify.annotations.Nullable;

/**
 * Remove requested reviewers from a pull request.
 *
 * @param reviewers User logins to remove
 * @param teamReviewers Team slugs to remove
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("operations/pulls/remove-requested-reviewers/requestBody")
public record GitHubRemoveReviewersPayload(
        List<String> reviewers, @Nullable List<String> teamReviewers) {

    public GitHubRemoveReviewersPayload {
        reviewers = List.copyOf(requireNonNull(reviewers, "reviewers"));

        if (teamReviewers != null) {
            teamReviewers = List.copyOf(teamReviewers);
        }
    }
}
