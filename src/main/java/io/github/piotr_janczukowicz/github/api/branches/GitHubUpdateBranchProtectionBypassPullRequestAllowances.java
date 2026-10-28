package io.github.piotr_janczukowicz.github.api.branches;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.List;
import org.jspecify.annotations.Nullable;

/**
 * Bypass pull request allowances for branch protection updates.
 *
 * @param users User logins allowed to bypass
 * @param teams Team slugs allowed to bypass
 * @param apps App slugs allowed to bypass
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema({
    "operations/repos/update-branch-protection/requestBody/properties/required_pull_request_reviews/properties/bypass_pull_request_allowances",
    "operations/repos/update-pull-request-review-protection/requestBody/properties/bypass_pull_request_allowances"
})
public record GitHubUpdateBranchProtectionBypassPullRequestAllowances(
        @Nullable List<String> users,
        @Nullable List<String> teams,
        @Nullable List<String> apps) {

    public GitHubUpdateBranchProtectionBypassPullRequestAllowances {
        if (users != null) {
            users = List.copyOf(users);
        }
        if (teams != null) {
            teams = List.copyOf(teams);
        }
        if (apps != null) {
            apps = List.copyOf(apps);
        }
    }
}
