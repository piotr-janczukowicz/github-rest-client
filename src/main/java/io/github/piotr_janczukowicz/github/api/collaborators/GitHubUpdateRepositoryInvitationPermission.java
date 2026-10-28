package io.github.piotr_janczukowicz.github.api.collaborators;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/** Permission to grant on a repository invitation. */
@CatalogSchema("operations/repos/update-invitation/requestBody/properties/permissions")
public enum GitHubUpdateRepositoryInvitationPermission {
    READ("read"),
    WRITE("write"),
    MAINTAIN("maintain"),
    TRIAGE("triage"),
    ADMIN("admin");

    private static final EnumUtils.WireIndex<GitHubUpdateRepositoryInvitationPermission> WIRE_INDEX = EnumUtils.index(
            GitHubUpdateRepositoryInvitationPermission.class, GitHubUpdateRepositoryInvitationPermission::wireValue);

    private final String wireValue;

    GitHubUpdateRepositoryInvitationPermission(final String wireValue) {
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
     * Permission from GitHub repository invitation update payloads.
     *
     * @param value JSON value
     * @return matching permission
     * @throws NullPointerException if {@code value} is {@code null}
     * @throws IllegalArgumentException if {@code value} is not a known permission
     */
    @JsonCreator
    public static GitHubUpdateRepositoryInvitationPermission fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX
                .fromWire(value)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Unknown GitHub update repository invitation permission: " + value));
    }
}
