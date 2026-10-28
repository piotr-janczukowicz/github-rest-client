package io.github.piotr_janczukowicz.github.api.secretscanning;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/**
 * The push protection setting for this pattern set at the enterprise level. Only present for partner patterns when the
 * organization has a parent enterprise.
 */
@CatalogSchema({
    "components/schemas/secret-scanning-pattern-override/properties/enterprise_setting",
    "components/schemas/secret-scanning-pattern-override/properties/setting",
    "operations/secret-scanning/update-org-pattern-configs/requestBody/properties/provider_pattern_settings/items/properties/push_protection_setting"
})
public enum GitHubSecretScanningPatternPushProtectionSetting {
    NOT_SET("not-set"),
    DISABLED("disabled"),
    ENABLED("enabled"),
    /** A value this library version does not know. */
    UNKNOWN("unknown");

    private static final EnumUtils.WireIndex<GitHubSecretScanningPatternPushProtectionSetting> WIRE_INDEX =
            EnumUtils.index(
                    GitHubSecretScanningPatternPushProtectionSetting.class,
                    GitHubSecretScanningPatternPushProtectionSetting::wireValue);

    private final String wireValue;

    GitHubSecretScanningPatternPushProtectionSetting(final String wireValue) {
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
     * Resolves secret scanning pattern push protection setting from its wire value.
     *
     * @param value JSON value
     * @return matching GitHubSecretScanningPatternPushProtectionSetting
     * @throws NullPointerException if {@code value} is {@code null}
     */
    @JsonCreator
    public static GitHubSecretScanningPatternPushProtectionSetting fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX.fromWire(value, UNKNOWN);
    }
}
