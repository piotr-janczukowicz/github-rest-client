package io.github.piotr_janczukowicz.github.api.apps;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubSortTest {

    @Test
    void fromWire_shouldReturnCREATED_whenWirecreated() {
        final GitHubSort decoded = GitHubSort.fromWire("created");

        assertThat(decoded).isEqualTo(GitHubSort.CREATED);
    }

    @Test
    void fromWire_shouldReturnUPDATED_whenWireupdated() {
        final GitHubSort decoded = GitHubSort.fromWire("updated");

        assertThat(decoded).isEqualTo(GitHubSort.UPDATED);
    }

    @Test
    void fromWire_shouldThrow_whenUnknownValue() {
        final Throwable thrown = catchThrowable(() -> GitHubSort.fromWire("unknown"));

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("unknown");
    }

    @Test
    void fromWire_shouldThrow_whenNull() {
        final Throwable thrown = catchThrowable(() -> GitHubSort.fromWire(null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("value");
    }
}
