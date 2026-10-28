package io.github.piotr_janczukowicz.github.api.organizations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.List;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubUpdateNetworkConfigurationPayloadTest {

    @Test
    void constructor_shouldAcceptEmptyPayload_whenNoFieldsPresent() {
        final GitHubUpdateNetworkConfigurationPayload payload =
                GitHubUpdateNetworkConfigurationPayloadBuilder.builder().build();

        assertThat(payload.name()).isNull();
    }

    @Test
    void constructor_shouldAcceptOptionalFields_whenPresent() {
        final GitHubUpdateNetworkConfigurationPayload payload = GitHubUpdateNetworkConfigurationPayloadBuilder.builder()
                .name("my-network-configuration")
                .computeService(GitHubCreateNetworkConfigurationComputeService.ACTIONS)
                .networkSettingsIds(List.of("23456789ABDCEF1"))
                .failoverNetworkEnabled(true)
                .build();

        assertThat(payload.computeService()).isEqualTo(GitHubCreateNetworkConfigurationComputeService.ACTIONS);
    }

    @Test
    void constructor_shouldAcceptFailoverNetworkSettingsIds_whenWithinLimit() {
        final GitHubUpdateNetworkConfigurationPayload payload = GitHubUpdateNetworkConfigurationPayloadBuilder.builder()
                .failoverNetworkSettingsIds(List.of("3456789ABDCEF12"))
                .build();

        assertThat(payload.failoverNetworkSettingsIds()).containsExactly("3456789ABDCEF12");
    }

    @Test
    void constructor_shouldThrow_whenNetworkSettingsIdsTooLarge() {
        final GitHubUpdateNetworkConfigurationPayloadBuilder builder =
                GitHubUpdateNetworkConfigurationPayloadBuilder.builder().networkSettingsIds(List.of("id-1", "id-2"));

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("networkSettingsIds");
    }

    @Test
    void constructor_shouldThrow_whenFailoverNetworkSettingsIdsTooLarge() {
        final GitHubUpdateNetworkConfigurationPayloadBuilder builder =
                GitHubUpdateNetworkConfigurationPayloadBuilder.builder()
                        .failoverNetworkSettingsIds(List.of("id-1", "id-2"));

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown)
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("failoverNetworkSettingsIds");
    }

    @Test
    void constructor_shouldThrow_whenNameTooLong() {
        final GitHubUpdateNetworkConfigurationPayloadBuilder builder =
                GitHubUpdateNetworkConfigurationPayloadBuilder.builder().name("n".repeat(101));

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("name");
    }
}
