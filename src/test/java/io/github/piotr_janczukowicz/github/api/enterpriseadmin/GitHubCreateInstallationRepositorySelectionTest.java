package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubCreateInstallationRepositorySelectionTest {

    @Test
    void fromWire_shouldReturnAll_whenKnownValue() {
        assertThat(GitHubCreateInstallationRepositorySelection.fromWire("all"))
                .isEqualTo(GitHubCreateInstallationRepositorySelection.ALL);
    }

    @Test
    void fromWire_shouldReturnSelected_whenKnownValue() {
        assertThat(GitHubCreateInstallationRepositorySelection.fromWire("selected"))
                .isEqualTo(GitHubCreateInstallationRepositorySelection.SELECTED);
    }

    @Test
    void fromWire_shouldReturnNone_whenKnownValue() {
        assertThat(GitHubCreateInstallationRepositorySelection.fromWire("none"))
                .isEqualTo(GitHubCreateInstallationRepositorySelection.NONE);
    }

    @Test
    void fromWire_shouldThrowIllegalArgumentException_whenValueUnknown() {
        final Throwable thrown = catchThrowable(() -> GitHubCreateInstallationRepositorySelection.fromWire("unknown"));

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("unknown");
    }
}
