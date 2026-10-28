package io.github.piotr_janczukowicz.github.api.issues;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubIssueMilestoneReferenceTest {

    @Test
    void ofNumber_shouldStoreValue_whenCalled() {
        final GitHubIssueMilestoneReference reference = GitHubIssueMilestoneReference.ofNumber(3L);

        assertThat(reference.numericId()).isEqualTo(3L);
    }

    @Test
    void ofTitle_shouldStoreValue_whenCalled() {
        final GitHubIssueMilestoneReference reference = GitHubIssueMilestoneReference.ofTitle("v1.0");

        assertThat(reference.stringId()).isEqualTo("v1.0");
    }

    @Test
    void ofTitle_shouldStoreValue_whenTitleBlank() {
        final GitHubIssueMilestoneReference reference = GitHubIssueMilestoneReference.ofTitle(" ");

        assertThat(reference.stringId()).isEqualTo(" ");
    }

    @Test
    void ofTitle_shouldThrow_whenTitleNull() {
        final Throwable thrown = catchThrowable(() -> GitHubIssueMilestoneReference.ofTitle(null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("title");
    }

    @Test
    void constructor_shouldThrow_whenNoBranchSet() {
        final Throwable thrown = catchThrowable(() -> new GitHubIssueMilestoneReference(null, null));

        assertThat(thrown)
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("exactly one of numericId or stringId is required");
    }

    @Test
    void constructor_shouldThrow_whenBothBranchesSet() {
        final Throwable thrown = catchThrowable(() -> new GitHubIssueMilestoneReference(1L, "v1.0"));

        assertThat(thrown)
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("exactly one of numericId or stringId is required");
    }

    @Test
    void jsonValue_shouldReturnNumber_whenNumericIdSet() {
        final Object jsonValue = GitHubIssueMilestoneReference.ofNumber(3L).jsonValue();

        assertThat(jsonValue).isEqualTo(3L);
    }

    @Test
    void jsonValue_shouldReturnString_whenStringIdSet() {
        final Object jsonValue = GitHubIssueMilestoneReference.ofTitle("v1.0").jsonValue();

        assertThat(jsonValue).isEqualTo("v1.0");
    }

    @Test
    void fromJson_shouldReturnNumberReference_whenNumberProvided() {
        final GitHubIssueMilestoneReference reference = GitHubIssueMilestoneReference.fromJson(3);

        assertThat(reference.numericId()).isEqualTo(3L);
    }

    @Test
    void fromJson_shouldReturnTitleReference_whenStringProvided() {
        final GitHubIssueMilestoneReference reference = GitHubIssueMilestoneReference.fromJson("v1.0");

        assertThat(reference.stringId()).isEqualTo("v1.0");
    }
}
