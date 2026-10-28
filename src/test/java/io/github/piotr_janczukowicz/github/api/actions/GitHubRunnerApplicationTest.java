package io.github.piotr_janczukowicz.github.api.actions;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubRunnerApplicationTest {

    @Test
    void constructor_shouldThrowNullPointerException_whenOsNull() {
        final String architecture = "architecture-sample";
        final String downloadUrl = "https://api.github.com/example";
        final String filename = "filename-sample";
        final String tempDownloadToken = "tempDownloadToken-sample";
        final String sha256Checksum = "sha256Checksum-sample";

        final Throwable thrown = catchThrowable(() -> new GitHubRunnerApplication(
                null, architecture, downloadUrl, filename, tempDownloadToken, sha256Checksum));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("os");
    }

    @Test
    void constructor_shouldThrowNullPointerException_whenArchitectureNull() {
        final String os = "os-sample";
        final String downloadUrl = "https://api.github.com/example";
        final String filename = "filename-sample";
        final String tempDownloadToken = "tempDownloadToken-sample";
        final String sha256Checksum = "sha256Checksum-sample";

        final Throwable thrown = catchThrowable(
                () -> new GitHubRunnerApplication(os, null, downloadUrl, filename, tempDownloadToken, sha256Checksum));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("architecture");
    }

    @Test
    void constructor_shouldThrowNullPointerException_whenDownloadUrlNull() {
        final String os = "os-sample";
        final String architecture = "architecture-sample";
        final String filename = "filename-sample";
        final String tempDownloadToken = "tempDownloadToken-sample";
        final String sha256Checksum = "sha256Checksum-sample";

        final Throwable thrown = catchThrowable(
                () -> new GitHubRunnerApplication(os, architecture, null, filename, tempDownloadToken, sha256Checksum));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("downloadUrl");
    }

    @Test
    void constructor_shouldThrowNullPointerException_whenFilenameNull() {
        final String os = "os-sample";
        final String architecture = "architecture-sample";
        final String downloadUrl = "https://api.github.com/example";
        final String tempDownloadToken = "tempDownloadToken-sample";
        final String sha256Checksum = "sha256Checksum-sample";

        final Throwable thrown = catchThrowable(() ->
                new GitHubRunnerApplication(os, architecture, downloadUrl, null, tempDownloadToken, sha256Checksum));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("filename");
    }

    @Test
    void toString_shouldHideSecrets() {
        final GitHubRunnerApplication value = ActionsCatalogFixtures.runnerApplication();

        final String text = value.toString();

        assertSoftly(softly -> {
            softly.assertThat(text).contains("tempDownloadToken=<redacted>");
            softly.assertThat(text).doesNotContain(value.tempDownloadToken());
        });
    }
}
