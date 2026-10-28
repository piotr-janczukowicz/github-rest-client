package io.github.piotr_janczukowicz.github.api.organizations;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireMaxLengthIfPresent;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/**
 * Update an organization.
 *
 * @param billingEmail Billing email address
 * @param company The company name
 * @param email The publicly visible email address
 * @param twitterUsername The Twitter username of the company
 * @param location The location
 * @param name The shorthand name of the company
 * @param description The description of the company
 * @param hasOrganizationProjects Whether an organization can use organization projects
 * @param hasRepositoryProjects Whether repositories that belong to the organization can use repository projects
 * @param defaultRepositoryPermission Default permission level members have for organization repositories
 * @param membersCanCreateRepositories Whether of non-admin organization members can create repositories
 * @param membersCanCreateInternalRepositories Whether organization members can create internal repositories
 * @param membersCanCreatePrivateRepositories Whether organization members can create private repositories
 * @param membersCanCreatePublicRepositories Whether organization members can create public repositories
 * @param membersAllowedRepositoryCreationType Specifies which types of repositories non-admin organization members can
 *     create
 * @param membersCanCreatePages Whether organization members can create GitHub Pages sites
 * @param membersCanCreatePublicPages Whether organization members can create public GitHub Pages sites
 * @param membersCanCreatePrivatePages Whether organization members can create private GitHub Pages sites
 * @param membersCanForkPrivateRepositories Whether organization members can fork private organization repositories
 * @param webCommitSignoffRequired Whether contributors to organization repositories are required to sign off on commits
 * @param blog Blog
 * @param advancedSecurityEnabledForNewRepositories Deprecated: endpoint closing down notice. Please use code security
 *     configurations instead.
 * @param dependabotAlertsEnabledForNewRepositories Deprecated: endpoint closing down notice. Please use code security
 *     configurations instead.
 * @param dependabotSecurityUpdatesEnabledForNewRepositories Deprecated: endpoint closing down notice. Please use code
 *     security configurations instead.
 * @param dependencyGraphEnabledForNewRepositories Deprecated: endpoint closing down notice. Please use code security
 *     configurations instead.
 * @param secretScanningEnabledForNewRepositories Deprecated: endpoint closing down notice. Please use code security
 *     configurations instead.
 * @param secretScanningPushProtectionEnabledForNewRepositories Deprecated: endpoint closing down notice. Please use
 *     code security configurations instead.
 * @param secretScanningPushProtectionCustomLink Secret scanning push protection custom link
 * @param deployKeysEnabledForRepositories Controls whether or not deploy keys may be added and used for repositories
 * @param secretScanningValidityChecksEnabled Deprecated: endpoint closing down notice. Please use code security
 *     configurations instead. Whether secret scanning automatic validity checks on supported partner tokens is enabled
 *     for all repositories under this organization.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("operations/orgs/update/requestBody")
public record GitHubUpdateOrganizationPayload(
        @Nullable String billingEmail,
        @Nullable String company,
        @Nullable String email,
        @Nullable String twitterUsername,
        @Nullable String location,
        @Nullable String name,
        @Nullable String description,
        @Nullable Boolean hasOrganizationProjects,
        @Nullable Boolean hasRepositoryProjects,
        @Nullable GitHubOrganizationDefaultRepositoryPermission defaultRepositoryPermission,
        @Nullable Boolean membersCanCreateRepositories,
        @Nullable Boolean membersCanCreateInternalRepositories,
        @Nullable Boolean membersCanCreatePrivateRepositories,
        @Nullable Boolean membersCanCreatePublicRepositories,
        @Nullable GitHubOrganizationMembersAllowedRepositoryCreationType membersAllowedRepositoryCreationType,
        @Nullable Boolean membersCanCreatePages,
        @Nullable Boolean membersCanCreatePublicPages,
        @Nullable Boolean membersCanCreatePrivatePages,
        @Nullable Boolean membersCanForkPrivateRepositories,
        @Nullable Boolean webCommitSignoffRequired,
        @Nullable String blog,
        @Deprecated @Nullable Boolean advancedSecurityEnabledForNewRepositories,
        @Deprecated @Nullable Boolean dependabotAlertsEnabledForNewRepositories,
        @Deprecated @Nullable Boolean dependabotSecurityUpdatesEnabledForNewRepositories,
        @Deprecated @Nullable Boolean dependencyGraphEnabledForNewRepositories,
        @Deprecated @Nullable Boolean secretScanningEnabledForNewRepositories,
        @Deprecated @Nullable Boolean secretScanningPushProtectionEnabledForNewRepositories,
        @Nullable String secretScanningPushProtectionCustomLink,
        @Nullable Boolean deployKeysEnabledForRepositories,
        @Deprecated @Nullable Boolean secretScanningValidityChecksEnabled) {

    public GitHubUpdateOrganizationPayload {
        description = requireMaxLengthIfPresent(description, 160, "description");
    }
}
