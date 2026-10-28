package io.github.piotr_janczukowicz.github.api.checks;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubCheckSuiteTest {

    @Test
    void constructor_shouldThrow_whenNodeIdNull() {
        final Throwable thrown =
                catchThrowable(() -> GitHubCheckSuiteBuilder.builder(ChecksCatalogFixtures.checkSuite())
                        .nodeId(null)
                        .build());

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("nodeId");
    }

    @Test
    void constructor_shouldThrow_whenHeadShaNull() {
        final Throwable thrown =
                catchThrowable(() -> GitHubCheckSuiteBuilder.builder(ChecksCatalogFixtures.checkSuite())
                        .headSha(null)
                        .build());

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("headSha");
    }

    @Test
    void constructor_shouldThrow_whenRepositoryNull() {
        final Throwable thrown =
                catchThrowable(() -> GitHubCheckSuiteBuilder.builder(ChecksCatalogFixtures.checkSuite())
                        .repository(null)
                        .build());

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("repository");
    }

    @Test
    void constructor_shouldThrow_whenHeadCommitNull() {
        final Throwable thrown =
                catchThrowable(() -> GitHubCheckSuiteBuilder.builder(ChecksCatalogFixtures.checkSuite())
                        .headCommit(null)
                        .build());

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("headCommit");
    }

    @Test
    void constructor_shouldThrow_whenCheckRunsUrlNull() {
        final Throwable thrown =
                catchThrowable(() -> GitHubCheckSuiteBuilder.builder(ChecksCatalogFixtures.checkSuite())
                        .checkRunsUrl(null)
                        .build());

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("checkRunsUrl");
    }

    @Test
    void constructor_shouldLeavePullRequestsNull_whenNull() {
        final GitHubCheckSuite suite = GitHubCheckSuiteBuilder.builder(ChecksCatalogFixtures.checkSuite())
                .pullRequests(null)
                .build();

        assertThat(suite.pullRequests()).isNull();
    }
}
