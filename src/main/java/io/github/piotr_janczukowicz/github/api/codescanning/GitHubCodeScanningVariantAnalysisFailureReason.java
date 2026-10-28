package io.github.piotr_janczukowicz.github.api.codescanning;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/** The reason for a failure of the variant analysis. */
@CatalogSchema("components/schemas/code-scanning-variant-analysis/properties/failure_reason")
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public enum GitHubCodeScanningVariantAnalysisFailureReason {
    NO_REPOS_QUERIED("no_repos_queried"),
    ACTIONS_WORKFLOW_RUN_FAILED("actions_workflow_run_failed"),
    INTERNAL_ERROR("internal_error"),
    /** A value this library version does not know. */
    UNKNOWN("unknown");

    private static final EnumUtils.WireIndex<GitHubCodeScanningVariantAnalysisFailureReason> WIRE_INDEX =
            EnumUtils.index(
                    GitHubCodeScanningVariantAnalysisFailureReason.class,
                    GitHubCodeScanningVariantAnalysisFailureReason::wireValue);

    private final String wireValue;

    GitHubCodeScanningVariantAnalysisFailureReason(final String wireValue) {
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
     * Resolves code scanning variant analysis failure reason from its wire value.
     *
     * @param value JSON value
     * @return matching GitHubCodeScanningVariantAnalysisFailureReason
     * @throws NullPointerException if {@code value} is {@code null}
     */
    @JsonCreator
    public static GitHubCodeScanningVariantAnalysisFailureReason fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX.fromWire(value, UNKNOWN);
    }
}
