package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubLiveMigrationDetailsCombinedStateTest {

    @Test
    void constructor_shouldThrow_whenDisplayMessageNull() {
        final GitHubLiveMigrationDetailsCombinedStateBuilder builder =
                GitHubLiveMigrationDetailsCombinedStateBuilder.builder(
                                EnterpriseAdminCatalogFixtures.liveMigrationDetailsCombinedState())
                        .displayMessage(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("displayMessage");
    }

    @Test
    void constructor_shouldThrow_whenCutoverBlockersNull() {
        final GitHubLiveMigrationDetailsCombinedStateBuilder builder =
                GitHubLiveMigrationDetailsCombinedStateBuilder.builder(
                                EnterpriseAdminCatalogFixtures.liveMigrationDetailsCombinedState())
                        .cutoverBlockers(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("cutoverBlockers");
    }

    @Test
    void constructor_shouldThrow_whenRepositoriesNull() {
        final GitHubLiveMigrationDetailsCombinedStateBuilder builder =
                GitHubLiveMigrationDetailsCombinedStateBuilder.builder(
                                EnterpriseAdminCatalogFixtures.liveMigrationDetailsCombinedState())
                        .repositories(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("repositories");
    }
}
