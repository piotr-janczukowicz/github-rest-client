package io.github.piotr_janczukowicz.github.api.actions;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/** Sort field of the actions caches list. */
@CatalogSchema("components/parameters/actions-cache-list-sort/schema")
public enum GitHubActionsCacheListSort {
    CREATED_AT("created_at"),
    LAST_ACCESSED_AT("last_accessed_at"),
    SIZE_IN_BYTES("size_in_bytes");

    private static final EnumUtils.WireIndex<GitHubActionsCacheListSort> WIRE_INDEX =
            EnumUtils.index(GitHubActionsCacheListSort.class, GitHubActionsCacheListSort::wireValue);

    private final String wireValue;

    GitHubActionsCacheListSort(final String wireValue) {
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
     * Type from a wire value.
     *
     * @param value JSON value
     * @return matching type
     * @throws NullPointerException if {@code value} is {@code null}
     * @throws IllegalArgumentException if {@code value} is not a known type
     */
    @JsonCreator
    public static GitHubActionsCacheListSort fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX
                .fromWire(value)
                .orElseThrow(() -> new IllegalArgumentException("Unknown GitHubActionsCacheListSort value: " + value));
    }
}
