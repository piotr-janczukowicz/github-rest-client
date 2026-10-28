package io.github.piotr_janczukowicz.github.api.organizations;

import io.github.piotr_janczukowicz.github.api.GitHubDirection;
import io.github.piotr_janczukowicz.github.api.enterpriseadmin.GitHubCustomPropertyBaseSourceType;
import io.github.piotr_janczukowicz.github.api.enterpriseadmin.GitHubCustomPropertyBaseValueType;
import io.github.piotr_janczukowicz.github.api.repositories.GitHubCustomPropertyValue;
import io.github.piotr_janczukowicz.github.api.repositories.GitHubRepositoryPermissions;
import io.github.piotr_janczukowicz.github.api.repositories.GitHubRepositoryRuleCreation;
import io.github.piotr_janczukowicz.github.api.repositories.GitHubRepositoryRuleCreationType;
import io.github.piotr_janczukowicz.github.api.repositories.GitHubRepositoryRuleEnforcement;
import io.github.piotr_janczukowicz.github.api.repositories.GitHubRepositoryRulesetBypassActor;
import io.github.piotr_janczukowicz.github.api.repositories.GitHubRepositoryRulesetBypassActorType;
import io.github.piotr_janczukowicz.github.api.repositories.GitHubRepositoryRulesetBypassMode;
import io.github.piotr_janczukowicz.github.api.repositories.GitHubRepositoryRulesetConditionsRefName;
import io.github.piotr_janczukowicz.github.api.repositories.GitHubRepositoryRulesetConditionsRepositoryNameTargetRepositoryName;
import io.github.piotr_janczukowicz.github.api.repositories.GitHubRepositoryRulesetTarget;
import io.github.piotr_janczukowicz.github.api.repositories.GitHubRuleSuiteEvaluateStatus;
import io.github.piotr_janczukowicz.github.api.repositories.GitHubRuleSuiteListResult;
import io.github.piotr_janczukowicz.github.api.repositories.GitHubRuleSuiteTimePeriod;
import io.github.piotr_janczukowicz.github.api.repositories.GitHubWebhookConfigInsecureSsl;
import io.github.piotr_janczukowicz.github.api.repositories.RepositoriesCatalogFixtures;
import io.github.piotr_janczukowicz.github.api.teams.GitHubTeamOrganizationPlan;
import io.github.piotr_janczukowicz.github.api.teams.GitHubTeamOwnershipType;
import io.github.piotr_janczukowicz.github.api.teams.GitHubTeamSimple;
import io.github.piotr_janczukowicz.github.api.users.GitHubSimpleUser;
import io.github.piotr_janczukowicz.github.api.users.GitHubSimpleUserBuilder;
import io.github.piotr_janczukowicz.github.api.users.GitHubUserType;
import io.github.piotr_janczukowicz.github.api.users.GitHubUserViewType;
import io.github.piotr_janczukowicz.github.api.users.UsersCatalogFixtures;
import io.github.piotr_janczukowicz.json.OptionalNull;
import java.net.URI;
import java.time.Instant;
import java.util.List;
import java.util.Map;

public final class OrganizationsCatalogFixtures {

    private OrganizationsCatalogFixtures() {}

    public static GitHubAuditLogEvent auditLogEvent() {
        return GitHubAuditLogEventBuilder.builder()
                .timestamp(1_761_000_000L)
                .action("repo.create")
                .active(true)
                .activeWas(false)
                .actor("octocat")
                .actorId(583_231L)
                .actorLocation(auditLogEventActorLocation())
                .data(Map.of("ref", "refs/heads/main"))
                .orgId(1000L)
                .userId(583_231L)
                .businessId(2000L)
                .blockedUser("spammer")
                .business("octo-business")
                .config(List.of(Map.of("name", "web")))
                .configWas(List.of(Map.of("name", "ssh")))
                .contentType("json")
                .operationType("create")
                .createdAt(1_761_000_000L)
                .deployKeyFingerprint("SHA256:abc")
                .documentId("doc-1")
                .emoji(":octocat:")
                .events(List.of(Map.of("name", "push")))
                .eventsWere(List.of(Map.of("name", "pull")))
                .explanation("Repository created.")
                .fingerprint("fp")
                .hookId(3000L)
                .limitedAvailability(false)
                .message("Created repository.")
                .name("octo-repo")
                .oldUser("old-user")
                .opensshPublicKey("ssh-ed25519 AAAA")
                .org("octo-org")
                .previousVisibility("private")
                .readOnly(false)
                .repo("octo-org/octo-repo")
                .repository("octo-repo")
                .repositoryPublic(true)
                .targetLogin("target")
                .team("team")
                .transportProtocol(1)
                .transportProtocolName("http")
                .user("octocat")
                .visibility("public")
                .build();
    }

    static GitHubAuditLogEventActorLocation auditLogEventActorLocation() {
        return GitHubAuditLogEventActorLocationBuilder.builder()
                .countryName("United States")
                .build();
    }

    static GitHubClusterDeploymentRecordItem clusterDeploymentRecordItem() {
        return new GitHubClusterDeploymentRecordItem(
                "awesome-image",
                "sha256:1bb1e949e55dcefc6353e7b36c8897d2a107d8e8dca49d4e3c0ea8493fc0bc72",
                "1.0.0",
                GitHubArtifactDeploymentStatus.DEPLOYED,
                "deployment-pod",
                "Hello-World",
                Map.of("environment", "prod"),
                List.of(GitHubArtifactRuntimeRisk.INTERNET_EXPOSED, GitHubArtifactRuntimeRisk.SENSITIVE_DATA));
    }

    static GitHubConvertMemberToOutsideCollaboratorPayload convertMemberToOutsideCollaboratorPayload() {
        return new GitHubConvertMemberToOutsideCollaboratorPayload(false);
    }

    static GitHubCreateArtifactDeploymentRecordPayload createArtifactDeploymentRecordPayload() {
        return new GitHubCreateArtifactDeploymentRecordPayload(
                "awesome-image",
                "sha256:1bb1e949e55dcefc6353e7b36c8897d2a107d8e8dca49d4e3c0ea8493fc0bc72",
                "1.0.0",
                GitHubArtifactDeploymentStatus.DEPLOYED,
                "prod",
                "us-east-1",
                "moda-1",
                "deployment-pod",
                Map.of("team", "platform"),
                List.of(GitHubArtifactRuntimeRisk.CRITICAL_RESOURCE),
                "Hello-World",
                true);
    }

    static GitHubCreateArtifactStorageRecordPayload createArtifactStorageRecordPayload() {
        return new GitHubCreateArtifactStorageRecordPayload(
                "libfoo",
                "sha256:1bb1e949e55dcefc6353e7b36c8897d2a107d8e8dca49d4e3c0ea8493fc0bc72",
                "1.0.0",
                URI.create("https://reg.example.com/artifactory/bar/libfoo-1.2.3"),
                URI.create("com/github/bar/libfoo-1.2.3"),
                URI.create("https://reg.example.com/artifactory/"),
                "bar",
                GitHubArtifactStorageStatus.ACTIVE,
                "Hello-World",
                true);
    }

    static GitHubCreateClusterDeploymentRecordsJobPayload createClusterDeploymentRecordsJobPayload() {
        return new GitHubCreateClusterDeploymentRecordsJobPayload(
                "prod", "us-east-1", List.of(clusterDeploymentRecordItem()));
    }

    static GitHubCreateIssueFieldOption createIssueFieldOption() {
        return new GitHubCreateIssueFieldOption(
                "High", OptionalNull.of("High priority work"), GitHubIssueFieldOptionColor.RED, 0);
    }

    static GitHubCreateIssueFieldPayload createIssueFieldPayload() {
        return new GitHubCreateIssueFieldPayload(
                "Priority",
                OptionalNull.of("Issue priority for triage"),
                GitHubIssueFieldDataType.SINGLE_SELECT,
                GitHubIssueFieldVisibility.ORGANIZATION_MEMBERS_ONLY,
                OptionalNull.of(List.of(createIssueFieldOption())));
    }

    static GitHubCreateIssueTypePayload createIssueTypePayload() {
        return new GitHubCreateIssueTypePayload(
                "Epic",
                true,
                OptionalNull.of("Large body of work spanning multiple issues"),
                OptionalNull.of(GitHubIssueTypeColor.PURPLE));
    }

