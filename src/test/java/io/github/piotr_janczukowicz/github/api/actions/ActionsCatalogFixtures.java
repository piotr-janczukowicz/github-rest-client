package io.github.piotr_janczukowicz.github.api.actions;

import io.github.piotr_janczukowicz.github.api.GitHubDirection;
import io.github.piotr_janczukowicz.github.api.agents.AgentsCatalogFixtures;
import io.github.piotr_janczukowicz.github.api.checks.ChecksCatalogFixtures;
import io.github.piotr_janczukowicz.github.api.deployments.GitHubDeploymentReviewerType;
import io.github.piotr_janczukowicz.github.api.organizations.OrganizationsCatalogFixtures;
import io.github.piotr_janczukowicz.github.api.pullrequests.PullrequestsCatalogFixtures;
import io.github.piotr_janczukowicz.github.api.repositories.GitHubRepositoryRuleEnforcement;
import io.github.piotr_janczukowicz.github.api.repositories.GitHubRepositoryRulesetConditionsRepositoryIdTargetRepositoryId;
import io.github.piotr_janczukowicz.github.api.repositories.GitHubRepositoryRulesetConditionsRepositoryPropertySource;
import io.github.piotr_janczukowicz.github.api.repositories.GitHubRepositoryRulesetConditionsRepositoryPropertySpec;
import io.github.piotr_janczukowicz.github.api.repositories.GitHubRepositoryRulesetConditionsRepositoryPropertyTargetRepositoryProperty;
import io.github.piotr_janczukowicz.github.api.repositories.RepositoriesCatalogFixtures;
import io.github.piotr_janczukowicz.github.api.users.UsersCatalogFixtures;
import java.time.Instant;
import java.util.List;
import java.util.Map;

public final class ActionsCatalogFixtures {

    private ActionsCatalogFixtures() {}

    static GitHubActionsArtifactAndLogRetention actionsArtifactAndLogRetention() {
        return GitHubActionsArtifactAndLogRetentionBuilder.builder().days(1).build();
    }

    static GitHubActionsArtifactAndLogRetentionResponse actionsArtifactAndLogRetentionResponse() {
        return GitHubActionsArtifactAndLogRetentionResponseBuilder.builder()
                .days(1)
                .maximumAllowedDays(1)
                .build();
    }

    static GitHubActionsCache actionsCache() {
        return GitHubActionsCacheBuilder.builder()
                .id(1L)
                .ref("ref-sample")
                .key("key-sample")
                .version("version-sample")
                .lastAccessedAt(Instant.parse("2024-01-02T03:04:05Z"))
                .createdAt(Instant.parse("2024-01-02T03:04:05Z"))
                .sizeInBytes(1)
                .build();
    }

    static GitHubActionsCacheList actionsCacheList() {
        return GitHubActionsCacheListBuilder.builder()
                .totalCount(1)
                .actionsCaches(List.of(actionsCache()))
                .build();
    }

    static GitHubActionsCacheUsagePolicyForRepository actionsCacheUsagePolicyForRepository() {
        return GitHubActionsCacheUsagePolicyForRepositoryBuilder.builder()
                .repoCacheSizeLimitInGb(14)
                .build();
    }

    static GitHubActionsCacheUsagePolicyEnterprise actionsCacheUsagePolicyEnterprise() {
        return GitHubActionsCacheUsagePolicyEnterpriseBuilder.builder()
                .repoCacheSizeLimitInGb(10)
                .maxRepoCacheSizeLimitInGb(15)
                .build();
    }

    static GitHubActionsCacheRetentionLimitForEnterprise actionsCacheRetentionLimitForEnterprise() {
        return GitHubActionsCacheRetentionLimitForEnterpriseBuilder.builder()
                .maxCacheRetentionDays(1)
                .build();
    }

    static GitHubActionsCacheRetentionLimitForOrganization actionsCacheRetentionLimitForOrganization() {
        return GitHubActionsCacheRetentionLimitForOrganizationBuilder.builder()
                .maxCacheRetentionDays(1)
                .build();
    }

    static GitHubActionsCacheRetentionLimitForRepository actionsCacheRetentionLimitForRepository() {
        return GitHubActionsCacheRetentionLimitForRepositoryBuilder.builder()
                .maxCacheRetentionDays(1)
                .build();
    }

    static GitHubActionsCacheStorageLimitForEnterprise actionsCacheStorageLimitForEnterprise() {
        return GitHubActionsCacheStorageLimitForEnterpriseBuilder.builder()
                .maxCacheSizeGb(1)
                .build();
    }

    static GitHubActionsCacheStorageLimitForOrganization actionsCacheStorageLimitForOrganization() {
        return GitHubActionsCacheStorageLimitForOrganizationBuilder.builder()
                .maxCacheSizeGb(1)
                .build();
    }

    static GitHubActionsCacheStorageLimitForRepository actionsCacheStorageLimitForRepository() {
        return GitHubActionsCacheStorageLimitForRepositoryBuilder.builder()
                .maxCacheSizeGb(1)
                .build();
    }

    static GitHubActionsCacheUsageByRepository actionsCacheUsageByRepository() {
        return GitHubActionsCacheUsageByRepositoryBuilder.builder()
                .fullName("fullName-sample")
                .activeCachesSizeInBytes(1)
                .activeCachesCount(1)
                .build();
    }

    static GitHubActionsCacheUsageOrgEnterprise actionsCacheUsageOrgEnterprise() {
        return GitHubActionsCacheUsageOrgEnterpriseBuilder.builder()
                .totalActiveCachesCount(1)
                .totalActiveCachesSizeInBytes(1)
                .build();
    }

    static GitHubActionsCacheUsages actionsCacheUsages() {
        return GitHubActionsCacheUsagesBuilder.builder()
                .totalCount(1)
                .repositoryCacheUsages(List.of(actionsCacheUsageByRepository()))
                .build();
    }

    static GitHubActionsCanApprovePullRequestReviews actionsCanApprovePullRequestReviews() {
        return new GitHubActionsCanApprovePullRequestReviews(true);
    }

    static GitHubActionsCustomImageVersions actionsCustomImageVersions() {
        return GitHubActionsCustomImageVersionsBuilder.builder()
                .totalCount(1)
                .imageVersions(List.of(actionsHostedRunnerCustomImageVersion()))
                .build();
    }

    static GitHubActionsCustomImages actionsCustomImages() {
        return GitHubActionsCustomImagesBuilder.builder()
                .totalCount(1)
                .images(List.of(actionsHostedRunnerCustomImage()))
                .build();
    }

    static GitHubActionsCustomImageVersionsForEnterprise actionsCustomImageVersionsForEnterprise() {
        return GitHubActionsCustomImageVersionsForEnterpriseBuilder.builder()
                .totalCount(1)
                .imageVersions(List.of(actionsHostedRunnerCustomImageVersion()))
                .build();
    }

    static GitHubActionsCustomImagesForEnterprise actionsCustomImagesForEnterprise() {
        return GitHubActionsCustomImagesForEnterpriseBuilder.builder()
                .totalCount(1)
                .images(List.of(actionsHostedRunnerCustomImage()))
                .build();
    }

    static GitHubActionsEnabled actionsEnabled() {
        return new GitHubActionsEnabled(true);
    }

    static GitHubActionsEnterprisePermissions actionsEnterprisePermissions() {
        return GitHubActionsEnterprisePermissionsBuilder.builder()
                .enabledOrganizations(GitHubEnabledOrganizations.SELECTED)
                .selectedOrganizationsUrl("https://api.github.com/example")
                .allowedActions(GitHubAllowedActions.ALL)
                .selectedActionsUrl(selectedActionsUrl())
                .shaPinningRequired(shaPinningRequired())
                .build();
    }

    static GitHubActionsForkPrContributorApproval actionsForkPrContributorApproval() {
        return GitHubActionsForkPrContributorApprovalBuilder.builder()
                .approvalPolicy(GitHubForkPrContributorApprovalPolicy.FIRST_TIME_CONTRIBUTORS_NEW_TO_GITHUB)
                .build();
    }

    static GitHubActionsForkPrWorkflowsPrivateRepos actionsForkPrWorkflowsPrivateRepos() {
        return GitHubActionsForkPrWorkflowsPrivateReposBuilder.builder()
                .runWorkflowsFromForkPullRequests(true)
                .sendWriteTokensToWorkflows(true)
                .sendSecretsAndVariables(true)
                .requireApprovalForForkPrWorkflows(true)
                .build();
    }

    static GitHubActionsForkPrWorkflowsPrivateReposRequest actionsForkPrWorkflowsPrivateReposRequest() {
        return GitHubActionsForkPrWorkflowsPrivateReposRequestBuilder.builder()
                .runWorkflowsFromForkPullRequests(true)
                .sendWriteTokensToWorkflows(true)
                .sendSecretsAndVariables(true)
                .requireApprovalForForkPrWorkflows(true)
                .build();
    }

    static GitHubActionsGetDefaultWorkflowPermissions actionsGetDefaultWorkflowPermissions() {
        return GitHubActionsGetDefaultWorkflowPermissionsBuilder.builder()
                .defaultWorkflowPermissions(GitHubActionsDefaultWorkflowPermissions.READ)
                .canApprovePullRequestReviews(actionsCanApprovePullRequestReviews())
                .build();
    }

    static GitHubActionsHostedRunner actionsHostedRunner() {
        return GitHubActionsHostedRunnerBuilder.builder()
                .id(1)
                .name("name-sample")
                .runnerGroupId(1L)
                .imageDetails(actionsHostedRunnerPoolImage())
                .machineSizeDetails(actionsHostedRunnerMachineSpec())
                .status(GitHubHostedRunnerStatus.READY)
                .platform("platform-sample")
                .maximumRunners(1)
                .publicIpEnabled(true)
                .publicIps(List.of(publicIp()))
                .lastActiveOn(Instant.parse("2024-01-02T03:04:05Z"))
                .imageGen(true)
                .build();
    }

    static GitHubActionsHostedRunnerCuratedImage actionsHostedRunnerCuratedImage() {
        return GitHubActionsHostedRunnerCuratedImageBuilder.builder()
                .id("id-sample")
                .platform("platform-sample")
                .sizeGb(1)
                .displayName("displayName-sample")
                .source(GitHubHostedRunnerImageSource.GITHUB)
                .build();
    }

