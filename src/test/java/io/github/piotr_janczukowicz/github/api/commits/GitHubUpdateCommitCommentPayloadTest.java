package io.github.piotr_janczukowicz.github.api.commits;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubUpdateCommitCommentPayloadTest {

    @Test
    void constructor_shouldThrow_whenBodyNull() {
        final Throwable thrown = catchThrowable(() ->
                GitHubUpdateCommitCommentPayloadBuilder.builder().body(null).build());

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("body");
    }
}
