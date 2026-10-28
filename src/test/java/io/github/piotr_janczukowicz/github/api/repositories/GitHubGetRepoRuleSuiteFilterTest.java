package io.github.piotr_janczukowicz.github.api.repositories;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubGetRepoRuleSuiteFilterTest {

    @Test
    void empty_shouldReturnEmptyMap_whenCalled() {
        final GitHubGetRepoRuleSuiteFilter filter = GitHubGetRepoRuleSuiteFilter.empty();

        assertThat(filter.toQueryParameters()).isEmpty();
    }

    @Test
    void toQueryParameters_shouldIncludeIncludeMetadata_whenIncludeMetadataTrue() {
        final GitHubGetRepoRuleSuiteFilter filter = RepositoriesCatalogFixtures.getRepoRuleSuiteFilter();

        assertThat(filter.toQueryParameters())
                .containsEntry("include_metadata", "true")
                .hasSize(1);
    }

    @Test
    void toQueryParameters_shouldIncludeIncludeMetadataFalse_whenIncludeMetadataFalse() {
        final GitHubGetRepoRuleSuiteFilter filter = GitHubGetRepoRuleSuiteFilterBuilder.builder()
                .includeMetadata(false)
                .build();

        assertThat(filter.toQueryParameters()).containsEntry("include_metadata", "false");
    }
}
