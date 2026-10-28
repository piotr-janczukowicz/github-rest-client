package io.github.piotr_janczukowicz.github.api.repositories;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.Map;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubListActivitiesFilterTest {

    @Test
    void empty_shouldReturnAllNulls_whenCalled() {
        final GitHubListActivitiesFilter filter = GitHubListActivitiesFilter.empty();

        assertSoftly(softly -> {
            softly.assertThat(filter.direction()).isNull();
            softly.assertThat(filter.ref()).isNull();
            softly.assertThat(filter.actor()).isNull();
            softly.assertThat(filter.timePeriod()).isNull();
            softly.assertThat(filter.activityType()).isNull();
        });
    }

    @Test
    void toQueryParameters_shouldReturnEmptyMap_whenAllNull() {
        final Map<String, String> query = GitHubListActivitiesFilter.empty().toQueryParameters();

        assertThat(query).isEmpty();
    }

    @Test
    void toQueryParameters_shouldOmitNulls_whenSomeFieldsSet() {
        final GitHubListActivitiesFilter filter = GitHubListActivitiesFilterBuilder.builder()
                .direction(GitHubReposSortDirection.DESC)
                .ref("main")
                .actor("octocat")
                .timePeriod(GitHubRepositoryActivityTimePeriod.WEEK)
                .activityType(GitHubRepositoryActivityType.PUSH)
                .build();

        final Map<String, String> query = filter.toQueryParameters();

        assertThat(query)
                .containsEntry("direction", "desc")
                .containsEntry("ref", "main")
                .containsEntry("actor", "octocat")
                .containsEntry("time_period", "week")
                .containsEntry("activity_type", "push");
    }

    @Test
    void toQueryParameters_shouldOmitBlankStringValues_whenBlank() {
        final GitHubListActivitiesFilter filter = GitHubListActivitiesFilterBuilder.builder()
                .ref("   ")
                .actor("   ")
                .build();

        assertThat(filter.toQueryParameters()).isEmpty();
    }
}
