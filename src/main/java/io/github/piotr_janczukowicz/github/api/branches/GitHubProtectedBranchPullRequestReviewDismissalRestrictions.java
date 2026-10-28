package io.github.piotr_janczukowicz.github.api.branches;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.apps.GitHubIntegration;
import io.github.piotr_janczukowicz.github.api.teams.GitHubTeam;
import io.github.piotr_janczukowicz.github.api.users.GitHubSimpleUser;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.List;
import org.jspecify.annotations.Nullable;

/**
 * Dismissal restrictions on {@link GitHubProtectedBranchPullRequestReview}.
 *
 * @param users Users
 * @param teams Teams
 * @param apps Apps
 * @param url Url
 * @param usersUrl Users url
 * @param teamsUrl Teams url
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/protected-branch-pull-request-review/properties/dismissal_restrictions")
public record GitHubProtectedBranchPullRequestReviewDismissalRestrictions(
        @Nullable List<GitHubSimpleUser> users,
        @Nullable List<GitHubTeam> teams,
        @Nullable List<GitHubIntegration> apps,
        @Nullable String url,
        @Nullable String usersUrl,
        @Nullable String teamsUrl) {}
