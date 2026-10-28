package io.github.piotr_janczukowicz.github.api.oauthauthorizations;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.Map;
import org.junit.jupiter.api.Test;

@UnitTest
@SuppressWarnings("deprecation")
class GitHubListAuthorizationsFilterTest {

    @Test
    void toQueryParameters_shouldBeEmpty_whenNothingSet() {
        final Map<String, String> query = GitHubListAuthorizationsFilter.empty().toQueryParameters();

        assertThat(query).isEmpty();
    }

    @Test
    void toQueryParameters_shouldListClientId_whenSet() {
        final Map<String, String> query =
                OauthAuthorizationsCatalogFixtures.listAuthorizationsFilter().toQueryParameters();

        assertThat(query).containsExactly(Map.entry("client_id", "client_id"));
    }
}
