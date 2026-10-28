package io.github.piotr_janczukowicz.github.api.actions;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.Map;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubListSelfHostedRunnersForEnterpriseFilterTest {

    @Test
    void empty_shouldReturnFilterWithoutQueryParameters() {
        final GitHubListSelfHostedRunnersForEnterpriseFilter sut =
                GitHubListSelfHostedRunnersForEnterpriseFilter.empty();

        final Map<String, String> query = sut.toQueryParameters();

        assertThat(query).isEmpty();
    }

    @Test
    void toQueryParameters_shouldIncludeName_whenSet() {
        final GitHubListSelfHostedRunnersForEnterpriseFilter sut =
                ActionsCatalogFixtures.listSelfHostedRunnersForEnterpriseFilter();

        final Map<String, String> query = sut.toQueryParameters();

        assertThat(query).containsExactlyEntriesOf(Map.of("name", "name-sample"));
    }
}
