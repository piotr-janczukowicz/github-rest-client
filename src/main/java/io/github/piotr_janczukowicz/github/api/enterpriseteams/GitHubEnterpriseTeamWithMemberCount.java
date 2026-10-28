package io.github.piotr_janczukowicz.github.api.enterpriseteams;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.time.Instant;
import org.jspecify.annotations.Nullable;

/**
 * Group of enterprise owners and/or members, with the number of members.
 *
 * @param id Id
 * @param url Url
 * @param membersUrl Members url
 * @param membersCount The number of members in the enterprise team.
 * @param name Name
 * @param description Description
 * @param slug Slug
 * @param syncToOrganizations Retired: this field will not be returned with GHEC enterprise teams.
 * @param organizationSelectionType Organization selection type
 * @param groupId Group id
 * @param groupName Retired: this field will not be returned with GHEC enterprise teams.
 * @param htmlUrl Html url
 * @param createdAt Created at
 * @param updatedAt Updated at
 * @param notificationSetting Whether team members will receive notifications when the team is mentioned.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/enterprise-team-with-member-count")
@Flavors(GitHubFlavor.GHEC)
public record GitHubEnterpriseTeamWithMemberCount(
        long id,
        String url,
        String membersUrl,
        int membersCount,
        String name,
        @Nullable String description,
        String slug,
        @Nullable String syncToOrganizations,
        @Nullable String organizationSelectionType,
        @Nullable String groupId,
        @Nullable String groupName,
        String htmlUrl,
        Instant createdAt,
        Instant updatedAt,
        @Nullable GitHubEnterpriseTeamWithMemberCountNotificationSetting notificationSetting) {

    public GitHubEnterpriseTeamWithMemberCount {
        requireNonNull(url, "url");
        requireNonNull(membersUrl, "membersUrl");
        requireNonNull(name, "name");
        requireNonNull(slug, "slug");
        requireNonNull(htmlUrl, "htmlUrl");
        requireNonNull(createdAt, "createdAt");
        requireNonNull(updatedAt, "updatedAt");
    }
}
