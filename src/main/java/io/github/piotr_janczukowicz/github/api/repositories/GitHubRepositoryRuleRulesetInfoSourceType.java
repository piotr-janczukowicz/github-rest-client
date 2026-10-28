package io.github.piotr_janczukowicz.github.api.repositories;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/** Source of a ruleset that includes a repository rule. */
@CatalogSchema("components/schemas/repository-rule-ruleset-info/properties/ruleset_source_type")
public enum GitHubRepositoryRuleRulesetInfoSourceType {
    REPOSITORY("Repository"),
    ORGANIZATION("Organization"),
    /** A value this library version does not know. */
    UNKNOWN("unknown");

    private static final EnumUtils.WireIndex<GitHubRepositoryRuleRulesetInfoSourceType> WIRE_INDEX = EnumUtils.index(
            GitHubRepositoryRuleRulesetInfoSourceType.class, GitHubRepositoryRuleRulesetInfoSourceType::wireValue);

    private final String wireValue;

    GitHubRepositoryRuleRulesetInfoSourceType(final String wireValue) {
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
     * Source of a ruleset that includes a repository rule from a wire value.
     *
     * @param value JSON value
     * @return matching value
     * @throws NullPointerException if {@code value} is {@code null}
     */
    @JsonCreator
    public static GitHubRepositoryRuleRulesetInfoSourceType fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX.fromWire(value, UNKNOWN);
    }
}
