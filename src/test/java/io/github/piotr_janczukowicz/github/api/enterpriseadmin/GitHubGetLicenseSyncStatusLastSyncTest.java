package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubGetLicenseSyncStatusLastSyncTest {

    @Test
    void accessors_shouldExposeEveryComponent() {
        final GitHubGetLicenseSyncStatusLastSync value = EnterpriseAdminCatalogFixtures.getLicenseSyncStatusLastSync();

        assertThat(value.date()).isEqualTo("sample-date");
        assertThat(value.status()).isEqualTo("sample-status");
        assertThat(value.error()).isEqualTo("sample-error");
    }
}
