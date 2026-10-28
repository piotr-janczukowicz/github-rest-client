package io.github.piotr_janczukowicz.github.api.teams;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.time.Instant;
import org.jspecify.annotations.Nullable;

/**
 * Full Team. Groups of organization members that gives permissions on specified repositories.
 *
 * @param id Unique identifier of the team
 * @param nodeId Node id
 * @param url URL for the team
 * @param htmlUrl Html url
 * @param name Name of the team
 * @param slug Slug
 * @param description Description
 * @param privacy The level of privacy this team should have
 * @param notificationSetting The notification setting the team has set
 * @param permission Permission that the team will have for its repositories
 * @param membersUrl Members url
 * @param repositoriesUrl Repositories url
 * @param parent Parent
 * @param membersCount Members count
 * @param reposCount Repos count
 * @param createdAt Created at
 * @param updatedAt Updated at
 * @param organization Team Organization
 * @param ldapDn The [distinguished name](https://www.ldap.com/ldap-dns-and-rdns) (DN) of the LDAP entry to map to a
 *     team.
 * @param type The ownership type of the team
 * @param organizationId Unique identifier of the organization to which this team belongs
 * @param enterpriseId Unique identifier of the enterprise to which this team belongs
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@CatalogSchema("components/schemas/team-full")
@RecordBuilder
public record GitHubTeamFull(
        long id,
        String nodeId,
        String url,
        String htmlUrl,
        String name,
        String slug,
        @Nullable String description,
        @Nullable GitHubTeamPrivacy privacy,
        @Nullable GitHubTeamNotificationSetting notificationSetting,
        String permission,
        String membersUrl,
        String repositoriesUrl,
        @Nullable GitHubTeamSimple parent,
        long membersCount,
        long reposCount,
        Instant createdAt,
        Instant updatedAt,
        GitHubTeamOrganization organization,
        @Nullable String ldapDn,
        GitHubTeamOwnershipType type,
        @Nullable Long organizationId,
        @Nullable Long enterpriseId) {

    public GitHubTeamFull {
        requireNonNull(nodeId, "nodeId");
        requireNonNull(url, "url");
        requireNonNull(htmlUrl, "htmlUrl");
        requireNonNull(name, "name");
        requireNonNull(slug, "slug");
        requireNonNull(permission, "permission");
        requireNonNull(membersUrl, "membersUrl");
        requireNonNull(repositoriesUrl, "repositoriesUrl");
        requireNonNull(createdAt, "createdAt");
        requireNonNull(updatedAt, "updatedAt");
        requireNonNull(organization, "organization");
        requireNonNull(type, "type");
    }
}
