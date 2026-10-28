package io.github.piotr_janczukowicz.github.api.issues;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.time.Instant;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubListCommentsFilterTest {

    @Test
    void empty_shouldReturnFilterWithNoQueryParameters_whenCalled() {
        final GitHubListCommentsFilter filter = GitHubListCommentsFilter.empty();

        assertThat(filter.toQueryParameters()).isEmpty();
    }

    @Test
    void toQueryParameters_shouldIncludeSince_whenSet() {
        final GitHubListCommentsFilter filter = GitHubListCommentsFilterBuilder.builder()
                .since(Instant.parse("2020-01-01T00:00:00Z"))
                .build();

        assertThat(filter.toQueryParameters()).containsEntry("since", "2020-01-01T00:00:00Z");
    }
}
