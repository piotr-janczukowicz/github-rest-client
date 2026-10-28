package io.github.piotr_janczukowicz.github.api.organizations;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/** Immutable releases enforced repositories policy. */
@CatalogSchema("components/schemas/immutable-releases-organization-settings/properties/enforced_repositories")
public enum GitHubImmutableReleasesEnforcedRepositories {
    ALL("all"),
    NONE("none"),
    SELECTED("selected");

    private static final EnumUtils.WireIndex<GitHubImmutableReleasesEnforcedRepositories> WIRE_INDEX = EnumUtils.index(
            GitHubImmutableReleasesEnforcedRepositories.class, GitHubImmutableReleasesEnforcedRepositories::wireValue);

    private final String wireValue;

    GitHubImmutableReleasesEnforcedRepositories(final String wireValue) {
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
     * Enforced repositories policy from a wire value.
     *
     * @param value JSON value
     * @return matching policy
     * @throws NullPointerException if {@code value} is {@code null}
     * @throws IllegalArgumentException if {@code value} is not a known policy
     */
    @JsonCreator
    public static GitHubImmutableReleasesEnforcedRepositories fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX
                .fromWire(value)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Unknown GitHub immutable releases enforced repositories policy: " + value));
    }
}
