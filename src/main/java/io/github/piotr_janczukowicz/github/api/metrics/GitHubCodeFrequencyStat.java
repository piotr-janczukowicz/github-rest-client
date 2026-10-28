package io.github.piotr_janczukowicz.github.api.metrics;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.List;

/**
 * Triple of integer statistics. Indices are shared by code-frequency stats
 * ({@link #week()}/{@link #additions()}/{@link #deletions()}) and punch-card stats
 * ({@link #day()}/{@link #hour()}/{@link #commits()}). Named accessors throw {@link IllegalArgumentException} when the
 * requested index is missing.
 */
@CatalogSchema("components/schemas/code-frequency-stat")
public record GitHubCodeFrequencyStat(List<Integer> values) {

    public GitHubCodeFrequencyStat {
        requireNonNull(values, "values");
    }

    /** Week timestamp (code-frequency index 0). */
    public int week() {
        return at(0, "week");
    }

    /** Lines added (code-frequency index 1). */
    public int additions() {
        return at(1, "additions");
    }

    /** Lines deleted (code-frequency index 2). */
    public int deletions() {
        return at(2, "deletions");
    }

    /** Day of week (punch-card index 0). */
    public int day() {
        return at(0, "day");
    }

    /** Hour of day (punch-card index 1). */
    public int hour() {
        return at(1, "hour");
    }

    /** Commit count (punch-card index 2). */
    public int commits() {
        return at(2, "commits");
    }

    /**
     * Deserializes a stat array from JSON.
     *
     * @param values stat values
     * @return stat wrapper
     */
    @JsonCreator
    public static GitHubCodeFrequencyStat fromJson(final List<Integer> values) {
        return new GitHubCodeFrequencyStat(values);
    }

    /**
     * Serializes as a stat array for JSON.
     *
     * @return stat values
     */
    @JsonValue
    public List<Integer> toJson() {
        return values;
    }

    private int at(final int index, final String name) {
        if (values.size() <= index) {
            throw new IllegalArgumentException("Missing " + name + " at index " + index);
        }
        return values.get(index);
    }
}
