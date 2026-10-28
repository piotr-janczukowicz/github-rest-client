package io.github.piotr_janczukowicz.github.api.copilot;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/** Order of usage records. */
@CatalogSchema("operations/copilot/get-usage-records/parameters/5")
@Flavors(GitHubFlavor.GHEC)
public enum GitHubGetUsageRecordsSortOrder {
    /** Desc. */
    DESC("desc"),
    /** Asc. */
    ASC("asc");

    private static final EnumUtils.WireIndex<GitHubGetUsageRecordsSortOrder> WIRE_INDEX =
            EnumUtils.index(GitHubGetUsageRecordsSortOrder.class, GitHubGetUsageRecordsSortOrder::wireValue);

    private final String wireValue;

    GitHubGetUsageRecordsSortOrder(final String wireValue) {
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
     * Value from a wire string.
     *
     * @param value JSON value
     * @return matching value
     * @throws NullPointerException if {@code value} is {@code null}
     */
    @JsonCreator
    public static GitHubGetUsageRecordsSortOrder fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX
                .fromWire(value)
                .orElseThrow(
                        () -> new IllegalArgumentException("Unknown GitHub get usage records sort order: " + value));
    }
}
