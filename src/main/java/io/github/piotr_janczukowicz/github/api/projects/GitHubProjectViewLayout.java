package io.github.piotr_janczukowicz.github.api.projects;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/** Layout of a project view. */
@CatalogSchema({
    "components/schemas/projects-v2-view/properties/layout",
    "operations/projects/create-view-for-org/requestBody/properties/layout",
    "operations/projects/create-view-for-user/requestBody/properties/layout"
})
public enum GitHubProjectViewLayout {
    TABLE("table"),
    BOARD("board"),
    ROADMAP("roadmap");

    private static final EnumUtils.WireIndex<GitHubProjectViewLayout> WIRE_INDEX =
            EnumUtils.index(GitHubProjectViewLayout.class, GitHubProjectViewLayout::wireValue);

    private final String wireValue;

    GitHubProjectViewLayout(final String wireValue) {
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
     * View layout from a wire value.
     *
     * @param value JSON value
     * @return matching layout
     * @throws NullPointerException if {@code value} is {@code null}
     * @throws IllegalArgumentException if {@code value} is not a known layout
     */
    @JsonCreator
    public static GitHubProjectViewLayout fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX
                .fromWire(value)
                .orElseThrow(() -> new IllegalArgumentException("Unknown GitHub project view layout: " + value));
    }
}
