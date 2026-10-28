package io.github.piotr_janczukowicz.github.api.codequality;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/** The frequency of the periodic analysis. */
@CatalogSchema("components/schemas/code-quality-setup/properties/schedule")
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public enum GitHubCodeQualitySetupSchedule {
    WEEKLY("weekly");

    private static final EnumUtils.WireIndex<GitHubCodeQualitySetupSchedule> WIRE_INDEX =
            EnumUtils.index(GitHubCodeQualitySetupSchedule.class, GitHubCodeQualitySetupSchedule::wireValue);

    private final String wireValue;

    GitHubCodeQualitySetupSchedule(final String wireValue) {
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
     * Resolves code quality setup schedule from its wire value.
     *
     * @param value JSON value
     * @return matching GitHubCodeQualitySetupSchedule
     * @throws NullPointerException if {@code value} is {@code null}
     * @throws IllegalArgumentException if {@code value} is not a known code quality setup schedule
     */
    @JsonCreator
    public static GitHubCodeQualitySetupSchedule fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX
                .fromWire(value)
                .orElseThrow(
                        () -> new IllegalArgumentException("Unknown GitHub code quality setup schedule: " + value));
    }
}
