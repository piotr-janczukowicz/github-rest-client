package io.github.piotr_janczukowicz.github.api.securityadvisories;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/** The state of the advisory. */
@CatalogSchema("components/schemas/repository-advisory-update/properties/state")
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public enum GitHubRepositoryAdvisoryUpdateState {
    PUBLISHED("published"),
    CLOSED("closed"),
    DRAFT("draft");

    private static final EnumUtils.WireIndex<GitHubRepositoryAdvisoryUpdateState> WIRE_INDEX =
            EnumUtils.index(GitHubRepositoryAdvisoryUpdateState.class, GitHubRepositoryAdvisoryUpdateState::wireValue);

    private final String wireValue;

    GitHubRepositoryAdvisoryUpdateState(final String wireValue) {
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
     * Resolves repository advisory update state from its wire value.
     *
     * @param value JSON value
     * @return matching GitHubRepositoryAdvisoryUpdateState
     * @throws NullPointerException if {@code value} is {@code null}
     * @throws IllegalArgumentException if {@code value} is not a known repository advisory update state
     */
    @JsonCreator
    public static GitHubRepositoryAdvisoryUpdateState fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX
                .fromWire(value)
                .orElseThrow(() ->
                        new IllegalArgumentException("Unknown GitHub repository advisory update state: " + value));
    }
}
