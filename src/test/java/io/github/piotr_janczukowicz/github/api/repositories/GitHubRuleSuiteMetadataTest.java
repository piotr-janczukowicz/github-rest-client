package io.github.piotr_janczukowicz.github.api.repositories;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.List;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubRuleSuiteMetadataTest {

    @Test
    void constructor_shouldStorePullRequestOnly_whenPullRequestSet() {
        final GitHubRuleSuitePullRequestMetadata pullRequest =
                RepositoriesCatalogFixtures.ruleSuitePullRequestMetadata();

        final GitHubRuleSuiteMetadata metadata = new GitHubRuleSuiteMetadata(pullRequest, null);

        assertSoftly(softly -> {
            softly.assertThat(metadata.pullRequest()).isEqualTo(pullRequest);
            softly.assertThat(metadata.checks()).isNull();
        });
    }

    @Test
    void constructor_shouldAllowNoBranch_whenEmptyMetadata() {
        final GitHubRuleSuiteMetadata metadata = new GitHubRuleSuiteMetadata(null, null);

        assertSoftly(softly -> {
            softly.assertThat(metadata.pullRequest()).isNull();
            softly.assertThat(metadata.checks()).isNull();
        });
    }

    @Test
    void constructor_shouldThrow_whenBothBranchesSet() {
        final GitHubRuleSuitePullRequestMetadata pullRequest =
                RepositoriesCatalogFixtures.ruleSuitePullRequestMetadata();
        final List<GitHubRuleSuiteCheck> checks = List.of(RepositoriesCatalogFixtures.ruleSuiteCheck());

        final Throwable thrown = catchThrowable(() -> new GitHubRuleSuiteMetadata(pullRequest, checks));

        assertThat(thrown)
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("at most one of pullRequest or checks is allowed");
    }
}
