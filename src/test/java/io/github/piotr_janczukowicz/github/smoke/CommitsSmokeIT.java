package io.github.piotr_janczukowicz.github.smoke;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.github.api.GitHub;
import io.github.piotr_janczukowicz.github.api.commits.GitHubCombinedCommitStatus;
import io.github.piotr_janczukowicz.github.api.commits.GitHubCommitComment;
import io.github.piotr_janczukowicz.github.api.commits.GitHubCommitStatus;
import io.github.piotr_janczukowicz.github.api.commits.GitHubCommitStatusState;
import io.github.piotr_janczukowicz.github.api.commits.GitHubCreateCommitCommentPayload;
import io.github.piotr_janczukowicz.github.api.commits.GitHubCreateCommitStatusPayload;
import io.github.piotr_janczukowicz.github.api.commits.GitHubSimpleCommitStatus;
import io.github.piotr_janczukowicz.github.api.reactions.GitHubCreateForCommitCommentPayload;
import io.github.piotr_janczukowicz.github.api.reactions.GitHubReaction;
import io.github.piotr_janczukowicz.github.api.reactions.GitHubReactionContent;
import java.io.IOException;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag("smoke")
class CommitsSmokeIT {

    private static final SmokeEnvironment ENV = SmokeEnvironment.fromEnv();

    @BeforeAll
    static void requireToken() {
        ENV.requireConfigured();
    }

    private static String headSha(final GitHub github) throws IOException {
        return github.commits()
                .commit()
                .listCommits(ENV.owner(), ENV.repo())
                .items()
                .getFirst()
                .sha();
    }

    @Test
    void status_shouldAppearInTheCombinedStatus_whenCreatedOnTheHeadCommit() throws IOException {
        final GitHub github = ENV.github();
        final String sha = headSha(github);
        final String context = ENV.name("status");

        final GitHubCommitStatus created = github.commits()
                .statuses()
                .createCommitStatus(
                        ENV.owner(),
                        ENV.repo(),
                        sha,
                        new GitHubCreateCommitStatusPayload(
                                GitHubCommitStatusState.SUCCESS, null, "smoke status", context));
        final GitHubCombinedCommitStatus combined = github.commits()
                .statuses()
                .getCombinedStatusForRef(ENV.owner(), ENV.repo(), sha)
                .orElseThrow();
        final List<GitHubCommitStatus> listed = github.commits()
                .statuses()
                .listCommitStatusesForRef(ENV.owner(), ENV.repo(), sha)
                .items();

        assertThat(created.state()).isEqualTo("success");
        assertThat(combined.statuses())
                .extracting(GitHubSimpleCommitStatus::context)
                .contains(context);
        assertThat(listed).extracting(GitHubCommitStatus::id).contains(created.id());
    }

    @Test
    void commitComment_shouldRoundTrip_whenCreatedReactedToAndDeleted() throws IOException {
        final GitHub github = ENV.github();
        final GitHubCommitComment comment = github.commits()
                .comments()
                .createCommitComment(
                        ENV.owner(),
                        ENV.repo(),
                        headSha(github),
                        new GitHubCreateCommitCommentPayload("smoke comment", null, null, null));
        try {
            final GitHubReaction reaction = github.reactions()
                    .createForCommitComment(
                            ENV.owner(),
                            ENV.repo(),
                            comment.id(),
                            new GitHubCreateForCommitCommentPayload(GitHubReactionContent.HEART));
            final GitHubCommitComment fetched = github.commits()
                    .comments()
                    .getCommitComment(ENV.owner(), ENV.repo(), comment.id())
                    .orElseThrow();
            github.reactions().deleteForCommitComment(ENV.owner(), ENV.repo(), comment.id(), reaction.id());

            assertThat(fetched.body()).isEqualTo("smoke comment");
            assertThat(reaction.content()).isEqualTo(GitHubReactionContent.HEART);
        } finally {
            github.commits().comments().deleteCommitComment(ENV.owner(), ENV.repo(), comment.id());
        }
    }
}
