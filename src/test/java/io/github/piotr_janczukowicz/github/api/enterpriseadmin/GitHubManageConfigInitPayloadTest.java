package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubManageConfigInitPayloadTest {

    @Test
    void constructor_shouldThrow_whenLicenseNull() {
        final GitHubManageConfigInitPayloadBuilder builder = GitHubManageConfigInitPayloadBuilder.builder(
                        EnterpriseAdminCatalogFixtures.manageConfigInitPayload())
                .license(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("license");
    }

    @Test
    void constructor_shouldThrow_whenPasswordNull() {
        final GitHubManageConfigInitPayloadBuilder builder = GitHubManageConfigInitPayloadBuilder.builder(
                        EnterpriseAdminCatalogFixtures.manageConfigInitPayload())
                .password(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("password");
    }

    @Test
    void toString_shouldHideSecrets() {
        final GitHubManageConfigInitPayload value = EnterpriseAdminCatalogFixtures.manageConfigInitPayload();

        assertThat(value.toString()).contains("password=<redacted>").doesNotContain("password=password");
    }
}
