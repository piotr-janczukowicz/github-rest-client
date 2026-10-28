package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubUpdateGlobalWebhookPayloadTest {

    @Test
    void constructor_shouldAcceptNull_whenEventsNull() {
        final GitHubUpdateGlobalWebhookPayload value = GitHubUpdateGlobalWebhookPayloadBuilder.builder(
                        EnterpriseAdminCatalogFixtures.updateGlobalWebhookPayload())
                .events(null)
                .build();

        assertThat(value.events()).isNull();
    }

    @Test
    void constructor_shouldCopy_whenEventsGiven() {
        final List<String> source = new ArrayList<>(List.of("first"));
        final GitHubUpdateGlobalWebhookPayload value = GitHubUpdateGlobalWebhookPayloadBuilder.builder(
                        EnterpriseAdminCatalogFixtures.updateGlobalWebhookPayload())
                .events(source)
                .build();

        source.add("second");

        assertThat(value.events()).containsExactly("first");
    }
}
