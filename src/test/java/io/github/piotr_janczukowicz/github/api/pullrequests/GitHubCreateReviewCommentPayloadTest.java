package io.github.piotr_janczukowicz.github.api.pullrequests;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubCreateReviewCommentPayloadTest {

    @Test
    void constructor_shouldThrow_whenBodyBlank() {
        final Throwable thrown = catchThrowable(() -> GitHubCreateReviewCommentPayloadBuilder.builder()
                .body(" ")
                .commitId("6dcb09b5b57875f334f61aebed695e2e4193db5e")
                .path("file1.txt")
                .build());

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("body");
    }

    @Test
    void constructor_shouldThrow_whenCommitIdBlank() {
        final Throwable thrown = catchThrowable(() -> GitHubCreateReviewCommentPayloadBuilder.builder()
                .body("Great stuff!")
                .commitId(" ")
                .path("file1.txt")
                .build());

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("commitId");
    }

    @Test
    void constructor_shouldThrow_whenPathBlank() {
        final Throwable thrown = catchThrowable(() -> GitHubCreateReviewCommentPayloadBuilder.builder()
                .body("Great stuff!")
                .commitId("6dcb09b5b57875f334f61aebed695e2e4193db5e")
                .path(" ")
                .build());

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("path");
    }
}
