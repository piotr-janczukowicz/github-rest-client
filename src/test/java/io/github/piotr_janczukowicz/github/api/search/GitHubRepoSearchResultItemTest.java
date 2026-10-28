package io.github.piotr_janczukowicz.github.api.search;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.time.Instant;
import java.util.List;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubRepoSearchResultItemTest {

    @Test
    void compactConstructor_shouldLeaveTopicsNull_whenTopicsNull() {
        final GitHubRepoSearchResultItem item = minimalItem(null);

        assertThat(item.topics()).isNull();
    }

    private static GitHubRepoSearchResultItem minimalItem(final List<String> topics) {
        return baseBuilder().topics(topics).build();
    }

    private static GitHubRepoSearchResultItemBuilder baseBuilder() {
        return GitHubRepoSearchResultItemBuilder.builder()
                .id(1L)
                .nodeId("node")
                .name("name")
                .fullName("owner/name")
                .isPrivate(false)
                .htmlUrl("html")
                .fork(false)
                .url("url")
                .createdAt(Instant.parse("2012-01-01T00:31:50Z"))
                .updatedAt(Instant.parse("2013-01-05T17:58:47Z"))
                .pushedAt(Instant.parse("2012-01-01T00:37:02Z"))
                .size(0)
                .stargazersCount(0)
                .watchersCount(0)
                .forksCount(0)
                .openIssuesCount(0)
                .defaultBranch("master")
                .score(1.0d)
                .forksUrl("forks")
                .keysUrl("keys")
                .collaboratorsUrl("collaborators")
                .teamsUrl("teams")
                .hooksUrl("hooks")
                .issueEventsUrl("issue-events")
                .eventsUrl("events")
                .assigneesUrl("assignees")
                .branchesUrl("branches")
                .tagsUrl("tags")
                .blobsUrl("blobs")
                .gitTagsUrl("git-tags")
                .gitRefsUrl("git-refs")
                .treesUrl("trees")
                .statusesUrl("statuses")
                .languagesUrl("languages")
                .stargazersUrl("stargazers")
                .contributorsUrl("contributors")
                .subscribersUrl("subscribers")
                .subscriptionUrl("subscription")
                .commitsUrl("commits")
                .gitCommitsUrl("git-commits")
                .commentsUrl("comments")
                .issueCommentUrl("issue-comment")
                .contentsUrl("contents")
                .compareUrl("compare")
                .mergesUrl("merges")
                .archiveUrl("archive")
                .downloadsUrl("downloads")
                .issuesUrl("issues")
                .pullsUrl("pulls")
                .milestonesUrl("milestones")
                .notificationsUrl("notifications")
                .labelsUrl("labels")
                .releasesUrl("releases")
                .deploymentsUrl("deployments")
                .gitUrl("git")
                .sshUrl("ssh")
                .cloneUrl("clone")
                .svnUrl("svn")
                .forks(0)
                .openIssues(0)
                .watchers(0)
                .hasIssues(false)
                .hasProjects(false)
                .hasPages(false)
                .hasWiki(false)
                .hasDownloads(false)
                .archived(false)
                .disabled(false);
    }

    @Test
    void toString_shouldHideSecrets() {
        final GitHubRepoSearchResultItem value = SearchCatalogFixtures.repoSearchTetris();

        final String text = value.toString();

        assertSoftly(softly -> {
            softly.assertThat(text).doesNotContain(value.tempCloneToken());
            softly.assertThat(text).doesNotContain("tempCloneToken");
            softly.assertThat(text).startsWith("GitHubRepoSearchResultItem[id=");
        });
    }
}
