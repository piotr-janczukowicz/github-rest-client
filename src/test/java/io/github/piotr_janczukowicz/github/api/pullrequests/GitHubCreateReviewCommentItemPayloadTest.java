package io.github.piotr_janczukowicz.github.api.pullrequests;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubCreateReviewCommentItemPayloadTest {

    @Test
    void constructor_shouldThrow_whenPathBlank() {
        final Throwable thrown = catchThrowable(
                () -> new GitHubCreateReviewCommentItemPayload(" ", "body", null, null, null, null, null));

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("path");
    }

    @Test
    void constructor_shouldThrow_whenBodyBlank() {
        final Throwable thrown = catchThrowable(
                () -> new GitHubCreateReviewCommentItemPayload("file.md", " ", null, null, null, null, null));

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("body");
    }

    @Test
    void constructor_shouldAcceptValues_whenRequiredFieldsPresent() {
        final GitHubCreateReviewCommentItemPayload item =
                new GitHubCreateReviewCommentItemPayload("file.md", "Please fix", 6, null, null, null, null);

        assertSoftly(softly -> {
            softly.assertThat(item.path()).isEqualTo("file.md");
            softly.assertThat(item.body()).isEqualTo("Please fix");
            softly.assertThat(item.position()).isEqualTo(6);
        });
    }
}
