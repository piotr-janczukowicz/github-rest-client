package io.github.piotr_janczukowicz.github.api.actions;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubSelectedActionsUrlTest {

    @Test
    void constructor_shouldThrowNullPointerException_whenValueNull() {
        final Throwable thrown = catchThrowable(() -> new GitHubSelectedActionsUrl(null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("value");
    }
}
