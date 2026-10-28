package io.github.piotr_janczukowicz.github.api.codescanning;

import io.github.piotr_janczukowicz.github.api.GitHubDirection;
import io.github.piotr_janczukowicz.github.api.repositories.RepositoriesCatalogFixtures;
import io.github.piotr_janczukowicz.github.api.users.UsersCatalogFixtures;
import java.net.URI;
import java.time.Instant;
import java.util.List;

public final class CodeScanningCatalogFixtures {

    private static final Instant CREATED_AT = Instant.parse("2025-01-01T00:00:00Z");
    private static final Instant UPDATED_AT = Instant.parse("2025-01-02T10:15:30Z");

    private CodeScanningCatalogFixtures() {}

    public static GitHubDismissalRequestResponse dismissalRequestResponse() {
        return GitHubDismissalRequestResponseBuilder.builder()
                .id(1L)
                .reviewer(dismissalRequestResponseReviewer())
                .message("Accepted the risk for this fixture.")
                .status(GitHubDismissalRequestResponseStatus.APPROVED)
                .createdAt(Instant.parse("2026-08-01T10:15:30Z"))
                .build();
    }

    static GitHubDismissalRequestResponseReviewer dismissalRequestResponseReviewer() {
        return GitHubDismissalRequestResponseReviewerBuilder.builder()
                .actorId(583_231L)
                .actorName("octocat")
                .build();
    }

    public static GitHubCodeScanningAlert codeScanningAlert() {
        return GitHubCodeScanningAlertBuilder.builder()
                .number(1)
                .createdAt(CREATED_AT)
                .updatedAt(UPDATED_AT)
                .url("https://api.github.com/repos/octo-org/octo-repo/code-scanning/alerts/1")
                .htmlUrl("https://github.com/octo-org/octo-repo/security/code-scanning/1")
                .instancesUrl("https://api.github.com/repos/octo-org/octo-repo/code-scanning/alerts/1/instances")
                .state(GitHubCodeScanningAlertState.OPEN)
                .fixedAt(CREATED_AT)
                .dismissedBy(UsersCatalogFixtures.octocat())
                .dismissedAt(CREATED_AT)
                .dismissedReason(GitHubCodeScanningAlertDismissedReason.FALSE_POSITIVE)
                .dismissedComment("Dismissed as test code.")
                .rule(codeScanningAlertRule())
                .tool(codeScanningAnalysisTool())
                .mostRecentInstance(codeScanningAlertInstance())
                .dismissalApprovedBy(UsersCatalogFixtures.octocat())
                .assignees(List.of(UsersCatalogFixtures.octocat()))
                .linkedPullRequests(List.of(RepositoriesCatalogFixtures.pullRequestSimple()))
                .build();
    }

    public static GitHubCodeScanningAlertItems codeScanningAlertItems() {
        return GitHubCodeScanningAlertItemsBuilder.builder()
                .number(1)
                .createdAt(CREATED_AT)
                .updatedAt(UPDATED_AT)
                .url("https://api.github.com/repos/octo-org/octo-repo/code-scanning/alerts/1")
                .htmlUrl("https://github.com/octo-org/octo-repo/security/code-scanning/1")
                .instancesUrl("https://api.github.com/repos/octo-org/octo-repo/code-scanning/alerts/1/instances")
                .state(GitHubCodeScanningAlertState.OPEN)
                .fixedAt(CREATED_AT)
                .dismissedBy(UsersCatalogFixtures.octocat())
                .dismissedAt(CREATED_AT)
                .dismissedReason(GitHubCodeScanningAlertDismissedReason.FALSE_POSITIVE)
                .dismissedComment("Dismissed as test code.")
                .rule(codeScanningAlertRuleSummary())
                .tool(codeScanningAnalysisTool())
                .mostRecentInstance(codeScanningAlertInstance())
                .dismissalApprovedBy(UsersCatalogFixtures.octocat())
                .assignees(List.of(UsersCatalogFixtures.octocat()))
                .build();
    }