    static GitHubCreateNetworkConfigurationPayload createNetworkConfigurationPayload() {
        return new GitHubCreateNetworkConfigurationPayload(
                "my-network-configuration",
                List.of("23456789ABDCEF1"),
                GitHubCreateNetworkConfigurationComputeService.ACTIONS,
                List.of("ABCDEF123456789"),
                true);
    }

    static GitHubCreateOrganizationInvitationPayload createOrganizationInvitationPayload() {
        return new GitHubCreateOrganizationInvitationPayload(
                1L, "octocat@github.com", GitHubOrganizationInvitationRole.DIRECT_MEMBER, List.of(42L));
    }

    static GitHubCreateOrgRulesetPayload createOrgRulesetPayload() {
        return new GitHubCreateOrgRulesetPayload(
                "main",
                GitHubRepositoryRulesetTarget.BRANCH,
                GitHubRepositoryRuleEnforcement.ACTIVE,
                List.of(new GitHubRepositoryRulesetBypassActor(
                        1L, GitHubRepositoryRulesetBypassActorType.TEAM, GitHubRepositoryRulesetBypassMode.ALWAYS)),
                orgRulesetConditions(),
                List.of(new GitHubRepositoryRuleCreation(GitHubRepositoryRuleCreationType.CREATION)),
                GitHubCreateOrgRulesetSourceType.ORGANIZATION);
    }

    static GitHubCreateOrgWebhookConfig createOrgWebhookConfig() {
        return new GitHubCreateOrgWebhookConfig(
                URI.create("https://example.com/webhook"),
                "json",
                "very-secret",
                GitHubWebhookConfigInsecureSsl.text("0"),
                "kdaigle",
                "password");
    }

    static GitHubCreateOrgWebhookPayload createOrgWebhookPayload() {
        return new GitHubCreateOrgWebhookPayload("web", createOrgWebhookConfig(), List.of("push"), true);
    }

    static GitHubCreateOrUpdateOrganizationDefinitionsPayload createOrUpdateOrganizationDefinitionsPayload() {
        return new GitHubCreateOrUpdateOrganizationDefinitionsPayload(List.of(customProperty()));
    }

    static GitHubCreateOrUpdateOrganizationValuesPayload createOrUpdateOrganizationValuesPayload() {
        return new GitHubCreateOrUpdateOrganizationValuesPayload(
                List.of("Hello-World"), List.of(new GitHubCustomPropertyValue("environment", List.of("production"))));
    }

    static GitHubExternalProperty externalProperty() {
        return new GitHubExternalProperty("environment");
    }

    static List<GitHubExternalProperty> externalProperties() {
        return List.of(externalProperty(), new GitHubExternalProperty("business_unit"));
    }

    static GitHubOrganizationExternalPropertyInstallationInstallation
            organizationExternalPropertyInstallationInstallation() {
        return new GitHubOrganizationExternalPropertyInstallationInstallation(1L);
    }

    static GitHubOrganizationExternalPropertyInstallation organizationExternalPropertyInstallation() {
        return new GitHubOrganizationExternalPropertyInstallation(
                "Acme", organizationExternalPropertyInstallationInstallation());
    }

    static GitHubRepositoryExternalPropertyPayload repositoryExternalPropertyPayload() {
        return new GitHubRepositoryExternalPropertyPayload("Hello-World", "production");
    }

    static GitHubExternalPropertiesCreateOrUpdateValuesPayload externalPropertiesCreateOrUpdateValuesPayload() {
        return new GitHubExternalPropertiesCreateOrUpdateValuesPayload(
                List.of("Hello-World", "octo-repo"),
                List.of(new GitHubCustomPropertyValue("environment", List.of("production"))));
    }

    static GitHubExternalPropertiesCreateOrUpdatePropertyValuesPayload
            externalPropertiesCreateOrUpdatePropertyValuesPayload() {
        return new GitHubExternalPropertiesCreateOrUpdatePropertyValuesPayload(List.of(
                repositoryExternalPropertyPayload(), new GitHubRepositoryExternalPropertyPayload("octo-repo", null)));
    }

    static GitHubExternalPropertiesRegisterInstallationPayload externalPropertiesRegisterInstallationPayload() {
        return new GitHubExternalPropertiesRegisterInstallationPayload(67_890L, "Acme");
    }

    public static GitHubCustomPropertySetPayload customPropertySetPayload() {
        return new GitHubCustomPropertySetPayload(
                GitHubCustomPropertyValueType.SINGLE_SELECT,
                true,
                OptionalNull.of(List.of("production")),
                OptionalNull.of("Prod or dev environment"),
                OptionalNull.of(List.of("production", "development")),
                OptionalNull.of(GitHubCustomPropertyValuesEditableBy.ORG_ACTORS),
                true);
    }

    static GitHubEnableOrganizationSecurityProductPayload enableOrganizationSecurityProductPayload() {
        return new GitHubEnableOrganizationSecurityProductPayload(GitHubOrganizationSecurityProductQuerySuite.DEFAULT);
    }

    static GitHubApiInsightsRouteStat apiInsightsRouteStat() {
        return new GitHubApiInsightsRouteStat(
                "GET", "/repositories/:repository_id", 544_665L, 13L, "2024-09-18T06:30:09Z", "2024-09-18T15:43:03Z");
    }

    static GitHubApiInsightsRouteStatsFilter apiInsightsRouteStatsFilter() {
        return new GitHubApiInsightsRouteStatsFilter(
                Instant.parse("2024-09-11T15:00:00Z"),
                Instant.parse("2024-09-18T15:43:03Z"),
                GitHubDirection.DESC,
                List.of(GitHubApiInsightsRouteStatsSort.TOTAL_REQUEST_COUNT),
                "/repos");
    }

    static GitHubApiInsightsSubjectStat apiInsightsSubjectStat() {
        return new GitHubApiInsightsSubjectStat(
                "installation",
                "GitHub Actions",
                954_453L,
                544_665,
                13,
                "2024-09-18T06:30:09Z",
                "2024-09-18T15:43:03Z");
    }

    static GitHubApiInsightsSubjectStatsFilter apiInsightsSubjectStatsFilter() {
        return new GitHubApiInsightsSubjectStatsFilter(
                Instant.parse("2024-09-11T15:00:00Z"),
                Instant.parse("2024-09-18T15:43:03Z"),
                GitHubDirection.DESC,
                List.of(GitHubApiInsightsSort.TOTAL_REQUEST_COUNT),
                "Actions");
    }

    static GitHubApiInsightsSummaryStats apiInsightsSummaryStats() {
        return new GitHubApiInsightsSummaryStats(34_225L, 23L);
    }

    static GitHubApiInsightsTimeStat apiInsightsTimeStat() {
        return new GitHubApiInsightsTimeStat("2024-09-11T15:00:00Z", 34_225L, 0L);
    }

    static GitHubApiInsightsTimeWindowFilter apiInsightsTimeWindowFilter() {
        return new GitHubApiInsightsTimeWindowFilter(
                Instant.parse("2024-09-11T15:00:00Z"), Instant.parse("2024-09-18T15:43:03Z"));
    }

    static GitHubApiInsightsTimeStatsFilter apiInsightsTimeStatsFilter() {
        return new GitHubApiInsightsTimeStatsFilter(
                Instant.parse("2024-09-11T15:00:00Z"), Instant.parse("2024-09-18T15:43:03Z"), "day");
    }

    static GitHubApiInsightsUserStat apiInsightsUserStat() {
        return new GitHubApiInsightsUserStat(
                "oauth_app",
                "GitHub Actions",
                954_453L,
                5678L,
                1245L,
                544_665,
                13,
                "2024-09-18T06:30:09Z",
                "2024-09-18T15:43:03Z");
    }

    static GitHubApiInsightsUserStatsFilter apiInsightsUserStatsFilter() {
        return new GitHubApiInsightsUserStatsFilter(
                Instant.parse("2024-09-11T15:00:00Z"),
                Instant.parse("2024-09-18T15:43:03Z"),
                GitHubDirection.DESC,
                List.of(GitHubApiInsightsSort.TOTAL_REQUEST_COUNT),
                "Actions");
    }

    public static GitHubAppPermissions appPermissions() {
        return appPermissionsFilled();
    }

    static GitHubArtifactClusterDeploymentRecordsJob artifactClusterDeploymentRecordsJob() {
        return new GitHubArtifactClusterDeploymentRecordsJob(123L, List.of());
    }

    static GitHubArtifactClusterDeploymentRecordsJobStatus artifactClusterDeploymentRecordsJobStatus() {
        return new GitHubArtifactClusterDeploymentRecordsJobStatus(
                123L, GitHubArtifactClusterJobStatus.COMPLETED, Instant.parse("2026-06-10T12:00:00Z"), 5, List.of());
    }

