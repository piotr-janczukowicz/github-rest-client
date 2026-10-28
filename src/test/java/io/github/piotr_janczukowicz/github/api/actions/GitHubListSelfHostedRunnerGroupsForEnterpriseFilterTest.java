package io.github.piotr_janczukowicz.github.api.actions;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.Map;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubListSelfHostedRunnerGroupsForEnterpriseFilterTest {

    @Test
    void empty_shouldReturnFilterWithoutQueryParameters() {
        final GitHubListSelfHostedRunnerGroupsForEnterpriseFilter sut =
                GitHubListSelfHostedRunnerGroupsForEnterpriseFilter.empty();

        final Map<String, String> query = sut.toQueryParameters();

        assertThat(query).isEmpty();
    }

    @Test
    void toQueryParameters_shouldIncludeVisibleToOrganization_whenSet() {
        final GitHubListSelfHostedRunnerGroupsForEnterpriseFilter sut =
                ActionsCatalogFixtures.listSelfHostedRunnerGroupsForEnterpriseFilter();

        final Map<String, String> query = sut.toQueryParameters();

        assertThat(query).containsExactlyEntriesOf(Map.of("visible_to_organization", "visibleToOrganization-sample"));
    }
}
