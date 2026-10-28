package io.github.piotr_janczukowicz.github.api.organizations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.net.URI;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubUpdateOrgWebhookConfigTest {

    @Test
    void constructor_shouldRetainUrl_whenProvided() {
        final GitHubUpdateOrgWebhookConfig config =
                new GitHubUpdateOrgWebhookConfig(URI.create("https://example.com/webhook"), "json", "secret", null);

        assertSoftly(softly -> {
            softly.assertThat(config).hasNoNullFieldsOrPropertiesExcept("insecureSsl");
            softly.assertThat(config.url()).hasToString("https://example.com/webhook");
            softly.assertThat(config.contentType()).isEqualTo("json");
            softly.assertThat(config.secret()).isEqualTo("secret");
        });
    }

    @Test
    void constructor_shouldThrow_whenUrlNull() {
        final Throwable thrown = catchThrowable(() -> new GitHubUpdateOrgWebhookConfig(null, null, null, null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("url");
    }

    @Test
    void toString_shouldHideSecrets() {
        final GitHubUpdateOrgWebhookConfig value = OrganizationsCatalogFixtures.updateOrgWebhookConfig();

        final String text = value.toString();

        assertSoftly(softly -> {
            softly.assertThat(text).contains("secret=<redacted>");
            softly.assertThat(text).doesNotContain(value.secret());
        });
    }
}