    static GitHubArtifactDeploymentError artifactDeploymentError() {
        return new GitHubArtifactDeploymentError(
                GitHubArtifactDeploymentErrorCause.NOT_FOUND, artifactDeploymentErrorDeployment());
    }

    static GitHubArtifactDeploymentErrorDeployment artifactDeploymentErrorDeployment() {
        return new GitHubArtifactDeploymentErrorDeployment(
                "awesome-image",
                "sha256:1bb1e949e55dcefc6353e7b36c8897d2a107d8e8dca49d4e3c0ea8493fc0bc72",
                "deployment-pod",
                "1.0.0",
                "deployed",
                "Hello-World",
                Map.of("team", "platform"),
                List.of("internet-exposed"));
    }

    static GitHubArtifactDeploymentRecord artifactDeploymentRecord() {
        return new GitHubArtifactDeploymentRecord(
                123L,
                "sha256:1bb1e949e55dcefc6353e7b36c8897d2a107d8e8dca49d4e3c0ea8493fc0bc72",
                "prod",
                "pacific-east",
                "moda-1",
                "prod-deployment",
                Map.of("data", "sensitive"),
                List.of(GitHubArtifactRuntimeRisk.INTERNET_EXPOSED),
                "2011-01-26T19:14:43Z",
                "2011-01-26T19:14:43Z",
                456L);
    }

    static GitHubArtifactCreatedDeploymentRecords artifactCreatedDeploymentRecords() {
        return new GitHubArtifactCreatedDeploymentRecords(1, List.of(artifactDeploymentRecord()));
    }

    static GitHubArtifactDeploymentRecordsPage artifactDeploymentRecordsPage() {
        return new GitHubArtifactDeploymentRecordsPage(
                1, List.of(artifactDeploymentRecord()), List.of(artifactDeploymentError()));
    }

    static GitHubArtifactListedDeploymentRecords artifactListedDeploymentRecords() {
        return new GitHubArtifactListedDeploymentRecords(1, List.of(artifactDeploymentRecord()));
    }

    static GitHubArtifactListedStorageRecords artifactListedStorageRecords() {
        return new GitHubArtifactListedStorageRecords(1, List.of(artifactStorageRecord()));
    }

    static GitHubArtifactStorageRecord artifactStorageRecord() {
        return new GitHubArtifactStorageRecord(
                1L,
                "libfoo",
                "sha256:1bb1e949e55dcefc6353e7b36c8897d2a107d8e8dca49d4e3c0ea8493fc0bc72",
                "https://reg.example.com/artifactory/bar/libfoo-1.2.3",
                "https://reg.example.com/artifactory/",
                "bar",
                "stored",
                "2023-10-01T12:00:00Z",
                "2023-10-01T12:00:00Z");
    }

    static GitHubArtifactStorageRecordsPage artifactStorageRecordsPage() {
        return new GitHubArtifactStorageRecordsPage(
                1,
                List.of(new GitHubArtifactStorageRecord(
                        1L,
                        "libfoo",
                        "sha256:1bb1e949e55dcefc6353e7b36c8897d2a107d8e8dca49d4e3c0ea8493fc0bc72",
                        "https://reg.example.com/artifactory/bar/libfoo-1.2.3",
                        "https://reg.example.com/artifactory/",
                        "bar",
                        "active",
                        "2023-10-01T12:00:00Z",
                        "2023-10-01T12:00:00Z")));
    }

    static GitHubAttestationRepository attestationRepository() {
        return new GitHubAttestationRepository(123L, "foo");
    }

    public static GitHubCustomProperty customProperty() {
        return new GitHubCustomProperty(
                "environment",
                "https://api.github.com/orgs/github/properties/schema/environment",
                GitHubCustomPropertySourceType.ORGANIZATION,
                GitHubCustomPropertyValueType.SINGLE_SELECT,
                true,
                List.of("production"),
                "Prod or dev environment",
                List.of("production", "development"),
                GitHubCustomPropertyValuesEditableBy.ORG_ACTORS,
                true);
    }

    public static GitHubEnterprise enterprise() {
        return new GitHubEnterprise(
                "The Octo Business enterprise",
                "https://github.com/enterprises/octo-business",
                "https://octo-business.example.com",
                42L,
                "MDEwOlJlcG9zaXRvcnkxMjk2MjY5",
                "Octo Business",
                "octo-business",
                Instant.parse("2019-01-26T19:01:12Z"),
                Instant.parse("2019-01-26T19:14:43Z"),
                "https://github.com/images/error/octocat_happy.gif");
    }

    static GitHubImmutableReleasesOrganizationSettings immutableReleasesOrganizationSettings() {
        return new GitHubImmutableReleasesOrganizationSettings(
                GitHubImmutableReleasesEnforcedRepositories.ALL,
                "https://api.github.com/orgs/github/immutable-releases/repositories");
    }

    static GitHubImmutableReleasesSelectedRepositoriesPage immutableReleasesSelectedRepositoriesPage() {
        return new GitHubImmutableReleasesSelectedRepositoriesPage(
                1, List.of(RepositoriesCatalogFixtures.helloWorldMinimalRepository()));
    }

    public static GitHubInstallationAccount installationAccount() {
        return new GitHubInstallationAccount.SimpleUser(octoOrgAccountUser());
    }

    public static GitHubInstallation installation() {
        return new GitHubInstallation(
                25_381L,
                new GitHubInstallationAccount.SimpleUser(octoOrgAccountUser()),
                GitHubInstallationRepositorySelection.SELECTED,
                "https://api.github.com/app/installations/25381/access_tokens",
                "https://api.github.com/installation/repositories",
                "https://github.com/organizations/octo-org/settings/installations/25381",
                2218L,
                "Iv1.8a61f9b3a7aba766",
                6_811_672L,
                "Organization",
                appPermissionsFilled(),
                List.of("deployment", "deployment_status"),
                Instant.parse("2017-05-16T15:47:09Z"),
                Instant.parse("2017-06-06T18:23:23Z"),
                "config.yml",
                true,
                List.of("config.yml", ".github/issue_TEMPLATE.md"),
                "github-actions",
                octocat(),
                Instant.parse("2017-06-01T12:00:00Z"),
                "admin@octo-org.example.com");
    }

    static GitHubIssueField issueField() {
        return new GitHubIssueField(
                512,
                "IF_kwDNAd3NAZr",
                "Priority",
                "Level of importance for the issue",
                GitHubIssueFieldDataType.SINGLE_SELECT,
                GitHubIssueFieldVisibility.ORGANIZATION_MEMBERS_ONLY,
                List.of(
                        new GitHubIssueFieldOption(
                                1,
                                "High",
                                "High priority",
                                GitHubIssueFieldOptionColor.RED,
                                1,
                                Instant.parse("2025-01-15T10:30:15Z"),
                                Instant.parse("2025-01-15T10:30:15Z")),
                        new GitHubIssueFieldOption(
                                2,
                                "Medium",
                                "Medium priority",
                                GitHubIssueFieldOptionColor.YELLOW,
                                2,
                                Instant.parse("2025-01-15T10:30:15Z"),
                                Instant.parse("2025-01-15T10:30:15Z")),
                        new GitHubIssueFieldOption(
                                3,
                                "Low",
                                "Low priority",
                                GitHubIssueFieldOptionColor.GREEN,
                                3,
                                Instant.parse("2025-01-15T10:30:15Z"),
                                Instant.parse("2025-01-15T10:30:15Z"))),
                Instant.parse("2025-01-15T10:30:15Z"),
                Instant.parse("2025-01-15T10:30:15Z"));
    }

    static GitHubIssueTypeWebhook issueTypeWebhook() {
        return new GitHubIssueTypeWebhook(1, "Bug", GitHubIssueTypeColor.RED);
    }

    static GitHubIssueFieldOption issueFieldOption() {
        return new GitHubIssueFieldOption(
                1,
                "High",
                "High priority",
                GitHubIssueFieldOptionColor.RED,
                1,
                Instant.parse("2025-01-15T10:30:15Z"),
                Instant.parse("2025-01-15T10:30:15Z"));
    }

    public static GitHubNetworkConfiguration networkConfiguration() {
        return new GitHubNetworkConfiguration(
                "123456789ABCDEF",
                "My network configuration",
                GitHubNetworkConfigurationComputeService.ACTIONS,
                List.of("23456789ABDCEF1", "3456789ABDCEF12"),
                List.of("456789ABDCEF123"),
                true,
                Instant.parse("2022-10-09T23:39:01Z"));
    }

