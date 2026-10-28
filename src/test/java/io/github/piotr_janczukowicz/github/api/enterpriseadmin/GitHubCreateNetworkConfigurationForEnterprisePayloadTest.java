package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.List;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubCreateNetworkConfigurationForEnterprisePayloadTest {

    @Test
    void accessors_shouldExposeEveryComponent() {
        final GitHubCreateNetworkConfigurationForEnterprisePayload value =
                EnterpriseAdminCatalogFixtures.createNetworkConfigurationForEnterprisePayload();

        assertThat(value.name()).isEqualTo("sample-name");
        assertThat(value.computeService()).isEqualTo(GitHubCreateNetworkConfigurationForEnterpriseComputeService.NONE);
        assertThat(value.networkSettingsIds()).isNotEmpty();
        assertThat(value.failoverNetworkSettingsIds()).isNotEmpty();
        assertThat(value.failoverNetworkEnabled()).isTrue();
    }

    @Test
    void constructor_shouldLeaveFailoverNetworkSettingsIdsNull_whenNull() {
        final GitHubCreateNetworkConfigurationForEnterprisePayload value =
                GitHubCreateNetworkConfigurationForEnterprisePayloadBuilder.builder(
                                EnterpriseAdminCatalogFixtures.createNetworkConfigurationForEnterprisePayload())
                        .failoverNetworkSettingsIds(null)
                        .build();

        assertThat(value.failoverNetworkSettingsIds()).isNull();
    }

    @Test
    void constructor_shouldRejectNetworkSettingsIds_whenInvalid() {
        final Throwable thrown =
                catchThrowable(() -> GitHubCreateNetworkConfigurationForEnterprisePayloadBuilder.builder(
                                EnterpriseAdminCatalogFixtures.createNetworkConfigurationForEnterprisePayload())
                        .networkSettingsIds(List.of("a", "b"))
                        .build());

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void constructor_shouldRejectFailoverNetworkSettingsIds_whenInvalid() {
        final Throwable thrown =
                catchThrowable(() -> GitHubCreateNetworkConfigurationForEnterprisePayloadBuilder.builder(
                                EnterpriseAdminCatalogFixtures.createNetworkConfigurationForEnterprisePayload())
                        .failoverNetworkSettingsIds(List.of("a", "b"))
                        .build());

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class);
    }
}
