package io.github.piotr_janczukowicz.github.api.teams;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.entry;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubListTeamsFilterTest {

    @Test
    void empty_shouldOmitAllQueryParameters() {
        final GitHubListTeamsFilter filter = GitHubListTeamsFilter.empty();

        assertThat(filter.toQueryParameters()).isEmpty();
    }

    @Test
    void toQueryParameters_shouldIncludeTeamType_whenSet() {
        final GitHubListTeamsFilter filter = GitHubListTeamsFilterBuilder.builder()
                .teamType(GitHubTeamListType.ENTERPRISE)
                .build();

        assertThat(filter.toQueryParameters()).containsExactly(entry("team_type", "enterprise"));
    }
}