    static GitHubActionsHostedRunnerCustomImage actionsHostedRunnerCustomImage() {
        return GitHubActionsHostedRunnerCustomImageBuilder.builder()
                .id(1)
                .platform("platform-sample")
                .totalVersionsSize(1)
                .name("name-sample")
                .source("source-sample")
                .versionsCount(1)
                .latestVersion("latestVersion-sample")
                .state("state-sample")
                .build();
    }

    static GitHubActionsHostedRunnerCustomImageVersion actionsHostedRunnerCustomImageVersion() {
        return GitHubActionsHostedRunnerCustomImageVersionBuilder.builder()
                .version("version-sample")
                .state("state-sample")
                .sizeGb(1)
                .createdOn("createdOn-sample")
                .stateDetails("stateDetails-sample")
                .build();
    }

    static GitHubActionsHostedRunnerImages actionsHostedRunnerImages() {
        return GitHubActionsHostedRunnerImagesBuilder.builder()
                .totalCount(1)
                .images(List.of(actionsHostedRunnerCuratedImage()))
                .build();
    }

    static GitHubActionsHostedRunnerLimits actionsHostedRunnerLimits() {
        return GitHubActionsHostedRunnerLimitsBuilder.builder()
                .publicIps(actionsHostedRunnerLimitsPublicIps())
                .build();
    }

    static GitHubActionsHostedRunnerLimitsPublicIps actionsHostedRunnerLimitsPublicIps() {
        return GitHubActionsHostedRunnerLimitsPublicIpsBuilder.builder()
                .maximum(1)
                .currentUsage(1)
                .build();
    }

    static GitHubActionsHostedRunnerMachineSpec actionsHostedRunnerMachineSpec() {
        return GitHubActionsHostedRunnerMachineSpecBuilder.builder()
                .id("id-sample")
                .cpuCores(1)
                .memoryGb(1)
                .storageGb(1)
                .build();
    }

    static GitHubActionsHostedRunnerMachineSpecs actionsHostedRunnerMachineSpecs() {
        return GitHubActionsHostedRunnerMachineSpecsBuilder.builder()
                .totalCount(1)
                .machineSpecs(List.of(actionsHostedRunnerMachineSpec()))
                .build();
    }

    static GitHubActionsHostedRunnerPlatforms actionsHostedRunnerPlatforms() {
        return GitHubActionsHostedRunnerPlatformsBuilder.builder()
                .totalCount(1)
                .platforms(List.of("platforms-sample"))
                .build();
    }

    static GitHubActionsHostedRunnerPoolImage actionsHostedRunnerPoolImage() {
        return GitHubActionsHostedRunnerPoolImageBuilder.builder()
                .id("id-sample")
                .sizeGb(1)
                .displayName("displayName-sample")
                .source(GitHubHostedRunnerImageSource.GITHUB)
                .version("version-sample")
                .build();
    }

    static GitHubActionsHostedRunners actionsHostedRunners() {
        return GitHubActionsHostedRunnersBuilder.builder()
                .totalCount(1)
                .runners(List.of(actionsHostedRunner()))
                .build();
    }

    static GitHubActionsHostedRunnersForEnterprise actionsHostedRunnersForEnterprise() {
        return GitHubActionsHostedRunnersForEnterpriseBuilder.builder()
                .totalCount(1)
                .runners(List.of(actionsHostedRunner()))
                .build();
    }

    static GitHubActionsGitHubOwnedImagesForEnterprise actionsGitHubOwnedImagesForEnterprise() {
        return GitHubActionsGitHubOwnedImagesForEnterpriseBuilder.builder()
                .totalCount(1)
                .images(List.of(actionsHostedRunnerCuratedImage()))
                .build();
    }

    static GitHubActionsPartnerImagesForEnterprise actionsPartnerImagesForEnterprise() {
        return GitHubActionsPartnerImagesForEnterpriseBuilder.builder()
                .totalCount(1)
                .images(List.of(actionsHostedRunnerCuratedImage()))
                .build();
    }

    static GitHubActionsHostedRunnerMachineSpecsForEnterprise actionsHostedRunnerMachineSpecsForEnterprise() {
        return GitHubActionsHostedRunnerMachineSpecsForEnterpriseBuilder.builder()
                .totalCount(1)
                .machineSpecs(List.of(actionsHostedRunnerMachineSpec()))
                .build();
    }

    static GitHubActionsHostedRunnerPlatformsForEnterprise actionsHostedRunnerPlatformsForEnterprise() {
        return GitHubActionsHostedRunnerPlatformsForEnterpriseBuilder.builder()
                .totalCount(1)
                .platforms(List.of("platforms-sample"))
                .build();
    }

    static GitHubActionsOrganizationPermissions actionsOrganizationPermissions() {
        return GitHubActionsOrganizationPermissionsBuilder.builder()
                .enabledRepositories(GitHubEnabledRepositories.ALL)
                .selectedRepositoriesUrl("https://api.github.com/example")
                .allowedActions(GitHubAllowedActions.ALL)
                .selectedActionsUrl(selectedActionsUrl())
                .shaPinningRequired(shaPinningRequired())
                .build();
    }

    static GitHubActionsRepositoryPermissions actionsRepositoryPermissions() {
        return GitHubActionsRepositoryPermissionsBuilder.builder()
                .enabled(actionsEnabled())
                .allowedActions(GitHubAllowedActions.ALL)
                .selectedActionsUrl(selectedActionsUrl())
                .shaPinningRequired(shaPinningRequired())
                .build();
    }

    static GitHubActionsSecrets actionsSecrets() {
        return GitHubActionsSecretsBuilder.builder()
                .totalCount(1)
                .secrets(List.of(AgentsCatalogFixtures.actionsSecret()))
                .build();
    }

    static GitHubActionsSetDefaultWorkflowPermissions actionsSetDefaultWorkflowPermissions() {
        return GitHubActionsSetDefaultWorkflowPermissionsBuilder.builder()
                .defaultWorkflowPermissions(GitHubActionsDefaultWorkflowPermissions.READ)
                .canApprovePullRequestReviews(actionsCanApprovePullRequestReviews())
                .build();
    }

    static GitHubActionsVariables actionsVariables() {
        return GitHubActionsVariablesBuilder.builder()
                .totalCount(1)
                .variables(List.of(AgentsCatalogFixtures.actionsVariable()))
                .build();
    }

    static GitHubActionsWorkflowAccessToRepository actionsWorkflowAccessToRepository() {
        return GitHubActionsWorkflowAccessToRepositoryBuilder.builder()
                .accessLevel(GitHubWorkflowAccessLevel.NONE)
                .build();
    }

    static GitHubAddCustomLabelsToSelfHostedRunnerForOrgPayload addCustomLabelsToSelfHostedRunnerForOrgPayload() {
        return GitHubAddCustomLabelsToSelfHostedRunnerForOrgPayloadBuilder.builder()
                .labels(List.of("labels-sample"))
                .build();
    }

    static GitHubAddCustomLabelsToSelfHostedRunnerForRepoPayload addCustomLabelsToSelfHostedRunnerForRepoPayload() {
        return GitHubAddCustomLabelsToSelfHostedRunnerForRepoPayloadBuilder.builder()
                .labels(List.of("labels-sample"))
                .build();
    }

    static GitHubArtifact artifact() {
        return GitHubArtifactBuilder.builder()
                .id(1)
                .nodeId("nodeId-sample")
                .name("name-sample")
                .sizeInBytes(1)
                .url("https://api.github.com/example")
                .archiveDownloadUrl("https://api.github.com/example")
                .expired(true)
                .createdAt(Instant.parse("2024-01-02T03:04:05Z"))
                .expiresAt(Instant.parse("2024-01-02T03:04:05Z"))
                .updatedAt(Instant.parse("2024-01-02T03:04:05Z"))
                .digest("digest-sample")
                .workflowRun(artifactWorkflowRun())
                .build();
    }

    static GitHubArtifactWorkflowRun artifactWorkflowRun() {
        return GitHubArtifactWorkflowRunBuilder.builder()
                .id(1L)
                .repositoryId(1L)
                .headRepositoryId(1L)
                .headBranch("headBranch-sample")
                .headSha("0123456789abcdef0123456789abcdef01234567")
                .build();
    }

    static GitHubArtifacts artifacts() {
        return GitHubArtifactsBuilder.builder()
                .totalCount(1)
                .artifacts(List.of(artifact()))
                .build();
    }

    static GitHubAuthenticationToken authenticationToken() {
        return GitHubAuthenticationTokenBuilder.builder()
                .token("token-sample")
                .expiresAt(Instant.parse("2024-01-02T03:04:05Z"))
                .permissions(Map.of("key", "value"))
                .repositories(List.of(RepositoriesCatalogFixtures.teamEnvironmentRepository()))
                .singleFile("singleFile-sample")
                .repositorySelection(GitHubAuthenticationTokenRepositorySelection.ALL)
                .build();
    }

    static GitHubConcurrencyGroup concurrencyGroup() {
        return GitHubConcurrencyGroupBuilder.builder()
                .groupName("groupName-sample")
                .groupUrl("https://api.github.com/example")
                .totalCount(1)
                .groupMembers(List.of(concurrencyGroupMember()))
                .build();
    }

    static GitHubConcurrencyGroupList concurrencyGroupList() {
        return GitHubConcurrencyGroupListBuilder.builder()
                .totalCount(1)
                .concurrencyGroups(List.of(concurrencyGroupListEntry()))
                .build();
    }

    static GitHubConcurrencyGroupListEntry concurrencyGroupListEntry() {
        return GitHubConcurrencyGroupListEntryBuilder.builder()
                .groupName("groupName-sample")
                .groupUrl("https://api.github.com/example")
                .lastAcquiredAt(Instant.parse("2024-01-02T03:04:05Z"))
                .build();
    }

    static GitHubConcurrencyGroupMember concurrencyGroupMember() {
        return GitHubConcurrencyGroupMemberBuilder.builder()
                .runId(1)
                .runName("runName-sample")
                .runUrl("https://api.github.com/example")
                .runHtmlUrl("https://github.com/example")
                .jobId(1L)
                .jobName("jobName-sample")
                .jobUrl("https://api.github.com/example")
                .jobHtmlUrl("https://github.com/example")
                .status(GitHubConcurrencyGroupMemberStatus.IN_PROGRESS)
                .build();
    }

