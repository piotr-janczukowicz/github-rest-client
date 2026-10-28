package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubLiveMigrationDetailsTargetStateTerminalFailureTest {

    @Test
    void constructor_shouldThrow_whenCodeNull() {
        final GitHubLiveMigrationDetailsTargetStateTerminalFailureBuilder builder =
                GitHubLiveMigrationDetailsTargetStateTerminalFailureBuilder.builder(
                                EnterpriseAdminCatalogFixtures.liveMigrationDetailsTargetStateTerminalFailure())
                        .code(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("code");
    }

    @Test
    void constructor_shouldThrow_whenSummaryNull() {
        final GitHubLiveMigrationDetailsTargetStateTerminalFailureBuilder builder =
                GitHubLiveMigrationDetailsTargetStateTerminalFailureBuilder.builder(
                                EnterpriseAdminCatalogFixtures.liveMigrationDetailsTargetStateTerminalFailure())
                        .summary(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("summary");
    }
}
