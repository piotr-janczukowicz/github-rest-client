package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubChangeRepositoryAccessSelectionTest {

    @Test
    void fromWire_shouldReturnAll_whenKnownValue() {
        assertThat(GitHubChangeRepositoryAccessSelection.fromWire("all"))
                .isEqualTo(GitHubChangeRepositoryAccessSelection.ALL);
    }

    @Test
    void fromWire_shouldReturnSelected_whenKnownValue() {
        assertThat(GitHubChangeRepositoryAccessSelection.fromWire("selected"))
                .isEqualTo(GitHubChangeRepositoryAccessSelection.SELECTED);
    }

    @Test
    void fromWire_shouldThrowIllegalArgumentException_whenValueUnknown() {
        final Throwable thrown = catchThrowable(() -> GitHubChangeRepositoryAccessSelection.fromWire("unknown"));

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("unknown");
    }
}
