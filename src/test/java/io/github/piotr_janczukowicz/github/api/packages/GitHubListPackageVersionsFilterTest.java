package io.github.piotr_janczukowicz.github.api.packages;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.Map;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubListPackageVersionsFilterTest {

    @Test
    void empty_shouldReturnNoQueryParameters_whenCalled() {
        final GitHubListPackageVersionsFilter filter = GitHubListPackageVersionsFilter.empty();

        final Map<String, String> query = filter.toQueryParameters();

        assertThat(query).isEmpty();
    }

    @Test
    void toQueryParameters_shouldIncludeState_whenSet() {
        final GitHubListPackageVersionsFilter filter = GitHubListPackageVersionsFilterBuilder.builder()
                .state(GitHubPackageVersionState.DELETED)
                .build();

        final Map<String, String> query = filter.toQueryParameters();

        assertThat(query).containsEntry("state", "deleted");
    }
}
