package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubEnterpriseTokenInventoryExportTest {

    @Test
    void accessors_shouldExposeEveryComponent() {
        final GitHubEnterpriseTokenInventoryExport value =
                EnterpriseAdminCatalogFixtures.enterpriseTokenInventoryExport();

        assertThat(value.exportId()).isEqualTo("sample-export_id");
        assertThat(value.status()).isEqualTo(GitHubEnterpriseTokenInventoryExportStatus.PENDING);
        assertThat(value.asOf()).isEqualTo("sample-as_of");
    }
}
