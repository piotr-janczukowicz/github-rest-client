package io.github.piotr_janczukowicz.github.api.securityadvisories;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireBetween;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/**
 * A CVSS severity score (v3 or v4).
 *
 * @param vectorString The CVSS vector string.
 * @param score The CVSS score.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@CatalogSchema({
    "components/schemas/cvss-severities/properties/cvss_v3",
    "components/schemas/cvss-severities/properties/cvss_v4"
})
@RecordBuilder
public record GitHubCvssScore(
        @Nullable String vectorString, @Nullable Double score) {

    public GitHubCvssScore {
        if (score != null) {
            requireBetween(score, 0.0, 10.0, "score");
        }
    }
}
