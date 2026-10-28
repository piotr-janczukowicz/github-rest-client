package io.github.piotr_janczukowicz.github.api.organizations;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/**
 * App Permissions.
 *
 * @param actions Actions
 * @param administration Administration
 * @param artifactMetadata Artifact metadata
 * @param attestations Attestations
 * @param checks Checks
 * @param codeQuality Code quality
 * @param codespaces Codespaces
 * @param contents Contents
 * @param dependabotSecrets Dependabot secrets
 * @param deployments Deployments
 * @param discussions Discussions
 * @param environments Environments
 * @param issues Issues
 * @param mergeQueues Merge queues
 * @param metadata Metadata
 * @param packages Packages
 * @param pages Pages
 * @param pullRequests Pull requests
 * @param repositoryCustomProperties Repository custom properties
 * @param repositoryHooks Repository hooks
 * @param repositoryProjects Repository projects
 * @param secretScanningAlerts Secret scanning alerts
 * @param secrets Secrets
 * @param securityEvents Security events
 * @param singleFile Single file
 * @param statuses Statuses
 * @param vulnerabilityAlerts Vulnerability alerts
 * @param workflows Workflows
 * @param customPropertiesForOrganizations Custom properties for organizations
 * @param members Members
 * @param organizationAdministration Organization administration
 * @param organizationCustomRoles Organization custom roles
 * @param organizationCustomOrgRoles Organization custom org roles
 * @param organizationCustomProperties Organization custom properties
 * @param organizationCopilotSeatManagement Organization copilot seat management
 * @param organizationCopilotAgentSettings Organization copilot agent settings
 * @param organizationCopilotRequests Organization copilot requests
 * @param organizationExternalPropertiesForRepos Organization external properties for repos
 * @param organizationAnnouncementBanners Organization announcement banners
 * @param organizationEvents Organization events
 * @param organizationHooks Organization hooks
 * @param organizationPersonalAccessTokens Organization personal access tokens
 * @param organizationPersonalAccessTokenRequests Organization personal access token requests
 * @param organizationPlan Organization plan
 * @param organizationProjects Organization projects
 * @param organizationPackages Organization packages
 * @param organizationSecrets Organization secrets
 * @param organizationSelfHostedRunners Organization self hosted runners
 * @param organizationUserBlocking Organization user blocking
 * @param emailAddresses Email addresses
 * @param followers Followers
 * @param gitSshKeys Git ssh keys
 * @param gpgKeys Gpg keys
 * @param interactionLimits Interaction limits
 * @param profile Profile
 * @param starring Starring
 * @param enterpriseCustomPropertiesForOrganizations Enterprise custom properties for organizations
 * @param enterpriseAdministration Enterprise administration
 * @param enterpriseOrganizationInstallationRepositories Enterprise organization installation repositories
 * @param enterpriseOrganizationInstallations Enterprise organization installations
 */
@RecordBuilder
@JsonInclude(JsonInclude.Include.NON_NULL)
@CatalogSchema("components/schemas/app-permissions")
public record GitHubAppPermissions(
        @Nullable GitHubAppPermissionReadWrite actions,
        @Nullable GitHubAppPermissionReadWrite administration,
        @Nullable GitHubAppPermissionReadWrite artifactMetadata,
        @Nullable GitHubAppPermissionReadWrite attestations,
        @Nullable GitHubAppPermissionReadWrite checks,
        @Nullable GitHubAppPermissionReadWrite codeQuality,
        @Nullable GitHubAppPermissionReadWrite codespaces,
        @Nullable GitHubAppPermissionReadWrite contents,
        @Nullable GitHubAppPermissionReadWrite dependabotSecrets,
        @Nullable GitHubAppPermissionReadWrite deployments,
        @Nullable GitHubAppPermissionReadWrite discussions,
        @Nullable GitHubAppPermissionReadWrite environments,
        @Nullable GitHubAppPermissionReadWrite issues,
        @Nullable GitHubAppPermissionReadWrite mergeQueues,
        @Nullable GitHubAppPermissionReadWrite metadata,
        @Nullable GitHubAppPermissionReadWrite packages,
        @Nullable GitHubAppPermissionReadWrite pages,
        @Nullable GitHubAppPermissionReadWrite pullRequests,
        @Nullable GitHubAppPermissionReadWrite repositoryCustomProperties,
        @Nullable GitHubAppPermissionReadWrite repositoryHooks,
        @Nullable GitHubAppPermissionReadWriteAdmin repositoryProjects,
        @Nullable GitHubAppPermissionReadWrite secretScanningAlerts,
        @Nullable GitHubAppPermissionReadWrite secrets,
        @Nullable GitHubAppPermissionReadWrite securityEvents,
        @Nullable GitHubAppPermissionReadWrite singleFile,
        @Nullable GitHubAppPermissionReadWrite statuses,
        @Nullable GitHubAppPermissionReadWrite vulnerabilityAlerts,
        @Nullable GitHubAppPermissionWrite workflows,
        @Nullable GitHubAppPermissionReadWrite customPropertiesForOrganizations,
        @Nullable GitHubAppPermissionReadWrite members,
        @Nullable GitHubAppPermissionReadWrite organizationAdministration,
        @Nullable GitHubAppPermissionReadWrite organizationCustomRoles,
        @Nullable GitHubAppPermissionReadWrite organizationCustomOrgRoles,
        @Nullable GitHubAppPermissionReadWriteAdmin organizationCustomProperties,
        @Nullable GitHubAppPermissionReadWrite organizationCopilotSeatManagement,
        @Nullable GitHubAppPermissionReadWrite organizationCopilotAgentSettings,
        @Nullable GitHubAppPermissionRead organizationCopilotRequests,
        @Nullable GitHubAppPermissionReadWriteAdmin organizationExternalPropertiesForRepos,
        @Nullable GitHubAppPermissionReadWrite organizationAnnouncementBanners,
        @Nullable GitHubAppPermissionRead organizationEvents,
        @Nullable GitHubAppPermissionReadWrite organizationHooks,
        @Nullable GitHubAppPermissionReadWrite organizationPersonalAccessTokens,
        @Nullable GitHubAppPermissionReadWrite organizationPersonalAccessTokenRequests,
        @Nullable GitHubAppPermissionRead organizationPlan,
        @Nullable GitHubAppPermissionReadWriteAdmin organizationProjects,
        @Nullable GitHubAppPermissionReadWrite organizationPackages,
        @Nullable GitHubAppPermissionReadWrite organizationSecrets,
        @Nullable GitHubAppPermissionReadWrite organizationSelfHostedRunners,
        @Nullable GitHubAppPermissionReadWrite organizationUserBlocking,
        @Nullable GitHubAppPermissionReadWrite emailAddresses,
        @Nullable GitHubAppPermissionReadWrite followers,
        @Nullable GitHubAppPermissionReadWrite gitSshKeys,
        @Nullable GitHubAppPermissionReadWrite gpgKeys,
        @Nullable GitHubAppPermissionReadWrite interactionLimits,
        @Nullable GitHubAppPermissionWrite profile,
        @Nullable GitHubAppPermissionReadWrite starring,
        @Nullable GitHubAppPermissionReadWriteAdmin enterpriseCustomPropertiesForOrganizations,
        @Nullable GitHubAppPermissionReadWrite enterpriseAdministration,
        @Nullable GitHubAppPermissionReadWrite enterpriseOrganizationInstallationRepositories,
        @Nullable GitHubAppPermissionReadWrite enterpriseOrganizationInstallations,
        @Nullable GitHubAppPermissionReadWrite enterpriseCustomProperties) {}
