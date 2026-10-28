package io.github.piotr_janczukowicz.github.api.metrics;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.List;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubCloneTrafficTest {

    @Test
    void constructor_shouldCopyClones_whenPresent() {
        final GitHubCloneTraffic traffic = new GitHubCloneTraffic(1L, 2L, List.of());

        assertThat(traffic.clones()).isEmpty();
    }

    @Test
    void constructor_shouldThrow_whenClonesNull() {
        final Throwable thrown = catchThrowable(() -> new GitHubCloneTraffic(1L, 2L, null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("clones");
    }
}
