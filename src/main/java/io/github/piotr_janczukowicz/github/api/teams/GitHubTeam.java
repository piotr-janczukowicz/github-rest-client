package io.github.piotr_janczukowicz.github.api.teams;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.repositories.GitHubRepositoryPermissions;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/**
 * Team. Groups of organization members that gives permissions on specified repositories.
 *
 * @param id Id
 * @param nodeId Node id
 * @param name Name
 * @param slug Slug
 * @param description Description
 * @param privacy Privacy
 * @param notificationSetting Notification setting
 * @param permission Permission
 * @param permissions Permissions
 * @param url Url
 * @param htmlUrl Html url
 * @param membersUrl Members url
 * @param repositoriesUrl Repositories url
 * @param type The ownership type of the team
 * @param accessSource How the team's access to the repository was granted. This property is only present when the team
 *     is returned in a repository context, such as {@code GET /repos/{owner}/{repo}/teams}.
 * @param organizationId Unique identifier of the organization to which this team belongs
 * @param enterpriseId Unique identifier of the enterprise to which this team belongs
 * @param parent Parent
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@CatalogSchema("components/schemas/team")
@RecordBuilder
public record GitHubTeam(
        long id,
        String nodeId,
        String name,
        String slug,
        @Nullable String description,
        @Nullable String privacy,
        @Nullable String notificationSetting,
        String permission,
        @Nullable GitHubRepositoryPermissions permissions,
        String url,
        String htmlUrl,
        String membersUrl,
        String repositoriesUrl,
        GitHubTeamOwnershipType type,
        @Nullable GitHubTeamAccessSource accessSource,
        @Nullable Long organizationId,
        @Nullable Long enterpriseId,
        @JsonInclude(JsonInclude.Include.ALWAYS) @Nullable GitHubTeamSimple parent)
        implements GitHubCopilotAssigningTeam {

    public GitHubTeam {
        requireNonNull(nodeId, "nodeId");
        requireNonNull(name, "name");
        requireNonNull(slug, "slug");
        requireNonNull(permission, "permission");
        requireNonNull(url, "url");
        requireNonNull(htmlUrl, "htmlUrl");
        requireNonNull(membersUrl, "membersUrl");
        requireNonNull(repositoriesUrl, "repositoriesUrl");
        requireNonNull(type, "type");
    }
}
