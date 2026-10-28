package io.github.piotr_janczukowicz.github.api.copilot;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubCaller;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.github.client.GitHubResourceApi;
import io.github.piotr_janczukowicz.github.client.exception.GitHubApiException;
import io.github.piotr_janczukowicz.github.client.pagination.GitHubListSpec;
import io.github.piotr_janczukowicz.github.pagination.GitHubPage;
import io.github.piotr_janczukowicz.github.pagination.GitHubPageRequest;
import io.github.piotr_janczukowicz.github.pagination.GitHubPageRequestKindValidators;
import io.github.piotr_janczukowicz.http.HttpStatus;
import io.github.piotr_janczukowicz.uri.PathTemplate;
import java.io.IOException;
import java.util.Map;
import java.util.Optional;

/** GitHub Copilot user management REST resource for API version {@code 2026-03-10}. */
public final class GitHubCopilotCopilotUserManagementApi extends GitHubResourceApi {

    private static final PathTemplate ORG_BILLING = PathTemplate.of("/orgs/{org}/copilot/billing");
    private static final PathTemplate ORG_BILLING_SEATS = PathTemplate.of("/orgs/{org}/copilot/billing/seats");
    private static final PathTemplate ORG_BILLING_SELECTED_TEAMS =
            PathTemplate.of("/orgs/{org}/copilot/billing/selected_teams");
    private static final PathTemplate ORG_BILLING_SELECTED_USERS =
            PathTemplate.of("/orgs/{org}/copilot/billing/selected_users");
    private static final PathTemplate ORG_MEMBER_COPILOT = PathTemplate.of("/orgs/{org}/members/{username}/copilot");
    private static final PathTemplate ENTERPRISE_BILLING_SEATS =
            PathTemplate.of("/enterprises/{enterprise}/copilot/billing/seats");
    private static final PathTemplate ENTERPRISE_BILLING_SELECTED_TEAMS =
            PathTemplate.of("/enterprises/{enterprise}/copilot/billing/selected_enterprise_teams");
    private static final PathTemplate ENTERPRISE_BILLING_SELECTED_USERS =
            PathTemplate.of("/enterprises/{enterprise}/copilot/billing/selected_users");
    private static final PathTemplate ENTERPRISE_MEMBER_COPILOT =
            PathTemplate.of("/enterprises/{enterprise}/members/{username}/copilot");

    /**
     * Copilot user management resource over {@code caller}.
     *
     * @param caller executes and decodes the calls of this resource
     * @throws NullPointerException if {@code caller} is {@code null}
     */
    public GitHubCopilotCopilotUserManagementApi(final GitHubCaller caller) {
        super(caller);
    }

