package io.github.piotr_janczukowicz.github.api.organizations;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/** API Insights route stats sort query parameter item. */
@CatalogSchema("components/parameters/api-insights-route-stats-sort/schema/items")
public enum GitHubApiInsightsRouteStatsSort {
    LAST_RATE_LIMITED_TIMESTAMP("last_rate_limited_timestamp"),
    LAST_REQUEST_TIMESTAMP("last_request_timestamp"),
    RATE_LIMITED_REQUEST_COUNT("rate_limited_request_count"),
    HTTP_METHOD("http_method"),
    API_ROUTE("api_route"),
    TOTAL_REQUEST_COUNT("total_request_count");

    private static final EnumUtils.WireIndex<GitHubApiInsightsRouteStatsSort> WIRE_INDEX =
            EnumUtils.index(GitHubApiInsightsRouteStatsSort.class, GitHubApiInsightsRouteStatsSort::wireValue);

    private final String wireValue;

    GitHubApiInsightsRouteStatsSort(final String wireValue) {
        this.wireValue = wireValue;
    }

    /**
     * Value GitHub sends and expects in query parameters.
     *
     * @return wire string
     */
    @JsonValue
    public String wireValue() {
        return wireValue;
    }

    /**
     * Route stats sort field from a wire value.
     *
     * @param value query parameter value
     * @return matching sort field
     * @throws NullPointerException if {@code value} is {@code null}
     * @throws IllegalArgumentException if {@code value} is not a known sort field
     */
    @JsonCreator
    public static GitHubApiInsightsRouteStatsSort fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX
                .fromWire(value)
                .orElseThrow(
                        () -> new IllegalArgumentException("Unknown GitHub API insights route stats sort: " + value));
    }
}
