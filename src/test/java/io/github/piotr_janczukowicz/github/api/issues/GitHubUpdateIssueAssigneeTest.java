package io.github.piotr_janczukowicz.github.api.issues;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubUpdateIssueAssigneeTest {

    @Test
    void ofLogin_shouldStoreValue_whenCalled() {
        final GitHubUpdateIssueAssignee assignee = GitHubUpdateIssueAssignee.ofLogin("octocat");

        assertThat(assignee.assigneeLogin()).isEqualTo("octocat");
    }

    @Test
    void ofLogin_shouldThrow_whenLoginNull() {
        final Throwable thrown = catchThrowable(() -> GitHubUpdateIssueAssignee.ofLogin(null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("login");
    }

    @Test
    void ofDetails_shouldStoreValue_whenCalled() {
        final GitHubUpdateIssueAssignee.Details details =
                new GitHubUpdateIssueAssignee.Details("octocat", null, null, GitHubIssueUpdateConfidence.MEDIUM);

        final GitHubUpdateIssueAssignee assignee = GitHubUpdateIssueAssignee.ofDetails(details);

        assertThat(assignee.assigneeDetails()).isEqualTo(details);
    }

    @Test
    void ofDetails_shouldThrow_whenDetailsNull() {
        final Throwable thrown = catchThrowable(() -> GitHubUpdateIssueAssignee.ofDetails(null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("details");
    }

    @Test
    void constructor_shouldThrow_whenNoBranchSet() {
        final Throwable thrown = catchThrowable(() -> new GitHubUpdateIssueAssignee(null, null));

        assertThat(thrown)
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("exactly one of assigneeLogin or assigneeDetails is required");
    }

    @Test
    void constructor_shouldThrow_whenBothBranchesSet() {
        final GitHubUpdateIssueAssignee.Details details =
                new GitHubUpdateIssueAssignee.Details("octocat", null, null, null);

        final Throwable thrown = catchThrowable(() -> new GitHubUpdateIssueAssignee("octocat", details));

        assertThat(thrown)
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("exactly one of assigneeLogin or assigneeDetails is required");
    }
}
