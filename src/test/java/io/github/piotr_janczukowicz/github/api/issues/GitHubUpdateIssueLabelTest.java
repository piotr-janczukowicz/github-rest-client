package io.github.piotr_janczukowicz.github.api.issues;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubUpdateIssueLabelTest {

    @Test
    void ofName_shouldStoreValue_whenCalled() {
        final GitHubUpdateIssueLabel label = GitHubUpdateIssueLabel.ofName("bug");

        assertThat(label.labelName()).isEqualTo("bug");
    }

    @Test
    void ofName_shouldThrow_whenNameNull() {
        final Throwable thrown = catchThrowable(() -> GitHubUpdateIssueLabel.ofName(null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("name");
    }

    @Test
    void ofDetails_shouldStoreValue_whenCalled() {
        final GitHubUpdateIssueLabel.Details details = new GitHubUpdateIssueLabel.Details(
                null, "bug", null, null, null, true, GitHubIssueUpdateConfidence.HIGH);

        final GitHubUpdateIssueLabel label = GitHubUpdateIssueLabel.ofDetails(details);

        assertThat(label.labelDetails()).isEqualTo(details);
    }

    @Test
    void ofDetails_shouldThrow_whenDetailsNull() {
        final Throwable thrown = catchThrowable(() -> GitHubUpdateIssueLabel.ofDetails(null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("details");
    }

    @Test
    void constructor_shouldThrow_whenNoBranchSet() {
        final Throwable thrown = catchThrowable(() -> new GitHubUpdateIssueLabel(null, null));

        assertThat(thrown)
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("exactly one of labelName or labelDetails is required");
    }

    @Test
    void constructor_shouldThrow_whenBothBranchesSet() {
        final GitHubUpdateIssueLabel.Details details =
                new GitHubUpdateIssueLabel.Details(null, "bug", null, null, null, null, null);

        final Throwable thrown = catchThrowable(() -> new GitHubUpdateIssueLabel("bug", details));

        assertThat(thrown)
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("exactly one of labelName or labelDetails is required");
    }
}
