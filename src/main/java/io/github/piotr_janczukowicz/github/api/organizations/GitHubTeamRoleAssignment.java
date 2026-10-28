package io.github.piotr_janczukowicz.github.api.organizations;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.repositories.GitHubRepositoryPermissions;
import io.github.piotr_janczukowicz.github.api.teams.GitHubTeamOwnershipType;
import io.github.piotr_janczukowicz.github.api.teams.GitHubTeamSimple;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/**
 * The relationship a team has with an organization role.
 *
 * @param assignment Determines if the team has a direct, indirect, or mixed relationship to a role
 * @param id Unique identifier of the team
 * @param nodeId Node id
 * @param name Name of the team
 * @param slug Slug
 * @param description Description of the team
 * @param privacy The level of privacy this team should have
 * @param notificationSetting The notification setting the team has set
 * @param permission Permission that the team will have for its repositories
 * @param permissions Permissions
 * @param url URL for the team
 * @param htmlUrl Html url
 * @param membersUrl Members url
 * @param repositoriesUrl Repositories url
 * @param parent Parent team
 * @param type The ownership type of the team
 * @param organizationId Unique identifier of the organization to which this team belongs
 * @param enterpriseId Unique identifier of the enterprise to which this team belongs
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@CatalogSchema("components/schemas/team-role-assignment")
@RecordBuilder
public record GitHubTeamRoleAssignment(
        @Nullable GitHubOrganizationRoleAssignment assignment,
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
        @JsonInclude(JsonInclude.Include.ALWAYS) @Nullable GitHubTeamSimple parent,
        GitHubTeamOwnershipType type,
        @Nullable Long organizationId,
        @Nullable Long enterpriseId) {

    public GitHubTeamRoleAssignment {
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
