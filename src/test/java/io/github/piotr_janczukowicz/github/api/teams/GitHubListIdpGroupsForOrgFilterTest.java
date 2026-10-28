package io.github.piotr_janczukowicz.github.api.teams;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.Map;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubListIdpGroupsForOrgFilterTest {

    @Test
    void empty_shouldOmitQueryParameters() {
        final GitHubListIdpGroupsForOrgFilter filter = GitHubListIdpGroupsForOrgFilter.empty();

        assertThat(filter.toQueryParameters()).isEmpty();
    }

    @Test
    void toQueryParameters_shouldIncludeQuery_whenSet() {
        final GitHubListIdpGroupsForOrgFilter filter = TeamCatalogFixtures.listIdpGroupsForOrgFilter();

        assertThat(filter.q()).isEqualTo("saml-azuread-test");
        assertThat(filter.toQueryParameters()).containsExactly(Map.entry("q", "saml-azuread-test"));
    }
}
