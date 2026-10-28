package io.github.piotr_janczukowicz.github.api.teams;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.entry;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubListTeamMembersFilterTest {

    @Test
    void empty_shouldOmitAllQueryParameters() {
        final GitHubListTeamMembersFilter filter = GitHubListTeamMembersFilter.empty();

        assertThat(filter.toQueryParameters()).isEmpty();
    }

    @Test
    void toQueryParameters_shouldIncludeRole_whenSet() {
        final GitHubListTeamMembersFilter filter = GitHubListTeamMembersFilterBuilder.builder()
                .role(GitHubTeamMembersListRole.MAINTAINER)
                .build();

        assertThat(filter.toQueryParameters()).containsExactly(entry("role", "maintainer"));
    }
}
