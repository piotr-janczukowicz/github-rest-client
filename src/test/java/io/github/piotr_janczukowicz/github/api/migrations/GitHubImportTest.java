package io.github.piotr_janczukowicz.github.api.migrations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.List;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubImportTest {

    @Test
    void constructor_shouldThrow_whenVcsUrlNull() {
        final Throwable thrown = catchThrowable(() -> anImport(null, GitHubImportStatus.COMPLETE, urls()));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("vcsUrl");
    }

    @Test
    void constructor_shouldThrow_whenStatusNull() {
        final Throwable thrown = catchThrowable(() -> anImport("http://svn.example.com/svn/myproject", null, urls()));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("status");
    }

    @Test
    void constructor_shouldThrow_whenUrlNull() {
        final Throwable thrown = catchThrowable(
                () -> anImport("http://svn.example.com/svn/myproject", GitHubImportStatus.COMPLETE, nullUrls()));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("url");
    }

    @Test
    void constructor_shouldThrow_whenHtmlUrlNull() {
        final Throwable thrown = catchThrowable(() -> anImport(
                "http://svn.example.com/svn/myproject",
                GitHubImportStatus.COMPLETE,
                new Urls(
                        "https://api.github.com/repos/octocat/socm/import",
                        null,
                        "https://api.github.com/repos/octocat/socm/import/authors",
                        "https://api.github.com/repos/octocat/socm")));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("htmlUrl");
    }

    @Test
    void constructor_shouldThrow_whenAuthorsUrlNull() {
        final Throwable thrown = catchThrowable(() -> anImport(
                "http://svn.example.com/svn/myproject",
                GitHubImportStatus.COMPLETE,
                new Urls(
                        "https://api.github.com/repos/octocat/socm/import",
                        "https://import.github.com/octocat/socm/import",
                        null,
                        "https://api.github.com/repos/octocat/socm")));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("authorsUrl");
    }

    @Test
    void constructor_shouldThrow_whenRepositoryUrlNull() {
        final Throwable thrown = catchThrowable(() -> anImport(
                "http://svn.example.com/svn/myproject",
                GitHubImportStatus.COMPLETE,
                new Urls(
                        "https://api.github.com/repos/octocat/socm/import",
                        "https://import.github.com/octocat/socm/import",
                        "https://api.github.com/repos/octocat/socm/import/authors",
                        null)));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("repositoryUrl");
    }

    @Test
    void constructor_shouldAllowNullProjectChoices_whenNotProvided() {
        final GitHubImport importStatus =
                anImport("http://svn.example.com/svn/myproject", GitHubImportStatus.COMPLETE, urls());

        assertThat(importStatus.projectChoices()).isNull();
    }

    private static GitHubImport anImport(final String vcsUrl, final GitHubImportStatus status, final Urls urls) {
        return anImport(vcsUrl, status, urls, null);
    }

    private static GitHubImport anImport(
            final String vcsUrl,
            final GitHubImportStatus status,
            final Urls urls,
            final List<GitHubImport.ProjectChoice> projectChoices) {
        return new GitHubImport(
                "subversion",
                true,
                vcsUrl,
                null,
                null,
                status,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                projectChoices,
                null,
                null,
                urls.url(),
                urls.htmlUrl(),
                urls.authorsUrl(),
                urls.repositoryUrl(),
                null);
    }

    private static Urls urls() {
        return new Urls(
                "https://api.github.com/repos/octocat/socm/import",
                "https://import.github.com/octocat/socm/import",
                "https://api.github.com/repos/octocat/socm/import/authors",
                "https://api.github.com/repos/octocat/socm");
    }

    private static Urls nullUrls() {
        return new Urls(
                null, "https://import.github.com/octocat/socm/import", urls().authorsUrl(), urls().repositoryUrl());
    }

    private record Urls(String url, String htmlUrl, String authorsUrl, String repositoryUrl) {}
}
