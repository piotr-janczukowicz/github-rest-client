package io.github.piotr_janczukowicz.github.api.apps;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubScopedInstallationTest {

    @Test
    void constructor_shouldThrow_whenPermissionsNull() {
        final GitHubScopedInstallationBuilder builder = GitHubScopedInstallationBuilder.builder(
                        AppsCatalogFixtures.scopedInstallation())
                .permissions(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("permissions");
    }

    @Test
    void constructor_shouldThrow_whenRepositorySelectionNull() {
        final GitHubScopedInstallationBuilder builder = GitHubScopedInstallationBuilder.builder(
                        AppsCatalogFixtures.scopedInstallation())
                .repositorySelection(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("repositorySelection");
    }

    @Test
    void constructor_shouldThrow_whenRepositoriesUrlNull() {
        final GitHubScopedInstallationBuilder builder = GitHubScopedInstallationBuilder.builder(
                        AppsCatalogFixtures.scopedInstallation())
                .repositoriesUrl(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("repositoriesUrl");
    }

    @Test
    void constructor_shouldThrow_whenAccountNull() {
        final GitHubScopedInstallationBuilder builder = GitHubScopedInstallationBuilder.builder(
                        AppsCatalogFixtures.scopedInstallation())
                .account(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("account");
    }

    @Test
    void constructor_shouldKeepNullSingleFilePaths_whenNull() {
        final GitHubScopedInstallationBuilder builder = GitHubScopedInstallationBuilder.builder(
                        AppsCatalogFixtures.scopedInstallation())
                .singleFilePaths(null);

        final GitHubScopedInstallation value = builder.build();

        assertThat(value.singleFilePaths()).isNull();
    }
}
