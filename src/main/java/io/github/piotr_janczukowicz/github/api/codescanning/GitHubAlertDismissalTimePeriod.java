package io.github.piotr_janczukowicz.github.api.codescanning;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/** Time period to filter dismissal requests by. */
@CatalogSchema({
    "operations/code-scanning/list-org-dismissal-requests/parameters/3",
    "operations/code-scanning/list-dismissal-requests-for-repo/parameters/4",
    "operations/dependabot/list-dismissal-requests-for-repo/parameters/4"
})
@Flavors({GitHubFlavor.GHEC, GitHubFlavor.GHES})
public enum GitHubAlertDismissalTimePeriod {
    /** hour. */
    HOUR("hour"),
    /** day. */
    DAY("day"),
    /** week. */
    WEEK("week"),
    /** month. */
    MONTH("month");

    private static final EnumUtils.WireIndex<GitHubAlertDismissalTimePeriod> WIRE_INDEX =
            EnumUtils.index(GitHubAlertDismissalTimePeriod.class, GitHubAlertDismissalTimePeriod::wireValue);

    private final String wireValue;

    GitHubAlertDismissalTimePeriod(final String wireValue) {
        this.wireValue = wireValue;
    }

    /**
     * Value GitHub sends and expects in JSON.
     *
     * @return wire string
     */
    @JsonValue
    public String wireValue() {
        return wireValue;
    }

    /**
     * Resolves time period to filter dismissal requests by from its wire value.
     *
     * @param value JSON value
     * @return matching constant
     * @throws NullPointerException if {@code value} is {@code null}
     * @throws IllegalArgumentException if {@code value} is not a known value
     */
    @JsonCreator
    public static GitHubAlertDismissalTimePeriod fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX
                .fromWire(value)
                .orElseThrow(
                        () -> new IllegalArgumentException("Unknown GitHub alert dismissal time period: " + value));
    }
}
