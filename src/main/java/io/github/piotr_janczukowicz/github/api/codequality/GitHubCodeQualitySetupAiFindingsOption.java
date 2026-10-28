package io.github.piotr_janczukowicz.github.api.codequality;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/** The AI findings configuration for the repository. */
@CatalogSchema("components/schemas/code-quality-setup/properties/ai_findings_option")
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public enum GitHubCodeQualitySetupAiFindingsOption {
    DISABLED("disabled"),
    ON_PUSH("on_push"),
    /** A value this library version does not know. */
    UNKNOWN("unknown");

    private static final EnumUtils.WireIndex<GitHubCodeQualitySetupAiFindingsOption> WIRE_INDEX = EnumUtils.index(
            GitHubCodeQualitySetupAiFindingsOption.class, GitHubCodeQualitySetupAiFindingsOption::wireValue);

    private final String wireValue;

    GitHubCodeQualitySetupAiFindingsOption(final String wireValue) {
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
     * Resolves code quality setup AI findings option from its wire value.
     *
     * @param value JSON value
     * @return matching GitHubCodeQualitySetupAiFindingsOption
     * @throws NullPointerException if {@code value} is {@code null}
     */
    @JsonCreator
    public static GitHubCodeQualitySetupAiFindingsOption fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX.fromWire(value, UNKNOWN);
    }
}