    public static GitHubCodeScanningOrganizationAlertItems codeScanningOrganizationAlertItems() {
        return GitHubCodeScanningOrganizationAlertItemsBuilder.builder()
                .number(1)
                .createdAt(CREATED_AT)
                .updatedAt(UPDATED_AT)
                .url("https://api.github.com/repos/octo-org/octo-repo/code-scanning/alerts/1")
                .htmlUrl("https://github.com/octo-org/octo-repo/security/code-scanning/1")
                .instancesUrl("https://api.github.com/repos/octo-org/octo-repo/code-scanning/alerts/1/instances")
                .state(GitHubCodeScanningAlertState.OPEN)
                .fixedAt(CREATED_AT)
                .dismissedBy(UsersCatalogFixtures.octocat())
                .dismissedAt(CREATED_AT)
                .dismissedReason(GitHubCodeScanningAlertDismissedReason.FALSE_POSITIVE)
                .dismissedComment("Dismissed as test code.")
                .rule(codeScanningAlertRuleSummary())
                .tool(codeScanningAnalysisTool())
                .mostRecentInstance(codeScanningAlertInstance())
                .repository(RepositoriesCatalogFixtures.helloWorldSimpleRepository())
                .dismissalApprovedBy(UsersCatalogFixtures.octocat())
                .assignees(List.of(UsersCatalogFixtures.octocat()))
                .build();
    }

    public static GitHubCodeScanningAlertInstance codeScanningAlertInstance() {
        return GitHubCodeScanningAlertInstanceBuilder.builder()
                .ref("refs/heads/main")
                .analysisKey(".github/workflows/codeql-analysis.yml:analyze")
                .environment("{\"language\":\"javascript\"}")
                .category("/language:javascript")
                .state(GitHubCodeScanningAlertState.OPEN)
                .commitSha("c2e6d3d6c7b7a3f1e0d5b4a3928170f6e5d4c3b2")
                .message(codeScanningAlertInstanceMessage())
                .location(codeScanningAlertLocation())
                .htmlUrl("https://github.com/octo-org/octo-repo/security/code-scanning/1")
                .classifications(List.of(GitHubCodeScanningAlertClassification.SOURCE))
                .build();
    }

    public static GitHubCodeScanningAlertInstanceList codeScanningAlertInstanceList() {
        return GitHubCodeScanningAlertInstanceListBuilder.builder()
                .ref("refs/heads/main")
                .analysisKey(".github/workflows/codeql-analysis.yml:analyze")
                .environment("{\"language\":\"javascript\"}")
                .category("/language:javascript")
                .state(GitHubCodeScanningAlertInstanceState.OPEN)
                .commitSha("c2e6d3d6c7b7a3f1e0d5b4a3928170f6e5d4c3b2")
                .message(codeScanningAlertInstanceListMessage())
                .location(codeScanningAlertLocation())
                .htmlUrl("https://github.com/octo-org/octo-repo/security/code-scanning/1")
                .classifications(List.of(GitHubCodeScanningAlertClassification.SOURCE))
                .build();
    }

    public static GitHubCodeScanningAlertLocation codeScanningAlertLocation() {
        return GitHubCodeScanningAlertLocationBuilder.builder()
                .path("src/main/java/Example.java")
                .startLine(1)
                .endLine(10)
                .startColumn(5)
                .endColumn(20)
                .build();
    }

    public static GitHubCodeScanningAlertRule codeScanningAlertRule() {
        return GitHubCodeScanningAlertRuleBuilder.builder()
                .id("8f9d1c2b-3e4a-5b6c-7d8e-9f0a1b2c3d4e")
                .name("Hard-coded credentials")
                .severity(GitHubCodeScanningAlertRuleSeverity.NONE)
                .securitySeverityLevel(GitHubCodeScanningAlertRuleSecuritySeverityLevel.LOW)
                .description("Hard-coded credentials are a security risk.")
                .fullDescription("Hard-coded credentials are a security risk and should be avoided.")
                .tags(List.of("security", "external/cwe/cwe-798"))
                .help("Remove hard-coded credentials.")
                .helpUri("https://codeql.github.com/codeql-query-help/javascript/js-hardcoded-credentials/")
                .build();
    }

    public static GitHubCodeScanningAlertRuleSummary codeScanningAlertRuleSummary() {
        return GitHubCodeScanningAlertRuleSummaryBuilder.builder()
                .id("8f9d1c2b-3e4a-5b6c-7d8e-9f0a1b2c3d4e")
                .name("Hard-coded credentials")
                .severity(GitHubCodeScanningAlertRuleSummarySeverity.NONE)
                .securitySeverityLevel(GitHubCodeScanningAlertRuleSummarySecuritySeverityLevel.LOW)
                .description("Hard-coded credentials are a security risk.")
                .fullDescription("Hard-coded credentials are a security risk and should be avoided.")
                .tags(List.of("security", "external/cwe/cwe-798"))
                .help("Remove hard-coded credentials.")
                .helpUri("https://codeql.github.com/codeql-query-help/javascript/js-hardcoded-credentials/")
                .build();
    }

