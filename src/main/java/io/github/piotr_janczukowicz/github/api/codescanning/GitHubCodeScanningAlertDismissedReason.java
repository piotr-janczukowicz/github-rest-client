package io.github.piotr_janczukowicz.github.api.codescanning;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/** **Required when the state is dismissed.** The reason for dismissing or closing the alert. */
@CatalogSchema("components/schemas/code-scanning-alert-dismissed-reason")
public enum GitHubCodeScanningAlertDismissedReason {
    FALSE_POSITIVE("false positive"),
    WONT_FIX("won't fix"),
    USED_IN_TESTS("used in tests"),
    MITIGATED("mitigated");

    private static final EnumUtils.WireIndex<GitHubCodeScanningAlertDismissedReason> WIRE_INDEX = EnumUtils.index(
            GitHubCodeScanningAlertDismissedReason.class, GitHubCodeScanningAlertDismissedReason::wireValue);

    private final String wireValue;

    GitHubCodeScanningAlertDismissedReason(final String wireValue) {
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
     * Resolves code scanning alert dismissed reason from its wire value.
     *
     * @param value JSON value
     * @return matching GitHubCodeScanningAlertDismissedReason
     * @throws NullPointerException if {@code value} is {@code null}
     * @throws IllegalArgumentException if {@code value} is not a known code scanning alert dismissed reason
     */
    @JsonCreator
    public static GitHubCodeScanningAlertDismissedReason fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX
                .fromWire(value)
                .orElseThrow(() ->
                        new IllegalArgumentException("Unknown GitHub code scanning alert dismissed reason: " + value));
    }
}
