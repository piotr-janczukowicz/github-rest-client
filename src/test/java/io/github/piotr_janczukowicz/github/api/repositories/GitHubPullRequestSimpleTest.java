package io.github.piotr_janczukowicz.github.api.repositories;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.github.api.teams.GitHubTeam;
import io.github.piotr_janczukowicz.github.api.users.GitHubSimpleUser;
import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.List;
import org.jspecify.annotations.Nullable;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubPullRequestSimpleTest {

    @Test
    void constructor_shouldThrow_whenLabelsNull() {
        final GitHubPullRequestSimple pullRequest = RepositoriesCatalogFixtures.pullRequestSimple();

        final Throwable thrown = catchThrowable(() -> copyWith(
                pullRequest,
                null,
                pullRequest.assignees(),
                pullRequest.requestedReviewers(),
                pullRequest.requestedTeams()));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("labels");
    }

    private static GitHubPullRequestSimple copyWith(
            final GitHubPullRequestSimple pullRequest,
            final @Nullable List<GitHubPullRequestLabel> labels,
            final @Nullable List<GitHubSimpleUser> assignees,
            final @Nullable List<GitHubSimpleUser> requestedReviewers,
            final @Nullable List<GitHubTeam> requestedTeams) {
        return new GitHubPullRequestSimple(
                pullRequest.url(),
                pullRequest.id(),
                pullRequest.nodeId(),
                pullRequest.htmlUrl(),
                pullRequest.diffUrl(),
                pullRequest.patchUrl(),
                pullRequest.issueUrl(),
                pullRequest.commitsUrl(),
                pullRequest.reviewCommentsUrl(),
                pullRequest.reviewCommentUrl(),
                pullRequest.commentsUrl(),
                pullRequest.statusesUrl(),
                pullRequest.number(),
                pullRequest.state(),
                pullRequest.locked(),
                pullRequest.title(),
                pullRequest.user(),
                pullRequest.body(),
                labels,
                pullRequest.milestone(),
                pullRequest.activeLockReason(),
                pullRequest.createdAt(),
                pullRequest.updatedAt(),
                pullRequest.closedAt(),
                pullRequest.mergedAt(),
                assignees,
                requestedReviewers,
                requestedTeams,
                pullRequest.head(),
                pullRequest.base(),
                pullRequest.links(),
                pullRequest.authorAssociation(),
                pullRequest.autoMerge(),
                pullRequest.stack(),
                pullRequest.draft());
    }
}
