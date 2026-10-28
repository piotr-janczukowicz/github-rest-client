package io.github.piotr_janczukowicz.github.api.deployments;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.github.api.users.UsersCatalogFixtures;
import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.List;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubEnvironmentProtectionRuleTest {

    @Test
    void constructor_shouldCopyReviewers_whenPresent() {
        final GitHubEnvironmentReviewerEntry reviewer = new GitHubEnvironmentReviewerEntry(
                GitHubDeploymentReviewerType.USER, GitHubEnvironmentReviewer.ofUser(UsersCatalogFixtures.octocat()));
        final GitHubEnvironmentProtectionRule rule =
                new GitHubEnvironmentProtectionRule(1, "n", "required_reviewers", null, false, List.of(reviewer));

        assertThat(rule.reviewers()).containsExactly(reviewer);
    }

    @Test
    void constructor_shouldLeaveReviewersNull_whenNull() {
        final GitHubEnvironmentProtectionRule rule = GitHubEnvironmentProtectionRuleBuilder.builder(
                        DeploymentsCatalogFixtures.environmentProtectionRule())
                .reviewers(null)
                .build();

        assertThat(rule.reviewers()).isNull();
    }

    @Test
    void constructor_shouldThrow_whenNodeIdNull() {
        final Throwable thrown =
                catchThrowable(() -> new GitHubEnvironmentProtectionRule(1, null, "t", null, null, null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("nodeId");
    }

    @Test
    void constructor_shouldThrow_whenTypeNull() {
        final Throwable thrown =
                catchThrowable(() -> new GitHubEnvironmentProtectionRule(1, "n", null, null, null, null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("type");
    }
}
