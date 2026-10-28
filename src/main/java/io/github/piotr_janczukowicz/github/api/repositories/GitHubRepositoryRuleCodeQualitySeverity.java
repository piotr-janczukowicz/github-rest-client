package io.github.piotr_janczukowicz.github.api.repositories;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/** Code quality severity level for repository rule parameters. */
@CatalogSchema("components/schemas/repository-rule-code-quality/properties/parameters/properties/severity")
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public enum GitHubRepositoryRuleCodeQualitySeverity {
    ERRORS("errors"),
    WARNINGS("warnings"),
    NOTES("notes"),
    ALL("all");

    private static final EnumUtils.WireIndex<GitHubRepositoryRuleCodeQualitySeverity> WIRE_INDEX = EnumUtils.index(
            GitHubRepositoryRuleCodeQualitySeverity.class, GitHubRepositoryRuleCodeQualitySeverity::wireValue);

    private final String wireValue;

    GitHubRepositoryRuleCodeQualitySeverity(final String wireValue) {
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
     * Code quality severity level for repository rule parameters from a wire value.
     *
     * @param value JSON value
     * @return matching value
     * @throws NullPointerException if {@code value} is {@code null}
     * @throws IllegalArgumentException if {@code value} is not a known value
     */
    @JsonCreator
    public static GitHubRepositoryRuleCodeQualitySeverity fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX
                .fromWire(value)
                .orElseThrow(() ->
                        new IllegalArgumentException("Unknown GitHub repository rule code quality severity: " + value));
    }
}
