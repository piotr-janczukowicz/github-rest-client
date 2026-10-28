package io.github.piotr_janczukowicz.github.api.actions;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubSelectedRepositoriesTest {

    @Test
    void constructor_shouldThrowNullPointerException_whenRepositoriesNull() {
        final int totalCount = 1;

        final Throwable thrown = catchThrowable(() -> new GitHubSelectedRepositories(totalCount, null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("repositories");
    }
}
