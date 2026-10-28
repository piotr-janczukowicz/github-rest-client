package io.github.piotr_janczukowicz.github.api.collaborators;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/** Collaborator permission filter. */
@CatalogSchema("operations/repos/list-collaborators/parameters/3")
public enum GitHubCollaboratorPermission {
    PULL("pull"),
    TRIAGE("triage"),
    TRIAGE_PLUS("triage_plus"),
    PUSH("push"),
    MAINTAIN("maintain"),
    ADMIN("admin");

    private static final EnumUtils.WireIndex<GitHubCollaboratorPermission> WIRE_INDEX =
            EnumUtils.index(GitHubCollaboratorPermission.class, GitHubCollaboratorPermission::wireValue);

    private final String wireValue;

    GitHubCollaboratorPermission(final String wireValue) {
        this.wireValue = wireValue;
    }

    /**
     * Value GitHub sends and expects in query parameters.
     *
     * @return wire string
     */
    @JsonValue
    public String wireValue() {
        return wireValue;
    }

    /**
     * Permission from GitHub's collaborator list {@code permission} parameter.
     *
     * @param value query parameter value
     * @return matching permission
     * @throws NullPointerException if {@code value} is {@code null}
     * @throws IllegalArgumentException if {@code value} is not a known permission
     */
    @JsonCreator
    public static GitHubCollaboratorPermission fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX
                .fromWire(value)
                .orElseThrow(() -> new IllegalArgumentException("Unknown GitHub collaborator permission: " + value));
    }
}
