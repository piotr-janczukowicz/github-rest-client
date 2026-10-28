package io.github.piotr_janczukowicz.github.api.teams;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubTeamMembershipTest {

    @Test
    void constructor_shouldThrow_whenUrlNull() {
        final GitHubTeamMembership base = sample();

        final Throwable thrown = catchThrowable(() -> new GitHubTeamMembership(null, base.role(), base.state()));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("url");
    }

    @Test
    void constructor_shouldThrow_whenRoleNull() {
        final GitHubTeamMembership base = sample();

        final Throwable thrown = catchThrowable(() -> new GitHubTeamMembership(base.url(), null, base.state()));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("role");
    }

    @Test
    void constructor_shouldThrow_whenStateNull() {
        final GitHubTeamMembership base = sample();

        final Throwable thrown = catchThrowable(() -> new GitHubTeamMembership(base.url(), base.role(), null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("state");
    }

    private static GitHubTeamMembership sample() {
        return new GitHubTeamMembership(
                "https://api.github.com/teams/1/memberships/octocat",
                GitHubTeamRole.MEMBER,
                GitHubTeamMembershipState.ACTIVE);
    }
}
