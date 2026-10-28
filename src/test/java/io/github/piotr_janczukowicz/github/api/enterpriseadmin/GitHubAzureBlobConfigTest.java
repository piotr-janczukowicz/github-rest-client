package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubAzureBlobConfigTest {

    @Test
    void builder_shouldKeepEveryField_whenFixtureBuilt() {
        final GitHubAzureBlobConfig value = EnterpriseAdminCatalogFixtures.azureBlobConfig();

        assertThat(GitHubAzureBlobConfigBuilder.builder(value).build()).isEqualTo(value);
    }

    @Test
    void constructor_shouldThrowNullPointerException_whenKeyIdNull() {
        final Throwable thrown = catchThrowable(
                () -> GitHubAzureBlobConfigBuilder.builder(EnterpriseAdminCatalogFixtures.azureBlobConfig())
                        .keyId(null)
                        .build());

        assertThat(thrown).isInstanceOf(NullPointerException.class);
    }

    @Test
    void toString_shouldHideSecrets() {
        final GitHubAzureBlobConfig value = EnterpriseAdminCatalogFixtures.azureBlobConfig();

        assertThat(value.toString())
                .contains("encryptedSasUrl=<redacted>", "keyId=" + value.keyId())
                .doesNotContain(value.encryptedSasUrl());
    }
}
