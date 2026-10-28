package io.github.piotr_janczukowicz.github.api.billing;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.Map;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubGetGithubAdvancedSecurityBillingOrgFilterTest {

    @Test
    void empty_shouldReturnNoQueryParameters() {
        assertThat(GitHubGetGithubAdvancedSecurityBillingOrgFilter.empty().toQueryParameters())
                .isEmpty();
    }

    @Test
    void toQueryParameters_shouldEncodeEveryField() {
        final GitHubGetGithubAdvancedSecurityBillingOrgFilter filter =
                BillingCatalogFixtures.getGithubAdvancedSecurityBillingOrgFilter();

        assertThat(filter.toQueryParameters()).isEqualTo(Map.of("advanced_security_product", "code_security"));
    }
}
