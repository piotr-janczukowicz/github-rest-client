package io.github.piotr_janczukowicz.github.api.organizations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

import io.github.piotr_janczukowicz.github.api.GitHubDirection;
import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubApiInsightsUserStatsFilterTest {

    private static final Instant MIN_TIMESTAMP = Instant.parse("2024-09-11T15:00:00Z");
    private static final String MIN_TIMESTAMP_TEXT = "2024-09-11T15:00:00Z";
    private static final Instant MAX_TIMESTAMP = Instant.parse("2024-09-18T15:00:00Z");
    private static final String MAX_TIMESTAMP_TEXT = "2024-09-18T15:00:00Z";

    @Test
    void of_shouldSetOnlyMinTimestamp_whenCalled() {
        final GitHubApiInsightsUserStatsFilter filter = GitHubApiInsightsUserStatsFilter.of(MIN_TIMESTAMP);

        assertSoftly(softly -> {
            softly.assertThat(filter.minTimestamp()).isEqualTo(MIN_TIMESTAMP);
            softly.assertThat(filter.maxTimestamp()).isNull();
            softly.assertThat(filter.direction()).isNull();
            softly.assertThat(filter.sort()).isNull();
            softly.assertThat(filter.actorNameSubstring()).isNull();
        });
    }

    @Test
    void constructor_shouldThrow_whenMinTimestampNull() {
        final Throwable thrown =
                catchThrowable(() -> new GitHubApiInsightsUserStatsFilter(null, null, null, null, null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("minTimestamp");
    }

    @Test
    void toQueryParameters_shouldIncludeMinTimestampOnly_whenOptionalFieldsNull() {
        final Map<String, String> query =
                GitHubApiInsightsUserStatsFilter.of(MIN_TIMESTAMP).toQueryParameters();

        assertThat(query).containsExactly(Map.entry("min_timestamp", MIN_TIMESTAMP_TEXT));
    }

    @Test
    void toQueryParameters_shouldOmitNullsAndJoinSort_whenSomeFieldsSet() {
        final GitHubApiInsightsUserStatsFilter filter = GitHubApiInsightsUserStatsFilterBuilder.builder()
                .minTimestamp(MIN_TIMESTAMP)
                .maxTimestamp(MAX_TIMESTAMP)
                .direction(GitHubDirection.DESC)
                .sort(List.of(GitHubApiInsightsSort.LAST_REQUEST_TIMESTAMP))
                .actorNameSubstring("GitHub")
                .build();

        final Map<String, String> query = filter.toQueryParameters();

        assertThat(query)
                .containsEntry("min_timestamp", MIN_TIMESTAMP_TEXT)
                .containsEntry("max_timestamp", MAX_TIMESTAMP_TEXT)
                .containsEntry("direction", "desc")
                .containsEntry("sort", "last_request_timestamp")
                .containsEntry("actor_name_substring", "GitHub");
    }

    @Test
    void constructor_shouldOmitSortQuery_whenEmptyList() {
        final GitHubApiInsightsUserStatsFilter filter =
                new GitHubApiInsightsUserStatsFilter(MIN_TIMESTAMP, null, null, List.of(), null);

        assertSoftly(softly -> {
            softly.assertThat(filter.sort()).isEmpty();
            softly.assertThat(filter.toQueryParameters()).doesNotContainKey("sort");
        });
    }

    @Test
    void constructor_shouldCopySort_whenPresent() {
        final List<GitHubApiInsightsSort> sort = new ArrayList<>(List.of(GitHubApiInsightsSort.LAST_REQUEST_TIMESTAMP));

        final GitHubApiInsightsUserStatsFilter filter =
                new GitHubApiInsightsUserStatsFilter(MIN_TIMESTAMP, null, null, sort, null);
        sort.add(GitHubApiInsightsSort.TOTAL_REQUEST_COUNT);

        assertThat(filter.sort()).containsExactly(GitHubApiInsightsSort.LAST_REQUEST_TIMESTAMP);
    }
}
