package io.github.piotr_janczukowicz.github.api.repositories;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.Map;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubGetRepoRuleSuitesFilterTest {

    @Test
    void empty_shouldReturnEmptyMap_whenCalled() {
        final GitHubGetRepoRuleSuitesFilter filter = GitHubGetRepoRuleSuitesFilter.empty();

        final Map<String, String> query = filter.toQueryParameters();

        assertThat(query).isEmpty();
    }

    @Test
    void toQueryParameters_shouldReturnRef_whenRefSet() {
        final GitHubGetRepoRuleSuitesFilter filter = GitHubGetRepoRuleSuitesFilterBuilder.builder()
                .ref("refs/heads/main")
                .build();

        assertThat(filter.toQueryParameters()).containsEntry("ref", "refs/heads/main");
    }

    @Test
    void toQueryParameters_shouldReturnTimePeriod_whenTimePeriodSet() {
        final Map<String, String> query = GitHubGetRepoRuleSuitesFilterBuilder.builder()
                .timePeriod(GitHubRuleSuiteTimePeriod.WEEK)
                .build()
                .toQueryParameters();

        assertThat(query).containsEntry("time_period", "week");
    }

    @Test
    void toQueryParameters_shouldReturnRuleSuiteResult_whenRuleSuiteResultSet() {
        final Map<String, String> query = GitHubGetRepoRuleSuitesFilterBuilder.builder()
                .ruleSuiteResult(GitHubRuleSuiteListResult.PASS)
                .build()
                .toQueryParameters();

        assertThat(query).containsEntry("rule_suite_result", "pass");
    }

    @Test
    void toQueryParameters_shouldReturnAll_whenRuleSuiteResultAll() {
        final Map<String, String> query = GitHubGetRepoRuleSuitesFilterBuilder.builder()
                .ruleSuiteResult(GitHubRuleSuiteListResult.ALL)
                .build()
                .toQueryParameters();

        assertThat(query).containsEntry("rule_suite_result", "all");
    }

    @Test
    void toQueryParameters_shouldReturnEvaluateStatus_whenEvaluateStatusSet() {
        final Map<String, String> query = GitHubGetRepoRuleSuitesFilterBuilder.builder()
                .evaluateStatus(GitHubRuleSuiteEvaluateStatus.EVALUATE)
                .build()
                .toQueryParameters();

        assertThat(query).containsEntry("evaluate_status", "evaluate");
    }

    @Test
    void toQueryParameters_shouldNotContainRepositoryName_whenAllParamsSet() {
        final GitHubGetRepoRuleSuitesFilter filter = GitHubGetRepoRuleSuitesFilterBuilder.builder()
                .ref("refs/heads/main")
                .timePeriod(GitHubRuleSuiteTimePeriod.WEEK)
                .actorName("octocat")
                .ruleSuiteResult(GitHubRuleSuiteListResult.PASS)
                .evaluateStatus(GitHubRuleSuiteEvaluateStatus.EVALUATE)
                .build();

        assertSoftly(softly -> {
            softly.assertThat(filter.toQueryParameters())
                    .containsEntry("ref", "refs/heads/main")
                    .containsEntry("time_period", "week")
                    .containsEntry("actor_name", "octocat")
                    .containsEntry("rule_suite_result", "pass")
                    .containsEntry("evaluate_status", "evaluate");
            softly.assertThat(filter.toQueryParameters()).doesNotContainKey("repository_name");
        });
    }

    @Test
    void toQueryParameters_shouldOmitBlankStringValues_whenBlank() {
        final GitHubGetRepoRuleSuitesFilter filter = GitHubGetRepoRuleSuitesFilterBuilder.builder()
                .ref("   ")
                .actorName("   ")
                .build();

        assertThat(filter.toQueryParameters()).isEmpty();
    }
}
