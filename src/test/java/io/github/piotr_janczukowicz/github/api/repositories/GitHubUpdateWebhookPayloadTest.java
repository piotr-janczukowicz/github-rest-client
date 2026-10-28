package io.github.piotr_janczukowicz.github.api.repositories;

import static org.assertj.core.api.SoftAssertions.assertSoftly;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.List;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubUpdateWebhookPayloadTest {

    @Test
    void constructor_shouldCopyLists_whenPresent() {
        final GitHubUpdateWebhookPayload payload = GitHubUpdateWebhookPayloadBuilder.builder()
                .events(List.of("push"))
                .addEvents(List.of("issues"))
                .removeEvents(List.of("pull_request"))
                .build();

        assertSoftly(softly -> {
            softly.assertThat(payload.events()).containsExactly("push");
            softly.assertThat(payload.addEvents()).containsExactly("issues");
            softly.assertThat(payload.removeEvents()).containsExactly("pull_request");
        });
    }
}
