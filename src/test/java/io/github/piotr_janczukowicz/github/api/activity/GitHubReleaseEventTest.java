package io.github.piotr_janczukowicz.github.api.activity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubReleaseEventTest {

    @Test
    void constructor_shouldThrow_whenActionNull() {
        final GitHubReleaseEventBuilder builder = GitHubReleaseEventBuilder.builder(
                        ActivityCatalogFixtures.releaseEvent())
                .action(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("action");
    }

    @Test
    void constructor_shouldThrow_whenReleaseNull() {
        final GitHubReleaseEventBuilder builder = GitHubReleaseEventBuilder.builder(
                        ActivityCatalogFixtures.releaseEvent())
                .release(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("release");
    }
}
