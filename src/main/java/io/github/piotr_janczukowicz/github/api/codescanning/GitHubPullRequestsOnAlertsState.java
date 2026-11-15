package io.github.piotr_janczukowicz.github.api.codescanning;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/** The state of the referenced pull request. */
@CatalogSchema("components/schemas/pull-requests-on-alerts/properties/state")
public enum GitHubPullRequestsOnAlertsState {
    OPEN("open"),
    CLOSED("closed"),
    MERGED("merged"),
    /** A value this library version does not know. */
    UNRECOGNIZED("unrecognized");

    private static final EnumUtils.WireIndex<GitHubPullRequestsOnAlertsState> WIRE_INDEX =
            EnumUtils.index(GitHubPullRequestsOnAlertsState.class, GitHubPullRequestsOnAlertsState::wireValue);

    private final String wireValue;

    GitHubPullRequestsOnAlertsState(final String wireValue) {
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
     * Resolves the constant from its wire value.
     *
     * @param value JSON value
     * @return matching constant, or {@link #UNRECOGNIZED} when the value is not known
     * @throws NullPointerException if {@code value} is {@code null}
     */
    @JsonCreator
    public static GitHubPullRequestsOnAlertsState fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX.fromWire(value, UNRECOGNIZED);
    }
}
