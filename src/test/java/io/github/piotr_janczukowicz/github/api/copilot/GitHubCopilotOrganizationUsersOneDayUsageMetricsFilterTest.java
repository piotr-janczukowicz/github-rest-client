package io.github.piotr_janczukowicz.github.api.copilot;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.time.LocalDate;
import java.util.Map;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubCopilotOrganizationUsersOneDayUsageMetricsFilterTest {

    @Test
    void of_shouldSetDay_whenCalled() {
        final GitHubCopilotOrganizationUsersOneDayUsageMetricsFilter filter =
                GitHubCopilotOrganizationUsersOneDayUsageMetricsFilter.of(LocalDate.parse("2025-10-13"));

        assertThat(filter.day()).isEqualTo(LocalDate.parse("2025-10-13"));
    }

    @Test
    void toQueryParameters_shouldIncludeDay_whenFilterSet() {
        final GitHubCopilotOrganizationUsersOneDayUsageMetricsFilter filter =
                CopilotCatalogFixtures.copilotOrganizationUsersOneDayUsageMetricsFilter();

        assertThat(filter.toQueryParameters()).isEqualTo(Map.of("day", "2025-10-13"));
    }

    @Test
    void constructor_shouldThrow_whenDayNull() {
        final Throwable thrown = catchThrowable(() -> new GitHubCopilotOrganizationUsersOneDayUsageMetricsFilter(null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("day");
    }
}
