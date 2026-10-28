package io.github.piotr_janczukowicz.github.api.branches;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubMergeBranchPayloadTest {

    @Test
    void constructor_shouldThrow_whenBaseNull() {
        final Throwable thrown = catchThrowable(() -> new GitHubMergeBranchPayload(null, "head", null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("base");
    }

    @Test
    void constructor_shouldThrow_whenHeadNull() {
        final Throwable thrown = catchThrowable(() -> new GitHubMergeBranchPayload("base", null, null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("head");
    }
}
