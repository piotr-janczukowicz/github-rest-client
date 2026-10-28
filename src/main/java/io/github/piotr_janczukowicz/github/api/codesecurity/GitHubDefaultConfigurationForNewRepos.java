package io.github.piotr_janczukowicz.github.api.codesecurity;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/** Repository types a default code security configuration applies to. */
@CatalogSchema("components/schemas/code-security-default-configurations/items/properties/default_for_new_repos")
public enum GitHubDefaultConfigurationForNewRepos {
    PUBLIC("public"),
    PRIVATE_AND_INTERNAL("private_and_internal"),
    ALL("all");

    private static final EnumUtils.WireIndex<GitHubDefaultConfigurationForNewRepos> WIRE_INDEX = EnumUtils.index(
            GitHubDefaultConfigurationForNewRepos.class, GitHubDefaultConfigurationForNewRepos::wireValue);

    private final String wireValue;

    GitHubDefaultConfigurationForNewRepos(final String wireValue) {
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
    public static GitHubDefaultConfigurationForNewRepos fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX
                .fromWire(value)
                .orElseThrow(() ->
                        new IllegalArgumentException("Unknown GitHubDefaultConfigurationForNewRepos value: " + value));
    }
}
