package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubLiveMigrationDetailsCombinedStateTerminalFailureTest {

    @Test
    void constructor_shouldThrow_whenCodeNull() {
        final GitHubLiveMigrationDetailsCombinedStateTerminalFailureBuilder builder =
                GitHubLiveMigrationDetailsCombinedStateTerminalFailureBuilder.builder(
                                EnterpriseAdminCatalogFixtures.liveMigrationDetailsCombinedStateTerminalFailure())
                        .code(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("code");
    }

    @Test
    void constructor_shouldThrow_whenSummaryNull() {
        final GitHubLiveMigrationDetailsCombinedStateTerminalFailureBuilder builder =
                GitHubLiveMigrationDetailsCombinedStateTerminalFailureBuilder.builder(
                                EnterpriseAdminCatalogFixtures.liveMigrationDetailsCombinedStateTerminalFailure())
                        .summary(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("summary");
    }
}
