package io.github.piotr_janczukowicz.github.api.organizations;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/** Type of events to include in the audit log. */
@CatalogSchema("operations/orgs/get-audit-log/parameters/2")
@Flavors({GitHubFlavor.GHEC, GitHubFlavor.GHES})
public enum GitHubAuditLogInclude {
    /** Web events only. */
    WEB("web"),
    /** Git events only. */
    GIT("git"),
    /** All events. */
    ALL("all");

    private static final EnumUtils.WireIndex<GitHubAuditLogInclude> WIRE_INDEX =
            EnumUtils.index(GitHubAuditLogInclude.class, GitHubAuditLogInclude::wireValue);

    private final String wireValue;

    GitHubAuditLogInclude(final String wireValue) {
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
     * Value from a wire string.
     *
     * @param value JSON value
     * @return matching value
     * @throws NullPointerException if {@code value} is {@code null}
     * @throws IllegalArgumentException if {@code value} is not a known value
     */
    @JsonCreator
    public static GitHubAuditLogInclude fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX
                .fromWire(value)
                .orElseThrow(() -> new IllegalArgumentException("Unknown GitHub audit log include: " + value));
    }
}
