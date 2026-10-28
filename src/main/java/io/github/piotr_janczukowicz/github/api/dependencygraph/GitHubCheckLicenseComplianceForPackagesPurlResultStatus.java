package io.github.piotr_janczukowicz.github.api.dependencygraph;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/** The package license compliance result. */
@CatalogSchema(
        "operations/dependency-graph/check-license-compliance-for-packages/200/properties/purls/additionalProperties/properties/status")
@Flavors(GitHubFlavor.GHEC)
public enum GitHubCheckLicenseComplianceForPackagesPurlResultStatus {
    /** allowed. */
    ALLOWED("allowed"),
    /** denied. */
    DENIED("denied"),
    /** unknown. */
    UNKNOWN("unknown");

    private static final EnumUtils.WireIndex<GitHubCheckLicenseComplianceForPackagesPurlResultStatus> WIRE_INDEX =
            EnumUtils.index(
                    GitHubCheckLicenseComplianceForPackagesPurlResultStatus.class,
                    GitHubCheckLicenseComplianceForPackagesPurlResultStatus::wireValue);

    private final String wireValue;

    GitHubCheckLicenseComplianceForPackagesPurlResultStatus(final String wireValue) {
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
     * Resolves the package license compliance result from its wire value.
     *
     * @param value JSON value
     * @return matching constant, or {@link #UNKNOWN} when the value is not known
     * @throws NullPointerException if {@code value} is {@code null}
     */
    @JsonCreator
    public static GitHubCheckLicenseComplianceForPackagesPurlResultStatus fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX.fromWire(value, UNKNOWN);
    }
}
