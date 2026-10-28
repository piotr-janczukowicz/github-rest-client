package io.github.piotr_janczukowicz.github.api.secretscanning;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/** The default push protection setting for this pattern. */
@CatalogSchema({
    "components/schemas/secret-scanning-pattern-override/properties/default_setting",
    "operations/secret-scanning/update-org-pattern-configs/requestBody/properties/custom_pattern_settings/items/properties/push_protection_setting"
})
public enum GitHubSecretScanningCustomPatternPushProtectionSetting {
    DISABLED("disabled"),
    ENABLED("enabled"),
    /** A value this library version does not know. */
    UNKNOWN("unknown");

    private static final EnumUtils.WireIndex<GitHubSecretScanningCustomPatternPushProtectionSetting> WIRE_INDEX =
            EnumUtils.index(
                    GitHubSecretScanningCustomPatternPushProtectionSetting.class,
                    GitHubSecretScanningCustomPatternPushProtectionSetting::wireValue);

    private final String wireValue;

    GitHubSecretScanningCustomPatternPushProtectionSetting(final String wireValue) {
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
     * Resolves secret scanning custom pattern push protection setting from its wire value.
     *
     * @param value JSON value
     * @return matching GitHubSecretScanningCustomPatternPushProtectionSetting
     * @throws NullPointerException if {@code value} is {@code null} setting
     */
    @JsonCreator
    public static GitHubSecretScanningCustomPatternPushProtectionSetting fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX.fromWire(value, UNKNOWN);
    }
}