    public static GitHubCodeScanningAnalysis codeScanningAnalysis() {
        return GitHubCodeScanningAnalysisBuilder.builder()
                .ref("refs/heads/main")
                .commitSha("c2e6d3d6c7b7a3f1e0d5b4a3928170f6e5d4c3b2")
                .analysisKey(".github/workflows/codeql-analysis.yml:analyze")
                .environment("{\"language\":\"javascript\"}")
                .category("/language:javascript")
                .error("")
                .createdAt(CREATED_AT)
                .resultsCount(3)
                .rulesCount(10)
                .id(1)
                .url("https://api.github.com/repos/octo-org/octo-repo/code-scanning/alerts/1")
                .sarifId("47177e22-5642-11ec-8d3d-0242ac130003")
                .tool(codeScanningAnalysisTool())
                .deletable(true)
                .warning("")
                .build();
    }

    public static GitHubCodeScanningAnalysisDeletion codeScanningAnalysisDeletion() {
        return GitHubCodeScanningAnalysisDeletionBuilder.builder()
                .nextAnalysisUrl("https://api.github.com/repos/octo-org/octo-repo/code-scanning/analyses/2")
                .confirmDeleteUrl(
                        "https://api.github.com/repos/octo-org/octo-repo/code-scanning/analyses/1?confirm_delete")
                .build();
    }

    public static GitHubCodeScanningAnalysisTool codeScanningAnalysisTool() {
        return GitHubCodeScanningAnalysisToolBuilder.builder()
                .name("CodeQL")
                .version("2.14.0")
                .guid("0a9b3c7d-e8f1-4b2a-9c3d-5e6f7a8b9c0d")
                .build();
    }

    public static GitHubCodeScanningAutofix codeScanningAutofix() {
        return GitHubCodeScanningAutofixBuilder.builder()
                .status(GitHubCodeScanningAutofixStatus.PENDING)
                .description("Hard-coded credentials are a security risk.")
                .startedAt(CREATED_AT)
                .build();
    }

    public static GitHubCodeScanningAutofixCommits codeScanningAutofixCommits() {
        return GitHubCodeScanningAutofixCommitsBuilder.builder()
                .targetRef("refs/heads/main")
                .message("Fix code scanning alert")
                .build();
    }

    public static GitHubCodeScanningAutofixCommitsResponse codeScanningAutofixCommitsResponse() {
        return GitHubCodeScanningAutofixCommitsResponseBuilder.builder()
                .targetRef("refs/heads/main")
                .sha("c2e6d3d6c7b7a3f1e0d5b4a3928170f6e5d4c3b2")
                .build();
    }

    public static GitHubCodeScanningCodeqlDatabase codeScanningCodeqlDatabase() {
        return GitHubCodeScanningCodeqlDatabaseBuilder.builder()
                .id(1)
                .name("javascript-database")
                .language("javascript")
                .uploader(UsersCatalogFixtures.octocat())
                .contentType("application/zip")
                .size(2048)
                .createdAt(CREATED_AT)
                .updatedAt(UPDATED_AT)
                .url("https://api.github.com/repos/octo-org/octo-repo/code-scanning/alerts/1")
                .commitOid("c2e6d3d6c7b7a3f1e0d5b4a3928170f6e5d4c3b2")
                .build();
    }

    public static GitHubCodeScanningDefaultSetup codeScanningDefaultSetup() {
        return GitHubCodeScanningDefaultSetupBuilder.builder()
                .state(GitHubCodeScanningDefaultSetupState.CONFIGURED)
                .languages(List.of(GitHubCodeScanningDefaultSetupLanguage.ACTIONS))
                .runnerType(GitHubCodeScanningDefaultSetupRunnerType.STANDARD)
                .runnerLabel("ubuntu-latest")
                .querySuite(GitHubCodeScanningDefaultSetupQuerySuite.DEFAULT)
                .threatModel(GitHubCodeScanningDefaultSetupThreatModel.REMOTE)
                .updatedAt(UPDATED_AT)
                .schedule(GitHubCodeScanningDefaultSetupSchedule.WEEKLY)
                .build();
    }