    static GitHubConcurrencyGroupRunList concurrencyGroupRunList() {
        return GitHubConcurrencyGroupRunListBuilder.builder()
                .totalCount(1)
                .concurrencyGroups(List.of(concurrencyGroupRunListEntry()))
                .build();
    }

    static GitHubConcurrencyGroupRunListEntry concurrencyGroupRunListEntry() {
        return GitHubConcurrencyGroupRunListEntryBuilder.builder()
                .groupName("groupName-sample")
                .groupUrl("https://api.github.com/example")
                .groupMembers(List.of(concurrencyGroupRunMember()))
                .build();
    }

    static GitHubConcurrencyGroupRunMember concurrencyGroupRunMember() {
        return GitHubConcurrencyGroupRunMemberBuilder.builder()
                .runId(1)
                .runName("runName-sample")
                .runUrl("https://api.github.com/example")
                .runHtmlUrl("https://github.com/example")
                .position(1)
                .positionUrl("https://api.github.com/example")
                .jobId(1L)
                .jobName("jobName-sample")
                .jobUrl("https://api.github.com/example")
                .jobHtmlUrl("https://github.com/example")
                .status(GitHubConcurrencyGroupMemberStatus.IN_PROGRESS)
                .build();
    }

    static GitHubCreateEnvironmentVariablePayload createEnvironmentVariablePayload() {
        return GitHubCreateEnvironmentVariablePayloadBuilder.builder()
                .name("name-sample")
                .value("value-sample")
                .build();
    }

    static GitHubCreateHostedRunnerForOrgPayload createHostedRunnerForOrgPayload() {
        return GitHubCreateHostedRunnerForOrgPayloadBuilder.builder()
                .name("name-sample")
                .image(createHostedRunnerImage())
                .size("size-sample")
                .runnerGroupId(1)
                .maximumRunners(1)
                .enableStaticIp(true)
                .imageGen(true)
                .build();
    }

    static GitHubCreateHostedRunnerImage createHostedRunnerImage() {
        return GitHubCreateHostedRunnerImageBuilder.builder()
                .id("id-sample")
                .source(GitHubHostedRunnerImageSource.GITHUB)
                .version("version-sample")
                .build();
    }

    static GitHubCreateHostedRunnerForEnterprisePayload createHostedRunnerForEnterprisePayload() {
        return GitHubCreateHostedRunnerForEnterprisePayloadBuilder.builder()
                .name("name-sample")
                .image(createHostedRunnerEnterpriseImage())
                .size("size-sample")
                .runnerGroupId(1)
                .maximumRunners(1)
                .enableStaticIp(true)
                .imageGen(true)
                .build();
    }

    static GitHubCreateHostedRunnerEnterpriseImage createHostedRunnerEnterpriseImage() {
        return GitHubCreateHostedRunnerEnterpriseImageBuilder.builder()
                .id("id-sample")
                .source(GitHubCreateHostedRunnerEnterpriseImageSource.GITHUB)
                .version("version-sample")
                .build();
    }

    static GitHubCreateOrUpdateEnvironmentSecretPayload createOrUpdateEnvironmentSecretPayload() {
        return GitHubCreateOrUpdateEnvironmentSecretPayloadBuilder.builder()
                .encryptedValue("YWJjZA==")
                .keyId("keyId-sample")
                .build();
    }

    static GitHubCreateOrUpdateOrgSecretPayload createOrUpdateOrgSecretPayload() {
        return GitHubCreateOrUpdateOrgSecretPayloadBuilder.builder()
                .encryptedValue("YWJjZA==")
                .keyId("keyId-sample")
                .visibility(GitHubOrgSecretVisibility.ALL)
                .selectedRepositoryIds(List.of(1L))
                .build();
    }

    static GitHubCreateOrUpdateRepoSecretPayload createOrUpdateRepoSecretPayload() {
        return GitHubCreateOrUpdateRepoSecretPayloadBuilder.builder()
                .encryptedValue("YWJjZA==")
                .keyId("keyId-sample")
                .build();
    }

    static GitHubCreateOrgVariablePayload createOrgVariablePayload() {
        return GitHubCreateOrgVariablePayloadBuilder.builder()
                .name("name-sample")
                .value("value-sample")
                .visibility(GitHubOrgVariableVisibility.ALL)
                .selectedRepositoryIds(List.of(1L))
                .build();
    }

    static GitHubCreateRepoVariablePayload createRepoVariablePayload() {
        return GitHubCreateRepoVariablePayloadBuilder.builder()
                .name("name-sample")
                .value("value-sample")
                .build();
    }

    static GitHubCreateSelfHostedRunnerGroupForOrgPayload createSelfHostedRunnerGroupForOrgPayload() {
        return GitHubCreateSelfHostedRunnerGroupForOrgPayloadBuilder.builder()
                .name("name-sample")
                .visibility(GitHubRunnerGroupVisibility.SELECTED)
                .selectedRepositoryIds(List.of(1L))
                .runners(List.of(1L))
                .allowsPublicRepositories(true)
                .restrictedToWorkflows(true)
                .selectedWorkflows(List.of("selectedWorkflows-sample"))
                .networkConfigurationId("networkConfigurationId-sample")
                .build();
    }

    static GitHubCreateWorkflowDispatchPayload createWorkflowDispatchPayload() {
        return GitHubCreateWorkflowDispatchPayloadBuilder.builder()
                .ref("ref-sample")
                .inputs(Map.of("key", "value"))
                .build();
    }

    static GitHubDeleteActionsCacheByKeyFilter deleteActionsCacheByKeyFilter() {
        return GitHubDeleteActionsCacheByKeyFilterBuilder.builder()
                .key("key-sample")
                .ref("ref-sample")
                .build();
    }

    static GitHubEnvironmentApprovals environmentApprovals() {
        return GitHubEnvironmentApprovalsBuilder.builder()
                .environments(List.of(environmentApprovalsEnvironment()))
                .state(GitHubEnvironmentApprovalState.APPROVED)
                .user(UsersCatalogFixtures.octocat())
                .comment("comment-sample")
                .build();
    }

    static GitHubEnvironmentApprovalsEnvironment environmentApprovalsEnvironment() {
        return GitHubEnvironmentApprovalsEnvironmentBuilder.builder()
                .id(1L)
                .nodeId("nodeId-sample")
                .name("name-sample")
                .url("https://api.github.com/example")
                .htmlUrl("https://github.com/example")
                .createdAt(Instant.parse("2024-01-02T03:04:05Z"))
                .updatedAt(Instant.parse("2024-01-02T03:04:05Z"))
                .build();
    }

    static GitHubGenerateRunnerJitconfigForOrgPayload generateRunnerJitconfigForOrgPayload() {
        return GitHubGenerateRunnerJitconfigForOrgPayloadBuilder.builder()
                .name("name-sample")
                .runnerGroupId(1)
                .labels(List.of("labels-sample"))
                .workFolder("workFolder-sample")
                .build();
    }

    static GitHubGenerateRunnerJitconfigForRepoPayload generateRunnerJitconfigForRepoPayload() {
        return GitHubGenerateRunnerJitconfigForRepoPayloadBuilder.builder()
                .name("name-sample")
                .runnerGroupId(1)
                .labels(List.of("labels-sample"))
                .workFolder("workFolder-sample")
                .build();
    }

    static GitHubGetActionsCacheListFilter getActionsCacheListFilter() {
        return GitHubGetActionsCacheListFilterBuilder.builder()
                .ref("ref-sample")
                .key("key-sample")
                .sort(GitHubActionsCacheListSort.CREATED_AT)
                .direction(GitHubDirection.ASC)
                .build();
    }

    static GitHubGetConcurrencyGroupForRepositoryFilter getConcurrencyGroupForRepositoryFilter() {
        return GitHubGetConcurrencyGroupForRepositoryFilterBuilder.builder()
                .aheadOfRun(1)
                .aheadOfJob(1)
                .build();
    }

    static GitHubGetWorkflowRunAttemptFilter getWorkflowRunAttemptFilter() {
        return GitHubGetWorkflowRunAttemptFilterBuilder.builder()
                .excludePullRequests(true)
                .build();
    }

    static GitHubGetWorkflowRunFilter getWorkflowRunFilter() {
        return GitHubGetWorkflowRunFilterBuilder.builder()
                .excludePullRequests(true)
                .build();
    }

    static GitHubJob job() {
        return GitHubJobBuilder.builder()
                .id(1L)
                .runId(1L)
                .runUrl("https://api.github.com/example")
                .runAttempt(1)
                .nodeId("nodeId-sample")
                .headSha("0123456789abcdef0123456789abcdef01234567")
                .url("https://api.github.com/example")
                .htmlUrl("https://github.com/example")
                .status(GitHubJobStatus.QUEUED)
                .conclusion(GitHubJobConclusion.SUCCESS)
                .createdAt(Instant.parse("2024-01-02T03:04:05Z"))
                .startedAt(Instant.parse("2024-01-02T03:04:05Z"))
                .completedAt(Instant.parse("2024-01-02T03:04:05Z"))
                .name("name-sample")
                .steps(List.of(jobStep()))
                .checkRunUrl("https://api.github.com/example")
                .labels(List.of("labels-sample"))
                .runnerId(1L)
                .runnerName("runnerName-sample")
                .runnerGroupId(1L)
                .runnerGroupName("runnerGroupName-sample")
                .workflowName("workflowName-sample")
                .headBranch("headBranch-sample")
                .build();
    }

    static GitHubJobStep jobStep() {
        return GitHubJobStepBuilder.builder()
                .status(GitHubJobStepStatus.QUEUED)
                .conclusion("conclusion-sample")
                .name("name-sample")
                .number(1)
                .startedAt(Instant.parse("2024-01-02T03:04:05Z"))
                .completedAt(Instant.parse("2024-01-02T03:04:05Z"))
                .build();
    }

    static GitHubJobs jobs() {
        return GitHubJobsBuilder.builder().totalCount(1).jobs(List.of(job())).build();
    }

    static GitHubListArtifactsForRepoFilter listArtifactsForRepoFilter() {
        return GitHubListArtifactsForRepoFilterBuilder.builder()
                .name("name-sample")
                .build();
    }

