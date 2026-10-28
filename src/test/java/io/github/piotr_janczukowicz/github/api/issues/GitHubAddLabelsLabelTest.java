package io.github.piotr_janczukowicz.github.api.issues;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubAddLabelsLabelTest {

    @Test
    void ofName_shouldStoreValue_whenCalled() {
        final GitHubAddLabelsLabel label = GitHubAddLabelsLabel.ofName("bug");

        assertThat(label.labelName()).isEqualTo("bug");
    }

    @Test
    void ofName_shouldThrow_whenNameNull() {
        final Throwable thrown = catchThrowable(() -> GitHubAddLabelsLabel.ofName(null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("name");
    }

    @Test
    void ofDetails_shouldStoreValue_whenCalled() {
        final GitHubAddLabelsLabel.Details details =
                new GitHubAddLabelsLabel.Details("bug", "reason", true, GitHubIssueUpdateConfidence.HIGH);

        final GitHubAddLabelsLabel label = GitHubAddLabelsLabel.ofDetails(details);

        assertThat(label.labelDetails()).isEqualTo(details);
    }

    @Test
    void ofDetails_shouldThrow_whenDetailsNull() {
        final Throwable thrown = catchThrowable(() -> GitHubAddLabelsLabel.ofDetails(null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("details");
    }

    @Test
    void constructor_shouldThrow_whenNoBranchSet() {
        final Throwable thrown = catchThrowable(() -> new GitHubAddLabelsLabel(null, null));

        assertThat(thrown)
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("labelName or labelDetails must have exactly one value present");
    }

    @Test
    void constructor_shouldThrow_whenBothBranchesSet() {
        final GitHubAddLabelsLabel.Details details = new GitHubAddLabelsLabel.Details("bug", null, null, null);

        final Throwable thrown = catchThrowable(() -> new GitHubAddLabelsLabel("bug", details));

        assertThat(thrown)
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("labelName or labelDetails must have exactly one value present");
    }

    @Test
    void detailsConstructor_shouldThrow_whenNameNull() {
        final Throwable thrown = catchThrowable(() -> new GitHubAddLabelsLabel.Details(null, null, null, null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("name");
    }
}
