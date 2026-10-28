package io.github.piotr_janczukowicz.github.api.securityadvisories;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/** State. */
@CatalogSchema({
    "operations/security-advisories/list-org-repository-advisories/parameters/6",
    "operations/security-advisories/list-repository-advisories/parameters/7"
})
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public enum GitHubRepositoryAdvisoriesFilterState {
    TRIAGE("triage"),
    DRAFT("draft"),
    PUBLISHED("published"),
    CLOSED("closed");

    private static final EnumUtils.WireIndex<GitHubRepositoryAdvisoriesFilterState> WIRE_INDEX = EnumUtils.index(
            GitHubRepositoryAdvisoriesFilterState.class, GitHubRepositoryAdvisoriesFilterState::wireValue);

    private final String wireValue;

    GitHubRepositoryAdvisoriesFilterState(final String wireValue) {
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
     * Resolves repository advisories filter state from its wire value.
     *
     * @param value JSON value
     * @return matching GitHubRepositoryAdvisoriesFilterState
     * @throws NullPointerException if {@code value} is {@code null}
     * @throws IllegalArgumentException if {@code value} is not a known repository advisories filter state
     */
    @JsonCreator
    public static GitHubRepositoryAdvisoriesFilterState fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX
                .fromWire(value)
                .orElseThrow(() ->
                        new IllegalArgumentException("Unknown GitHub repository advisories filter state: " + value));
    }
}
