package io.github.piotr_janczukowicz.github.api.copilot;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.time.LocalDate;
import java.util.Map;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubCopilotOrganizationReposOneDayReportFilterTest {

    @Test
    void of_shouldSetDay_whenCalled() {
        final GitHubCopilotOrganizationReposOneDayReportFilter filter =
                GitHubCopilotOrganizationReposOneDayReportFilter.of(LocalDate.parse("2025-10-13"));

        assertThat(filter.day()).isEqualTo(LocalDate.parse("2025-10-13"));
    }

    @Test
    void toQueryParameters_shouldIncludeDay_whenFilterSet() {
        final GitHubCopilotOrganizationReposOneDayReportFilter filter =
                CopilotCatalogFixtures.copilotOrganizationReposOneDayReportFilter();

        assertThat(filter.toQueryParameters()).isEqualTo(Map.of("day", "2025-10-13"));
    }

    @Test
    void constructor_shouldThrow_whenDayNull() {
        final Throwable thrown = catchThrowable(() -> new GitHubCopilotOrganizationReposOneDayReportFilter(null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("day");
    }
}
