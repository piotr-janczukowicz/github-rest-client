package io.github.piotr_janczukowicz.github.api.codesecurity;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/** Repository types a code security configuration is applied to by default. */
@CatalogSchema({
    "operations/code-security/set-configuration-as-default-for-enterprise/200/properties/default_for_new_repos",
    "operations/code-security/set-configuration-as-default-for-enterprise/requestBody/properties/default_for_new_repos",
    "operations/code-security/set-configuration-as-default/200/properties/default_for_new_repos",
    "operations/code-security/set-configuration-as-default/requestBody/properties/default_for_new_repos"
})
public enum GitHubDefaultForNewRepos {
    ALL("all"),
    NONE("none"),
    PRIVATE_AND_INTERNAL("private_and_internal"),
    PUBLIC("public");

    private static final EnumUtils.WireIndex<GitHubDefaultForNewRepos> WIRE_INDEX =
            EnumUtils.index(GitHubDefaultForNewRepos.class, GitHubDefaultForNewRepos::wireValue);

    private final String wireValue;

    GitHubDefaultForNewRepos(final String wireValue) {
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
    public static GitHubDefaultForNewRepos fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX
                .fromWire(value)
                .orElseThrow(() -> new IllegalArgumentException("Unknown GitHubDefaultForNewRepos value: " + value));
    }
}
