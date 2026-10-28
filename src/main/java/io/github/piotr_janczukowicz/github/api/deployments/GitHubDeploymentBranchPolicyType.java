package io.github.piotr_janczukowicz.github.api.deployments;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/** Whether this rule targets a branch or tag. */
@CatalogSchema({
    "components/schemas/deployment-branch-policy/properties/type",
    "components/schemas/deployment-branch-policy-name-pattern-with-type/properties/type"
})
public enum GitHubDeploymentBranchPolicyType {
    BRANCH("branch"),
    TAG("tag");

    private static final EnumUtils.WireIndex<GitHubDeploymentBranchPolicyType> WIRE_INDEX =
            EnumUtils.index(GitHubDeploymentBranchPolicyType.class, GitHubDeploymentBranchPolicyType::wireValue);

    private final String wireValue;

    GitHubDeploymentBranchPolicyType(final String wireValue) {
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
     * Deployment branch policy type from a wire value.
     *
     * @param value JSON value
     * @return matching type
     * @throws NullPointerException if {@code value} is {@code null}
     * @throws IllegalArgumentException if {@code value} is not a known type
     */
    @JsonCreator
    public static GitHubDeploymentBranchPolicyType fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX
                .fromWire(value)
                .orElseThrow(
                        () -> new IllegalArgumentException("Unknown GitHub deployment branch policy type: " + value));
    }
}
