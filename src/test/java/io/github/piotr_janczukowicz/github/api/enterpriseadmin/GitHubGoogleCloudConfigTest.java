package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubGoogleCloudConfigTest {

    @Test
    void builder_shouldKeepEveryField_whenFixtureBuilt() {
        final GitHubGoogleCloudConfig value = EnterpriseAdminCatalogFixtures.googleCloudConfig();

        assertThat(GitHubGoogleCloudConfigBuilder.builder(value).build()).isEqualTo(value);
    }

    @Test
    void constructor_shouldThrowNullPointerException_whenBucketNull() {
        final Throwable thrown = catchThrowable(
                () -> GitHubGoogleCloudConfigBuilder.builder(EnterpriseAdminCatalogFixtures.googleCloudConfig())
                        .bucket(null)
                        .build());

        assertThat(thrown).isInstanceOf(NullPointerException.class);
    }

    @Test
    void toString_shouldHideSecrets() {
        final GitHubGoogleCloudConfig value = EnterpriseAdminCatalogFixtures.googleCloudConfig();

        assertThat(value.toString())
                .contains("encryptedJsonCredentials=<redacted>", "bucket=octo-bucket")
                .doesNotContain(value.encryptedJsonCredentials());
    }
}
