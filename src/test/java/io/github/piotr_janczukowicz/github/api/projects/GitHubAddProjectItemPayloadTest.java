package io.github.piotr_janczukowicz.github.api.projects;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubAddProjectItemPayloadTest {

    @Test
    void byId_shouldCreatePayload_whenCalled() {
        final GitHubAddProjectItemPayload payload =
                GitHubAddProjectItemPayload.byId(GitHubProjectItemContentType.ISSUE, 3L);

        assertSoftly(softly -> {
            softly.assertThat(payload.type()).isEqualTo(GitHubProjectItemContentType.ISSUE);
            softly.assertThat(payload.id()).isEqualTo(3L);
            softly.assertThat(payload.owner()).isNull();
        });
    }

    @Test
    void byRepository_shouldCreatePayload_whenCalled() {
        final GitHubAddProjectItemPayload payload = GitHubAddProjectItemPayload.byRepository(
                GitHubProjectItemContentType.PULL_REQUEST, "octocat", "hello-world", 123);

        assertSoftly(softly -> {
            softly.assertThat(payload.owner()).isEqualTo("octocat");
            softly.assertThat(payload.repo()).isEqualTo("hello-world");
            softly.assertThat(payload.number()).isEqualTo(123);
        });
    }

    @Test
    void constructor_shouldThrow_whenTypeAndIdAndRepositoryBothSet() {
        final Throwable thrown = catchThrowable(() ->
                new GitHubAddProjectItemPayload(GitHubProjectItemContentType.ISSUE, 3L, "octocat", "hello-world", 42));

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("exactly one");
    }

    @Test
    void constructor_shouldThrow_whenTypeIsUnknown() {
        final Throwable thrown =
                catchThrowable(() -> GitHubAddProjectItemPayload.byId(GitHubProjectItemContentType.UNKNOWN, 1L));

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("type");
    }

    @Test
    void constructor_shouldThrow_whenTypeIsDraftIssue() {
        final Throwable thrown =
                catchThrowable(() -> GitHubAddProjectItemPayload.byId(GitHubProjectItemContentType.DRAFT_ISSUE, 1L));

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("type");
    }

    @Test
    void constructor_shouldThrow_whenNeitherIdNorRepositorySet() {
        final Throwable thrown = catchThrowable(
                () -> new GitHubAddProjectItemPayload(GitHubProjectItemContentType.ISSUE, null, null, null, null));

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("exactly one");
    }

    @Test
    void constructor_shouldThrow_whenRepositoryOwnerNull() {
        final Throwable thrown = catchThrowable(() ->
                new GitHubAddProjectItemPayload(GitHubProjectItemContentType.ISSUE, null, null, "hello-world", 1));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("owner");
    }

    @Test
    void constructor_shouldThrow_whenRepositoryRepoNull() {
        final Throwable thrown = catchThrowable(
                () -> new GitHubAddProjectItemPayload(GitHubProjectItemContentType.ISSUE, null, "octocat", null, 1));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("repo");
    }

    @Test
    void constructor_shouldThrow_whenRepositoryNumberNull() {
        final Throwable thrown = catchThrowable(() -> new GitHubAddProjectItemPayload(
                GitHubProjectItemContentType.ISSUE, null, "octocat", "hello-world", null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("number");
    }

    @Test
    void constructor_shouldThrow_whenOnlyNumberSet() {
        final Throwable thrown = catchThrowable(
                () -> new GitHubAddProjectItemPayload(GitHubProjectItemContentType.ISSUE, null, null, null, 1));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("owner");
    }

    @Test
    void constructor_shouldThrow_whenOnlyRepoSet() {
        final Throwable thrown = catchThrowable(() ->
                new GitHubAddProjectItemPayload(GitHubProjectItemContentType.ISSUE, null, null, "hello-world", null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("owner");
    }
}
