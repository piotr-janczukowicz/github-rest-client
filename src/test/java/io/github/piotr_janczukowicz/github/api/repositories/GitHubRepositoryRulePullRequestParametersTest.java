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
                new GitHubRepositoryRulePullRequestParameters(null, false, null, false, false, 0, false, null, null);

        assertSoftly(softly -> {
            softly.assertThat(parameters.allowedMergeMethods()).isNull();
            softly.assertThat(parameters.requiredReviewers()).isNull();
        });
    }

    @Test
    void constructor_shouldLeaveRequireExtraApprovalNull_whenNull() {
        final GitHubRepositoryRulePullRequestParameters parameters =
                GitHubRepositoryRulePullRequestParametersBuilder.builder(
                                RepositoriesCatalogFixtures.pullRequestRuleParameters())
                        .requireExtraApprovalForUnattributedChanges(null)
                        .build();

        assertThat(parameters.requireExtraApprovalForUnattributedChanges()).isNull();
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

        final GitHubRepositoryRulePullRequestParameters parameters = new GitHubRepositoryRulePullRequestParameters(
                methods, false, null, false, false, 0, false, reviewers, null);
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
        final GitHubRepositoryRulePullRequestParametersBuilder builder =
                GitHubRepositoryRulePullRequestParametersBuilder.builder(
                                RepositoriesCatalogFixtures.pullRequestRuleParameters())
                        .requiredApprovingReviewCount(-1);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown)
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("requiredApprovingReviewCount");
    }

    @Test
    void constructor_shouldThrow_whenRequiredApprovingReviewCountAboveMaximum() {
        final GitHubRepositoryRulePullRequestParametersBuilder builder =
                GitHubRepositoryRulePullRequestParametersBuilder.builder(
                                RepositoriesCatalogFixtures.pullRequestRuleParameters())
                        .requiredApprovingReviewCount(11);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown)
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("requiredApprovingReviewCount");
    }

    @Test
    void constructor_shouldAcceptRequiredApprovingReviewCount_whenAtDumpBounds() {
        final GitHubRepositoryRulePullRequestParametersBuilder builder =
                GitHubRepositoryRulePullRequestParametersBuilder.builder(
                        RepositoriesCatalogFixtures.pullRequestRuleParameters());

        final GitHubRepositoryRulePullRequestParameters minimum =
                builder.requiredApprovingReviewCount(0).build();
        final GitHubRepositoryRulePullRequestParameters maximum =
                builder.requiredApprovingReviewCount(10).build();

        assertSoftly(softly -> {
            softly.assertThat(minimum.requiredApprovingReviewCount()).isZero();
            softly.assertThat(maximum.requiredApprovingReviewCount()).isEqualTo(10);
        });
    }
}