    static GitHubListJobsForWorkflowRunFilter listJobsForWorkflowRunFilter() {
        return GitHubListJobsForWorkflowRunFilterBuilder.builder()
                .filter(GitHubJobFilterMode.LATEST)
                .build();
    }

    static GitHubListSelfHostedRunnerGroupsForOrgFilter listSelfHostedRunnerGroupsForOrgFilter() {
        return GitHubListSelfHostedRunnerGroupsForOrgFilterBuilder.builder()
                .visibleToRepository("visibleToRepository-sample")
                .build();
    }

    static GitHubListSelfHostedRunnerGroupsForEnterpriseFilter listSelfHostedRunnerGroupsForEnterpriseFilter() {
        return GitHubListSelfHostedRunnerGroupsForEnterpriseFilterBuilder.builder()
                .visibleToOrganization("visibleToOrganization-sample")
                .build();
    }

    static GitHubListSelfHostedRunnersForOrgFilter listSelfHostedRunnersForOrgFilter() {
        return GitHubListSelfHostedRunnersForOrgFilterBuilder.builder()
                .name("name-sample")
                .build();
    }

    static GitHubListSelfHostedRunnersForRepoFilter listSelfHostedRunnersForRepoFilter() {
        return GitHubListSelfHostedRunnersForRepoFilterBuilder.builder()
                .name("name-sample")
                .build();
    }

    static GitHubListWorkflowRunArtifactsFilter listWorkflowRunArtifactsFilter() {
        return GitHubListWorkflowRunArtifactsFilterBuilder.builder()
                .name("name-sample")
                .direction(GitHubDirection.ASC)
                .build();
    }

    static GitHubListWorkflowRunsFilter listWorkflowRunsFilter() {
        return GitHubListWorkflowRunsFilterBuilder.builder()
                .actor("actor-sample")
                .branch("branch-sample")
                .event("event-sample")
                .status(GitHubWorkflowRunStatus.COMPLETED)
                .created(Instant.parse("2024-01-02T03:04:05Z"))
                .excludePullRequests(true)
                .checkSuiteId(1L)
                .headSha("0123456789abcdef0123456789abcdef01234567")
                .build();
    }

    static GitHubListWorkflowRunsForRepoFilter listWorkflowRunsForRepoFilter() {
        return GitHubListWorkflowRunsForRepoFilterBuilder.builder()
                .actor("actor-sample")
                .branch("branch-sample")
                .event("event-sample")
                .status(GitHubWorkflowRunStatus.COMPLETED)
                .created(Instant.parse("2024-01-02T03:04:05Z"))
                .excludePullRequests(true)
                .checkSuiteId(1L)
                .headSha("0123456789abcdef0123456789abcdef01234567")
                .build();
    }

    static GitHubOidcCustomPropertyInclusion oidcCustomPropertyInclusion() {
        return GitHubOidcCustomPropertyInclusionBuilder.builder()
                .customPropertyName("customPropertyName-sample")
                .inclusionSource(GitHubOidcCustomPropertyInclusionSource.ORGANIZATION)
                .build();
    }

    static GitHubOidcCustomPropertyInclusionInput oidcCustomPropertyInclusionInput() {
        return GitHubOidcCustomPropertyInclusionInputBuilder.builder()
                .customPropertyName("customPropertyName-sample")
                .build();
    }

    static GitHubOidcCustomSub oidcCustomSub() {
        return GitHubOidcCustomSubBuilder.builder()
                .includeClaimKeys(List.of("includeClaimKeys-sample"))
                .useImmutableSubject(true)
                .build();
    }

    static GitHubOidcCustomSubRepo oidcCustomSubRepo() {
        return GitHubOidcCustomSubRepoBuilder.builder()
                .useDefault(true)
                .includeClaimKeys(List.of("includeClaimKeys-sample"))
                .useImmutableSubject(true)
                .subClaimPrefix("subClaimPrefix-sample")
                .build();
    }

    static GitHubOrganizationActionsSecrets organizationActionsSecrets() {
        return GitHubOrganizationActionsSecretsBuilder.builder()
                .totalCount(1)
                .secrets(List.of(OrganizationsCatalogFixtures.organizationActionsSecret()))
                .build();
    }

    static GitHubOrganizationActionsVariables organizationActionsVariables() {
        return GitHubOrganizationActionsVariablesBuilder.builder()
                .totalCount(1)
                .variables(List.of(OrganizationsCatalogFixtures.organizationActionsVariable()))
                .build();
    }

    static GitHubPendingDeployment pendingDeployment() {
        return GitHubPendingDeploymentBuilder.builder()
                .environment(pendingDeploymentEnvironment())
                .waitTimer(1)
                .waitTimerStartedAt(Instant.parse("2024-01-02T03:04:05Z"))
                .currentUserCanApprove(true)
                .reviewers(List.of(pendingDeploymentReviewerEntry()))
                .build();
    }

    static GitHubPendingDeploymentEnvironment pendingDeploymentEnvironment() {
        return GitHubPendingDeploymentEnvironmentBuilder.builder()
                .id(1L)
                .nodeId("nodeId-sample")
                .name("name-sample")
                .url("https://api.github.com/example")
                .htmlUrl("https://github.com/example")
                .build();
    }

    static GitHubPendingDeploymentReviewerEntry pendingDeploymentReviewerEntry() {
        return GitHubPendingDeploymentReviewerEntryBuilder.builder()
                .type(GitHubDeploymentReviewerType.USER)
                .reviewer(GitHubPendingDeploymentReviewer.ofUser(UsersCatalogFixtures.octocat()))
                .build();
    }

    static GitHubPublicIp publicIp() {
        return GitHubPublicIpBuilder.builder()
                .enabled(true)
                .prefix("prefix-sample")
                .length(1)
                .build();
    }

    static GitHubReRunJobForWorkflowRunPayload reRunJobForWorkflowRunPayload() {
        return GitHubReRunJobForWorkflowRunPayloadBuilder.builder()
                .enableDebugLogging(true)
                .enableDebugger(true)
                .build();
    }

    static GitHubReRunWorkflowFailedJobsPayload reRunWorkflowFailedJobsPayload() {
        return GitHubReRunWorkflowFailedJobsPayloadBuilder.builder()
                .enableDebugLogging(true)
                .build();
    }

    static GitHubReRunWorkflowPayload reRunWorkflowPayload() {
        return GitHubReRunWorkflowPayloadBuilder.builder()
                .enableDebugLogging(true)
                .build();
    }

    static GitHubReferencedWorkflow referencedWorkflow() {
        return GitHubReferencedWorkflowBuilder.builder()
                .path("path-sample")
                .sha("0123456789abcdef0123456789abcdef01234567")
                .ref("ref-sample")
                .build();
    }

    static GitHubReviewCustomGatesCommentRequired reviewCustomGatesCommentRequired() {
        return GitHubReviewCustomGatesCommentRequiredBuilder.builder()
                .environmentName("environmentName-sample")
                .comment("comment-sample")
                .build();
    }

    static GitHubReviewCustomGatesForRunPayload reviewCustomGatesForRunPayload() {
        return GitHubReviewCustomGatesForRunPayloadBuilder.builder()
                .environmentName("environmentName-sample")
                .state(GitHubReviewCustomGatesState.APPROVED)
                .comment("comment-sample")
                .build();
    }

    static GitHubReviewCustomGatesStateRequired reviewCustomGatesStateRequired() {
        return GitHubReviewCustomGatesStateRequiredBuilder.builder()
                .environmentName("environmentName-sample")
                .state(GitHubReviewCustomGatesState.APPROVED)
                .comment("comment-sample")
                .build();
    }

    static GitHubReviewPendingDeploymentsForRunPayload reviewPendingDeploymentsForRunPayload() {
        return GitHubReviewPendingDeploymentsForRunPayloadBuilder.builder()
                .environmentIds(List.of(1L))
                .state(GitHubReviewCustomGatesState.APPROVED)
                .comment("comment-sample")
                .build();
    }

    static GitHubRunner runner() {
        return GitHubRunnerBuilder.builder()
                .id(1)
                .runnerGroupId(1L)
                .name("name-sample")
                .os("os-sample")
                .status("status-sample")
                .busy(true)
                .labels(List.of(runnerLabel()))
                .ephemeral(true)
                .version("version-sample")
                .build();
    }

    static GitHubRunnerApplication runnerApplication() {
        return GitHubRunnerApplicationBuilder.builder()
                .os("os-sample")
                .architecture("architecture-sample")
                .downloadUrl("https://api.github.com/example")
                .filename("filename-sample")
                .tempDownloadToken("tempDownloadToken-sample")
                .sha256Checksum("sha256Checksum-sample")
                .build();
    }

    static GitHubRunnerGroupHostedRunners runnerGroupHostedRunners() {
        return GitHubRunnerGroupHostedRunnersBuilder.builder()
                .totalCount(1)
                .runners(List.of(actionsHostedRunner()))
                .build();
    }

    static GitHubRunnerGroupRepositories runnerGroupRepositories() {
        return GitHubRunnerGroupRepositoriesBuilder.builder()
                .totalCount(1)
                .repositories(List.of(RepositoriesCatalogFixtures.helloWorldMinimalRepository()))
                .build();
    }

    static GitHubRunnerGroupRunners runnerGroupRunners() {
        return GitHubRunnerGroupRunnersBuilder.builder()
                .totalCount(1)
                .runners(List.of(runner()))
                .build();
    }

    static GitHubRunnerGroups runnerGroups() {
        return GitHubRunnerGroupsBuilder.builder()
                .totalCount(1)
                .runnerGroups(List.of(runnerGroupsOrg()))
                .build();
    }

    static GitHubRunnerGroupsOrg runnerGroupsOrg() {
        return GitHubRunnerGroupsOrgBuilder.builder()
                .id(1.0)
                .name("name-sample")
                .visibility("visibility-sample")
                .isDefault(true)
                .selectedRepositoriesUrl("https://api.github.com/example")
                .runnersUrl("https://api.github.com/example")
                .hostedRunnersUrl("https://api.github.com/example")
                .networkConfigurationId("networkConfigurationId-sample")
                .inherited(true)
                .inheritedAllowsPublicRepositories(true)
                .allowsPublicRepositories(true)
                .workflowRestrictionsReadOnly(true)
                .restrictedToWorkflows(true)
                .selectedWorkflows(List.of("selectedWorkflows-sample"))
                .build();
    }

