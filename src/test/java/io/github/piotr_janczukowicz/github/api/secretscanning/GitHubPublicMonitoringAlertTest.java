package io.github.piotr_janczukowicz.github.api.secretscanning;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubPublicMonitoringAlertTest {

    @Test
    void constructor_shouldThrow_whenUrlNull() {
        final GitHubPublicMonitoringAlertBuilder builder = GitHubPublicMonitoringAlertBuilder.builder(
                        SecretScanningCatalogFixtures.publicMonitoringAlert())
                .url(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("url");
    }

    @Test
    void constructor_shouldThrow_whenNumberTooSmall() {
        final GitHubPublicMonitoringAlertBuilder builder = GitHubPublicMonitoringAlertBuilder.builder(
                        SecretScanningCatalogFixtures.publicMonitoringAlert())
                .number(0);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("number");
    }

    @Test
    void toString_shouldHideSecrets() {
        final GitHubPublicMonitoringAlert value = SecretScanningCatalogFixtures.publicMonitoringAlert();

        assertThat(value.toString()).contains("secret=<redacted>").doesNotContain("secret=secret");
    }

    @Test
    void toString_shouldHideTheSecret_whenSecretSet() {
        final GitHubPublicMonitoringAlert alert = GitHubPublicMonitoringAlertBuilder.builder(
                        SecretScanningCatalogFixtures.publicMonitoringAlert())
                .secret("ghp_do_not_print_this_value")
                .build();

        final String text = alert.toString();

        assertThat(text).contains("secret=<redacted>").doesNotContain("ghp_do_not_print_this_value");
    }

    @Test
    void toString_shouldShowTheNonSecretFields_whenCalled() {
        final GitHubPublicMonitoringAlert alert = SecretScanningCatalogFixtures.publicMonitoringAlert();

        final String text = alert.toString();

        assertThat(text).contains("number=" + alert.number()).contains("url=" + alert.url());
    }
}
