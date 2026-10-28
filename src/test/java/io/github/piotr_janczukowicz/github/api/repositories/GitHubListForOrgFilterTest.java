package io.github.piotr_janczukowicz.github.api.repositories;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.Map;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubListForOrgFilterTest {

    @Test
    void empty_shouldReturnAllNulls_whenCalled() {
        final GitHubListForOrgFilter filter = GitHubListForOrgFilter.empty();

        assertSoftly(softly -> {
            softly.assertThat(filter.type()).isNull();
            softly.assertThat(filter.sort()).isNull();
            softly.assertThat(filter.direction()).isNull();
        });
    }

    @Test
    void toQueryParameters_shouldReturnEmptyMap_whenAllNull() {
        final Map<String, String> query = GitHubListForOrgFilter.empty().toQueryParameters();

        assertThat(query).isEmpty();
    }

    @Test
    void toQueryParameters_shouldOmitNulls_whenSomeFieldsSet() {
        final GitHubListForOrgFilter filter = GitHubListForOrgFilterBuilder.builder()
                .type(GitHubOrgReposListType.FORKS)
                .sort(GitHubReposSort.UPDATED)
                .direction(GitHubReposSortDirection.DESC)
                .build();

        final Map<String, String> query = filter.toQueryParameters();

        assertThat(query)
                .containsEntry("type", "forks")
                .containsEntry("sort", "updated")
                .containsEntry("direction", "desc");
    }
}
