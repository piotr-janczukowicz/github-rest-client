package io.github.piotr_janczukowicz.github.api.issues;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.json.OptionalNull;
import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubUpdateIssueTypeTest {

    @Test
    void ofName_shouldStoreValue_whenCalled() {
        final GitHubUpdateIssueType type = GitHubUpdateIssueType.ofName("bug");

        assertThat(type.typeName()).isEqualTo("bug");
    }

    @Test
    void ofName_shouldThrow_whenNameNull() {
        final Throwable thrown = catchThrowable(() -> GitHubUpdateIssueType.ofName(null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("name");
    }

    @Test
    void ofDetails_shouldStoreValue_whenCalled() {
        final GitHubUpdateIssueType.Details details =
                new GitHubUpdateIssueType.Details(OptionalNull.of("bug"), null, true, GitHubIssueUpdateConfidence.LOW);

        final GitHubUpdateIssueType type = GitHubUpdateIssueType.ofDetails(details);

        assertThat(type.typeDetails()).isEqualTo(details);
    }

    @Test
    void ofDetails_shouldThrow_whenDetailsNull() {
        final Throwable thrown = catchThrowable(() -> GitHubUpdateIssueType.ofDetails(null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("details");
    }

    @Test
    void constructor_shouldThrow_whenNoBranchSet() {
        final Throwable thrown = catchThrowable(() -> new GitHubUpdateIssueType(null, null));

        assertThat(thrown)
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("exactly one of typeName or typeDetails is required");
    }

    @Test
    void constructor_shouldThrow_whenBothBranchesSet() {
        final GitHubUpdateIssueType.Details details = new GitHubUpdateIssueType.Details(null, null, null, null);

        final Throwable thrown = catchThrowable(() -> new GitHubUpdateIssueType("bug", details));

        assertThat(thrown)
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("exactly one of typeName or typeDetails is required");
    }
}
