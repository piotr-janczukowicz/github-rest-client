package io.github.piotr_janczukowicz.github.api.apps;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubScopedInstallationRepositorySelectionTest {

    @Test
    void fromWire_shouldReturnALL_whenWireall() {
        final GitHubScopedInstallationRepositorySelection decoded =
                GitHubScopedInstallationRepositorySelection.fromWire("all");

        assertThat(decoded).isEqualTo(GitHubScopedInstallationRepositorySelection.ALL);
    }

    @Test
    void fromWire_shouldReturnSELECTED_whenWireselected() {
        final GitHubScopedInstallationRepositorySelection decoded =
                GitHubScopedInstallationRepositorySelection.fromWire("selected");

        assertThat(decoded).isEqualTo(GitHubScopedInstallationRepositorySelection.SELECTED);
    }

    @Test
    void fromWire_shouldThrow_whenUnknownValue() {
        final Throwable thrown = catchThrowable(() -> GitHubScopedInstallationRepositorySelection.fromWire("unknown"));

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("unknown");
    }

    @Test
    void fromWire_shouldThrow_whenNull() {
        final Throwable thrown = catchThrowable(() -> GitHubScopedInstallationRepositorySelection.fromWire(null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("value");
    }
}
