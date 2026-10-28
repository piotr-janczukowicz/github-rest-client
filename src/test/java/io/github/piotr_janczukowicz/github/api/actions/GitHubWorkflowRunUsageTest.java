package io.github.piotr_janczukowicz.github.api.actions;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubWorkflowRunUsageTest {

    @Test
    void constructor_shouldThrowNullPointerException_whenBillableNull() {
        final Integer runDurationMs = 1;

        final Throwable thrown = catchThrowable(() -> new GitHubWorkflowRunUsage(null, runDurationMs));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("billable");
    }
}
