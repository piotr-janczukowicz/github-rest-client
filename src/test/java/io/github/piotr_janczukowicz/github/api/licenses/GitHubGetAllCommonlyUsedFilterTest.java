package io.github.piotr_janczukowicz.github.api.licenses;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubGetAllCommonlyUsedFilterTest {

    @Test
    void empty_shouldReturnFilter_whenCalled() {
        final GitHubGetAllCommonlyUsedFilter filter = GitHubGetAllCommonlyUsedFilter.empty();

        assertThat(filter.toQueryParameters()).isEmpty();
    }

    @Test
    void toQueryParameters_shouldIncludeFeatured_whenFilterSet() {
        final GitHubGetAllCommonlyUsedFilter filter =
                GitHubGetAllCommonlyUsedFilterBuilder.builder().featured(true).build();

        assertThat(filter.toQueryParameters()).containsEntry("featured", "true");
    }
}
