package io.github.piotr_janczukowicz.github.api.codesecurity;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/** Bypass mode of a secret scanning delegated bypass reviewer. */
@CatalogSchema({
    "components/schemas/code-security-configuration/properties/secret_scanning_delegated_bypass_options/properties/reviewers/items/properties/mode",
    "operations/code-security/create-configuration/requestBody/properties/secret_scanning_delegated_bypass_options/properties/reviewers/items/properties/mode",
    "operations/code-security/update-configuration/requestBody/properties/secret_scanning_delegated_bypass_options/properties/reviewers/items/properties/mode"
})
public enum GitHubBypassMode {
    ALWAYS("ALWAYS"),
    EXEMPT("EXEMPT");

    private static final EnumUtils.WireIndex<GitHubBypassMode> WIRE_INDEX =
            EnumUtils.index(GitHubBypassMode.class, GitHubBypassMode::wireValue);

    private final String wireValue;

    GitHubBypassMode(final String wireValue) {
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
     * @throws IllegalArgumentException if {@code value} is not a known value
     */
    @JsonCreator
    public static GitHubBypassMode fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX
                .fromWire(value)
                .orElseThrow(() -> new IllegalArgumentException("Unknown GitHubBypassMode value: " + value));
    }
}
