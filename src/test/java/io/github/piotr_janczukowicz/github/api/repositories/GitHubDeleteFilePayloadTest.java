package io.github.piotr_janczukowicz.github.api.repositories;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubDeleteFilePayloadTest {

    @Test
    void constructor_shouldThrow_whenMessageNull() {
        final Throwable thrown = catchThrowable(() -> GitHubDeleteFilePayloadBuilder.builder()
                .message(null)
                .sha("abc")
                .build());

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("message");
    }

    @Test
    void constructor_shouldThrow_whenShaNull() {
        final Throwable thrown = catchThrowable(() -> GitHubDeleteFilePayloadBuilder.builder()
                .message("message")
                .sha(null)
                .build());

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("sha");
    }
}
