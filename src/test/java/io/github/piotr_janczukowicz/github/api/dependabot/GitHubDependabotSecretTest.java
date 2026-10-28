package io.github.piotr_janczukowicz.github.api.dependabot;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubDependabotSecretTest {

    @Test
    void constructor_shouldThrow_whenNameNull() {
        final GitHubDependabotSecretBuilder builder = GitHubDependabotSecretBuilder.builder(
                        DependabotCatalogFixtures.dependabotSecret())
                .name(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("name");
    }

    @Test
    void constructor_shouldThrow_whenCreatedAtNull() {
        final GitHubDependabotSecretBuilder builder = GitHubDependabotSecretBuilder.builder(
                        DependabotCatalogFixtures.dependabotSecret())
                .createdAt(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("createdAt");
    }

    @Test
    void constructor_shouldThrow_whenUpdatedAtNull() {
        final GitHubDependabotSecretBuilder builder = GitHubDependabotSecretBuilder.builder(
                        DependabotCatalogFixtures.dependabotSecret())
                .updatedAt(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("updatedAt");
    }
}
