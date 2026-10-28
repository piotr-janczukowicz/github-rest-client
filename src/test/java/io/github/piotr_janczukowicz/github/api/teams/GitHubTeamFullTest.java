package io.github.piotr_janczukowicz.github.api.teams;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.time.Instant;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubTeamFullTest {

    @Test
    void constructor_shouldThrow_whenNameNull() {
        final GitHubTeamFull base = sample();

        final Throwable thrown = catchThrowable(() -> new GitHubTeamFull(
                base.id(),
                base.nodeId(),
                base.url(),
                base.htmlUrl(),
                null,
                base.slug(),
                base.description(),
                base.privacy(),
                base.notificationSetting(),
                base.permission(),
                base.membersUrl(),
                base.repositoriesUrl(),
                base.parent(),
                base.membersCount(),
                base.reposCount(),
                base.createdAt(),
                base.updatedAt(),
                base.organization(),
                base.ldapDn(),
                base.type(),
                base.organizationId(),
                base.enterpriseId()));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("name");
    }

    @Test
    void constructor_shouldThrow_whenOrganizationNull() {
        final GitHubTeamFull base = sample();

        final Throwable thrown = catchThrowable(() -> new GitHubTeamFull(
                base.id(),
                base.nodeId(),
                base.url(),
                base.htmlUrl(),
                base.name(),
                base.slug(),
                base.description(),
                base.privacy(),
                base.notificationSetting(),
                base.permission(),
                base.membersUrl(),
                base.repositoriesUrl(),
                base.parent(),
                base.membersCount(),
                base.reposCount(),
                base.createdAt(),
                base.updatedAt(),
                null,
                base.ldapDn(),
                base.type(),
                base.organizationId(),
                base.enterpriseId()));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("organization");
    }

    private static GitHubTeamFull sample() {
        return GitHubTeamFullBuilder.builder()
                .nodeId("MDQ6VGVhbTE=")
                .url("https://api.github.com/organizations/1/team/1")
                .htmlUrl("https://github.com/orgs/rails/teams/core")
                .name("Developers")
                .slug("justice-league")
                .permission("push")
                .membersUrl("https://api.github.com/organizations/1/team/1/members{/member}")
                .repositoriesUrl("https://api.github.com/organizations/1/team/1/repos")
                .createdAt(Instant.parse("2017-07-14T16:53:42Z"))
                .updatedAt(Instant.parse("2017-08-17T12:37:15Z"))
                .organization(TeamOrganizationCatalogFixtures.sampleOrganization())
                .type(GitHubTeamOwnershipType.ORGANIZATION)
                .build();
    }
}
