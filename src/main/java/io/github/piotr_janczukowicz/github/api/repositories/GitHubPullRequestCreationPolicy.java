package io.github.piotr_janczukowicz.github.api.repositories;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/** Pull request creation policy. */
@CatalogSchema({
    "components/schemas/repository/properties/pull_request_creation_policy",
    "components/schemas/full-repository/properties/pull_request_creation_policy",
    "components/schemas/minimal-repository/properties/pull_request_creation_policy",
    "components/schemas/repo-search-result-item/properties/pull_request_creation_policy"
})
public enum GitHubPullRequestCreationPolicy {
    ALL("all"),
    COLLABORATORS_ONLY("collaborators_only");

    private static final EnumUtils.WireIndex<GitHubPullRequestCreationPolicy> WIRE_INDEX =
            EnumUtils.index(GitHubPullRequestCreationPolicy.class, GitHubPullRequestCreationPolicy::wireValue);

    private final String wireValue;

    GitHubPullRequestCreationPolicy(final String wireValue) {
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
     * Pull request creation policy from a wire value.
     *
     * @param value JSON value
     * @return matching value
     * @throws NullPointerException if {@code value} is {@code null}
     * @throws IllegalArgumentException if {@code value} is not a known value
     */
    @JsonCreator
    public static GitHubPullRequestCreationPolicy fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX
                .fromWire(value)
                .orElseThrow(
                        () -> new IllegalArgumentException("Unknown GitHub pull request creation policy: " + value));
    }
}
