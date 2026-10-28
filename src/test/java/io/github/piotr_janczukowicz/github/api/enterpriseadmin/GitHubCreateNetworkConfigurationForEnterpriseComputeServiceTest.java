package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubCreateNetworkConfigurationForEnterpriseComputeServiceTest {

    @Test
    void fromWire_shouldReturnNone_whenKnownValue() {
        assertThat(GitHubCreateNetworkConfigurationForEnterpriseComputeService.fromWire("none"))
                .isEqualTo(GitHubCreateNetworkConfigurationForEnterpriseComputeService.NONE);
    }

    @Test
    void fromWire_shouldReturnActions_whenKnownValue() {
        assertThat(GitHubCreateNetworkConfigurationForEnterpriseComputeService.fromWire("actions"))
                .isEqualTo(GitHubCreateNetworkConfigurationForEnterpriseComputeService.ACTIONS);
    }

    @Test
    void fromWire_shouldThrowIllegalArgumentException_whenValueUnknown() {
        final Throwable thrown =
                catchThrowable(() -> GitHubCreateNetworkConfigurationForEnterpriseComputeService.fromWire("unknown"));

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("unknown");
    }
}
