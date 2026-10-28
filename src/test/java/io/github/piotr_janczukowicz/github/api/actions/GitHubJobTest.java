package io.github.piotr_janczukowicz.github.api.actions;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.time.Instant;
import java.util.List;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubJobTest {

    @Test
    void constructor_shouldThrowNullPointerException_whenRunUrlNull() {
        final long id = 1L;
        final long runId = 1L;
        final Integer runAttempt = 1;
        final String nodeId = "nodeId-sample";
        final String headSha = "headSha-sample";
        final String url = "https://api.github.com/example";
        final String htmlUrl = "https://github.com/example";
        final GitHubJobStatus status = GitHubJobStatus.QUEUED;
        final GitHubJobConclusion conclusion = GitHubJobConclusion.SUCCESS;
        final Instant createdAt = Instant.parse("2024-01-02T03:04:05Z");
        final Instant startedAt = Instant.parse("2024-01-02T03:04:05Z");
        final Instant completedAt = Instant.parse("2024-01-02T03:04:05Z");
        final String name = "name-sample";
        final List<GitHubJobStep> steps = List.of(ActionsCatalogFixtures.jobStep());
        final String checkRunUrl = "https://api.github.com/example";
        final List<String> labels = List.of("labels-sample");
        final Long runnerId = 1L;
        final String runnerName = "runnerName-sample";
        final Long runnerGroupId = 1L;
        final String runnerGroupName = "runnerGroupName-sample";
        final String workflowName = "workflowName-sample";
        final String headBranch = "headBranch-sample";

        final Throwable thrown = catchThrowable(() -> new GitHubJob(
                id,
                runId,
                null,
                runAttempt,
                nodeId,
                headSha,
                url,
                htmlUrl,
                status,
                conclusion,
                createdAt,
                startedAt,
                completedAt,
                name,
                steps,
                checkRunUrl,
                labels,
                runnerId,
                runnerName,
                runnerGroupId,
                runnerGroupName,
                workflowName,
                headBranch));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("runUrl");
    }

    @Test
    void constructor_shouldThrowNullPointerException_whenNodeIdNull() {
        final long id = 1L;
        final long runId = 1L;
        final String runUrl = "https://api.github.com/example";
        final Integer runAttempt = 1;
        final String headSha = "headSha-sample";
        final String url = "https://api.github.com/example";
        final String htmlUrl = "https://github.com/example";
        final GitHubJobStatus status = GitHubJobStatus.QUEUED;
        final GitHubJobConclusion conclusion = GitHubJobConclusion.SUCCESS;
        final Instant createdAt = Instant.parse("2024-01-02T03:04:05Z");
        final Instant startedAt = Instant.parse("2024-01-02T03:04:05Z");
        final Instant completedAt = Instant.parse("2024-01-02T03:04:05Z");
        final String name = "name-sample";
        final List<GitHubJobStep> steps = List.of(ActionsCatalogFixtures.jobStep());
        final String checkRunUrl = "https://api.github.com/example";
        final List<String> labels = List.of("labels-sample");
        final Long runnerId = 1L;
        final String runnerName = "runnerName-sample";
        final Long runnerGroupId = 1L;
        final String runnerGroupName = "runnerGroupName-sample";
        final String workflowName = "workflowName-sample";
        final String headBranch = "headBranch-sample";

        final Throwable thrown = catchThrowable(() -> new GitHubJob(
                id,
                runId,
                runUrl,
                runAttempt,
                null,
                headSha,
                url,
                htmlUrl,
                status,
                conclusion,
                createdAt,
                startedAt,
                completedAt,
                name,
                steps,
                checkRunUrl,
                labels,
                runnerId,
                runnerName,
                runnerGroupId,
                runnerGroupName,
                workflowName,
                headBranch));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("nodeId");
    }

    @Test
    void constructor_shouldThrowNullPointerException_whenHeadShaNull() {
        final long id = 1L;
        final long runId = 1L;
        final String runUrl = "https://api.github.com/example";
        final Integer runAttempt = 1;
        final String nodeId = "nodeId-sample";
        final String url = "https://api.github.com/example";
        final String htmlUrl = "https://github.com/example";
        final GitHubJobStatus status = GitHubJobStatus.QUEUED;
        final GitHubJobConclusion conclusion = GitHubJobConclusion.SUCCESS;
        final Instant createdAt = Instant.parse("2024-01-02T03:04:05Z");
        final Instant startedAt = Instant.parse("2024-01-02T03:04:05Z");
        final Instant completedAt = Instant.parse("2024-01-02T03:04:05Z");
        final String name = "name-sample";
        final List<GitHubJobStep> steps = List.of(ActionsCatalogFixtures.jobStep());
        final String checkRunUrl = "https://api.github.com/example";
        final List<String> labels = List.of("labels-sample");
        final Long runnerId = 1L;
        final String runnerName = "runnerName-sample";
        final Long runnerGroupId = 1L;
        final String runnerGroupName = "runnerGroupName-sample";
        final String workflowName = "workflowName-sample";
        final String headBranch = "headBranch-sample";

        final Throwable thrown = catchThrowable(() -> new GitHubJob(
                id,
                runId,
                runUrl,
                runAttempt,
                nodeId,
                null,
                url,
                htmlUrl,
                status,
                conclusion,
                createdAt,
                startedAt,
                completedAt,
                name,
                steps,
                checkRunUrl,
                labels,
                runnerId,
                runnerName,
                runnerGroupId,
                runnerGroupName,
                workflowName,
                headBranch));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("headSha");
    }

    @Test
    void constructor_shouldThrowNullPointerException_whenUrlNull() {
        final long id = 1L;
        final long runId = 1L;
        final String runUrl = "https://api.github.com/example";
        final Integer runAttempt = 1;
        final String nodeId = "nodeId-sample";
        final String headSha = "headSha-sample";
        final String htmlUrl = "https://github.com/example";
        final GitHubJobStatus status = GitHubJobStatus.QUEUED;
        final GitHubJobConclusion conclusion = GitHubJobConclusion.SUCCESS;
        final Instant createdAt = Instant.parse("2024-01-02T03:04:05Z");
        final Instant startedAt = Instant.parse("2024-01-02T03:04:05Z");
        final Instant completedAt = Instant.parse("2024-01-02T03:04:05Z");
        final String name = "name-sample";
        final List<GitHubJobStep> steps = List.of(ActionsCatalogFixtures.jobStep());
        final String checkRunUrl = "https://api.github.com/example";
        final List<String> labels = List.of("labels-sample");
        final Long runnerId = 1L;
        final String runnerName = "runnerName-sample";
        final Long runnerGroupId = 1L;
        final String runnerGroupName = "runnerGroupName-sample";
        final String workflowName = "workflowName-sample";
        final String headBranch = "headBranch-sample";

        final Throwable thrown = catchThrowable(() -> new GitHubJob(
                id,
                runId,
                runUrl,
                runAttempt,
                nodeId,
                headSha,
                null,
                htmlUrl,
                status,
                conclusion,
                createdAt,
                startedAt,
                completedAt,
                name,
                steps,
                checkRunUrl,
                labels,
                runnerId,
                runnerName,
                runnerGroupId,
                runnerGroupName,
                workflowName,
                headBranch));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("url");
    }

    @Test
    void constructor_shouldThrowNullPointerException_whenStatusNull() {
        final long id = 1L;
        final long runId = 1L;
        final String runUrl = "https://api.github.com/example";
        final Integer runAttempt = 1;
        final String nodeId = "nodeId-sample";
        final String headSha = "headSha-sample";
        final String url = "https://api.github.com/example";
        final String htmlUrl = "https://github.com/example";
        final GitHubJobConclusion conclusion = GitHubJobConclusion.SUCCESS;
        final Instant createdAt = Instant.parse("2024-01-02T03:04:05Z");
        final Instant startedAt = Instant.parse("2024-01-02T03:04:05Z");
        final Instant completedAt = Instant.parse("2024-01-02T03:04:05Z");
        final String name = "name-sample";
        final List<GitHubJobStep> steps = List.of(ActionsCatalogFixtures.jobStep());
        final String checkRunUrl = "https://api.github.com/example";
        final List<String> labels = List.of("labels-sample");
        final Long runnerId = 1L;
        final String runnerName = "runnerName-sample";
        final Long runnerGroupId = 1L;
        final String runnerGroupName = "runnerGroupName-sample";
        final String workflowName = "workflowName-sample";
        final String headBranch = "headBranch-sample";

        final Throwable thrown = catchThrowable(() -> new GitHubJob(
                id,
                runId,
                runUrl,
                runAttempt,
                nodeId,
                headSha,
                url,
                htmlUrl,
                null,
                conclusion,
                createdAt,
                startedAt,
                completedAt,
                name,
                steps,
                checkRunUrl,
                labels,
                runnerId,
                runnerName,
                runnerGroupId,
                runnerGroupName,
                workflowName,
                headBranch));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("status");
    }

    @Test
    void constructor_shouldThrowNullPointerException_whenCreatedAtNull() {
        final long id = 1L;
        final long runId = 1L;
        final String runUrl = "https://api.github.com/example";
        final Integer runAttempt = 1;
        final String nodeId = "nodeId-sample";
        final String headSha = "headSha-sample";
        final String url = "https://api.github.com/example";
        final String htmlUrl = "https://github.com/example";
        final GitHubJobStatus status = GitHubJobStatus.QUEUED;
        final GitHubJobConclusion conclusion = GitHubJobConclusion.SUCCESS;
        final Instant startedAt = Instant.parse("2024-01-02T03:04:05Z");
        final Instant completedAt = Instant.parse("2024-01-02T03:04:05Z");
        final String name = "name-sample";
        final List<GitHubJobStep> steps = List.of(ActionsCatalogFixtures.jobStep());
        final String checkRunUrl = "https://api.github.com/example";
        final List<String> labels = List.of("labels-sample");
        final Long runnerId = 1L;
        final String runnerName = "runnerName-sample";
        final Long runnerGroupId = 1L;
        final String runnerGroupName = "runnerGroupName-sample";
        final String workflowName = "workflowName-sample";
        final String headBranch = "headBranch-sample";

        final Throwable thrown = catchThrowable(() -> new GitHubJob(
                id,
                runId,
                runUrl,
                runAttempt,
                nodeId,
                headSha,
                url,
                htmlUrl,
                status,
                conclusion,
                null,
                startedAt,
                completedAt,
                name,
                steps,
                checkRunUrl,
                labels,
                runnerId,
                runnerName,
                runnerGroupId,
                runnerGroupName,
                workflowName,
                headBranch));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("createdAt");
    }

    @Test
    void constructor_shouldThrowNullPointerException_whenStartedAtNull() {
        final long id = 1L;
        final long runId = 1L;
        final String runUrl = "https://api.github.com/example";
        final Integer runAttempt = 1;
        final String nodeId = "nodeId-sample";
        final String headSha = "headSha-sample";
        final String url = "https://api.github.com/example";
        final String htmlUrl = "https://github.com/example";
        final GitHubJobStatus status = GitHubJobStatus.QUEUED;
        final GitHubJobConclusion conclusion = GitHubJobConclusion.SUCCESS;
        final Instant createdAt = Instant.parse("2024-01-02T03:04:05Z");
        final Instant completedAt = Instant.parse("2024-01-02T03:04:05Z");
        final String name = "name-sample";
        final List<GitHubJobStep> steps = List.of(ActionsCatalogFixtures.jobStep());
        final String checkRunUrl = "https://api.github.com/example";
        final List<String> labels = List.of("labels-sample");
        final Long runnerId = 1L;
        final String runnerName = "runnerName-sample";
        final Long runnerGroupId = 1L;
        final String runnerGroupName = "runnerGroupName-sample";
        final String workflowName = "workflowName-sample";
        final String headBranch = "headBranch-sample";

        final Throwable thrown = catchThrowable(() -> new GitHubJob(
                id,
                runId,
                runUrl,
                runAttempt,
                nodeId,
                headSha,
                url,
                htmlUrl,
                status,
                conclusion,
                createdAt,
                null,
                completedAt,
                name,
                steps,
                checkRunUrl,
                labels,
                runnerId,
                runnerName,
                runnerGroupId,
                runnerGroupName,
                workflowName,
                headBranch));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("startedAt");
    }

    @Test
    void constructor_shouldThrowNullPointerException_whenNameNull() {
        final long id = 1L;
        final long runId = 1L;
        final String runUrl = "https://api.github.com/example";
        final Integer runAttempt = 1;
        final String nodeId = "nodeId-sample";
        final String headSha = "headSha-sample";
        final String url = "https://api.github.com/example";
        final String htmlUrl = "https://github.com/example";
        final GitHubJobStatus status = GitHubJobStatus.QUEUED;
        final GitHubJobConclusion conclusion = GitHubJobConclusion.SUCCESS;
        final Instant createdAt = Instant.parse("2024-01-02T03:04:05Z");
        final Instant startedAt = Instant.parse("2024-01-02T03:04:05Z");
        final Instant completedAt = Instant.parse("2024-01-02T03:04:05Z");
        final List<GitHubJobStep> steps = List.of(ActionsCatalogFixtures.jobStep());
        final String checkRunUrl = "https://api.github.com/example";
        final List<String> labels = List.of("labels-sample");
        final Long runnerId = 1L;
        final String runnerName = "runnerName-sample";
        final Long runnerGroupId = 1L;
        final String runnerGroupName = "runnerGroupName-sample";
        final String workflowName = "workflowName-sample";
        final String headBranch = "headBranch-sample";

        final Throwable thrown = catchThrowable(() -> new GitHubJob(
                id,
                runId,
                runUrl,
                runAttempt,
                nodeId,
                headSha,
                url,
                htmlUrl,
                status,
                conclusion,
                createdAt,
                startedAt,
                completedAt,
                null,
                steps,
                checkRunUrl,
                labels,
                runnerId,
                runnerName,
                runnerGroupId,
                runnerGroupName,
                workflowName,
                headBranch));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("name");
    }

    @Test
    void constructor_shouldThrowNullPointerException_whenCheckRunUrlNull() {
        final long id = 1L;
        final long runId = 1L;
        final String runUrl = "https://api.github.com/example";
        final Integer runAttempt = 1;
        final String nodeId = "nodeId-sample";
        final String headSha = "headSha-sample";
        final String url = "https://api.github.com/example";
        final String htmlUrl = "https://github.com/example";
        final GitHubJobStatus status = GitHubJobStatus.QUEUED;
        final GitHubJobConclusion conclusion = GitHubJobConclusion.SUCCESS;
        final Instant createdAt = Instant.parse("2024-01-02T03:04:05Z");
        final Instant startedAt = Instant.parse("2024-01-02T03:04:05Z");
        final Instant completedAt = Instant.parse("2024-01-02T03:04:05Z");
        final String name = "name-sample";
        final List<GitHubJobStep> steps = List.of(ActionsCatalogFixtures.jobStep());
        final List<String> labels = List.of("labels-sample");
        final Long runnerId = 1L;
        final String runnerName = "runnerName-sample";
        final Long runnerGroupId = 1L;
        final String runnerGroupName = "runnerGroupName-sample";
        final String workflowName = "workflowName-sample";
        final String headBranch = "headBranch-sample";

        final Throwable thrown = catchThrowable(() -> new GitHubJob(
                id,
                runId,
                runUrl,
                runAttempt,
                nodeId,
                headSha,
                url,
                htmlUrl,
                status,
                conclusion,
                createdAt,
                startedAt,
                completedAt,
                name,
                steps,
                null,
                labels,
                runnerId,
                runnerName,
                runnerGroupId,
                runnerGroupName,
                workflowName,
                headBranch));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("checkRunUrl");
    }

    @Test
    void constructor_shouldThrowNullPointerException_whenLabelsNull() {
        final long id = 1L;
        final long runId = 1L;
        final String runUrl = "https://api.github.com/example";
        final Integer runAttempt = 1;
        final String nodeId = "nodeId-sample";
        final String headSha = "headSha-sample";
        final String url = "https://api.github.com/example";
        final String htmlUrl = "https://github.com/example";
        final GitHubJobStatus status = GitHubJobStatus.QUEUED;
        final GitHubJobConclusion conclusion = GitHubJobConclusion.SUCCESS;
        final Instant createdAt = Instant.parse("2024-01-02T03:04:05Z");
        final Instant startedAt = Instant.parse("2024-01-02T03:04:05Z");
        final Instant completedAt = Instant.parse("2024-01-02T03:04:05Z");
        final String name = "name-sample";
        final List<GitHubJobStep> steps = List.of(ActionsCatalogFixtures.jobStep());
        final String checkRunUrl = "https://api.github.com/example";
        final Long runnerId = 1L;
        final String runnerName = "runnerName-sample";
        final Long runnerGroupId = 1L;
        final String runnerGroupName = "runnerGroupName-sample";
        final String workflowName = "workflowName-sample";
        final String headBranch = "headBranch-sample";

        final Throwable thrown = catchThrowable(() -> new GitHubJob(
                id,
                runId,
                runUrl,
                runAttempt,
                nodeId,
                headSha,
                url,
                htmlUrl,
                status,
                conclusion,
                createdAt,
                startedAt,
                completedAt,
                name,
                steps,
                checkRunUrl,
                null,
                runnerId,
                runnerName,
                runnerGroupId,
                runnerGroupName,
                workflowName,
                headBranch));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("labels");
    }

    @Test
    void constructor_shouldAcceptNullSteps_whenStepsNull() {
        final GitHubJob job = GitHubJobBuilder.builder(ActionsCatalogFixtures.job())
                .steps(null)
                .build();

        assertThat(job.steps()).isNull();
    }
}
