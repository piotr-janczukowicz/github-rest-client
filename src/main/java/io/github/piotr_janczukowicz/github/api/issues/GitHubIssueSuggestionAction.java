package io.github.piotr_janczukowicz.github.api.issues;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/** Kind of change proposed by an issue suggestion. */
@CatalogSchema({
    "components/schemas/issue-suggestion/properties/action",
    "operations/issues/list-suggestions/parameters/4"
})
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public enum GitHubIssueSuggestionAction {
    SET_TYPE("set_type"),
    ADD_LABEL("add_label"),
    ADD_FIELD("add_field"),
    ADD_ASSIGNEE("add_assignee"),
    CLOSE_ISSUE("close_issue");

    private static final EnumUtils.WireIndex<GitHubIssueSuggestionAction> WIRE_INDEX =
            EnumUtils.index(GitHubIssueSuggestionAction.class, GitHubIssueSuggestionAction::wireValue);

    private final String wireValue;

    GitHubIssueSuggestionAction(final String wireValue) {
        this.wireValue = wireValue;
    }

    /**
     * Value GitHub sends and expects in JSON and query parameters.
     *
     * @return wire string
     */
    @JsonValue
    public String wireValue() {
        return wireValue;
    }

    /**
     * Suggestion action from a wire value.
     *
     * @param value JSON or query parameter value
     * @return matching suggestion action
     * @throws NullPointerException if {@code value} is {@code null}
     * @throws IllegalArgumentException if {@code value} is not a known suggestion action
     */
    @JsonCreator
    public static GitHubIssueSuggestionAction fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX
                .fromWire(value)
                .orElseThrow(() -> new IllegalArgumentException("Unknown GitHub issue suggestion action: " + value));
    }
}
