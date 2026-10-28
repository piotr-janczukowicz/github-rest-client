package io.github.piotr_janczukowicz.github.api.codesecurity;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/** Attachment status of a code security configuration on a repository. */
@CatalogSchema({
    "components/schemas/code-security-configuration-for-repository/properties/status",
    "components/schemas/code-security-configuration-repositories/properties/status"
})
public enum GitHubCodeSecurityAttachmentStatus {
    ATTACHED("attached"),
    ATTACHING("attaching"),
    DETACHED("detached"),
    REMOVED("removed"),
    ENFORCED("enforced"),
    FAILED("failed"),
    UPDATING("updating"),
    REMOVED_BY_ENTERPRISE("removed_by_enterprise"),
    ENTERPRISE_ENFORCED("enterprise_enforced"),
    /** A value this library version does not know. */
    UNKNOWN("unknown");

    private static final EnumUtils.WireIndex<GitHubCodeSecurityAttachmentStatus> WIRE_INDEX =
            EnumUtils.index(GitHubCodeSecurityAttachmentStatus.class, GitHubCodeSecurityAttachmentStatus::wireValue);

    private final String wireValue;

    GitHubCodeSecurityAttachmentStatus(final String wireValue) {
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
     */
    @JsonCreator
    public static GitHubCodeSecurityAttachmentStatus fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX.fromWire(value, UNKNOWN);
    }
}