    static GitHubNetworkConfigurationsPage networkConfigurationsPage() {
        return new GitHubNetworkConfigurationsPage(
                2,
                List.of(
                        new GitHubNetworkConfiguration(
                                "123456789ABCDEF",
                                "My network configuration",
                                GitHubNetworkConfigurationComputeService.ACTIONS,
                                List.of("23456789ABDCEF1", "3456789ABDCEF12"),
                                List.of("456789ABDCEF123"),
                                true,
                                Instant.parse("2022-10-09T23:39:01Z")),
                        new GitHubNetworkConfiguration(
                                "456789ABDCEF123",
                                "My other configuration",
                                GitHubNetworkConfigurationComputeService.NONE,
                                List.of("56789ABDCEF1234", "6789ABDCEF12345"),
                                List.of("789ABDCEF123456"),
                                false,
                                Instant.parse("2023-04-26T15:23:37Z"))));
    }

    public static GitHubNetworkSettings networkSettings() {
        return new GitHubNetworkSettings(
                "220F78DACB92BBFBC5E6F22DE1CCF52309D",
                "934E208B3EE0BD60CF5F752C426BFB53562",
                "my_network_settings",
                "/subscriptions/14839728-3ad9-43ab-bd2b-fa6ad0f75e2a/resourceGroups/my-rg/providers/Microsoft.Network/virtualNetworks/my-vnet/subnets/my-subnet",
                "eastus");
    }

    static GitHubOrganizationFull organizationFull() {
        return new GitHubOrganizationFull(
                "github",
                1L,
                "MDEyOk9yZ2FuaXphdGlvbjE=",
                "https://api.github.com/orgs/github",
                "https://api.github.com/orgs/github/repos",
                "https://api.github.com/orgs/github/events",
                "https://api.github.com/orgs/github/hooks",
                "https://api.github.com/orgs/github/issues",
                "https://api.github.com/orgs/github/members{/member}",
                "https://api.github.com/orgs/github/public_members{/member}",
                "https://github.com/images/error/octocat_happy.gif",
                "A great organization",
                "github",
                "GitHub",
                "https://github.com/blog",
                "San Francisco",
                "octocat@github.com",
                "github",
                true,
                true,
                true,
                2L,
                1L,
                20L,
                0L,
                "https://github.com/octocat",
                "Organization",
                100L,
                100L,
                81L,
                10_000L,
                8L,
                "mona@github.com",
                new GitHubTeamOrganizationPlan("Medium", 400L, 20L, 4L, 5L),
                "read",
                "main",
                true,
                true,
                "all",
                false,
                false,
                false,
                true,
                true,
                true,
                true,
                true,
                true,
                false,
                false,
                true,
                true,
                true,
                false,
                false,
                false,
                false,
                false,
                false,
                false,
                false,
                "https://github.com/octo-org/octo-repo/blob/main/im-blocked.md",
                Instant.parse("2008-01-14T04:33:35Z"),
                Instant.parse("2014-03-03T18:58:10Z"),
                Instant.parse("2013-01-01T00:00:00Z"),
                false,
                false);
    }

    static GitHubOrganizationInstallationsPage organizationInstallationsPage() {
        return new GitHubOrganizationInstallationsPage(1, List.of(installation()));
    }

    static GitHubOrganizationPatGrant organizationPatGrant() {
        return new GitHubOrganizationPatGrant(
                25_381L,
                octocat(),
                GitHubOrganizationPatRepositorySelection.ALL,
                "https://api.github.com/organizations/652551/personal-access-tokens/25381/repositories",
                organizationPatPermissions(),
                Instant.parse("2023-05-16T15:47:09Z"),
                98_716L,
                "Some Token",
                false,
                Instant.parse("2023-11-16T15:47:09Z"),
                Instant.parse("2022-04-28T12:00:00Z"));
    }

    static GitHubOrganizationPatGrantRequest organizationPatGrantRequest() {
        return new GitHubOrganizationPatGrantRequest(
                25_381L,
                "I need to access the GitHub API",
                octocat(),
                GitHubOrganizationPatRepositorySelection.ALL,
                "https://api.github.com/organizations/652551/personal-access-token-requests/25381/repositories",
                organizationPatPermissions(),
                Instant.parse("2023-05-16T15:47:09Z"),
                98_716L,
                "Some Token",
                false,
                Instant.parse("2023-11-16T15:47:09Z"),
                Instant.parse("2022-04-28T12:00:00Z"));
    }

    static GitHubOrganizationPatPermissions organizationPatPermissions() {
        return new GitHubOrganizationPatPermissions(
                Map.of("members", "read"), Map.of("metadata", "read"), Map.of("organization_plan", "read"));
    }

    static GitHubOrganizationRole organizationRole() {
        return new GitHubOrganizationRole(
                8030L,
                "Custom Role Manager",
                "Permissions to manage custom roles within an org",
                GitHubOrganizationRoleBaseRole.ADMIN,
                GitHubOrganizationRoleSource.ORGANIZATION,
                List.of(
                        "write_organization_custom_repo_role",
                        "write_organization_custom_org_role",
                        "read_organization_custom_repo_role",
                        "read_organization_custom_org_role"),
                githubOrgActorUser(),
                Instant.parse("2022-07-04T22:19:11Z"),
                Instant.parse("2022-07-04T22:20:11Z"));
    }

    static GitHubOrganizationRolesPage organizationRolesPage() {
        return new GitHubOrganizationRolesPage(
                2,
                List.of(
                        organizationRole(),
                        new GitHubOrganizationRole(
                                8031L,
                                "Auditor",
                                "Permissions to read the organization audit log",
                                GitHubOrganizationRoleBaseRole.READ,
                                GitHubOrganizationRoleSource.ORGANIZATION,
                                List.of("read_audit_logs"),
                                githubOrgActorUser(),
                                Instant.parse("2022-07-04T22:19:11Z"),
                                Instant.parse("2022-07-04T22:20:11Z"))));
    }

    static GitHubOrganizationsAuthenticatedMembershipsFilter organizationsAuthenticatedMembershipsFilter() {
        return new GitHubOrganizationsAuthenticatedMembershipsFilter(GitHubOrgMembershipState.ACTIVE);
    }

    static GitHubOrganizationsCustomPropertyValuesFilter organizationsCustomPropertyValuesFilter() {
        return new GitHubOrganizationsCustomPropertyValuesFilter("is:public");
    }

    public static GitHubOrganizationSimple organizationSimple() {
        return new GitHubOrganizationSimple(
                "github",
                1L,
                "MDEyOk9yZ2FuaXphdGlvbjE=",
                "https://api.github.com/orgs/github",
                "https://api.github.com/orgs/github/repos",
                "https://api.github.com/orgs/github/events",
                "https://api.github.com/orgs/github/hooks",
                "https://api.github.com/orgs/github/issues",
                "https://api.github.com/orgs/github/members{/member}",
                "https://api.github.com/orgs/github/public_members{/member}",
                "https://github.com/images/error/octocat_happy.gif",
                "A great organization");
    }

    static GitHubOrganizationsMembersFilter organizationsMembersFilter() {
        return new GitHubOrganizationsMembersFilter(
                GitHubOrganizationMembersKind.ALL, GitHubOrganizationMembersListRole.ALL);
    }

    static GitHubOrganizationsOutsideCollaboratorsFilter organizationsOutsideCollaboratorsFilter() {
        return new GitHubOrganizationsOutsideCollaboratorsFilter(
                GitHubOrganizationOutsideCollaboratorsKind.TWO_FA_DISABLED);
    }

    static GitHubOrganizationsPendingInvitationsFilter organizationsPendingInvitationsFilter() {
        return new GitHubOrganizationsPendingInvitationsFilter(
                GitHubOrganizationPendingInvitationRole.ALL, GitHubOrganizationPendingInvitationSource.ALL);
    }

    static GitHubOrganizationsPersonalAccessTokensFilter organizationsPersonalAccessTokensFilter() {
        return new GitHubOrganizationsPersonalAccessTokensFilter(
                GitHubOrganizationPersonalAccessTokenSort.CREATED_AT,
                GitHubDirection.DESC,
                List.of("octocat"),
                "Hello-World",
                "metadata",
                Instant.parse("2024-01-01T00:00:00Z"),
                Instant.parse("2023-01-01T00:00:00Z"),
                List.of("42"));
    }

