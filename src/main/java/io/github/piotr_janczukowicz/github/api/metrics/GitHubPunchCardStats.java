package io.github.piotr_janczukowicz.github.api.metrics;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.List;

/**
 * Repository punch card statistics (day / hour / commit-count triples).
 *
 * <p>Each element is a {@link GitHubCodeFrequencyStat} ({@code code-frequency-stat} in OpenAPI). Code frequency
 * responses reuse the same item schema; see {@link GitHubCodeFrequencyStats}.
 */
@CatalogSchema("operations/repos/get-punch-card-stats/200")
public record GitHubPunchCardStats(List<GitHubCodeFrequencyStat> stats) {

    public GitHubPunchCardStats {
        requireNonNull(stats, "stats");
    }

    /**
     * Deserializes a punch card array from JSON.
     *
     * @param stats stats
     * @return stats wrapper
     */
    @JsonCreator
    public static GitHubPunchCardStats fromJson(final List<GitHubCodeFrequencyStat> stats) {
        return new GitHubPunchCardStats(stats);
    }

    /**
     * Serializes as a punch card array for JSON.
     *
     * @return stats
     */
    @JsonValue
    public List<GitHubCodeFrequencyStat> toJson() {
        return stats;
    }
}
