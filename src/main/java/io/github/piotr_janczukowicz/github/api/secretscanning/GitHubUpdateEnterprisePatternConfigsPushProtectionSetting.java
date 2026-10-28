package io.github.piotr_janczukowicz.github.api.secretscanning;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/**
 * The push protection setting for this pattern set at the enterprise level. Only present for partner patterns when the
 * organization has a parent enterprise.
 */
@CatalogSchema(
        "operations/secret-scanning/update-enterprise-pattern-configs/requestBody/properties/provider_pattern_settings/items/properties/push_protection_setting")
@Flavors({GitHubFlavor.GHEC, GitHubFlavor.GHES})
public enum GitHubUpdateEnterprisePatternConfigsPushProtectionSetting {
    NOT_SET("not-set"),
    DISABLED("disabled"),
    ENABLED("enabled");

    private static final EnumUtils.WireIndex<GitHubUpdateEnterprisePatternConfigsPushProtectionSetting> WIRE_INDEX =
            EnumUtils.index(
                    GitHubUpdateEnterprisePatternConfigsPushProtectionSetting.class,
                    GitHubUpdateEnterprisePatternConfigsPushProtectionSetting::wireValue);

    private final String wireValue;

    GitHubUpdateEnterprisePatternConfigsPushProtectionSetting(final String wireValue) {
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
     * @return matching GitHubUpdateEnterprisePatternConfigsPushProtectionSetting
     * @throws NullPointerException if {@code value} is {@code null}
     * @throws IllegalArgumentException if {@code value} is not a known secret scanning pattern push protection setting
     */
    @JsonCreator
    public static GitHubUpdateEnterprisePatternConfigsPushProtectionSetting fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX
                .fromWire(value)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Unknown GitHub secret scanning pattern push protection setting: " + value));
    }
}
