package io.github.piotr_janczukowicz.github.api.pullrequests;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/** Pull request stack state. */
@CatalogSchema("components/schemas/pull-request-stack-minimal/properties/pull_requests/items/properties/state")
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public enum GitHubPullRequestStackState {
    OPEN("open"),
    CLOSED("closed");

    private static final EnumUtils.WireIndex<GitHubPullRequestStackState> WIRE_INDEX =
            EnumUtils.index(GitHubPullRequestStackState.class, GitHubPullRequestStackState::wireValue);

    private final String wireValue;

    GitHubPullRequestStackState(final String wireValue) {
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
     * State from a wire value.
     *
     * @param value JSON value
     * @return matching state
     * @throws NullPointerException if {@code value} is {@code null}
     * @throws IllegalArgumentException if {@code value} is not a known state
     */
    @JsonCreator
    public static GitHubPullRequestStackState fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX
                .fromWire(value)
                .orElseThrow(() -> new IllegalArgumentException("Unknown GitHub pull request stack state: " + value));
    }
}
