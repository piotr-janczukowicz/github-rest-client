package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubCreateGlobalWebhookPayloadTest {

    @Test
    void constructor_shouldThrow_whenNameNull() {
        final GitHubCreateGlobalWebhookPayloadBuilder builder = GitHubCreateGlobalWebhookPayloadBuilder.builder(
                        EnterpriseAdminCatalogFixtures.createGlobalWebhookPayload())
                .name(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("name");
    }

    @Test
    void constructor_shouldThrow_whenConfigNull() {
        final GitHubCreateGlobalWebhookPayloadBuilder builder = GitHubCreateGlobalWebhookPayloadBuilder.builder(
                        EnterpriseAdminCatalogFixtures.createGlobalWebhookPayload())
                .config(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("config");
    }

    @Test
    void constructor_shouldAcceptNull_whenEventsNull() {
        final GitHubCreateGlobalWebhookPayload value = GitHubCreateGlobalWebhookPayloadBuilder.builder(
                        EnterpriseAdminCatalogFixtures.createGlobalWebhookPayload())
                .events(null)
                .build();

        assertThat(value.events()).isNull();
    }

    @Test
    void constructor_shouldCopy_whenEventsGiven() {
        final List<String> source = new ArrayList<>(List.of("first"));
        final GitHubCreateGlobalWebhookPayload value = GitHubCreateGlobalWebhookPayloadBuilder.builder(
                        EnterpriseAdminCatalogFixtures.createGlobalWebhookPayload())
                .events(source)
                .build();

        source.add("second");

        assertThat(value.events()).containsExactly("first");
    }
}
