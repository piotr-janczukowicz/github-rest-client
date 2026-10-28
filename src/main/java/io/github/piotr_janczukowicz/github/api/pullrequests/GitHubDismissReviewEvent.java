package io.github.piotr_janczukowicz.github.api.pullrequests;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/** Pull request review dismissal action. */
@CatalogSchema("operations/pulls/dismiss-review/requestBody/properties/event")
public enum GitHubDismissReviewEvent {
    DISMISS("DISMISS");

    private static final EnumUtils.WireIndex<GitHubDismissReviewEvent> WIRE_INDEX =
            EnumUtils.index(GitHubDismissReviewEvent.class, GitHubDismissReviewEvent::wireValue);

    private final String wireValue;

    GitHubDismissReviewEvent(final String wireValue) {
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
     * Dismissal event from a wire value.
     *
     * @param value JSON value
     * @return matching event
     * @throws NullPointerException if {@code value} is {@code null}
     * @throws IllegalArgumentException if {@code value} is not a known event
     */
    @JsonCreator
    public static GitHubDismissReviewEvent fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX
                .fromWire(value)
                .orElseThrow(() -> new IllegalArgumentException("Unknown GitHub dismiss review event: " + value));
    }
}
