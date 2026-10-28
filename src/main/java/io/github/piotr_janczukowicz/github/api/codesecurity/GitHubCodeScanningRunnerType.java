package io.github.piotr_janczukowicz.github.api.codesecurity;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/** Runner type used by code scanning default setup. */
@CatalogSchema({
    "components/schemas/code-scanning-default-setup-options/properties/runner_type",
    "components/schemas/code-security-configuration/properties/code_scanning_default_setup_options/properties/runner_type"
})
public enum GitHubCodeScanningRunnerType {
    STANDARD("standard"),
    LABELED("labeled"),
    NOT_SET("not_set");

    private static final EnumUtils.WireIndex<GitHubCodeScanningRunnerType> WIRE_INDEX =
            EnumUtils.index(GitHubCodeScanningRunnerType.class, GitHubCodeScanningRunnerType::wireValue);

    private final String wireValue;

    GitHubCodeScanningRunnerType(final String wireValue) {
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
    public static GitHubCodeScanningRunnerType fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX
                .fromWire(value)
                .orElseThrow(
                        () -> new IllegalArgumentException("Unknown GitHubCodeScanningRunnerType value: " + value));
    }
}
