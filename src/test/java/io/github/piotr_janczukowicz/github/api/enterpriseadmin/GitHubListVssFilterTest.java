package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.Map;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubListVssFilterTest {

    @Test
    void accessors_shouldExposeEveryComponent() {
        final GitHubListVssFilter value = EnterpriseAdminCatalogFixtures.listVssFilter();

        assertThat(value.isUnmatchedOnly()).isTrue();
    }

    @Test
    void toQueryParameters_shouldReturnEmptyMap_whenFilterNotSet() {
        assertThat(GitHubListVssFilter.empty().toQueryParameters()).isEmpty();
    }

    @Test
    void toQueryParameters_shouldSendQueryParameter_whenFilterSet() {
        assertThat(EnterpriseAdminCatalogFixtures.listVssFilter().toQueryParameters())
                .containsExactly(Map.entry("is_unmatched_only", "true"));
    }
}
