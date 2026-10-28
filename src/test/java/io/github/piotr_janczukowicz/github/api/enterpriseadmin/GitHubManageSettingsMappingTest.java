package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubManageSettingsMappingTest {

    @Test
    void toString_shouldHideSecrets() {
        final GitHubManageSettingsMapping value = EnterpriseAdminCatalogFixtures.manageSettingsMapping();

        assertThat(value.toString()).contains("token=<redacted>").doesNotContain("token=token");
    }
}
