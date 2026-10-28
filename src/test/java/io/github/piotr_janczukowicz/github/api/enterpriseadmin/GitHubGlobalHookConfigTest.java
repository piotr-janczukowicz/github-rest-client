package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubGlobalHookConfigTest {

    @Test
    void toString_shouldHideSecrets() {
        final GitHubGlobalHookConfig value = EnterpriseAdminCatalogFixtures.globalHookConfig();

        assertThat(value.toString()).contains("secret=<redacted>").doesNotContain("secret=secret");
    }
}
