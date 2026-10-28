package io.github.piotr_janczukowicz.github.api.repositories;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/** Ruleset target. */
@CatalogSchema("components/schemas/repository-ruleset/properties/target")
public enum GitHubRepositoryRulesetTarget {
    BRANCH("branch"),
    TAG("tag"),
    PUSH("push"),
    REPOSITORY("repository");

    private static final EnumUtils.WireIndex<GitHubRepositoryRulesetTarget> WIRE_INDEX =
            EnumUtils.index(GitHubRepositoryRulesetTarget.class, GitHubRepositoryRulesetTarget::wireValue);

    private final String wireValue;

    GitHubRepositoryRulesetTarget(final String wireValue) {
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
     * Ruleset target from a wire value.
     *
     * @param value JSON value
     * @return matching value
     * @throws NullPointerException if {@code value} is {@code null}
     * @throws IllegalArgumentException if {@code value} is not a known value
     */
    @JsonCreator
    public static GitHubRepositoryRulesetTarget fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX
                .fromWire(value)
                .orElseThrow(() -> new IllegalArgumentException("Unknown GitHub repository ruleset target: " + value));
    }
}
