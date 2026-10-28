package io.github.piotr_janczukowicz.github.api.codesecurity;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubGetConfigurationsForOrgFilterTest {

    @Test
    void empty_shouldReturnFilter_whenCalled() {
        final GitHubGetConfigurationsForOrgFilter filter = GitHubGetConfigurationsForOrgFilter.empty();

        assertThat(filter.toQueryParameters()).isEmpty();
    }

    @Test
    void toQueryParameters_shouldIncludeQuery_whenFilterSet() {
        final GitHubGetConfigurationsForOrgFilter filter = GitHubGetConfigurationsForOrgFilterBuilder.builder()
                .targetType(GitHubGetConfigurationsForOrgTargetType.GLOBAL)
                .build();

        assertThat(filter.toQueryParameters()).containsEntry("target_type", "global");
    }
}
