package io.github.piotr_janczukowicz.github.api.dependabot;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.api.codescanning.GitHubDismissalRequestResponse;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.time.Instant;
import java.util.List;
import org.jspecify.annotations.Nullable;

/**
 * A dismissal request made by a user asking to close a Dependabot alert in this repository.
 *
 * @param id The unique identifier of the dismissal request
 * @param number The number uniquely identifying the dismissal request within its repository
 * @param repository The repository the dismissal request is for
 * @param organization The organization associated with the repository the dismissal request is for
 * @param requester The user who requested the dismissal
 * @param requestType The type of request
 * @param data Data describing the Dependabot alert that is requested to be dismissed
 * @param resourceIdentifier The number of the Dependabot alert that is requested to be dismissed
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
@CatalogSchema("components/schemas/dependabot-alert-dismissal-request")
@Flavors({GitHubFlavor.GHEC, GitHubFlavor.GHES})
public record GitHubDependabotDismissalRequest(
        @Nullable Long id,
        @Nullable Long number,
        @Nullable GitHubDependabotDismissalRequestRepository repository,
        @Nullable GitHubDependabotDismissalRequestOrganization organization,
        @Nullable GitHubDependabotDismissalRequestRequester requester,
        @Nullable String requestType,
        @Nullable List<GitHubDependabotDismissalRequestData> data,
        @Nullable String resourceIdentifier,
        @Nullable GitHubDependabotDismissalRequestStatus status,
        @Nullable String requesterComment,
        @Nullable Instant expiresAt,
        @Nullable Instant createdAt,
        @Nullable List<GitHubDismissalRequestResponse> responses,
        @Nullable String url,
        @Nullable String htmlUrl) {}
