package io.github.piotr_janczukowicz.github.api.codespaces;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.entry;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.Map;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubRepoMachinesForAuthenticatedUserFilterTest {

    @Test
    void empty_shouldReturnEmptyQueryParameters_whenCalled() {
        final Map<String, String> query =
                GitHubRepoMachinesForAuthenticatedUserFilter.empty().toQueryParameters();

        assertThat(query).isEmpty();
    }

    @Test
    void toQueryParameters_shouldIncludeAllSetFields_whenCalled() {
        final GitHubRepoMachinesForAuthenticatedUserFilter filter =
                CodespacesCatalogFixtures.repoMachinesForAuthenticatedUserFilter();

        final Map<String, String> query = filter.toQueryParameters();

        assertThat(query)
                .containsExactly(entry("location", "WestUs2"), entry("client_ip", "1.2.3.4"), entry("ref", "main"));
    }
}
