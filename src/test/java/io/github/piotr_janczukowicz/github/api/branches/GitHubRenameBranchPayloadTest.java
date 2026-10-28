package io.github.piotr_janczukowicz.github.api.branches;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubRenameBranchPayloadTest {

    @Test
    void constructor_shouldThrow_whenNewNameNull() {
        final Throwable thrown = catchThrowable(
                () -> GitHubRenameBranchPayloadBuilder.builder().newName(null).build());

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("newName");
    }
}
