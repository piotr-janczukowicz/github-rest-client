package io.github.piotr_janczukowicz.github.api.copilot;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/** Organization policy for allowing or disallowing a Copilot feature. */
@CatalogSchema({
    "components/schemas/copilot-organization-details/properties/ide_chat",
    "components/schemas/copilot-organization-details/properties/platform_chat",
    "components/schemas/copilot-organization-details/properties/cli"
})
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public enum GitHubCopilotOrganizationFeatureSetting {
    ENABLED("enabled"),
    DISABLED("disabled"),
    UNCONFIGURED("unconfigured"),
    /** A value this library version does not know. */
    UNKNOWN("unknown");

    private static final EnumUtils.WireIndex<GitHubCopilotOrganizationFeatureSetting> WIRE_INDEX = EnumUtils.index(
            GitHubCopilotOrganizationFeatureSetting.class, GitHubCopilotOrganizationFeatureSetting::wireValue);

    private final String wireValue;

    GitHubCopilotOrganizationFeatureSetting(final String wireValue) {
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
     * Feature setting from a wire value.
     *
     * @param value JSON value
     * @return matching setting
     * @throws NullPointerException if {@code value} is {@code null}
     */
    @JsonCreator
    public static GitHubCopilotOrganizationFeatureSetting fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX.fromWire(value, UNKNOWN);
    }
}
