package io.github.piotr_janczukowicz.github.api.actions;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.Map;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubListSelfHostedRunnerGroupsForOrgFilterTest {

    @Test
    void empty_shouldReturnFilterWithoutQueryParameters() {
        final GitHubListSelfHostedRunnerGroupsForOrgFilter sut = GitHubListSelfHostedRunnerGroupsForOrgFilter.empty();

        final Map<String, String> query = sut.toQueryParameters();

        assertThat(query).isEmpty();
    }

    @Test
    void toQueryParameters_shouldIncludeVisibleToRepository_whenSet() {
        final GitHubListSelfHostedRunnerGroupsForOrgFilter sut =
                new GitHubListSelfHostedRunnerGroupsForOrgFilter("octo-repo");

        final Map<String, String> query = sut.toQueryParameters();

        assertThat(query).containsExactlyEntriesOf(Map.of("visible_to_repository", "octo-repo"));
    }
}
