package io.github.piotr_janczukowicz.github.api.interactions;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubInteractionLimitTest {

    @Test
    void constructor_shouldThrow_whenLimitNull() {
        final Throwable thrown = catchThrowable(() -> new GitHubInteractionLimit(null, null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("limit");
    }

    @Test
    void constructor_shouldLeaveExpiryNull_whenNull() {
        final GitHubInteractionLimit limit =
                new GitHubInteractionLimit(GitHubInteractionGroup.COLLABORATORS_ONLY, null);

        assertThat(limit.expiry()).isNull();
    }
}
