package io.github.piotr_janczukowicz.github.api.releases;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubCreateReleasePayloadTest {

    @Test
    void constructor_shouldThrow_whenTagNameNull() {
        final GitHubCreateReleasePayloadBuilder builder =
                GitHubCreateReleasePayloadBuilder.builder().tagName(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("tagName");
    }
}
