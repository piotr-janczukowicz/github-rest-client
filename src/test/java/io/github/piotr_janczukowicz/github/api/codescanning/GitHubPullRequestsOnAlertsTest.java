package io.github.piotr_janczukowicz.github.api.codescanning;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubPullRequestsOnAlertsTest {

    @Test
    void constructor_shouldThrow_whenRepositoryNull() {
        final GitHubPullRequestsOnAlertsBuilder builder = GitHubPullRequestsOnAlertsBuilder.builder(
                        CodeScanningCatalogFixtures.pullRequestsOnAlerts())
                .repository(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("repository");
    }

    @Test
    void constructor_shouldThrow_whenTitleNull() {
        final GitHubPullRequestsOnAlertsBuilder builder = GitHubPullRequestsOnAlertsBuilder.builder(
                        CodeScanningCatalogFixtures.pullRequestsOnAlerts())
                .title(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("title");
    }

    @Test
    void constructor_shouldThrow_whenStateNull() {
        final GitHubPullRequestsOnAlertsBuilder builder = GitHubPullRequestsOnAlertsBuilder.builder(
                        CodeScanningCatalogFixtures.pullRequestsOnAlerts())
                .state(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("state");
    }

    @Test
    void constructor_shouldThrow_whenHtmlUrlNull() {
        final GitHubPullRequestsOnAlertsBuilder builder = GitHubPullRequestsOnAlertsBuilder.builder(
                        CodeScanningCatalogFixtures.pullRequestsOnAlerts())
                .htmlUrl(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("htmlUrl");
    }

    @Test
    void constructor_shouldThrow_whenUrlNull() {
        final GitHubPullRequestsOnAlertsBuilder builder = GitHubPullRequestsOnAlertsBuilder.builder(
                        CodeScanningCatalogFixtures.pullRequestsOnAlerts())
                .url(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("url");
    }
}
