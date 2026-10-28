package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubLiveMigrationListMigrationRepositoryTest {

    @Test
    void constructor_shouldThrow_whenSourceRepositoryUrlNull() {
        final GitHubLiveMigrationListMigrationRepositoryBuilder builder =
                GitHubLiveMigrationListMigrationRepositoryBuilder.builder(
                                EnterpriseAdminCatalogFixtures.liveMigrationListMigrationRepository())
                        .sourceRepositoryUrl(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("sourceRepositoryUrl");
    }

    @Test
    void constructor_shouldThrow_whenTargetRepositoryUrlNull() {
        final GitHubLiveMigrationListMigrationRepositoryBuilder builder =
                GitHubLiveMigrationListMigrationRepositoryBuilder.builder(
                                EnterpriseAdminCatalogFixtures.liveMigrationListMigrationRepository())
                        .targetRepositoryUrl(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("targetRepositoryUrl");
    }
}
