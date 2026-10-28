package io.github.piotr_janczukowicz.github.api.actions;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/** The policy that controls whether self-hosted runners can be used by repositories. */
@CatalogSchema({
    "components/schemas/self-hosted-runners-settings/properties/enabled_repositories",
    "operations/actions/set-self-hosted-runners-permissions-organization/requestBody/properties/enabled_repositories"
})
public enum GitHubSelfHostedRunnersEnabledRepositories {
    ALL("all"),
    SELECTED("selected"),
    NONE("none");

    private static final EnumUtils.WireIndex<GitHubSelfHostedRunnersEnabledRepositories> WIRE_INDEX = EnumUtils.index(
            GitHubSelfHostedRunnersEnabledRepositories.class, GitHubSelfHostedRunnersEnabledRepositories::wireValue);

    private final String wireValue;

    GitHubSelfHostedRunnersEnabledRepositories(final String wireValue) {
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
     * Type from a wire value.
     *
     * @param value JSON value
     * @return matching type
     * @throws NullPointerException if {@code value} is {@code null}
     * @throws IllegalArgumentException if {@code value} is not a known type
     */
    @JsonCreator
    public static GitHubSelfHostedRunnersEnabledRepositories fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX
                .fromWire(value)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Unknown GitHubSelfHostedRunnersEnabledRepositories value: " + value));
    }
}
