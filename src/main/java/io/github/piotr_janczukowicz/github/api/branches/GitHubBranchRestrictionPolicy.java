package io.github.piotr_janczukowicz.github.api.branches;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.teams.GitHubTeam;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.List;

/**
 * Branch Restriction Policy
 *
 * @param url Url
 * @param usersUrl Users url
 * @param teamsUrl Teams url
 * @param appsUrl Apps url
 * @param users Users
 * @param teams Teams
 * @param apps Apps
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/branch-restriction-policy")
public record GitHubBranchRestrictionPolicy(
        String url,
        String usersUrl,
        String teamsUrl,
        String appsUrl,
        List<GitHubBranchRestrictionPolicyUser> users,
        List<GitHubTeam> teams,
        List<GitHubBranchRestrictionPolicyApp> apps) {

    public GitHubBranchRestrictionPolicy {
        requireNonNull(url, "url");
        requireNonNull(usersUrl, "usersUrl");
        requireNonNull(teamsUrl, "teamsUrl");
        requireNonNull(appsUrl, "appsUrl");
        requireNonNull(users, "users");
        requireNonNull(teams, "teams");
        requireNonNull(apps, "apps");
    }
}
