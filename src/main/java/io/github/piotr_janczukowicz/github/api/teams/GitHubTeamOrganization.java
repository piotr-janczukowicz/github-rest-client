package io.github.piotr_janczukowicz.github.api.teams;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.time.Instant;
import org.jspecify.annotations.Nullable;

/**
 * Team Organization.
 *
 * @param login Login
 * @param id Id
 * @param nodeId Node id
 * @param url Url
 * @param reposUrl Repos url
 * @param eventsUrl Events url
 * @param hooksUrl Hooks url
 * @param issuesUrl Issues url
 * @param membersUrl Members url
 * @param publicMembersUrl Public members url
 * @param avatarUrl Avatar url
 * @param description Description
 * @param name Name
 * @param company Company
 * @param blog Blog
 * @param location Location
 * @param email Email
 * @param twitterUsername Twitter username
 * @param isVerified Is verified
 * @param hasOrganizationProjects Has organization projects
 * @param hasRepositoryProjects Has repository projects
 * @param publicRepos Public repos
 * @param publicGists Public gists
 * @param followers Followers
 * @param following Following
 * @param htmlUrl Html url
 * @param createdAt Created at
 * @param type Type
 * @param totalPrivateRepos Total private repos
 * @param ownedPrivateRepos Owned private repos
 * @param privateGists Private gists
 * @param diskUsage Disk usage
 * @param collaborators Collaborators
 * @param billingEmail Billing email
 * @param plan Plan
 * @param defaultRepositoryPermission Default repository permission
 * @param membersCanCreateRepositories Members can create repositories
 * @param twoFactorRequirementEnabled Two factor requirement enabled
 * @param membersAllowedRepositoryCreationType Members allowed repository creation type
 * @param membersCanCreatePublicRepositories Members can create public repositories
 * @param membersCanCreatePrivateRepositories Members can create private repositories
 * @param membersCanCreateInternalRepositories Members can create internal repositories
 * @param membersCanCreatePages Members can create pages
 * @param membersCanCreatePublicPages Members can create public pages
 * @param membersCanCreatePrivatePages Members can create private pages
 * @param membersCanForkPrivateRepositories Members can fork private repositories
 * @param webCommitSignoffRequired Web commit signoff required
 * @param updatedAt Updated at
 * @param archivedAt Archived at
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@CatalogSchema("components/schemas/team-organization")
@RecordBuilder
public record GitHubTeamOrganization(
        String login,
        long id,
        String nodeId,
        String url,
        String reposUrl,
        String eventsUrl,
        String hooksUrl,
        String issuesUrl,
        String membersUrl,
        String publicMembersUrl,
        String avatarUrl,
        @Nullable String description,
        @Nullable String name,
        @Nullable String company,
        @Nullable String blog,
        @Nullable String location,
        @Nullable String email,
        @Nullable String twitterUsername,
        @Nullable Boolean isVerified,
        boolean hasOrganizationProjects,
        boolean hasRepositoryProjects,
        long publicRepos,
        long publicGists,
        long followers,
        long following,
        String htmlUrl,
        Instant createdAt,
        String type,
        @Nullable Long totalPrivateRepos,
        @Nullable Long ownedPrivateRepos,
        @Nullable Long privateGists,
        @Nullable Long diskUsage,
        @Nullable Long collaborators,
        @Nullable String billingEmail,
        @Nullable GitHubTeamOrganizationPlan plan,
        @Nullable String defaultRepositoryPermission,
        @Nullable Boolean membersCanCreateRepositories,
        @Nullable Boolean twoFactorRequirementEnabled,
        @Nullable String membersAllowedRepositoryCreationType,
        @Nullable Boolean membersCanCreatePublicRepositories,
        @Nullable Boolean membersCanCreatePrivateRepositories,
        @Nullable Boolean membersCanCreateInternalRepositories,
        @Nullable Boolean membersCanCreatePages,
        @Nullable Boolean membersCanCreatePublicPages,
        @Nullable Boolean membersCanCreatePrivatePages,
        @Nullable Boolean membersCanForkPrivateRepositories,
        @Nullable Boolean webCommitSignoffRequired,
        Instant updatedAt,
        @JsonInclude(JsonInclude.Include.ALWAYS) @Nullable Instant archivedAt) {

    public GitHubTeamOrganization {
        requireNonNull(login, "login");
        requireNonNull(nodeId, "nodeId");
        requireNonNull(url, "url");
        requireNonNull(reposUrl, "reposUrl");
        requireNonNull(eventsUrl, "eventsUrl");
        requireNonNull(hooksUrl, "hooksUrl");
        requireNonNull(issuesUrl, "issuesUrl");
        requireNonNull(membersUrl, "membersUrl");
        requireNonNull(publicMembersUrl, "publicMembersUrl");
        requireNonNull(avatarUrl, "avatarUrl");
        requireNonNull(htmlUrl, "htmlUrl");
        requireNonNull(createdAt, "createdAt");
        requireNonNull(type, "type");
        requireNonNull(updatedAt, "updatedAt");
    }
}