    static GitHubOrgHook orgHook() {
        return new GitHubOrgHook(
                1L,
                "https://api.github.com/orgs/octocat/hooks/1",
                "https://api.github.com/orgs/octocat/hooks/1/pings",
                "https://api.github.com/orgs/octocat/hooks/1/deliveries",
                "web",
                List.of("push", "pull_request"),
                true,
                orgHookConfig(),
                Instant.parse("2011-09-06T20:39:23Z"),
                Instant.parse("2011-09-06T17:26:27Z"),
                "Organization");
    }

    static GitHubOrgHookConfig orgHookConfig() {
        return new GitHubOrgHookConfig("http://example.com", "0", "json", "hook-secret");
    }

    static GitHubOrgMembership orgMembership() {
        return new GitHubOrgMembership(
                "https://api.github.com/orgs/octocat/memberships/defunkt",
                GitHubOrgMembershipState.ACTIVE,
                GitHubOrgMembershipRole.ADMIN,
                true,
                List.of("ent:justice-league", "ent:security-managers"),
                "https://api.github.com/orgs/octocat",
                organizationSimple(),
                octocat(),
                new GitHubOrgMembershipPermissions(true));
    }

    static GitHubOrgMembershipPermissions orgMembershipPermissions() {
        return new GitHubOrgMembershipPermissions(true);
    }

    static GitHubOrgRepoCustomPropertyValues orgRepoCustomPropertyValues() {
        return new GitHubOrgRepoCustomPropertyValues(
                1_296_269L,
                "Hello-World",
                "octocat/Hello-World",
                List.of(
                        new GitHubCustomPropertyValue("environment", List.of("production")),
                        new GitHubCustomPropertyValue("service", List.of("web")),
                        new GitHubCustomPropertyValue("team", List.of("octocat"))));
    }

    public static GitHubOrgRulesetConditions orgRulesetConditions() {
        return GitHubOrgRulesetConditions.byRepositoryName(
                new GitHubRepositoryRulesetConditionsRefName(List.of("refs/heads/main"), List.of()),
                new GitHubRepositoryRulesetConditionsRepositoryNameTargetRepositoryName(
                        List.of("*"), List.of(), false));
    }

    static GitHubTeamRoleAssignment teamRoleAssignment() {
        return new GitHubTeamRoleAssignment(
                GitHubOrganizationRoleAssignment.DIRECT,
                1L,
                "MDQ6VGVhbTE=",
                "Justice League",
                "justice-league",
                "A great team.",
                "closed",
                "notifications_enabled",
                "admin",
                new GitHubRepositoryPermissions(true, true, true, true, true),
                "https://api.github.com/organizations/1/team/1",
                "https://github.com/orgs/rails/teams/core",
                "https://api.github.com/organizations/1/team/1/members{/member}",
                "https://api.github.com/organizations/1/team/1/repos",
                justiceLeagueTeamSimple(),
                GitHubTeamOwnershipType.ORGANIZATION,
                37L,
                1L);
    }

    static GitHubUserRoleAssignment userRoleAssignment() {
        final GitHubSimpleUser user = octocat();
        return new GitHubUserRoleAssignment(
                GitHubOrganizationRoleAssignment.INDIRECT,
                List.of(justiceLeagueTeamSimple()),
                user.name(),
                user.email(),
                user.login(),
                user.id(),
                user.nodeId(),
                user.avatarUrl(),
                user.gravatarId(),
                user.url(),
                user.htmlUrl(),
                user.followersUrl(),
                user.followingUrl(),
                user.gistsUrl(),
                user.starredUrl(),
                user.subscriptionsUrl(),
                user.organizationsUrl(),
                user.reposUrl(),
                user.eventsUrl(),
                user.receivedEventsUrl(),
                GitHubUserType.USER,
                user.siteAdmin(),
                user.starredAt(),
                user.userViewType());
    }

    static GitHubListOrgRulesetsFilter listOrgRulesetsFilter() {
        return new GitHubListOrgRulesetsFilter("branch,tag");
    }

    static GitHubListOrgRuleSuitesFilter listOrgRuleSuitesFilter() {
        return new GitHubListOrgRuleSuitesFilter(
                "refs/heads/main",
                "Hello-World",
                GitHubRuleSuiteTimePeriod.DAY,
                "octocat",
                GitHubRuleSuiteListResult.PASS,
                GitHubRuleSuiteEvaluateStatus.ACTIVE);
    }

    static GitHubGetOrgRuleSuiteFilter getOrgRuleSuiteFilter() {
        return new GitHubGetOrgRuleSuiteFilter(true);
    }

    static GitHubReviewPatGrantRequestPayload reviewPatGrantRequestPayload() {
        return new GitHubReviewPatGrantRequestPayload(
                GitHubOrganizationPatGrantReviewAction.DENY,
                OptionalNull.of("This request is denied because the access is too broad."));
    }

    static GitHubReviewPatGrantRequestsInBulkPayload reviewPatGrantRequestsInBulkPayload() {
        return new GitHubReviewPatGrantRequestsInBulkPayload(
                List.of(42L, 73L),
                GitHubOrganizationPatGrantReviewAction.DENY,
                OptionalNull.of("Access is too broad."));
    }

    static GitHubSetClusterDeploymentRecordsPayload setClusterDeploymentRecordsPayload() {
        return new GitHubSetClusterDeploymentRecordsPayload(
                "prod", "us-east-1", List.of(clusterDeploymentRecordItem()), false, true);
    }

    static GitHubSetImmutableReleasesSelectedRepositoriesPayload setImmutableReleasesSelectedRepositoriesPayload() {
        return new GitHubSetImmutableReleasesSelectedRepositoriesPayload(List.of(64_780_797L));
    }

    static GitHubSetImmutableReleasesSettingsPayload setImmutableReleasesSettingsPayload() {
        return new GitHubSetImmutableReleasesSettingsPayload(
                GitHubImmutableReleasesEnforcedRepositories.ALL, List.of(64_780_797L));
    }

    static GitHubSetOrganizationMembershipPayload setOrganizationMembershipPayload() {
        return new GitHubSetOrganizationMembershipPayload(GitHubOrganizationMembershipRole.ADMIN);
    }

    static GitHubUpdateAuthenticatedOrganizationMembershipPayload updateAuthenticatedOrganizationMembershipPayload() {
        return new GitHubUpdateAuthenticatedOrganizationMembershipPayload(
                GitHubOrganizationAuthenticatedMembershipState.ACTIVE);
    }

    static GitHubUpdateIssueFieldOption updateIssueFieldOption() {
        return new GitHubUpdateIssueFieldOption(
                1L, "High", OptionalNull.of("High priority work"), GitHubIssueFieldOptionColor.RED, 0);
    }

    static GitHubUpdateIssueFieldPayload updateIssueFieldPayload() {
        return new GitHubUpdateIssueFieldPayload(
                "Priority",
                OptionalNull.of("Issue priority for triage"),
                GitHubIssueFieldVisibility.ORGANIZATION_MEMBERS_ONLY,
                List.of(updateIssueFieldOption()));
    }

    static GitHubUpdateIssueTypePayload updateIssueTypePayload() {
        return new GitHubUpdateIssueTypePayload(
                "Epic", true, OptionalNull.of("Large body of work"), OptionalNull.of(GitHubIssueTypeColor.PURPLE));
    }

    static GitHubUpdateNetworkConfigurationPayload updateNetworkConfigurationPayload() {
        return new GitHubUpdateNetworkConfigurationPayload(
                "my-network-configuration",
                GitHubCreateNetworkConfigurationComputeService.ACTIONS,
                List.of("23456789ABDCEF1"),
                List.of("456789ABDCEF123"),
                true);
    }

    static GitHubUpdateOrganizationPayload updateOrganizationPayload() {
        return new GitHubUpdateOrganizationPayload(
                "mona@github.com",
                "GitHub",
                "octocat@github.com",
                "github",
                "San Francisco",
                "github",
                "A great organization",
                true,
                true,
                GitHubOrganizationDefaultRepositoryPermission.READ,
                true,
                false,
                false,
                false,
                GitHubOrganizationMembersAllowedRepositoryCreationType.ALL,
                true,
                true,
                true,
                false,
                false,
                "https://github.com/blog",
                false,
                false,
                false,
                false,
                false,
                false,
                "https://github.com/octo-org/octo-repo/blob/main/im-blocked.md",
                false,
                false);
    }

