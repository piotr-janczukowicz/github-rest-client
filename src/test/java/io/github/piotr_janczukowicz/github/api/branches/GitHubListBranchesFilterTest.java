package io.github.piotr_janczukowicz.github.api.branches;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubListBranchesFilterTest {

    @Test
    void empty_shouldReturnFilter_whenCalled() {
        final GitHubListBranchesFilter filter = GitHubListBranchesFilter.empty();

        assertThat(filter.toQueryParameters()).isEmpty();
    }

    @Test
    void toQueryParameters_shouldIncludeProtected_whenFilterSet() {
        final GitHubListBranchesFilter filter =
                GitHubListBranchesFilterBuilder.builder().protectedFilter(false).build();

        assertThat(filter.toQueryParameters()).containsEntry("protected", "false");
    }
}
