package io.github.piotr_janczukowicz.github.api.actions;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubWorkflowDispatchResponseTest {

    @Test
    void constructor_shouldThrowNullPointerException_whenWorkflowRunIdNull() {
        final String runUrl = "https://api.github.com/example";
        final String htmlUrl = "https://github.com/example";

        final Throwable thrown = catchThrowable(() -> new GitHubWorkflowDispatchResponse(null, runUrl, htmlUrl));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("workflowRunId");
    }

    @Test
    void constructor_shouldThrowNullPointerException_whenRunUrlNull() {
        final GitHubWorkflowRunId workflowRunId = ActionsCatalogFixtures.workflowRunId();
        final String htmlUrl = "https://github.com/example";

        final Throwable thrown = catchThrowable(() -> new GitHubWorkflowDispatchResponse(workflowRunId, null, htmlUrl));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("runUrl");
    }

    @Test
    void constructor_shouldThrowNullPointerException_whenHtmlUrlNull() {
        final GitHubWorkflowRunId workflowRunId = ActionsCatalogFixtures.workflowRunId();
        final String runUrl = "https://api.github.com/example";

        final Throwable thrown = catchThrowable(() -> new GitHubWorkflowDispatchResponse(workflowRunId, runUrl, null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("htmlUrl");
    }
}
