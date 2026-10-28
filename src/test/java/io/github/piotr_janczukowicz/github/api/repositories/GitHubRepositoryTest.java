package io.github.piotr_janczukowicz.github.api.repositories;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.List;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubRepositoryTest {

    @Test
    void constructor_shouldKeepNullTopics_whenTopicsNull() {
        final GitHubRepository base = RepositoriesCatalogFixtures.teamEnvironmentRepository();
        final GitHubRepository repository = rebuild(base, null);

        assertThat(repository.topics()).isNull();
    }

    private static GitHubRepository rebuild(final GitHubRepository base, final List<String> topics) {
        return new GitHubRepository(
                base.id(),
                base.nodeId(),
                base.name(),
                base.fullName(),
                base.license(),
                base.forks(),
                base.permissions(),
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
                base.hasDiscussions(),
                base.hasPullRequests(),
                base.pullRequestCreationPolicy(),
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
                base.allowUpdateBranch(),
                base.squashMergeCommitTitle(),
                base.squashMergeCommitMessage(),
                base.mergeCommitTitle(),
                base.mergeCommitMessage(),
                base.allowMergeCommit(),
                base.allowForking(),
                base.webCommitSignoffRequired(),
                base.openIssues(),
                base.watchers(),
                base.starredAt(),
                base.anonymousAccessEnabled(),
                base.codeSearchIndexStatus());
    }
}
