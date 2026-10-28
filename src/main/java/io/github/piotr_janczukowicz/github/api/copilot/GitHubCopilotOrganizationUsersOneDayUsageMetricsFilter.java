package io.github.piotr_janczukowicz.github.api.copilot;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.time.LocalDate;
import java.util.Map;

/**
 * Query filters for {@code GET /orgs/{org}/copilot/metrics/reports/users-1-day}, excluding pagination.
 *
 * @param day The day to request data for, in {@code YYYY-MM-DD} format.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@CatalogSchema("operations/copilot/copilot-organization-users-one-day-usage-metrics/parameters")
@RecordBuilder
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public record GitHubCopilotOrganizationUsersOneDayUsageMetricsFilter(LocalDate day) {

    public GitHubCopilotOrganizationUsersOneDayUsageMetricsFilter {
        requireNonNull(day, "day");
    }

    /**
     * Filter for one day.
     *
     * @param day The day to request data for
     * @return filter
     */
    public static GitHubCopilotOrganizationUsersOneDayUsageMetricsFilter of(final LocalDate day) {
        return GitHubCopilotOrganizationUsersOneDayUsageMetricsFilterBuilder.builder()
                .day(day)
                .build();
    }

    /**
     * Non-null query parameters for this filter.
     *
     * @return query map
     */
    public Map<String, String> toQueryParameters() {
        return Map.of("day", day.toString());
    }
}
