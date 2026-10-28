package io.github.piotr_janczukowicz.github.api.repositories;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.time.Instant;
import java.util.Map;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubListForAuthenticatedUserFilterTest {

    @Test
    void empty_shouldReturnAllNulls_whenCalled() {
        final GitHubListForAuthenticatedUserFilter filter = GitHubListForAuthenticatedUserFilter.empty();

        assertSoftly(softly -> {
            softly.assertThat(filter.visibility()).isNull();
            softly.assertThat(filter.affiliation()).isNull();
            softly.assertThat(filter.type()).isNull();
            softly.assertThat(filter.sort()).isNull();
            softly.assertThat(filter.direction()).isNull();
            softly.assertThat(filter.since()).isNull();
            softly.assertThat(filter.before()).isNull();
        });
    }

    @Test
    void toQueryParameters_shouldReturnEmptyMap_whenAllNull() {
        final Map<String, String> query =
                GitHubListForAuthenticatedUserFilter.empty().toQueryParameters();

        assertThat(query).isEmpty();
    }

    @Test
    void toQueryParameters_shouldOmitNulls_whenSomeFieldsSet() {
        final Instant since = Instant.parse("2024-01-01T00:00:00Z");
        final Instant before = Instant.parse("2024-06-01T00:00:00Z");
        final GitHubListForAuthenticatedUserFilter filter = GitHubListForAuthenticatedUserFilterBuilder.builder()
                .visibility(GitHubAuthenticatedUserReposVisibility.PUBLIC)
                .affiliation("owner,collaborator")
                .type(GitHubAuthenticatedUserReposType.MEMBER)
                .sort(GitHubReposSort.FULL_NAME)
                .direction(GitHubReposSortDirection.ASC)
                .since(since)
                .before(before)
                .build();

        final Map<String, String> query = filter.toQueryParameters();

        assertThat(query)
                .containsEntry("visibility", "public")
                .containsEntry("affiliation", "owner,collaborator")
                .containsEntry("type", "member")
                .containsEntry("sort", "full_name")
                .containsEntry("direction", "asc")
                .containsEntry("since", since.toString())
                .containsEntry("before", before.toString());
    }
}
