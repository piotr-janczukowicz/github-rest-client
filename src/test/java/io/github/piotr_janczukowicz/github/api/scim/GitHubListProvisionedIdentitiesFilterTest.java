package io.github.piotr_janczukowicz.github.api.scim;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.Map;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubListProvisionedIdentitiesFilterTest {

    @Test
    void toQueryParameters_shouldReturnEmptyMap_whenFilterNotSet() {
        final Map<String, String> query =
                GitHubListProvisionedIdentitiesFilter.empty().toQueryParameters();

        assertThat(query).isEmpty();
    }

    @Test
    void toQueryParameters_shouldSendFilter_whenFilterSet() {
        final Map<String, String> query =
                ScimCatalogFixtures.listProvisionedIdentitiesFilter().toQueryParameters();

        assertThat(query).containsExactly(Map.entry("filter", "userName eq \"Octocat\""));
    }
}
