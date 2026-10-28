package io.github.piotr_janczukowicz.github.api.organizations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubCreateOrgWebhookConfigTest {

    @Test
    void constructor_shouldThrow_whenUrlNull() {
        final Throwable thrown =
                catchThrowable(() -> new GitHubCreateOrgWebhookConfig(null, null, null, null, null, null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("url");
    }

    @Test
    void toString_shouldHideSecrets() {
        final GitHubCreateOrgWebhookConfig value = OrganizationsCatalogFixtures.createOrgWebhookConfig();

        final String text = value.toString();

        assertSoftly(softly -> {
            softly.assertThat(text).contains("secret=<redacted>");
            softly.assertThat(text).doesNotContain(value.secret());
            softly.assertThat(text).contains("password=<redacted>");
            softly.assertThat(text).doesNotContain("=" + value.password());
        });
    }
}
