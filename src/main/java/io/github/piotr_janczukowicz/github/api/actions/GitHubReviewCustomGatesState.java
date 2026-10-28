package io.github.piotr_janczukowicz.github.api.actions;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/** Whether to approve or reject deployment to the specified environments. */
@CatalogSchema({
    "components/schemas/review-custom-gates-state-required/properties/state",
    "operations/actions/review-pending-deployments-for-run/requestBody/properties/state"
})
public enum GitHubReviewCustomGatesState {
    APPROVED("approved"),
    REJECTED("rejected");

    private static final EnumUtils.WireIndex<GitHubReviewCustomGatesState> WIRE_INDEX =
            EnumUtils.index(GitHubReviewCustomGatesState.class, GitHubReviewCustomGatesState::wireValue);

    private final String wireValue;

    GitHubReviewCustomGatesState(final String wireValue) {
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
     * Type from a wire value.
     *
     * @param value JSON value
     * @return matching type
     * @throws NullPointerException if {@code value} is {@code null}
     * @throws IllegalArgumentException if {@code value} is not a known type
     */
    @JsonCreator
    public static GitHubReviewCustomGatesState fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX
                .fromWire(value)
                .orElseThrow(
                        () -> new IllegalArgumentException("Unknown GitHubReviewCustomGatesState value: " + value));
    }
}