    public static GitHubCodeScanningDefaultSetupUpdate codeScanningDefaultSetupUpdate() {
        return GitHubCodeScanningDefaultSetupUpdateBuilder.builder()
                .state(GitHubCodeScanningDefaultSetupUpdateState.CONFIGURED)
                .runnerType(GitHubCodeScanningDefaultSetupUpdateRunnerType.STANDARD)
                .runnerLabel("ubuntu-latest")
                .querySuite(GitHubCodeScanningDefaultSetupUpdateQuerySuite.DEFAULT)
                .threatModel(GitHubCodeScanningDefaultSetupUpdateThreatModel.REMOTE)
                .languages(List.of(GitHubCodeScanningDefaultSetupUpdateLanguage.ACTIONS))
                .build();
    }

    public static GitHubCodeScanningDefaultSetupUpdateResponse codeScanningDefaultSetupUpdateResponse() {
        return GitHubCodeScanningDefaultSetupUpdateResponseBuilder.builder()
                .runId(1L)
                .runUrl("https://api.github.com/repos/octo-org/octo-repo/actions/runs/1")
                .build();
    }

    public static GitHubCodeScanningAiScanEnablement codeScanningAiScanEnablement() {
        return GitHubCodeScanningAiScanEnablementBuilder.builder()
                .prScan(GitHubCodeScanningAiScanPrScan.ENABLED)
                .build();
    }

    public static GitHubCodeScanningAiScanEnablementUpdate codeScanningAiScanEnablementUpdate() {
        return GitHubCodeScanningAiScanEnablementUpdateBuilder.builder()
                .prScan(GitHubCodeScanningAiScanPrScan.ENABLED)
                .build();
    }

    public static GitHubCodeScanningAiScanOrgEnablementUpdate codeScanningAiScanOrgEnablementUpdate() {
        return GitHubCodeScanningAiScanOrgEnablementUpdateBuilder.builder()
                .prScan(GitHubCodeScanningAiScanPrScan.ENABLED)
                .build();
    }

    public static GitHubCodeScanningAiScanOrgSettings codeScanningAiScanOrgSettings() {
        return GitHubCodeScanningAiScanOrgSettingsBuilder.builder()
                .prScan(GitHubCodeScanningAiScanPrScan.ENABLED)
                .build();
    }

    public static GitHubCodeScanningSarifsReceipt codeScanningSarifsReceipt() {
        return GitHubCodeScanningSarifsReceiptBuilder.builder()
                .id("8f9d1c2b-3e4a-5b6c-7d8e-9f0a1b2c3d4e")
                .url("https://api.github.com/repos/octo-org/octo-repo/code-scanning/alerts/1")
                .build();
    }

    public static GitHubCodeScanningSarifsStatus codeScanningSarifsStatus() {
        return GitHubCodeScanningSarifsStatusBuilder.builder()
                .processingStatus(GitHubCodeScanningSarifsStatusProcessingStatus.PENDING)
                .analysesUrl("https://api.github.com/repos/octo-org/octo-repo/code-scanning/analyses")
                .errors(List.of("Upload failed"))
                .build();
    }

    public static GitHubCodeScanningVariantAnalysisRepoTask codeScanningVariantAnalysisRepoTask() {
        return GitHubCodeScanningVariantAnalysisRepoTaskBuilder.builder()
                .repository(RepositoriesCatalogFixtures.helloWorldSimpleRepository())
                .analysisStatus(GitHubCodeScanningVariantAnalysisStatus.PENDING)
                .artifactSizeInBytes(1024)
                .resultCount(1)
                .failureMessage("Repository not found")
                .databaseCommitSha("c2e6d3d6c7b7a3f1e0d5b4a3928170f6e5d4c3b2")
                .sourceLocationPrefix("/home/runner/work/octo-repo")
                .artifactUrl(
                        "https://api.github.com/repos/octo-org/octo-repo/code-scanning/codeql/variant-analyses/1/artifacts")
                .build();
    }

    public static GitHubCodeScanningVariantAnalysisRepository codeScanningVariantAnalysisRepository() {
        return GitHubCodeScanningVariantAnalysisRepositoryBuilder.builder()
                .id(1)
                .name("octo-repo")
                .fullName("octo-org/octo-repo")
                .isPrivate(false)
                .stargazersCount(42)
                .updatedAt(UPDATED_AT)
                .build();
    }

