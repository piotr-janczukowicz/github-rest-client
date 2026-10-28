package io.github.piotr_janczukowicz.github.api.codescanning;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/** Whether AI Scan is enabled for the repository. */
@CatalogSchema({
    "components/schemas/code-scanning-ai-scan-enablement/properties/pr_scan",
    "components/schemas/code-scanning-ai-scan-enablement-update/properties/pr_scan",
    "components/schemas/code-scanning-ai-scan-org-enablement-update/properties/pr_scan",
    "components/schemas/code-scanning-ai-scan-org-settings/properties/pr_scan"
})
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public enum GitHubCodeScanningAiScanPrScan {
    ENABLED("enabled"),
    DISABLED("disabled"),
    /** A value this library version does not know. */
    UNKNOWN("unknown");

    private static final EnumUtils.WireIndex<GitHubCodeScanningAiScanPrScan> WIRE_INDEX =
            EnumUtils.index(GitHubCodeScanningAiScanPrScan.class, GitHubCodeScanningAiScanPrScan::wireValue);

    private final String wireValue;

    GitHubCodeScanningAiScanPrScan(final String wireValue) {
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
     * Resolves AI scan setting from its wire value.
     *
     * @param value JSON value
     * @return matching GitHubCodeScanningAiScanPrScan
     * @throws NullPointerException if {@code value} is {@code null}
     */
    @JsonCreator
    public static GitHubCodeScanningAiScanPrScan fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX.fromWire(value, UNKNOWN);
    }
}
