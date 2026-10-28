package io.github.piotr_janczukowicz.github.api.migrations;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/**
 * Import progress status.
 *
 * @deprecated Endpoint closing down notice: due to very low levels of usage and available alternatives, this endpoint
 *     is closing down and will no longer be available from 00:00 UTC on April 12, 2024. Deprecation date
 *     {@code 2023-10-12}; removal date {@code 2024-04-12}.
 */
@Deprecated(since = "2023-10-12")
@CatalogSchema("components/schemas/import/properties/status")
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public enum GitHubImportStatus {
    AUTH("auth"),
    ERROR("error"),
    NONE("none"),
    DETECTING("detecting"),
    CHOOSE("choose"),
    AUTH_FAILED("auth_failed"),
    IMPORTING("importing"),
    MAPPING("mapping"),
    WAITING_TO_PUSH("waiting_to_push"),
    PUSHING("pushing"),
    COMPLETE("complete"),
    SETUP("setup"),
    UNKNOWN("unknown"),
    DETECTION_FOUND_MULTIPLE("detection_found_multiple"),
    DETECTION_FOUND_NOTHING("detection_found_nothing"),
    DETECTION_NEEDS_AUTH("detection_needs_auth");

    private static final EnumUtils.WireIndex<GitHubImportStatus> WIRE_INDEX =
            EnumUtils.index(GitHubImportStatus.class, GitHubImportStatus::wireValue);

    private final String wireValue;

    GitHubImportStatus(final String wireValue) {
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
     * Import status from a wire value.
     *
     * @param value JSON value
     * @return matching status
     * @throws NullPointerException if {@code value} is {@code null}
     * @throws IllegalArgumentException if {@code value} is not a known status
     */
    @JsonCreator
    public static GitHubImportStatus fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX
                .fromWire(value)
                .orElseThrow(() -> new IllegalArgumentException("Unknown GitHub import status: " + value));
    }
}
