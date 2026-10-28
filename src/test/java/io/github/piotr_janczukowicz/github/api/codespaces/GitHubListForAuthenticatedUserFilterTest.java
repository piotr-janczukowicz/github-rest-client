package io.github.piotr_janczukowicz.github.api.codespaces;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.entry;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.Map;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubListForAuthenticatedUserFilterTest {

    @Test
    void empty_shouldReturnEmptyQueryParameters_whenCalled() {
        final Map<String, String> query =
                GitHubListForAuthenticatedUserFilter.empty().toQueryParameters();

        assertThat(query).isEmpty();
    }

    @Test
    void toQueryParameters_shouldIncludeRepositoryId_whenSet() {
        final GitHubListForAuthenticatedUserFilter filter = CodespacesCatalogFixtures.listForAuthenticatedUserFilter();

        final Map<String, String> query = filter.toQueryParameters();

        assertThat(query).containsExactly(entry("repository_id", "1296269"));
    }
}
