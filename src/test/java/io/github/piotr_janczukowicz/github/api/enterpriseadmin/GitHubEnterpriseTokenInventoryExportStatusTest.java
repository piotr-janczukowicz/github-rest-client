package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubEnterpriseTokenInventoryExportStatusTest {

    @Test
    void fromWire_shouldReturnPending_whenKnownValue() {
        assertThat(GitHubEnterpriseTokenInventoryExportStatus.fromWire("pending"))
                .isEqualTo(GitHubEnterpriseTokenInventoryExportStatus.PENDING);
    }

    @Test
    void fromWire_shouldReturnQueued_whenKnownValue() {
        assertThat(GitHubEnterpriseTokenInventoryExportStatus.fromWire("queued"))
                .isEqualTo(GitHubEnterpriseTokenInventoryExportStatus.QUEUED);
    }

    @Test
    void fromWire_shouldReturnStarted_whenKnownValue() {
        assertThat(GitHubEnterpriseTokenInventoryExportStatus.fromWire("started"))
                .isEqualTo(GitHubEnterpriseTokenInventoryExportStatus.STARTED);
    }

    @Test
    void fromWire_shouldReturnSuccess_whenKnownValue() {
        assertThat(GitHubEnterpriseTokenInventoryExportStatus.fromWire("success"))
                .isEqualTo(GitHubEnterpriseTokenInventoryExportStatus.SUCCESS);
    }

    @Test
    void fromWire_shouldReturnError_whenKnownValue() {
        assertThat(GitHubEnterpriseTokenInventoryExportStatus.fromWire("error"))
                .isEqualTo(GitHubEnterpriseTokenInventoryExportStatus.ERROR);
    }

    @Test
    void fromWire_shouldReturnUnknown_whenValueNotKnown() {
        assertThat(GitHubEnterpriseTokenInventoryExportStatus.fromWire("not-a-known-value"))
                .isEqualTo(GitHubEnterpriseTokenInventoryExportStatus.UNKNOWN);
    }
}
