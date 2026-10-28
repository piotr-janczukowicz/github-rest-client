package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubUpdateGlobalWebhookPayloadConfigTest {

    @Test
    void constructor_shouldThrow_whenUrlNull() {
        final GitHubUpdateGlobalWebhookPayloadConfigBuilder builder =
                GitHubUpdateGlobalWebhookPayloadConfigBuilder.builder(
                                EnterpriseAdminCatalogFixtures.updateGlobalWebhookPayloadConfig())
                        .url(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("url");
    }

    @Test
    void toString_shouldHideSecrets() {
        final GitHubUpdateGlobalWebhookPayloadConfig value =
                EnterpriseAdminCatalogFixtures.updateGlobalWebhookPayloadConfig();

        assertThat(value.toString()).contains("secret=<redacted>").doesNotContain("secret=secret");
    }
}
