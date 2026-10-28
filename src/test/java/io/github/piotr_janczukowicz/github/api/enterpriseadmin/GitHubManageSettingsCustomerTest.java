package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubManageSettingsCustomerTest {

    @Test
    void toString_shouldHideSecrets() {
        final GitHubManageSettingsCustomer value = EnterpriseAdminCatalogFixtures.manageSettingsCustomer();

        assertThat(value.toString())
                .contains("secretKeyData=<redacted>")
                .doesNotContain("secretKeyData=secret_key_data");
    }
}