    static GitHubRunnerGroupsEnterprise runnerGroupsEnterprise() {
        return GitHubRunnerGroupsEnterpriseBuilder.builder()
                .id(1.0)
                .name("name-sample")
                .visibility("visibility-sample")
                .isDefault(true)
                .selectedOrganizationsUrl("https://api.github.com/example")
                .runnersUrl("https://api.github.com/example")
                .hostedRunnersUrl("https://api.github.com/example")
                .networkConfigurationId("networkConfigurationId-sample")
                .allowsPublicRepositories(true)
                .workflowRestrictionsReadOnly(true)
                .restrictedToWorkflows(true)
                .selectedWorkflows(List.of("selectedWorkflows-sample"))
                .build();
    }

    static GitHubRunnerGroupsForEnterprise runnerGroupsForEnterprise() {
        return GitHubRunnerGroupsForEnterpriseBuilder.builder()
                .totalCount(1)
                .runnerGroups(List.of(runnerGroupsEnterprise()))
                .build();
    }

    static GitHubRunnerGroupOrganizationsForEnterprise runnerGroupOrganizationsForEnterprise() {
        return GitHubRunnerGroupOrganizationsForEnterpriseBuilder.builder()
                .totalCount(1)
                .organizations(List.of(OrganizationsCatalogFixtures.organizationSimple()))
                .build();
    }

    static GitHubRunnerGroupRunnersForEnterprise runnerGroupRunnersForEnterprise() {
        return GitHubRunnerGroupRunnersForEnterpriseBuilder.builder()
                .totalCount(1)
                .runners(List.of(runner()))
                .build();
    }

    static GitHubRunnerJitConfig runnerJitConfig() {
        return GitHubRunnerJitConfigBuilder.builder()
                .runner(runner())
                .encodedJitConfig("encodedJitConfig-sample")
                .build();
    }

    static GitHubRunnerLabel runnerLabel() {
        return GitHubRunnerLabelBuilder.builder()
                .id(1L)
                .name("name-sample")
                .type(GitHubRunnerLabelType.READ_ONLY)
                .build();
    }

    static GitHubRunnerLabels runnerLabels() {
        return GitHubRunnerLabelsBuilder.builder()
                .totalCount(1)
                .labels(List.of(runnerLabel()))
                .build();
    }

    static GitHubRunners runners() {
        return GitHubRunnersBuilder.builder()
                .totalCount(1)
                .runners(List.of(runner()))
                .build();
    }

    static GitHubSelectedActions selectedActions() {
        return GitHubSelectedActionsBuilder.builder()
                .githubOwnedAllowed(true)
                .verifiedAllowed(true)
                .patternsAllowed(List.of("patternsAllowed-sample"))
                .build();
    }

    static GitHubSelectedActionsUrl selectedActionsUrl() {
        return new GitHubSelectedActionsUrl("value-sample");
    }

    static GitHubSelectedOrganizationsEnabledActions selectedOrganizationsEnabledActions() {
        return GitHubSelectedOrganizationsEnabledActionsBuilder.builder()
                .totalCount(1)
                .organizations(List.of(OrganizationsCatalogFixtures.organizationSimple()))
                .build();
    }

    static GitHubSelectedRepositories selectedRepositories() {
        return GitHubSelectedRepositoriesBuilder.builder()
                .totalCount(1)
                .repositories(List.of(RepositoriesCatalogFixtures.helloWorldMinimalRepository()))
                .build();
    }

    static GitHubSelectedRepositoriesEnabledActions selectedRepositoriesEnabledActions() {
        return GitHubSelectedRepositoriesEnabledActionsBuilder.builder()
                .totalCount(1)
                .repositories(List.of(RepositoriesCatalogFixtures.teamEnvironmentRepository()))
                .build();
    }

    static GitHubSelectedRepositoriesSelfHostedRunners selectedRepositoriesSelfHostedRunners() {
        return GitHubSelectedRepositoriesSelfHostedRunnersBuilder.builder()
                .totalCount(1)
                .repositories(List.of(RepositoriesCatalogFixtures.teamEnvironmentRepository()))
                .build();
    }

    static GitHubSelfHostedRunnersPermissionsForEnterprise selfHostedRunnersPermissionsForEnterprise() {
        return GitHubSelfHostedRunnersPermissionsForEnterpriseBuilder.builder()
                .disableSelfHostedRunnersForAllOrgs(true)
                .build();
    }

    static GitHubSelfHostedRunnersSettings selfHostedRunnersSettings() {
        return GitHubSelfHostedRunnersSettingsBuilder.builder()
                .enabledRepositories(GitHubSelfHostedRunnersEnabledRepositories.ALL)
                .selectedRepositoriesUrl("https://api.github.com/example")
                .build();
    }

    static GitHubSetCustomLabelsForSelfHostedRunnerForOrgPayload replaceCustomLabelsForSelfHostedRunnerForOrgPayload() {
        return GitHubSetCustomLabelsForSelfHostedRunnerForOrgPayloadBuilder.builder()
                .labels(List.of("labels-sample"))
                .build();
    }

    static GitHubSetCustomLabelsForSelfHostedRunnerForRepoPayload
            replaceCustomLabelsForSelfHostedRunnerForRepoPayload() {
        return GitHubSetCustomLabelsForSelfHostedRunnerForRepoPayloadBuilder.builder()
                .labels(List.of("labels-sample"))
                .build();
    }

    static GitHubSetCustomOidcSubClaimForRepoPayload setCustomOidcSubClaimForRepoPayload() {
        return GitHubSetCustomOidcSubClaimForRepoPayloadBuilder.builder()
                .useDefault(true)
                .includeClaimKeys(List.of("includeClaimKeys-sample"))
                .useImmutableSubject(true)
                .build();
    }

    static GitHubSetGithubActionsPermissionsEnterprisePayload setGithubActionsPermissionsEnterprisePayload() {
        return GitHubSetGithubActionsPermissionsEnterprisePayloadBuilder.builder()
                .enabledOrganizations(GitHubEnabledOrganizations.ALL)
                .allowedActions(GitHubAllowedActions.ALL)
                .shaPinningRequired(shaPinningRequired())
                .build();
    }

    static GitHubSetGithubActionsPermissionsOrganizationPayload setGithubActionsPermissionsOrganizationPayload() {
        return GitHubSetGithubActionsPermissionsOrganizationPayloadBuilder.builder()
                .enabledRepositories(GitHubEnabledRepositories.ALL)
                .allowedActions(GitHubAllowedActions.ALL)
                .shaPinningRequired(shaPinningRequired())
                .build();
    }

    static GitHubSetGithubActionsPermissionsRepositoryPayload setGithubActionsPermissionsRepositoryPayload() {
        return GitHubSetGithubActionsPermissionsRepositoryPayloadBuilder.builder()
                .enabled(true)
                .allowedActions(GitHubAllowedActions.ALL)
                .shaPinningRequired(shaPinningRequired())
                .build();
    }

    static GitHubSetRepoAccessToSelfHostedRunnerGroupInOrgPayload setRepoAccessToSelfHostedRunnerGroupInOrgPayload() {
        return GitHubSetRepoAccessToSelfHostedRunnerGroupInOrgPayloadBuilder.builder()
                .selectedRepositoryIds(List.of(1L))
                .build();
    }

    static GitHubSetSelectedReposForOrgSecretPayload setSelectedReposForOrgSecretPayload() {
        return GitHubSetSelectedReposForOrgSecretPayloadBuilder.builder()
                .selectedRepositoryIds(List.of(1L))
                .build();
    }

    static GitHubSetSelectedReposForOrgVariablePayload setSelectedReposForOrgVariablePayload() {
        return GitHubSetSelectedReposForOrgVariablePayloadBuilder.builder()
                .selectedRepositoryIds(List.of(1L))
                .build();
    }

    static GitHubSetSelectedRepositoriesEnabledGithubActionsOrganizationPayload
            setSelectedRepositoriesEnabledGithubActionsOrganizationPayload() {
        return GitHubSetSelectedRepositoriesEnabledGithubActionsOrganizationPayloadBuilder.builder()
                .selectedRepositoryIds(List.of(1L))
                .build();
    }

    static GitHubSetSelectedRepositoriesSelfHostedRunnersOrganizationPayload
            setSelectedRepositoriesSelfHostedRunnersOrganizationPayload() {
        return GitHubSetSelectedRepositoriesSelfHostedRunnersOrganizationPayloadBuilder.builder()
                .selectedRepositoryIds(List.of(1L))
                .build();
    }

    static GitHubSetSelectedOrganizationsEnabledGithubActionsEnterprisePayload
            setSelectedOrganizationsEnabledGithubActionsEnterprisePayload() {
        return GitHubSetSelectedOrganizationsEnabledGithubActionsEnterprisePayloadBuilder.builder()
                .selectedOrganizationIds(List.of(1L))
                .build();
    }

    static GitHubSetSelfHostedRunnersInGroupForOrgPayload setSelfHostedRunnersInGroupForOrgPayload() {
        return GitHubSetSelfHostedRunnersInGroupForOrgPayloadBuilder.builder()
                .runners(List.of(1L))
                .build();
    }

    static GitHubSetSelfHostedRunnersPermissionsEnterprisePayload setSelfHostedRunnersPermissionsEnterprisePayload() {
        return GitHubSetSelfHostedRunnersPermissionsEnterprisePayloadBuilder.builder()
                .disableSelfHostedRunnersForAllOrgs(true)
                .build();
    }

    static GitHubSetSelfHostedRunnersPermissionsOrganizationPayload
            setSelfHostedRunnersPermissionsOrganizationPayload() {
        return GitHubSetSelfHostedRunnersPermissionsOrganizationPayloadBuilder.builder()
                .enabledRepositories(GitHubSelfHostedRunnersEnabledRepositories.ALL)
                .build();
    }

    static GitHubShaPinningRequired shaPinningRequired() {
        return new GitHubShaPinningRequired(true);
    }

    static GitHubUpdateEnvironmentVariablePayload updateEnvironmentVariablePayload() {
        return GitHubUpdateEnvironmentVariablePayloadBuilder.builder()
                .name("name-sample")
                .value("value-sample")
                .build();
    }

