package io.github.piotr_janczukowicz.github.api.secretscanning;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.time.Instant;
import java.util.List;
import org.jspecify.annotations.Nullable;

/**
 * A dismissal request made by a user asking to close a secret scanning alert in this repository.
 *
 * @param id The unique identifier of the dismissal request
 * @param number The number uniquely identifying the dismissal request within its repository
 * @param repository The repository the dismissal request is for
 * @param organization The organization associated with the repository the dismissal request is for
 * @param requester The user who requested the dismissal
 * @param requestType The type of request
 * @param data Data describing the secret alert that is being requested to be dismissed.
 * @param resourceIdentifier The number of the secret scanning alert that was detected.
 * @param status The status of the dismissal request
 * @param requesterComment The comment the requester provided when creating the dismissal request
 * @param expiresAt The date and time the dismissal request will expire
 * @param createdAt The date and time the dismissal request was created
 * @param responses The responses to the dismissal request
 * @param url The API URL of the dismissal request
 * @param htmlUrl The URL to view the dismissal request in a browser
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/secret-scanning-dismissal-request")
@Flavors({GitHubFlavor.GHEC, GitHubFlavor.GHES})
public record GitHubSecretScanningDismissalRequest(
        @Nullable Long id,
        @Nullable Integer number,
        @Nullable GitHubSecretScanningDismissalRequestRepository repository,
        @Nullable GitHubSecretScanningDismissalRequestOrganization organization,
        @Nullable GitHubSecretScanningDismissalRequestRequester requester,
        @Nullable String requestType,
        @Nullable List<GitHubSecretScanningDismissalRequestData> data,
        @Nullable String resourceIdentifier,
        @Nullable GitHubSecretScanningDismissalRequestStatus status,
        @Nullable String requesterComment,
        @Nullable Instant expiresAt,
        @Nullable Instant createdAt,
        @Nullable List<GitHubBypassResponse> responses,
        @Nullable String url,
        @Nullable String htmlUrl) {}