    public static GitHubCodeScanningVariantAnalysisSkippedRepoGroup codeScanningVariantAnalysisSkippedRepoGroup() {
        return GitHubCodeScanningVariantAnalysisSkippedRepoGroupBuilder.builder()
                .repositoryCount(1)
                .repositories(List.of(codeScanningVariantAnalysisRepository()))
                .build();
    }

    public static GitHubCodeScanningVariantAnalysis codeScanningVariantAnalysis() {
        return GitHubCodeScanningVariantAnalysisBuilder.builder()
                .id(1)
                .controllerRepo(RepositoriesCatalogFixtures.helloWorldSimpleRepository())
                .actor(UsersCatalogFixtures.octocat())
                .queryLanguage(GitHubCodeScanningVariantAnalysisLanguage.ACTIONS)
                .queryPackUrl("https://api.github.com/repos/octo-org/octo-repo/code-scanning/codeql/variant-analyses/1")
                .createdAt(CREATED_AT)
                .updatedAt(UPDATED_AT)
                .completedAt(CREATED_AT)
                .status(GitHubCodeScanningVariantAnalysisOverallStatus.IN_PROGRESS)
                .actionsWorkflowRunId(1L)
                .failureReason(GitHubCodeScanningVariantAnalysisFailureReason.NO_REPOS_QUERIED)
                .scannedRepositories(List.of(codeScanningVariantAnalysisScannedRepository()))
                .skippedRepositories(codeScanningVariantAnalysisSkippedRepositories())
                .build();
    }

    public static GitHubCodeScanningAlertInstanceMessage codeScanningAlertInstanceMessage() {
        return GitHubCodeScanningAlertInstanceMessageBuilder.builder()
                .text("Use of a hard-coded password.")
                .markdown("Use of a **hard-coded** password.")
                .build();
    }

    public static GitHubCodeScanningAlertInstanceListMessage codeScanningAlertInstanceListMessage() {
        return GitHubCodeScanningAlertInstanceListMessageBuilder.builder()
                .text("Use of a hard-coded password.")
                .build();
    }

    public static GitHubCodeScanningVariantAnalysisScannedRepository codeScanningVariantAnalysisScannedRepository() {
        return GitHubCodeScanningVariantAnalysisScannedRepositoryBuilder.builder()
                .repository(codeScanningVariantAnalysisRepository())
                .analysisStatus(GitHubCodeScanningVariantAnalysisStatus.PENDING)
                .resultCount(1)
                .artifactSizeInBytes(1024)
                .failureMessage("Repository not found")
                .build();
    }

    public static GitHubCodeScanningVariantAnalysisSkippedRepositories
            codeScanningVariantAnalysisSkippedRepositories() {
        return GitHubCodeScanningVariantAnalysisSkippedRepositoriesBuilder.builder()
                .accessMismatchRepos(codeScanningVariantAnalysisSkippedRepoGroup())
                .notFoundRepos(codeScanningVariantAnalysisSkippedRepositoriesNotFoundRepos())
                .noCodeqlDbRepos(codeScanningVariantAnalysisSkippedRepoGroup())
                .overLimitRepos(codeScanningVariantAnalysisSkippedRepoGroup())
                .build();
    }

    public static GitHubCodeScanningVariantAnalysisSkippedRepositoriesNotFoundRepos
            codeScanningVariantAnalysisSkippedRepositoriesNotFoundRepos() {
        return GitHubCodeScanningVariantAnalysisSkippedRepositoriesNotFoundReposBuilder.builder()
                .repositoryCount(1)
                .repositoryFullNames(List.of("octo-org/octo-repo"))
                .build();
    }

    public static GitHubUpdateAlertPayload updateAlertPayload() {
        return GitHubUpdateAlertPayloadBuilder.builder()
                .state(GitHubCodeScanningAlertSetState.OPEN)
                .dismissedReason(GitHubCodeScanningAlertDismissedReason.FALSE_POSITIVE)
                .dismissedComment("Dismissed as test code.")
                .createRequest(true)
                .assignees(List.of("octocat"))
                .build();
    }

    public static GitHubCreateVariantAnalysisPayload createVariantAnalysisPayload() {
        return GitHubCreateVariantAnalysisPayloadBuilder.builder()
                .language(GitHubCodeScanningVariantAnalysisLanguage.ACTIONS)
                .queryPack("H4sIAAAAAAAAA")
                .repositories(List.of("octo-org/octo-repo"))
                .build();
    }

