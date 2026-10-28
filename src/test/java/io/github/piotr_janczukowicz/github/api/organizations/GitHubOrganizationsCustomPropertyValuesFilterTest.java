package io.github.piotr_janczukowicz.github.api.organizations;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubOrganizationsCustomPropertyValuesFilterTest {

    @Test
    void empty_shouldReturnFilterWithNoQueryParameters_whenCalled() {
        final GitHubOrganizationsCustomPropertyValuesFilter filter =
                GitHubOrganizationsCustomPropertyValuesFilter.empty();

        assertThat(filter.toQueryParameters()).isEmpty();
    }

    @Test
    void toQueryParameters_shouldIncludeRepositoryQuery_whenSet() {
        final GitHubOrganizationsCustomPropertyValuesFilter filter =
                GitHubOrganizationsCustomPropertyValuesFilterBuilder.builder()
                        .repositoryQuery("Hello-World")
                        .build();

        assertThat(filter.toQueryParameters()).containsEntry("repository_query", "Hello-World");
    }
}
