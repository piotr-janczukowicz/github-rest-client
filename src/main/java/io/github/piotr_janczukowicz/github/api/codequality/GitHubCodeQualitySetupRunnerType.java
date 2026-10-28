package io.github.piotr_janczukowicz.github.api.codequality;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/** Runner type to be used. */
@CatalogSchema("components/schemas/code-quality-setup/properties/runner_type")
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public enum GitHubCodeQualitySetupRunnerType {
    STANDARD("standard"),
    LABELED("labeled"),
    /** A value this library version does not know. */
    UNKNOWN("unknown");

    private static final EnumUtils.WireIndex<GitHubCodeQualitySetupRunnerType> WIRE_INDEX =
            EnumUtils.index(GitHubCodeQualitySetupRunnerType.class, GitHubCodeQualitySetupRunnerType::wireValue);

    private final String wireValue;

    GitHubCodeQualitySetupRunnerType(final String wireValue) {
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
     * Resolves code quality setup runner type from its wire value.
     *
     * @param value JSON value
     * @return matching GitHubCodeQualitySetupRunnerType
     * @throws NullPointerException if {@code value} is {@code null}
     */
    @JsonCreator
    public static GitHubCodeQualitySetupRunnerType fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX.fromWire(value, UNKNOWN);
    }
}
