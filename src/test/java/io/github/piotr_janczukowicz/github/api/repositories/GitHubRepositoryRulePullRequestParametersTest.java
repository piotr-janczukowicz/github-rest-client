package io.github.piotr_janczukowicz.github.api.repositories;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubRepositoryRulePullRequestParametersTest {

    @Test
    void constructor_shouldLeaveAllowedMergeMethodsAndRequiredReviewersNull_whenBothNull() {
        final GitHubRepositoryRulePullRequestParameters parameters =
                new GitHubRepositoryRulePullRequestParameters(null, false, null, false, false, 0, false, null);

        assertSoftly(softly -> {
            softly.assertThat(parameters.allowedMergeMethods()).isNull();
            softly.assertThat(parameters.requiredReviewers()).isNull();
        });
    }

    @Test
    void constructor_shouldCopyLists_whenPresent() {
        final List<GitHubRepositoryRulePullRequestAllowedMergeMethod> methods =
                new ArrayList<>(List.of(GitHubRepositoryRulePullRequestAllowedMergeMethod.MERGE));
        final List<GitHubRepositoryRuleParamsRequiredReviewerConfiguration> reviewers =
                new ArrayList<>(List.of(new GitHubRepositoryRuleParamsRequiredReviewerConfiguration(
                        List.of("*.java"),
                        1,
                        new GitHubRepositoryRuleParamsRequiredReviewerConfigurationReviewer(
                                1L, GitHubRepositoryRuleParamsRequiredReviewerConfigurationReviewerType.TEAM))));

        final GitHubRepositoryRulePullRequestParameters parameters =
                new GitHubRepositoryRulePullRequestParameters(methods, false, null, false, false, 0, false, reviewers);
        methods.clear();
        reviewers.clear();

        assertSoftly(softly -> {
            softly.assertThat(parameters.allowedMergeMethods())
                    .containsExactly(GitHubRepositoryRulePullRequestAllowedMergeMethod.MERGE);
            softly.assertThat(parameters.requiredReviewers()).hasSize(1);
        });
    }

    @Test
    void constructor_shouldThrow_whenRequiredApprovingReviewCountBelowMinimum() {
        final GitHubRepositoryRulePullRequestParameters fixture =
                RepositoriesCatalogFixtures.pullRequestRuleParameters();

        final Throwable thrown = catchThrowable(() -> new GitHubRepositoryRulePullRequestParameters(
                fixture.allowedMergeMethods(),
                fixture.dismissStaleReviewsOnPush(),
                fixture.dismissalRestriction(),
                fixture.requireCodeOwnerReview(),
                fixture.requireLastPushApproval(),
                -1,
                fixture.requiredReviewThreadResolution(),
                fixture.requiredReviewers()));

        assertThat(thrown)
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("requiredApprovingReviewCount");
    }

    @Test
    void constructor_shouldThrow_whenRequiredApprovingReviewCountAboveMaximum() {
        final GitHubRepositoryRulePullRequestParameters fixture =
                RepositoriesCatalogFixtures.pullRequestRuleParameters();

        final Throwable thrown = catchThrowable(() -> new GitHubRepositoryRulePullRequestParameters(
                fixture.allowedMergeMethods(),
                fixture.dismissStaleReviewsOnPush(),
                fixture.dismissalRestriction(),
                fixture.requireCodeOwnerReview(),
                fixture.requireLastPushApproval(),
                11,
                fixture.requiredReviewThreadResolution(),
                fixture.requiredReviewers()));

        assertThat(thrown)
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("requiredApprovingReviewCount");
    }

    @Test
    void constructor_shouldAcceptRequiredApprovingReviewCount_whenAtDumpBounds() {
        final GitHubRepositoryRulePullRequestParameters fixture =
                RepositoriesCatalogFixtures.pullRequestRuleParameters();

        final GitHubRepositoryRulePullRequestParameters minimum = new GitHubRepositoryRulePullRequestParameters(
                fixture.allowedMergeMethods(),
                fixture.dismissStaleReviewsOnPush(),
                fixture.dismissalRestriction(),
                fixture.requireCodeOwnerReview(),
                fixture.requireLastPushApproval(),
                0,
                fixture.requiredReviewThreadResolution(),
                fixture.requiredReviewers());
        final GitHubRepositoryRulePullRequestParameters maximum = new GitHubRepositoryRulePullRequestParameters(
                fixture.allowedMergeMethods(),
                fixture.dismissStaleReviewsOnPush(),
                fixture.dismissalRestriction(),
                fixture.requireCodeOwnerReview(),
                fixture.requireLastPushApproval(),
                10,
                fixture.requiredReviewThreadResolution(),
                fixture.requiredReviewers());

        assertThat(minimum.requiredApprovingReviewCount()).isZero();
        assertThat(maximum.requiredApprovingReviewCount()).isEqualTo(10);
    }
}
