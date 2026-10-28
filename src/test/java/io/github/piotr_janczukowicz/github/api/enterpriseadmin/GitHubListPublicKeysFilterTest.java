package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.Map;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubListPublicKeysFilterTest {

    @Test
    void toQueryParameters_shouldBeEmpty_whenNothingSet() {
        final Map<String, String> query = GitHubListPublicKeysFilter.empty().toQueryParameters();

        assertThat(query).isEmpty();
    }

    @Test
    void toQueryParameters_shouldListEverything_whenAllSet() {
        final Map<String, String> query =
                EnterpriseAdminCatalogFixtures.listPublicKeysFilter().toQueryParameters();

        assertThat(query)
                .containsExactly(
                        Map.entry("sort", "created"),
                        Map.entry("direction", "desc"),
                        Map.entry("since", "2026-01-01T00:00:00Z"));
    }
}
