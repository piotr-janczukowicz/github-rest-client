package io.github.piotr_janczukowicz.github.api.copilot;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubCopilotUsageMetrics1DayReportTest {

    @Test
    void constructor_shouldThrow_whenDownloadLinksNull() {
        final Throwable thrown = catchThrowable(() -> GitHubCopilotUsageMetrics1DayReportBuilder.builder(
                        CopilotCatalogFixtures.copilotUsageMetrics1DayReport())
                .downloadLinks(null)
                .build());

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("downloadLinks");
    }

    @Test
    void constructor_shouldThrow_whenReportDayNull() {
        final Throwable thrown = catchThrowable(() -> GitHubCopilotUsageMetrics1DayReportBuilder.builder(
                        CopilotCatalogFixtures.copilotUsageMetrics1DayReport())
                .reportDay(null)
                .build());

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("reportDay");
    }
}
