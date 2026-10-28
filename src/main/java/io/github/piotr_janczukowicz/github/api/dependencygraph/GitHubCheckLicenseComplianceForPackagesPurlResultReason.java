package io.github.piotr_janczukowicz.github.api.dependencygraph;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/** The reason for a license compliance result. */
@CatalogSchema(
        "operations/dependency-graph/check-license-compliance-for-packages/200/properties/purls/additionalProperties/properties/reason")
@Flavors(GitHubFlavor.GHEC)
public enum GitHubCheckLicenseComplianceForPackagesPurlResultReason {
    /** allowed license. */
    ALLOWED_LICENSE("allowed_license"),
    /** allowed package. */
    ALLOWED_PACKAGE("allowed_package"),
    /** denied license. */
    DENIED_LICENSE("denied_license"),
    /** unsupported package ecosystem. */
    UNSUPPORTED_PACKAGE_ECOSYSTEM("unsupported_package_ecosystem"),
    /** unknown. */
    UNKNOWN("unknown");

    private static final EnumUtils.WireIndex<GitHubCheckLicenseComplianceForPackagesPurlResultReason> WIRE_INDEX =
            EnumUtils.index(
                    GitHubCheckLicenseComplianceForPackagesPurlResultReason.class,
                    GitHubCheckLicenseComplianceForPackagesPurlResultReason::wireValue);

    private final String wireValue;

    GitHubCheckLicenseComplianceForPackagesPurlResultReason(final String wireValue) {
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
     * Resolves the reason for a license compliance result from its wire value.
     *
     * @param value JSON value
     * @return matching constant, or {@link #UNKNOWN} when the value is not known
     * @throws NullPointerException if {@code value} is {@code null}
     */
    @JsonCreator
    public static GitHubCheckLicenseComplianceForPackagesPurlResultReason fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX.fromWire(value, UNKNOWN);
    }
}
