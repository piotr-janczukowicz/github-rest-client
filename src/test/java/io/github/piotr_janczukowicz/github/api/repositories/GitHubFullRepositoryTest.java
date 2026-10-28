package io.github.piotr_janczukowicz.github.api.repositories;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubFullRepositoryTest {

    @Test
    void constructor_shouldKeepNullTopics_whenTopicsNull() {
        final GitHubFullRepository base = RepositoriesCatalogFixtures.fullRepository();
        final GitHubFullRepository repository = rebuild(base, null, base.customProperties());

        assertThat(repository.topics()).isNull();
    }

    @Test
    void constructor_shouldKeepNullCustomProperties_whenCustomPropertiesNull() {
        final GitHubFullRepository base = RepositoriesCatalogFixtures.fullRepository();
        final GitHubFullRepository repository = rebuild(base, base.topics(), null);

        assertThat(repository.customProperties()).isNull();
    }

    @Test
    void constructor_shouldKeepNullValue_whenCustomPropertyValueNull() {
        final GitHubFullRepository base = RepositoriesCatalogFixtures.fullRepository();
        final Map<String, Object> customProperties = new HashMap<>();
        customProperties.put("environment", null);

        final GitHubFullRepository repository = rebuild(base, base.topics(), customProperties);

        assertThat(repository.customProperties()).containsEntry("environment", null);
    }

    private static GitHubFullRepository rebuild(
            final GitHubFullRepository base, final List<String> topics, final Map<String, Object> customProperties) {
        return new GitHubFullRepository(
                base.id(),
                base.nodeId(),
                base.name(),
                base.fullName(),
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
                base.permissions(),
                base.allowRebaseMerge(),
                base.templateRepository(),
                base.tempCloneToken(),
                base.allowSquashMerge(),
                base.allowAutoMerge(),
                base.deleteBranchOnMerge(),
                base.allowMergeCommit(),
                base.allowUpdateBranch(),
                base.squashMergeCommitTitle(),
                base.squashMergeCommitMessage(),
                base.mergeCommitTitle(),
                base.mergeCommitMessage(),
                base.allowForking(),
                base.webCommitSignoffRequired(),
                base.subscribersCount(),
                base.networkCount(),
                base.license(),
                base.organization(),
                base.parent(),
                base.source(),
                base.forks(),
                base.masterBranch(),
                base.openIssues(),
                base.watchers(),
                base.anonymousAccessEnabled(),
                base.codeOfConduct(),
                base.securityAndAnalysis(),
                customProperties);
    }
}
