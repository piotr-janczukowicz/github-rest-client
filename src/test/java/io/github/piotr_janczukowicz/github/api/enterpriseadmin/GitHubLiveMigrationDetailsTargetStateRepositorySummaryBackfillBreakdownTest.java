package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubLiveMigrationDetailsTargetStateRepositorySummaryBackfillBreakdownTest {

    @Test
    void constructor_shouldThrow_whenStateNull() {
        final GitHubLiveMigrationDetailsTargetStateRepositorySummaryBackfillBreakdownBuilder builder =
                GitHubLiveMigrationDetailsTargetStateRepositorySummaryBackfillBreakdownBuilder.builder(
                                EnterpriseAdminCatalogFixtures
                                        .liveMigrationDetailsTargetStateRepositorySummaryBackfillBreakdown())
                        .state(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("state");
    }

    @Test
    void constructor_shouldThrow_whenKindNull() {
        final GitHubLiveMigrationDetailsTargetStateRepositorySummaryBackfillBreakdownBuilder builder =
                GitHubLiveMigrationDetailsTargetStateRepositorySummaryBackfillBreakdownBuilder.builder(
                                EnterpriseAdminCatalogFixtures
                                        .liveMigrationDetailsTargetStateRepositorySummaryBackfillBreakdown())
                        .kind(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("kind");
    }

    @Test
    void constructor_shouldThrow_whenTypeNull() {
        final GitHubLiveMigrationDetailsTargetStateRepositorySummaryBackfillBreakdownBuilder builder =
                GitHubLiveMigrationDetailsTargetStateRepositorySummaryBackfillBreakdownBuilder.builder(
                                EnterpriseAdminCatalogFixtures
                                        .liveMigrationDetailsTargetStateRepositorySummaryBackfillBreakdown())
                        .type(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("type");
    }
}
