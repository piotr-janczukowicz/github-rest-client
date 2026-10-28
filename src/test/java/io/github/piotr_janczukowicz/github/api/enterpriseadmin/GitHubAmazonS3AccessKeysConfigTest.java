package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubAmazonS3AccessKeysConfigTest {

    @Test
    void builder_shouldKeepEveryField_whenFixtureBuilt() {
        final GitHubAmazonS3AccessKeysConfig value = EnterpriseAdminCatalogFixtures.amazonS3AccessKeysConfig();

        assertThat(GitHubAmazonS3AccessKeysConfigBuilder.builder(value).build()).isEqualTo(value);
    }

    @Test
    void constructor_shouldThrowNullPointerException_whenBucketNull() {
        final Throwable thrown = catchThrowable(() -> GitHubAmazonS3AccessKeysConfigBuilder.builder(
                        EnterpriseAdminCatalogFixtures.amazonS3AccessKeysConfig())
                .bucket(null)
                .build());

        assertThat(thrown).isInstanceOf(NullPointerException.class);
    }

    @Test
    void toString_shouldHideSecrets() {
        final GitHubAmazonS3AccessKeysConfig value = EnterpriseAdminCatalogFixtures.amazonS3AccessKeysConfig();

        assertThat(value.toString())
                .contains("encryptedSecretKey=<redacted>", "encryptedAccessKeyId=<redacted>", "bucket=octo-bucket")
                .doesNotContain(value.encryptedSecretKey(), value.encryptedAccessKeyId());
    }
}
