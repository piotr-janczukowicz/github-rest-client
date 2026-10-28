package io.github.piotr_janczukowicz.github.api.pullrequests;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.teams.GitHubTeam;
import io.github.piotr_janczukowicz.github.api.users.GitHubSimpleUser;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.List;

/**
 * Pull request review request.
 *
 * @param users Users whose review is requested
 * @param teams Teams whose review is requested
 */
@CatalogSchema("components/schemas/pull-request-review-request")
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
public record GitHubPullRequestReviewRequest(List<GitHubSimpleUser> users, List<GitHubTeam> teams) {

    public GitHubPullRequestReviewRequest {
        requireNonNull(users, "users");
        requireNonNull(teams, "teams");
    }
}
