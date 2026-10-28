package io.github.piotr_janczukowicz.github.api.codesecurity;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/** Repositories a code security configuration is attached to. */
@CatalogSchema("operations/code-security/attach-configuration/requestBody/properties/scope")
public enum GitHubAttachConfigurationScope {
    ALL("all"),
    ALL_WITHOUT_CONFIGURATIONS("all_without_configurations"),
    PUBLIC("public"),
    PRIVATE_OR_INTERNAL("private_or_internal"),
    SELECTED("selected");

    private static final EnumUtils.WireIndex<GitHubAttachConfigurationScope> WIRE_INDEX =
            EnumUtils.index(GitHubAttachConfigurationScope.class, GitHubAttachConfigurationScope::wireValue);

    private final String wireValue;

    GitHubAttachConfigurationScope(final String wireValue) {
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
    public static GitHubAttachConfigurationScope fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX
                .fromWire(value)
                .orElseThrow(
                        () -> new IllegalArgumentException("Unknown GitHubAttachConfigurationScope value: " + value));
    }
}
