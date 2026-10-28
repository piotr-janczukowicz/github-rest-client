package io.github.piotr_janczukowicz.github.api.dependabot;

import io.github.piotr_janczukowicz.github.api.GitHubDirection;
import io.github.piotr_janczukowicz.github.api.codescanning.CodeScanningCatalogFixtures;
import io.github.piotr_janczukowicz.github.api.codescanning.GitHubAlertDismissalRequestStatus;
import io.github.piotr_janczukowicz.github.api.codescanning.GitHubAlertDismissalTimePeriod;
import io.github.piotr_janczukowicz.github.api.repositories.GitHubRuleSuiteTimePeriod;
import io.github.piotr_janczukowicz.github.api.repositories.RepositoriesCatalogFixtures;
import io.github.piotr_janczukowicz.github.api.securityadvisories.GitHubCvssScore;
import io.github.piotr_janczukowicz.github.api.securityadvisories.GitHubCvssSeverities;
import io.github.piotr_janczukowicz.github.api.securityadvisories.GitHubSecurityAdvisoryEpss;
import io.github.piotr_janczukowicz.github.api.users.UsersCatalogFixtures;
import java.time.Instant;
import java.util.List;

final class DependabotCatalogFixtures {

    private static final String ENCRYPTED_VALUE = "c2VjcmV0";
    private static final String KEY_ID = "012345678912345678";
    private static final Instant SECRET_CREATED_AT = Instant.parse("2019-08-10T14:59:22Z");
    private static final Instant SECRET_UPDATED_AT = Instant.parse("2020-01-10T14:59:22Z");

    private DependabotCatalogFixtures() {}

    static GitHubDependabotAlertPackage dependabotAlertPackage() {
        return new GitHubDependabotAlertPackage("pip", "django");
    }

    static GitHubDependabotAlertFirstPatchedVersion dependabotAlertFirstPatchedVersion() {
        return new GitHubDependabotAlertFirstPatchedVersion("2.0.2");
    }

    static GitHubDependabotAlertSecurityVulnerability dependabotAlertSecurityVulnerability() {
        return GitHubDependabotAlertSecurityVulnerabilityBuilder.builder()
                .vulnerablePackage(dependabotAlertPackage())
                .severity(GitHubDependabotAlertSeverity.HIGH)
                .vulnerableVersionRange(">= 2.0.0, < 2.0.2")
                .firstPatchedVersion(dependabotAlertFirstPatchedVersion())
                .build();
    }

    static GitHubDependabotAlertSecurityAdvisoryCwe dependabotAlertSecurityAdvisoryCwe() {
        return new GitHubDependabotAlertSecurityAdvisoryCwe(
                "CWE-200", "Exposure of Sensitive Information to an Unauthorized Actor");
    }

    static GitHubDependabotAlertSecurityAdvisoryIdentifier dependabotAlertSecurityAdvisoryIdentifier() {
        return new GitHubDependabotAlertSecurityAdvisoryIdentifier(
                GitHubDependabotAlertSecurityAdvisoryIdentifierType.GHSA, "GHSA-rf4j-j272-fj86");
    }

    static GitHubDependabotAlertSecurityAdvisoryReference dependabotAlertSecurityAdvisoryReference() {
        return new GitHubDependabotAlertSecurityAdvisoryReference("https://nvd.nist.gov/vuln/detail/CVE-2018-6188");
    }

    static GitHubDependabotAlertSecurityAdvisory dependabotAlertSecurityAdvisory() {
        return GitHubDependabotAlertSecurityAdvisoryBuilder.builder()
                .ghsaId("GHSA-rf4j-j272-fj86")
                .cveId("CVE-2018-6188")
                .summary("Django allows remote attackers to obtain potentially sensitive information")
                .description("django.contrib.auth.forms.AuthenticationForm in Django 2.0 before 2.0.2 allows remote"
                        + " attackers to obtain potentially sensitive information.")
                .vulnerabilities(List.of(dependabotAlertSecurityVulnerability()))
                .severity(GitHubDependabotAlertSeverity.HIGH)
                .classification(GitHubDependabotAlertClassification.GENERAL)
                .cvssSeverities(new GitHubCvssSeverities(
                        new GitHubCvssScore("CVSS:3.0/AV:N/AC:L/PR:N/UI:N/S:U/C:H/I:N/A:N", 7.5),
                        new GitHubCvssScore("CVSS:4.0/AV:N/AC:L/AT:N/PR:N/UI:N/VC:H/VI:N/VA:N/SC:N/SI:N/SA:N", 8.7)))
                .epss(new GitHubSecurityAdvisoryEpss(0.000_45, 0.16))
                .cwes(List.of(dependabotAlertSecurityAdvisoryCwe()))
                .identifiers(List.of(dependabotAlertSecurityAdvisoryIdentifier()))
                .references(List.of(dependabotAlertSecurityAdvisoryReference()))
                .publishedAt(Instant.parse("2018-10-03T21:13:54Z"))
                .updatedAt(Instant.parse("2022-04-26T18:35:37Z"))
                .withdrawnAt(Instant.parse("2022-05-01T00:00:00Z"))
                .build();
    }

