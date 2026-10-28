package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubDatadogConfigSiteTest {

    @Test
    void fromWire_shouldReturnUs_whenKnownValue() {
        assertThat(GitHubDatadogConfigSite.fromWire("US")).isEqualTo(GitHubDatadogConfigSite.US);
    }

    @Test
    void fromWire_shouldReturnUs3_whenKnownValue() {
        assertThat(GitHubDatadogConfigSite.fromWire("US3")).isEqualTo(GitHubDatadogConfigSite.US3);
    }

    @Test
    void fromWire_shouldReturnUs5_whenKnownValue() {
        assertThat(GitHubDatadogConfigSite.fromWire("US5")).isEqualTo(GitHubDatadogConfigSite.US5);
    }

    @Test
    void fromWire_shouldReturnEu1_whenKnownValue() {
        assertThat(GitHubDatadogConfigSite.fromWire("EU1")).isEqualTo(GitHubDatadogConfigSite.EU1);
    }

    @Test
    void fromWire_shouldReturnUs1Fed_whenKnownValue() {
        assertThat(GitHubDatadogConfigSite.fromWire("US1-FED")).isEqualTo(GitHubDatadogConfigSite.US1_FED);
    }

    @Test
    void fromWire_shouldReturnAp1_whenKnownValue() {
        assertThat(GitHubDatadogConfigSite.fromWire("AP1")).isEqualTo(GitHubDatadogConfigSite.AP1);
    }

    @Test
    void fromWire_shouldThrowIllegalArgumentException_whenValueUnknown() {
        final Throwable thrown = catchThrowable(() -> GitHubDatadogConfigSite.fromWire("unknown"));

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("unknown");
    }
}
