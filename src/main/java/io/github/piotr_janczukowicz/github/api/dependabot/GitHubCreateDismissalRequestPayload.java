package io.github.piotr_janczukowicz.github.api.dependabot;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireMaxLengthIfPresent;
import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/**
 * Request a dismissal of a Dependabot alert.
 *
 * @param dismissedReason The reason the alert is being dismissed
 * @param dismissedComment An optional comment to include with the dismissal request
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("operations/dependabot/create-dismissal-request-for-repo/requestBody")
@Flavors({GitHubFlavor.GHEC, GitHubFlavor.GHES})
public record GitHubCreateDismissalRequestPayload(
        GitHubCreateDismissalRequestDismissedReason dismissedReason,
        @Nullable String dismissedComment) {

    public GitHubCreateDismissalRequestPayload {
        requireNonNull(dismissedReason, "dismissedReason");
        dismissedComment = requireMaxLengthIfPresent(dismissedComment, 280, "dismissedComment");
    }
}