    static GitHubUpdateHostedRunnerForOrgPayload updateHostedRunnerForOrgPayload() {
        return GitHubUpdateHostedRunnerForOrgPayloadBuilder.builder()
                .name("name-sample")
                .runnerGroupId(1L)
                .maximumRunners(1)
                .enableStaticIp(true)
                .size("size-sample")
                .imageSource(GitHubHostedRunnerImageSource.GITHUB)
                .imageId("imageId-sample")
                .imageVersion("imageVersion-sample")
                .imageGen(true)
                .build();
    }

    static GitHubUpdateHostedRunnerForEnterprisePayload updateHostedRunnerForEnterprisePayload() {
        return GitHubUpdateHostedRunnerForEnterprisePayloadBuilder.builder()
                .name("name-sample")
                .runnerGroupId(1L)
                .maximumRunners(1)
                .enableStaticIp(true)
                .size("size-sample")
                .imageSource(GitHubUpdateHostedRunnerEnterpriseImageSource.GITHUB)
                .imageId("imageId-sample")
                .imageVersion("imageVersion-sample")
                .imageGen(true)
                .build();
    }

    static GitHubUpdateOidcCustomSubTemplateForOrgPayload updateOidcCustomSubTemplateForOrgPayload() {
        return GitHubUpdateOidcCustomSubTemplateForOrgPayloadBuilder.builder()
                .includeClaimKeys(List.of("includeClaimKeys-sample"))
                .useImmutableSubject(true)
                .build();
    }

    static GitHubUpdateOrgVariablePayload updateOrgVariablePayload() {
        return GitHubUpdateOrgVariablePayloadBuilder.builder()
                .name("name-sample")
                .value("value-sample")
                .visibility(GitHubOrgVariableVisibility.ALL)
                .selectedRepositoryIds(List.of(1L))
                .build();
    }

    static GitHubUpdateRepoVariablePayload updateRepoVariablePayload() {
        return GitHubUpdateRepoVariablePayloadBuilder.builder()
                .name("name-sample")
                .value("value-sample")
                .build();
    }

    static GitHubCreateSelfHostedRunnerGroupForEnterprisePayload createSelfHostedRunnerGroupForEnterprisePayload() {
        return GitHubCreateSelfHostedRunnerGroupForEnterprisePayloadBuilder.builder()
                .name("name-sample")
                .visibility(GitHubRunnerGroupVisibilityForEnterprise.SELECTED)
                .selectedOrganizationIds(List.of(1L))
                .runners(List.of(1L))
                .allowsPublicRepositories(true)
                .restrictedToWorkflows(true)
                .selectedWorkflows(List.of("selectedWorkflows-sample"))
                .networkConfigurationId("networkConfigurationId-sample")
                .build();
    }

    static GitHubUpdateSelfHostedRunnerGroupForEnterprisePayload updateSelfHostedRunnerGroupForEnterprisePayload() {
        return GitHubUpdateSelfHostedRunnerGroupForEnterprisePayloadBuilder.builder()
                .name("name-sample")
                .visibility(GitHubRunnerGroupVisibilityForEnterprise.SELECTED)
                .allowsPublicRepositories(true)
                .restrictedToWorkflows(true)
                .selectedWorkflows(List.of("selectedWorkflows-sample"))
                .networkConfigurationId("networkConfigurationId-sample")
                .build();
    }

    static GitHubSetOrgAccessToSelfHostedRunnerGroupInEnterprisePayload
            setOrgAccessToSelfHostedRunnerGroupInEnterprisePayload() {
        return GitHubSetOrgAccessToSelfHostedRunnerGroupInEnterprisePayloadBuilder.builder()
                .selectedOrganizationIds(List.of(1L))
                .build();
    }

    static GitHubSetSelfHostedRunnersInGroupForEnterprisePayload setSelfHostedRunnersInGroupForEnterprisePayload() {
        return GitHubSetSelfHostedRunnersInGroupForEnterprisePayloadBuilder.builder()
                .runners(List.of(1L))
                .build();
    }

    static GitHubUpdateSelfHostedRunnerGroupForOrgPayload updateSelfHostedRunnerGroupForOrgPayload() {
        return GitHubUpdateSelfHostedRunnerGroupForOrgPayloadBuilder.builder()
                .name("name-sample")
                .visibility(GitHubRunnerGroupVisibility.SELECTED)
                .allowsPublicRepositories(true)
                .restrictedToWorkflows(true)
                .selectedWorkflows(List.of("selectedWorkflows-sample"))
                .networkConfigurationId("networkConfigurationId-sample")
                .build();
    }

    static GitHubWorkflow workflow() {
        return GitHubWorkflowBuilder.builder()
                .id(1)
                .nodeId("nodeId-sample")
                .name("name-sample")
                .path("path-sample")
                .state(GitHubWorkflowState.ACTIVE)
                .createdAt(Instant.parse("2024-01-02T03:04:05Z"))
                .updatedAt(Instant.parse("2024-01-02T03:04:05Z"))
                .url("https://api.github.com/example")
                .htmlUrl("https://github.com/example")
                .badgeUrl("https://api.github.com/example")
                .deletedAt(Instant.parse("2024-01-02T03:04:05Z"))
                .build();
    }

    static GitHubWorkflowDispatchResponse workflowDispatchResponse() {
        return GitHubWorkflowDispatchResponseBuilder.builder()
                .workflowRunId(workflowRunId())
                .runUrl("https://api.github.com/example")
                .htmlUrl("https://github.com/example")
                .build();
    }

    static GitHubWorkflowRun workflowRun() {
        return GitHubWorkflowRunBuilder.builder()
                .id(1L)
                .name("name-sample")
                .nodeId("nodeId-sample")
                .checkSuiteId(1L)
                .checkSuiteNodeId("checkSuiteNodeId-sample")
                .headBranch("headBranch-sample")
                .headSha("0123456789abcdef0123456789abcdef01234567")
                .path("path-sample")
                .runNumber(1)
                .runAttempt(1)
                .referencedWorkflows(List.of(referencedWorkflow()))
                .event("event-sample")
                .status("status-sample")
                .conclusion("conclusion-sample")
                .workflowId(1)
                .url("https://api.github.com/example")
                .htmlUrl("https://github.com/example")
                .pullRequests(List.of(PullrequestsCatalogFixtures.pullRequestMinimal()))
                .createdAt(Instant.parse("2024-01-02T03:04:05Z"))
                .updatedAt(Instant.parse("2024-01-02T03:04:05Z"))
                .actor(UsersCatalogFixtures.octocat())
                .triggeringActor(UsersCatalogFixtures.octocat())
                .runStartedAt(Instant.parse("2024-01-02T03:04:05Z"))
                .jobsUrl("https://api.github.com/example")
                .logsUrl("https://api.github.com/example")
                .checkSuiteUrl("https://api.github.com/example")
                .artifactsUrl("https://api.github.com/example")
                .cancelUrl("https://api.github.com/example")
                .rerunUrl("https://api.github.com/example")
                .previousAttemptUrl("https://api.github.com/example")
                .workflowUrl("https://api.github.com/example")
                .headCommit(ChecksCatalogFixtures.simpleCommit())
                .repository(RepositoriesCatalogFixtures.helloWorldMinimalRepository())
                .headRepository(RepositoriesCatalogFixtures.helloWorldMinimalRepository())
                .headRepositoryId(1L)
                .displayTitle("displayTitle-sample")
                .build();
    }

    static GitHubWorkflowRunId workflowRunId() {
        return new GitHubWorkflowRunId(1L);
    }

    static GitHubWorkflowRunUsage workflowRunUsage() {
        return GitHubWorkflowRunUsageBuilder.builder()
                .billable(workflowRunUsageBillable())
                .runDurationMs(1)
                .build();
    }

    static GitHubWorkflowRunUsageBillable workflowRunUsageBillable() {
        return GitHubWorkflowRunUsageBillableBuilder.builder()
                .ubuntu(workflowRunUsageBillableOs())
                .macos(workflowRunUsageBillableOs())
                .windows(workflowRunUsageBillableOs())
                .build();
    }

    static GitHubWorkflowRunUsageBillableOs workflowRunUsageBillableOs() {
        return GitHubWorkflowRunUsageBillableOsBuilder.builder()
                .totalMs(1)
                .jobs(1)
                .jobRuns(List.of(workflowRunUsageJobRun()))
                .build();
    }

    static GitHubWorkflowRunUsageJobRun workflowRunUsageJobRun() {
        return GitHubWorkflowRunUsageJobRunBuilder.builder()
                .jobId(1)
                .durationMs(1)
                .build();
    }

    static GitHubWorkflowRuns workflowRuns() {
        return GitHubWorkflowRunsBuilder.builder()
                .totalCount(1)
                .workflowRuns(List.of(workflowRun()))
                .build();
    }

    static GitHubWorkflowUsage workflowUsage() {
        return GitHubWorkflowUsageBuilder.builder()
                .billable(workflowUsageBillable())
                .build();
    }

    static GitHubWorkflowUsageBillable workflowUsageBillable() {
        return GitHubWorkflowUsageBillableBuilder.builder()
                .ubuntu(workflowUsageBillableOs())
                .macos(workflowUsageBillableOs())
                .windows(workflowUsageBillableOs())
                .build();
    }

    static GitHubWorkflowUsageBillableOs workflowUsageBillableOs() {
        return GitHubWorkflowUsageBillableOsBuilder.builder().totalMs(1).build();
    }

    static GitHubWorkflows workflows() {
        return GitHubWorkflowsBuilder.builder()
                .totalCount(1)
                .workflows(List.of(workflow()))
                .build();
    }

    static GitHubActionsCacheListSort actionsCacheListSort() {
        return GitHubActionsCacheListSort.CREATED_AT;
    }

    static GitHubActionsDefaultWorkflowPermissions actionsDefaultWorkflowPermissions() {
        return GitHubActionsDefaultWorkflowPermissions.READ;
    }

    static GitHubAllowedActions allowedActions() {
        return GitHubAllowedActions.ALL;
    }

    static GitHubAuthenticationTokenRepositorySelection authenticationTokenRepositorySelection() {
        return GitHubAuthenticationTokenRepositorySelection.ALL;
    }

    static GitHubConcurrencyGroupMemberStatus concurrencyGroupMemberStatus() {
        return GitHubConcurrencyGroupMemberStatus.IN_PROGRESS;
    }

    static GitHubEnabledRepositories enabledRepositories() {
        return GitHubEnabledRepositories.ALL;
    }

