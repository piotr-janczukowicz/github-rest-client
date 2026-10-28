package io.github.piotr_janczukowicz.github.api.metrics;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.List;

/**
 * Repository weekly code frequency statistics (additions / deletions per week).
 *
 * <p>Each element is a {@link GitHubCodeFrequencyStat} ({@code code-frequency-stat} in OpenAPI). Punch card responses
 * reuse the same item schema; see {@link GitHubPunchCardStats}.
 */
@CatalogSchema("operations/repos/get-code-frequency-stats/200")
public record GitHubCodeFrequencyStats(List<GitHubCodeFrequencyStat> stats) {

    public GitHubCodeFrequencyStats {
        requireNonNull(stats, "stats");
    }

    /**
     * Deserializes a stat array from JSON.
     *
     * @param stats stats
     * @return stats wrapper
     */
    @JsonCreator
    public static GitHubCodeFrequencyStats fromJson(final List<GitHubCodeFrequencyStat> stats) {
        return new GitHubCodeFrequencyStats(stats);
    }

    /**
     * Serializes as a stat array for JSON.
     *
     * @return stats
     */
    @JsonValue
    public List<GitHubCodeFrequencyStat> toJson() {
        return stats;
    }
}
