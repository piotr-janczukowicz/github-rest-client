package io.github.piotr_janczukowicz.github.api.issues;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubCreateIssueLabelTest {

    @Test
    void ofName_shouldStoreValue_whenCalled() {
        final GitHubCreateIssueLabel label = GitHubCreateIssueLabel.ofName("bug");

        assertThat(label.labelName()).isEqualTo("bug");
    }

    @Test
    void ofName_shouldThrow_whenNameNull() {
        final Throwable thrown = catchThrowable(() -> GitHubCreateIssueLabel.ofName(null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("name");
    }

    @Test
    void ofDetails_shouldStoreValue_whenCalled() {
        final GitHubCreateIssueLabel.Details details = new GitHubCreateIssueLabel.Details(1L, "bug", "bugs", "f29513");

        final GitHubCreateIssueLabel label = GitHubCreateIssueLabel.ofDetails(details);

        assertThat(label.labelDetails()).isEqualTo(details);
    }

    @Test
    void ofDetails_shouldThrow_whenDetailsNull() {
        final Throwable thrown = catchThrowable(() -> GitHubCreateIssueLabel.ofDetails(null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("details");
    }

    @Test
    void constructor_shouldThrow_whenNoBranchSet() {
        final Throwable thrown = catchThrowable(() -> new GitHubCreateIssueLabel(null, null));

        assertThat(thrown)
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("exactly one of labelName or labelDetails is required");
    }

    @Test
    void constructor_shouldThrow_whenBothBranchesSet() {
        final GitHubCreateIssueLabel.Details details = new GitHubCreateIssueLabel.Details(1L, "bug", null, null);

        final Throwable thrown = catchThrowable(() -> new GitHubCreateIssueLabel("bug", details));

        assertThat(thrown)
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("exactly one of labelName or labelDetails is required");
    }
}
