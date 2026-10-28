package io.github.piotr_janczukowicz.github.api.checks;

import io.github.piotr_janczukowicz.github.api.branches.BranchesCatalogFixtures;
import io.github.piotr_janczukowicz.github.api.pullrequests.PullrequestsCatalogFixtures;
import io.github.piotr_janczukowicz.github.api.repositories.RepositoriesCatalogFixtures;
import java.time.Instant;
import java.util.List;

public final class ChecksCatalogFixtures {

    private static final String HEAD_SHA = "ce587453ced02b1526dfb4cb910479d431683101";
    private static final String CHECK_RUN_URL = "https://api.github.com/repos/github/hello-world/check-runs/4";
    private static final String ANNOTATIONS_URL =
            "https://api.github.com/repos/github/hello-world/check-runs/4/annotations";
    private static final String ANNOTATION_PATH = "README.md";
    private static final String ANNOTATION_MESSAGE = "Check your spelling for 'banaas'.";
    private static final String OUTPUT_TITLE = "Mighty Readme report";
    private static final String OUTPUT_SUMMARY = "There are 0 failures, 2 warnings, and 1 notice.";
    private static final String OUTPUT_TEXT = "You may have some misspelled words on lines 2 and 4.";
    private static final String BLOB_HREF = "https://api.github.com/repos/github/rest-api-description/git/blobs/abc";
    private static final Instant TIMESTAMP = Instant.parse("2018-05-04T01:14:52Z");

    private ChecksCatalogFixtures() {}

    static GitHubCheckAnnotation checkAnnotation() {
        return GitHubCheckAnnotationBuilder.builder()
                .path(ANNOTATION_PATH)
                .startLine(2)
                .endLine(2)
                .startColumn(5)
                .endColumn(10)
                .annotationLevel("warning")
                .title("Spell Checker")
                .message(ANNOTATION_MESSAGE)
                .rawDetails("Do you mean 'bananas' or 'banana'?")
                .blobHref(BLOB_HREF)
                .build();
    }

    static GitHubCheckRun checkRun() {
        return GitHubCheckRunBuilder.builder()
                .id(4L)
                .headSha(HEAD_SHA)
                .nodeId("MDg6Q2hlY2tSdW40")
                .externalId("")
                .url(CHECK_RUN_URL)
                .htmlUrl("https://github.com/github/hello-world/runs/4")
                .detailsUrl("https://example.com")
                .status(checkRunStatus())
                .conclusion(checkRunConclusion())
                .startedAt(TIMESTAMP)
                .completedAt(TIMESTAMP)
                .output(checkRunOutput())
                .name("mighty_readme")
                .checkSuite(checkRunCheckSuite())
                .app(BranchesCatalogFixtures.integration())
                .pullRequests(List.of(PullrequestsCatalogFixtures.pullRequestMinimal()))
                .deployment(deploymentSimple())
                .build();
    }

    static GitHubCheckRunOutput checkRunOutput() {
        return GitHubCheckRunOutputBuilder.builder()
                .title(OUTPUT_TITLE)
                .summary(OUTPUT_SUMMARY)
                .text(OUTPUT_TEXT)
                .annotationsCount(2)
                .annotationsUrl(ANNOTATIONS_URL)
                .build();
    }

    static GitHubCheckRunCheckSuite checkRunCheckSuite() {
        return new GitHubCheckRunCheckSuite(5);
    }

    static GitHubCheckSuite checkSuite() {
        return GitHubCheckSuiteBuilder.builder()
                .id(5L)
                .nodeId("MDEwOkNoZWNrU3VpdGU1")
                .headBranch("master")
                .headSha("d6fde92930d4715a2b49857d24b940956b26d2d3")
                .status(checkSuiteStatus())
                .conclusion(checkSuiteConclusion())
                .url("https://api.github.com/repos/github/hello-world/check-suites/5")
                .before("146e867f55c26428e5f9fade55a9bbf5e95a7912")
                .after("d6fde92930d4715a2b49857d24b940956b26d2d3")
                .pullRequests(List.of(PullrequestsCatalogFixtures.pullRequestMinimal()))
                .app(BranchesCatalogFixtures.integration())
                .repository(RepositoriesCatalogFixtures.helloWorldMinimalRepository())
                .createdAt(TIMESTAMP)
                .updatedAt(TIMESTAMP)
                .headCommit(simpleCommit())
                .latestCheckRunsCount(1)
                .checkRunsUrl("https://api.github.com/repos/github/hello-world/check-suites/5/check-runs")
                .rerequestable(true)
                .runsRerequestable(false)
                .build();
    }

