package io.github.piotr_janczukowicz.github.api.teams;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/**
 * Team Simple. Groups of organization members that gives permissions on specified repositories.
 *
 * @param id Unique identifier of the team
 * @param nodeId Node id
 * @param url URL for the team
 * @param membersUrl Members url
 * @param name Name of the team
 * @param description Description of the team
 * @param permission Permission that the team will have for its repositories
 * @param privacy The level of privacy this team should have
 * @param notificationSetting The notification setting the team has set
 * @param htmlUrl Html url
 * @param repositoriesUrl Repositories url
 * @param slug Slug
 * @param ldapDn Distinguished Name (DN) that team maps to within LDAP environment
 * @param type The ownership type of the team
 * @param organizationId Unique identifier of the organization to which this team belongs
 * @param enterpriseId Unique identifier of the enterprise to which this team belongs
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@CatalogSchema("components/schemas/team-simple")
@RecordBuilder
public record GitHubTeamSimple(
        long id,
        String nodeId,
        String url,
        String membersUrl,
        String name,
        @Nullable String description,
        String permission,
        @Nullable String privacy,
        @Nullable String notificationSetting,
        String htmlUrl,
        String repositoriesUrl,
        String slug,
        @Nullable String ldapDn,
        GitHubTeamOwnershipType type,
        @Nullable Long organizationId,
        @Nullable Long enterpriseId) {

    public GitHubTeamSimple {
        requireNonNull(nodeId, "nodeId");
        requireNonNull(url, "url");
        requireNonNull(membersUrl, "membersUrl");
        requireNonNull(name, "name");
        requireNonNull(permission, "permission");
        requireNonNull(htmlUrl, "htmlUrl");
        requireNonNull(repositoriesUrl, "repositoriesUrl");
        requireNonNull(slug, "slug");
        requireNonNull(type, "type");
    }
}
