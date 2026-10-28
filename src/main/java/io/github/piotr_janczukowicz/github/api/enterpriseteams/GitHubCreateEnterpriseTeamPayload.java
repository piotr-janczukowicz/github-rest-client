package io.github.piotr_janczukowicz.github.api.enterpriseteams;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/**
 * Enterprise team to create.
 *
 * @param name The name of the team.
 * @param description A description of the team.
 * @param syncToOrganizations Retired: this field is no longer supported.
 * @param organizationSelectionType Specifies which organizations in the enterprise should have access to this team.
 * @param groupId The ID of the IdP group to assign team membership with.
 * @param notificationSetting The notification setting the team is set to.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("operations/enterprise-teams/create/requestBody")
@Flavors({GitHubFlavor.GHEC, GitHubFlavor.GHES})
public record GitHubCreateEnterpriseTeamPayload(
        String name,
        @Nullable String description,
        @Nullable GitHubCreateEnterpriseTeamSyncToOrganizations syncToOrganizations,
        @Nullable GitHubCreateEnterpriseTeamOrganizationSelectionType organizationSelectionType,
        @Nullable String groupId,
        @Nullable GitHubCreateEnterpriseTeamNotificationSetting notificationSetting) {

    public GitHubCreateEnterpriseTeamPayload {
        requireNonNull(name, "name");
    }
}
