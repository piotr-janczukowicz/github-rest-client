package io.github.piotr_janczukowicz.github.api.issues;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubListEventsForTimelineFilterTest {

    @Test
    void empty_shouldReturnFilterWithNoQueryParameters_whenCalled() {
        final GitHubListEventsForTimelineFilter filter = GitHubListEventsForTimelineFilter.empty();

        assertThat(filter.toQueryParameters()).isEmpty();
    }

    @Test
    void toQueryParameters_shouldIncludeExclude_whenSet() {
        final GitHubListEventsForTimelineFilter filter = GitHubListEventsForTimelineFilterBuilder.builder()
                .exclude("committed")
                .build();

        assertThat(filter.toQueryParameters()).containsEntry("exclude", "committed");
    }
}
