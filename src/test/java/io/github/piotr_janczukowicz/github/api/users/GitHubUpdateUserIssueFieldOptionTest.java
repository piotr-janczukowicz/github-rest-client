package io.github.piotr_janczukowicz.github.api.users;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubUpdateUserIssueFieldOptionTest {

    @Test
    void constructor_shouldThrow_whenNameNull() {
        final GitHubUpdateUserIssueFieldOptionBuilder builder = GitHubUpdateUserIssueFieldOptionBuilder.builder(
                        UsersCatalogFixtures.updateUserIssueFieldOption())
                .name(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("name");
    }

    @Test
    void constructor_shouldThrow_whenColorNull() {
        final GitHubUpdateUserIssueFieldOptionBuilder builder = GitHubUpdateUserIssueFieldOptionBuilder.builder(
                        UsersCatalogFixtures.updateUserIssueFieldOption())
                .color(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("color");
    }
}
