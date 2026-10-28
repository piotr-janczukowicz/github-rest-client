package io.github.piotr_janczukowicz.github.api.organizations;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/** The type of the source of the ruleset. Only GitHub Enterprise Server accepts it when creating a ruleset. */
@CatalogSchema("operations/repos/create-org-ruleset/requestBody/properties/source_type")
public enum GitHubCreateOrgRulesetSourceType {
    REPOSITORY("Repository"),
    ORGANIZATION("Organization");

    private static final EnumUtils.WireIndex<GitHubCreateOrgRulesetSourceType> WIRE_INDEX =
            EnumUtils.index(GitHubCreateOrgRulesetSourceType.class, GitHubCreateOrgRulesetSourceType::wireValue);

    private final String wireValue;

    GitHubCreateOrgRulesetSourceType(final String wireValue) {
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
     * Source type from a wire value.
     *
     * @param value JSON value
     * @return matching source type
     * @throws NullPointerException if {@code value} is {@code null}
     * @throws IllegalArgumentException if {@code value} is not a known source type
     */
    @JsonCreator
    public static GitHubCreateOrgRulesetSourceType fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX
                .fromWire(value)
                .orElseThrow(() -> new IllegalArgumentException("Unknown GitHub ruleset source type: " + value));
    }
}
