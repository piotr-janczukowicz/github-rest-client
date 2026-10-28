package io.github.piotr_janczukowicz.github.api.repositories;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubCreateOrUpdateFileContentsPayloadTest {

    @Test
    void constructor_shouldThrow_whenMessageNull() {
        final GitHubCreateOrUpdateFileContentsPayloadBuilder builder =
                GitHubCreateOrUpdateFileContentsPayloadBuilder.builder()
                        .message(null)
                        .content("content");

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("message");
    }

    @Test
    void constructor_shouldThrow_whenContentNull() {
        final GitHubCreateOrUpdateFileContentsPayloadBuilder builder =
                GitHubCreateOrUpdateFileContentsPayloadBuilder.builder()
                        .message("message")
                        .content(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("content");
    }
}
