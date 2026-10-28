package io.github.piotr_janczukowicz.github.api.deployments;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/** The state of the status. */
@CatalogSchema({
    "components/schemas/deployment-status/properties/state",
    "operations/repos/create-deployment-status/requestBody/properties/state"
})
public enum GitHubDeploymentStatusState {
    ERROR("error"),
    FAILURE("failure"),
    INACTIVE("inactive"),
    IN_PROGRESS("in_progress"),
    QUEUED("queued"),
    PENDING("pending"),
    SUCCESS("success");

    private static final EnumUtils.WireIndex<GitHubDeploymentStatusState> WIRE_INDEX =
            EnumUtils.index(GitHubDeploymentStatusState.class, GitHubDeploymentStatusState::wireValue);

    private final String wireValue;

    GitHubDeploymentStatusState(final String wireValue) {
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
     * Deployment status state from a wire value.
     *
     * @param value JSON value
     * @return matching state
     * @throws NullPointerException if {@code value} is {@code null}
     * @throws IllegalArgumentException if {@code value} is not a known state
     */
    @JsonCreator
    public static GitHubDeploymentStatusState fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX
                .fromWire(value)
                .orElseThrow(() -> new IllegalArgumentException("Unknown GitHub deployment status state: " + value));
    }
}
