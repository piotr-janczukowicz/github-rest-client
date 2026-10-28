package io.github.piotr_janczukowicz.github.api.repositories;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/** Ruleset target accepted by repository ruleset create and update. */
@CatalogSchema({
    "operations/repos/create-repo-ruleset/requestBody/properties/target",
    "operations/repos/update-repo-ruleset/requestBody/properties/target"
})
public enum GitHubRepositoryRulesetPayloadTarget {
    BRANCH("branch"),
    TAG("tag"),
    PUSH("push");

    private static final EnumUtils.WireIndex<GitHubRepositoryRulesetPayloadTarget> WIRE_INDEX = EnumUtils.index(
            GitHubRepositoryRulesetPayloadTarget.class, GitHubRepositoryRulesetPayloadTarget::wireValue);

    private final String wireValue;

    GitHubRepositoryRulesetPayloadTarget(final String wireValue) {
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
     * Ruleset target accepted by repository ruleset create and update from a wire value.
     *
     * @param value JSON value
     * @return matching value
     * @throws NullPointerException if {@code value} is {@code null}
     * @throws IllegalArgumentException if {@code value} is not a known value
     */
    @JsonCreator
    public static GitHubRepositoryRulesetPayloadTarget fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX
                .fromWire(value)
                .orElseThrow(() ->
                        new IllegalArgumentException("Unknown GitHub repository ruleset payload target: " + value));
    }
}
