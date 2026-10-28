package io.github.piotr_janczukowicz.github.api.organizations;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubGetOrgRuleSuiteFilterTest {

    @Test
    void empty_shouldReturnEmptyMap_whenCalled() {
        final GitHubGetOrgRuleSuiteFilter filter = GitHubGetOrgRuleSuiteFilter.empty();

        assertThat(filter.toQueryParameters()).isEmpty();
    }

    @Test
    void toQueryParameters_shouldIncludeIncludeMetadata_whenIncludeMetadataTrue() {
        final GitHubGetOrgRuleSuiteFilter filter = OrganizationsCatalogFixtures.getOrgRuleSuiteFilter();

        assertThat(filter.toQueryParameters())
                .containsEntry("include_metadata", "true")
                .hasSize(1);
    }

    @Test
    void toQueryParameters_shouldIncludeIncludeMetadataFalse_whenIncludeMetadataFalse() {
        final GitHubGetOrgRuleSuiteFilter filter = GitHubGetOrgRuleSuiteFilterBuilder.builder()
                .includeMetadata(false)
                .build();

        assertThat(filter.toQueryParameters()).containsEntry("include_metadata", "false");
    }
}
