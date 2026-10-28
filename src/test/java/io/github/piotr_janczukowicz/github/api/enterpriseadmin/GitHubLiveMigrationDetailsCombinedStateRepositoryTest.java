package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubLiveMigrationDetailsCombinedStateRepositoryTest {

    @Test
    void constructor_shouldThrow_whenRepositoryNwoNull() {
        final GitHubLiveMigrationDetailsCombinedStateRepositoryBuilder builder =
                GitHubLiveMigrationDetailsCombinedStateRepositoryBuilder.builder(
                                EnterpriseAdminCatalogFixtures.liveMigrationDetailsCombinedStateRepository())
                        .repositoryNwo(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("repositoryNwo");
    }

    @Test
    void constructor_shouldThrow_whenDisplayStatusNull() {
        final GitHubLiveMigrationDetailsCombinedStateRepositoryBuilder builder =
                GitHubLiveMigrationDetailsCombinedStateRepositoryBuilder.builder(
                                EnterpriseAdminCatalogFixtures.liveMigrationDetailsCombinedStateRepository())
                        .displayStatus(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("displayStatus");
    }
}
