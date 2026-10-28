package io.github.piotr_janczukowicz.github.api.copilot;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/** Organization policy for allowing or blocking suggestions matching public code. */
@CatalogSchema("components/schemas/copilot-organization-details/properties/public_code_suggestions")
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public enum GitHubCopilotOrganizationPublicCodeSuggestions {
    ALLOW("allow"),
    BLOCK("block"),
    UNCONFIGURED("unconfigured"),
    /** A value this library version does not know. */
    UNKNOWN("unknown");

    private static final EnumUtils.WireIndex<GitHubCopilotOrganizationPublicCodeSuggestions> WIRE_INDEX =
            EnumUtils.index(
                    GitHubCopilotOrganizationPublicCodeSuggestions.class,
                    GitHubCopilotOrganizationPublicCodeSuggestions::wireValue);

    private final String wireValue;

    GitHubCopilotOrganizationPublicCodeSuggestions(final String wireValue) {
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
     * Public code suggestions policy from a wire value.
     *
     * @param value JSON value
     * @return matching policy
     * @throws NullPointerException if {@code value} is {@code null}
     */
    @JsonCreator
    public static GitHubCopilotOrganizationPublicCodeSuggestions fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX.fromWire(value, UNKNOWN);
    }
}