    static GitHubDependabotAlertDependency dependabotAlertDependency() {
        return GitHubDependabotAlertDependencyBuilder.builder()
                .dependencyPackage(dependabotAlertPackage())
                .manifestPath("path/to/requirements.txt")
                .versionRequirement("2.31.0")
                .scope(GitHubDependabotAlertDependencyScope.RUNTIME)
                .relationship(GitHubDependabotAlertDependencyRelationship.DIRECT)
                .build();
    }

    static GitHubDependabotAlertDismissalRequestRequester dependabotAlertDismissalRequestRequester() {
        return new GitHubDependabotAlertDismissalRequestRequester(1L, "octocat");
    }

    static GitHubDependabotAlertDismissalRequest dependabotAlertDismissalRequest() {
        return GitHubDependabotAlertDismissalRequestBuilder.builder()
                .id(42L)
                .status(GitHubDependabotAlertDismissalRequestStatus.PENDING)
                .requester(dependabotAlertDismissalRequestRequester())
                .createdAt(Instant.parse("2022-08-20T10:00:00Z"))
                .url("https://api.github.com/repos/octocat/hello-world/dependabot/alerts/2/dismissal-requests/42")
                .build();
    }

    static GitHubDependabotAlert dependabotAlert() {
        return GitHubDependabotAlertBuilder.builder()
                .number(2)
                .state(GitHubDependabotAlertState.DISMISSED)
                .dependency(dependabotAlertDependency())
                .securityAdvisory(dependabotAlertSecurityAdvisory())
                .securityVulnerability(dependabotAlertSecurityVulnerability())
                .url("https://api.github.com/repos/octocat/hello-world/dependabot/alerts/2")
                .htmlUrl("https://github.com/octocat/hello-world/security/dependabot/2")
                .createdAt(Instant.parse("2022-06-15T07:43:03Z"))
                .updatedAt(Instant.parse("2022-08-23T14:29:47Z"))
                .dismissedAt(Instant.parse("2022-08-23T14:29:47Z"))
                .dismissedBy(UsersCatalogFixtures.octocat())
                .dismissedReason(GitHubDependabotAlertDismissedReason.TOLERABLE_RISK)
                .dismissedComment("This alert is accurate but we use a sanitizer.")
                .fixedAt(Instant.parse("2022-09-01T00:00:00Z"))
                .autoDismissedAt(Instant.parse("2022-08-23T14:29:47Z"))
                .dismissalRequest(dependabotAlertDismissalRequest())
                .assignees(List.of(UsersCatalogFixtures.octocat()))
                .build();
    }

    static GitHubDependabotAlertWithRepository dependabotAlertWithRepository() {
        return GitHubDependabotAlertWithRepositoryBuilder.builder()
                .number(2)
                .state(GitHubDependabotAlertState.DISMISSED)
                .dependency(dependabotAlertDependency())
                .securityAdvisory(dependabotAlertSecurityAdvisory())
                .securityVulnerability(dependabotAlertSecurityVulnerability())
                .url("https://api.github.com/repos/octo-org/octo-repo/dependabot/alerts/2")
                .htmlUrl("https://github.com/octo-org/octo-repo/security/dependabot/2")
                .createdAt(Instant.parse("2022-06-15T07:43:03Z"))
                .updatedAt(Instant.parse("2022-08-23T14:29:47Z"))
                .dismissedAt(Instant.parse("2022-08-23T14:29:47Z"))
                .dismissedBy(UsersCatalogFixtures.octocat())
                .dismissedReason(GitHubDependabotAlertDismissedReason.TOLERABLE_RISK)
                .dismissedComment("This alert is accurate but we use a sanitizer.")
                .fixedAt(Instant.parse("2022-09-01T00:00:00Z"))
                .autoDismissedAt(Instant.parse("2022-08-23T14:29:47Z"))
                .dismissalRequest(dependabotAlertDismissalRequest())
                .assignees(List.of(UsersCatalogFixtures.octocat()))
                .repository(RepositoriesCatalogFixtures.helloWorldSimpleRepository())
                .build();
    }

    static GitHubDependabotAlertAgentAssignment dependabotAlertAgentAssignment() {
        return new GitHubDependabotAlertAgentAssignment("Prefer minimal upgrades.", "security-fixer", "claude-sonnet");
    }

