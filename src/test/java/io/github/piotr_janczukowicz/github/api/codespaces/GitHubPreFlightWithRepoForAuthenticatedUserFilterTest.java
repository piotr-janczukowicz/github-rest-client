package io.github.piotr_janczukowicz.github.api.codespaces;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.entry;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.Map;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubPreFlightWithRepoForAuthenticatedUserFilterTest {

    @Test
    void empty_shouldReturnEmptyQueryParameters_whenCalled() {
        final Map<String, String> query =
                GitHubPreFlightWithRepoForAuthenticatedUserFilter.empty().toQueryParameters();

        assertThat(query).isEmpty();
    }

    @Test
    void toQueryParameters_shouldIncludeAllSetFields_whenCalled() {
        final GitHubPreFlightWithRepoForAuthenticatedUserFilter filter =
                CodespacesCatalogFixtures.preFlightWithRepoForAuthenticatedUserFilter();

        final Map<String, String> query = filter.toQueryParameters();

        assertThat(query).containsExactly(entry("ref", "main"), entry("client_ip", "1.2.3.4"));
    }
}
