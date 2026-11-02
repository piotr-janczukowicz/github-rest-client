package io.github.piotr_janczukowicz.github.api.secretscanning;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubUpdatePublicMonitoringAlertForEnterprisePayloadTest {

    @Test
    void constructor_shouldThrow_whenStateNull() {
        final GitHubUpdatePublicMonitoringAlertForEnterprisePayloadBuilder builder =
                GitHubUpdatePublicMonitoringAlertForEnterprisePayloadBuilder.builder(
                                SecretScanningCatalogFixtures.updatePublicMonitoringAlertForEnterprisePayload())
                        .state(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("state");
    }

    @Test
    void constructor_shouldThrow_whenStateResolvedWithoutResolution() {
        final GitHubUpdatePublicMonitoringAlertForEnterprisePayloadBuilder builder =
                GitHubUpdatePublicMonitoringAlertForEnterprisePayloadBuilder.builder(
                                SecretScanningCatalogFixtures.updatePublicMonitoringAlertForEnterprisePayload())
                        .state(GitHubSecretScanningAlertState.RESOLVED)
                        .resolution(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("resolution");
    }

    @Test
    void constructor_shouldAccept_whenStateResolvedWithResolution() {
        final GitHubUpdatePublicMonitoringAlertForEnterprisePayload payload =
                GitHubUpdatePublicMonitoringAlertForEnterprisePayloadBuilder.builder(
                                SecretScanningCatalogFixtures.updatePublicMonitoringAlertForEnterprisePayload())
                        .state(GitHubSecretScanningAlertState.RESOLVED)
                        .resolution(GitHubUpdatePublicMonitoringAlertForEnterpriseResolution.FIXED)
                        .build();

        assertThat(payload.resolution()).isEqualTo(GitHubUpdatePublicMonitoringAlertForEnterpriseResolution.FIXED);
    }

    @Test
    void constructor_shouldAcceptNullResolution_whenStateOpen() {
        final GitHubUpdatePublicMonitoringAlertForEnterprisePayload payload =
                GitHubUpdatePublicMonitoringAlertForEnterprisePayloadBuilder.builder(
                                SecretScanningCatalogFixtures.updatePublicMonitoringAlertForEnterprisePayload())
                        .state(GitHubSecretScanningAlertState.OPEN)
                        .resolution(null)
                        .build();

        assertThat(payload.resolution()).isNull();
    }
}
