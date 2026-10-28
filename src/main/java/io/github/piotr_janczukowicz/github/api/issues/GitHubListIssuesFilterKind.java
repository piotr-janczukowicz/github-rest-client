package io.github.piotr_janczukowicz.github.api.issues;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/** Issue list {@code filter} query parameter. */
@CatalogSchema({
    "operations/issues/list/parameters/0",
    "operations/issues/list-for-org/parameters/1",
    "operations/issues/list-for-authenticated-user/parameters/0"
})
public enum GitHubListIssuesFilterKind {
    ASSIGNED("assigned"),
    CREATED("created"),
    MENTIONED("mentioned"),
    SUBSCRIBED("subscribed"),
    REPOS("repos"),
    ALL("all");

    private static final EnumUtils.WireIndex<GitHubListIssuesFilterKind> WIRE_INDEX =
            EnumUtils.index(GitHubListIssuesFilterKind.class, GitHubListIssuesFilterKind::wireValue);

    private final String wireValue;

    GitHubListIssuesFilterKind(final String wireValue) {
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
     * Filter kind from a wire value.
     *
     * @param value query parameter value
     * @return matching filter kind
     * @throws NullPointerException if {@code value} is {@code null}
     * @throws IllegalArgumentException if {@code value} is not a known filter kind
     */
    @JsonCreator
    public static GitHubListIssuesFilterKind fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX
                .fromWire(value)
                .orElseThrow(() -> new IllegalArgumentException("Unknown GitHub list issues filter kind: " + value));
    }
}