    static GitHubEnvironmentApprovalState environmentApprovalState() {
        return GitHubEnvironmentApprovalState.APPROVED;
    }

    static GitHubForkPrContributorApprovalPolicy forkPrContributorApprovalPolicy() {
        return GitHubForkPrContributorApprovalPolicy.FIRST_TIME_CONTRIBUTORS_NEW_TO_GITHUB;
    }

    static GitHubHostedRunnerImageSource hostedRunnerImageSource() {
        return GitHubHostedRunnerImageSource.GITHUB;
    }

    static GitHubHostedRunnerStatus hostedRunnerStatus() {
        return GitHubHostedRunnerStatus.READY;
    }

    static GitHubJobConclusion jobConclusion() {
        return GitHubJobConclusion.SUCCESS;
    }

    static GitHubJobFilterMode jobFilterMode() {
        return GitHubJobFilterMode.LATEST;
    }

    static GitHubJobStatus jobStatus() {
        return GitHubJobStatus.QUEUED;
    }

    static GitHubJobStepStatus jobStepStatus() {
        return GitHubJobStepStatus.QUEUED;
    }

    static GitHubOidcCustomPropertyInclusionSource oidcCustomPropertyInclusionSource() {
        return GitHubOidcCustomPropertyInclusionSource.ORGANIZATION;
    }

    static GitHubOrgSecretVisibility orgSecretVisibility() {
        return GitHubOrgSecretVisibility.ALL;
    }

    static GitHubOrgVariableVisibility orgVariableVisibility() {
        return GitHubOrgVariableVisibility.ALL;
    }

    static GitHubReviewCustomGatesState reviewCustomGatesState() {
        return GitHubReviewCustomGatesState.APPROVED;
    }

    static GitHubRunnerGroupVisibility runnerGroupVisibility() {
        return GitHubRunnerGroupVisibility.SELECTED;
    }

    static GitHubRunnerLabelType runnerLabelType() {
        return GitHubRunnerLabelType.READ_ONLY;
    }

    static GitHubSelfHostedRunnersEnabledRepositories selfHostedRunnersEnabledRepositories() {
        return GitHubSelfHostedRunnersEnabledRepositories.ALL;
    }

    static GitHubWorkflowAccessLevel workflowAccessLevel() {
        return GitHubWorkflowAccessLevel.NONE;
    }

    static GitHubWorkflowRunStatus workflowRunStatus() {
        return GitHubWorkflowRunStatus.COMPLETED;
    }

    static GitHubWorkflowState workflowState() {
        return GitHubWorkflowState.ACTIVE;
    }

    static GitHubActionsPolicyWorkflowPathConditionWorkflowPath actionsPolicyWorkflowPath() {
        return new GitHubActionsPolicyWorkflowPathConditionWorkflowPath(
                List.of(".github/workflows/ci.yml"), List.of(".github/workflows/legacy.yml"));
    }

    static GitHubActionsPolicyWorkflowPathCondition actionsPolicyWorkflowPathCondition() {
        return new GitHubActionsPolicyWorkflowPathCondition(actionsPolicyWorkflowPath());
    }

    static GitHubActionsPolicyRepoConditions actionsPolicyRepoConditions() {
        return new GitHubActionsPolicyRepoConditions(actionsPolicyWorkflowPath());
    }

    static GitHubRepositoryRulesetConditionsRepositoryIdTargetRepositoryId actionsPolicyRepositoryId() {
        return new GitHubRepositoryRulesetConditionsRepositoryIdTargetRepositoryId(List.of(1L));
    }

    static GitHubRepositoryRulesetConditionsRepositoryPropertyTargetRepositoryProperty
            actionsPolicyRepositoryProperty() {
        return new GitHubRepositoryRulesetConditionsRepositoryPropertyTargetRepositoryProperty(
                List.of(new GitHubRepositoryRulesetConditionsRepositoryPropertySpec(
                        "team", List.of("core"), GitHubRepositoryRulesetConditionsRepositoryPropertySource.CUSTOM)),
                List.of(new GitHubRepositoryRulesetConditionsRepositoryPropertySpec(
                        "lifecycle",
                        List.of("deprecated"),
                        GitHubRepositoryRulesetConditionsRepositoryPropertySource.CUSTOM)));
    }

    static GitHubEnterpriseRulesetConditionsOrganizationPropertySpec
            enterpriseRulesetConditionsOrganizationPropertySpec() {
        return new GitHubEnterpriseRulesetConditionsOrganizationPropertySpec("tier", List.of("gold"));
    }

    public static GitHubEnterpriseRulesetConditionsOrganizationNameTargetOrganizationName
            enterpriseRulesetConditionsOrganizationNameTargetOrganizationName() {
        return new GitHubEnterpriseRulesetConditionsOrganizationNameTargetOrganizationName(
                List.of("octo-org"), List.of("octo-org-archive"));
    }

    static GitHubEnterpriseRulesetConditionsOrganizationNameTarget enterpriseRulesetConditionsOrganizationNameTarget() {
        return new GitHubEnterpriseRulesetConditionsOrganizationNameTarget(
                enterpriseRulesetConditionsOrganizationNameTargetOrganizationName());
    }

    public static GitHubEnterpriseRulesetConditionsOrganizationIdTargetOrganizationId
            enterpriseRulesetConditionsOrganizationIdTargetOrganizationId() {
        return new GitHubEnterpriseRulesetConditionsOrganizationIdTargetOrganizationId(List.of(42L));
    }

    static GitHubEnterpriseRulesetConditionsOrganizationIdTarget enterpriseRulesetConditionsOrganizationIdTarget() {
        return new GitHubEnterpriseRulesetConditionsOrganizationIdTarget(
                enterpriseRulesetConditionsOrganizationIdTargetOrganizationId());
    }

    public static GitHubEnterpriseRulesetConditionsOrganizationPropertyTargetOrganizationProperty
            enterpriseRulesetConditionsOrganizationPropertyTargetOrganizationProperty() {
        return new GitHubEnterpriseRulesetConditionsOrganizationPropertyTargetOrganizationProperty(
                List.of(enterpriseRulesetConditionsOrganizationPropertySpec()),
                List.of(new GitHubEnterpriseRulesetConditionsOrganizationPropertySpec("status", List.of("archived"))));
    }

    static GitHubEnterpriseRulesetConditionsOrganizationPropertyTarget
            enterpriseRulesetConditionsOrganizationPropertyTarget() {
        return new GitHubEnterpriseRulesetConditionsOrganizationPropertyTarget(
                enterpriseRulesetConditionsOrganizationPropertyTargetOrganizationProperty());
    }

    static GitHubActionsRuleParamsActor actionsRuleParamsActor() {
        return new GitHubActionsRuleParamsActor(1234L, GitHubActionsRuleParamsActorType.TEAM);
    }

    static GitHubActionsRuleRestrictActionsActorsParameters actionsRuleRestrictActionsActorsParameters() {
        return new GitHubActionsRuleRestrictActionsActorsParameters(List.of(actionsRuleParamsActor()));
    }

    static GitHubActionsRuleRestrictActionsActors actionsRuleRestrictActionsActors() {
        return new GitHubActionsRuleRestrictActionsActors(
                GitHubActionsRuleRestrictActionsActorsType.RESTRICT_ACTIONS_ACTORS,
                actionsRuleRestrictActionsActorsParameters());
    }

    static GitHubActionsRuleRestrictActionEventsParameters actionsRuleRestrictActionEventsParameters() {
        return new GitHubActionsRuleRestrictActionEventsParameters(List.of(
                GitHubActionsRuleRestrictActionEventsAllowedEvent.PUSH,
                GitHubActionsRuleRestrictActionEventsAllowedEvent.PULL_REQUEST));
    }

    static GitHubActionsRuleRestrictActionEvents actionsRuleRestrictActionEvents() {
        return new GitHubActionsRuleRestrictActionEvents(
                GitHubActionsRuleRestrictActionEventsType.RESTRICT_ACTION_EVENTS,
                actionsRuleRestrictActionEventsParameters());
    }

    static GitHubActionsPolicyLink actionsPolicyLink() {
        return new GitHubActionsPolicyLink("https://api.github.com/enterprises/enterprise/actions/policies/1");
    }

    static GitHubActionsPolicyLinks actionsPolicyLinks() {
        return new GitHubActionsPolicyLinks(
                actionsPolicyLink(),
                new GitHubActionsPolicyLink("https://github.com/enterprises/enterprise/settings/policies/actions/1"));
    }

    static GitHubActionsPolicyOrgConditions actionsPolicyOrgConditions() {
        return GitHubActionsPolicyOrgConditionsBuilder.builder()
                .repositoryName(RepositoriesCatalogFixtures.repositoryNameConditions())
                .workflowPath(actionsPolicyWorkflowPath())
                .build();
    }

    static GitHubActionsPolicyOrgConditions actionsPolicyOrgConditionsRepositoryId() {
        return GitHubActionsPolicyOrgConditionsBuilder.builder()
                .repositoryId(actionsPolicyRepositoryId())
                .workflowPath(actionsPolicyWorkflowPath())
                .build();
    }

    static GitHubActionsPolicyOrgConditions actionsPolicyOrgConditionsRepositoryProperty() {
        return GitHubActionsPolicyOrgConditionsBuilder.builder()
                .repositoryProperty(actionsPolicyRepositoryProperty())
                .workflowPath(actionsPolicyWorkflowPath())
                .build();
    }

    static GitHubActionsPolicyEnterpriseConditions actionsPolicyEnterpriseConditions() {
        return GitHubActionsPolicyEnterpriseConditionsBuilder.builder()
                .organizationName(enterpriseRulesetConditionsOrganizationNameTargetOrganizationName())
                .repositoryName(RepositoriesCatalogFixtures.repositoryNameConditions())
                .workflowPath(actionsPolicyWorkflowPath())
                .build();
    }

    static GitHubActionsPolicyEnterpriseConditions
            actionsPolicyEnterpriseConditionsOrganizationNameAndRepositoryProperty() {
        return GitHubActionsPolicyEnterpriseConditionsBuilder.builder()
                .organizationName(enterpriseRulesetConditionsOrganizationNameTargetOrganizationName())
                .repositoryProperty(actionsPolicyRepositoryProperty())
                .workflowPath(actionsPolicyWorkflowPath())
                .build();
    }

