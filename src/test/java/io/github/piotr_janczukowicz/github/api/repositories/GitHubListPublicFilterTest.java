package io.github.piotr_janczukowicz.github.api.repositories;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.Map;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubListPublicFilterTest {

    @Test
    void empty_shouldLeaveVisibilityUnset_whenCalled() {
        final GitHubListPublicFilter filter = GitHubListPublicFilter.empty();

        assertThat(filter.visibility()).isNull();
    }

    @Test
    void toQueryParameters_shouldReturnEmptyMap_whenVisibilityUnset() {
        final Map<String, String> query = GitHubListPublicFilter.empty().toQueryParameters();

        assertThat(query).isEmpty();
    }

    @Test
    void toQueryParameters_shouldSendVisibility_whenVisibilitySet() {
        final GitHubListPublicFilter filter = new GitHubListPublicFilter(GitHubPublicReposVisibility.ALL);

        final Map<String, String> query = filter.toQueryParameters();

        assertThat(query).containsExactly(Map.entry("visibility", "all"));
    }
}
