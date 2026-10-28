package io.github.piotr_janczukowicz.github.api.organizations;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.Map;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubGetAuditLogFilterTest {

    @Test
    void empty_shouldHaveNoQueryParameters() {
        assertThat(GitHubGetAuditLogFilter.empty().toQueryParameters()).isEmpty();
    }

    @Test
    void toQueryParameters_shouldIncludeEverySet_whenAllSet() {
        final Map<String, String> query =
                OrganizationsCatalogFixtures.getAuditLogFilter().toQueryParameters();

        assertThat(query)
                .hasSize(3)
                .containsEntry("phrase", "action:team.add")
                .containsEntry("include", "all")
                .containsEntry("order", "desc");
    }

    @Test
    void toQueryParameters_shouldSkipNulls_whenOnlyOrderSet() {
        final Map<String, String> query =
                new GitHubGetAuditLogFilter(null, null, GitHubAuditLogOrder.ASC).toQueryParameters();

        assertThat(query).containsExactlyEntriesOf(Map.of("order", "asc"));
    }
}
