package io.github.piotr_janczukowicz.github.api.secretscanning;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireMaxLength;
import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;

/**
 * Review of a dismissal request.
 *
 * @param status The review action to perform on the dismissal request
 * @param message A message to include with the review. Has a maximum character length of 2048
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("operations/secret-scanning/review-dismissal-request/requestBody")
@Flavors({GitHubFlavor.GHEC, GitHubFlavor.GHES})
public record GitHubReviewDismissalRequestPayload(GitHubReviewDismissalRequestStatus status, String message) {

    public GitHubReviewDismissalRequestPayload {
        requireNonNull(status, "status");
        message = requireMaxLength(requireNonNull(message, "message"), 2048, "message");
    }
}
