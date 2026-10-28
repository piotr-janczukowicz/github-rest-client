package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.Map;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubRevokeDelegatedCredentialAuthorizationsFilterTest {

    @Test
    void accessors_shouldExposeEveryComponent() {
        final GitHubRevokeDelegatedCredentialAuthorizationsFilter value =
                EnterpriseAdminCatalogFixtures.revokeDelegatedCredentialAuthorizationsFilter();

        assertThat(value.integrationId()).isEqualTo(1L);
    }

    @Test
    void toQueryParameters_shouldReturnEmptyMap_whenFilterNotSet() {
        assertThat(GitHubRevokeDelegatedCredentialAuthorizationsFilter.empty().toQueryParameters())
                .isEmpty();
    }

    @Test
    void toQueryParameters_shouldSendQueryParameter_whenFilterSet() {
        assertThat(EnterpriseAdminCatalogFixtures.revokeDelegatedCredentialAuthorizationsFilter()
                        .toQueryParameters())
                .containsExactly(Map.entry("integration_id", "1"));
    }
}
