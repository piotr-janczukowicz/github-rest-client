package io.github.piotr_janczukowicz.github.api.deployments;

import static org.assertj.core.api.SoftAssertions.assertSoftly;

import io.github.piotr_janczukowicz.github.api.users.UsersCatalogFixtures;
import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubEnvironmentReviewerEntryTest {

    @Test
    void constructor_shouldStoreFields_whenCalled() {
        final GitHubEnvironmentReviewer reviewer = GitHubEnvironmentReviewer.ofUser(UsersCatalogFixtures.octocat());
        final GitHubEnvironmentReviewerEntry entry =
                new GitHubEnvironmentReviewerEntry(GitHubDeploymentReviewerType.TEAM, reviewer);

        assertSoftly(softly -> {
            softly.assertThat(entry.type()).isEqualTo(GitHubDeploymentReviewerType.TEAM);
            softly.assertThat(entry.reviewer()).isEqualTo(reviewer);
        });
    }

    @Test
    void constructor_shouldAllowNullReviewer_whenAbsent() {
        final GitHubEnvironmentReviewerEntry entry =
                new GitHubEnvironmentReviewerEntry(GitHubDeploymentReviewerType.USER, null);

        assertSoftly(softly -> {
            softly.assertThat(entry.type()).isEqualTo(GitHubDeploymentReviewerType.USER);
            softly.assertThat(entry.reviewer()).isNull();
        });
    }
}
