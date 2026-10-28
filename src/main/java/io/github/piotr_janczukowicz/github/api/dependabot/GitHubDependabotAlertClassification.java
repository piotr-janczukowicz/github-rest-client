package io.github.piotr_janczukowicz.github.api.dependabot;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/** The classification of the advisory. */
@CatalogSchema("components/schemas/dependabot-alert-security-advisory/properties/classification")
public enum GitHubDependabotAlertClassification {
    GENERAL("general"),
    MALWARE("malware"),
    /** A value this library version does not know. */
    UNKNOWN("unknown");

    private static final EnumUtils.WireIndex<GitHubDependabotAlertClassification> WIRE_INDEX =
            EnumUtils.index(GitHubDependabotAlertClassification.class, GitHubDependabotAlertClassification::wireValue);

    private final String wireValue;

    GitHubDependabotAlertClassification(final String wireValue) {
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
     * Resolves Dependabot alert classification from its wire value.
     *
     * @param value JSON value
     * @return matching GitHubDependabotAlertClassification
     * @throws NullPointerException if {@code value} is {@code null}
     */
    @JsonCreator
    public static GitHubDependabotAlertClassification fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX.fromWire(value, UNKNOWN);
    }
}
