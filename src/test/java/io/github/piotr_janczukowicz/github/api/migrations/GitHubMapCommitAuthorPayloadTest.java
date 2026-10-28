package io.github.piotr_janczukowicz.github.api.migrations;

import static org.assertj.core.api.SoftAssertions.assertSoftly;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubMapCommitAuthorPayloadTest {

    @Test
    void builder_shouldCreatePayload_whenCalled() {
        final GitHubMapCommitAuthorPayload payload = GitHubMapCommitAuthorPayloadBuilder.builder()
                .email("hubot@github.com")
                .name("Hubot")
                .build();
        final GitHubMapCommitAuthorPayload same = GitHubMapCommitAuthorPayloadBuilder.builder()
                .email("hubot@github.com")
                .name("Hubot")
                .build();

        assertSoftly(softly -> {
            softly.assertThat(payload.email()).isEqualTo("hubot@github.com");
            softly.assertThat(payload).isEqualTo(same);
            softly.assertThat(payload.hashCode()).isEqualTo(same.hashCode());
            softly.assertThat(payload.toString()).contains("Hubot");
        });
    }
}
