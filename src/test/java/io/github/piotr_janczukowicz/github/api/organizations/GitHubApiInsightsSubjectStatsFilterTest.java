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
class GitHubApiInsightsSubjectStatsFilterTest {

    private static final Instant MIN_TIMESTAMP = Instant.parse("2024-09-11T15:00:00Z");
    private static final String MIN_TIMESTAMP_TEXT = "2024-09-11T15:00:00Z";
    private static final Instant MAX_TIMESTAMP = Instant.parse("2024-09-18T15:00:00Z");
    private static final String MAX_TIMESTAMP_TEXT = "2024-09-18T15:00:00Z";

    @Test
    void of_shouldSetOnlyMinTimestamp_whenCalled() {
        final GitHubApiInsightsSubjectStatsFilter filter = GitHubApiInsightsSubjectStatsFilter.of(MIN_TIMESTAMP);

        assertSoftly(softly -> {
            softly.assertThat(filter.minTimestamp()).isEqualTo(MIN_TIMESTAMP);
            softly.assertThat(filter.maxTimestamp()).isNull();
            softly.assertThat(filter.direction()).isNull();
            softly.assertThat(filter.sort()).isNull();
            softly.assertThat(filter.subjectNameSubstring()).isNull();
        });
    }

    @Test
    void constructor_shouldThrow_whenMinTimestampNull() {
        final Throwable thrown =
                catchThrowable(() -> new GitHubApiInsightsSubjectStatsFilter(null, null, null, null, null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("minTimestamp");
    }

    @Test
    void toQueryParameters_shouldIncludeMinTimestampOnly_whenOptionalFieldsNull() {
        final Map<String, String> query =
                GitHubApiInsightsSubjectStatsFilter.of(MIN_TIMESTAMP).toQueryParameters();

        assertThat(query).containsExactly(Map.entry("min_timestamp", MIN_TIMESTAMP_TEXT));
    }

    @Test
    void toQueryParameters_shouldOmitNullsAndJoinSort_whenSomeFieldsSet() {
        final GitHubApiInsightsSubjectStatsFilter filter = GitHubApiInsightsSubjectStatsFilterBuilder.builder()
                .minTimestamp(MIN_TIMESTAMP)
                .maxTimestamp(MAX_TIMESTAMP)
                .direction(GitHubDirection.ASC)
                .sort(List.of(GitHubApiInsightsSort.SUBJECT_NAME, GitHubApiInsightsSort.TOTAL_REQUEST_COUNT))
                .subjectNameSubstring("Actions")
                .build();

        final Map<String, String> query = filter.toQueryParameters();

        assertThat(query)
                .containsEntry("min_timestamp", MIN_TIMESTAMP_TEXT)
                .containsEntry("max_timestamp", MAX_TIMESTAMP_TEXT)
                .containsEntry("direction", "asc")
                .containsEntry("sort", "subject_name,total_request_count")
                .containsEntry("subject_name_substring", "Actions");
    }

    @Test
    void constructor_shouldOmitSortQuery_whenEmptyList() {
        final GitHubApiInsightsSubjectStatsFilter filter =
                new GitHubApiInsightsSubjectStatsFilter(MIN_TIMESTAMP, null, null, List.of(), null);

        assertSoftly(softly -> {
            softly.assertThat(filter.sort()).isEmpty();
            softly.assertThat(filter.toQueryParameters()).doesNotContainKey("sort");
        });
    }

    @Test
    void constructor_shouldCopySort_whenPresent() {
        final List<GitHubApiInsightsSort> sort = new ArrayList<>(List.of(GitHubApiInsightsSort.SUBJECT_NAME));

        final GitHubApiInsightsSubjectStatsFilter filter =
                new GitHubApiInsightsSubjectStatsFilter(MIN_TIMESTAMP, null, null, sort, null);
        sort.add(GitHubApiInsightsSort.TOTAL_REQUEST_COUNT);

        assertThat(filter.sort()).containsExactly(GitHubApiInsightsSort.SUBJECT_NAME);
    }
}
