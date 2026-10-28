package io.github.piotr_janczukowicz.github.api.codesecurity;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/**
 * Bypass reviewer for secret scanning delegated bypass.
 *
 * @param reviewerId The ID of the team or role selected as a bypass reviewer
 * @param reviewerType The type of the bypass reviewer
 * @param mode The bypass mode for the reviewer
 * @param securityConfigurationId The ID of the security configuration associated with this bypass reviewer
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@CatalogSchema(
        "components/schemas/code-security-configuration/properties/secret_scanning_delegated_bypass_options/properties/reviewers/items")
@RecordBuilder
public record GitHubSecretScanningDelegatedBypassReviewer(
        long reviewerId,
        GitHubBypassReviewerType reviewerType,
        @Nullable GitHubBypassMode mode,
        @Nullable Long securityConfigurationId) {

    public GitHubSecretScanningDelegatedBypassReviewer {
        requireNonNull(reviewerType, "reviewerType");
    }
}
