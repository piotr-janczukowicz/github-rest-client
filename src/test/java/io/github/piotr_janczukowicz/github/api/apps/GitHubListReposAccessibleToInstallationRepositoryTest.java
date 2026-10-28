package io.github.piotr_janczukowicz.github.api.apps;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubListReposAccessibleToInstallationRepositoryTest {

    @Test
    void constructor_shouldThrow_whenNodeIdNull() {
        final GitHubListReposAccessibleToInstallationRepositoryBuilder builder =
                GitHubListReposAccessibleToInstallationRepositoryBuilder.builder(
                                AppsCatalogFixtures.reposAccessibleToInstallationRepository())
                        .nodeId(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("nodeId");
    }

    @Test
    void constructor_shouldThrow_whenNameNull() {
        final GitHubListReposAccessibleToInstallationRepositoryBuilder builder =
                GitHubListReposAccessibleToInstallationRepositoryBuilder.builder(
                                AppsCatalogFixtures.reposAccessibleToInstallationRepository())
                        .name(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("name");
    }

    @Test
    void constructor_shouldThrow_whenOwnerNull() {
        final GitHubListReposAccessibleToInstallationRepositoryBuilder builder =
                GitHubListReposAccessibleToInstallationRepositoryBuilder.builder(
                                AppsCatalogFixtures.reposAccessibleToInstallationRepository())
                        .owner(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("owner");
    }

    @Test
    void constructor_shouldKeepNullCustomProperties_whenNull() {
        final GitHubListReposAccessibleToInstallationRepositoryBuilder builder =
                GitHubListReposAccessibleToInstallationRepositoryBuilder.builder(
                                AppsCatalogFixtures.reposAccessibleToInstallationRepository())
                        .customProperties(null);

        final GitHubListReposAccessibleToInstallationRepository value = builder.build();

        assertThat(value.customProperties()).isNull();
    }

    @Test
    void constructor_shouldKeepNullTopics_whenNull() {
        final GitHubListReposAccessibleToInstallationRepositoryBuilder builder =
                GitHubListReposAccessibleToInstallationRepositoryBuilder.builder(
                                AppsCatalogFixtures.reposAccessibleToInstallationRepository())
                        .topics(null);

        final GitHubListReposAccessibleToInstallationRepository repository = builder.build();

        assertThat(repository.topics()).isNull();
    }

    @Test
    void toString_shouldIncludeFullName_whenCalled() {
        final GitHubListReposAccessibleToInstallationRepository repository =
                AppsCatalogFixtures.reposAccessibleToInstallationRepository();

        final String text = repository.toString();

        assertThat(text)
                .startsWith("GitHubListReposAccessibleToInstallationRepository[")
                .contains("fullName=");
    }
}
