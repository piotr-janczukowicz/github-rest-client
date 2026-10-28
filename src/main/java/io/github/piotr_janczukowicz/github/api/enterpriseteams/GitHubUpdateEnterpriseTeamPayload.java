package io.github.piotr_janczukowicz.github.api.enterpriseteams;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/**
 * Fields of an enterprise team to change.
 *
 * @param name A new name for the team.
 * @param description A new description for the team.
 * @param syncToOrganizations Retired: this field is no longer supported.
 * @param organizationSelectionType Specifies which organizations in the enterprise should have access to this team.
 * @param groupId The ID of the IdP group to assign team membership with.
 * @param notificationSetting The notification setting the team is set to.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("operations/enterprise-teams/update/requestBody")
@Flavors({GitHubFlavor.GHEC, GitHubFlavor.GHES})
public record GitHubUpdateEnterpriseTeamPayload(
        @Nullable String name,
        @Nullable String description,
        @Nullable GitHubUpdateEnterpriseTeamSyncToOrganizations syncToOrganizations,
        @Nullable GitHubUpdateEnterpriseTeamOrganizationSelectionType organizationSelectionType,
        @Nullable String groupId,
        @Nullable GitHubUpdateEnterpriseTeamNotificationSetting notificationSetting) {}
