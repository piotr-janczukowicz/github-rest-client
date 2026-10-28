package io.github.piotr_janczukowicz.github.api.activity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubEventTest {

    @Test
    void constructor_shouldThrow_whenIdNull() {
        final GitHubEventBuilder builder =
                GitHubEventBuilder.builder(ActivityCatalogFixtures.event()).id(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("id");
    }

    @Test
    void constructor_shouldThrow_whenActorNull() {
        final GitHubEventBuilder builder =
                GitHubEventBuilder.builder(ActivityCatalogFixtures.event()).actor(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("actor");
    }

    @Test
    void constructor_shouldThrow_whenRepoNull() {
        final GitHubEventBuilder builder =
                GitHubEventBuilder.builder(ActivityCatalogFixtures.event()).repo(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("repo");
    }

    @Test
    void constructor_shouldThrow_whenPayloadNull() {
        final GitHubEventBuilder builder =
                GitHubEventBuilder.builder(ActivityCatalogFixtures.event()).payload(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("payload");
    }

    @Test
    void constructor_shouldThrow_whenIsPublicNull() {
        final GitHubEventBuilder builder =
                GitHubEventBuilder.builder(ActivityCatalogFixtures.event()).isPublic(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("isPublic");
    }
}
