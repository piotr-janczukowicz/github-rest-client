package io.github.piotr_janczukowicz.github.api.securityadvisories;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

import io.github.piotr_janczukowicz.github.api.GitHubDirection;
import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.Map;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubListRepositoryAdvisoriesFilterTest {

    @Test
    void empty_shouldReturnAllNulls_whenCalled() {
        final GitHubListRepositoryAdvisoriesFilter filter = GitHubListRepositoryAdvisoriesFilter.empty();

        assertSoftly(softly -> {
            softly.assertThat(filter.direction()).isNull();
            softly.assertThat(filter.sort()).isNull();
            softly.assertThat(filter.state()).isNull();
        });
    }

    @Test
    void toQueryParameters_shouldReturnEmptyMap_whenAllNull() {
        final Map<String, String> query =
                GitHubListRepositoryAdvisoriesFilter.empty().toQueryParameters();

        assertThat(query).isEmpty();
    }

    @Test
    void toQueryParameters_shouldOmitNulls_whenSomeFieldsSet() {
        final GitHubListRepositoryAdvisoriesFilter filter = GitHubListRepositoryAdvisoriesFilterBuilder.builder()
                .direction(GitHubDirection.ASC)
                .sort(GitHubRepositoryAdvisoriesSort.PUBLISHED)
                .state(GitHubRepositoryAdvisoriesFilterState.DRAFT)
                .build();

        final Map<String, String> query = filter.toQueryParameters();

        assertThat(query)
                .containsEntry("direction", "asc")
                .containsEntry("sort", "published")
                .containsEntry("state", "draft");
    }
}
