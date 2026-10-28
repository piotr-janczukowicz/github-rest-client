package io.github.piotr_janczukowicz.github.api.apps;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubInstallationTokenRepositorySelectionTest {

    @Test
    void fromWire_shouldReturnALL_whenWireall() {
        final GitHubInstallationTokenRepositorySelection decoded =
                GitHubInstallationTokenRepositorySelection.fromWire("all");

        assertThat(decoded).isEqualTo(GitHubInstallationTokenRepositorySelection.ALL);
    }

    @Test
    void fromWire_shouldReturnSELECTED_whenWireselected() {
        final GitHubInstallationTokenRepositorySelection decoded =
                GitHubInstallationTokenRepositorySelection.fromWire("selected");

        assertThat(decoded).isEqualTo(GitHubInstallationTokenRepositorySelection.SELECTED);
    }

    @Test
    void fromWire_shouldThrow_whenUnknownValue() {
        final Throwable thrown = catchThrowable(() -> GitHubInstallationTokenRepositorySelection.fromWire("unknown"));

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("unknown");
    }

    @Test
    void fromWire_shouldThrow_whenNull() {
        final Throwable thrown = catchThrowable(() -> GitHubInstallationTokenRepositorySelection.fromWire(null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("value");
    }
}
