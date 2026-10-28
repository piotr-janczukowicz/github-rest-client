package io.github.piotr_janczukowicz.github.api.pages;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

import io.github.piotr_janczukowicz.github.api.pages.GitHubUpdatePagesSiteSource.BranchAndPath;
import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubUpdatePagesSiteSourceTest {

    @Test
    void constructor_shouldThrow_whenBothBranchesSet() {
        final BranchAndPath branchAndPath = new BranchAndPath("main", GitHubPagesSourcePath.DOCS);

        final Throwable thrown =
                catchThrowable(() -> new GitHubUpdatePagesSiteSource(GitHubPagesSourcePreset.GH_PAGES, branchAndPath));

        assertThat(thrown)
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("exactly one of preset or branchAndPath is required");
    }

    @Test
    void constructor_shouldThrow_whenNeitherBranchSet() {
        final Throwable thrown = catchThrowable(() -> new GitHubUpdatePagesSiteSource(null, null));

        assertThat(thrown)
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("exactly one of preset or branchAndPath is required");
    }

    @Test
    void constructor_shouldThrow_whenBranchNull() {
        final Throwable thrown = catchThrowable(() -> new BranchAndPath(null, GitHubPagesSourcePath.ROOT));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("branch");
    }

    @Test
    void constructor_shouldThrow_whenPathNull() {
        final Throwable thrown = catchThrowable(() -> new BranchAndPath("main", null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("path");
    }

    @Test
    void preset_shouldThrow_whenPresetNull() {
        final Throwable thrown = catchThrowable(() -> GitHubUpdatePagesSiteSource.preset(null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("preset");
    }

    @Test
    void branchAndPath_shouldThrow_whenBranchNull() {
        final Throwable thrown =
                catchThrowable(() -> GitHubUpdatePagesSiteSource.branchAndPath(null, GitHubPagesSourcePath.ROOT));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("branch");
    }

    @Test
    void branchAndPath_shouldThrow_whenPathNull() {
        final Throwable thrown = catchThrowable(() -> GitHubUpdatePagesSiteSource.branchAndPath("main", null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("path");
    }

    @Test
    void preset_shouldReturnPresetBranch_whenPresetProvided() {
        final GitHubUpdatePagesSiteSource source = GitHubUpdatePagesSiteSource.preset(GitHubPagesSourcePreset.GH_PAGES);

        assertSoftly(softly -> {
            softly.assertThat(source.preset()).isEqualTo(GitHubPagesSourcePreset.GH_PAGES);
            softly.assertThat(source.branchAndPath()).isNull();
        });
    }

    @Test
    void branchAndPath_shouldReturnBranchAndPath_whenProvided() {
        final GitHubUpdatePagesSiteSource source =
                GitHubUpdatePagesSiteSource.branchAndPath("main", GitHubPagesSourcePath.DOCS);

        assertSoftly(softly -> {
            softly.assertThat(source.preset()).isNull();
            softly.assertThat(source.branchAndPath().branch()).isEqualTo("main");
            softly.assertThat(source.branchAndPath().path()).isEqualTo(GitHubPagesSourcePath.DOCS);
        });
    }

    @Test
    void jsonValue_shouldReturnPresetWireValue_whenPresetSet() {
        final Object jsonValue = GitHubUpdatePagesSiteSource.preset(GitHubPagesSourcePreset.MASTER)
                .jsonValue();

        assertThat(jsonValue).isEqualTo("master");
    }

    @Test
    void jsonValue_shouldReturnBranchAndPath_whenBranchAndPathSet() {
        final Object jsonValue = GitHubUpdatePagesSiteSource.branchAndPath("main", GitHubPagesSourcePath.ROOT)
                .jsonValue();

        assertThat(jsonValue).isInstanceOf(BranchAndPath.class);
    }

    @Test
    void fromJson_shouldReturnPresetSource_whenStringProvided() {
        final GitHubUpdatePagesSiteSource source = GitHubUpdatePagesSiteSource.fromJson("gh-pages");

        assertThat(source.preset()).isEqualTo(GitHubPagesSourcePreset.GH_PAGES);
    }

    @Test
    void fromJson_shouldReturnBranchAndPathSource_whenObjectProvided() {
        final BranchAndPath branchAndPath = new BranchAndPath("main", GitHubPagesSourcePath.DOCS);

        final GitHubUpdatePagesSiteSource source = GitHubUpdatePagesSiteSource.fromJson(branchAndPath);

        assertThat(source.branchAndPath()).isEqualTo(branchAndPath);
    }
}
