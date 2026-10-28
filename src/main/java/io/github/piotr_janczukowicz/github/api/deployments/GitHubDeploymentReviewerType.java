package io.github.piotr_janczukowicz.github.api.deployments;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/** The type of reviewer. */
@CatalogSchema("components/schemas/deployment-reviewer-type")
public enum GitHubDeploymentReviewerType {
    USER("User"),
    TEAM("Team");

    private static final EnumUtils.WireIndex<GitHubDeploymentReviewerType> WIRE_INDEX =
            EnumUtils.index(GitHubDeploymentReviewerType.class, GitHubDeploymentReviewerType::wireValue);

    private final String wireValue;

    GitHubDeploymentReviewerType(final String wireValue) {
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
     * Deployment reviewer type from a wire value.
     *
     * @param value JSON value
     * @return matching type
     * @throws NullPointerException if {@code value} is {@code null}
     * @throws IllegalArgumentException if {@code value} is not a known type
     */
    @JsonCreator
    public static GitHubDeploymentReviewerType fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX
                .fromWire(value)
                .orElseThrow(() -> new IllegalArgumentException("Unknown GitHub deployment reviewer type: " + value));
    }
}
