package io.github.piotr_janczukowicz.github.api.repositories;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/**
 * Reviewers.
 *
 * @param reviewerId The ID of the team or role selected as a bypass reviewer
 * @param reviewerType The type of the bypass reviewer
 * @param mode The bypass mode for the reviewer
 */
@CatalogSchema(
        "components/schemas/security-and-analysis/properties/secret_scanning_delegated_bypass_options/properties/reviewers/items")
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
public record GitHubSecretScanningDelegatedBypassReviewer(
        long reviewerId,
        GitHubSecretScanningBypassReviewerType reviewerType,
        @Nullable GitHubSecretScanningDelegatedBypassMode mode) {

    public GitHubSecretScanningDelegatedBypassReviewer {
        requireNonNull(reviewerType, "reviewerType");
    }
}
