package io.github.piotr_janczukowicz.github.api.repositories;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.Map;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubListWebhookDeliveriesFilterTest {

    @Test
    void toQueryParameters_shouldIncludeStatus_whenProvided() {
        final GitHubListWebhookDeliveriesFilter filter = GitHubListWebhookDeliveriesFilterBuilder.builder()
                .status(GitHubWebhookDeliveryStatus.FAILURE)
                .build();

        assertThat(filter.toQueryParameters()).containsEntry("status", "failure");
    }

    @Test
    void empty_shouldReturnEmptyQuery_whenCalled() {
        final GitHubListWebhookDeliveriesFilter filter = GitHubListWebhookDeliveriesFilter.empty();

        final Map<String, String> query = filter.toQueryParameters();

        assertThat(query).isEmpty();
    }
}
