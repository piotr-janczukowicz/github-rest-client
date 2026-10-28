package io.github.piotr_janczukowicz.github.api.commits;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubCreateCommitStatusPayloadTest {

    @Test
    void constructor_shouldThrow_whenStateNull() {
        final Throwable thrown = catchThrowable(() ->
                GitHubCreateCommitStatusPayloadBuilder.builder().state(null).build());

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("state");
    }
}
