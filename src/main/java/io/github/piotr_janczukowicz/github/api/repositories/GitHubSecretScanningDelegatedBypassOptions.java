package io.github.piotr_janczukowicz.github.api.repositories;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.List;
import org.jspecify.annotations.Nullable;

/**
 * Secret scanning delegated bypass options.
 *
 * @param reviewers The bypass reviewers for secret scanning delegated bypass
 */
@CatalogSchema("components/schemas/security-and-analysis/properties/secret_scanning_delegated_bypass_options")
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
public record GitHubSecretScanningDelegatedBypassOptions(
        @Nullable List<GitHubSecretScanningDelegatedBypassReviewer> reviewers) {

    public GitHubSecretScanningDelegatedBypassOptions {
        if (reviewers != null) {
            reviewers = List.copyOf(reviewers);
        }
    }
}
