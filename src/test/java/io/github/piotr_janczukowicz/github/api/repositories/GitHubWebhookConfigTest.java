package io.github.piotr_janczukowicz.github.api.repositories;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubWebhookConfigTest {

    @Test
    void toString_shouldRedactSecret_whenSecretPresent() {
        final GitHubWebhookConfig config = new GitHubWebhookConfig(null, "json", "s3cr3t-value", null);

        final String text = config.toString();

        assertSoftly(softly -> {
            softly.assertThat(text).doesNotContain("s3cr3t-value");
            softly.assertThat(text).contains("secret=<redacted>", "contentType=json");
        });
    }

    @Test
    void toString_shouldRedactSecret_whenPayloadSecretPresent() {
        final GitHubUpdateWebhookConfigForRepoPayload payload = GitHubUpdateWebhookConfigForRepoPayloadBuilder.builder()
                .secret("s3cr3t-value")
                .build();

        assertThat(payload.toString()).doesNotContain("s3cr3t-value").contains("secret=<redacted>");
    }
}