    public static GitHubCreateVariantAnalysisPayload createVariantAnalysisPayloadByRepositoryLists() {
        return GitHubCreateVariantAnalysisPayloadBuilder.builder()
                .language(GitHubCodeScanningVariantAnalysisLanguage.ACTIONS)
                .queryPack("H4sIAAAAAAAAA")
                .repositoryLists(List.of("octo-org/list"))
                .build();
    }

    public static GitHubCreateVariantAnalysisPayload createVariantAnalysisPayloadByRepositoryOwners() {
        return GitHubCreateVariantAnalysisPayloadBuilder.builder()
                .language(GitHubCodeScanningVariantAnalysisLanguage.ACTIONS)
                .queryPack("H4sIAAAAAAAAA")
                .repositoryOwners(List.of("octo-org"))
                .build();
    }

    public static GitHubUploadSarifPayload uploadSarifPayload() {
        return GitHubUploadSarifPayloadBuilder.builder()
                .commitSha("c2e6d3d6c7b7a3f1e0d5b4a3928170f6e5d4c3b2")
                .ref("refs/heads/main")
                .sarif("H4sIAAAAAAAAA")
                .checkoutUri(URI.create("file:///github/workspace/"))
                .startedAt(CREATED_AT)
                .toolName("CodeQL")
                .validate(true)
                .build();
    }

    public static GitHubListAlertsForOrgFilter listAlertsForOrgFilter() {
        return GitHubListAlertsForOrgFilterBuilder.builder()
                .toolName("CodeQL")
                .toolGuid("0a9b3c7d-e8f1-4b2a-9c3d-5e6f7a8b9c0d")
                .direction(GitHubDirection.DESC)
                .state(GitHubCodeScanningAlertStateQuery.OPEN)
                .sort(GitHubListAlertsForOrgSort.CREATED)
                .severity(GitHubCodeScanningAlertSeverity.CRITICAL)
                .assignees("assignees")
                .build();
    }

    public static GitHubListAlertsForRepoFilter listAlertsForRepoFilter() {
        return GitHubListAlertsForRepoFilterBuilder.builder()
                .toolName("CodeQL")
                .toolGuid("0a9b3c7d-e8f1-4b2a-9c3d-5e6f7a8b9c0d")
                .ref("refs/heads/main")
                .pr(123)
                .direction(GitHubDirection.DESC)
                .sort(GitHubListAlertsForRepoSort.CREATED)
                .state(GitHubCodeScanningAlertStateQuery.OPEN)
                .severity(GitHubCodeScanningAlertSeverity.CRITICAL)
                .assignees("assignees")
                .build();
    }

    public static GitHubListAlertInstancesFilter listAlertInstancesFilter() {
        return GitHubListAlertInstancesFilterBuilder.builder()
                .ref("refs/heads/main")
                .pr(123)
                .build();
    }

    public static GitHubListRecentAnalysesFilter listRecentAnalysesFilter() {
        return GitHubListRecentAnalysesFilterBuilder.builder()
                .toolName("CodeQL")
                .toolGuid("0a9b3c7d-e8f1-4b2a-9c3d-5e6f7a8b9c0d")
                .pr(123)
                .ref("refs/heads/main")
                .sarifId("47177e22-5642-11ec-8d3d-0242ac130003")
                .direction(GitHubDirection.DESC)
                .sort(GitHubListRecentAnalysesSort.CREATED)
                .build();
    }

    public static GitHubDeleteAnalysisFilter deleteAnalysisFilter() {
        return GitHubDeleteAnalysisFilterBuilder.builder().confirmDelete("true").build();
    }

    public static GitHubCodeScanningAlertClassification codeScanningAlertClassification() {
        return GitHubCodeScanningAlertClassification.SOURCE;
    }

    public static GitHubCodeScanningAlertDismissedReason codeScanningAlertDismissedReason() {
        return GitHubCodeScanningAlertDismissedReason.FALSE_POSITIVE;
    }

    public static GitHubCodeScanningAlertInstanceState codeScanningAlertInstanceState() {
        return GitHubCodeScanningAlertInstanceState.OPEN;
    }

    public static GitHubCodeScanningAlertSetState codeScanningAlertSetState() {
        return GitHubCodeScanningAlertSetState.OPEN;
    }

