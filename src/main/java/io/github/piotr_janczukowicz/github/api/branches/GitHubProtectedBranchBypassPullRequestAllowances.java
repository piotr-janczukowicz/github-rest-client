package io.github.piotr_janczukowicz.github.api.branches;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.apps.GitHubIntegration;
import io.github.piotr_janczukowicz.github.api.teams.GitHubTeam;
import io.github.piotr_janczukowicz.github.api.users.GitHubSimpleUser;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.List;
import org.jspecify.annotations.Nullable;

/**
 * Protected branch bypass pull request allowances.
 *
 * @param users Users
 * @param teams Teams
 * @param apps Apps
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema(
        "components/schemas/protected-branch/properties/required_pull_request_reviews/properties/bypass_pull_request_allowances")
public record GitHubProtectedBranchBypassPullRequestAllowances(
        List<GitHubSimpleUser> users,
        List<GitHubTeam> teams,
        @Nullable List<GitHubIntegration> apps) {

    public GitHubProtectedBranchBypassPullRequestAllowances {
        requireNonNull(users, "users");
        requireNonNull(teams, "teams");
    }
}
