package io.github.piotr_janczukowicz.github.api.checks;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubCreateSuitePayloadTest {

    @Test
    void constructor_shouldThrow_whenHeadShaNull() {
        final Throwable thrown = catchThrowable(() -> new GitHubCreateSuitePayload(null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("headSha");
    }
}
