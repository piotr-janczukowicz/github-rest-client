package io.github.piotr_janczukowicz.github.api.codequality;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/** The category of the rule used to detect the finding. */
@CatalogSchema("components/schemas/code-quality-finding-rule/properties/category")
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public enum GitHubCodeQualityFindingRuleCategory {
    NONE("none"),
    MAINTAINABILITY("maintainability"),
    RELIABILITY("reliability");

    private static final EnumUtils.WireIndex<GitHubCodeQualityFindingRuleCategory> WIRE_INDEX = EnumUtils.index(
            GitHubCodeQualityFindingRuleCategory.class, GitHubCodeQualityFindingRuleCategory::wireValue);

    private final String wireValue;

    GitHubCodeQualityFindingRuleCategory(final String wireValue) {
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
     * Resolves code quality finding rule category from its wire value.
     *
     * @param value JSON value
     * @return matching GitHubCodeQualityFindingRuleCategory
     * @throws NullPointerException if {@code value} is {@code null}
     * @throws IllegalArgumentException if {@code value} is not a known code quality finding rule category
     */
    @JsonCreator
    public static GitHubCodeQualityFindingRuleCategory fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX
                .fromWire(value)
                .orElseThrow(() ->
                        new IllegalArgumentException("Unknown GitHub code quality finding rule category: " + value));
    }
}
