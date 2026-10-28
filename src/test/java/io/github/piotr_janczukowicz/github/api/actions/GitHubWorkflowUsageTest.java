package io.github.piotr_janczukowicz.github.api.actions;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubWorkflowUsageTest {

    @Test
    void constructor_shouldThrowNullPointerException_whenBillableNull() {
        final Throwable thrown = catchThrowable(() -> new GitHubWorkflowUsage(null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("billable");
    }
}
