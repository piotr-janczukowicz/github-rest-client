package io.github.piotr_janczukowicz.github.api.repositories;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/** Repository property source for ruleset conditions. */
@CatalogSchema("components/schemas/repository-ruleset-conditions-repository-property-spec/properties/source")
public enum GitHubRepositoryRulesetConditionsRepositoryPropertySource {
    CUSTOM("custom"),
    SYSTEM("system");

    private static final EnumUtils.WireIndex<GitHubRepositoryRulesetConditionsRepositoryPropertySource> WIRE_INDEX =
            EnumUtils.index(
                    GitHubRepositoryRulesetConditionsRepositoryPropertySource.class,
                    GitHubRepositoryRulesetConditionsRepositoryPropertySource::wireValue);

    private final String wireValue;

    GitHubRepositoryRulesetConditionsRepositoryPropertySource(final String wireValue) {
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
     * Repository property source for ruleset conditions from a wire value.
     *
     * @param value JSON value
     * @return matching value
     * @throws NullPointerException if {@code value} is {@code null}
     * @throws IllegalArgumentException if {@code value} is not a known value
     */
    @JsonCreator
    public static GitHubRepositoryRulesetConditionsRepositoryPropertySource fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX
                .fromWire(value)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Unknown GitHub repository ruleset conditions repository property source: " + value));
    }
}
