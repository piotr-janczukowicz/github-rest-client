package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubLiveMigrationListMigrationTest {

    @Test
    void constructor_shouldThrow_whenMigrationIdNull() {
        final GitHubLiveMigrationListMigrationBuilder builder = GitHubLiveMigrationListMigrationBuilder.builder(
                        EnterpriseAdminCatalogFixtures.liveMigrationListMigration())
                .migrationId(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("migrationId");
    }

    @Test
    void constructor_shouldThrow_whenSourceOrganizationLoginNull() {
        final GitHubLiveMigrationListMigrationBuilder builder = GitHubLiveMigrationListMigrationBuilder.builder(
                        EnterpriseAdminCatalogFixtures.liveMigrationListMigration())
                .sourceOrganizationLogin(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("sourceOrganizationLogin");
    }

    @Test
    void constructor_shouldThrow_whenTargetOrganizationLoginNull() {
        final GitHubLiveMigrationListMigrationBuilder builder = GitHubLiveMigrationListMigrationBuilder.builder(
                        EnterpriseAdminCatalogFixtures.liveMigrationListMigration())
                .targetOrganizationLogin(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("targetOrganizationLogin");
    }

    @Test
    void constructor_shouldThrow_whenSourceRepositoryNameNull() {
        final GitHubLiveMigrationListMigrationBuilder builder = GitHubLiveMigrationListMigrationBuilder.builder(
                        EnterpriseAdminCatalogFixtures.liveMigrationListMigration())
                .sourceRepositoryName(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("sourceRepositoryName");
    }

    @Test
    void constructor_shouldThrow_whenTargetRepositoryNameNull() {
        final GitHubLiveMigrationListMigrationBuilder builder = GitHubLiveMigrationListMigrationBuilder.builder(
                        EnterpriseAdminCatalogFixtures.liveMigrationListMigration())
                .targetRepositoryName(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("targetRepositoryName");
    }

    @Test
    void constructor_shouldThrow_whenTargetApiEndpointNull() {
        final GitHubLiveMigrationListMigrationBuilder builder = GitHubLiveMigrationListMigrationBuilder.builder(
                        EnterpriseAdminCatalogFixtures.liveMigrationListMigration())
                .targetApiEndpoint(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("targetApiEndpoint");
    }

    @Test
    void constructor_shouldThrow_whenRepositoriesNull() {
        final GitHubLiveMigrationListMigrationBuilder builder = GitHubLiveMigrationListMigrationBuilder.builder(
                        EnterpriseAdminCatalogFixtures.liveMigrationListMigration())
                .repositories(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("repositories");
    }
}
