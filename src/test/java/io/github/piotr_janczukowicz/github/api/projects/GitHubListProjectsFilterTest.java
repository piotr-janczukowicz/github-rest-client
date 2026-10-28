package io.github.piotr_janczukowicz.github.api.projects;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.Map;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubListProjectsFilterTest {

    @Test
    void empty_shouldReturnEmptyQueryParameters_whenCalled() {
        final Map<String, String> query = GitHubListProjectsFilter.empty().toQueryParameters();

        assertThat(query).isEmpty();
    }

    @Test
    void toQueryParameters_shouldPutQ_whenSet() {
        final GitHubListProjectsFilter filter =
                GitHubListProjectsFilterBuilder.builder().q("open").build();

        final Map<String, String> query = filter.toQueryParameters();

        assertThat(query).containsEntry("q", "open");
    }
}
