package io.github.piotr_janczukowicz.github.api.licenses;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubGetForRepoFilterTest {

    @Test
    void empty_shouldReturnFilter_whenCalled() {
        final GitHubGetForRepoFilter filter = GitHubGetForRepoFilter.empty();

        assertThat(filter.toQueryParameters()).isEmpty();
    }

    @Test
    void toQueryParameters_shouldIncludeRef_whenFilterSet() {
        final GitHubGetForRepoFilter filter =
                GitHubGetForRepoFilterBuilder.builder().ref("main").build();

        assertThat(filter.toQueryParameters()).containsEntry("ref", "main");
    }
}