    /**
     * Get Copilot billing details for an organization ({@code GET /orgs/{org}/copilot/billing}).
     *
     * @param org organization name
     * @return organization Copilot details
     * @throws IllegalArgumentException if {@code org} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public GitHubCopilotOrganizationDetails getOrganizationDetails(final String org) throws IOException {
        requireFlavor(GitHubFlavor.CLOUD, GitHubFlavor.GHEC);
        return caller.fetch(get(ORG_BILLING, org), GitHubCopilotOrganizationDetails.class);
    }

    /**
     * List Copilot seat assignments for an organization ({@code GET /orgs/{org}/copilot/billing/seats}).
     *
     * <p>Uses {@link GitHubPageRequest#byPage()}. See {@link #listSeats(String, GitHubPageRequest)}.
     *
     * @param org organization name
     * @return first HTTP page of seats
     * @throws IllegalArgumentException if {@code org} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public GitHubPage<GitHubCopilotSeatDetails> listSeats(final String org) throws IOException {
        return listSeats(org, GitHubPageRequest.byPage());
    }

    /**
     * List Copilot seat assignments for an organization ({@code GET /orgs/{org}/copilot/billing/seats}).
     *
     * <p>The response's {@code total_seats} is carried by {@link GitHubPage#totalCount()}, which is {@code null} when
     * absent.
     *
     * @param org organization name
     * @param request pagination token
     * @return first HTTP page of seats
     * @throws IllegalArgumentException if {@code org} is blank, or {@code request} is a since or cursor variant
     * @throws NullPointerException if {@code request} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public GitHubPage<GitHubCopilotSeatDetails> listSeats(final String org, final GitHubPageRequest request)
            throws IOException {
        requireFlavor(GitHubFlavor.CLOUD, GitHubFlavor.GHEC);
        requireNonNull(request, PARAM_REQUEST);

        return list(
                ORG_BILLING_SEATS,
                Map.of(),
                request,
                GitHubListSpec.wrapper(
                        GitHubPageRequestKindValidators.PAGE,
                        GitHubListCopilotSeatsResponse.class,
                        GitHubListCopilotSeatsResponse::seats,
                        GitHubListCopilotSeatsResponse::totalSeats),
                org);
    }

    /**
     * Add Copilot seats for teams ({@code POST /orgs/{org}/copilot/billing/selected_teams}).
     *
     * @param org organization name
     * @param payload teams to assign
     * @return number of seats created
     * @throws IllegalArgumentException if {@code org} is blank
     * @throws NullPointerException if {@code payload} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public GitHubAddCopilotSeatsForTeamsResponse addSeatsForTeams(
            final String org, final GitHubAddCopilotSeatsForTeamsPayload payload) throws IOException {
        requireFlavor(GitHubFlavor.CLOUD, GitHubFlavor.GHEC);
        requireNonNull(payload, PARAM_PAYLOAD);

        return caller.fetch(
                caller.json(post(ORG_BILLING_SELECTED_TEAMS, org), payload),
                GitHubAddCopilotSeatsForTeamsResponse.class);
    }

    /**
     * Cancel Copilot seat assignments for teams ({@code DELETE /orgs/{org}/copilot/billing/selected_teams}).
     *
     * @param org organization name
     * @param payload teams to unassign
     * @return number of seats cancelled
     * @throws IllegalArgumentException if {@code org} is blank
     * @throws NullPointerException if {@code payload} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public GitHubCancelCopilotSeatAssignmentForTeamsResponse cancelSeatAssignmentForTeams(
            final String org, final GitHubCancelCopilotSeatAssignmentForTeamsPayload payload) throws IOException {
        requireFlavor(GitHubFlavor.CLOUD, GitHubFlavor.GHEC);
        requireNonNull(payload, PARAM_PAYLOAD);

        return caller.fetch(
                caller.json(delete(ORG_BILLING_SELECTED_TEAMS, org), payload),
                GitHubCancelCopilotSeatAssignmentForTeamsResponse.class);
    }

    /**
     * Add Copilot seats for users ({@code POST /orgs/{org}/copilot/billing/selected_users}).
     *
     * @param org organization name
     * @param payload users to assign
     * @return number of seats created
     * @throws IllegalArgumentException if {@code org} is blank
     * @throws NullPointerException if {@code payload} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public GitHubAddCopilotSeatsForUsersResponse addSeatsForUsers(
            final String org, final GitHubAddCopilotSeatsForUsersPayload payload) throws IOException {
        requireFlavor(GitHubFlavor.CLOUD, GitHubFlavor.GHEC);
        requireNonNull(payload, PARAM_PAYLOAD);

        return caller.fetch(
                caller.json(post(ORG_BILLING_SELECTED_USERS, org), payload),
                GitHubAddCopilotSeatsForUsersResponse.class);
    }

    /**
     * Cancel Copilot seat assignments for users ({@code DELETE /orgs/{org}/copilot/billing/selected_users}).
     *
     * @param org organization name
     * @param payload users to unassign
     * @return number of seats cancelled
     * @throws IllegalArgumentException if {@code org} is blank
     * @throws NullPointerException if {@code payload} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public GitHubCancelCopilotSeatAssignmentForUsersResponse cancelSeatAssignmentForUsers(
            final String org, final GitHubCancelCopilotSeatAssignmentForUsersPayload payload) throws IOException {
        requireFlavor(GitHubFlavor.CLOUD, GitHubFlavor.GHEC);
        requireNonNull(payload, PARAM_PAYLOAD);

        return caller.fetch(
                caller.json(delete(ORG_BILLING_SELECTED_USERS, org), payload),
                GitHubCancelCopilotSeatAssignmentForUsersResponse.class);
    }

    /**
     * Get Copilot seat details for a user ({@code GET /orgs/{org}/members/{username}/copilot}).
     *
     * @param org organization name
     * @param username user login
     * @return seat details, or empty on HTTP 404
     * @throws IllegalArgumentException if {@code org} or {@code username} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public Optional<GitHubCopilotSeatDetails> getSeatDetailsForUser(final String org, final String username)
            throws IOException {
        requireFlavor(GitHubFlavor.CLOUD, GitHubFlavor.GHEC);
        return caller.fetchOrEmpty(get(ORG_MEMBER_COPILOT, org, username), GitHubCopilotSeatDetails.class);
    }

    /**
     * List Copilot seat assignments for an enterprise ({@code GET /enterprises/{enterprise}/copilot/billing/seats}).
     *
     * <p>Uses {@link GitHubPageRequest#byPage()}. See {@link #listSeatsForEnterprise(String, GitHubPageRequest)}.
     *
     * @param enterprise the slug version of the enterprise name
     * @return first HTTP page of seats
     * @throws IllegalArgumentException if {@code enterprise} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHEC}
     */
    @Flavors(GitHubFlavor.GHEC)
    public GitHubPage<GitHubCopilotSeatDetails> listSeatsForEnterprise(final String enterprise) throws IOException {
        return listSeatsForEnterprise(enterprise, GitHubPageRequest.byPage());
    }

