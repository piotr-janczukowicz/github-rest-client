package io.github.piotr_janczukowicz.github.api.gists;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.time.Instant;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubListStarredFilterTest {
    @Test
    void empty_shouldReturnFilter_whenCalled() {
        final GitHubListStarredFilter filter = GitHubListStarredFilter.empty();

        assertThat(filter.toQueryParameters()).isEmpty();
    }

    @Test
    void toQueryParameters_shouldIncludeSince_whenFilterSet() {
        final GitHubListStarredFilter filter = GitHubListStarredFilterBuilder.builder()
                .since(Instant.parse("2022-09-20T12:11:58Z"))
                .build();

        assertThat(filter.toQueryParameters()).containsEntry("since", "2022-09-20T12:11:58Z");
    }
}
