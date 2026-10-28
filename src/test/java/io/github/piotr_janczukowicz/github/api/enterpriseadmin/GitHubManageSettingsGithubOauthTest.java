package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubManageSettingsGithubOauthTest {

    @Test
    void toString_shouldHideSecrets() {
        final GitHubManageSettingsGithubOauth value = EnterpriseAdminCatalogFixtures.manageSettingsGithubOauth();

        assertThat(value.toString()).contains("clientSecret=<redacted>").doesNotContain("clientSecret=client_secret");
    }
}
