package io.github.piotr_janczukowicz.github.api.branches;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.json.OptionalNull;
import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubStatusCheckPolicyCheckTest {

    @Test
    void constructor_shouldThrow_whenContextNull() {
        final Throwable thrown = catchThrowable(() -> new GitHubStatusCheckPolicyCheck(null, OptionalNull.of(1L)));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("context");
    }

    @Test
    void constructor_shouldThrow_whenAppIdNull() {
        final Throwable thrown = catchThrowable(() -> new GitHubStatusCheckPolicyCheck("ci", null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("appId");
    }
}
