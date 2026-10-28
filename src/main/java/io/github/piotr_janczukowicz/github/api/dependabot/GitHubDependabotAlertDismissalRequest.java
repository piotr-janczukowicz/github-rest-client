package io.github.piotr_janczukowicz.github.api.dependabot;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.time.Instant;
import org.jspecify.annotations.Nullable;

/**
 * Information about an active dismissal request for this Dependabot alert.
 *
 * @param id The unique identifier of the dismissal request.
 * @param status The current status of the dismissal request.
 * @param requester The user who requested the dismissal.
 * @param createdAt The date and time when the dismissal request was created.
 * @param url The API URL to get more information about this dismissal request.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/dependabot-alert-dismissal-request-simple")
public record GitHubDependabotAlertDismissalRequest(
        @Nullable Long id,
        @Nullable GitHubDependabotAlertDismissalRequestStatus status,
        @Nullable GitHubDependabotAlertDismissalRequestRequester requester,
        @Nullable Instant createdAt,
        @Nullable String url) {}
