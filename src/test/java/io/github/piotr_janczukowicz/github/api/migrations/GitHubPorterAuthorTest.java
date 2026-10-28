package io.github.piotr_janczukowicz.github.api.migrations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubPorterAuthorTest {

    @Test
    void constructor_shouldThrow_whenRemoteIdNull() {
        final Throwable thrown =
                catchThrowable(() -> author(null, "nobody", "hubot@github.com", "Hubot", url(), importUrl()));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("remoteId");
    }

    @Test
    void constructor_shouldThrow_whenRemoteNameNull() {
        final Throwable thrown =
                catchThrowable(() -> author("remote", null, "hubot@github.com", "Hubot", url(), importUrl()));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("remoteName");
    }

    @Test
    void constructor_shouldThrow_whenEmailNull() {
        final Throwable thrown = catchThrowable(() -> author("remote", "nobody", null, "Hubot", url(), importUrl()));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("email");
    }

    @Test
    void constructor_shouldThrow_whenNameNull() {
        final Throwable thrown =
                catchThrowable(() -> author("remote", "nobody", "hubot@github.com", null, url(), importUrl()));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("name");
    }

    @Test
    void constructor_shouldThrow_whenUrlNull() {
        final Throwable thrown =
                catchThrowable(() -> author("remote", "nobody", "hubot@github.com", "Hubot", null, importUrl()));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("url");
    }

    @Test
    void constructor_shouldThrow_whenImportUrlNull() {
        final Throwable thrown =
                catchThrowable(() -> author("remote", "nobody", "hubot@github.com", "Hubot", url(), null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("importUrl");
    }

    private static GitHubPorterAuthor author(
            final String remoteId,
            final String remoteName,
            final String email,
            final String name,
            final String url,
            final String importUrl) {
        return new GitHubPorterAuthor(1, remoteId, remoteName, email, name, url, importUrl);
    }

    private static String url() {
        return "https://api.github.com/repos/octocat/socm/import/authors/1";
    }

    private static String importUrl() {
        return "https://api.github.com/repos/octocat/socm/import";
    }
}
