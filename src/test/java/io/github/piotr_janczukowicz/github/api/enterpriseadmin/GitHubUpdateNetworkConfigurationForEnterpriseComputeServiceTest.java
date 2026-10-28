package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubUpdateNetworkConfigurationForEnterpriseComputeServiceTest {

    @Test
    void fromWire_shouldReturnNone_whenKnownValue() {
        assertThat(GitHubUpdateNetworkConfigurationForEnterpriseComputeService.fromWire("none"))
                .isEqualTo(GitHubUpdateNetworkConfigurationForEnterpriseComputeService.NONE);
    }

    @Test
    void fromWire_shouldReturnActions_whenKnownValue() {
        assertThat(GitHubUpdateNetworkConfigurationForEnterpriseComputeService.fromWire("actions"))
                .isEqualTo(GitHubUpdateNetworkConfigurationForEnterpriseComputeService.ACTIONS);
    }

    @Test
    void fromWire_shouldThrowIllegalArgumentException_whenValueUnknown() {
        final Throwable thrown =
                catchThrowable(() -> GitHubUpdateNetworkConfigurationForEnterpriseComputeService.fromWire("unknown"));

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("unknown");
    }
}
