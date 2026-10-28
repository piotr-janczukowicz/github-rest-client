package io.github.piotr_janczukowicz.github.api.repositories;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/** non_fast_forward type. */
@CatalogSchema("components/schemas/repository-rule-non-fast-forward/properties/type")
public enum GitHubRepositoryRuleNonFastForwardType {
    NON_FAST_FORWARD("non_fast_forward");

    private static final EnumUtils.WireIndex<GitHubRepositoryRuleNonFastForwardType> WIRE_INDEX = EnumUtils.index(
            GitHubRepositoryRuleNonFastForwardType.class, GitHubRepositoryRuleNonFastForwardType::wireValue);

    private final String wireValue;

    GitHubRepositoryRuleNonFastForwardType(final String wireValue) {
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
     * non_fast_forward type from a wire value.
     *
     * @param value JSON value
     * @return matching value
     * @throws NullPointerException if {@code value} is {@code null}
     * @throws IllegalArgumentException if {@code value} is not a known value
     */
    @JsonCreator
    public static GitHubRepositoryRuleNonFastForwardType fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX
                .fromWire(value)
                .orElseThrow(() ->
                        new IllegalArgumentException("Unknown GitHub repository rule non fast forward type: " + value));
    }
}
