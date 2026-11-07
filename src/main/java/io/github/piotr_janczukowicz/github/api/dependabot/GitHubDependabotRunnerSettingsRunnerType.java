package io.github.piotr_janczukowicz.github.api.dependabot;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/** {@code labeled} when Dependabot runs on labeled runners. Otherwise {@code standard}. */
@CatalogSchema("components/schemas/dependabot-runner-settings/properties/runner_type")
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public enum GitHubDependabotRunnerSettingsRunnerType {
    STANDARD("standard"),
    LABELED("labeled"),
    /** A value this library version does not know. */
    UNRECOGNIZED("unrecognized");

    private static final EnumUtils.WireIndex<GitHubDependabotRunnerSettingsRunnerType> WIRE_INDEX = EnumUtils.index(
            GitHubDependabotRunnerSettingsRunnerType.class, GitHubDependabotRunnerSettingsRunnerType::wireValue);

    private final String wireValue;

    GitHubDependabotRunnerSettingsRunnerType(final String wireValue) {
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
     * @return matching constant, or {@link #UNRECOGNIZED} when the value is not known
     * @throws NullPointerException if {@code value} is {@code null}
     */
    @JsonCreator
    public static GitHubDependabotRunnerSettingsRunnerType fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX.fromWire(value, UNRECOGNIZED);
    }
}
