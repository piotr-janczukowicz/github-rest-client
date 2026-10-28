package io.github.piotr_janczukowicz.github.api.securityadvisories;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireBetween;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/**
 * The EPSS scores as calculated by the [Exploit Prediction Scoring System](https://www.first.org/epss).
 *
 * @param percentage Percentage
 * @param percentile Percentile
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@CatalogSchema("components/schemas/security-advisory-epss")
@RecordBuilder
public record GitHubSecurityAdvisoryEpss(
        @Nullable Double percentage, @Nullable Double percentile) {

    public GitHubSecurityAdvisoryEpss {
        if (percentage != null) {
            requireBetween(percentage, 0.0, 100.0, "percentage");
        }
        if (percentile != null) {
            requireBetween(percentile, 0.0, 100.0, "percentile");
        }
    }
}
