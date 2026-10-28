package io.github.piotr_janczukowicz.github.api.pullrequests;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/** Pull request list state filter. */
@CatalogSchema("operations/pulls/list/parameters/2")
public enum GitHubListPullRequestsState {
    OPEN("open"),
    CLOSED("closed"),
    ALL("all");

    private static final EnumUtils.WireIndex<GitHubListPullRequestsState> WIRE_INDEX =
            EnumUtils.index(GitHubListPullRequestsState.class, GitHubListPullRequestsState::wireValue);

    private final String wireValue;

    GitHubListPullRequestsState(final String wireValue) {
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
     * State filter for GitHub pull request list {@code state} parameters.
     *
     * @param value query parameter value
     * @return matching state
     * @throws NullPointerException if {@code value} is {@code null}
     * @throws IllegalArgumentException if {@code value} is not a known state
     */
    @JsonCreator
    public static GitHubListPullRequestsState fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX
                .fromWire(value)
                .orElseThrow(() -> new IllegalArgumentException("Unknown GitHub list pull requests state: " + value));
    }
}
