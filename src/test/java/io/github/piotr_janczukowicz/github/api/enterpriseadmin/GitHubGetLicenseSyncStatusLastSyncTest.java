package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.time.Instant;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubGetLicenseSyncStatusLastSyncTest {

    @Test
    void accessors_shouldExposeEveryComponent() {
        final GitHubGetLicenseSyncStatusLastSync value = EnterpriseAdminCatalogFixtures.getLicenseSyncStatusLastSync();

        assertThat(value.date()).isEqualTo(Instant.parse("2026-01-01T00:00:00Z"));
        assertThat(value.status()).isEqualTo("sample-status");
        assertThat(value.error()).isEqualTo("sample-error");
    }
}
