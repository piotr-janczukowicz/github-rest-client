package io.github.piotr_janczukowicz.github.api.activity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubDiscussionAnswerChosenByTest {

    @Test
    void constructor_shouldThrow_whenLoginNull() {
        final GitHubDiscussionAnswerChosenByBuilder builder = GitHubDiscussionAnswerChosenByBuilder.builder(
                        ActivityCatalogFixtures.discussionAnswerChosenBy())
                .login(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("login");
    }
}
