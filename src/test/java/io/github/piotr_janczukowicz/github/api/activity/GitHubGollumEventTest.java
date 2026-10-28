package io.github.piotr_janczukowicz.github.api.activity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubGollumEventTest {

    @Test
    void constructor_shouldThrow_whenPagesNull() {
        final Throwable thrown = catchThrowable(() -> new GitHubGollumEvent(null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("pages");
    }
}
