package io.github.piotr_janczukowicz.github.api.repositories;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/** license_compliance_scanning type. */
@CatalogSchema("components/schemas/repository-rule-license-compliance-scanning/properties/type")
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public enum GitHubRepositoryRuleLicenseComplianceScanningType {
    LICENSE_COMPLIANCE_SCANNING("license_compliance_scanning");

    private static final EnumUtils.WireIndex<GitHubRepositoryRuleLicenseComplianceScanningType> WIRE_INDEX =
            EnumUtils.index(
                    GitHubRepositoryRuleLicenseComplianceScanningType.class,
                    GitHubRepositoryRuleLicenseComplianceScanningType::wireValue);

    private final String wireValue;

    GitHubRepositoryRuleLicenseComplianceScanningType(final String wireValue) {
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
     * license_compliance_scanning type from a wire value.
     *
     * @param value JSON value
     * @return matching value
     * @throws NullPointerException if {@code value} is {@code null}
     * @throws IllegalArgumentException if {@code value} is not a known value
     */
    @JsonCreator
    public static GitHubRepositoryRuleLicenseComplianceScanningType fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX
                .fromWire(value)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Unknown GitHub repository rule license compliance scanning type: " + value));
    }
}