    /**
     * List Copilot seat assignments for an enterprise ({@code GET /enterprises/{enterprise}/copilot/billing/seats}).
     *
     * <p>The response's {@code total_seats} is carried by {@link GitHubPage#totalCount()}, which is {@code null} when
     * absent.
     *
     * @param enterprise the slug version of the enterprise name
     * @param request pagination token
     * @return requested page of seats
     * @throws IllegalArgumentException if {@code enterprise} is blank, or {@code request} is a since or cursor variant
     * @throws NullPointerException if {@code request} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHEC}
     */
    @Flavors(GitHubFlavor.GHEC)
    public GitHubPage<GitHubCopilotSeatDetails> listSeatsForEnterprise(
            final String enterprise, final GitHubPageRequest request) throws IOException {
        requireFlavor(GitHubFlavor.GHEC);
        requireNonNull(request, PARAM_REQUEST);

        return list(
                ENTERPRISE_BILLING_SEATS,
                Map.of(),
                request,
                GitHubListSpec.wrapper(
                        GitHubPageRequestKindValidators.PAGE,
                        GitHubListCopilotSeatsForEnterpriseResponse.class,
                        GitHubListCopilotSeatsForEnterpriseResponse::seats,
                        GitHubListCopilotSeatsForEnterpriseResponse::totalSeats),
                enterprise);
    }

    /**
     * Add Copilot seats for enterprise teams ({@code POST
     * /enterprises/{enterprise}/copilot/billing/selected_enterprise_teams}).
     *
     * @param enterprise the slug version of the enterprise name
     * @param payload enterprise teams to assign
     * @return number of seats created
     * @throws IllegalArgumentException if {@code enterprise} is blank
     * @throws NullPointerException if {@code payload} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHEC}
     */
    @Flavors(GitHubFlavor.GHEC)
    public GitHubAddCopilotSeatsForEnterpriseTeamsResponse addSeatsForEnterpriseTeams(
            final String enterprise, final GitHubAddCopilotSeatsForEnterpriseTeamsPayload payload) throws IOException {
        requireFlavor(GitHubFlavor.GHEC);
        requireNonNull(payload, PARAM_PAYLOAD);

        return caller.fetch(
                caller.json(post(ENTERPRISE_BILLING_SELECTED_TEAMS, enterprise), payload),
                GitHubAddCopilotSeatsForEnterpriseTeamsResponse.class);
    }

