package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubLiveMigrationDetailsTargetStateTest {

    @Test
    void constructor_shouldThrow_whenRepositorySummariesNull() {
        final GitHubLiveMigrationDetailsTargetStateBuilder builder =
                GitHubLiveMigrationDetailsTargetStateBuilder.builder(
                                EnterpriseAdminCatalogFixtures.liveMigrationDetailsTargetState())
                        .repositorySummaries(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("repositorySummaries");
    }

    @Test
    void constructor_shouldThrow_whenRepositoryProgressNull() {
        final GitHubLiveMigrationDetailsTargetStateBuilder builder =
                GitHubLiveMigrationDetailsTargetStateBuilder.builder(
                                EnterpriseAdminCatalogFixtures.liveMigrationDetailsTargetState())
                        .repositoryProgress(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("repositoryProgress");
    }
}