    static GitHubCheckSuitePreference checkSuitePreference() {
        return GitHubCheckSuitePreferenceBuilder.builder()
                .preferences(checkSuitePreferences())
                .repository(RepositoriesCatalogFixtures.helloWorldMinimalRepository())
                .build();
    }

    static GitHubCheckSuitePreferences checkSuitePreferences() {
        return GitHubCheckSuitePreferencesBuilder.builder()
                .autoTriggerChecks(List.of(checkSuiteAutoTriggerCheck(), checkSuiteAutoTriggerCheckDisabled()))
                .build();
    }

    static GitHubCheckRunsPage checkRunsPage() {
        return GitHubCheckRunsPageBuilder.builder()
                .totalCount(1)
                .checkRuns(List.of(checkRun()))
                .build();
    }

    static GitHubCheckSuitesPage checkSuitesPage() {
        return GitHubCheckSuitesPageBuilder.builder()
                .totalCount(1)
                .checkSuites(List.of(checkSuite()))
                .build();
    }

    static GitHubDeploymentSimple deploymentSimple() {
        return GitHubDeploymentSimpleBuilder.builder()
                .url("https://api.github.com/repos/github/hello-world/deployments/1")
                .id(1)
                .nodeId("MDEwOkRlcGxveW1lbnQx")
                .task("deploy")
                .originalEnvironment("production")
                .environment("production")
                .description("Deployment description")
                .createdAt(TIMESTAMP)
                .updatedAt(TIMESTAMP)
                .statusesUrl("https://api.github.com/repos/github/hello-world/deployments/1/statuses")
                .repositoryUrl("https://api.github.com/repos/github/hello-world")
                .transientEnvironment(false)
                .productionEnvironment(true)
                .performedViaGithubApp(BranchesCatalogFixtures.integration())
                .build();
    }

    public static GitHubSimpleCommit simpleCommit() {
        return GitHubSimpleCommitBuilder.builder()
                .id("6dcb09b5b57875f334f61aebed695e2e4193db5e")
                .treeId("2dd5b51ad3f0c49ddb2ed4f0f9b36ef1d12d1a57")
                .message("Fix all the bugs")
                .timestamp(TIMESTAMP)
                .author(simpleCommitPerson())
                .committer(simpleCommitPerson())
                .build();
    }

    static GitHubSimpleCommitPerson simpleCommitPerson() {
        return GitHubSimpleCommitPersonBuilder.builder()
                .name("The Octocat")
                .email("octocat@github.com")
                .build();
    }

    static GitHubCreateCheckRunPayload createCheckRunPayload() {
        return GitHubCreateCheckRunPayloadBuilder.builder()
                .name("mighty_readme")
                .headSha(HEAD_SHA)
                .detailsUrl("https://example.com")
                .externalId("42")
                .status(createCheckRunStatus())
                .startedAt(TIMESTAMP)
                .conclusion(createCheckRunConclusion())
                .completedAt(TIMESTAMP)
                .output(createCheckRunOutput())
                .actions(List.of(createCheckRunAction()))
                .build();
    }

    static GitHubCreateCheckRunOutput createCheckRunOutput() {
        return GitHubCreateCheckRunOutputBuilder.builder()
                .title(OUTPUT_TITLE)
                .summary(OUTPUT_SUMMARY)
                .text(OUTPUT_TEXT)
                .annotations(List.of(createCheckRunAnnotation()))
                .images(List.of(createCheckRunImage()))
                .build();
    }

