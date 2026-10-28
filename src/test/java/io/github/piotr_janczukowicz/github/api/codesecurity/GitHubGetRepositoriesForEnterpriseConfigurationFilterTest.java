package io.github.piotr_janczukowicz.github.api.codesecurity;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubGetRepositoriesForEnterpriseConfigurationFilterTest {

    @Test
    void empty_shouldReturnFilter_whenCalled() {
        final GitHubGetRepositoriesForEnterpriseConfigurationFilter filter =
                GitHubGetRepositoriesForEnterpriseConfigurationFilter.empty();

        assertThat(filter.toQueryParameters()).isEmpty();
    }

    @Test
    void toQueryParameters_shouldIncludeQuery_whenFilterSet() {
        final GitHubGetRepositoriesForEnterpriseConfigurationFilter filter =
                GitHubGetRepositoriesForEnterpriseConfigurationFilterBuilder.builder()
                        .status("attached")
                        .build();

        assertThat(filter.toQueryParameters()).containsEntry("status", "attached");
    }
}
