package io.github.piotr_janczukowicz.github.api.projects;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/** Sort direction for a project view. */
@CatalogSchema("components/schemas/projects-v2-view/properties/sort_by/items/items/oneOf/1")
public enum GitHubProjectViewSortDirection {
    ASC("asc"),
    DESC("desc"),

    /** A value this library version does not know. */
    UNKNOWN("unknown");

    private static final EnumUtils.WireIndex<GitHubProjectViewSortDirection> WIRE_INDEX =
            EnumUtils.index(GitHubProjectViewSortDirection.class, GitHubProjectViewSortDirection::wireValue);

    private final String wireValue;

    GitHubProjectViewSortDirection(final String wireValue) {
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
     * Sort direction from a wire value.
     *
     * @param value JSON value
     * @return matching direction, or {@link #UNKNOWN} for a value this library does not know
     * @throws NullPointerException if {@code value} is {@code null}
     */
    @JsonCreator
    public static GitHubProjectViewSortDirection fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX.fromWire(value, UNKNOWN);
    }
}
