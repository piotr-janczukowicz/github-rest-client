package io.github.piotr_janczukowicz.github.api.actions;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubConcurrencyGroupRunMemberTest {

    @Test
    void constructor_shouldThrowNullPointerException_whenRunNameNull() {
        final int runId = 1;
        final String runUrl = "https://api.github.com/example";
        final String runHtmlUrl = "https://github.com/example";
        final int position = 1;
        final String positionUrl = "https://api.github.com/example";
        final Long jobId = 1L;
        final String jobName = "jobName-sample";
        final String jobUrl = "https://api.github.com/example";
        final String jobHtmlUrl = "https://github.com/example";
        final GitHubConcurrencyGroupMemberStatus status = GitHubConcurrencyGroupMemberStatus.IN_PROGRESS;

        final Throwable thrown = catchThrowable(() -> new GitHubConcurrencyGroupRunMember(
                runId, null, runUrl, runHtmlUrl, position, positionUrl, jobId, jobName, jobUrl, jobHtmlUrl, status));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("runName");
    }

    @Test
    void constructor_shouldThrowNullPointerException_whenPositionUrlNull() {
        final int runId = 1;
        final String runName = "runName-sample";
        final String runUrl = "https://api.github.com/example";
        final String runHtmlUrl = "https://github.com/example";
        final int position = 1;
        final Long jobId = 1L;
        final String jobName = "jobName-sample";
        final String jobUrl = "https://api.github.com/example";
        final String jobHtmlUrl = "https://github.com/example";
        final GitHubConcurrencyGroupMemberStatus status = GitHubConcurrencyGroupMemberStatus.IN_PROGRESS;

        final Throwable thrown = catchThrowable(() -> new GitHubConcurrencyGroupRunMember(
                runId, runName, runUrl, runHtmlUrl, position, null, jobId, jobName, jobUrl, jobHtmlUrl, status));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("positionUrl");
    }

    @Test
    void constructor_shouldThrowNullPointerException_whenStatusNull() {
        final int runId = 1;
        final String runName = "runName-sample";
        final String runUrl = "https://api.github.com/example";
        final String runHtmlUrl = "https://github.com/example";
        final int position = 1;
        final String positionUrl = "https://api.github.com/example";
        final Long jobId = 1L;
        final String jobName = "jobName-sample";
        final String jobUrl = "https://api.github.com/example";
        final String jobHtmlUrl = "https://github.com/example";

        final Throwable thrown = catchThrowable(() -> new GitHubConcurrencyGroupRunMember(
                runId, runName, runUrl, runHtmlUrl, position, positionUrl, jobId, jobName, jobUrl, jobHtmlUrl, null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("status");
    }
}
