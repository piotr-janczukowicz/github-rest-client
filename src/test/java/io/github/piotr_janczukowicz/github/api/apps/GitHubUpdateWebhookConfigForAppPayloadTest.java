package io.github.piotr_janczukowicz.github.api.apps;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubUpdateWebhookConfigForAppPayloadTest {

    @Test
    void toString_shouldRedactSecret_whenCalled() {
        final GitHubUpdateWebhookConfigForAppPayload payload = AppsCatalogFixtures.updateWebhookConfigForAppPayload();

        final String text = payload.toString();

        assertThat(text).contains("secret=<redacted>").doesNotContain("test-secret");
    }
}
