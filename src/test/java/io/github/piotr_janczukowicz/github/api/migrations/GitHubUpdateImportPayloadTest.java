package io.github.piotr_janczukowicz.github.api.migrations;

import static org.assertj.core.api.SoftAssertions.assertSoftly;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubUpdateImportPayloadTest {

    @Test
    void builder_shouldCreatePayload_whenCalled() {
        final GitHubUpdateImportPayload payload = GitHubUpdateImportPayloadBuilder.builder()
                .vcsUsername("octocat")
                .vcsPassword("secret")
                .build();
        final GitHubUpdateImportPayload same = GitHubUpdateImportPayloadBuilder.builder()
                .vcsUsername("octocat")
                .vcsPassword("secret")
                .build();

        assertSoftly(softly -> {
            softly.assertThat(payload.vcsUsername()).isEqualTo("octocat");
            softly.assertThat(payload.tfvcProject()).isNull();
            softly.assertThat(payload).isEqualTo(same);
            softly.assertThat(payload.hashCode()).isEqualTo(same.hashCode());
            softly.assertThat(payload.toString()).contains("octocat");
        });
    }

    @Test
    void toString_shouldRedactSecret_whenPopulated() {
        final GitHubUpdateImportPayload subject = MigrationsCatalogFixtures.updateImportPayload();
        final String password = subject.vcsPassword();

        final String text = subject.toString();

        assertSoftly(softly -> {
            softly.assertThat(text).contains("<redacted>");
            softly.assertThat(text).doesNotContain(password);
        });
    }
}
