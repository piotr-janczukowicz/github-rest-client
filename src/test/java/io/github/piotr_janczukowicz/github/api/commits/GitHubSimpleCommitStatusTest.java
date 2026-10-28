package io.github.piotr_janczukowicz.github.api.commits;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.time.Instant;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubSimpleCommitStatusTest {

    @Test
    void constructor_shouldThrow_whenNodeIdNull() {
        final Throwable thrown = catchThrowable(() -> new GitHubSimpleCommitStatus(
                null, 1L, null, "success", "ci", null, null, null, uri(), instant(), instant()));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("nodeId");
    }

    @Test
    void constructor_shouldThrow_whenStateNull() {
        final Throwable thrown = catchThrowable(() -> new GitHubSimpleCommitStatus(
                null, 1L, "node", null, "ci", null, null, null, uri(), instant(), instant()));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("state");
    }

    @Test
    void constructor_shouldThrow_whenContextNull() {
        final Throwable thrown = catchThrowable(() -> new GitHubSimpleCommitStatus(
                null, 1L, "node", "success", null, null, null, null, uri(), instant(), instant()));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("context");
    }

    @Test
    void constructor_shouldThrow_whenUrlNull() {
        final Throwable thrown = catchThrowable(() -> new GitHubSimpleCommitStatus(
                null, 1L, "node", "success", "ci", null, null, null, null, instant(), instant()));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("url");
    }

    @Test
    void constructor_shouldThrow_whenCreatedAtNull() {
        final Throwable thrown = catchThrowable(() -> new GitHubSimpleCommitStatus(
                null, 1L, "node", "success", "ci", null, null, null, uri(), null, instant()));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("createdAt");
    }

    @Test
    void constructor_shouldThrow_whenUpdatedAtNull() {
        final Throwable thrown = catchThrowable(() -> new GitHubSimpleCommitStatus(
                null, 1L, "node", "success", "ci", null, null, null, uri(), instant(), null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("updatedAt");
    }

    private static String uri() {
        return "https://example.com";
    }

    private static Instant instant() {
        return Instant.parse("2011-04-14T16:00:49Z");
    }
}
