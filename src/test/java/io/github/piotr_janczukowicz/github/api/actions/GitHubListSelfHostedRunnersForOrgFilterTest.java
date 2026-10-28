package io.github.piotr_janczukowicz.github.api.actions;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.Map;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubListSelfHostedRunnersForOrgFilterTest {

    @Test
    void empty_shouldReturnFilterWithoutQueryParameters() {
        final GitHubListSelfHostedRunnersForOrgFilter sut = GitHubListSelfHostedRunnersForOrgFilter.empty();

        final Map<String, String> query = sut.toQueryParameters();

        assertThat(query).isEmpty();
    }

    @Test
    void toQueryParameters_shouldIncludeName_whenSet() {
        final GitHubListSelfHostedRunnersForOrgFilter sut = new GitHubListSelfHostedRunnersForOrgFilter("runner-1");

        final Map<String, String> query = sut.toQueryParameters();

        assertThat(query).containsExactlyEntriesOf(Map.of("name", "runner-1"));
    }
}
