package io.github.piotr_janczukowicz.github.api.actions;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubReferencedWorkflowTest {

    @Test
    void constructor_shouldThrowNullPointerException_whenPathNull() {
        final String sha = "sha-sample";
        final String ref = "ref-sample";

        final Throwable thrown = catchThrowable(() -> new GitHubReferencedWorkflow(null, sha, ref));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("path");
    }

    @Test
    void constructor_shouldThrowNullPointerException_whenShaNull() {
        final String path = "path-sample";
        final String ref = "ref-sample";

        final Throwable thrown = catchThrowable(() -> new GitHubReferencedWorkflow(path, null, ref));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("sha");
    }
}
