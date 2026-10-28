package io.github.piotr_janczukowicz.github.api.teams;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubTeamMemberTest {

    @Test
    void constructor_shouldThrow_whenLoginNull() {
        final GitHubTeamMember base = sample();

        final Throwable thrown = catchThrowable(() -> new GitHubTeamMember(
                base.name(),
                base.email(),
                null,
                base.id(),
                base.nodeId(),
                base.avatarUrl(),
                base.gravatarId(),
                base.url(),
                base.htmlUrl(),
                base.followersUrl(),
                base.followingUrl(),
                base.gistsUrl(),
                base.starredUrl(),
                base.subscriptionsUrl(),
                base.organizationsUrl(),
                base.reposUrl(),
                base.eventsUrl(),
                base.receivedEventsUrl(),
                base.type(),
                base.siteAdmin(),
                base.starredAt(),
                base.userViewType(),
                base.role(),
                base.inherited()));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("login");
    }

    @Test
    void constructor_shouldThrow_whenTypeNull() {
        final GitHubTeamMember base = sample();

        final Throwable thrown = catchThrowable(() -> new GitHubTeamMember(
                base.name(),
                base.email(),
                base.login(),
                base.id(),
                base.nodeId(),
                base.avatarUrl(),
                base.gravatarId(),
                base.url(),
                base.htmlUrl(),
                base.followersUrl(),
                base.followingUrl(),
                base.gistsUrl(),
                base.starredUrl(),
                base.subscriptionsUrl(),
                base.organizationsUrl(),
                base.reposUrl(),
                base.eventsUrl(),
                base.receivedEventsUrl(),
                null,
                base.siteAdmin(),
                base.starredAt(),
                base.userViewType(),
                base.role(),
                base.inherited()));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("type");
    }

    private static GitHubTeamMember sample() {
        return GitHubTeamMemberBuilder.builder()
                .login("octocat")
                .nodeId("MDQ6VXNlcjE=")
                .avatarUrl("https://github.com/images/error/octocat_happy.gif")
                .url("https://api.github.com/users/octocat")
                .htmlUrl("https://github.com/octocat")
                .followersUrl("https://api.github.com/users/octocat/followers")
                .followingUrl("https://api.github.com/users/octocat/following{/other_user}")
                .gistsUrl("https://api.github.com/users/octocat/gists{/gist_id}")
                .starredUrl("https://api.github.com/users/octocat/starred{/owner}{/repo}")
                .subscriptionsUrl("https://api.github.com/users/octocat/subscriptions")
                .organizationsUrl("https://api.github.com/users/octocat/orgs")
                .reposUrl("https://api.github.com/users/octocat/repos")
                .eventsUrl("https://api.github.com/users/octocat/events{/privacy}")
                .receivedEventsUrl("https://api.github.com/users/octocat/received_events")
                .type("User")
                .build();
    }
}
