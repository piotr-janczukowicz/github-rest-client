package io.github.piotr_janczukowicz.github.api.issues;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubSetLabelsLabelTest {

    @Test
    void ofName_shouldStoreValue_whenCalled() {
        final GitHubSetLabelsLabel label = GitHubSetLabelsLabel.ofName("bug");

        assertThat(label.labelName()).isEqualTo("bug");
    }

    @Test
    void ofName_shouldThrow_whenNameNull() {
        final Throwable thrown = catchThrowable(() -> GitHubSetLabelsLabel.ofName(null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("name");
    }

    @Test
    void ofDetails_shouldStoreValue_whenCalled() {
        final GitHubSetLabelsLabel.Details details = new GitHubSetLabelsLabel.Details("bug");

        final GitHubSetLabelsLabel label = GitHubSetLabelsLabel.ofDetails(details);

        assertThat(label.labelDetails()).isEqualTo(details);
    }

    @Test
    void ofDetails_shouldThrow_whenDetailsNull() {
        final Throwable thrown = catchThrowable(() -> GitHubSetLabelsLabel.ofDetails(null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("details");
    }

    @Test
    void constructor_shouldThrow_whenNoBranchSet() {
        final Throwable thrown = catchThrowable(() -> new GitHubSetLabelsLabel(null, null));

        assertThat(thrown)
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("exactly one of labelName or labelDetails is required");
    }

    @Test
    void constructor_shouldThrow_whenBothBranchesSet() {
        final GitHubSetLabelsLabel.Details details = new GitHubSetLabelsLabel.Details("bug");

        final Throwable thrown = catchThrowable(() -> new GitHubSetLabelsLabel("bug", details));

        assertThat(thrown)
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("exactly one of labelName or labelDetails is required");
    }

    @Test
    void detailsConstructor_shouldThrow_whenNameNull() {
        final Throwable thrown = catchThrowable(() -> new GitHubSetLabelsLabel.Details(null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("name");
    }
}
