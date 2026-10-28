package io.github.piotr_janczukowicz.github.api.gitdatabase;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubGetTreeFilterTest {

    @Test
    void empty_shouldReturnFilter_whenCalled() {
        final GitHubGetTreeFilter filter = GitHubGetTreeFilter.empty();

        assertThat(filter.toQueryParameters()).isEmpty();
    }

    @Test
    void recursively_shouldSendRecursiveOne_whenCalled() {
        final GitHubGetTreeFilter filter = GitHubGetTreeFilter.recursively();

        assertThat(filter.toQueryParameters()).containsEntry("recursive", "1").hasSize(1);
    }

    @Test
    void toQueryParameters_shouldIncludeRecursive_whenFilterSet() {
        final GitHubGetTreeFilter filter =
                GitHubGetTreeFilterBuilder.builder().recursive("1").build();

        assertThat(filter.toQueryParameters()).containsEntry("recursive", "1");
    }
}
