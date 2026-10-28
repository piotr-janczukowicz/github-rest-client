package io.github.piotr_janczukowicz.github.api.codesecurity;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubGetRepositoriesForConfigurationFilterTest {

    @Test
    void empty_shouldReturnFilter_whenCalled() {
        final GitHubGetRepositoriesForConfigurationFilter filter = GitHubGetRepositoriesForConfigurationFilter.empty();

        assertThat(filter.toQueryParameters()).isEmpty();
    }

    @Test
    void toQueryParameters_shouldIncludeQuery_whenFilterSet() {
        final GitHubGetRepositoriesForConfigurationFilter filter =
                GitHubGetRepositoriesForConfigurationFilterBuilder.builder()
                        .status("attached")
                        .build();

        assertThat(filter.toQueryParameters()).containsEntry("status", "attached");
    }
}
