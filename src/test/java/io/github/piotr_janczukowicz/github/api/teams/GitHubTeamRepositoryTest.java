package io.github.piotr_janczukowicz.github.api.teams;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.List;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubTeamRepositoryTest {

    @Test
    void constructor_shouldThrow_whenNodeIdNull() {
        final GitHubTeamRepository base = sample();

        final Throwable thrown = catchThrowable(() -> copy(base, null, base.topics()));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("nodeId");
    }

    @Test
    void constructor_shouldKeepNullTopics_whenTopicsNull() {
        final GitHubTeamRepository base = sample();
        final GitHubTeamRepository repository = copy(base, base.nodeId(), null);

        assertThat(repository.topics()).isNull();
    }

    private static GitHubTeamRepository copy(
            final GitHubTeamRepository base, final String nodeId, final List<String> topics) {
        return new GitHubTeamRepository(
                base.id(),
                nodeId,
                base.name(),
                base.fullName(),
                base.license(),
                base.forks(),
                base.permissions(),
                base.roleName(),
                base.owner(),
                base.isPrivate(),
                base.htmlUrl(),
                base.description(),
                base.fork(),
                base.url(),
                base.archiveUrl(),
                base.assigneesUrl(),
                base.blobsUrl(),
                base.branchesUrl(),
                base.collaboratorsUrl(),
                base.commentsUrl(),
                base.commitsUrl(),
                base.compareUrl(),
                base.contentsUrl(),
                base.contributorsUrl(),
                base.deploymentsUrl(),
                base.downloadsUrl(),
                base.eventsUrl(),
                base.forksUrl(),
                base.gitCommitsUrl(),
                base.gitRefsUrl(),
                base.gitTagsUrl(),
                base.gitUrl(),
                base.issueCommentUrl(),
                base.issueEventsUrl(),
                base.issuesUrl(),
                base.keysUrl(),
                base.labelsUrl(),
                base.languagesUrl(),
                base.mergesUrl(),
                base.milestonesUrl(),
                base.notificationsUrl(),
                base.pullsUrl(),
                base.releasesUrl(),
                base.sshUrl(),
                base.stargazersUrl(),
                base.statusesUrl(),
                base.subscribersUrl(),
                base.subscriptionUrl(),
                base.tagsUrl(),
                base.teamsUrl(),
                base.treesUrl(),
                base.cloneUrl(),
                base.mirrorUrl(),
                base.hooksUrl(),
                base.svnUrl(),
                base.homepage(),
                base.language(),
                base.forksCount(),
                base.stargazersCount(),
                base.watchersCount(),
                base.size(),
                base.defaultBranch(),
                base.openIssuesCount(),
                base.isTemplate(),
                topics,
                base.hasIssues(),
                base.hasProjects(),
                base.hasWiki(),
                base.hasPages(),
                base.archived(),
                base.disabled(),
                base.visibility(),
                base.pushedAt(),
                base.createdAt(),
                base.updatedAt(),
                base.allowRebaseMerge(),
                base.tempCloneToken(),
                base.allowSquashMerge(),
                base.allowAutoMerge(),
                base.deleteBranchOnMerge(),
                base.allowMergeCommit(),
                base.allowForking(),
                base.webCommitSignoffRequired(),
                base.subscribersCount(),
                base.networkCount(),
                base.openIssues(),
                base.watchers(),
                base.masterBranch());
    }

    private static GitHubTeamRepository sample() {
        final String repo = "https://api.github.com/repos/octocat/Hello-World";
        return GitHubTeamRepositoryBuilder.builder()
                .nodeId("MDEwOlJlcG9zaXRvcnkxMjk2MjY5")
                .name("Hello-World")
                .fullName("octocat/Hello-World")
                .htmlUrl("https://github.com/octocat/Hello-World")
                .url(repo)
                .archiveUrl("https://api.github.com/repos/octocat/Hello-World/{archive_format}{/ref}")
                .assigneesUrl("https://api.github.com/repos/octocat/Hello-World/assignees{/user}")
                .blobsUrl("https://api.github.com/repos/octocat/Hello-World/git/blobs{/sha}")
                .branchesUrl("https://api.github.com/repos/octocat/Hello-World/branches{/branch}")
                .collaboratorsUrl("https://api.github.com/repos/octocat/Hello-World/collaborators{/collaborator}")
                .commentsUrl("https://api.github.com/repos/octocat/Hello-World/comments{/number}")
                .commitsUrl("https://api.github.com/repos/octocat/Hello-World/commits{/sha}")
                .compareUrl("https://api.github.com/repos/octocat/Hello-World/compare/{base}...{head}")
                .contentsUrl("https://api.github.com/repos/octocat/Hello-World/contents/{+path}")
                .contributorsUrl("https://api.github.com/repos/octocat/Hello-World/contributors")
                .deploymentsUrl("https://api.github.com/repos/octocat/Hello-World/deployments")
                .downloadsUrl("https://api.github.com/repos/octocat/Hello-World/downloads")
                .eventsUrl("https://api.github.com/repos/octocat/Hello-World/events")
                .forksUrl("https://api.github.com/repos/octocat/Hello-World/forks")
                .gitCommitsUrl("https://api.github.com/repos/octocat/Hello-World/git/commits{/sha}")
                .gitRefsUrl("https://api.github.com/repos/octocat/Hello-World/git/refs{/sha}")
                .gitTagsUrl("https://api.github.com/repos/octocat/Hello-World/git/tags{/sha}")
                .gitUrl("git:github.com/octocat/Hello-World.git")
                .issueCommentUrl("https://api.github.com/repos/octocat/Hello-World/issues/comments{/number}")
                .issueEventsUrl("https://api.github.com/repos/octocat/Hello-World/issues/events{/number}")
                .issuesUrl("https://api.github.com/repos/octocat/Hello-World/issues{/number}")
                .keysUrl("https://api.github.com/repos/octocat/Hello-World/keys{/key_id}")
                .labelsUrl("https://api.github.com/repos/octocat/Hello-World/labels{/name}")
                .languagesUrl("https://api.github.com/repos/octocat/Hello-World/languages")
                .mergesUrl("https://api.github.com/repos/octocat/Hello-World/merges")
                .milestonesUrl("https://api.github.com/repos/octocat/Hello-World/milestones{/number}")
                .notificationsUrl(
                        "https://api.github.com/repos/octocat/Hello-World/notifications{?since,all,participating}")
                .pullsUrl("https://api.github.com/repos/octocat/Hello-World/pulls{/number}")
                .releasesUrl("https://api.github.com/repos/octocat/Hello-World/releases{/id}")
                .sshUrl("git@github.com:octocat/Hello-World.git")
                .stargazersUrl("https://api.github.com/repos/octocat/Hello-World/stargazers")
                .statusesUrl("https://api.github.com/repos/octocat/Hello-World/statuses/{sha}")
                .subscribersUrl("https://api.github.com/repos/octocat/Hello-World/subscribers")
                .subscriptionUrl("https://api.github.com/repos/octocat/Hello-World/subscription")
                .tagsUrl("https://api.github.com/repos/octocat/Hello-World/tags")
                .teamsUrl("https://api.github.com/repos/octocat/Hello-World/teams")
                .treesUrl("https://api.github.com/repos/octocat/Hello-World/git/trees{/sha}")
                .cloneUrl("https://github.com/octocat/Hello-World.git")
                .hooksUrl("https://api.github.com/repos/octocat/Hello-World/hooks")
                .svnUrl("https://svn.github.com/octocat/Hello-World")
                .defaultBranch("master")
                .build();
    }

    @Test
    void toString_shouldHideSecrets() {
        final GitHubTeamRepository value = TeamCatalogFixtures.teamRepository();

        final String text = value.toString();

        assertSoftly(softly -> {
            softly.assertThat(text).doesNotContain(value.tempCloneToken());
            softly.assertThat(text).doesNotContain("tempCloneToken");
            softly.assertThat(text).startsWith("GitHubTeamRepository[id=");
        });
    }
}
