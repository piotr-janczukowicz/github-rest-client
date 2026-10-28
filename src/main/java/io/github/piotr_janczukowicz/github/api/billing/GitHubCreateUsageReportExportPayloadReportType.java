package io.github.piotr_janczukowicz.github.api.billing;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/** The type of usage report to generate. */
@CatalogSchema("components/schemas/usage-report-export-request/properties/report_type")
@Flavors(GitHubFlavor.GHEC)
public enum GitHubCreateUsageReportExportPayloadReportType {
    DETAILED("detailed"),
    SUMMARIZED("summarized"),
    PREMIUM_REQUEST("premium_request"),
    AI_CREDIT("ai_credit");

    private static final EnumUtils.WireIndex<GitHubCreateUsageReportExportPayloadReportType> WIRE_INDEX =
            EnumUtils.index(
                    GitHubCreateUsageReportExportPayloadReportType.class,
                    GitHubCreateUsageReportExportPayloadReportType::wireValue);

    private final String wireValue;

    GitHubCreateUsageReportExportPayloadReportType(final String wireValue) {
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
     * Resolves the constant of this enum from its wire value.
     *
     * @param value JSON value
     * @return matching constant
     * @throws NullPointerException if {@code value} is {@code null}
     * @throws IllegalArgumentException if {@code value} is not a known value
     */
    @JsonCreator
    public static GitHubCreateUsageReportExportPayloadReportType fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX
                .fromWire(value)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Unknown GitHubCreateUsageReportExportPayloadReportType value: " + value));
    }
}
