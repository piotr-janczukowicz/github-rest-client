package io.github.piotr_janczukowicz.github.api.dependabot;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubOrgDependabotSecretTest {

    @Test
    void constructor_shouldThrow_whenNameNull() {
        final GitHubOrgDependabotSecretBuilder builder = GitHubOrgDependabotSecretBuilder.builder(
                        DependabotCatalogFixtures.orgDependabotSecret())
                .name(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("name");
    }

    @Test
    void constructor_shouldThrow_whenCreatedAtNull() {
        final GitHubOrgDependabotSecretBuilder builder = GitHubOrgDependabotSecretBuilder.builder(
                        DependabotCatalogFixtures.orgDependabotSecret())
                .createdAt(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("createdAt");
    }

    @Test
    void constructor_shouldThrow_whenUpdatedAtNull() {
        final GitHubOrgDependabotSecretBuilder builder = GitHubOrgDependabotSecretBuilder.builder(
                        DependabotCatalogFixtures.orgDependabotSecret())
                .updatedAt(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("updatedAt");
    }

    @Test
    void constructor_shouldThrow_whenVisibilityNull() {
        final GitHubOrgDependabotSecretBuilder builder = GitHubOrgDependabotSecretBuilder.builder(
                        DependabotCatalogFixtures.orgDependabotSecret())
                .visibility(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("visibility");
    }
}
