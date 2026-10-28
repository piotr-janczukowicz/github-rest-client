package io.github.piotr_janczukowicz.github.api.actions;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.github.api.checks.ChecksCatalogFixtures;
import io.github.piotr_janczukowicz.github.api.checks.GitHubSimpleCommit;
import io.github.piotr_janczukowicz.github.api.pullrequests.GitHubPullRequestMinimal;
import io.github.piotr_janczukowicz.github.api.pullrequests.PullrequestsCatalogFixtures;
import io.github.piotr_janczukowicz.github.api.repositories.GitHubMinimalRepository;
import io.github.piotr_janczukowicz.github.api.repositories.RepositoriesCatalogFixtures;
import io.github.piotr_janczukowicz.github.api.users.GitHubSimpleUser;
import io.github.piotr_janczukowicz.github.api.users.UsersCatalogFixtures;
import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.time.Instant;
import java.util.List;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubWorkflowRunTest {

    @Test
    void constructor_shouldThrowNullPointerException_whenNodeIdNull() {
        final long id = 1L;
        final String name = "name-sample";
        final Long checkSuiteId = 1L;
        final String checkSuiteNodeId = "checkSuiteNodeId-sample";
        final String headBranch = "headBranch-sample";
        final String headSha = "headSha-sample";
        final String path = "path-sample";
        final int runNumber = 1;
        final Integer runAttempt = 1;
        final List<GitHubReferencedWorkflow> referencedWorkflows = List.of(ActionsCatalogFixtures.referencedWorkflow());
        final String event = "event-sample";
        final String status = "status-sample";
        final String conclusion = "conclusion-sample";
        final int workflowId = 1;
        final String url = "https://api.github.com/example";
        final String htmlUrl = "https://github.com/example";
        final List<GitHubPullRequestMinimal> pullRequests = List.of(PullrequestsCatalogFixtures.pullRequestMinimal());
        final Instant createdAt = Instant.parse("2024-01-02T03:04:05Z");
        final Instant updatedAt = Instant.parse("2024-01-02T03:04:05Z");
        final GitHubSimpleUser actor = UsersCatalogFixtures.octocat();
        final GitHubSimpleUser triggeringActor = UsersCatalogFixtures.octocat();
        final Instant runStartedAt = Instant.parse("2024-01-02T03:04:05Z");
        final String jobsUrl = "https://api.github.com/example";
        final String logsUrl = "https://api.github.com/example";
        final String checkSuiteUrl = "https://api.github.com/example";
        final String artifactsUrl = "https://api.github.com/example";
        final String cancelUrl = "https://api.github.com/example";
        final String rerunUrl = "https://api.github.com/example";
        final String previousAttemptUrl = "https://api.github.com/example";
        final String workflowUrl = "https://api.github.com/example";
        final GitHubSimpleCommit headCommit = ChecksCatalogFixtures.simpleCommit();
        final GitHubMinimalRepository repository = RepositoriesCatalogFixtures.helloWorldMinimalRepository();
        final GitHubMinimalRepository headRepository = RepositoriesCatalogFixtures.helloWorldMinimalRepository();
        final Long headRepositoryId = 1L;
        final String displayTitle = "displayTitle-sample";

        final Throwable thrown = catchThrowable(() -> new GitHubWorkflowRun(
                id,
                name,
                null,
                checkSuiteId,
                checkSuiteNodeId,
                headBranch,
                headSha,
                path,
                runNumber,
                runAttempt,
                referencedWorkflows,
                event,
                status,
                conclusion,
                workflowId,
                url,
                htmlUrl,
                pullRequests,
                createdAt,
                updatedAt,
                actor,
                triggeringActor,
                runStartedAt,
                jobsUrl,
                logsUrl,
                checkSuiteUrl,
                artifactsUrl,
                cancelUrl,
                rerunUrl,
                previousAttemptUrl,
                workflowUrl,
                headCommit,
                repository,
                headRepository,
                headRepositoryId,
                displayTitle));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("nodeId");
    }

    @Test
    void constructor_shouldThrowNullPointerException_whenHeadShaNull() {
        final long id = 1L;
        final String name = "name-sample";
        final String nodeId = "nodeId-sample";
        final Long checkSuiteId = 1L;
        final String checkSuiteNodeId = "checkSuiteNodeId-sample";
        final String headBranch = "headBranch-sample";
        final String path = "path-sample";
        final int runNumber = 1;
        final Integer runAttempt = 1;
        final List<GitHubReferencedWorkflow> referencedWorkflows = List.of(ActionsCatalogFixtures.referencedWorkflow());
        final String event = "event-sample";
        final String status = "status-sample";
        final String conclusion = "conclusion-sample";
        final int workflowId = 1;
        final String url = "https://api.github.com/example";
        final String htmlUrl = "https://github.com/example";
        final List<GitHubPullRequestMinimal> pullRequests = List.of(PullrequestsCatalogFixtures.pullRequestMinimal());
        final Instant createdAt = Instant.parse("2024-01-02T03:04:05Z");
        final Instant updatedAt = Instant.parse("2024-01-02T03:04:05Z");
        final GitHubSimpleUser actor = UsersCatalogFixtures.octocat();
        final GitHubSimpleUser triggeringActor = UsersCatalogFixtures.octocat();
        final Instant runStartedAt = Instant.parse("2024-01-02T03:04:05Z");
        final String jobsUrl = "https://api.github.com/example";
        final String logsUrl = "https://api.github.com/example";
        final String checkSuiteUrl = "https://api.github.com/example";
        final String artifactsUrl = "https://api.github.com/example";
        final String cancelUrl = "https://api.github.com/example";
        final String rerunUrl = "https://api.github.com/example";
        final String previousAttemptUrl = "https://api.github.com/example";
        final String workflowUrl = "https://api.github.com/example";
        final GitHubSimpleCommit headCommit = ChecksCatalogFixtures.simpleCommit();
        final GitHubMinimalRepository repository = RepositoriesCatalogFixtures.helloWorldMinimalRepository();
        final GitHubMinimalRepository headRepository = RepositoriesCatalogFixtures.helloWorldMinimalRepository();
        final Long headRepositoryId = 1L;
        final String displayTitle = "displayTitle-sample";

        final Throwable thrown = catchThrowable(() -> new GitHubWorkflowRun(
                id,
                name,
                nodeId,
                checkSuiteId,
                checkSuiteNodeId,
                headBranch,
                null,
                path,
                runNumber,
                runAttempt,
                referencedWorkflows,
                event,
                status,
                conclusion,
                workflowId,
                url,
                htmlUrl,
                pullRequests,
                createdAt,
                updatedAt,
                actor,
                triggeringActor,
                runStartedAt,
                jobsUrl,
                logsUrl,
                checkSuiteUrl,
                artifactsUrl,
                cancelUrl,
                rerunUrl,
                previousAttemptUrl,
                workflowUrl,
                headCommit,
                repository,
                headRepository,
                headRepositoryId,
                displayTitle));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("headSha");
    }

    @Test
    void constructor_shouldThrowNullPointerException_whenPathNull() {
        final long id = 1L;
        final String name = "name-sample";
        final String nodeId = "nodeId-sample";
        final Long checkSuiteId = 1L;
        final String checkSuiteNodeId = "checkSuiteNodeId-sample";
        final String headBranch = "headBranch-sample";
        final String headSha = "headSha-sample";
        final int runNumber = 1;
        final Integer runAttempt = 1;
        final List<GitHubReferencedWorkflow> referencedWorkflows = List.of(ActionsCatalogFixtures.referencedWorkflow());
        final String event = "event-sample";
        final String status = "status-sample";
        final String conclusion = "conclusion-sample";
        final int workflowId = 1;
        final String url = "https://api.github.com/example";
        final String htmlUrl = "https://github.com/example";
        final List<GitHubPullRequestMinimal> pullRequests = List.of(PullrequestsCatalogFixtures.pullRequestMinimal());
        final Instant createdAt = Instant.parse("2024-01-02T03:04:05Z");
        final Instant updatedAt = Instant.parse("2024-01-02T03:04:05Z");
        final GitHubSimpleUser actor = UsersCatalogFixtures.octocat();
        final GitHubSimpleUser triggeringActor = UsersCatalogFixtures.octocat();
        final Instant runStartedAt = Instant.parse("2024-01-02T03:04:05Z");
        final String jobsUrl = "https://api.github.com/example";
        final String logsUrl = "https://api.github.com/example";
        final String checkSuiteUrl = "https://api.github.com/example";
        final String artifactsUrl = "https://api.github.com/example";
        final String cancelUrl = "https://api.github.com/example";
        final String rerunUrl = "https://api.github.com/example";
        final String previousAttemptUrl = "https://api.github.com/example";
        final String workflowUrl = "https://api.github.com/example";
        final GitHubSimpleCommit headCommit = ChecksCatalogFixtures.simpleCommit();
        final GitHubMinimalRepository repository = RepositoriesCatalogFixtures.helloWorldMinimalRepository();
        final GitHubMinimalRepository headRepository = RepositoriesCatalogFixtures.helloWorldMinimalRepository();
        final Long headRepositoryId = 1L;
        final String displayTitle = "displayTitle-sample";

        final Throwable thrown = catchThrowable(() -> new GitHubWorkflowRun(
                id,
                name,
                nodeId,
                checkSuiteId,
                checkSuiteNodeId,
                headBranch,
                headSha,
                null,
                runNumber,
                runAttempt,
                referencedWorkflows,
                event,
                status,
                conclusion,
                workflowId,
                url,
                htmlUrl,
                pullRequests,
                createdAt,
                updatedAt,
                actor,
                triggeringActor,
                runStartedAt,
                jobsUrl,
                logsUrl,
                checkSuiteUrl,
                artifactsUrl,
                cancelUrl,
                rerunUrl,
                previousAttemptUrl,
                workflowUrl,
                headCommit,
                repository,
                headRepository,
                headRepositoryId,
                displayTitle));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("path");
    }

    @Test
    void constructor_shouldThrowNullPointerException_whenEventNull() {
        final long id = 1L;
        final String name = "name-sample";
        final String nodeId = "nodeId-sample";
        final Long checkSuiteId = 1L;
        final String checkSuiteNodeId = "checkSuiteNodeId-sample";
        final String headBranch = "headBranch-sample";
        final String headSha = "headSha-sample";
        final String path = "path-sample";
        final int runNumber = 1;
        final Integer runAttempt = 1;
        final List<GitHubReferencedWorkflow> referencedWorkflows = List.of(ActionsCatalogFixtures.referencedWorkflow());
        final String status = "status-sample";
        final String conclusion = "conclusion-sample";
        final int workflowId = 1;
        final String url = "https://api.github.com/example";
        final String htmlUrl = "https://github.com/example";
        final List<GitHubPullRequestMinimal> pullRequests = List.of(PullrequestsCatalogFixtures.pullRequestMinimal());
        final Instant createdAt = Instant.parse("2024-01-02T03:04:05Z");
        final Instant updatedAt = Instant.parse("2024-01-02T03:04:05Z");
        final GitHubSimpleUser actor = UsersCatalogFixtures.octocat();
        final GitHubSimpleUser triggeringActor = UsersCatalogFixtures.octocat();
        final Instant runStartedAt = Instant.parse("2024-01-02T03:04:05Z");
        final String jobsUrl = "https://api.github.com/example";
        final String logsUrl = "https://api.github.com/example";
        final String checkSuiteUrl = "https://api.github.com/example";
        final String artifactsUrl = "https://api.github.com/example";
        final String cancelUrl = "https://api.github.com/example";
        final String rerunUrl = "https://api.github.com/example";
        final String previousAttemptUrl = "https://api.github.com/example";
        final String workflowUrl = "https://api.github.com/example";
        final GitHubSimpleCommit headCommit = ChecksCatalogFixtures.simpleCommit();
        final GitHubMinimalRepository repository = RepositoriesCatalogFixtures.helloWorldMinimalRepository();
        final GitHubMinimalRepository headRepository = RepositoriesCatalogFixtures.helloWorldMinimalRepository();
        final Long headRepositoryId = 1L;
        final String displayTitle = "displayTitle-sample";

        final Throwable thrown = catchThrowable(() -> new GitHubWorkflowRun(
                id,
                name,
                nodeId,
                checkSuiteId,
                checkSuiteNodeId,
                headBranch,
                headSha,
                path,
                runNumber,
                runAttempt,
                referencedWorkflows,
                null,
                status,
                conclusion,
                workflowId,
                url,
                htmlUrl,
                pullRequests,
                createdAt,
                updatedAt,
                actor,
                triggeringActor,
                runStartedAt,
                jobsUrl,
                logsUrl,
                checkSuiteUrl,
                artifactsUrl,
                cancelUrl,
                rerunUrl,
                previousAttemptUrl,
                workflowUrl,
                headCommit,
                repository,
                headRepository,
                headRepositoryId,
                displayTitle));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("event");
    }

    @Test
    void constructor_shouldThrowNullPointerException_whenUrlNull() {
        final long id = 1L;
        final String name = "name-sample";
        final String nodeId = "nodeId-sample";
        final Long checkSuiteId = 1L;
        final String checkSuiteNodeId = "checkSuiteNodeId-sample";
        final String headBranch = "headBranch-sample";
        final String headSha = "headSha-sample";
        final String path = "path-sample";
        final int runNumber = 1;
        final Integer runAttempt = 1;
        final List<GitHubReferencedWorkflow> referencedWorkflows = List.of(ActionsCatalogFixtures.referencedWorkflow());
        final String event = "event-sample";
        final String status = "status-sample";
        final String conclusion = "conclusion-sample";
        final int workflowId = 1;
        final String htmlUrl = "https://github.com/example";
        final List<GitHubPullRequestMinimal> pullRequests = List.of(PullrequestsCatalogFixtures.pullRequestMinimal());
        final Instant createdAt = Instant.parse("2024-01-02T03:04:05Z");
        final Instant updatedAt = Instant.parse("2024-01-02T03:04:05Z");
        final GitHubSimpleUser actor = UsersCatalogFixtures.octocat();
        final GitHubSimpleUser triggeringActor = UsersCatalogFixtures.octocat();
        final Instant runStartedAt = Instant.parse("2024-01-02T03:04:05Z");
        final String jobsUrl = "https://api.github.com/example";
        final String logsUrl = "https://api.github.com/example";
        final String checkSuiteUrl = "https://api.github.com/example";
        final String artifactsUrl = "https://api.github.com/example";
        final String cancelUrl = "https://api.github.com/example";
        final String rerunUrl = "https://api.github.com/example";
        final String previousAttemptUrl = "https://api.github.com/example";
        final String workflowUrl = "https://api.github.com/example";
        final GitHubSimpleCommit headCommit = ChecksCatalogFixtures.simpleCommit();
        final GitHubMinimalRepository repository = RepositoriesCatalogFixtures.helloWorldMinimalRepository();
        final GitHubMinimalRepository headRepository = RepositoriesCatalogFixtures.helloWorldMinimalRepository();
        final Long headRepositoryId = 1L;
        final String displayTitle = "displayTitle-sample";

        final Throwable thrown = catchThrowable(() -> new GitHubWorkflowRun(
                id,
                name,
                nodeId,
                checkSuiteId,
                checkSuiteNodeId,
                headBranch,
                headSha,
                path,
                runNumber,
                runAttempt,
                referencedWorkflows,
                event,
                status,
                conclusion,
                workflowId,
                null,
                htmlUrl,
                pullRequests,
                createdAt,
                updatedAt,
                actor,
                triggeringActor,
                runStartedAt,
                jobsUrl,
                logsUrl,
                checkSuiteUrl,
                artifactsUrl,
                cancelUrl,
                rerunUrl,
                previousAttemptUrl,
                workflowUrl,
                headCommit,
                repository,
                headRepository,
                headRepositoryId,
                displayTitle));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("url");
    }

    @Test
    void constructor_shouldThrowNullPointerException_whenHtmlUrlNull() {
        final long id = 1L;
        final String name = "name-sample";
        final String nodeId = "nodeId-sample";
        final Long checkSuiteId = 1L;
        final String checkSuiteNodeId = "checkSuiteNodeId-sample";
        final String headBranch = "headBranch-sample";
        final String headSha = "headSha-sample";
        final String path = "path-sample";
        final int runNumber = 1;
        final Integer runAttempt = 1;
        final List<GitHubReferencedWorkflow> referencedWorkflows = List.of(ActionsCatalogFixtures.referencedWorkflow());
        final String event = "event-sample";
        final String status = "status-sample";
        final String conclusion = "conclusion-sample";
        final int workflowId = 1;
        final String url = "https://api.github.com/example";
        final List<GitHubPullRequestMinimal> pullRequests = List.of(PullrequestsCatalogFixtures.pullRequestMinimal());
        final Instant createdAt = Instant.parse("2024-01-02T03:04:05Z");
        final Instant updatedAt = Instant.parse("2024-01-02T03:04:05Z");
        final GitHubSimpleUser actor = UsersCatalogFixtures.octocat();
        final GitHubSimpleUser triggeringActor = UsersCatalogFixtures.octocat();
        final Instant runStartedAt = Instant.parse("2024-01-02T03:04:05Z");
        final String jobsUrl = "https://api.github.com/example";
        final String logsUrl = "https://api.github.com/example";
        final String checkSuiteUrl = "https://api.github.com/example";
        final String artifactsUrl = "https://api.github.com/example";
        final String cancelUrl = "https://api.github.com/example";
        final String rerunUrl = "https://api.github.com/example";
        final String previousAttemptUrl = "https://api.github.com/example";
        final String workflowUrl = "https://api.github.com/example";
        final GitHubSimpleCommit headCommit = ChecksCatalogFixtures.simpleCommit();
        final GitHubMinimalRepository repository = RepositoriesCatalogFixtures.helloWorldMinimalRepository();
        final GitHubMinimalRepository headRepository = RepositoriesCatalogFixtures.helloWorldMinimalRepository();
        final Long headRepositoryId = 1L;
        final String displayTitle = "displayTitle-sample";

        final Throwable thrown = catchThrowable(() -> new GitHubWorkflowRun(
                id,
                name,
                nodeId,
                checkSuiteId,
                checkSuiteNodeId,
                headBranch,
                headSha,
                path,
                runNumber,
                runAttempt,
                referencedWorkflows,
                event,
                status,
                conclusion,
                workflowId,
                url,
                null,
                pullRequests,
                createdAt,
                updatedAt,
                actor,
                triggeringActor,
                runStartedAt,
                jobsUrl,
                logsUrl,
                checkSuiteUrl,
                artifactsUrl,
                cancelUrl,
                rerunUrl,
                previousAttemptUrl,
                workflowUrl,
                headCommit,
                repository,
                headRepository,
                headRepositoryId,
                displayTitle));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("htmlUrl");
    }

    @Test
    void constructor_shouldThrowNullPointerException_whenCreatedAtNull() {
        final long id = 1L;
        final String name = "name-sample";
        final String nodeId = "nodeId-sample";
        final Long checkSuiteId = 1L;
        final String checkSuiteNodeId = "checkSuiteNodeId-sample";
        final String headBranch = "headBranch-sample";
        final String headSha = "headSha-sample";
        final String path = "path-sample";
        final int runNumber = 1;
        final Integer runAttempt = 1;
        final List<GitHubReferencedWorkflow> referencedWorkflows = List.of(ActionsCatalogFixtures.referencedWorkflow());
        final String event = "event-sample";
        final String status = "status-sample";
        final String conclusion = "conclusion-sample";
        final int workflowId = 1;
        final String url = "https://api.github.com/example";
        final String htmlUrl = "https://github.com/example";
        final List<GitHubPullRequestMinimal> pullRequests = List.of(PullrequestsCatalogFixtures.pullRequestMinimal());
        final Instant updatedAt = Instant.parse("2024-01-02T03:04:05Z");
        final GitHubSimpleUser actor = UsersCatalogFixtures.octocat();
        final GitHubSimpleUser triggeringActor = UsersCatalogFixtures.octocat();
        final Instant runStartedAt = Instant.parse("2024-01-02T03:04:05Z");
        final String jobsUrl = "https://api.github.com/example";
        final String logsUrl = "https://api.github.com/example";
        final String checkSuiteUrl = "https://api.github.com/example";
        final String artifactsUrl = "https://api.github.com/example";
        final String cancelUrl = "https://api.github.com/example";
        final String rerunUrl = "https://api.github.com/example";
        final String previousAttemptUrl = "https://api.github.com/example";
        final String workflowUrl = "https://api.github.com/example";
        final GitHubSimpleCommit headCommit = ChecksCatalogFixtures.simpleCommit();
        final GitHubMinimalRepository repository = RepositoriesCatalogFixtures.helloWorldMinimalRepository();
        final GitHubMinimalRepository headRepository = RepositoriesCatalogFixtures.helloWorldMinimalRepository();
        final Long headRepositoryId = 1L;
        final String displayTitle = "displayTitle-sample";

        final Throwable thrown = catchThrowable(() -> new GitHubWorkflowRun(
                id,
                name,
                nodeId,
                checkSuiteId,
                checkSuiteNodeId,
                headBranch,
                headSha,
                path,
                runNumber,
                runAttempt,
                referencedWorkflows,
                event,
                status,
                conclusion,
                workflowId,
                url,
                htmlUrl,
                pullRequests,
                null,
                updatedAt,
                actor,
                triggeringActor,
                runStartedAt,
                jobsUrl,
                logsUrl,
                checkSuiteUrl,
                artifactsUrl,
                cancelUrl,
                rerunUrl,
                previousAttemptUrl,
                workflowUrl,
                headCommit,
                repository,
                headRepository,
                headRepositoryId,
                displayTitle));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("createdAt");
    }

    @Test
    void constructor_shouldThrowNullPointerException_whenUpdatedAtNull() {
        final long id = 1L;
        final String name = "name-sample";
        final String nodeId = "nodeId-sample";
        final Long checkSuiteId = 1L;
        final String checkSuiteNodeId = "checkSuiteNodeId-sample";
        final String headBranch = "headBranch-sample";
        final String headSha = "headSha-sample";
        final String path = "path-sample";
        final int runNumber = 1;
        final Integer runAttempt = 1;
        final List<GitHubReferencedWorkflow> referencedWorkflows = List.of(ActionsCatalogFixtures.referencedWorkflow());
        final String event = "event-sample";
        final String status = "status-sample";
        final String conclusion = "conclusion-sample";
        final int workflowId = 1;
        final String url = "https://api.github.com/example";
        final String htmlUrl = "https://github.com/example";
        final List<GitHubPullRequestMinimal> pullRequests = List.of(PullrequestsCatalogFixtures.pullRequestMinimal());
        final Instant createdAt = Instant.parse("2024-01-02T03:04:05Z");
        final GitHubSimpleUser actor = UsersCatalogFixtures.octocat();
        final GitHubSimpleUser triggeringActor = UsersCatalogFixtures.octocat();
        final Instant runStartedAt = Instant.parse("2024-01-02T03:04:05Z");
        final String jobsUrl = "https://api.github.com/example";
        final String logsUrl = "https://api.github.com/example";
        final String checkSuiteUrl = "https://api.github.com/example";
        final String artifactsUrl = "https://api.github.com/example";
        final String cancelUrl = "https://api.github.com/example";
        final String rerunUrl = "https://api.github.com/example";
        final String previousAttemptUrl = "https://api.github.com/example";
        final String workflowUrl = "https://api.github.com/example";
        final GitHubSimpleCommit headCommit = ChecksCatalogFixtures.simpleCommit();
        final GitHubMinimalRepository repository = RepositoriesCatalogFixtures.helloWorldMinimalRepository();
        final GitHubMinimalRepository headRepository = RepositoriesCatalogFixtures.helloWorldMinimalRepository();
        final Long headRepositoryId = 1L;
        final String displayTitle = "displayTitle-sample";

        final Throwable thrown = catchThrowable(() -> new GitHubWorkflowRun(
                id,
                name,
                nodeId,
                checkSuiteId,
                checkSuiteNodeId,
                headBranch,
                headSha,
                path,
                runNumber,
                runAttempt,
                referencedWorkflows,
                event,
                status,
                conclusion,
                workflowId,
                url,
                htmlUrl,
                pullRequests,
                createdAt,
                null,
                actor,
                triggeringActor,
                runStartedAt,
                jobsUrl,
                logsUrl,
                checkSuiteUrl,
                artifactsUrl,
                cancelUrl,
                rerunUrl,
                previousAttemptUrl,
                workflowUrl,
                headCommit,
                repository,
                headRepository,
                headRepositoryId,
                displayTitle));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("updatedAt");
    }

    @Test
    void constructor_shouldThrowNullPointerException_whenJobsUrlNull() {
        final long id = 1L;
        final String name = "name-sample";
        final String nodeId = "nodeId-sample";
        final Long checkSuiteId = 1L;
        final String checkSuiteNodeId = "checkSuiteNodeId-sample";
        final String headBranch = "headBranch-sample";
        final String headSha = "headSha-sample";
        final String path = "path-sample";
        final int runNumber = 1;
        final Integer runAttempt = 1;
        final List<GitHubReferencedWorkflow> referencedWorkflows = List.of(ActionsCatalogFixtures.referencedWorkflow());
        final String event = "event-sample";
        final String status = "status-sample";
        final String conclusion = "conclusion-sample";
        final int workflowId = 1;
        final String url = "https://api.github.com/example";
        final String htmlUrl = "https://github.com/example";
        final List<GitHubPullRequestMinimal> pullRequests = List.of(PullrequestsCatalogFixtures.pullRequestMinimal());
        final Instant createdAt = Instant.parse("2024-01-02T03:04:05Z");
        final Instant updatedAt = Instant.parse("2024-01-02T03:04:05Z");
        final GitHubSimpleUser actor = UsersCatalogFixtures.octocat();
        final GitHubSimpleUser triggeringActor = UsersCatalogFixtures.octocat();
        final Instant runStartedAt = Instant.parse("2024-01-02T03:04:05Z");
        final String logsUrl = "https://api.github.com/example";
        final String checkSuiteUrl = "https://api.github.com/example";
        final String artifactsUrl = "https://api.github.com/example";
        final String cancelUrl = "https://api.github.com/example";
        final String rerunUrl = "https://api.github.com/example";
        final String previousAttemptUrl = "https://api.github.com/example";
        final String workflowUrl = "https://api.github.com/example";
        final GitHubSimpleCommit headCommit = ChecksCatalogFixtures.simpleCommit();
        final GitHubMinimalRepository repository = RepositoriesCatalogFixtures.helloWorldMinimalRepository();
        final GitHubMinimalRepository headRepository = RepositoriesCatalogFixtures.helloWorldMinimalRepository();
        final Long headRepositoryId = 1L;
        final String displayTitle = "displayTitle-sample";

        final Throwable thrown = catchThrowable(() -> new GitHubWorkflowRun(
                id,
                name,
                nodeId,
                checkSuiteId,
                checkSuiteNodeId,
                headBranch,
                headSha,
                path,
                runNumber,
                runAttempt,
                referencedWorkflows,
                event,
                status,
                conclusion,
                workflowId,
                url,
                htmlUrl,
                pullRequests,
                createdAt,
                updatedAt,
                actor,
                triggeringActor,
                runStartedAt,
                null,
                logsUrl,
                checkSuiteUrl,
                artifactsUrl,
                cancelUrl,
                rerunUrl,
                previousAttemptUrl,
                workflowUrl,
                headCommit,
                repository,
                headRepository,
                headRepositoryId,
                displayTitle));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("jobsUrl");
    }

    @Test
    void constructor_shouldThrowNullPointerException_whenLogsUrlNull() {
        final long id = 1L;
        final String name = "name-sample";
        final String nodeId = "nodeId-sample";
        final Long checkSuiteId = 1L;
        final String checkSuiteNodeId = "checkSuiteNodeId-sample";
        final String headBranch = "headBranch-sample";
        final String headSha = "headSha-sample";
        final String path = "path-sample";
        final int runNumber = 1;
        final Integer runAttempt = 1;
        final List<GitHubReferencedWorkflow> referencedWorkflows = List.of(ActionsCatalogFixtures.referencedWorkflow());
        final String event = "event-sample";
        final String status = "status-sample";
        final String conclusion = "conclusion-sample";
        final int workflowId = 1;
        final String url = "https://api.github.com/example";
        final String htmlUrl = "https://github.com/example";
        final List<GitHubPullRequestMinimal> pullRequests = List.of(PullrequestsCatalogFixtures.pullRequestMinimal());
        final Instant createdAt = Instant.parse("2024-01-02T03:04:05Z");
        final Instant updatedAt = Instant.parse("2024-01-02T03:04:05Z");
        final GitHubSimpleUser actor = UsersCatalogFixtures.octocat();
        final GitHubSimpleUser triggeringActor = UsersCatalogFixtures.octocat();
        final Instant runStartedAt = Instant.parse("2024-01-02T03:04:05Z");
        final String jobsUrl = "https://api.github.com/example";
        final String checkSuiteUrl = "https://api.github.com/example";
        final String artifactsUrl = "https://api.github.com/example";
        final String cancelUrl = "https://api.github.com/example";
        final String rerunUrl = "https://api.github.com/example";
        final String previousAttemptUrl = "https://api.github.com/example";
        final String workflowUrl = "https://api.github.com/example";
        final GitHubSimpleCommit headCommit = ChecksCatalogFixtures.simpleCommit();
        final GitHubMinimalRepository repository = RepositoriesCatalogFixtures.helloWorldMinimalRepository();
        final GitHubMinimalRepository headRepository = RepositoriesCatalogFixtures.helloWorldMinimalRepository();
        final Long headRepositoryId = 1L;
        final String displayTitle = "displayTitle-sample";

        final Throwable thrown = catchThrowable(() -> new GitHubWorkflowRun(
                id,
                name,
                nodeId,
                checkSuiteId,
                checkSuiteNodeId,
                headBranch,
                headSha,
                path,
                runNumber,
                runAttempt,
                referencedWorkflows,
                event,
                status,
                conclusion,
                workflowId,
                url,
                htmlUrl,
                pullRequests,
                createdAt,
                updatedAt,
                actor,
                triggeringActor,
                runStartedAt,
                jobsUrl,
                null,
                checkSuiteUrl,
                artifactsUrl,
                cancelUrl,
                rerunUrl,
                previousAttemptUrl,
                workflowUrl,
                headCommit,
                repository,
                headRepository,
                headRepositoryId,
                displayTitle));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("logsUrl");
    }

    @Test
    void constructor_shouldThrowNullPointerException_whenCheckSuiteUrlNull() {
        final long id = 1L;
        final String name = "name-sample";
        final String nodeId = "nodeId-sample";
        final Long checkSuiteId = 1L;
        final String checkSuiteNodeId = "checkSuiteNodeId-sample";
        final String headBranch = "headBranch-sample";
        final String headSha = "headSha-sample";
        final String path = "path-sample";
        final int runNumber = 1;
        final Integer runAttempt = 1;
        final List<GitHubReferencedWorkflow> referencedWorkflows = List.of(ActionsCatalogFixtures.referencedWorkflow());
        final String event = "event-sample";
        final String status = "status-sample";
        final String conclusion = "conclusion-sample";
        final int workflowId = 1;
        final String url = "https://api.github.com/example";
        final String htmlUrl = "https://github.com/example";
        final List<GitHubPullRequestMinimal> pullRequests = List.of(PullrequestsCatalogFixtures.pullRequestMinimal());
        final Instant createdAt = Instant.parse("2024-01-02T03:04:05Z");
        final Instant updatedAt = Instant.parse("2024-01-02T03:04:05Z");
        final GitHubSimpleUser actor = UsersCatalogFixtures.octocat();
        final GitHubSimpleUser triggeringActor = UsersCatalogFixtures.octocat();
        final Instant runStartedAt = Instant.parse("2024-01-02T03:04:05Z");
        final String jobsUrl = "https://api.github.com/example";
        final String logsUrl = "https://api.github.com/example";
        final String artifactsUrl = "https://api.github.com/example";
        final String cancelUrl = "https://api.github.com/example";
        final String rerunUrl = "https://api.github.com/example";
        final String previousAttemptUrl = "https://api.github.com/example";
        final String workflowUrl = "https://api.github.com/example";
        final GitHubSimpleCommit headCommit = ChecksCatalogFixtures.simpleCommit();
        final GitHubMinimalRepository repository = RepositoriesCatalogFixtures.helloWorldMinimalRepository();
        final GitHubMinimalRepository headRepository = RepositoriesCatalogFixtures.helloWorldMinimalRepository();
        final Long headRepositoryId = 1L;
        final String displayTitle = "displayTitle-sample";

        final Throwable thrown = catchThrowable(() -> new GitHubWorkflowRun(
                id,
                name,
                nodeId,
                checkSuiteId,
                checkSuiteNodeId,
                headBranch,
                headSha,
                path,
                runNumber,
                runAttempt,
                referencedWorkflows,
                event,
                status,
                conclusion,
                workflowId,
                url,
                htmlUrl,
                pullRequests,
                createdAt,
                updatedAt,
                actor,
                triggeringActor,
                runStartedAt,
                jobsUrl,
                logsUrl,
                null,
                artifactsUrl,
                cancelUrl,
                rerunUrl,
                previousAttemptUrl,
                workflowUrl,
                headCommit,
                repository,
                headRepository,
                headRepositoryId,
                displayTitle));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("checkSuiteUrl");
    }

    @Test
    void constructor_shouldThrowNullPointerException_whenArtifactsUrlNull() {
        final long id = 1L;
        final String name = "name-sample";
        final String nodeId = "nodeId-sample";
        final Long checkSuiteId = 1L;
        final String checkSuiteNodeId = "checkSuiteNodeId-sample";
        final String headBranch = "headBranch-sample";
        final String headSha = "headSha-sample";
        final String path = "path-sample";
        final int runNumber = 1;
        final Integer runAttempt = 1;
        final List<GitHubReferencedWorkflow> referencedWorkflows = List.of(ActionsCatalogFixtures.referencedWorkflow());
        final String event = "event-sample";
        final String status = "status-sample";
        final String conclusion = "conclusion-sample";
        final int workflowId = 1;
        final String url = "https://api.github.com/example";
        final String htmlUrl = "https://github.com/example";
        final List<GitHubPullRequestMinimal> pullRequests = List.of(PullrequestsCatalogFixtures.pullRequestMinimal());
        final Instant createdAt = Instant.parse("2024-01-02T03:04:05Z");
        final Instant updatedAt = Instant.parse("2024-01-02T03:04:05Z");
        final GitHubSimpleUser actor = UsersCatalogFixtures.octocat();
        final GitHubSimpleUser triggeringActor = UsersCatalogFixtures.octocat();
        final Instant runStartedAt = Instant.parse("2024-01-02T03:04:05Z");
        final String jobsUrl = "https://api.github.com/example";
        final String logsUrl = "https://api.github.com/example";
        final String checkSuiteUrl = "https://api.github.com/example";
        final String cancelUrl = "https://api.github.com/example";
        final String rerunUrl = "https://api.github.com/example";
        final String previousAttemptUrl = "https://api.github.com/example";
        final String workflowUrl = "https://api.github.com/example";
        final GitHubSimpleCommit headCommit = ChecksCatalogFixtures.simpleCommit();
        final GitHubMinimalRepository repository = RepositoriesCatalogFixtures.helloWorldMinimalRepository();
        final GitHubMinimalRepository headRepository = RepositoriesCatalogFixtures.helloWorldMinimalRepository();
        final Long headRepositoryId = 1L;
        final String displayTitle = "displayTitle-sample";

        final Throwable thrown = catchThrowable(() -> new GitHubWorkflowRun(
                id,
                name,
                nodeId,
                checkSuiteId,
                checkSuiteNodeId,
                headBranch,
                headSha,
                path,
                runNumber,
                runAttempt,
                referencedWorkflows,
                event,
                status,
                conclusion,
                workflowId,
                url,
                htmlUrl,
                pullRequests,
                createdAt,
                updatedAt,
                actor,
                triggeringActor,
                runStartedAt,
                jobsUrl,
                logsUrl,
                checkSuiteUrl,
                null,
                cancelUrl,
                rerunUrl,
                previousAttemptUrl,
                workflowUrl,
                headCommit,
                repository,
                headRepository,
                headRepositoryId,
                displayTitle));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("artifactsUrl");
    }

    @Test
    void constructor_shouldThrowNullPointerException_whenCancelUrlNull() {
        final long id = 1L;
        final String name = "name-sample";
        final String nodeId = "nodeId-sample";
        final Long checkSuiteId = 1L;
        final String checkSuiteNodeId = "checkSuiteNodeId-sample";
        final String headBranch = "headBranch-sample";
        final String headSha = "headSha-sample";
        final String path = "path-sample";
        final int runNumber = 1;
        final Integer runAttempt = 1;
        final List<GitHubReferencedWorkflow> referencedWorkflows = List.of(ActionsCatalogFixtures.referencedWorkflow());
        final String event = "event-sample";
        final String status = "status-sample";
        final String conclusion = "conclusion-sample";
        final int workflowId = 1;
        final String url = "https://api.github.com/example";
        final String htmlUrl = "https://github.com/example";
        final List<GitHubPullRequestMinimal> pullRequests = List.of(PullrequestsCatalogFixtures.pullRequestMinimal());
        final Instant createdAt = Instant.parse("2024-01-02T03:04:05Z");
        final Instant updatedAt = Instant.parse("2024-01-02T03:04:05Z");
        final GitHubSimpleUser actor = UsersCatalogFixtures.octocat();
        final GitHubSimpleUser triggeringActor = UsersCatalogFixtures.octocat();
        final Instant runStartedAt = Instant.parse("2024-01-02T03:04:05Z");
        final String jobsUrl = "https://api.github.com/example";
        final String logsUrl = "https://api.github.com/example";
        final String checkSuiteUrl = "https://api.github.com/example";
        final String artifactsUrl = "https://api.github.com/example";
        final String rerunUrl = "https://api.github.com/example";
        final String previousAttemptUrl = "https://api.github.com/example";
        final String workflowUrl = "https://api.github.com/example";
        final GitHubSimpleCommit headCommit = ChecksCatalogFixtures.simpleCommit();
        final GitHubMinimalRepository repository = RepositoriesCatalogFixtures.helloWorldMinimalRepository();
        final GitHubMinimalRepository headRepository = RepositoriesCatalogFixtures.helloWorldMinimalRepository();
        final Long headRepositoryId = 1L;
        final String displayTitle = "displayTitle-sample";

        final Throwable thrown = catchThrowable(() -> new GitHubWorkflowRun(
                id,
                name,
                nodeId,
                checkSuiteId,
                checkSuiteNodeId,
                headBranch,
                headSha,
                path,
                runNumber,
                runAttempt,
                referencedWorkflows,
                event,
                status,
                conclusion,
                workflowId,
                url,
                htmlUrl,
                pullRequests,
                createdAt,
                updatedAt,
                actor,
                triggeringActor,
                runStartedAt,
                jobsUrl,
                logsUrl,
                checkSuiteUrl,
                artifactsUrl,
                null,
                rerunUrl,
                previousAttemptUrl,
                workflowUrl,
                headCommit,
                repository,
                headRepository,
                headRepositoryId,
                displayTitle));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("cancelUrl");
    }

    @Test
    void constructor_shouldThrowNullPointerException_whenRerunUrlNull() {
        final long id = 1L;
        final String name = "name-sample";
        final String nodeId = "nodeId-sample";
        final Long checkSuiteId = 1L;
        final String checkSuiteNodeId = "checkSuiteNodeId-sample";
        final String headBranch = "headBranch-sample";
        final String headSha = "headSha-sample";
        final String path = "path-sample";
        final int runNumber = 1;
        final Integer runAttempt = 1;
        final List<GitHubReferencedWorkflow> referencedWorkflows = List.of(ActionsCatalogFixtures.referencedWorkflow());
        final String event = "event-sample";
        final String status = "status-sample";
        final String conclusion = "conclusion-sample";
        final int workflowId = 1;
        final String url = "https://api.github.com/example";
        final String htmlUrl = "https://github.com/example";
        final List<GitHubPullRequestMinimal> pullRequests = List.of(PullrequestsCatalogFixtures.pullRequestMinimal());
        final Instant createdAt = Instant.parse("2024-01-02T03:04:05Z");
        final Instant updatedAt = Instant.parse("2024-01-02T03:04:05Z");
        final GitHubSimpleUser actor = UsersCatalogFixtures.octocat();
        final GitHubSimpleUser triggeringActor = UsersCatalogFixtures.octocat();
        final Instant runStartedAt = Instant.parse("2024-01-02T03:04:05Z");
        final String jobsUrl = "https://api.github.com/example";
        final String logsUrl = "https://api.github.com/example";
        final String checkSuiteUrl = "https://api.github.com/example";
        final String artifactsUrl = "https://api.github.com/example";
        final String cancelUrl = "https://api.github.com/example";
        final String previousAttemptUrl = "https://api.github.com/example";
        final String workflowUrl = "https://api.github.com/example";
        final GitHubSimpleCommit headCommit = ChecksCatalogFixtures.simpleCommit();
        final GitHubMinimalRepository repository = RepositoriesCatalogFixtures.helloWorldMinimalRepository();
        final GitHubMinimalRepository headRepository = RepositoriesCatalogFixtures.helloWorldMinimalRepository();
        final Long headRepositoryId = 1L;
        final String displayTitle = "displayTitle-sample";

        final Throwable thrown = catchThrowable(() -> new GitHubWorkflowRun(
                id,
                name,
                nodeId,
                checkSuiteId,
                checkSuiteNodeId,
                headBranch,
                headSha,
                path,
                runNumber,
                runAttempt,
                referencedWorkflows,
                event,
                status,
                conclusion,
                workflowId,
                url,
                htmlUrl,
                pullRequests,
                createdAt,
                updatedAt,
                actor,
                triggeringActor,
                runStartedAt,
                jobsUrl,
                logsUrl,
                checkSuiteUrl,
                artifactsUrl,
                cancelUrl,
                null,
                previousAttemptUrl,
                workflowUrl,
                headCommit,
                repository,
                headRepository,
                headRepositoryId,
                displayTitle));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("rerunUrl");
    }

    @Test
    void constructor_shouldThrowNullPointerException_whenWorkflowUrlNull() {
        final long id = 1L;
        final String name = "name-sample";
        final String nodeId = "nodeId-sample";
        final Long checkSuiteId = 1L;
        final String checkSuiteNodeId = "checkSuiteNodeId-sample";
        final String headBranch = "headBranch-sample";
        final String headSha = "headSha-sample";
        final String path = "path-sample";
        final int runNumber = 1;
        final Integer runAttempt = 1;
        final List<GitHubReferencedWorkflow> referencedWorkflows = List.of(ActionsCatalogFixtures.referencedWorkflow());
        final String event = "event-sample";
        final String status = "status-sample";
        final String conclusion = "conclusion-sample";
        final int workflowId = 1;
        final String url = "https://api.github.com/example";
        final String htmlUrl = "https://github.com/example";
        final List<GitHubPullRequestMinimal> pullRequests = List.of(PullrequestsCatalogFixtures.pullRequestMinimal());
        final Instant createdAt = Instant.parse("2024-01-02T03:04:05Z");
        final Instant updatedAt = Instant.parse("2024-01-02T03:04:05Z");
        final GitHubSimpleUser actor = UsersCatalogFixtures.octocat();
        final GitHubSimpleUser triggeringActor = UsersCatalogFixtures.octocat();
        final Instant runStartedAt = Instant.parse("2024-01-02T03:04:05Z");
        final String jobsUrl = "https://api.github.com/example";
        final String logsUrl = "https://api.github.com/example";
        final String checkSuiteUrl = "https://api.github.com/example";
        final String artifactsUrl = "https://api.github.com/example";
        final String cancelUrl = "https://api.github.com/example";
        final String rerunUrl = "https://api.github.com/example";
        final String previousAttemptUrl = "https://api.github.com/example";
        final GitHubSimpleCommit headCommit = ChecksCatalogFixtures.simpleCommit();
        final GitHubMinimalRepository repository = RepositoriesCatalogFixtures.helloWorldMinimalRepository();
        final GitHubMinimalRepository headRepository = RepositoriesCatalogFixtures.helloWorldMinimalRepository();
        final Long headRepositoryId = 1L;
        final String displayTitle = "displayTitle-sample";

        final Throwable thrown = catchThrowable(() -> new GitHubWorkflowRun(
                id,
                name,
                nodeId,
                checkSuiteId,
                checkSuiteNodeId,
                headBranch,
                headSha,
                path,
                runNumber,
                runAttempt,
                referencedWorkflows,
                event,
                status,
                conclusion,
                workflowId,
                url,
                htmlUrl,
                pullRequests,
                createdAt,
                updatedAt,
                actor,
                triggeringActor,
                runStartedAt,
                jobsUrl,
                logsUrl,
                checkSuiteUrl,
                artifactsUrl,
                cancelUrl,
                rerunUrl,
                previousAttemptUrl,
                null,
                headCommit,
                repository,
                headRepository,
                headRepositoryId,
                displayTitle));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("workflowUrl");
    }

    @Test
    void constructor_shouldThrowNullPointerException_whenRepositoryNull() {
        final long id = 1L;
        final String name = "name-sample";
        final String nodeId = "nodeId-sample";
        final Long checkSuiteId = 1L;
        final String checkSuiteNodeId = "checkSuiteNodeId-sample";
        final String headBranch = "headBranch-sample";
        final String headSha = "headSha-sample";
        final String path = "path-sample";
        final int runNumber = 1;
        final Integer runAttempt = 1;
        final List<GitHubReferencedWorkflow> referencedWorkflows = List.of(ActionsCatalogFixtures.referencedWorkflow());
        final String event = "event-sample";
        final String status = "status-sample";
        final String conclusion = "conclusion-sample";
        final int workflowId = 1;
        final String url = "https://api.github.com/example";
        final String htmlUrl = "https://github.com/example";
        final List<GitHubPullRequestMinimal> pullRequests = List.of(PullrequestsCatalogFixtures.pullRequestMinimal());
        final Instant createdAt = Instant.parse("2024-01-02T03:04:05Z");
        final Instant updatedAt = Instant.parse("2024-01-02T03:04:05Z");
        final GitHubSimpleUser actor = UsersCatalogFixtures.octocat();
        final GitHubSimpleUser triggeringActor = UsersCatalogFixtures.octocat();
        final Instant runStartedAt = Instant.parse("2024-01-02T03:04:05Z");
        final String jobsUrl = "https://api.github.com/example";
        final String logsUrl = "https://api.github.com/example";
        final String checkSuiteUrl = "https://api.github.com/example";
        final String artifactsUrl = "https://api.github.com/example";
        final String cancelUrl = "https://api.github.com/example";
        final String rerunUrl = "https://api.github.com/example";
        final String previousAttemptUrl = "https://api.github.com/example";
        final String workflowUrl = "https://api.github.com/example";
        final GitHubSimpleCommit headCommit = ChecksCatalogFixtures.simpleCommit();
        final GitHubMinimalRepository headRepository = RepositoriesCatalogFixtures.helloWorldMinimalRepository();
        final Long headRepositoryId = 1L;
        final String displayTitle = "displayTitle-sample";

        final Throwable thrown = catchThrowable(() -> new GitHubWorkflowRun(
                id,
                name,
                nodeId,
                checkSuiteId,
                checkSuiteNodeId,
                headBranch,
                headSha,
                path,
                runNumber,
                runAttempt,
                referencedWorkflows,
                event,
                status,
                conclusion,
                workflowId,
                url,
                htmlUrl,
                pullRequests,
                createdAt,
                updatedAt,
                actor,
                triggeringActor,
                runStartedAt,
                jobsUrl,
                logsUrl,
                checkSuiteUrl,
                artifactsUrl,
                cancelUrl,
                rerunUrl,
                previousAttemptUrl,
                workflowUrl,
                headCommit,
                null,
                headRepository,
                headRepositoryId,
                displayTitle));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("repository");
    }

    @Test
    void constructor_shouldThrowNullPointerException_whenHeadRepositoryNull() {
        final long id = 1L;
        final String name = "name-sample";
        final String nodeId = "nodeId-sample";
        final Long checkSuiteId = 1L;
        final String checkSuiteNodeId = "checkSuiteNodeId-sample";
        final String headBranch = "headBranch-sample";
        final String headSha = "headSha-sample";
        final String path = "path-sample";
        final int runNumber = 1;
        final Integer runAttempt = 1;
        final List<GitHubReferencedWorkflow> referencedWorkflows = List.of(ActionsCatalogFixtures.referencedWorkflow());
        final String event = "event-sample";
        final String status = "status-sample";
        final String conclusion = "conclusion-sample";
        final int workflowId = 1;
        final String url = "https://api.github.com/example";
        final String htmlUrl = "https://github.com/example";
        final List<GitHubPullRequestMinimal> pullRequests = List.of(PullrequestsCatalogFixtures.pullRequestMinimal());
        final Instant createdAt = Instant.parse("2024-01-02T03:04:05Z");
        final Instant updatedAt = Instant.parse("2024-01-02T03:04:05Z");
        final GitHubSimpleUser actor = UsersCatalogFixtures.octocat();
        final GitHubSimpleUser triggeringActor = UsersCatalogFixtures.octocat();
        final Instant runStartedAt = Instant.parse("2024-01-02T03:04:05Z");
        final String jobsUrl = "https://api.github.com/example";
        final String logsUrl = "https://api.github.com/example";
        final String checkSuiteUrl = "https://api.github.com/example";
        final String artifactsUrl = "https://api.github.com/example";
        final String cancelUrl = "https://api.github.com/example";
        final String rerunUrl = "https://api.github.com/example";
        final String previousAttemptUrl = "https://api.github.com/example";
        final String workflowUrl = "https://api.github.com/example";
        final GitHubSimpleCommit headCommit = ChecksCatalogFixtures.simpleCommit();
        final GitHubMinimalRepository repository = RepositoriesCatalogFixtures.helloWorldMinimalRepository();
        final Long headRepositoryId = 1L;
        final String displayTitle = "displayTitle-sample";

        final Throwable thrown = catchThrowable(() -> new GitHubWorkflowRun(
                id,
                name,
                nodeId,
                checkSuiteId,
                checkSuiteNodeId,
                headBranch,
                headSha,
                path,
                runNumber,
                runAttempt,
                referencedWorkflows,
                event,
                status,
                conclusion,
                workflowId,
                url,
                htmlUrl,
                pullRequests,
                createdAt,
                updatedAt,
                actor,
                triggeringActor,
                runStartedAt,
                jobsUrl,
                logsUrl,
                checkSuiteUrl,
                artifactsUrl,
                cancelUrl,
                rerunUrl,
                previousAttemptUrl,
                workflowUrl,
                headCommit,
                repository,
                null,
                headRepositoryId,
                displayTitle));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("headRepository");
    }

    @Test
    void constructor_shouldThrowNullPointerException_whenDisplayTitleNull() {
        final long id = 1L;
        final String name = "name-sample";
        final String nodeId = "nodeId-sample";
        final Long checkSuiteId = 1L;
        final String checkSuiteNodeId = "checkSuiteNodeId-sample";
        final String headBranch = "headBranch-sample";
        final String headSha = "headSha-sample";
        final String path = "path-sample";
        final int runNumber = 1;
        final Integer runAttempt = 1;
        final List<GitHubReferencedWorkflow> referencedWorkflows = List.of(ActionsCatalogFixtures.referencedWorkflow());
        final String event = "event-sample";
        final String status = "status-sample";
        final String conclusion = "conclusion-sample";
        final int workflowId = 1;
        final String url = "https://api.github.com/example";
        final String htmlUrl = "https://github.com/example";
        final List<GitHubPullRequestMinimal> pullRequests = List.of(PullrequestsCatalogFixtures.pullRequestMinimal());
        final Instant createdAt = Instant.parse("2024-01-02T03:04:05Z");
        final Instant updatedAt = Instant.parse("2024-01-02T03:04:05Z");
        final GitHubSimpleUser actor = UsersCatalogFixtures.octocat();
        final GitHubSimpleUser triggeringActor = UsersCatalogFixtures.octocat();
        final Instant runStartedAt = Instant.parse("2024-01-02T03:04:05Z");
        final String jobsUrl = "https://api.github.com/example";
        final String logsUrl = "https://api.github.com/example";
        final String checkSuiteUrl = "https://api.github.com/example";
        final String artifactsUrl = "https://api.github.com/example";
        final String cancelUrl = "https://api.github.com/example";
        final String rerunUrl = "https://api.github.com/example";
        final String previousAttemptUrl = "https://api.github.com/example";
        final String workflowUrl = "https://api.github.com/example";
        final GitHubSimpleCommit headCommit = ChecksCatalogFixtures.simpleCommit();
        final GitHubMinimalRepository repository = RepositoriesCatalogFixtures.helloWorldMinimalRepository();
        final GitHubMinimalRepository headRepository = RepositoriesCatalogFixtures.helloWorldMinimalRepository();
        final Long headRepositoryId = 1L;

        final Throwable thrown = catchThrowable(() -> new GitHubWorkflowRun(
                id,
                name,
                nodeId,
                checkSuiteId,
                checkSuiteNodeId,
                headBranch,
                headSha,
                path,
                runNumber,
                runAttempt,
                referencedWorkflows,
                event,
                status,
                conclusion,
                workflowId,
                url,
                htmlUrl,
                pullRequests,
                createdAt,
                updatedAt,
                actor,
                triggeringActor,
                runStartedAt,
                jobsUrl,
                logsUrl,
                checkSuiteUrl,
                artifactsUrl,
                cancelUrl,
                rerunUrl,
                previousAttemptUrl,
                workflowUrl,
                headCommit,
                repository,
                headRepository,
                headRepositoryId,
                null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("displayTitle");
    }

    @Test
    void constructor_shouldAcceptNullReferencedWorkflows_whenReferencedWorkflowsNull() {
        final GitHubWorkflowRun workflowRun = GitHubWorkflowRunBuilder.builder(ActionsCatalogFixtures.workflowRun())
                .referencedWorkflows(null)
                .build();

        assertThat(workflowRun.referencedWorkflows()).isNull();
    }

    @Test
    void constructor_shouldAcceptNullPullRequests_whenPullRequestsNull() {
        final GitHubWorkflowRun workflowRun = GitHubWorkflowRunBuilder.builder(ActionsCatalogFixtures.workflowRun())
                .pullRequests(null)
                .build();

        assertThat(workflowRun.pullRequests()).isNull();
    }
}