    static GitHubUpdateOrgRulesetPayload updateOrgRulesetPayload() {
        return new GitHubUpdateOrgRulesetPayload(
                "main",
                GitHubRepositoryRulesetTarget.BRANCH,
                GitHubRepositoryRuleEnforcement.ACTIVE,
                List.of(new GitHubRepositoryRulesetBypassActor(
                        1L, GitHubRepositoryRulesetBypassActorType.TEAM, GitHubRepositoryRulesetBypassMode.ALWAYS)),
                orgRulesetConditions(),
                List.of(new GitHubRepositoryRuleCreation(GitHubRepositoryRuleCreationType.CREATION)));
    }

    static GitHubUpdateOrgWebhookConfig updateOrgWebhookConfig() {
        return new GitHubUpdateOrgWebhookConfig(
                URI.create("https://example.com/webhook"),
                "json",
                "very-secret",
                GitHubWebhookConfigInsecureSsl.text("0"));
    }

    static GitHubUpdateOrgWebhookPayload updateOrgWebhookPayload() {
        return new GitHubUpdateOrgWebhookPayload(updateOrgWebhookConfig(), List.of("push"), true, "web");
    }

    static GitHubUpdatePatAccessesPayload updatePatAccessesPayload() {
        return new GitHubUpdatePatAccessesPayload(GitHubOrganizationPatAccessUpdateAction.REVOKE, List.of(42L, 73L));
    }

    static GitHubUpdatePatAccessPayload updatePatAccessPayload() {
        return new GitHubUpdatePatAccessPayload(GitHubOrganizationPatAccessUpdateAction.REVOKE);
    }

    private static GitHubSimpleUser octocat() {
        return UsersCatalogFixtures.octocat();
    }

    private static GitHubSimpleUser octoOrgAccountUser() {
        return new GitHubSimpleUser(
                "Octo Org",
                "billing@octo-org.example.com",
                "octo-org",
                6_811_672L,
                "MDEyOk9yZ2FuaXphdGlvbjY4MTE2NzI=",
                "https://avatars3.githubusercontent.com/u/6811672?v=4",
                "",
                "https://api.github.com/users/octo-org",
                "https://github.com/octo-org",
                "https://api.github.com/users/octo-org/followers",
                "https://api.github.com/users/octo-org/following{/other_user}",
                "https://api.github.com/users/octo-org/gists{/gist_id}",
                "https://api.github.com/users/octo-org/starred{/owner}{/repo}",
                "https://api.github.com/users/octo-org/subscriptions",
                "https://api.github.com/users/octo-org/orgs",
                "https://api.github.com/users/octo-org/repos",
                "https://api.github.com/users/octo-org/events{/privacy}",
                "https://api.github.com/users/octo-org/received_events",
                "Organization",
                false,
                "2020-11-02T16:12:33Z",
                GitHubUserViewType.PUBLIC);
    }

    private static GitHubSimpleUser githubOrgActorUser() {
        return new GitHubSimpleUser(
                "GitHub",
                "support@github.com",
                "github",
                9919L,
                "MDEyOk9yZ2FuaXphdGlvbjk5MTk=",
                "https://avatars.githubusercontent.com/u/9919?v=4",
                "",
                "https://api.github.com/users/github",
                "https://github.com/github",
                "https://api.github.com/users/github/followers",
                "https://api.github.com/users/github/following{/other_user}",
                "https://api.github.com/users/github/gists{/gist_id}",
                "https://api.github.com/users/github/starred{/owner}{/repo}",
                "https://api.github.com/users/github/subscriptions",
                "https://api.github.com/users/github/orgs",
                "https://api.github.com/users/github/repos",
                "https://api.github.com/users/github/events{/privacy}",
                "https://api.github.com/users/github/received_events",
                "Organization",
                false,
                "2020-11-02T16:12:33Z",
                GitHubUserViewType.PUBLIC);
    }

    private static GitHubTeamSimple justiceLeagueTeamSimple() {
        return new GitHubTeamSimple(
                1L,
                "MDQ6VGVhbTE=",
                "https://api.github.com/organizations/1/team/1",
                "https://api.github.com/organizations/1/team/1/members{/member}",
                "Justice League",
                "A great team.",
                "admin",
                "closed",
                "notifications_enabled",
                "https://github.com/orgs/rails/teams/core",
                "https://api.github.com/organizations/1/team/1/repos",
                "justice-league",
                "uid=justice-league,ou=teams,dc=github,dc=com",
                GitHubTeamOwnershipType.ORGANIZATION,
                37L,
                1L);
    }

    private static GitHubAppPermissions appPermissionsFilled() {
        return GitHubAppPermissionsBuilder.builder()
                .actions(GitHubAppPermissionReadWrite.READ)
                .administration(GitHubAppPermissionReadWrite.READ)
                .artifactMetadata(GitHubAppPermissionReadWrite.READ)
                .attestations(GitHubAppPermissionReadWrite.READ)
                .checks(GitHubAppPermissionReadWrite.READ)
                .codeQuality(GitHubAppPermissionReadWrite.READ)
                .codespaces(GitHubAppPermissionReadWrite.READ)
                .contents(GitHubAppPermissionReadWrite.READ)
                .dependabotSecrets(GitHubAppPermissionReadWrite.READ)
                .deployments(GitHubAppPermissionReadWrite.READ)
                .discussions(GitHubAppPermissionReadWrite.READ)
                .environments(GitHubAppPermissionReadWrite.READ)
                .issues(GitHubAppPermissionReadWrite.READ)
                .mergeQueues(GitHubAppPermissionReadWrite.READ)
                .metadata(GitHubAppPermissionReadWrite.READ)
                .packages(GitHubAppPermissionReadWrite.READ)
                .pages(GitHubAppPermissionReadWrite.READ)
                .pullRequests(GitHubAppPermissionReadWrite.READ)
                .repositoryCustomProperties(GitHubAppPermissionReadWrite.READ)
                .repositoryHooks(GitHubAppPermissionReadWrite.READ)
                .repositoryProjects(GitHubAppPermissionReadWriteAdmin.READ)
                .secretScanningAlerts(GitHubAppPermissionReadWrite.READ)
                .secrets(GitHubAppPermissionReadWrite.READ)
                .securityEvents(GitHubAppPermissionReadWrite.READ)
                .singleFile(GitHubAppPermissionReadWrite.READ)
                .statuses(GitHubAppPermissionReadWrite.READ)
                .vulnerabilityAlerts(GitHubAppPermissionReadWrite.READ)
                .workflows(GitHubAppPermissionWrite.WRITE)
                .customPropertiesForOrganizations(GitHubAppPermissionReadWrite.READ)
                .members(GitHubAppPermissionReadWrite.READ)
                .organizationAdministration(GitHubAppPermissionReadWrite.READ)
                .organizationCustomRoles(GitHubAppPermissionReadWrite.READ)
                .organizationCustomOrgRoles(GitHubAppPermissionReadWrite.READ)
                .organizationCustomProperties(GitHubAppPermissionReadWriteAdmin.READ)
                .organizationCopilotSeatManagement(GitHubAppPermissionReadWrite.READ)
                .organizationCopilotAgentSettings(GitHubAppPermissionReadWrite.READ)
                .organizationCopilotRequests(GitHubAppPermissionRead.READ)
                .organizationExternalPropertiesForRepos(GitHubAppPermissionReadWriteAdmin.READ)
                .organizationAnnouncementBanners(GitHubAppPermissionReadWrite.READ)
                .organizationEvents(GitHubAppPermissionRead.READ)
                .organizationHooks(GitHubAppPermissionReadWrite.READ)
                .organizationPersonalAccessTokens(GitHubAppPermissionReadWrite.READ)
                .organizationPersonalAccessTokenRequests(GitHubAppPermissionReadWrite.READ)
                .organizationPlan(GitHubAppPermissionRead.READ)
                .organizationProjects(GitHubAppPermissionReadWriteAdmin.READ)
                .organizationPackages(GitHubAppPermissionReadWrite.READ)
                .organizationSecrets(GitHubAppPermissionReadWrite.READ)
                .organizationSelfHostedRunners(GitHubAppPermissionReadWrite.READ)
                .organizationUserBlocking(GitHubAppPermissionReadWrite.READ)
                .emailAddresses(GitHubAppPermissionReadWrite.READ)
                .followers(GitHubAppPermissionReadWrite.READ)
                .gitSshKeys(GitHubAppPermissionReadWrite.READ)
                .gpgKeys(GitHubAppPermissionReadWrite.READ)
                .interactionLimits(GitHubAppPermissionReadWrite.READ)
                .profile(GitHubAppPermissionWrite.WRITE)
                .enterpriseCustomProperties(GitHubAppPermissionReadWrite.READ)
                .starring(GitHubAppPermissionReadWrite.READ)
                .enterpriseCustomPropertiesForOrganizations(GitHubAppPermissionReadWriteAdmin.READ)
                .enterpriseAdministration(GitHubAppPermissionReadWrite.READ)
                .enterpriseOrganizationInstallationRepositories(GitHubAppPermissionReadWrite.READ)
                .enterpriseOrganizationInstallations(GitHubAppPermissionReadWrite.READ)
                .build();
    }

