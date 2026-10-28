package io.github.piotr_janczukowicz.github.api.organizations;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.github.api.repositories.GitHubRuleSuiteEvaluateStatus;
import io.github.piotr_janczukowicz.github.api.repositories.GitHubRuleSuiteListResult;
import io.github.piotr_janczukowicz.github.api.repositories.GitHubRuleSuiteTimePeriod;
import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.Map;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubListOrgRuleSuitesFilterTest {

    @Test
    void empty_shouldReturnEmptyMap_whenCalled() {
        assertThat(GitHubListOrgRuleSuitesFilter.empty().toQueryParameters()).isEmpty();
    }

    @Test
    void toQueryParameters_shouldReturnRepositoryName_whenRepositoryNameSet() {
        final GitHubListOrgRuleSuitesFilter filter = GitHubListOrgRuleSuitesFilterBuilder.builder()
                .repositoryName("Hello-World")
                .build();

        assertThat(filter.toQueryParameters()).containsEntry("repository_name", "Hello-World");
    }

    @Test
    void toQueryParameters_shouldReturnTimePeriod_whenTimePeriodSet() {
        final Map<String, String> query = GitHubListOrgRuleSuitesFilterBuilder.builder()
                .timePeriod(GitHubRuleSuiteTimePeriod.DAY)
                .build()
                .toQueryParameters();

        assertThat(query).containsEntry("time_period", "day");
    }

    @Test
    void toQueryParameters_shouldReturnRuleSuiteResult_whenRuleSuiteResultSet() {
        final Map<String, String> query = GitHubListOrgRuleSuitesFilterBuilder.builder()
                .ruleSuiteResult(GitHubRuleSuiteListResult.ALL)
                .build()
                .toQueryParameters();

        assertThat(query).containsEntry("rule_suite_result", "all");
    }

    @Test
    void toQueryParameters_shouldReturnEvaluateStatus_whenEvaluateStatusSet() {
        final Map<String, String> query = GitHubListOrgRuleSuitesFilterBuilder.builder()
                .evaluateStatus(GitHubRuleSuiteEvaluateStatus.ACTIVE)
                .build()
                .toQueryParameters();

        assertThat(query).containsEntry("evaluate_status", "active");
    }

    @Test
    void toQueryParameters_shouldOmitBlankStringValues_whenBlank() {
        final GitHubListOrgRuleSuitesFilter filter = GitHubListOrgRuleSuitesFilterBuilder.builder()
                .ref("   ")
                .repositoryName("   ")
                .actorName("   ")
                .build();

        assertThat(filter.toQueryParameters()).isEmpty();
    }

    @Test
    void toQueryParameters_shouldReturnAllQueryParams_whenAllSet() {
        final Map<String, String> query = GitHubListOrgRuleSuitesFilterBuilder.builder()
                .ref("refs/heads/main")
                .repositoryName("Hello-World")
                .timePeriod(GitHubRuleSuiteTimePeriod.MONTH)
                .actorName("octocat")
                .ruleSuiteResult(GitHubRuleSuiteListResult.FAIL)
                .evaluateStatus(GitHubRuleSuiteEvaluateStatus.ALL)
                .build()
                .toQueryParameters();

        assertThat(query)
                .containsEntry("ref", "refs/heads/main")
                .containsEntry("repository_name", "Hello-World")
                .containsEntry("time_period", "month")
                .containsEntry("actor_name", "octocat")
                .containsEntry("rule_suite_result", "fail")
                .containsEntry("evaluate_status", "all");
    }
}
