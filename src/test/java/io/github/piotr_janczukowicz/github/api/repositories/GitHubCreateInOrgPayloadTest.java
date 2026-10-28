package io.github.piotr_janczukowicz.github.api.repositories;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.Map;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubCreateInOrgPayloadTest {

    @Test
    void builder_shouldAcceptMergeCommitEnums_whenSet() {
        final GitHubCreateInOrgPayload payload = GitHubCreateInOrgPayloadBuilder.builder()
                .name("repo")
                .squashMergeCommitTitle(GitHubSquashMergeCommitTitle.PR_TITLE)
                .squashMergeCommitMessage(GitHubSquashMergeCommitMessage.PR_BODY)
                .mergeCommitTitle(GitHubMergeCommitTitle.MERGE_MESSAGE)
                .mergeCommitMessage(GitHubMergeCommitMessage.BLANK)
                .build();

        assertSoftly(softly -> {
            softly.assertThat(payload.squashMergeCommitTitle()).isEqualTo(GitHubSquashMergeCommitTitle.PR_TITLE);
            softly.assertThat(payload.squashMergeCommitMessage()).isEqualTo(GitHubSquashMergeCommitMessage.PR_BODY);
            softly.assertThat(payload.mergeCommitTitle()).isEqualTo(GitHubMergeCommitTitle.MERGE_MESSAGE);
            softly.assertThat(payload.mergeCommitMessage()).isEqualTo(GitHubMergeCommitMessage.BLANK);
        });
    }

    @Test
    void constructor_shouldThrow_whenNameNull() {
        final Throwable thrown = catchThrowable(
                () -> GitHubCreateInOrgPayloadBuilder.builder().name(null).build());

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("name");
    }

    @Test
    void constructor_shouldLeaveCustomPropertiesNull_whenCustomPropertiesNull() {
        final GitHubCreateInOrgPayload payload =
                GitHubCreateInOrgPayloadBuilder.builder().name("repo").build();

        assertThat(payload.customProperties()).isNull();
    }

    @Test
    void constructor_shouldCopyCustomProperties_whenCustomPropertiesProvided() {
        final GitHubCreateInOrgPayload payload = GitHubCreateInOrgPayloadBuilder.builder()
                .name("repo")
                .customProperties(Map.of("team", "core"))
                .build();

        assertThat(payload.customProperties()).containsEntry("team", "core");
    }
}
