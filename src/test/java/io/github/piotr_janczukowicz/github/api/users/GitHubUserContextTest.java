package io.github.piotr_janczukowicz.github.api.users;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubUserContextTest {

    @Test
    void constructor_shouldThrow_whenContextsNull() {
        final Throwable thrown = catchThrowable(() -> new GitHubUserContext(null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("contexts");
    }
}
