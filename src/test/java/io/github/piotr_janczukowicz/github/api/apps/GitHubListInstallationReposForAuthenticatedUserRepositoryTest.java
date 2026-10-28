package io.github.piotr_janczukowicz.github.api.apps;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubListInstallationReposForAuthenticatedUserRepositoryTest {

    @Test
    void constructor_shouldThrow_whenNodeIdNull() {
        final GitHubListInstallationReposForAuthenticatedUserRepositoryBuilder builder =
                GitHubListInstallationReposForAuthenticatedUserRepositoryBuilder.builder(
                                AppsCatalogFixtures.installationReposForAuthenticatedUserRepository())
                        .nodeId(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("nodeId");
    }

    @Test
    void constructor_shouldThrow_whenNameNull() {
        final GitHubListInstallationReposForAuthenticatedUserRepositoryBuilder builder =
                GitHubListInstallationReposForAuthenticatedUserRepositoryBuilder.builder(
                                AppsCatalogFixtures.installationReposForAuthenticatedUserRepository())
                        .name(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("name");
    }

    @Test
    void constructor_shouldThrow_whenOwnerNull() {
        final GitHubListInstallationReposForAuthenticatedUserRepositoryBuilder builder =
                GitHubListInstallationReposForAuthenticatedUserRepositoryBuilder.builder(
                                AppsCatalogFixtures.installationReposForAuthenticatedUserRepository())
                        .owner(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("owner");
    }

    @Test
    void constructor_shouldKeepNullCustomProperties_whenNull() {
        final GitHubListInstallationReposForAuthenticatedUserRepositoryBuilder builder =
                GitHubListInstallationReposForAuthenticatedUserRepositoryBuilder.builder(
                                AppsCatalogFixtures.installationReposForAuthenticatedUserRepository())
                        .customProperties(null);

        final GitHubListInstallationReposForAuthenticatedUserRepository value = builder.build();

        assertThat(value.customProperties()).isNull();
    }

    @Test
    void constructor_shouldKeepNullTopics_whenNull() {
        final GitHubListInstallationReposForAuthenticatedUserRepositoryBuilder builder =
                GitHubListInstallationReposForAuthenticatedUserRepositoryBuilder.builder(
                                AppsCatalogFixtures.installationReposForAuthenticatedUserRepository())
                        .topics(null);

        final GitHubListInstallationReposForAuthenticatedUserRepository repository = builder.build();

        assertThat(repository.topics()).isNull();
    }

    @Test
    void toString_shouldIncludeFullName_whenCalled() {
        final GitHubListInstallationReposForAuthenticatedUserRepository repository =
                AppsCatalogFixtures.installationReposForAuthenticatedUserRepository();

        final String text = repository.toString();

        assertThat(text)
                .startsWith("GitHubListInstallationReposForAuthenticatedUserRepository[")
                .contains("fullName=");
    }
}
