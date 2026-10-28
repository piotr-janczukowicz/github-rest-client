package io.github.piotr_janczukowicz.github.api.issues;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/** Issue suggestion list {@code state} query parameter. */
@CatalogSchema("operations/issues/list-suggestions/parameters/3")
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public enum GitHubListIssueSuggestionsState {
    PENDING("pending"),
    APPLIED("applied"),
    APPROVED("approved"),
    DISMISSED("dismissed"),
    REPLACED("replaced"),
    INVALIDATED("invalidated"),
    ALL("all");

    private static final EnumUtils.WireIndex<GitHubListIssueSuggestionsState> WIRE_INDEX =
            EnumUtils.index(GitHubListIssueSuggestionsState.class, GitHubListIssueSuggestionsState::wireValue);

    private final String wireValue;

    GitHubListIssueSuggestionsState(final String wireValue) {
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
     * List suggestion state from a wire value.
     *
     * @param value query parameter value
     * @return matching list suggestion state
     * @throws NullPointerException if {@code value} is {@code null}
     * @throws IllegalArgumentException if {@code value} is not a known list suggestion state
     */
    @JsonCreator
    public static GitHubListIssueSuggestionsState fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX
                .fromWire(value)
                .orElseThrow(
                        () -> new IllegalArgumentException("Unknown GitHub list issue suggestions state: " + value));
    }
}
