package io.github.piotr_janczukowicz.github.api.securityadvisories;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/** The state of the advisory. */
@CatalogSchema("components/schemas/repository-advisory/properties/state")
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public enum GitHubRepositoryAdvisoryState {
    PUBLISHED("published"),
    CLOSED("closed"),
    WITHDRAWN("withdrawn"),
    DRAFT("draft"),
    TRIAGE("triage"),
    /** A value this library version does not know. */
    UNKNOWN("unknown");

    private static final EnumUtils.WireIndex<GitHubRepositoryAdvisoryState> WIRE_INDEX =
            EnumUtils.index(GitHubRepositoryAdvisoryState.class, GitHubRepositoryAdvisoryState::wireValue);

    private final String wireValue;

    GitHubRepositoryAdvisoryState(final String wireValue) {
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
     * Resolves repository advisory state from its wire value.
     *
     * @param value JSON value
     * @return matching GitHubRepositoryAdvisoryState
     * @throws NullPointerException if {@code value} is {@code null}
     */
    @JsonCreator
    public static GitHubRepositoryAdvisoryState fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX.fromWire(value, UNKNOWN);
    }
}
