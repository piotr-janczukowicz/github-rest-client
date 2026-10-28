package io.github.piotr_janczukowicz.github.api.commits;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubCommitStatusTest {

    @Test
    void constructor_shouldThrow_whenUrlNull() {
        final Throwable thrown = catchThrowable(() -> new GitHubCommitStatus(
                null, null, 1L, "node", "success", null, null, "ci", "created", "updated", null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("url");
    }

    @Test
    void constructor_shouldThrow_whenNodeIdNull() {
        final Throwable thrown = catchThrowable(() ->
                new GitHubCommitStatus("url", null, 1L, null, "success", null, null, "ci", "created", "updated", null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("nodeId");
    }

    @Test
    void constructor_shouldThrow_whenStateNull() {
        final Throwable thrown = catchThrowable(() ->
                new GitHubCommitStatus("url", null, 1L, "node", null, null, null, "ci", "created", "updated", null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("state");
    }

    @Test
    void constructor_shouldThrow_whenContextNull() {
        final Throwable thrown = catchThrowable(() -> new GitHubCommitStatus(
                "url", null, 1L, "node", "success", null, null, null, "created", "updated", null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("context");
    }

    @Test
    void constructor_shouldThrow_whenCreatedAtNull() {
        final Throwable thrown = catchThrowable(() ->
                new GitHubCommitStatus("url", null, 1L, "node", "success", null, null, "ci", null, "updated", null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("createdAt");
    }

    @Test
    void constructor_shouldThrow_whenUpdatedAtNull() {
        final Throwable thrown = catchThrowable(() ->
                new GitHubCommitStatus("url", null, 1L, "node", "success", null, null, "ci", "created", null, null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("updatedAt");
    }
}
