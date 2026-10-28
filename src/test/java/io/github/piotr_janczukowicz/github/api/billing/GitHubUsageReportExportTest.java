package io.github.piotr_janczukowicz.github.api.billing;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubUsageReportExportTest {

    @Test
    void fixture_shouldBuildRecord_whenFixtureUsed() {
        assertThat(BillingCatalogFixtures.usageReportExport()).isNotNull();
    }

    @Test
    void constructor_shouldLeaveDownloadUrlsNull_whenNull() {
        final GitHubUsageReportExport value = GitHubUsageReportExportBuilder.builder(
                        BillingCatalogFixtures.usageReportExport())
                .downloadUrls(null)
                .build();

        assertThat(value.downloadUrls()).isNull();
    }
}
