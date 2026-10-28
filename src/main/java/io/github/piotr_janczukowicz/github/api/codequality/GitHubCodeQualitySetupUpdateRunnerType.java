package io.github.piotr_janczukowicz.github.api.codequality;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/** Runner type to be used. */
@CatalogSchema("components/schemas/code-quality-setup-update/properties/runner_type")
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public enum GitHubCodeQualitySetupUpdateRunnerType {
    STANDARD("standard"),
    LABELED("labeled");

    private static final EnumUtils.WireIndex<GitHubCodeQualitySetupUpdateRunnerType> WIRE_INDEX = EnumUtils.index(
            GitHubCodeQualitySetupUpdateRunnerType.class, GitHubCodeQualitySetupUpdateRunnerType::wireValue);

    private final String wireValue;

    GitHubCodeQualitySetupUpdateRunnerType(final String wireValue) {
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
     * Resolves code quality setup update runner type from its wire value.
     *
     * @param value JSON value
     * @return matching GitHubCodeQualitySetupUpdateRunnerType
     * @throws NullPointerException if {@code value} is {@code null}
     * @throws IllegalArgumentException if {@code value} is not a known code quality setup update runner type
     */
    @JsonCreator
    public static GitHubCodeQualitySetupUpdateRunnerType fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX
                .fromWire(value)
                .orElseThrow(() ->
                        new IllegalArgumentException("Unknown GitHub code quality setup update runner type: " + value));
    }
}
