package io.github.piotr_janczukowicz.github.api.teams;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.time.Instant;
import org.jspecify.annotations.Nullable;

/**
 * Group of enterprise owners and/or members.
 *
 * @param id Id
 * @param name Name
 * @param description Description
 * @param slug Slug
 * @param url Url
 * @param syncToOrganizations Retired: this field will not be returned with GHEC enterprise teams.
 * @param organizationSelectionType Organization selection type
 * @param groupId Group id
 * @param groupName Retired: this field will not be returned with GHEC enterprise teams.
 * @param htmlUrl Html url
 * @param membersUrl Members url
 * @param createdAt Created at
 * @param updatedAt Updated at
 * @param notificationSetting Whether team members will receive notifications when the team is mentioned.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@CatalogSchema("components/schemas/enterprise-team")
@RecordBuilder
public record GitHubEnterpriseTeam(
        long id,
        String name,
        @Nullable String description,
        String slug,
        String url,
        @Nullable String syncToOrganizations,
        @Nullable String organizationSelectionType,
        @Nullable String groupId,
        @Nullable String groupName,
        String htmlUrl,
        String membersUrl,
        Instant createdAt,
        Instant updatedAt,
        @Nullable GitHubEnterpriseTeamNotificationSetting notificationSetting)
        implements GitHubCopilotAssigningTeam {

    public GitHubEnterpriseTeam {
        requireNonNull(name, "name");
        requireNonNull(slug, "slug");
        requireNonNull(url, "url");
        requireNonNull(htmlUrl, "htmlUrl");
        requireNonNull(membersUrl, "membersUrl");
        requireNonNull(createdAt, "createdAt");
        requireNonNull(updatedAt, "updatedAt");
    }
}
