package io.github.piotr_janczukowicz.github.api.repositories;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.List;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubCreateWebhookPayloadTest {

    @Test
    void constructor_shouldCopyEvents_whenPresent() {
        final GitHubCreateWebhookPayload payload = GitHubCreateWebhookPayloadBuilder.builder()
                .events(List.of("push"))
                .build();

        assertThat(payload.events()).containsExactly("push");
    }

    @Test
    void constructor_shouldLeaveEventsNull_whenNull() {
        final GitHubCreateWebhookPayload payload = GitHubCreateWebhookPayloadBuilder.builder(
                        RepositoriesCatalogFixtures.createWebhookPayload())
                .events(null)
                .build();

        assertThat(payload.events()).isNull();
    }
}