    /**
     * Cancel Copilot seat assignments for enterprise teams ({@code DELETE
     * /enterprises/{enterprise}/copilot/billing/selected_enterprise_teams}).
     *
     * <p>HTTP 200 carries the seats cancelled; HTTP 202 means the cancellation was accepted for asynchronous
     * processing.
     *
     * @param enterprise the slug version of the enterprise name
     * @param payload enterprise teams to unassign
     * @return cancelled result, or accepted result on HTTP 202
     * @throws IllegalArgumentException if {@code enterprise} is blank
     * @throws NullPointerException if {@code payload} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHEC}
     */
    @Flavors(GitHubFlavor.GHEC)
    public GitHubCancelCopilotSeatAssignmentForEnterpriseTeamsResult cancelSeatAssignmentForEnterpriseTeams(
            final String enterprise, final GitHubCancelCopilotSeatAssignmentForEnterpriseTeamsPayload payload)
            throws IOException {
        requireFlavor(GitHubFlavor.GHEC);
        requireNonNull(payload, PARAM_PAYLOAD);

        return caller.fetchByStatus(
                caller.json(delete(ENTERPRISE_BILLING_SELECTED_TEAMS, enterprise), payload),
                body -> GitHubCancelCopilotSeatAssignmentForEnterpriseTeamsResult.ofCancelled(
                        body.as(GitHubCancelCopilotSeatAssignmentForEnterpriseTeamsResponse.class)),
                Map.of(
                        HttpStatus.ACCEPTED,
                        body -> GitHubCancelCopilotSeatAssignmentForEnterpriseTeamsResult.ofAccepted(
                                body.as(GitHubCancelCopilotSeatAssignmentForEnterpriseTeamsAcceptedResponse.class))));
    }

    /**
     * Add Copilot seats for enterprise users ({@code POST /enterprises/{enterprise}/copilot/billing/selected_users}).
     *
     * @param enterprise the slug version of the enterprise name
     * @param payload enterprise users to assign
     * @return number of seats created
     * @throws IllegalArgumentException if {@code enterprise} is blank
     * @throws NullPointerException if {@code payload} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHEC}
     */
    @Flavors(GitHubFlavor.GHEC)
    public GitHubAddCopilotSeatsForEnterpriseUsersResponse addSeatsForEnterpriseUsers(
            final String enterprise, final GitHubAddCopilotSeatsForEnterpriseUsersPayload payload) throws IOException {
        requireFlavor(GitHubFlavor.GHEC);
        requireNonNull(payload, PARAM_PAYLOAD);

        return caller.fetch(
                caller.json(post(ENTERPRISE_BILLING_SELECTED_USERS, enterprise), payload),
                GitHubAddCopilotSeatsForEnterpriseUsersResponse.class);
    }

    /**
     * Cancel Copilot seat assignments for enterprise users ({@code DELETE
     * /enterprises/{enterprise}/copilot/billing/selected_users}).
     *
     * @param enterprise the slug version of the enterprise name
     * @param payload enterprise users to unassign
     * @return number of seats cancelled
     * @throws IllegalArgumentException if {@code enterprise} is blank
     * @throws NullPointerException if {@code payload} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHEC}
     */
    @Flavors(GitHubFlavor.GHEC)
    public GitHubCancelCopilotSeatAssignmentForEnterpriseUsersResponse cancelSeatAssignmentForEnterpriseUsers(
            final String enterprise, final GitHubCancelCopilotSeatAssignmentForEnterpriseUsersPayload payload)
            throws IOException {
        requireFlavor(GitHubFlavor.GHEC);
        requireNonNull(payload, PARAM_PAYLOAD);

        return caller.fetch(
                caller.json(delete(ENTERPRISE_BILLING_SELECTED_USERS, enterprise), payload),
                GitHubCancelCopilotSeatAssignmentForEnterpriseUsersResponse.class);
    }

    /**
     * Get Copilot seat details for an enterprise user ({@code GET
     * /enterprises/{enterprise}/members/{username}/copilot}).
     *
     * @param enterprise the slug version of the enterprise name
     * @param username user login
     * @return seat details, or empty on HTTP 404
     * @throws IllegalArgumentException if {@code enterprise} or {@code username} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHEC}
     */
    @Flavors(GitHubFlavor.GHEC)
    public Optional<GitHubGetCopilotSeatDetailsForEnterpriseUserResponse> getSeatDetailsForEnterpriseUser(
            final String enterprise, final String username) throws IOException {
        requireFlavor(GitHubFlavor.GHEC);
        return caller.fetchOrEmpty(
                get(ENTERPRISE_MEMBER_COPILOT, enterprise, username),
                GitHubGetCopilotSeatDetailsForEnterpriseUserResponse.class);
    }
}