    static GitHubCreateCheckRunAnnotation createCheckRunAnnotation() {
        return GitHubCreateCheckRunAnnotationBuilder.builder()
                .path(ANNOTATION_PATH)
                .startLine(2)
                .endLine(2)
                .startColumn(5)
                .endColumn(10)
                .annotationLevel(createCheckRunAnnotationLevel())
                .message(ANNOTATION_MESSAGE)
                .title("Spell Checker")
                .rawDetails("Do you mean 'bananas' or 'banana'?")
                .build();
    }

    static GitHubCreateCheckRunImage createCheckRunImage() {
        return GitHubCreateCheckRunImageBuilder.builder()
                .alt("Octocat")
                .imageUrl("https://github.com/images/error/octocat_happy.gif")
                .caption("Octocat happy")
                .build();
    }

    static GitHubCreateCheckRunAction createCheckRunAction() {
        return GitHubCreateCheckRunActionBuilder.builder()
                .label("Fix")
                .description("Fix the issue")
                .identifier("fix_it")
                .build();
    }

    static GitHubUpdateCheckRunPayload updateCheckRunPayload() {
        return GitHubUpdateCheckRunPayloadBuilder.builder()
                .name("mighty_readme")
                .detailsUrl("https://example.com")
                .externalId("42")
                .startedAt(TIMESTAMP)
                .status(updateCheckRunStatus())
                .conclusion(updateCheckRunConclusion())
                .completedAt(TIMESTAMP)
                .output(updateCheckRunOutput())
                .actions(List.of(updateCheckRunAction()))
                .build();
    }

    static GitHubUpdateCheckRunOutput updateCheckRunOutput() {
        return GitHubUpdateCheckRunOutputBuilder.builder()
                .title(OUTPUT_TITLE)
                .summary(OUTPUT_SUMMARY)
                .text(OUTPUT_TEXT)
                .annotations(List.of(updateCheckRunAnnotation()))
                .images(List.of(updateCheckRunImage()))
                .build();
    }

    static GitHubUpdateCheckRunAnnotation updateCheckRunAnnotation() {
        return GitHubUpdateCheckRunAnnotationBuilder.builder()
                .path(ANNOTATION_PATH)
                .startLine(2)
                .endLine(2)
                .startColumn(5)
                .endColumn(10)
                .annotationLevel(updateCheckRunAnnotationLevel())
                .message(ANNOTATION_MESSAGE)
                .title("Spell Checker")
                .rawDetails("Do you mean 'bananas' or 'banana'?")
                .build();
    }

    static GitHubUpdateCheckRunImage updateCheckRunImage() {
        return GitHubUpdateCheckRunImageBuilder.builder()
                .alt("Octocat")
                .imageUrl("https://github.com/images/error/octocat_happy.gif")
                .caption("Octocat happy")
                .build();
    }

    static GitHubUpdateCheckRunAction updateCheckRunAction() {
        return GitHubUpdateCheckRunActionBuilder.builder()
                .label("Fix")
                .description("Fix the issue")
                .identifier("fix_it")
                .build();
    }

    static GitHubCreateSuitePayload createSuitePayload() {
        return new GitHubCreateSuitePayload(HEAD_SHA);
    }

    static GitHubSetSuitesPreferencesPayload setSuitesPreferencesPayload() {
        return GitHubSetSuitesPreferencesPayloadBuilder.builder()
                .autoTriggerChecks(
                        List.of(setSuitesPreferencesAutoTriggerCheck(), setSuitesPreferencesAutoTriggerCheckDisabled()))
                .build();
    }

    static GitHubListSuitesForRefFilter listSuitesForRefFilter() {
        return GitHubListSuitesForRefFilterBuilder.builder()
                .appId(1L)
                .checkName("mighty_readme")
                .build();
    }

    static GitHubCheckRunStatus checkRunStatus() {
        return GitHubCheckRunStatus.COMPLETED;
    }

