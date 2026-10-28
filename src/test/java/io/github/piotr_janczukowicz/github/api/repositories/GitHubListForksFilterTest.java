package io.github.piotr_janczukowicz.github.api.repositories;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.Map;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubListForksFilterTest {

    @Test
    void empty_shouldReturnNullSort_whenCalled() {
        final GitHubListForksFilter filter = GitHubListForksFilter.empty();

        assertThat(filter.sort()).isNull();
    }

    @Test
    void toQueryParameters_shouldReturnEmptyMap_whenSortNull() {
        final Map<String, String> query = GitHubListForksFilter.empty().toQueryParameters();

        assertThat(query).isEmpty();
    }

    @Test
    void toQueryParameters_shouldReturnSort_whenSortSet() {
        final Map<String, String> query = GitHubListForksFilterBuilder.builder()
                .sort(GitHubRepoForksSort.STARGAZERS)
                .build()
                .toQueryParameters();

        assertThat(query).containsEntry("sort", "stargazers");
    }
}
