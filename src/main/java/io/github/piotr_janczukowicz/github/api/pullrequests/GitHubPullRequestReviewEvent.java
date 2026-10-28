package io.github.piotr_janczukowicz.github.api.pullrequests;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/** Pull request review action for create and submit requests. */
@CatalogSchema({
    "operations/pulls/create-review/requestBody/properties/event",
    "operations/pulls/submit-review/requestBody/properties/event"
})
public enum GitHubPullRequestReviewEvent {
    APPROVE("APPROVE"),
    REQUEST_CHANGES("REQUEST_CHANGES"),
    COMMENT("COMMENT");

    private static final EnumUtils.WireIndex<GitHubPullRequestReviewEvent> WIRE_INDEX =
            EnumUtils.index(GitHubPullRequestReviewEvent.class, GitHubPullRequestReviewEvent::wireValue);

    private final String wireValue;

    GitHubPullRequestReviewEvent(final String wireValue) {
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
     * Review event from a wire value.
     *
     * @param value JSON value
     * @return matching event
     * @throws NullPointerException if {@code value} is {@code null}
     * @throws IllegalArgumentException if {@code value} is not a known event
     */
    @JsonCreator
    public static GitHubPullRequestReviewEvent fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX
                .fromWire(value)
                .orElseThrow(() -> new IllegalArgumentException("Unknown GitHub pull request review event: " + value));
    }
}
