package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubManageDownloadUpgradeOrchestrationTest {

    @Test
    void constructor_shouldThrow_whenMessageNull() {
        final GitHubManageDownloadUpgradeOrchestrationBuilder builder =
                GitHubManageDownloadUpgradeOrchestrationBuilder.builder(
                                EnterpriseAdminCatalogFixtures.manageDownloadUpgradeOrchestration())
                        .message(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("message");
    }

    @Test
    void constructor_shouldThrow_whenDownloadIdNull() {
        final GitHubManageDownloadUpgradeOrchestrationBuilder builder =
                GitHubManageDownloadUpgradeOrchestrationBuilder.builder(
                                EnterpriseAdminCatalogFixtures.manageDownloadUpgradeOrchestration())
                        .downloadId(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("downloadId");
    }
}
