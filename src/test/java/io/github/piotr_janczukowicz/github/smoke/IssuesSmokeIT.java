package io.github.piotr_janczukowicz.github.smoke;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.github.api.GitHub;
import io.github.piotr_janczukowicz.github.api.issues.GitHubCreateCommentPayload;
import io.github.piotr_janczukowicz.github.api.issues.GitHubCreateIssueLabel;
import io.github.piotr_janczukowicz.github.api.issues.GitHubCreateLabelPayload;
import io.github.piotr_janczukowicz.github.api.issues.GitHubCreateMilestonePayload;
import io.github.piotr_janczukowicz.github.api.issues.GitHubCreatePayload;
import io.github.piotr_janczukowicz.github.api.issues.GitHubIssue;
import io.github.piotr_janczukowicz.github.api.issues.GitHubIssueMilestoneReference;
import io.github.piotr_janczukowicz.github.api.issues.GitHubIssueUpdateState;
import io.github.piotr_janczukowicz.github.api.issues.GitHubLabel;
import io.github.piotr_janczukowicz.github.api.issues.GitHubUpdatePayload;
import io.github.piotr_janczukowicz.github.api.search.GitHubIssueComment;
import io.github.piotr_janczukowicz.github.api.search.GitHubMilestone;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag("smoke")
class IssuesSmokeIT {

    private static final SmokeEnvironment ENV = SmokeEnvironment.fromEnv();

    @BeforeAll
    static void requireToken() {
        ENV.requireConfigured();
    }

    @Test
    void issue_shouldRoundTrip_whenCreatedCommentedLabeledAndClosed() throws IOException {
        final GitHub github = ENV.github();
        final String owner = ENV.owner();
        final String repo = ENV.repo();
        final String labelName = ENV.name("label");
        github.issues().labels().createLabel(owner, repo, new GitHubCreateLabelPayload(labelName, "ededed", null));
        GitHubMilestone milestone = null;
        GitHubIssueComment comment = null;
        try {
            milestone = github.issues()
                    .milestones()
                    .createMilestone(
                            owner, repo, new GitHubCreateMilestonePayload(ENV.name("milestone"), null, null, null));
            final GitHubIssue created = github.issues()
                    .issue()
                    .create(
                            owner,
                            repo,
                            new GitHubCreatePayload(
                                    ENV.name("issue"),
                                    "created by a smoke test",
                                    new GitHubIssueMilestoneReference((long) milestone.number(), null),
                                    List.of(new GitHubCreateIssueLabel(labelName, null)),
                                    null,
                                    null,
                                    null,
                                    null));
            comment = github.issues()
                    .comments()
                    .createComment(owner, repo, created.number(), new GitHubCreateCommentPayload("smoke comment"));

            final GitHubIssue fetched =
                    github.issues().issue().get(owner, repo, created.number()).orElseThrow();
            github.issues()
                    .issue()
                    .update(
                            owner,
                            repo,
                            created.number(),
                            new GitHubUpdatePayload(
                                    null,
                                    null,
                                    GitHubIssueUpdateState.CLOSED,
                                    null,
                                    null,
                                    null,
                                    null,
                                    null,
                                    null,
                                    null));
            final GitHubIssue closed =
                    github.issues().issue().get(owner, repo, created.number()).orElseThrow();

            assertThat(fetched.title()).isEqualTo(ENV.name("issue"));
            assertThat(fetched.milestone()).isNotNull();
            assertThat(fetched.milestone().number()).isEqualTo(milestone.number());
            assertThat(comment.body()).isEqualTo("smoke comment");
            assertThat(closed.state()).isEqualTo("closed");
        } finally {
            if (comment != null) {
                github.issues().comments().deleteComment(owner, repo, comment.id());
            }
            if (milestone != null) {
                github.issues().milestones().deleteMilestone(owner, repo, milestone.number());
            }
            github.issues().labels().deleteLabel(owner, repo, labelName);
        }
    }

    @Test
    void getLabel_shouldBeEmpty_whenTheLabelWasDeleted() throws IOException {
        final GitHub github = ENV.github();
        final String name = ENV.name("gone");
        github.issues().labels().createLabel(ENV.owner(), ENV.repo(), new GitHubCreateLabelPayload(name, null, null));
        github.issues().labels().deleteLabel(ENV.owner(), ENV.repo(), name);

        final Optional<GitHubLabel> label = github.issues().labels().getLabel(ENV.owner(), ENV.repo(), name);

        assertThat(label).isEmpty();
    }
}
