package io.github.piotr_janczukowicz.github.api.actions;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/** Default workflow permissions granted to the GITHUB_TOKEN when running workflows. */
@CatalogSchema("components/schemas/actions-default-workflow-permissions")
public enum GitHubActionsDefaultWorkflowPermissions {
    READ("read"),
    WRITE("write");

    private static final EnumUtils.WireIndex<GitHubActionsDefaultWorkflowPermissions> WIRE_INDEX = EnumUtils.index(
            GitHubActionsDefaultWorkflowPermissions.class, GitHubActionsDefaultWorkflowPermissions::wireValue);

    private final String wireValue;

    GitHubActionsDefaultWorkflowPermissions(final String wireValue) {
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
    public static GitHubActionsDefaultWorkflowPermissions fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX
                .fromWire(value)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Unknown GitHubActionsDefaultWorkflowPermissions value: " + value));
    }
}
