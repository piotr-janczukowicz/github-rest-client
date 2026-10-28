package io.github.piotr_janczukowicz.github.api.repositories;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.Map;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubGetRepoRulesetFilterTest {

    @Test
    void empty_shouldReturnEmptyQuery_whenCalled() {
        final GitHubGetRepoRulesetFilter filter = GitHubGetRepoRulesetFilter.empty();

        final Map<String, String> query = filter.toQueryParameters();

        assertThat(query).isEmpty();
    }

    @Test
    void toQueryParameters_shouldIncludeIncludesParents_whenProvided() {
        final GitHubGetRepoRulesetFilter filter = GitHubGetRepoRulesetFilterBuilder.builder()
                .includesParents(true)
                .build();

        assertThat(filter.toQueryParameters()).containsEntry("includes_parents", "true");
    }

    @Test
    void toQueryParameters_shouldEncodeFalse_whenIncludesParentsFalse() {
        final GitHubGetRepoRulesetFilter filter = GitHubGetRepoRulesetFilterBuilder.builder()
                .includesParents(false)
                .build();

        assertThat(filter.toQueryParameters()).containsEntry("includes_parents", "false");
    }
}
