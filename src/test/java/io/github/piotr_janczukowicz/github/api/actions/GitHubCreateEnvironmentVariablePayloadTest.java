package io.github.piotr_janczukowicz.github.api.actions;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubCreateEnvironmentVariablePayloadTest {

    @Test
    void constructor_shouldThrowNullPointerException_whenNameNull() {
        final String value = "value-sample";

        final Throwable thrown = catchThrowable(() -> new GitHubCreateEnvironmentVariablePayload(null, value));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("name");
    }

    @Test
    void constructor_shouldThrowNullPointerException_whenValueNull() {
        final String name = "name-sample";

        final Throwable thrown = catchThrowable(() -> new GitHubCreateEnvironmentVariablePayload(name, null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("value");
    }
}
