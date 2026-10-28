package io.github.piotr_janczukowicz.github.api.dependabot;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireMaxLength;
import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;

/**
 * Review a dismissal request for a Dependabot alert.
 *
 * @param status Whether to approve or deny the dismissal request
 * @param message The message to include with the review
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("operations/dependabot/review-dismissal-request-for-repo/requestBody")
@Flavors({GitHubFlavor.GHEC, GitHubFlavor.GHES})
public record GitHubReviewDismissalRequestPayload(GitHubReviewDismissalRequestStatus status, String message) {

    public GitHubReviewDismissalRequestPayload {
        requireNonNull(status, "status");
        message = requireMaxLength(requireNonNull(message, "message"), 2048, "message");
    }
}