    static GitHubArtifactDeploymentRecordsPage artifactDeploymentRecordsPageWithoutErrors() {
        return new GitHubArtifactDeploymentRecordsPage(1, List.of(artifactDeploymentRecord()), null);
    }

    static GitHubArtifactListedStorageRecords artifactListedStorageRecordsActive() {
        return new GitHubArtifactListedStorageRecords(
                1,
                List.of(GitHubArtifactStorageRecordBuilder.builder(artifactStorageRecord())
                        .status("active")
                        .build()));
    }

    static List<GitHubApiInsightsTimeStat> apiInsightsTimeStats() {
        return List.of(
                new GitHubApiInsightsTimeStat("2024-09-11T15:00:00Z", 34_225L, 0L),
                new GitHubApiInsightsTimeStat("2024-09-11T15:05:00Z", 10_587L, 18L),
                new GitHubApiInsightsTimeStat("2024-09-11T15:10:00Z", 43_587L, 14L),
                new GitHubApiInsightsTimeStat("2024-09-11T15:15:00Z", 19_463L, 4L),
                new GitHubApiInsightsTimeStat("2024-09-11T15:20:00Z", 60_542L, 3L),
                new GitHubApiInsightsTimeStat("2024-09-11T15:25:00Z", 55_872L, 23L));
    }

    static List<GitHubAttestationRepository> attestationRepositories() {
        return List.of(new GitHubAttestationRepository(123L, "foo"), new GitHubAttestationRepository(456L, "bar"));
    }

    static GitHubApiInsightsUserStat apiInsightsUserStatWithoutIntegration() {
        return new GitHubApiInsightsUserStat(
                "oauth_app",
                "GitHub Actions",
                954_453L,
                null,
                1245L,
                544_665,
                13,
                "2024-09-18T06:30:09Z",
                "2024-09-18T15:43:03Z");
    }

    static List<GitHubCustomProperty> customPropertyDefinitions() {
        return List.of(
                customProperty(),
                new GitHubCustomProperty(
                        "service",
                        "https://api.github.com/orgs/github/properties/schema/service",
                        GitHubCustomPropertySourceType.ORGANIZATION,
                        GitHubCustomPropertyValueType.STRING,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null),
                new GitHubCustomProperty(
                        "team",
                        "https://api.github.com/orgs/github/properties/schema/team",
                        GitHubCustomPropertySourceType.ORGANIZATION,
                        GitHubCustomPropertyValueType.STRING,
                        null,
                        null,
                        "Team owning the repository",
                        null,
                        null,
                        null));
    }

    static List<GitHubIssueField> issueFieldItems() {
        final Instant createdAt = Instant.parse("2024-12-11T14:39:09Z");
        return List.of(
                new GitHubIssueField(
                        1,
                        "IFT_kwDNAd3NAZo",
                        "Text field",
                        "DRI",
                        GitHubIssueFieldDataType.TEXT,
                        null,
                        null,
                        createdAt,
                        createdAt),
                new GitHubIssueField(
                        2,
                        "IFSS_kwDNAd3NAZs",
                        "Priority",
                        "Level of importance",
                        GitHubIssueFieldDataType.SINGLE_SELECT,
                        null,
                        List.of(
                                new GitHubIssueFieldOption(
                                        1, "High", null, GitHubIssueFieldOptionColor.RED, null, null, null),
                                new GitHubIssueFieldOption(
                                        2, "Medium", null, GitHubIssueFieldOptionColor.YELLOW, null, null, null),
                                new GitHubIssueFieldOption(
                                        3, "Low", null, GitHubIssueFieldOptionColor.GREEN, null, null, null)),
                        createdAt,
                        createdAt),
                new GitHubIssueField(
                        3,
                        "IFMS_kwDNAd3NAZt",
                        "Categories",
                        "Issue categories",
                        GitHubIssueFieldDataType.MULTI_SELECT,
                        null,
                        List.of(
                                new GitHubIssueFieldOption(
                                        4, "Frontend", null, GitHubIssueFieldOptionColor.BLUE, null, null, null),
                                new GitHubIssueFieldOption(
                                        5, "Backend", null, GitHubIssueFieldOptionColor.GREEN, null, null, null),
                                new GitHubIssueFieldOption(
                                        6,
                                        "Infrastructure",
                                        null,
                                        GitHubIssueFieldOptionColor.PURPLE,
                                        null,
                                        null,
                                        null)),
                        createdAt,
                        createdAt));
    }

    public static GitHubSimpleUser usersOne() {
        return GitHubSimpleUserBuilder.builder()
                .login("one")
                .id(1L)
                .nodeId("n1")
                .avatarUrl("https://example.com/a")
                .gravatarId("")
                .url("https://api.github.com/users/one")
                .htmlUrl("https://github.com/one")
                .followersUrl("https://api.github.com/users/one/followers")
                .followingUrl("https://api.github.com/users/one/following{/other_user}")
                .gistsUrl("https://api.github.com/users/one/gists{/gist_id}")
                .starredUrl("https://api.github.com/users/one/starred{/owner}{/repo}")
                .subscriptionsUrl("https://api.github.com/users/one/subscriptions")
                .organizationsUrl("https://api.github.com/users/one/orgs")
                .reposUrl("https://api.github.com/users/one/repos")
                .eventsUrl("https://api.github.com/users/one/events{/privacy}")
                .receivedEventsUrl("https://api.github.com/users/one/received_events")
                .type("User")
                .siteAdmin(false)
                .build();
    }

    static GitHubOrganizationPatGrantRequest organizationPatGrantRequestFromStub() {
        return new GitHubOrganizationPatGrantRequest(
                25_381L,
                "I need to access the GitHub API",
                UsersCatalogFixtures.octocatWithoutOptionalFields(),
                GitHubOrganizationPatRepositorySelection.ALL,
                "https://api.github.com/organizations/652551/personal-access-token-requests/25381/repositories",
                new GitHubOrganizationPatPermissions(Map.of("members", "read"), Map.of("metadata", "read"), null),
                Instant.parse("2023-05-16T15:47:09Z"),
                98_716L,
                "Some Token",
                false,
                Instant.parse("2023-11-16T15:47:09Z"),
                null);
    }

    static GitHubOrganizationPatGrant organizationPatGrantFromStub() {
        return new GitHubOrganizationPatGrant(
                25_381L,
                UsersCatalogFixtures.octocatWithoutOptionalFields(),
                GitHubOrganizationPatRepositorySelection.ALL,
                "https://api.github.com/organizations/652551/personal-access-tokens/25381/repositories",
                new GitHubOrganizationPatPermissions(Map.of("members", "read"), Map.of("metadata", "read"), null),
                Instant.parse("2023-05-16T15:47:09Z"),
                98_716L,
                "Some Token",
                false,
                Instant.parse("2023-11-16T15:47:09Z"),
                null);
    }

    static GitHubArtifactDeploymentRecordsPage artifactDeploymentRecordsPartialSuccess() {
        return new GitHubArtifactDeploymentRecordsPage(
                1,
                List.of(new GitHubArtifactDeploymentRecord(
                        42L,
                        "sha256:1bb1e949e55dcefc6353e7b36c8897d2a107d8e8dca49d4e3c0ea8493fc0bc72",
                        "prod",
                        "us-east-1a",
                        "production-cluster",
                        "deployment-pod",
                        Map.of("owning-team", "platform"),
                        List.of(GitHubArtifactRuntimeRisk.SENSITIVE_DATA),
                        "2024-01-01T12:00:00Z",
                        "2024-01-01T12:30:00Z",
                        777L)),
                List.of(new GitHubArtifactDeploymentError(
                        GitHubArtifactDeploymentErrorCause.UNAUTHORIZED,
                        new GitHubArtifactDeploymentErrorDeployment(
                                "my-image",
                                "sha256:5aa19f9a13f67a644f6d8528e2eb5cf64431e236df5dbbd44ff9b9e3f6fc6cb2",
                                "restricted-deployment",
                                "2.1.1",
                                "deployed",
                                "private-repo",
                                Map.of("owning-team", "platform"),
                                List.of("sensitive-data")))));
    }

