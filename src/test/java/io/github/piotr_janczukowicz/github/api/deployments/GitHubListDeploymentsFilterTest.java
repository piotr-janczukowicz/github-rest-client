package io.github.piotr_janczukowicz.github.api.deployments;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubListDeploymentsFilterTest {

    @Test
    void toQueryParameters_shouldIncludeSetFilters_whenProvided() {
        final GitHubListDeploymentsFilter filter = GitHubListDeploymentsFilterBuilder.builder()
                .sha("abc")
                .ref("main")
                .task("deploy")
                .environment("production")
                .build();

        assertThat(filter.toQueryParameters())
                .containsEntry("sha", "abc")
                .containsEntry("ref", "main")
                .containsEntry("task", "deploy")
                .containsEntry("environment", "production");
    }

    @Test
    void empty_shouldReturnEmptyQuery_whenCalled() {
        assertThat(GitHubListDeploymentsFilter.empty().toQueryParameters()).isEmpty();
    }
}
