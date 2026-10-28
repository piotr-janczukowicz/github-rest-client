package io.github.piotr_janczukowicz.github.api.codesecurity;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.List;
import org.jspecify.annotations.Nullable;

/**
 * Feature options for secret scanning delegated bypass.
 *
 * @param reviewers The bypass reviewers for secret scanning delegated bypass
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@CatalogSchema({
    "operations/code-security/create-configuration/requestBody/properties/secret_scanning_delegated_bypass_options",
    "operations/code-security/update-configuration/requestBody/properties/secret_scanning_delegated_bypass_options"
})
@RecordBuilder
public record GitHubSecretScanningDelegatedBypassOptionsInput(
        @Nullable List<GitHubSecretScanningDelegatedBypassReviewerInput> reviewers) {

    public GitHubSecretScanningDelegatedBypassOptionsInput {
        if (reviewers != null) {
            reviewers = List.copyOf(reviewers);
        }
    }
}
