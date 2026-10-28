package io.github.piotr_janczukowicz.github.api.pullrequests;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubCreatePullRequestPayloadTest {

    @Test
    void constructor_shouldThrow_whenHeadBlank() {
        final Throwable thrown = catchThrowable(() -> GitHubCreatePullRequestPayloadBuilder.builder()
                .head(" ")
                .base("master")
                .title("title")
                .build());

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("head");
    }

    @Test
    void constructor_shouldThrow_whenBaseBlank() {
        final Throwable thrown = catchThrowable(() -> GitHubCreatePullRequestPayloadBuilder.builder()
                .head("feature")
                .base(" ")
                .title("title")
                .build());

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("base");
    }

    @Test
    void constructor_shouldThrow_whenNeitherTitleNorIssue() {
        final Throwable thrown = catchThrowable(() -> GitHubCreatePullRequestPayloadBuilder.builder()
                .head("feature")
                .base("master")
                .build());

        assertThat(thrown)
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("title or issue is required");
    }

    @Test
    void constructor_shouldAcceptTitleAndIssue_whenBothPresent() {
        final GitHubCreatePullRequestPayload payload = GitHubCreatePullRequestPayloadBuilder.builder()
                .head("feature")
                .base("master")
                .title("title")
                .issue(1L)
                .build();

        assertSoftly(softly -> {
            softly.assertThat(payload.title()).isEqualTo("title");
            softly.assertThat(payload.issue()).isEqualTo(1L);
        });
    }

    @Test
    void constructor_shouldThrow_whenTitleBlank() {
        final Throwable thrown = catchThrowable(() -> GitHubCreatePullRequestPayloadBuilder.builder()
                .head("feature")
                .base("master")
                .title(" ")
                .build());

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("title");
    }

    @Test
    void constructor_shouldAcceptTitle_whenIssueAbsent() {
        final GitHubCreatePullRequestPayload payload = GitHubCreatePullRequestPayloadBuilder.builder()
                .head("feature")
                .base("master")
                .title("new-feature")
                .build();

        assertThat(payload.title()).isEqualTo("new-feature");
    }

    @Test
    void constructor_shouldAcceptIssue_whenTitleAbsent() {
        final GitHubCreatePullRequestPayload payload = GitHubCreatePullRequestPayloadBuilder.builder()
                .head("feature")
                .base("master")
                .issue(1347L)
                .build();

        assertThat(payload.issue()).isEqualTo(1347L);
    }
}