    public static GitHubOrganizationActionsSecret organizationActionsSecret() {
        return new GitHubOrganizationActionsSecret(
                "GH_TOKEN",
                Instant.parse("2019-08-10T14:59:22Z"),
                Instant.parse("2020-01-10T14:59:22Z"),
                GitHubOrganizationActionsSecretVisibility.SELECTED,
                "https://api.github.com/orgs/octo-org/actions/secrets/GH_TOKEN/repositories");
    }

    public static GitHubOrganizationActionsVariable organizationActionsVariable() {
        return new GitHubOrganizationActionsVariable(
                "USERNAME",
                "octocat",
                Instant.parse("2019-01-24T22:45:36Z"),
                Instant.parse("2019-01-24T22:45:36Z"),
                GitHubOrganizationActionsVariableVisibility.SELECTED,
                "https://api.github.com/organizations/org/variables/USERNAME/repositories");
    }

    public static GitHubOrganizationCustomProperty organizationCustomProperty() {
        return GitHubOrganizationCustomPropertyBuilder.builder()
                .propertyName("environment")
                .url("https://api.github.com/enterprises/octo-enterprise/properties/schema/environment")
                .sourceType(GitHubCustomPropertyBaseSourceType.ENTERPRISE)
                .valueType(GitHubCustomPropertyBaseValueType.SINGLE_SELECT)
                .required(true)
                .defaultValue(List.of("production"))
                .description("Deployment environment")
                .allowedValues(List.of("production", "staging"))
                .valuesEditableBy(GitHubOrganizationCustomPropertyValuesEditableBy.ENTERPRISE_ACTORS)
                .build();
    }

    public static GitHubOrganizationCustomPropertyPayload organizationCustomPropertyPayload() {
        return GitHubOrganizationCustomPropertyPayloadBuilder.builder()
                .valueType(GitHubOrganizationCustomPropertyPayloadValueType.SINGLE_SELECT)
                .required(true)
                .defaultValue(List.of("production"))
                .description("Deployment environment")
                .allowedValues(List.of("production", "staging"))
                .valuesEditableBy(GitHubOrganizationCustomPropertyPayloadValuesEditableBy.ENTERPRISE_ACTORS)
                .build();
    }

    public static GitHubGetAuditLogFilter getAuditLogFilter() {
        return new GitHubGetAuditLogFilter("action:team.add", GitHubAuditLogInclude.ALL, GitHubAuditLogOrder.DESC);
    }

    public static GitHubListSamlSsoAuthorizationsFilter listSamlSsoAuthorizationsFilter() {
        return new GitHubListSamlSsoAuthorizationsFilter("monalisa");
    }

    public static GitHubListPushBypassRequestsFilter listPushBypassRequestsFilter() {
        return new GitHubListPushBypassRequestsFilter(
                "octo-repo", "octocat", "hubot", GitHubRuleSuiteTimePeriod.WEEK, GitHubBypassRequestStatus.OPEN);
    }

    public static GitHubCredentialAuthorization credentialAuthorization() {
        return new GitHubCredentialAuthorization(
                "octocat",
                1L,
                GitHubCredentialAuthorizationCredentialType.SSH_KEY,
                "12345678",
                Instant.parse("2011-01-26T19:06:43Z"),
                List.of("repo"),
                "jklmnop12345678",
                Instant.parse("2011-01-26T19:06:43Z"),
                2L,
                "Deploy key",
                "CI",
                Instant.parse("2026-12-31T00:00:00Z"));
    }

    public static GitHubRevokeAllCredentialAuthorizationsResponse revokeAllCredentialAuthorizationsResponse() {
        return new GitHubRevokeAllCredentialAuthorizationsResponse(
                "Revocation queued.", "Revoking all credential authorizations is irreversible.");
    }

    public static GitHubRevokeCredentialTypeResponse revokeCredentialTypeResponse() {
        return new GitHubRevokeCredentialTypeResponse(
                "Revocation queued.", "Revoking this credential type is irreversible.");
    }

    public static GitHubRevokeCredentialAuthorizationsForUserResponse revokeCredentialAuthorizationsForUserResponse() {
        return new GitHubRevokeCredentialAuthorizationsForUserResponse(
                "Revocation queued.", "Revoking this user's credential authorizations is irreversible.");
    }

    public static GitHubRevokeCredentialTypeForUserResponse revokeCredentialTypeForUserResponse() {
        return new GitHubRevokeCredentialTypeForUserResponse(
                "Revocation queued.", "Revoking this user's credential type is irreversible.");
    }

    public static GitHubRevokeCredentialTypePayload revokeCredentialTypePayload() {
        return new GitHubRevokeCredentialTypePayload(GitHubRevokeCredentialTypeCredentialType.CLASSIC_PAT);
    }

    public static GitHubRevokeCredentialTypeForUserPayload revokeCredentialTypeForUserPayload() {
        return new GitHubRevokeCredentialTypeForUserPayload(GitHubRevokeCredentialTypeForUserCredentialType.SSH_KEY);
    }

    public static GitHubOrganizationFineGrainedPermission organizationFineGrainedPermission() {
        return new GitHubOrganizationFineGrainedPermission(
                "organization_administration", "Manage the organization settings.");
    }

    public static GitHubRepositoryFineGrainedPermission repositoryFineGrainedPermission() {
        return new GitHubRepositoryFineGrainedPermission(
                "repository_contents", "Read and write the repository contents.");
    }

    public static GitHubCreateCustomOrganizationRolePayload createCustomOrganizationRolePayload() {
        return new GitHubCreateCustomOrganizationRolePayload(
                "Auditor",
                "Read-only auditor",
                List.of("read_audit_logs"),
                GitHubCustomOrganizationRoleCreateBaseRole.READ);
    }

    public static GitHubPatchCustomOrganizationRolePayload patchCustomOrganizationRolePayload() {
        return new GitHubPatchCustomOrganizationRolePayload(
                "Auditor",
                "Updated auditor",
                List.of("read_audit_logs"),
                GitHubCustomOrganizationRoleUpdateBaseRole.NONE);
    }

    public static GitHubOrganizationCustomRepositoryRole organizationCustomRepositoryRole() {
        return new GitHubOrganizationCustomRepositoryRole(
                1L,
                "Reviewer",
                "Reviews pull requests",
                GitHubOrganizationCustomRepositoryRoleBaseRole.READ,
                List.of("pull"),
                UsersCatalogFixtures.octocat(),
                Instant.parse("2026-03-04T10:00:00Z"),
                Instant.parse("2026-03-05T10:00:00Z"));
    }

    public static GitHubCreateCustomRepoRolePayload createCustomRepoRolePayload() {
        return new GitHubCreateCustomRepoRolePayload(
                "Reviewer", "Reviews pull requests", GitHubCustomRepositoryRoleCreateBaseRole.READ, List.of("pull"));
    }

    public static GitHubCreateCustomRolePayload createCustomRolePayload() {
        return new GitHubCreateCustomRolePayload(
                "Reviewer", "Reviews pull requests", GitHubCustomRepositoryRoleCreateBaseRole.READ, List.of("pull"));
    }

    public static GitHubUpdateCustomRepoRolePayload updateCustomRepoRolePayload() {
        return new GitHubUpdateCustomRepoRolePayload(
                "Reviewer", "Reviews and pushes", GitHubCustomRepositoryRoleUpdateBaseRole.WRITE, List.of("push"));
    }

    public static GitHubUpdateCustomRolePayload updateCustomRolePayload() {
        return new GitHubUpdateCustomRolePayload(
                "Reviewer", "Reviews and pushes", GitHubCustomRepositoryRoleUpdateBaseRole.WRITE, List.of("push"));
    }

    public static GitHubCustomPropertiesForOrgsCreateOrUpdateValuesPayload
            customPropertiesForOrgsCreateOrUpdateValuesPayload() {
        return new GitHubCustomPropertiesForOrgsCreateOrUpdateValuesPayload(
                List.of(new GitHubCustomPropertyValue("environment", List.of("production"))));
    }

    public static GitHubListCustomRepoRolesResponse listCustomRepoRolesResponse() {
        return new GitHubListCustomRepoRolesResponse(1, List.of(organizationCustomRepositoryRole()));
    }
}
