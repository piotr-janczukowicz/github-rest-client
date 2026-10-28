package io.github.piotr_janczukowicz.github.api.copilot;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/** Mode of assigning new Copilot seats. */
@CatalogSchema("components/schemas/copilot-organization-details/properties/seat_management_setting")
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public enum GitHubCopilotOrganizationSeatManagementSetting {
    ASSIGN_ALL("assign_all"),
    ASSIGN_SELECTED("assign_selected"),
    DISABLED("disabled"),
    UNCONFIGURED("unconfigured"),
    /** A value this library version does not know. */
    UNKNOWN("unknown");

    private static final EnumUtils.WireIndex<GitHubCopilotOrganizationSeatManagementSetting> WIRE_INDEX =
            EnumUtils.index(
                    GitHubCopilotOrganizationSeatManagementSetting.class,
                    GitHubCopilotOrganizationSeatManagementSetting::wireValue);

    private final String wireValue;

    GitHubCopilotOrganizationSeatManagementSetting(final String wireValue) {
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
     * Seat management setting from a wire value.
     *
     * @param value JSON value
     * @return matching setting
     * @throws NullPointerException if {@code value} is {@code null}
     */
    @JsonCreator
    public static GitHubCopilotOrganizationSeatManagementSetting fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX.fromWire(value, UNKNOWN);
    }
}
