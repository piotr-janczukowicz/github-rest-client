package io.github.piotr_janczukowicz.github.api.codesecurity;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/** Repositories a code security configuration is attached to for an enterprise. */
@CatalogSchema("operations/code-security/attach-enterprise-configuration/requestBody/properties/scope")
public enum GitHubAttachEnterpriseConfigurationScope {
    ALL("all"),
    ALL_WITHOUT_CONFIGURATIONS("all_without_configurations");

    private static final EnumUtils.WireIndex<GitHubAttachEnterpriseConfigurationScope> WIRE_INDEX = EnumUtils.index(
            GitHubAttachEnterpriseConfigurationScope.class, GitHubAttachEnterpriseConfigurationScope::wireValue);

    private final String wireValue;

    GitHubAttachEnterpriseConfigurationScope(final String wireValue) {
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
    public static GitHubAttachEnterpriseConfigurationScope fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX
                .fromWire(value)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Unknown GitHubAttachEnterpriseConfigurationScope value: " + value));
    }
}
