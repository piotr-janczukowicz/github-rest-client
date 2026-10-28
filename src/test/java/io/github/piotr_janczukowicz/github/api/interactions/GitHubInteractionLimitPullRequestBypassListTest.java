package io.github.piotr_janczukowicz.github.api.interactions;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.List;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubInteractionLimitPullRequestBypassListTest {

    @Test
    void constructor_shouldThrow_whenUsersNull() {
        final Throwable thrown = catchThrowable(() -> new GitHubInteractionLimitPullRequestBypassList(null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("users");
    }

    @Test
    void constructor_shouldCopyUsers_whenUsersProvided() {
        final GitHubInteractionLimitPullRequestBypassList payload =
                new GitHubInteractionLimitPullRequestBypassList(List.of("octocat"));

        assertThat(payload.users()).containsExactly("octocat");
    }
}
