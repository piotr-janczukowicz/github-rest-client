package io.github.piotr_janczukowicz.github.api.repositories;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.Map;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubGetRepoRulesetsFilterTest {

    @Test
    void empty_shouldReturnEmptyQuery_whenCalled() {
        final GitHubGetRepoRulesetsFilter filter = GitHubGetRepoRulesetsFilter.empty();

        final Map<String, String> query = filter.toQueryParameters();

        assertThat(query).isEmpty();
    }

    @Test
    void toQueryParameters_shouldIncludeFields_whenProvided() {
        final GitHubGetRepoRulesetsFilter filter = GitHubGetRepoRulesetsFilterBuilder.builder()
                .includesParents(true)
                .targets("branch,tag")
                .build();

        final Map<String, String> query = filter.toQueryParameters();

        assertThat(query).containsEntry("includes_parents", "true").containsEntry("targets", "branch,tag");
    }

    @Test
    void toQueryParameters_shouldOmitTargets_whenBlank() {
        final GitHubGetRepoRulesetsFilter filter = GitHubGetRepoRulesetsFilterBuilder.builder()
                .includesParents(false)
                .targets(" ")
                .build();

        final Map<String, String> query = filter.toQueryParameters();

        assertThat(query).containsExactly(Map.entry("includes_parents", "false"));
    }
}
