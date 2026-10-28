package io.github.piotr_janczukowicz.github.api.actions;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubRunnerLabelTest {

    @Test
    void constructor_shouldThrowNullPointerException_whenNameNull() {
        final Long id = 1L;
        final GitHubRunnerLabelType type = GitHubRunnerLabelType.READ_ONLY;

        final Throwable thrown = catchThrowable(() -> new GitHubRunnerLabel(id, null, type));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("name");
    }
}
