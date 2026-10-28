package io.github.piotr_janczukowicz.github.api.organizations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubCreateOrgWebhookPayloadTest {

    @Test
    void constructor_shouldThrow_whenNameNull() {
        final GitHubCreateOrgWebhookConfig config = OrganizationsCatalogFixtures.createOrgWebhookConfig();

        final Throwable thrown = catchThrowable(() -> new GitHubCreateOrgWebhookPayload(null, config, null, null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("name");
    }

    @Test
    void constructor_shouldThrow_whenConfigNull() {
        final Throwable thrown = catchThrowable(() -> new GitHubCreateOrgWebhookPayload("web", null, null, null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("config");
    }

    @Test
    void constructor_shouldCopyEvents_whenPresent() {
        final List<String> events = new ArrayList<>(List.of("push"));
        final GitHubCreateOrgWebhookConfig config = OrganizationsCatalogFixtures.createOrgWebhookConfig();

        final GitHubCreateOrgWebhookPayload payload = new GitHubCreateOrgWebhookPayload("web", config, events, true);
        events.clear();

        assertThat(payload.events()).containsExactly("push");
    }

    @Test
    void constructor_shouldLeaveEventsNull_whenNull() {
        final GitHubCreateOrgWebhookPayload payload = GitHubCreateOrgWebhookPayloadBuilder.builder(
                        OrganizationsCatalogFixtures.createOrgWebhookPayload())
                .events(null)
                .build();

        assertThat(payload.events()).isNull();
    }
}
