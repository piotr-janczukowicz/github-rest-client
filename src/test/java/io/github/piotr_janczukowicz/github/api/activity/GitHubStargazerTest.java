package io.github.piotr_janczukowicz.github.api.activity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubStargazerTest {

    @Test
    void constructor_shouldThrow_whenStarredAtNull() {
        final GitHubStargazerBuilder builder = GitHubStargazerBuilder.builder(ActivityCatalogFixtures.stargazer())
                .starredAt(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("starredAt");
    }
}
