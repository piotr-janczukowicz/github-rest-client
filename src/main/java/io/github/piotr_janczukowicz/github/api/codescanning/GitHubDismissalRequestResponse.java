package io.github.piotr_janczukowicz.github.api.codescanning;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.time.Instant;
import org.jspecify.annotations.Nullable;

/**
 * A response made by a requester to dismiss the request.
 *
 * @param id The ID of the response to the dismissal request
 * @param reviewer The user who reviewed the dismissal request
 * @param message The response comment of the reviewer
 * @param status The response status to the dismissal request until dismissed
 * @param createdAt The date and time the response to the dismissal request was created
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/dismissal-request-response")
@Flavors({GitHubFlavor.GHEC, GitHubFlavor.GHES})
public record GitHubDismissalRequestResponse(
        @Nullable Long id,
        @Nullable GitHubDismissalRequestResponseReviewer reviewer,
        @Nullable String message,
        @Nullable GitHubDismissalRequestResponseStatus status,
        @Nullable Instant createdAt) {}
