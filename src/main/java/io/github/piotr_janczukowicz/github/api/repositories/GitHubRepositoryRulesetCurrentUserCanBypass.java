package io.github.piotr_janczukowicz.github.api.repositories;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/** Repository ruleset current-user bypass capability. */
@CatalogSchema("components/schemas/repository-ruleset/properties/current_user_can_bypass")
public enum GitHubRepositoryRulesetCurrentUserCanBypass {
    ALWAYS("always"),
    PULL_REQUESTS_ONLY("pull_requests_only"),
    NEVER("never"),
    EXEMPT("exempt"),
    /** A value this library version does not know. */
    UNKNOWN("unknown");

    private static final EnumUtils.WireIndex<GitHubRepositoryRulesetCurrentUserCanBypass> WIRE_INDEX = EnumUtils.index(
            GitHubRepositoryRulesetCurrentUserCanBypass.class, GitHubRepositoryRulesetCurrentUserCanBypass::wireValue);

    private final String wireValue;

    GitHubRepositoryRulesetCurrentUserCanBypass(final String wireValue) {
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
     * Repository ruleset current-user bypass capability from a wire value.
     *
     * @param value JSON value
     * @return matching value
     * @throws NullPointerException if {@code value} is {@code null}
     */
    @JsonCreator
    public static GitHubRepositoryRulesetCurrentUserCanBypass fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX.fromWire(value, UNKNOWN);
    }
}
