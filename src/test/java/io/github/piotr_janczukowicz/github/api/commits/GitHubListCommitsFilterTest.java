package io.github.piotr_janczukowicz.github.api.commits;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.time.Instant;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubListCommitsFilterTest {

    @Test
    void toQueryParameters_shouldIncludeSetFilters_whenCalled() {
        final GitHubListCommitsFilter filter = GitHubListCommitsFilterBuilder.builder()
                .sha("main")
                .since(Instant.parse("2020-01-01T00:00:00Z"))
                .build();

        assertThat(filter.toQueryParameters())
                .containsEntry("sha", "main")
                .containsEntry("since", "2020-01-01T00:00:00Z");
    }
}
