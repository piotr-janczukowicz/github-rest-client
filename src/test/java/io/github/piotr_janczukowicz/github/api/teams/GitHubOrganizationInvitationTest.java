package io.github.piotr_janczukowicz.github.api.teams;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.github.api.users.UsersCatalogFixtures;
import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubOrganizationInvitationTest {

    @Test
    void constructor_shouldThrow_whenRoleNull() {
        final GitHubOrganizationInvitation base = GitHubOrganizationInvitationBuilder.builder()
                .role("direct_member")
                .createdAt("2016-05-30T19:18:10Z")
                .inviter(UsersCatalogFixtures.octocat())
                .nodeId("MDIyOk9yZ2FuaXphdGlvbkludml0YXRpb24x")
                .invitationTeamsUrl("https://api.github.com/organizations/16/invitations/1/teams")
                .build();

        final Throwable thrown = catchThrowable(() -> new GitHubOrganizationInvitation(
                1L,
                "octocat",
                "octocat@github.com",
                null,
                "2016-05-30T19:18:10Z",
                null,
                null,
                base.inviter(),
                1L,
                "MDIyOk9yZ2FuaXphdGlvbkludml0YXRpb24x",
                "https://api.github.com/organizations/16/invitations/1/teams",
                null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("role");
    }

    @Test
    void constructor_shouldThrow_whenInviterNull() {
        final Throwable thrown = catchThrowable(() -> new GitHubOrganizationInvitation(
                1L,
                "octocat",
                "octocat@github.com",
                "direct_member",
                "2016-05-30T19:18:10Z",
                null,
                null,
                null,
                1L,
                "MDIyOk9yZ2FuaXphdGlvbkludml0YXRpb24x",
                "https://api.github.com/organizations/16/invitations/1/teams",
                null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("inviter");
    }
}
