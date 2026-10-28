package io.github.piotr_janczukowicz.github.api.migrations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubStartImportPayloadTest {

    @Test
    void constructor_shouldThrow_whenVcsUrlNull() {
        final Throwable thrown = catchThrowable(() -> new GitHubStartImportPayload(null, null, null, null, null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("vcsUrl");
    }

    @Test
    void builder_shouldCreatePayload_whenCalled() {
        final GitHubStartImportPayload payload = GitHubStartImportPayloadBuilder.builder()
                .vcsUrl("http://svn.example.com/svn/myproject")
                .vcs(GitHubImportVcs.SUBVERSION)
                .build();

        assertThat(payload.vcsUrl()).isEqualTo("http://svn.example.com/svn/myproject");
    }

    @Test
    void toString_shouldRedactSecret_whenPopulated() {
        final GitHubStartImportPayload subject = GitHubStartImportPayloadBuilder.builder(
                        MigrationsCatalogFixtures.startImportPayload())
                .vcsPassword("hunter2-password")
                .build();

        final String text = subject.toString();

        assertSoftly(softly -> {
            softly.assertThat(text).contains("<redacted>");
            softly.assertThat(text).doesNotContain("hunter2-password");
        });
    }
}
