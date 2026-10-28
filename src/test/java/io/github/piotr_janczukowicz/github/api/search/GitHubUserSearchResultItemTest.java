package io.github.piotr_janczukowicz.github.api.search;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.github.api.users.GitHubUserType;
import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.List;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubUserSearchResultItemTest {

    @Test
    void compactConstructor_shouldLeaveTextMatchesNull_whenTextMatchesNull() {
        final GitHubUserSearchResultItem item = minimalItem(null);

        assertThat(item.textMatches()).isNull();
    }

    private static GitHubUserSearchResultItem minimalItem(final List<GitHubSearchTextMatchItem> textMatches) {
        return GitHubUserSearchResultItemBuilder.builder()
                .login("login")
                .id(1L)
                .nodeId("node")
                .avatarUrl("avatar")
                .url("url")
                .htmlUrl("html")
                .followersUrl("followers")
                .subscriptionsUrl("subscriptions")
                .organizationsUrl("organizations")
                .reposUrl("repos")
                .receivedEventsUrl("received")
                .type(GitHubUserType.USER)
                .score(1.0d)
                .followingUrl("following")
                .gistsUrl("gists")
                .starredUrl("starred")
                .eventsUrl("events")
                .siteAdmin(false)
                .textMatches(textMatches)
                .build();
    }
}