    static GitHubDependabotPublicKey dependabotPublicKey() {
        return new GitHubDependabotPublicKey(KEY_ID, "2Sg8iYjAxxmI2LvUXpJjkYrMxURPc8r+dB7TJyvv1234");
    }

    static GitHubDependabotRepositoryAccessDetails dependabotRepositoryAccessDetails() {
        return new GitHubDependabotRepositoryAccessDetails(
                GitHubDependabotRepositoryAccessDefaultLevel.PUBLIC,
                List.of(RepositoriesCatalogFixtures.helloWorldSimpleRepository()));
    }

    static GitHubDependabotSecret dependabotSecret() {
        return new GitHubDependabotSecret("MY_ARTIFACTORY_PASSWORD", SECRET_CREATED_AT, SECRET_UPDATED_AT);
    }

    static GitHubOrgDependabotSecret orgDependabotSecret() {
        return new GitHubOrgDependabotSecret(
                "NPM_TOKEN",
                SECRET_CREATED_AT,
                SECRET_UPDATED_AT,
                GitHubDependabotSecretVisibility.SELECTED,
                "https://api.github.com/orgs/octo-org/dependabot/secrets/NPM_TOKEN/repositories");
    }

    static GitHubOrgDependabotSecretsPage orgDependabotSecretsPage() {
        return new GitHubOrgDependabotSecretsPage(1, List.of(orgDependabotSecret()));
    }

    static GitHubDependabotSecretsPage dependabotSecretsPage() {
        return new GitHubDependabotSecretsPage(1, List.of(dependabotSecret()));
    }

    static GitHubDependabotSelectedRepositoriesPage dependabotSelectedRepositoriesPage() {
        return new GitHubDependabotSelectedRepositoriesPage(
                1, List.of(RepositoriesCatalogFixtures.helloWorldMinimalRepository()));
    }

    static GitHubUpdateAlertPayload updateAlertPayload() {
        return GitHubUpdateAlertPayloadBuilder.builder()
                .state(GitHubDependabotAlertUpdateState.DISMISSED)
                .dismissedReason(GitHubDependabotAlertDismissedReason.TOLERABLE_RISK)
                .dismissedComment("This alert is accurate but we use a sanitizer.")
                .assignees(List.of("octocat"))
                .agentAssignment(dependabotAlertAgentAssignment())
                .build();
    }

    static GitHubUpdateRepositoryAccessPayload updateRepositoryAccessPayload() {
        return new GitHubUpdateRepositoryAccessPayload(List.of(123L, 456L), List.of(789L));
    }

    static GitHubSetRepositoryAccessDefaultLevelPayload setRepositoryAccessDefaultLevelPayload() {
        return new GitHubSetRepositoryAccessDefaultLevelPayload(GitHubDependabotRepositoryAccessDefaultLevel.INTERNAL);
    }

    static GitHubSetSelectedReposForOrgSecretPayload setSelectedReposForOrgSecretPayload() {
        return new GitHubSetSelectedReposForOrgSecretPayload(List.of(64_780_797L));
    }

    static GitHubCreateOrUpdateOrgSecretPayload createOrUpdateOrgSecretPayload() {
        return new GitHubCreateOrUpdateOrgSecretPayload(
                ENCRYPTED_VALUE, KEY_ID, GitHubDependabotSecretVisibility.SELECTED, List.of(1_296_269L, 1_296_280L));
    }

    static GitHubCreateOrUpdateRepoSecretPayload createOrUpdateRepoSecretPayload() {
        return new GitHubCreateOrUpdateRepoSecretPayload(ENCRYPTED_VALUE, KEY_ID);
    }

    static GitHubListAlertsForEnterpriseFilter listAlertsForEnterpriseFilter() {
        return GitHubListAlertsForEnterpriseFilterBuilder.builder()
                .classification("general,malware")
                .state("open,fixed")
                .severity("high,critical")
                .ecosystem("npm,pip")
                .packages("django,lodash")
                .epssPercentage(">0.5")
                .has(List.of("patch"))
                .assignee("octocat")
                .scope(GitHubDependabotAlertScope.RUNTIME)
                .relationship("direct")
                .sort(GitHubDependabotAlertSort.EPSS_PERCENTAGE)
                .direction(GitHubDirection.ASC)
                .build();
    }

