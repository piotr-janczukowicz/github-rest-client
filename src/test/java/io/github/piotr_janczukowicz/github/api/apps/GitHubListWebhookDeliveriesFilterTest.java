package io.github.piotr_janczukowicz.github.api.apps;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.Map;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubListWebhookDeliveriesFilterTest {

    @Test
    void empty_shouldReturnNoQueryParameters_whenCalled() {
        final GitHubListWebhookDeliveriesFilter filter = GitHubListWebhookDeliveriesFilter.empty();

        final Map<String, String> query = filter.toQueryParameters();

        assertThat(query).isEmpty();
    }

    @Test
    void toQueryParameters_shouldIncludeSetFields_whenFilterFilled() {
        final GitHubListWebhookDeliveriesFilter filter = AppsCatalogFixtures.listWebhookDeliveriesFilter();

        final Map<String, String> query = filter.toQueryParameters();

        assertThat(query).containsEntry("status", "success");
    }
}
