package io.github.piotr_janczukowicz.github.api.codequality;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/** If specified, only code quality findings with this state will be returned. */
@CatalogSchema("operations/code-quality/list-findings-for-repo/parameters/6")
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public enum GitHubListFindingsForRepoState {
    OPEN("open"),
    DISMISSED("dismissed");

    private static final EnumUtils.WireIndex<GitHubListFindingsForRepoState> WIRE_INDEX =
            EnumUtils.index(GitHubListFindingsForRepoState.class, GitHubListFindingsForRepoState::wireValue);

    private final String wireValue;

    GitHubListFindingsForRepoState(final String wireValue) {
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
     * Resolves list findings for repo state from its wire value.
     *
     * @param value JSON value
     * @return matching GitHubListFindingsForRepoState
     * @throws NullPointerException if {@code value} is {@code null}
     * @throws IllegalArgumentException if {@code value} is not a known list findings for repo state
     */
    @JsonCreator
    public static GitHubListFindingsForRepoState fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX
                .fromWire(value)
                .orElseThrow(
                        () -> new IllegalArgumentException("Unknown GitHub list findings for repo state: " + value));
    }
}
