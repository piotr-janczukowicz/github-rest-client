package io.github.piotr_janczukowicz.github.api.copilot;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubCopilotUsageMetrics28DayReportTest {

    @Test
    void constructor_shouldThrow_whenDownloadLinksNull() {
        final Throwable thrown = catchThrowable(() -> GitHubCopilotUsageMetrics28DayReportBuilder.builder(
                        CopilotCatalogFixtures.copilotUsageMetrics28DayReport())
                .downloadLinks(null)
                .build());

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("downloadLinks");
    }

    @Test
    void constructor_shouldThrow_whenReportStartDayNull() {
        final Throwable thrown = catchThrowable(() -> GitHubCopilotUsageMetrics28DayReportBuilder.builder(
                        CopilotCatalogFixtures.copilotUsageMetrics28DayReport())
                .reportStartDay(null)
                .build());

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("reportStartDay");
    }

    @Test
    void constructor_shouldThrow_whenReportEndDayNull() {
        final Throwable thrown = catchThrowable(() -> GitHubCopilotUsageMetrics28DayReportBuilder.builder(
                        CopilotCatalogFixtures.copilotUsageMetrics28DayReport())
                .reportEndDay(null)
                .build());

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("reportEndDay");
    }
}
