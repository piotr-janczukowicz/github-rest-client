package io.github.piotr_janczukowicz.github.api.repositories;

import static org.assertj.core.api.SoftAssertions.assertSoftly;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubCreateForAuthenticatedUserPayloadTest {

    @Test
    void builder_shouldAcceptMergeCommitEnums_whenSet() {
        final GitHubCreateForAuthenticatedUserPayload payload = GitHubCreateForAuthenticatedUserPayloadBuilder.builder()
                .name("repo")
                .squashMergeCommitTitle(GitHubSquashMergeCommitTitle.COMMIT_OR_PR_TITLE)
                .squashMergeCommitMessage(GitHubSquashMergeCommitMessage.COMMIT_MESSAGES)
                .mergeCommitTitle(GitHubMergeCommitTitle.PR_TITLE)
                .mergeCommitMessage(GitHubMergeCommitMessage.PR_TITLE)
                .build();

        assertSoftly(softly -> {
            softly.assertThat(payload.squashMergeCommitTitle())
                    .isEqualTo(GitHubSquashMergeCommitTitle.COMMIT_OR_PR_TITLE);
            softly.assertThat(payload.squashMergeCommitMessage())
                    .isEqualTo(GitHubSquashMergeCommitMessage.COMMIT_MESSAGES);
            softly.assertThat(payload.mergeCommitTitle()).isEqualTo(GitHubMergeCommitTitle.PR_TITLE);
            softly.assertThat(payload.mergeCommitMessage()).isEqualTo(GitHubMergeCommitMessage.PR_TITLE);
        });
    }
}
