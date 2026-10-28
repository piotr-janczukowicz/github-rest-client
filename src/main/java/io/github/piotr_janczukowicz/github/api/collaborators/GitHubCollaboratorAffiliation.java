package io.github.piotr_janczukowicz.github.api.collaborators;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/** Collaborator affiliation filter. */
@CatalogSchema("operations/repos/list-collaborators/parameters/2")
public enum GitHubCollaboratorAffiliation {
    OUTSIDE("outside"),
    DIRECT("direct"),
    ALL("all");

    private static final EnumUtils.WireIndex<GitHubCollaboratorAffiliation> WIRE_INDEX =
            EnumUtils.index(GitHubCollaboratorAffiliation.class, GitHubCollaboratorAffiliation::wireValue);

    private final String wireValue;

    GitHubCollaboratorAffiliation(final String wireValue) {
        this.wireValue = wireValue;
    }

    /**
     * Value GitHub sends and expects in query parameters.
     *
     * @return wire string
     */
    @JsonValue
    public String wireValue() {
        return wireValue;
    }

    /**
     * Affiliation from GitHub's collaborator list {@code affiliation} parameter.
     *
     * @param value query parameter value
     * @return matching affiliation
     * @throws NullPointerException if {@code value} is {@code null}
     * @throws IllegalArgumentException if {@code value} is not a known affiliation
     */
    @JsonCreator
    public static GitHubCollaboratorAffiliation fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX
                .fromWire(value)
                .orElseThrow(() -> new IllegalArgumentException("Unknown GitHub collaborator affiliation: " + value));
    }
}
