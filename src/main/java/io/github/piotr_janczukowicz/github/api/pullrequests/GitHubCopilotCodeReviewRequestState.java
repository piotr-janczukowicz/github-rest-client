package io.github.piotr_janczukowicz.github.api.pullrequests;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/** The current state of the review request. */
@CatalogSchema("components/schemas/copilot-code-review-request/properties/state")
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public enum GitHubCopilotCodeReviewRequestState {
    PENDING("PENDING");

    private static final EnumUtils.WireIndex<GitHubCopilotCodeReviewRequestState> WIRE_INDEX =
            EnumUtils.index(GitHubCopilotCodeReviewRequestState.class, GitHubCopilotCodeReviewRequestState::wireValue);

    private final String wireValue;

    GitHubCopilotCodeReviewRequestState(final String wireValue) {
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
     * @return matching constant
     * @throws NullPointerException if {@code value} is {@code null}
     * @throws IllegalArgumentException if {@code value} is not a known value
     */
    @JsonCreator
    public static GitHubCopilotCodeReviewRequestState fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX
                .fromWire(value)
                .orElseThrow(() ->
                        new IllegalArgumentException("Unknown GitHub copilot code review request state: " + value));
    }
}
