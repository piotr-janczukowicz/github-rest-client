package io.github.piotr_janczukowicz.github.api.organizations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.time.Instant;
import java.util.Map;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubApiInsightsTimeWindowFilterTest {

    private static final Instant MIN_TIMESTAMP = Instant.parse("2024-09-11T15:00:00Z");
    private static final String MIN_TIMESTAMP_TEXT = "2024-09-11T15:00:00Z";
    private static final Instant MAX_TIMESTAMP = Instant.parse("2024-09-18T15:00:00Z");
    private static final String MAX_TIMESTAMP_TEXT = "2024-09-18T15:00:00Z";

    @Test
    void of_shouldSetOnlyMinTimestamp_whenCalled() {
        final GitHubApiInsightsTimeWindowFilter filter = GitHubApiInsightsTimeWindowFilter.of(MIN_TIMESTAMP);

        assertThat(filter.minTimestamp()).isEqualTo(MIN_TIMESTAMP);
        assertThat(filter.maxTimestamp()).isNull();
    }

    @Test
    void constructor_shouldThrow_whenMinTimestampNull() {
        final Throwable thrown = catchThrowable(() -> new GitHubApiInsightsTimeWindowFilter(null, MAX_TIMESTAMP));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("minTimestamp");
    }

    @Test
    void toQueryParameters_shouldIncludeMinTimestampOnly_whenMaxTimestampNull() {
        final Map<String, String> query =
                GitHubApiInsightsTimeWindowFilter.of(MIN_TIMESTAMP).toQueryParameters();

        assertThat(query).containsExactly(Map.entry("min_timestamp", MIN_TIMESTAMP_TEXT));
    }

    @Test
    void toQueryParameters_shouldIncludeMaxTimestamp_whenPresent() {
        final GitHubApiInsightsTimeWindowFilter filter = GitHubApiInsightsTimeWindowFilterBuilder.builder()
                .minTimestamp(MIN_TIMESTAMP)
                .maxTimestamp(MAX_TIMESTAMP)
                .build();

        final Map<String, String> query = filter.toQueryParameters();

        assertThat(query)
                .containsEntry("min_timestamp", MIN_TIMESTAMP_TEXT)
                .containsEntry("max_timestamp", MAX_TIMESTAMP_TEXT);
    }
}
