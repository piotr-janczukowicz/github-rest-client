package io.github.piotr_janczukowicz.github.api.actions;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.Map;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubGetOrgActionsPoliciesFilterTest {

    @Test
    void empty_shouldReturnFilterWithoutQueryParameters() {
        final GitHubGetOrgActionsPoliciesFilter sut = GitHubGetOrgActionsPoliciesFilter.empty();

        final Map<String, String> query = sut.toQueryParameters();

        assertThat(query).isEmpty();
    }

    @Test
    void toQueryParameters_shouldIncludeHasParents_whenSet() {
        final GitHubGetOrgActionsPoliciesFilter sut = ActionsCatalogFixtures.getOrgActionsPoliciesFilter();

        final Map<String, String> query = sut.toQueryParameters();

        assertThat(query).containsExactlyEntriesOf(Map.of("has_parents", "true"));
    }
}