    public static GitHubCodeScanningAlertSeverity codeScanningAlertSeverity() {
        return GitHubCodeScanningAlertSeverity.CRITICAL;
    }

    public static GitHubCodeScanningAlertStateQuery codeScanningAlertStateQuery() {
        return GitHubCodeScanningAlertStateQuery.OPEN;
    }

    public static GitHubCodeScanningAlertState codeScanningAlertState() {
        return GitHubCodeScanningAlertState.OPEN;
    }

    public static GitHubCodeScanningAutofixStatus codeScanningAutofixStatus() {
        return GitHubCodeScanningAutofixStatus.PENDING;
    }

    public static GitHubCodeScanningVariantAnalysisLanguage codeScanningVariantAnalysisLanguage() {
        return GitHubCodeScanningVariantAnalysisLanguage.ACTIONS;
    }

    public static GitHubCodeScanningVariantAnalysisStatus codeScanningVariantAnalysisStatus() {
        return GitHubCodeScanningVariantAnalysisStatus.PENDING;
    }

    public static GitHubCodeScanningVariantAnalysisOverallStatus codeScanningVariantAnalysisOverallStatus() {
        return GitHubCodeScanningVariantAnalysisOverallStatus.IN_PROGRESS;
    }

    public static GitHubCodeScanningAlertRuleSeverity codeScanningAlertRuleSeverity() {
        return GitHubCodeScanningAlertRuleSeverity.NONE;
    }

    public static GitHubCodeScanningAlertRuleSecuritySeverityLevel codeScanningAlertRuleSecuritySeverityLevel() {
        return GitHubCodeScanningAlertRuleSecuritySeverityLevel.LOW;
    }

    public static GitHubCodeScanningAlertRuleSummarySeverity codeScanningAlertRuleSummarySeverity() {
        return GitHubCodeScanningAlertRuleSummarySeverity.NONE;
    }

    public static GitHubCodeScanningAlertRuleSummarySecuritySeverityLevel
            codeScanningAlertRuleSummarySecuritySeverityLevel() {
        return GitHubCodeScanningAlertRuleSummarySecuritySeverityLevel.LOW;
    }

    public static GitHubCodeScanningVariantAnalysisFailureReason codeScanningVariantAnalysisFailureReason() {
        return GitHubCodeScanningVariantAnalysisFailureReason.NO_REPOS_QUERIED;
    }

    public static GitHubCodeScanningDefaultSetupState codeScanningDefaultSetupState() {
        return GitHubCodeScanningDefaultSetupState.CONFIGURED;
    }

    public static GitHubCodeScanningDefaultSetupRunnerType codeScanningDefaultSetupRunnerType() {
        return GitHubCodeScanningDefaultSetupRunnerType.STANDARD;
    }

    public static GitHubCodeScanningDefaultSetupQuerySuite codeScanningDefaultSetupQuerySuite() {
        return GitHubCodeScanningDefaultSetupQuerySuite.DEFAULT;
    }

    public static GitHubCodeScanningDefaultSetupThreatModel codeScanningDefaultSetupThreatModel() {
        return GitHubCodeScanningDefaultSetupThreatModel.REMOTE;
    }

    public static GitHubCodeScanningDefaultSetupSchedule codeScanningDefaultSetupSchedule() {
        return GitHubCodeScanningDefaultSetupSchedule.WEEKLY;
    }

    public static GitHubCodeScanningDefaultSetupLanguage codeScanningDefaultSetupLanguage() {
        return GitHubCodeScanningDefaultSetupLanguage.ACTIONS;
    }

    public static GitHubCodeScanningDefaultSetupUpdateState codeScanningDefaultSetupUpdateState() {
        return GitHubCodeScanningDefaultSetupUpdateState.CONFIGURED;
    }

    public static GitHubCodeScanningDefaultSetupUpdateRunnerType codeScanningDefaultSetupUpdateRunnerType() {
        return GitHubCodeScanningDefaultSetupUpdateRunnerType.STANDARD;
    }

    public static GitHubCodeScanningDefaultSetupUpdateQuerySuite codeScanningDefaultSetupUpdateQuerySuite() {
        return GitHubCodeScanningDefaultSetupUpdateQuerySuite.DEFAULT;
    }

    public static GitHubCodeScanningDefaultSetupUpdateThreatModel codeScanningDefaultSetupUpdateThreatModel() {
        return GitHubCodeScanningDefaultSetupUpdateThreatModel.REMOTE;
    }

