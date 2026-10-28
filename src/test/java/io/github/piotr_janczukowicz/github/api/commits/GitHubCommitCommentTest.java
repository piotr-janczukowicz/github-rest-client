package io.github.piotr_janczukowicz.github.api.commits;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.github.api.search.GitHubAuthorAssociation;
import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.time.Instant;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubCommitCommentTest {

    @Test
    void constructor_shouldThrow_whenHtmlUrlNull() {
        final Throwable thrown =
                catchThrowable(() -> comment(null, uri(), "node", "body", "sha", instant(), instant()));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("htmlUrl");
    }

    @Test
    void constructor_shouldThrow_whenUrlNull() {
        final Throwable thrown =
                catchThrowable(() -> comment(uri(), null, "node", "body", "sha", instant(), instant()));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("url");
    }

    @Test
    void constructor_shouldThrow_whenNodeIdNull() {
        final Throwable thrown = catchThrowable(() -> comment(uri(), uri(), null, "body", "sha", instant(), instant()));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("nodeId");
    }

    @Test
    void constructor_shouldThrow_whenBodyNull() {
        final Throwable thrown = catchThrowable(() -> comment(uri(), uri(), "node", null, "sha", instant(), instant()));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("body");
    }

    @Test
    void constructor_shouldThrow_whenCommitIdNull() {
        final Throwable thrown =
                catchThrowable(() -> comment(uri(), uri(), "node", "body", null, instant(), instant()));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("commitId");
    }

    @Test
    void constructor_shouldThrow_whenCreatedAtNull() {
        final Throwable thrown = catchThrowable(() -> comment(uri(), uri(), "node", "body", "sha", null, instant()));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("createdAt");
    }

    @Test
    void constructor_shouldThrow_whenUpdatedAtNull() {
        final Throwable thrown = catchThrowable(() -> comment(uri(), uri(), "node", "body", "sha", instant(), null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("updatedAt");
    }

    @Test
    void constructor_shouldThrow_whenAuthorAssociationNull() {
        final Throwable thrown = catchThrowable(() -> new GitHubCommitComment(
                uri(), uri(), 1L, "node", "body", null, null, null, "sha", null, instant(), instant(), null, null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("authorAssociation");
    }

    private static GitHubCommitComment comment(
            final String htmlUrl,
            final String url,
            final String nodeId,
            final String body,
            final String commitId,
            final Instant createdAt,
            final Instant updatedAt) {
        return new GitHubCommitComment(
                htmlUrl,
                url,
                1L,
                nodeId,
                body,
                null,
                null,
                null,
                commitId,
                null,
                createdAt,
                updatedAt,
                GitHubAuthorAssociation.NONE,
                null);
    }

    private static String uri() {
        return "https://example.com";
    }

    private static Instant instant() {
        return Instant.parse("2011-04-14T16:00:49Z");
    }
}