    static GitHubCheckRunConclusion checkRunConclusion() {
        return GitHubCheckRunConclusion.NEUTRAL;
    }

    static GitHubCheckSuiteStatus checkSuiteStatus() {
        return GitHubCheckSuiteStatus.COMPLETED;
    }

    static GitHubCheckSuiteConclusion checkSuiteConclusion() {
        return GitHubCheckSuiteConclusion.NEUTRAL;
    }

    static GitHubCreateCheckRunStatus createCheckRunStatus() {
        return GitHubCreateCheckRunStatus.COMPLETED;
    }

    static GitHubCreateCheckRunConclusion createCheckRunConclusion() {
        return GitHubCreateCheckRunConclusion.SUCCESS;
    }

    static GitHubCreateCheckRunAnnotationLevel createCheckRunAnnotationLevel() {
        return GitHubCreateCheckRunAnnotationLevel.WARNING;
    }

    static GitHubUpdateCheckRunStatus updateCheckRunStatus() {
        return GitHubUpdateCheckRunStatus.COMPLETED;
    }

    static GitHubUpdateCheckRunConclusion updateCheckRunConclusion() {
        return GitHubUpdateCheckRunConclusion.SUCCESS;
    }

    static GitHubCheckRunFilterMode checkRunFilterMode() {
        return GitHubCheckRunFilterMode.LATEST;
    }

    static GitHubCheckRunQueryStatus checkRunQueryStatus() {
        return GitHubCheckRunQueryStatus.COMPLETED;
    }

    static GitHubListForSuiteFilter listForSuiteFilter() {
        return GitHubListForSuiteFilterBuilder.builder()
                .checkName("mighty_readme")
                .status(checkRunQueryStatus())
                .filter(checkRunFilterMode())
                .build();
    }

    static GitHubListForRefFilter listForRefFilter() {
        return GitHubListForRefFilterBuilder.builder()
                .checkName("mighty_readme")
                .status(checkRunQueryStatus())
                .filter(checkRunFilterMode())
                .appId(1L)
                .build();
    }

    static GitHubUpdateCheckRunAnnotationLevel updateCheckRunAnnotationLevel() {
        return GitHubUpdateCheckRunAnnotationLevel.WARNING;
    }

    static GitHubCheckSuiteAutoTriggerCheck checkSuiteAutoTriggerCheck() {
        return new GitHubCheckSuiteAutoTriggerCheck(2, true);
    }

    static GitHubCheckSuiteAutoTriggerCheck checkSuiteAutoTriggerCheckDisabled() {
        return new GitHubCheckSuiteAutoTriggerCheck(4, false);
    }

    static GitHubSetSuitesPreferencesAutoTriggerCheck setSuitesPreferencesAutoTriggerCheck() {
        return new GitHubSetSuitesPreferencesAutoTriggerCheck(2, true);
    }

    static GitHubSetSuitesPreferencesAutoTriggerCheck setSuitesPreferencesAutoTriggerCheckDisabled() {
        return new GitHubSetSuitesPreferencesAutoTriggerCheck(4, false);
    }

    static GitHubCreateCheckRunPayload createCheckRunPayloadInProgress() {
        return GitHubCreateCheckRunPayloadBuilder.builder()
                .name("mighty_readme")
                .headSha(HEAD_SHA)
                .detailsUrl("https://example.com")
                .externalId("42")
                .status(GitHubCreateCheckRunStatus.IN_PROGRESS)
                .startedAt(TIMESTAMP)
                .output(createCheckRunOutput())
                .actions(List.of(createCheckRunAction()))
                .build();
    }

    static GitHubUpdateCheckRunPayload updateCheckRunPayloadInProgress() {
        return GitHubUpdateCheckRunPayloadBuilder.builder()
                .name("mighty_readme")
                .detailsUrl("https://example.com")
                .externalId("42")
                .startedAt(TIMESTAMP)
                .status(GitHubUpdateCheckRunStatus.IN_PROGRESS)
                .output(updateCheckRunOutput())
                .actions(List.of(updateCheckRunAction()))
                .build();
    }
}
