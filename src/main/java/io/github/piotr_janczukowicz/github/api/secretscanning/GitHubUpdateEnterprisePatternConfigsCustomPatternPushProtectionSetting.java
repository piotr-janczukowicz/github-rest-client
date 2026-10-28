package io.github.piotr_janczukowicz.github.api.secretscanning;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/** The default push protection setting for this pattern. */
@CatalogSchema(
        "operations/secret-scanning/update-enterprise-pattern-configs/requestBody/properties/custom_pattern_settings/items/properties/push_protection_setting")
@Flavors({GitHubFlavor.GHEC, GitHubFlavor.GHES})
public enum GitHubUpdateEnterprisePatternConfigsCustomPatternPushProtectionSetting {
    DISABLED("disabled"),
    ENABLED("enabled");

    private static final EnumUtils.WireIndex<GitHubUpdateEnterprisePatternConfigsCustomPatternPushProtectionSetting>
            WIRE_INDEX = EnumUtils.index(
                    GitHubUpdateEnterprisePatternConfigsCustomPatternPushProtectionSetting.class,
                    GitHubUpdateEnterprisePatternConfigsCustomPatternPushProtectionSetting::wireValue);

    private final String wireValue;

    GitHubUpdateEnterprisePatternConfigsCustomPatternPushProtectionSetting(final String wireValue) {
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
     * @return matching GitHubUpdateEnterprisePatternConfigsCustomPatternPushProtectionSetting
     * @throws NullPointerException if {@code value} is {@code null}
     * @throws IllegalArgumentException if {@code value} is not a known secret scanning custom pattern push protection
     *     setting
     */
    @JsonCreator
    public static GitHubUpdateEnterprisePatternConfigsCustomPatternPushProtectionSetting fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX
                .fromWire(value)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Unknown GitHub secret scanning custom pattern push protection setting: " + value));
    }
}
