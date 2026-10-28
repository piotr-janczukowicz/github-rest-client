package io.github.piotr_janczukowicz.github.api.secretscanning;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/** The reason for bypassing push protection. */
@CatalogSchema("components/schemas/secret-scanning-push-protection-bypass-reason")
public enum GitHubSecretScanningPushProtectionBypassReason {
    FALSE_POSITIVE("false_positive"),
    USED_IN_TESTS("used_in_tests"),
    WILL_FIX_LATER("will_fix_later");

    private static final EnumUtils.WireIndex<GitHubSecretScanningPushProtectionBypassReason> WIRE_INDEX =
            EnumUtils.index(
                    GitHubSecretScanningPushProtectionBypassReason.class,
                    GitHubSecretScanningPushProtectionBypassReason::wireValue);

    private final String wireValue;

    GitHubSecretScanningPushProtectionBypassReason(final String wireValue) {
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
     * Resolves secret scanning push protection bypass reason from its wire value.
     *
     * @param value JSON value
     * @return matching GitHubSecretScanningPushProtectionBypassReason
     * @throws NullPointerException if {@code value} is {@code null}
     * @throws IllegalArgumentException if {@code value} is not a known secret scanning push protection bypass reason
     */
    @JsonCreator
    public static GitHubSecretScanningPushProtectionBypassReason fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX
                .fromWire(value)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Unknown GitHub secret scanning push protection bypass reason: " + value));
    }
}
