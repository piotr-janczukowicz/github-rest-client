package io.github.piotr_janczukowicz.github.api.issues;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubAddAssigneesAssigneeTest {

    @Test
    void ofLogin_shouldStoreValue_whenCalled() {
        final GitHubAddAssigneesAssignee assignee = GitHubAddAssigneesAssignee.ofLogin("octocat");

        assertThat(assignee.assigneeLogin()).isEqualTo("octocat");
    }

    @Test
    void ofLogin_shouldThrow_whenLoginNull() {
        final Throwable thrown = catchThrowable(() -> GitHubAddAssigneesAssignee.ofLogin(null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("login");
    }

    @Test
    void ofDetails_shouldStoreValue_whenCalled() {
        final GitHubAddAssigneesAssignee.Details details =
                new GitHubAddAssigneesAssignee.Details("octocat", null, null, GitHubIssueUpdateConfidence.MEDIUM);

        final GitHubAddAssigneesAssignee assignee = GitHubAddAssigneesAssignee.ofDetails(details);

        assertThat(assignee.assigneeDetails()).isEqualTo(details);
    }

    @Test
    void constructor_shouldThrow_whenNoBranchSet() {
        final Throwable thrown = catchThrowable(() -> new GitHubAddAssigneesAssignee(null, null));

        assertThat(thrown)
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("assigneeLogin or assigneeDetails must have exactly one value present");
    }
}
