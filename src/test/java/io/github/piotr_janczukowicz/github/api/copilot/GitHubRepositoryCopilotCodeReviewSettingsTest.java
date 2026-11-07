package io.github.piotr_janczukowicz.github.api.copilot;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubRepositoryCopilotCodeReviewSettingsTest {

    @Test
    void constructor_shouldThrow_whenReviewEffortNull() {
        final GitHubRepositoryCopilotCodeReviewSettingsBuilder builder =
                GitHubRepositoryCopilotCodeReviewSettingsBuilder.builder(
                                CopilotCatalogFixtures.repositoryCopilotCodeReviewSettings())
                        .reviewEffort(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("reviewEffort");
    }

    @Test
    void constructor_shouldThrow_whenAvailableReviewEffortsNull() {
        final GitHubRepositoryCopilotCodeReviewSettingsBuilder builder =
                GitHubRepositoryCopilotCodeReviewSettingsBuilder.builder(
                                CopilotCatalogFixtures.repositoryCopilotCodeReviewSettings())
                        .availableReviewEfforts(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("availableReviewEfforts");
    }

    @Test
    void constructor_shouldThrow_whenSeverityFilterNull() {
        final GitHubRepositoryCopilotCodeReviewSettingsBuilder builder =
                GitHubRepositoryCopilotCodeReviewSettingsBuilder.builder(
                                CopilotCatalogFixtures.repositoryCopilotCodeReviewSettings())
                        .severityFilter(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("severityFilter");
    }
}
