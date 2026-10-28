package io.github.piotr_janczukowicz.github.api.billing;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/** The type of usage report. */
@CatalogSchema("components/schemas/usage-report-export/properties/report_type")
@Flavors(GitHubFlavor.GHEC)
public enum GitHubUsageReportExportReportType {
    DETAILED("detailed"),
    SUMMARIZED("summarized"),
    PREMIUM_REQUEST("premium_request"),
    AI_CREDIT("ai_credit"),
    /** A value this library version does not know. */
    UNKNOWN("unknown");

    private static final EnumUtils.WireIndex<GitHubUsageReportExportReportType> WIRE_INDEX =
            EnumUtils.index(GitHubUsageReportExportReportType.class, GitHubUsageReportExportReportType::wireValue);

    private final String wireValue;

    GitHubUsageReportExportReportType(final String wireValue) {
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
     * @return matching constant, or {@link #UNKNOWN} when the value is not known
     * @throws NullPointerException if {@code value} is {@code null}
     */
    @JsonCreator
    public static GitHubUsageReportExportReportType fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX.fromWire(value, UNKNOWN);
    }
}
