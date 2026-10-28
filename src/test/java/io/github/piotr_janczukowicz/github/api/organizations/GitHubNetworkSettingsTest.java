package io.github.piotr_janczukowicz.github.api.organizations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubNetworkSettingsTest {

    @Test
    void constructor_shouldThrow_whenIdNull() {
        final Throwable thrown =
                catchThrowable(() -> new GitHubNetworkSettings(null, null, "my_network_settings", "subnet", "eastus"));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("id");
    }

    @Test
    void constructor_shouldThrow_whenNameNull() {
        final Throwable thrown = catchThrowable(
                () -> new GitHubNetworkSettings("220F78DACB92BBFBC5E6F22DE1CCF52309D", null, null, "subnet", "eastus"));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("name");
    }

    @Test
    void constructor_shouldThrow_whenSubnetIdNull() {
        final Throwable thrown = catchThrowable(() -> new GitHubNetworkSettings(
                "220F78DACB92BBFBC5E6F22DE1CCF52309D", null, "my_network_settings", null, "eastus"));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("subnetId");
    }

    @Test
    void constructor_shouldThrow_whenRegionNull() {
        final Throwable thrown = catchThrowable(() -> new GitHubNetworkSettings(
                "220F78DACB92BBFBC5E6F22DE1CCF52309D", null, "my_network_settings", "subnet", null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("region");
    }
}
