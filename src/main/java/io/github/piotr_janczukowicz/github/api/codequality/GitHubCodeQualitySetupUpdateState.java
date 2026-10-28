package io.github.piotr_janczukowicz.github.api.codequality;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/** The desired state of code quality setup. */
@CatalogSchema("components/schemas/code-quality-setup-update/properties/state")
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public enum GitHubCodeQualitySetupUpdateState {
    CONFIGURED("configured"),
    NOT_CONFIGURED("not-configured");

    private static final EnumUtils.WireIndex<GitHubCodeQualitySetupUpdateState> WIRE_INDEX =
            EnumUtils.index(GitHubCodeQualitySetupUpdateState.class, GitHubCodeQualitySetupUpdateState::wireValue);

    private final String wireValue;

    GitHubCodeQualitySetupUpdateState(final String wireValue) {
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
     * Resolves code quality setup update state from its wire value.
     *
     * @param value JSON value
     * @return matching GitHubCodeQualitySetupUpdateState
     * @throws NullPointerException if {@code value} is {@code null}
     * @throws IllegalArgumentException if {@code value} is not a known code quality setup update state
     */
    @JsonCreator
    public static GitHubCodeQualitySetupUpdateState fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX
                .fromWire(value)
                .orElseThrow(
                        () -> new IllegalArgumentException("Unknown GitHub code quality setup update state: " + value));
    }
}
