package io.github.piotr_janczukowicz.github.api.commits;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.github.api.repositories.GitHubCommit;
import io.github.piotr_janczukowicz.github.api.repositories.RepositoriesCatalogFixtures;
import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.List;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubCommitComparisonTest {

    @Test
    void constructor_shouldThrow_whenCommitsNull() {
        final Throwable thrown = catchThrowable(() -> new GitHubCommitComparison(
                "https://example.com/url",
                "https://example.com/html",
                "https://example.com/permalink",
                "https://example.com/diff",
                "https://example.com/patch",
                sampleCommit(),
                sampleCommit(),
                GitHubCommitComparisonStatus.AHEAD,
                1,
                2,
                3,
                null,
                null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("commits");
    }

    @Test
    void constructor_shouldKeepNullFiles_whenFilesNull() {
        final GitHubCommitComparison comparison = new GitHubCommitComparison(
                "https://example.com/url",
                "https://example.com/html",
                "https://example.com/permalink",
                "https://example.com/diff",
                "https://example.com/patch",
                sampleCommit(),
                sampleCommit(),
                GitHubCommitComparisonStatus.AHEAD,
                1,
                2,
                3,
                List.of(),
                null);

        assertThat(comparison.files()).isNull();
    }

    @Test
    void constructor_shouldCopyCommits_whenPresent() {
        final GitHubCommitComparison comparison = new GitHubCommitComparison(
                "https://example.com/url",
                "https://example.com/html",
                "https://example.com/permalink",
                "https://example.com/diff",
                "https://example.com/patch",
                sampleCommit(),
                sampleCommit(),
                GitHubCommitComparisonStatus.AHEAD,
                1,
                2,
                3,
                List.of(sampleCommit()),
                null);

        assertThat(comparison.commits()).hasSize(1);
    }

    private static GitHubCommit sampleCommit() {
        return RepositoriesCatalogFixtures.commit();
    }
}
