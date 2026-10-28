package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubAmazonS3OidcConfigTest {

    @Test
    void builder_shouldKeepEveryField_whenFixtureBuilt() {
        final GitHubAmazonS3OidcConfig value = EnterpriseAdminCatalogFixtures.amazonS3OidcConfig();

        assertThat(GitHubAmazonS3OidcConfigBuilder.builder(value).build()).isEqualTo(value);
    }

    @Test
    void constructor_shouldThrowNullPointerException_whenBucketNull() {
        final Throwable thrown = catchThrowable(
                () -> GitHubAmazonS3OidcConfigBuilder.builder(EnterpriseAdminCatalogFixtures.amazonS3OidcConfig())
                        .bucket(null)
                        .build());

        assertThat(thrown).isInstanceOf(NullPointerException.class);
    }
}
