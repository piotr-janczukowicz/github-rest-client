package io.github.piotr_janczukowicz.github.api.markdown;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubRenderMarkdownPayloadTest {

    @Test
    void constructor_shouldThrow_whenTextNull() {
        final GitHubRenderMarkdownPayloadBuilder builder =
                GitHubRenderMarkdownPayloadBuilder.builder().text(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("text");
    }
}
