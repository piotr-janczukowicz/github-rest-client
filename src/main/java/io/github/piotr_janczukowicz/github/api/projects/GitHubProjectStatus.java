package io.github.piotr_janczukowicz.github.api.projects;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/** Status of a project status update. */
@CatalogSchema("components/schemas/projects-v2-status-update/properties/status")
public enum GitHubProjectStatus {
    INACTIVE("INACTIVE"),
    ON_TRACK("ON_TRACK"),
    AT_RISK("AT_RISK"),
    OFF_TRACK("OFF_TRACK"),
    COMPLETE("COMPLETE"),
    /** A value this library version does not know. */
    UNKNOWN("unknown");

    private static final EnumUtils.WireIndex<GitHubProjectStatus> WIRE_INDEX =
            EnumUtils.index(GitHubProjectStatus.class, GitHubProjectStatus::wireValue);

    private final String wireValue;

    GitHubProjectStatus(final String wireValue) {
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
     * Project status from a wire value.
     *
     * @param value JSON value
     * @return matching status
     * @throws NullPointerException if {@code value} is {@code null}
     */
    @JsonCreator
    public static GitHubProjectStatus fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX.fromWire(value, UNKNOWN);
    }
}
