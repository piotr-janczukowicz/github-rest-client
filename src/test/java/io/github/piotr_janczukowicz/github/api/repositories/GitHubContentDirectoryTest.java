package io.github.piotr_janczukowicz.github.api.repositories;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubContentDirectoryTest {

    @Test
    void constructor_shouldThrow_whenEntriesNull() {
        final Throwable thrown = catchThrowable(() -> new GitHubContentDirectory(null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("entries");
    }
}
