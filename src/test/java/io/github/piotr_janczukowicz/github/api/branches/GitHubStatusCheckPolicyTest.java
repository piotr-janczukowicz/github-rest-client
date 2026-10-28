package io.github.piotr_janczukowicz.github.api.branches;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.List;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubStatusCheckPolicyTest {

    @Test
    void constructor_shouldThrow_whenUrlNull() {
        final Throwable thrown = catchThrowable(
                () -> new GitHubStatusCheckPolicy(null, true, List.of(), List.of(), "https://example.com/contexts"));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("url");
    }

    @Test
    void constructor_shouldThrow_whenContextsNull() {
        final Throwable thrown = catchThrowable(() -> new GitHubStatusCheckPolicy(
                "https://example.com/url", true, null, List.of(), "https://example.com/contexts"));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("contexts");
    }
}
