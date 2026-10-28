package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubDatadogConfigTest {

    @Test
    void builder_shouldKeepEveryField_whenFixtureBuilt() {
        final GitHubDatadogConfig value = EnterpriseAdminCatalogFixtures.datadogConfig();

        assertThat(GitHubDatadogConfigBuilder.builder(value).build()).isEqualTo(value);
    }

    @Test
    void constructor_shouldThrowNullPointerException_whenEncryptedTokenNull() {
        final Throwable thrown =
                catchThrowable(() -> GitHubDatadogConfigBuilder.builder(EnterpriseAdminCatalogFixtures.datadogConfig())
                        .encryptedToken(null)
                        .build());

        assertThat(thrown).isInstanceOf(NullPointerException.class);
    }

    @Test
    void toString_shouldHideSecrets() {
        final GitHubDatadogConfig value = EnterpriseAdminCatalogFixtures.datadogConfig();

        assertThat(value.toString())
                .contains("encryptedToken=<redacted>", "site=US")
                .doesNotContain(value.encryptedToken());
    }
}
