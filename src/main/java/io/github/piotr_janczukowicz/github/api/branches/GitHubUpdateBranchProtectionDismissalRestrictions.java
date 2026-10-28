package io.github.piotr_janczukowicz.github.api.branches;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.List;
import org.jspecify.annotations.Nullable;

/**
 * Dismissal restrictions for branch protection updates.
 *
 * @param users User logins with dismissal access
 * @param teams Team slugs with dismissal access
 * @param apps App slugs with dismissal access
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema({
    "operations/repos/update-branch-protection/requestBody/properties/required_pull_request_reviews/properties/dismissal_restrictions",
    "operations/repos/update-pull-request-review-protection/requestBody/properties/dismissal_restrictions"
})
public record GitHubUpdateBranchProtectionDismissalRestrictions(
        @Nullable List<String> users,
        @Nullable List<String> teams,
        @Nullable List<String> apps) {

    public GitHubUpdateBranchProtectionDismissalRestrictions {
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
