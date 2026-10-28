package io.github.piotr_janczukowicz.github.api.organizations;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubUpdateOrgWebhookPayloadTest {

    @Test
    void constructor_shouldCopyEvents_whenPresent() {
        final List<String> events = new ArrayList<>(List.of("push"));

        final GitHubUpdateOrgWebhookPayload payload = new GitHubUpdateOrgWebhookPayload(null, events, null, null);
        events.clear();

        assertThat(payload.events()).containsExactly("push");
    }

    @Test
    void constructor_shouldLeaveEventsNull_whenNull() {
        final GitHubUpdateOrgWebhookPayload payload = GitHubUpdateOrgWebhookPayloadBuilder.builder(
                        OrganizationsCatalogFixtures.updateOrgWebhookPayload())
                .events(null)
                .build();

        assertThat(payload.events()).isNull();
    }
}
