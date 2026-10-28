package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.List;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubUpdateNetworkConfigurationForEnterprisePayloadTest {

    @Test
    void accessors_shouldExposeEveryComponent() {
        final GitHubUpdateNetworkConfigurationForEnterprisePayload value =
                EnterpriseAdminCatalogFixtures.updateNetworkConfigurationForEnterprisePayload();

        assertThat(value.name()).isEqualTo("sample-name");
        assertThat(value.computeService()).isEqualTo(GitHubUpdateNetworkConfigurationForEnterpriseComputeService.NONE);
        assertThat(value.networkSettingsIds()).isNotEmpty();
        assertThat(value.failoverNetworkSettingsIds()).isNotEmpty();
        assertThat(value.failoverNetworkEnabled()).isTrue();
    }

    @Test
    void constructor_shouldLeaveNetworkSettingsIdsNull_whenNull() {
        final GitHubUpdateNetworkConfigurationForEnterprisePayload value =
                GitHubUpdateNetworkConfigurationForEnterprisePayloadBuilder.builder(
                                EnterpriseAdminCatalogFixtures.updateNetworkConfigurationForEnterprisePayload())
                        .networkSettingsIds(null)
                        .build();

        assertThat(value.networkSettingsIds()).isNull();
    }

    @Test
    void constructor_shouldLeaveFailoverNetworkSettingsIdsNull_whenNull() {
        final GitHubUpdateNetworkConfigurationForEnterprisePayload value =
                GitHubUpdateNetworkConfigurationForEnterprisePayloadBuilder.builder(
                                EnterpriseAdminCatalogFixtures.updateNetworkConfigurationForEnterprisePayload())
                        .failoverNetworkSettingsIds(null)
                        .build();

        assertThat(value.failoverNetworkSettingsIds()).isNull();
    }

    @Test
    void constructor_shouldRejectNetworkSettingsIds_whenInvalid() {
        final Throwable thrown =
                catchThrowable(() -> GitHubUpdateNetworkConfigurationForEnterprisePayloadBuilder.builder(
                                EnterpriseAdminCatalogFixtures.updateNetworkConfigurationForEnterprisePayload())
                        .networkSettingsIds(List.of("a", "b"))
                        .build());

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void constructor_shouldRejectFailoverNetworkSettingsIds_whenInvalid() {
        final Throwable thrown =
                catchThrowable(() -> GitHubUpdateNetworkConfigurationForEnterprisePayloadBuilder.builder(
                                EnterpriseAdminCatalogFixtures.updateNetworkConfigurationForEnterprisePayload())
                        .failoverNetworkSettingsIds(List.of("a", "b"))
                        .build());

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class);
    }
}