    static GitHubActionsPolicyEnterpriseConditions actionsPolicyEnterpriseConditionsOrganizationIdAndRepositoryName() {
        return GitHubActionsPolicyEnterpriseConditionsBuilder.builder()
                .organizationId(enterpriseRulesetConditionsOrganizationIdTargetOrganizationId())
                .repositoryName(RepositoriesCatalogFixtures.repositoryNameConditions())
                .workflowPath(actionsPolicyWorkflowPath())
                .build();
    }

    static GitHubActionsPolicyEnterpriseConditions
            actionsPolicyEnterpriseConditionsOrganizationIdAndRepositoryProperty() {
        return GitHubActionsPolicyEnterpriseConditionsBuilder.builder()
                .organizationId(enterpriseRulesetConditionsOrganizationIdTargetOrganizationId())
                .repositoryProperty(actionsPolicyRepositoryProperty())
                .workflowPath(actionsPolicyWorkflowPath())
                .build();
    }

    static GitHubActionsPolicyEnterpriseConditions
            actionsPolicyEnterpriseConditionsOrganizationPropertyAndRepositoryName() {
        return GitHubActionsPolicyEnterpriseConditionsBuilder.builder()
                .organizationProperty(enterpriseRulesetConditionsOrganizationPropertyTargetOrganizationProperty())
                .repositoryName(RepositoriesCatalogFixtures.repositoryNameConditions())
                .workflowPath(actionsPolicyWorkflowPath())
                .build();
    }

    static GitHubActionsPolicyEnterpriseConditions
            actionsPolicyEnterpriseConditionsOrganizationPropertyAndRepositoryProperty() {
        return GitHubActionsPolicyEnterpriseConditionsBuilder.builder()
                .organizationProperty(enterpriseRulesetConditionsOrganizationPropertyTargetOrganizationProperty())
                .repositoryProperty(actionsPolicyRepositoryProperty())
                .workflowPath(actionsPolicyWorkflowPath())
                .build();
    }

    static GitHubActionsPolicyConditions actionsPolicyConditions() {
        return GitHubActionsPolicyConditionsBuilder.builder()
                .organizationName(enterpriseRulesetConditionsOrganizationNameTargetOrganizationName())
                .repositoryName(RepositoriesCatalogFixtures.repositoryNameConditions())
                .workflowPath(actionsPolicyWorkflowPath())
                .build();
    }

    static GitHubActionsPolicyConditions actionsPolicyConditionsRepositoryName() {
        return GitHubActionsPolicyConditionsBuilder.builder()
                .repositoryName(RepositoriesCatalogFixtures.repositoryNameConditions())
                .workflowPath(actionsPolicyWorkflowPath())
                .build();
    }

    static GitHubActionsPolicyConditions actionsPolicyConditionsWorkflowPath() {
        return GitHubActionsPolicyConditionsBuilder.builder()
                .workflowPath(actionsPolicyWorkflowPath())
                .build();
    }

    static GitHubActionsPolicy actionsPolicy() {
        return GitHubActionsPolicyBuilder.builder()
                .id(1L)
                .name("Restrict workflow modifications")
                .target(GitHubActionsPolicyTarget.ACTIONS)
                .sourceType(GitHubActionsPolicySourceType.ENTERPRISE)
                .source("enterprise")
                .enforcement(GitHubRepositoryRuleEnforcement.ACTIVE)
                .conditions(actionsPolicyConditions())
                .rules(List.of(actionsRuleRestrictActionsActors(), actionsRuleRestrictActionEvents()))
                .nodeId("RUL_lA")
                .links(actionsPolicyLinks())
                .createdAt(Instant.parse("2024-01-15T10:30:00Z"))
                .updatedAt(Instant.parse("2024-01-15T11:00:00Z"))
                .build();
    }

    static GitHubActionsPolicies actionsPolicies() {
        return new GitHubActionsPolicies(1, List.of(actionsPolicy()));
    }

    static GitHubGetOrgActionsPoliciesFilter getOrgActionsPoliciesFilter() {
        return GitHubGetOrgActionsPoliciesFilterBuilder.builder()
                .hasParents(true)
                .build();
    }

    static GitHubGetRepoActionsPoliciesFilter getRepoActionsPoliciesFilter() {
        return GitHubGetRepoActionsPoliciesFilterBuilder.builder()
                .hasParents(true)
                .build();
    }

    static GitHubCreateOrgActionsPolicyPayload createOrgActionsPolicyPayload() {
        return GitHubCreateOrgActionsPolicyPayloadBuilder.builder()
                .name("Require approved actors")
                .enforcement(GitHubRepositoryRuleEnforcement.ACTIVE)
                .conditions(actionsPolicyOrgConditions())
                .rules(List.of(actionsRuleRestrictActionsActors()))
                .build();
    }

    static GitHubCreateRepoActionsPolicyPayload createRepoActionsPolicyPayload() {
        return GitHubCreateRepoActionsPolicyPayloadBuilder.builder()
                .name("Require approved actors")
                .enforcement(GitHubRepositoryRuleEnforcement.ACTIVE)
                .conditions(actionsPolicyRepoConditions())
                .rules(List.of(actionsRuleRestrictActionsActors()))
                .build();
    }

    static GitHubUpdateOrgActionsPolicyPayload updateOrgActionsPolicyPayload() {
        return GitHubUpdateOrgActionsPolicyPayloadBuilder.builder()
                .name("Require approved actors")
                .enforcement(GitHubRepositoryRuleEnforcement.ACTIVE)
                .conditions(actionsPolicyOrgConditions())
                .rules(List.of(actionsRuleRestrictActionsActors()))
                .build();
    }

    static GitHubUpdateRepoActionsPolicyPayload updateRepoActionsPolicyPayload() {
        return GitHubUpdateRepoActionsPolicyPayloadBuilder.builder()
                .name("Require approved actors")
                .enforcement(GitHubRepositoryRuleEnforcement.ACTIVE)
                .conditions(actionsPolicyRepoConditions())
                .rules(List.of(actionsRuleRestrictActionsActors()))
                .build();
    }

    static GitHubListSelfHostedRunnersForEnterpriseFilter listSelfHostedRunnersForEnterpriseFilter() {
        return GitHubListSelfHostedRunnersForEnterpriseFilterBuilder.builder()
                .name("name-sample")
                .build();
    }

    static GitHubRunnersForEnterprise runnersForEnterprise() {
        return GitHubRunnersForEnterpriseBuilder.builder()
                .totalCount(1)
                .runners(List.of(runner()))
                .build();
    }

    static GitHubRunnerVersionDeprecationForEnterprise runnerVersionDeprecationForEnterprise() {
        return GitHubRunnerVersionDeprecationForEnterpriseBuilder.builder()
                .runnerVersion("2.300.0")
                .registrationDeprecatesAt(Instant.parse("2026-08-01T00:00:00Z"))
                .runtimeDeprecatesAt(Instant.parse("2026-09-01T00:00:00Z"))
                .build();
    }

    static GitHubRunnerJitConfigForEnterprise runnerJitConfigForEnterprise() {
        return GitHubRunnerJitConfigForEnterpriseBuilder.builder()
                .runner(runner())
                .encodedJitConfig("encodedJitConfig-sample")
                .build();
    }

    static GitHubRunnerLabelsForEnterprise runnerLabelsForEnterprise() {
        return GitHubRunnerLabelsForEnterpriseBuilder.builder()
                .totalCount(1)
                .labels(List.of(runnerLabel()))
                .build();
    }

    static GitHubActionsPoliciesForEnterprise actionsPoliciesForEnterprise() {
        return new GitHubActionsPoliciesForEnterprise(1, List.of(actionsPolicy()));
    }

    static GitHubGenerateRunnerJitconfigForEnterprisePayload generateRunnerJitconfigForEnterprisePayload() {
        return GitHubGenerateRunnerJitconfigForEnterprisePayloadBuilder.builder()
                .name("name-sample")
                .runnerGroupId(1)
                .labels(List.of("labels-sample"))
                .workFolder("workFolder-sample")
                .build();
    }

    static GitHubAddCustomLabelsToSelfHostedRunnerForEnterprisePayload
            addCustomLabelsToSelfHostedRunnerForEnterprisePayload() {
        return GitHubAddCustomLabelsToSelfHostedRunnerForEnterprisePayloadBuilder.builder()
                .labels(List.of("labels-sample"))
                .build();
    }

    static GitHubSetCustomLabelsForSelfHostedRunnerForEnterprisePayload
            setCustomLabelsForSelfHostedRunnerForEnterprisePayload() {
        return GitHubSetCustomLabelsForSelfHostedRunnerForEnterprisePayloadBuilder.builder()
                .labels(List.of("labels-sample"))
                .build();
    }

    static GitHubCreateEnterpriseActionsPolicyPayload createEnterpriseActionsPolicyPayload() {
        return GitHubCreateEnterpriseActionsPolicyPayloadBuilder.builder()
                .name("Require approved actors")
                .enforcement(GitHubRepositoryRuleEnforcement.ACTIVE)
                .conditions(actionsPolicyEnterpriseConditions())
                .rules(List.of(actionsRuleRestrictActionsActors()))
                .build();
    }

    static GitHubUpdateEnterpriseActionsPolicyPayload updateEnterpriseActionsPolicyPayload() {
        return GitHubUpdateEnterpriseActionsPolicyPayloadBuilder.builder()
                .name("Require approved actors")
                .enforcement(GitHubRepositoryRuleEnforcement.ACTIVE)
                .conditions(actionsPolicyEnterpriseConditions())
                .rules(List.of(actionsRuleRestrictActionsActors()))
                .build();
    }

    static GitHubActionsOidcCustomIssuerPolicyForEnterprise actionsOidcCustomIssuerPolicyForEnterprise() {
        return GitHubActionsOidcCustomIssuerPolicyForEnterpriseBuilder.builder()
                .includeEnterpriseSlug(true)
                .build();
    }

    static GitHubRunnerVersionDeprecation runnerVersionDeprecation() {
        return GitHubRunnerVersionDeprecationBuilder.builder()
                .runnerVersion("2.300.0")
                .registrationDeprecatesAt(Instant.parse("2026-08-01T00:00:00Z"))
                .runtimeDeprecatesAt(Instant.parse("2026-09-01T00:00:00Z"))
                .build();
    }
}
