package io.github.piotr_janczukowicz.github.api.organizations;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.List;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubCreateOrganizationInvitationPayloadTest {

    @Test
    void constructor_shouldCopyTeamIds_whenPresent() {
        final GitHubCreateOrganizationInvitationPayload payload =
                new GitHubCreateOrganizationInvitationPayload(null, "octocat@github.com", null, List.of(12L, 26L));

        assertThat(payload.teamIds()).containsExactly(12L, 26L);
    }

    @Test
    void constructor_shouldKeepNullTeamIds_whenAbsent() {
        final GitHubCreateOrganizationInvitationPayload payload = new GitHubCreateOrganizationInvitationPayload(
                1L, null, GitHubOrganizationInvitationRole.DIRECT_MEMBER, null);

        assertThat(payload.teamIds()).isNull();
    }
}