    static GitHubListAlertsForOrgFilter listAlertsForOrgFilter() {
        return GitHubListAlertsForOrgFilterBuilder.builder()
                .classification("general,malware")
                .state("open,fixed")
                .severity("high,critical")
                .ecosystem("npm,pip")
                .packages("django,lodash")
                .epssPercentage(">0.5")
                .artifactRegistryUrl("https://octo.jfrog.io/artifactory")
                .artifactRegistry("jfrog-artifactory")
                .has(List.of("patch", "deployment"))
                .assignee("octocat")
                .runtimeRisk("internet-exposed,sensitive-data")
                .scope(GitHubDependabotAlertScope.DEVELOPMENT)
                .relationship("transitive")
                .sort(GitHubDependabotAlertSort.UPDATED)
                .direction(GitHubDirection.DESC)
                .build();
    }

    static GitHubListAlertsForOrgFilter listAlertsForOrgFilterHasString() {
        return GitHubListAlertsForOrgFilterBuilder.builder(listAlertsForOrgFilter())
                .has(List.of("patch"))
                .build();
    }

    static GitHubListAlertsForRepoFilter listAlertsForRepoFilter() {
        return GitHubListAlertsForRepoFilterBuilder.builder()
                .classification("general,malware")
                .state("open,fixed")
                .severity("high,critical")
                .ecosystem("npm,pip")
                .packages("django,lodash")
                .manifest("path/to/requirements.txt")
                .epssPercentage(">0.5")
                .has(List.of("patch"))
                .assignee("octocat")
                .scope(GitHubDependabotAlertScope.RUNTIME)
                .relationship("direct")
                .sort(GitHubDependabotAlertSort.CREATED)
                .direction(GitHubDirection.DESC)
                .build();
    }

    public static GitHubDependabotDismissalRequestRepository dependabotDismissalRequestRepository() {
        return new GitHubDependabotDismissalRequestRepository(1L, "octo-repo", "octo-org/octo-repo");
    }

    public static GitHubDependabotDismissalRequestOrganization dependabotDismissalRequestOrganization() {
        return new GitHubDependabotDismissalRequestOrganization(1L, "octo-org");
    }

    public static GitHubDependabotDismissalRequestRequester dependabotDismissalRequestRequester() {
        return new GitHubDependabotDismissalRequestRequester(583_231L, "octocat");
    }

    public static GitHubDependabotDismissalRequestData dependabotDismissalRequestData() {
        return new GitHubDependabotDismissalRequestData("fix_started", "1", "Prototype pollution in lodash");
    }

    public static GitHubDependabotDismissalRequest dependabotDismissalRequest() {
        return new GitHubDependabotDismissalRequest(
                1L,
                1L,
                dependabotDismissalRequestRepository(),
                dependabotDismissalRequestOrganization(),
                dependabotDismissalRequestRequester(),
                "dependabot_alert",
                List.of(dependabotDismissalRequestData()),
                "1",
                GitHubDependabotDismissalRequestStatus.PENDING,
                "Please dismiss this alert.",
                Instant.parse("2026-09-01T00:00:00Z"),
                Instant.parse("2026-08-01T10:15:30Z"),
                List.of(CodeScanningCatalogFixtures.dismissalRequestResponse()),
                "https://api.github.com/repos/octo-org/octo-repo/dismissal-requests/dependabot/1",
                "https://github.com/octo-org/octo-repo/security/dismissal-requests/dependabot/1");
    }

    public static GitHubListDismissalRequestsForOrgFilter listDismissalRequestsForOrgFilter() {
        return GitHubListDismissalRequestsForOrgFilterBuilder.builder()
                .repositoryName("octo-repo")
                .reviewer("octocat")
                .requester("hubot")
                .timePeriod(GitHubRuleSuiteTimePeriod.DAY)
                .requestStatus(GitHubDismissalRequestStatus.APPROVED)
                .build();
    }

    public static GitHubListDismissalRequestsForRepoFilter listDismissalRequestsForRepoFilter() {
        return GitHubListDismissalRequestsForRepoFilterBuilder.builder()
                .reviewer("octocat")
                .requester("hubot")
                .timePeriod(GitHubAlertDismissalTimePeriod.MONTH)
                .requestStatus(GitHubAlertDismissalRequestStatus.OPEN)
                .build();
    }

    public static GitHubCreateDismissalRequestPayload createDismissalRequestPayload() {
        return new GitHubCreateDismissalRequestPayload(
                GitHubCreateDismissalRequestDismissedReason.NOT_USED, "Not used in production.");
    }

    public static GitHubReviewDismissalRequestPayload reviewDismissalRequestPayload() {
        return new GitHubReviewDismissalRequestPayload(GitHubReviewDismissalRequestStatus.DENY, "Please review again.");
    }

    public static GitHubReviewDismissalRequestResult reviewDismissalRequestResult() {
        return new GitHubReviewDismissalRequestResult(1L);
    }
}
