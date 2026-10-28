package io.github.piotr_janczukowicz.github.api.repositories;

import static org.assertj.core.api.SoftAssertions.assertSoftly;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubUpdatePayloadTest {

    @Test
    void builder_shouldAcceptMergeCommitEnums_whenSet() {
        final GitHubUpdatePayload payload = GitHubUpdatePayloadBuilder.builder()
                .pullRequestCreationPolicy(GitHubPullRequestCreationPolicy.COLLABORATORS_ONLY)
                .squashMergeCommitTitle(GitHubSquashMergeCommitTitle.PR_TITLE)
                .squashMergeCommitMessage(GitHubSquashMergeCommitMessage.BLANK)
                .mergeCommitTitle(GitHubMergeCommitTitle.MERGE_MESSAGE)
                .mergeCommitMessage(GitHubMergeCommitMessage.PR_BODY)
                .build();

        assertSoftly(softly -> {
            softly.assertThat(payload.pullRequestCreationPolicy())
                    .isEqualTo(GitHubPullRequestCreationPolicy.COLLABORATORS_ONLY);
            softly.assertThat(payload.squashMergeCommitTitle()).isEqualTo(GitHubSquashMergeCommitTitle.PR_TITLE);
            softly.assertThat(payload.squashMergeCommitMessage()).isEqualTo(GitHubSquashMergeCommitMessage.BLANK);
            softly.assertThat(payload.mergeCommitTitle()).isEqualTo(GitHubMergeCommitTitle.MERGE_MESSAGE);
            softly.assertThat(payload.mergeCommitMessage()).isEqualTo(GitHubMergeCommitMessage.PR_BODY);
        });
    }
}
