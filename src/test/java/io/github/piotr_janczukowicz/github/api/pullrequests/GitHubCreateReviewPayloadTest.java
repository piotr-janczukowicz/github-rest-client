package io.github.piotr_janczukowicz.github.api.pullrequests;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.List;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubCreateReviewPayloadTest {

    @Test
    void constructor_shouldLeaveCommentsNull_whenCommentsNull() {
        final GitHubCreateReviewPayload payload = new GitHubCreateReviewPayload(null, "body", null, null);

        assertThat(payload.comments()).isNull();
    }

    @Test
    void constructor_shouldCopyComments_whenCommentsPresent() {
        final GitHubCreateReviewPayload payload = new GitHubCreateReviewPayload(
                null, "body", null, List.of(PullrequestsCatalogFixtures.createReviewCommentItemPayload()));

        assertSoftly(softly -> {
            softly.assertThat(payload.comments()).hasSize(1);
            softly.assertThat(payload.comments().getFirst().path()).isEqualTo("file.md");
        });
    }
}
