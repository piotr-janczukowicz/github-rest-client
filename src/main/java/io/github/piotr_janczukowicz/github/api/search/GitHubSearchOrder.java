package io.github.piotr_janczukowicz.github.api.search;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/** Sort order for GitHub Search {@code order} query parameter. */
@CatalogSchema({"components/parameters/order/schema", "operations/search/code/parameters/2"})
public enum GitHubSearchOrder {
    ASC("asc"),
    DESC("desc");

    private static final EnumUtils.WireIndex<GitHubSearchOrder> WIRE_INDEX =
            EnumUtils.index(GitHubSearchOrder.class, GitHubSearchOrder::wireValue);

    private final String wireValue;

    GitHubSearchOrder(final String wireValue) {
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
     * Sort order from a wire value.
     *
     * @param value query parameter value
     * @return matching sort order
     * @throws NullPointerException if {@code value} is {@code null}
     * @throws IllegalArgumentException if {@code value} is not a known sort order
     */
    @JsonCreator
    public static GitHubSearchOrder fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX
                .fromWire(value)
                .orElseThrow(() -> new IllegalArgumentException("Unknown GitHub search order: " + value));
    }
}
