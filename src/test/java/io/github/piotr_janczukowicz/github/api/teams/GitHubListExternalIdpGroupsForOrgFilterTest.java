package io.github.piotr_janczukowicz.github.api.teams;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.Map;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubListExternalIdpGroupsForOrgFilterTest {

    @Test
    void empty_shouldOmitQueryParameters() {
        final GitHubListExternalIdpGroupsForOrgFilter filter = GitHubListExternalIdpGroupsForOrgFilter.empty();

        assertThat(filter.toQueryParameters()).isEmpty();
    }

    @Test
    void toQueryParameters_shouldIncludeQuery_whenSet() {
        final GitHubListExternalIdpGroupsForOrgFilter filter = TeamCatalogFixtures.listExternalIdpGroupsForOrgFilter();

        assertThat(filter.displayName()).isEqualTo("group-azuread-test");
        assertThat(filter.toQueryParameters()).containsExactly(Map.entry("display_name", "group-azuread-test"));
    }
}