    public static GitHubCodeScanningDefaultSetupUpdateLanguage codeScanningDefaultSetupUpdateLanguage() {
        return GitHubCodeScanningDefaultSetupUpdateLanguage.ACTIONS;
    }

    public static GitHubCodeScanningSarifsStatusProcessingStatus codeScanningSarifsStatusProcessingStatus() {
        return GitHubCodeScanningSarifsStatusProcessingStatus.PENDING;
    }

    public static GitHubListAlertsForOrgSort listAlertsForOrgSort() {
        return GitHubListAlertsForOrgSort.CREATED;
    }

    public static GitHubListAlertsForRepoSort listAlertsForRepoSort() {
        return GitHubListAlertsForRepoSort.CREATED;
    }

    public static GitHubListRecentAnalysesSort listRecentAnalysesSort() {
        return GitHubListRecentAnalysesSort.CREATED;
    }

    public static GitHubCodeScanningAlertDismissalRequestRepository codeScanningAlertDismissalRequestRepository() {
        return new GitHubCodeScanningAlertDismissalRequestRepository(1L, "octo-repo", "octo-org/octo-repo");
    }

    public static GitHubCodeScanningAlertDismissalRequestOrganization codeScanningAlertDismissalRequestOrganization() {
        return new GitHubCodeScanningAlertDismissalRequestOrganization(1L, "octo-org");
    }

    public static GitHubCodeScanningAlertDismissalRequestRequester codeScanningAlertDismissalRequestRequester() {
        return new GitHubCodeScanningAlertDismissalRequestRequester(583_231L, "octocat");
    }

    public static GitHubCodeScanningAlertDismissalRequestData codeScanningAlertDismissalRequestData() {
        return new GitHubCodeScanningAlertDismissalRequestData("false_positive", "1", "PRRT_1");
    }

    public static GitHubCodeScanningAlertDismissalRequest codeScanningAlertDismissalRequest() {
        return new GitHubCodeScanningAlertDismissalRequest(
                1L,
                1L,
                codeScanningAlertDismissalRequestRepository(),
                codeScanningAlertDismissalRequestOrganization(),
                codeScanningAlertDismissalRequestRequester(),
                "code_scanning_alert",
                List.of(codeScanningAlertDismissalRequestData()),
                "1",
                GitHubCodeScanningAlertDismissalRequestStatus.APPROVED,
                "This is a false positive.",
                Instant.parse("2026-09-01T00:00:00Z"),
                Instant.parse("2026-08-01T10:15:30Z"),
                List.of(dismissalRequestResponse()),
                "https://api.github.com/repos/octo-org/octo-repo/dismissal-requests/code-scanning/1",
                "https://github.com/octo-org/octo-repo/security/dismissal-requests/code-scanning/1");
    }

    public static GitHubListOrgDismissalRequestsFilter listOrgDismissalRequestsFilter() {
        return GitHubListOrgDismissalRequestsFilterBuilder.builder()
                .reviewer("octocat")
                .requester("hubot")
                .timePeriod(GitHubAlertDismissalTimePeriod.WEEK)
                .requestStatus(GitHubAlertDismissalRequestStatus.OPEN)
                .repositoryName("octo-repo")
                .build();
    }

    public static GitHubListDismissalRequestsForRepoFilter listDismissalRequestsForRepoFilter() {
        return GitHubListDismissalRequestsForRepoFilterBuilder.builder()
                .reviewer("octocat")
                .requester("hubot")
                .timePeriod(GitHubAlertDismissalTimePeriod.DAY)
                .requestStatus(GitHubAlertDismissalRequestStatus.APPROVED)
                .build();
    }

    public static GitHubReviewDismissalRequestPayload reviewDismissalRequestPayload() {
        return new GitHubReviewDismissalRequestPayload(GitHubReviewDismissalRequestStatus.APPROVE, "Looks good.");
    }

    public static GitHubListAlertsForEnterpriseFilter listAlertsForEnterpriseFilter() {
        return GitHubListAlertsForEnterpriseFilterBuilder.builder()
                .toolName("CodeQL")
                .toolGuid("2d1ec4ac-8e38-11ec-8d3d-0242ac130003")
                .direction(GitHubDirection.DESC)
                .state(GitHubCodeScanningAlertStateQuery.OPEN)
                .sort(GitHubListAlertsForEnterpriseSort.CREATED)
                .assignees("octocat")
                .build();
    }
}
