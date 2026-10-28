package io.github.piotr_janczukowicz.github.api.repositories;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

@UnitTest
class GitHubRepositoryRuleMergeQueueParametersTest {

    @ParameterizedTest
    @CsvSource({
        "0, 5, 5, 1, 0, checkResponseTimeoutMinutes",
        "361, 5, 5, 1, 0, checkResponseTimeoutMinutes",
        "5, -1, 5, 1, 0, maxEntriesToBuild",
        "5, 101, 5, 1, 0, maxEntriesToBuild",
        "5, 5, -1, 1, 0, maxEntriesToMerge",
        "5, 5, 101, 1, 0, maxEntriesToMerge",
        "5, 5, 5, -1, 0, minEntriesToMerge",
        "5, 5, 5, 101, 0, minEntriesToMerge",
        "5, 5, 5, 1, -1, minEntriesToMergeWaitMinutes",
        "5, 5, 5, 1, 361, minEntriesToMergeWaitMinutes"
    })
    void constructor_shouldThrow_whenIntOutsideDumpBounds(
            final int timeout,
            final int build,
            final int merge,
            final int minMerge,
            final int wait,
            final String field) {
        final Throwable thrown = catchThrowable(() -> parameters(timeout, build, merge, minMerge, wait));

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining(field);
    }

    @Test
    void constructor_shouldAcceptFixture_whenInsideDumpBounds() {
        final GitHubRepositoryRuleMergeQueueParameters parameters = RepositoriesCatalogFixtures.mergeQueueParameters();

        assertThat(parameters.checkResponseTimeoutMinutes()).isEqualTo(5);
        assertThat(parameters.minEntriesToMergeWaitMinutes()).isZero();
    }

    @Test
    void constructor_shouldAcceptValues_whenAtDumpBounds() {
        assertThat(parameters(1, 0, 0, 0, 0).maxEntriesToBuild()).isZero();
        assertThat(parameters(360, 100, 100, 100, 360).checkResponseTimeoutMinutes())
                .isEqualTo(360);
    }

    private static GitHubRepositoryRuleMergeQueueParameters parameters(
            final int timeout, final int build, final int merge, final int minMerge, final int wait) {
        final GitHubRepositoryRuleMergeQueueParameters fixture = RepositoriesCatalogFixtures.mergeQueueParameters();
        return new GitHubRepositoryRuleMergeQueueParameters(
                timeout, fixture.groupingStrategy(), build, merge, fixture.mergeMethod(), minMerge, wait);
    }
}
