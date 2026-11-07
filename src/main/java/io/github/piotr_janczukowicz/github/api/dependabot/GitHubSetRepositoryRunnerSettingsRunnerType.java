package io.github.piotr_janczukowicz.github.api.dependabot;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/** {@code labeled} for labeled runners, {@code standard} for standard GitHub-hosted runners. */
@CatalogSchema("operations/dependabot/set-repository-runner-settings/requestBody/properties/runner_type")
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public enum GitHubSetRepositoryRunnerSettingsRunnerType {
    STANDARD("standard"),
    LABELED("labeled");

    private static final EnumUtils.WireIndex<GitHubSetRepositoryRunnerSettingsRunnerType> WIRE_INDEX = EnumUtils.index(
            GitHubSetRepositoryRunnerSettingsRunnerType.class, GitHubSetRepositoryRunnerSettingsRunnerType::wireValue);

    private final String wireValue;

    GitHubSetRepositoryRunnerSettingsRunnerType(final String wireValue) {
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
     * Resolves the constant from its wire value.
     *
     * @param value JSON value
     * @return matching constant
     * @throws NullPointerException if {@code value} is {@code null}
     * @throws IllegalArgumentException if {@code value} is not a known value
     */
    @JsonCreator
    public static GitHubSetRepositoryRunnerSettingsRunnerType fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX
                .fromWire(value)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Unknown GitHub set repository runner settings runner type: " + value));
    }
}
