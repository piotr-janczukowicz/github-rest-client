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
 * A bypass request made by a user asking to be exempted from push protection in this repository.
 *
 * @param id The unique identifier of the bypass request
 * @param number The number uniquely identifying the bypass request within its repository
 * @param repository The repository the bypass request is for
 * @param organization The organization associated with the repository the bypass request is for
 * @param requester The user who requested the bypass
 * @param requestType The type of request
 * @param data Data describing the push rules that are being requested to be bypassed.
 * @param resourceIdentifier The unique identifier for the request type of the bypass request, for example a commit SHA.
 * @param status The status of the bypass request
 * @param requesterComment The comment the requester provided when creating the bypass request
 * @param expiresAt The date and time the bypass request will expire
 * @param createdAt The date and time the bypass request was created
 * @param responses The responses to the bypass request
 * @param url The API URL of the bypass request
 * @param htmlUrl The URL to view the bypass request in a browser
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/secret-scanning-bypass-request")
@Flavors({GitHubFlavor.GHEC, GitHubFlavor.GHES})
public record GitHubSecretScanningBypassRequest(
        @Nullable Long id,
        @Nullable Integer number,
        @Nullable GitHubSecretScanningBypassRequestRepository repository,
        @Nullable GitHubSecretScanningBypassRequestOrganization organization,
        @Nullable GitHubSecretScanningBypassRequestRequester requester,
        @Nullable String requestType,
        @Nullable List<GitHubSecretScanningBypassRequestData> data,
        @Nullable String resourceIdentifier,
        @Nullable GitHubSecretScanningBypassRequestStatus status,
        @Nullable String requesterComment,
        @Nullable Instant expiresAt,
        @Nullable Instant createdAt,
        @Nullable List<GitHubBypassResponse> responses,
        @Nullable String url,
        @Nullable String htmlUrl) {}
