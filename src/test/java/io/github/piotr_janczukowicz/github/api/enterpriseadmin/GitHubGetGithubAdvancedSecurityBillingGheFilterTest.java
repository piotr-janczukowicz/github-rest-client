package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.Map;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubGetGithubAdvancedSecurityBillingGheFilterTest {

    @Test
    void accessors_shouldExposeEveryComponent() {
        final GitHubGetGithubAdvancedSecurityBillingGheFilter value =
                EnterpriseAdminCatalogFixtures.getGithubAdvancedSecurityBillingGheFilter();

        assertThat(value.advancedSecurityProduct()).isEqualTo(GitHubAdvancedSecurityProduct.CODE_SECURITY);
    }

    @Test
    void toQueryParameters_shouldReturnEmptyMap_whenFilterNotSet() {
        assertThat(GitHubGetGithubAdvancedSecurityBillingGheFilter.empty().toQueryParameters())
                .isEmpty();
    }

    @Test
    void toQueryParameters_shouldSendQueryParameter_whenFilterSet() {
        assertThat(EnterpriseAdminCatalogFixtures.getGithubAdvancedSecurityBillingGheFilter()
                        .toQueryParameters())
                .containsExactly(Map.entry("advanced_security_product", "code_security"));
    }
}
