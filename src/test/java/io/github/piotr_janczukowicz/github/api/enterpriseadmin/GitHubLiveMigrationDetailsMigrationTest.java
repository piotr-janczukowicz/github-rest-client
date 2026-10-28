package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubLiveMigrationDetailsMigrationTest {

    @Test
    void constructor_shouldThrow_whenMigrationIdNull() {
        final GitHubLiveMigrationDetailsMigrationBuilder builder = GitHubLiveMigrationDetailsMigrationBuilder.builder(
                        EnterpriseAdminCatalogFixtures.liveMigrationDetailsMigration())
                .migrationId(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("migrationId");
    }

    @Test
    void constructor_shouldThrow_whenSourceOrganizationLoginNull() {
        final GitHubLiveMigrationDetailsMigrationBuilder builder = GitHubLiveMigrationDetailsMigrationBuilder.builder(
                        EnterpriseAdminCatalogFixtures.liveMigrationDetailsMigration())
                .sourceOrganizationLogin(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("sourceOrganizationLogin");
    }

    @Test
    void constructor_shouldThrow_whenTargetOrganizationLoginNull() {
        final GitHubLiveMigrationDetailsMigrationBuilder builder = GitHubLiveMigrationDetailsMigrationBuilder.builder(
                        EnterpriseAdminCatalogFixtures.liveMigrationDetailsMigration())
                .targetOrganizationLogin(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("targetOrganizationLogin");
    }

    @Test
    void constructor_shouldThrow_whenSourceRepositoryNameNull() {
        final GitHubLiveMigrationDetailsMigrationBuilder builder = GitHubLiveMigrationDetailsMigrationBuilder.builder(
                        EnterpriseAdminCatalogFixtures.liveMigrationDetailsMigration())
                .sourceRepositoryName(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("sourceRepositoryName");
    }

    @Test
    void constructor_shouldThrow_whenTargetRepositoryNameNull() {
        final GitHubLiveMigrationDetailsMigrationBuilder builder = GitHubLiveMigrationDetailsMigrationBuilder.builder(
                        EnterpriseAdminCatalogFixtures.liveMigrationDetailsMigration())
                .targetRepositoryName(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("targetRepositoryName");
    }

    @Test
    void constructor_shouldThrow_whenTargetApiEndpointNull() {
        final GitHubLiveMigrationDetailsMigrationBuilder builder = GitHubLiveMigrationDetailsMigrationBuilder.builder(
                        EnterpriseAdminCatalogFixtures.liveMigrationDetailsMigration())
                .targetApiEndpoint(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("targetApiEndpoint");
    }

    @Test
    void constructor_shouldThrow_whenRepositoriesNull() {
        final GitHubLiveMigrationDetailsMigrationBuilder builder = GitHubLiveMigrationDetailsMigrationBuilder.builder(
                        EnterpriseAdminCatalogFixtures.liveMigrationDetailsMigration())
                .repositories(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("repositories");
    }
}
