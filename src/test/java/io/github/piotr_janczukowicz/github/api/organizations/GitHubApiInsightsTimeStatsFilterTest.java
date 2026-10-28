package io.github.piotr_janczukowicz.github.api.organizations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.time.Instant;
import java.util.Map;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubApiInsightsTimeStatsFilterTest {

    private static final Instant MIN_TIMESTAMP = Instant.parse("2024-09-11T15:00:00Z");
    private static final String MIN_TIMESTAMP_TEXT = "2024-09-11T15:00:00Z";
    private static final Instant MAX_TIMESTAMP = Instant.parse("2024-09-18T15:00:00Z");
    private static final String MAX_TIMESTAMP_TEXT = "2024-09-18T15:00:00Z";
    private static final String TIMESTAMP_INCREMENT = "day";

    @Test
    void of_shouldSetMinTimestampAndIncrement_whenCalled() {
        final GitHubApiInsightsTimeStatsFilter filter =
                GitHubApiInsightsTimeStatsFilter.of(MIN_TIMESTAMP, TIMESTAMP_INCREMENT);

        assertThat(filter.minTimestamp()).isEqualTo(MIN_TIMESTAMP);
        assertThat(filter.maxTimestamp()).isNull();
        assertThat(filter.timestampIncrement()).isEqualTo(TIMESTAMP_INCREMENT);
    }

    @Test
    void constructor_shouldThrow_whenMinTimestampNull() {
        final Throwable thrown =
                catchThrowable(() -> new GitHubApiInsightsTimeStatsFilter(null, MAX_TIMESTAMP, TIMESTAMP_INCREMENT));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("minTimestamp");
    }

    @Test
    void constructor_shouldThrow_whenTimestampIncrementBlank() {
        final Throwable thrown = catchThrowable(() -> new GitHubApiInsightsTimeStatsFilter(MIN_TIMESTAMP, null, "  "));

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("timestampIncrement");
    }

    @Test
    void toQueryParameters_shouldIncludeIncrement_whenMaxTimestampNull() {
        final Map<String, String> query = GitHubApiInsightsTimeStatsFilter.of(MIN_TIMESTAMP, TIMESTAMP_INCREMENT)
                .toQueryParameters();

        assertThat(query)
                .containsExactly(
                        Map.entry("min_timestamp", MIN_TIMESTAMP_TEXT),
                        Map.entry("timestamp_increment", TIMESTAMP_INCREMENT));
    }

    @Test
    void toQueryParameters_shouldIncludeMaxTimestamp_whenPresent() {
        final GitHubApiInsightsTimeStatsFilter filter = GitHubApiInsightsTimeStatsFilterBuilder.builder()
                .minTimestamp(MIN_TIMESTAMP)
                .maxTimestamp(MAX_TIMESTAMP)
                .timestampIncrement(TIMESTAMP_INCREMENT)
                .build();

        final Map<String, String> query = filter.toQueryParameters();

        assertThat(query)
                .containsEntry("min_timestamp", MIN_TIMESTAMP_TEXT)
                .containsEntry("max_timestamp", MAX_TIMESTAMP_TEXT)
                .containsEntry("timestamp_increment", TIMESTAMP_INCREMENT);
    }
}
