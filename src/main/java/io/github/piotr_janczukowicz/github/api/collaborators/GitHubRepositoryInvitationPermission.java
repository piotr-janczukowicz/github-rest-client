package io.github.piotr_janczukowicz.github.api.collaborators;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/** Repository invitation permission. */
@CatalogSchema("components/schemas/repository-invitation/properties/permissions")
public enum GitHubRepositoryInvitationPermission {
    READ("read"),
    WRITE("write"),
    ADMIN("admin"),
    TRIAGE("triage"),
    TRIAGE_PLUS("triage_plus"),
    MAINTAIN("maintain"),
    /** A value this library version does not know. */
    UNKNOWN("unknown");

    private static final EnumUtils.WireIndex<GitHubRepositoryInvitationPermission> WIRE_INDEX = EnumUtils.index(
            GitHubRepositoryInvitationPermission.class, GitHubRepositoryInvitationPermission::wireValue);

    private final String wireValue;

    GitHubRepositoryInvitationPermission(final String wireValue) {
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
     * Permission from GitHub repository invitation payloads.
     *
     * @param value JSON value
     * @return matching permission
     * @throws NullPointerException if {@code value} is {@code null}
     */
    @JsonCreator
    public static GitHubRepositoryInvitationPermission fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX.fromWire(value, UNKNOWN);
    }
}
