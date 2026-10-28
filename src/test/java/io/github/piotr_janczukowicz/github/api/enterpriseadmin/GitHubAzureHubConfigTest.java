package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubAzureHubConfigTest {

    @Test
    void builder_shouldKeepEveryField_whenFixtureBuilt() {
        final GitHubAzureHubConfig value = EnterpriseAdminCatalogFixtures.azureHubConfig();

        assertThat(GitHubAzureHubConfigBuilder.builder(value).build()).isEqualTo(value);
    }

    @Test
    void constructor_shouldThrowNullPointerException_whenNameNull() {
        final Throwable thrown = catchThrowable(
                () -> GitHubAzureHubConfigBuilder.builder(EnterpriseAdminCatalogFixtures.azureHubConfig())
                        .name(null)
                        .build());

        assertThat(thrown).isInstanceOf(NullPointerException.class);
    }

    @Test
    void toString_shouldHideSecrets() {
        final GitHubAzureHubConfig value = EnterpriseAdminCatalogFixtures.azureHubConfig();

        assertThat(value.toString())
                .contains("encryptedConnstring=<redacted>", "keyId=" + value.keyId())
                .doesNotContain(value.encryptedConnstring());
    }
}
