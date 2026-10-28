package io.github.piotr_janczukowicz.github.api.codequality;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/** Whether AI findings run for Code Quality on this repository. */
@CatalogSchema("components/schemas/code-quality-setup-update/properties/ai_findings_option")
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public enum GitHubCodeQualitySetupUpdateAiFindingsOption {
    DISABLED("disabled"),
    ON_PUSH("on_push");

    private static final EnumUtils.WireIndex<GitHubCodeQualitySetupUpdateAiFindingsOption> WIRE_INDEX = EnumUtils.index(
            GitHubCodeQualitySetupUpdateAiFindingsOption.class,
            GitHubCodeQualitySetupUpdateAiFindingsOption::wireValue);

    private final String wireValue;

    GitHubCodeQualitySetupUpdateAiFindingsOption(final String wireValue) {
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
     * Resolves code quality setup update AI findings option from its wire value.
     *
     * @param value JSON value
     * @return matching GitHubCodeQualitySetupUpdateAiFindingsOption
     * @throws NullPointerException if {@code value} is {@code null}
     * @throws IllegalArgumentException if {@code value} is not a known code quality setup update AI findings option
     */
    @JsonCreator
    public static GitHubCodeQualitySetupUpdateAiFindingsOption fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX
                .fromWire(value)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Unknown GitHub code quality setup update AI findings option: " + value));
    }
}
