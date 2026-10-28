package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubLiveMigrationDetailsTargetStateRepositorySummaryTest {

    @Test
    void constructor_shouldThrow_whenNamespaceNull() {
        final GitHubLiveMigrationDetailsTargetStateRepositorySummaryBuilder builder =
                GitHubLiveMigrationDetailsTargetStateRepositorySummaryBuilder.builder(
                                EnterpriseAdminCatalogFixtures.liveMigrationDetailsTargetStateRepositorySummary())
                        .namespace(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("namespace");
    }

    @Test
    void constructor_shouldThrow_whenRepositoryNull() {
        final GitHubLiveMigrationDetailsTargetStateRepositorySummaryBuilder builder =
                GitHubLiveMigrationDetailsTargetStateRepositorySummaryBuilder.builder(
                                EnterpriseAdminCatalogFixtures.liveMigrationDetailsTargetStateRepositorySummary())
                        .repository(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("repository");
    }
}
