package io.github.piotr_janczukowicz.github.api.repositories;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/** Repository ruleset bypass mode. */
@CatalogSchema("components/schemas/repository-ruleset-bypass-actor/properties/bypass_mode")
public enum GitHubRepositoryRulesetBypassMode {
    ALWAYS("always"),
    PULL_REQUEST("pull_request"),
    EXEMPT("exempt");

    private static final EnumUtils.WireIndex<GitHubRepositoryRulesetBypassMode> WIRE_INDEX =
            EnumUtils.index(GitHubRepositoryRulesetBypassMode.class, GitHubRepositoryRulesetBypassMode::wireValue);

    private final String wireValue;

    GitHubRepositoryRulesetBypassMode(final String wireValue) {
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
     * Repository ruleset bypass mode from a wire value.
     *
     * @param value JSON value
     * @return matching value
     * @throws NullPointerException if {@code value} is {@code null}
     * @throws IllegalArgumentException if {@code value} is not a known value
     */
    @JsonCreator
    public static GitHubRepositoryRulesetBypassMode fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX
                .fromWire(value)
                .orElseThrow(
                        () -> new IllegalArgumentException("Unknown GitHub repository ruleset bypass mode: " + value));
    }
}
