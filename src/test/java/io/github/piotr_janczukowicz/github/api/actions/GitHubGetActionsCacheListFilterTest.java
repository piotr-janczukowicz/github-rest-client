package io.github.piotr_janczukowicz.github.api.actions;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.github.api.GitHubDirection;
import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.Map;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubGetActionsCacheListFilterTest {

    @Test
    void empty_shouldReturnFilterWithoutQueryParameters() {
        final GitHubGetActionsCacheListFilter sut = GitHubGetActionsCacheListFilter.empty();

        final Map<String, String> query = sut.toQueryParameters();

        assertThat(query).isEmpty();
    }

    @Test
    void toQueryParameters_shouldIncludeAllFields_whenSet() {
        final GitHubGetActionsCacheListFilter sut = new GitHubGetActionsCacheListFilter(
                "refs/heads/main", "cache-key", GitHubActionsCacheListSort.SIZE_IN_BYTES, GitHubDirection.DESC);

        final Map<String, String> query = sut.toQueryParameters();

        assertThat(query)
                .containsExactlyInAnyOrderEntriesOf(Map.of(
                        "ref", "refs/heads/main",
                        "key", "cache-key",
                        "sort", "size_in_bytes",
                        "direction", "desc"));
    }
}
