package io.github.piotr_janczukowicz.github.api.collaborators;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import io.github.piotr_janczukowicz.github.client.GitHubCaller;
import io.github.piotr_janczukowicz.github.client.GitHubResourceApi;
import io.github.piotr_janczukowicz.github.client.exception.GitHubApiException;
import io.github.piotr_janczukowicz.github.pagination.GitHubPage;
import io.github.piotr_janczukowicz.github.pagination.GitHubPageRequest;
import io.github.piotr_janczukowicz.json.JsonType;
import io.github.piotr_janczukowicz.json.JsonTypes;
import io.github.piotr_janczukowicz.uri.PathTemplate;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/** GitHub repository invitations REST resource for API version {@code 2026-03-10}. */
public final class GitHubCollaboratorsInvitationsApi extends GitHubResourceApi {

    private static final PathTemplate USER_INVITATION = PathTemplate.of("/user/repository_invitations/{invitation_id}");
    private static final PathTemplate USER_INVITATIONS = PathTemplate.of("/user/repository_invitations");
    private static final PathTemplate REPO_INVITATION =
            PathTemplate.of("/repos/{owner}/{repo}/invitations/{invitation_id}");
    private static final PathTemplate REPO_INVITATIONS = PathTemplate.of("/repos/{owner}/{repo}/invitations");

    private static final JsonType<List<GitHubRepositoryInvitation>> INVITATIONS_TYPE =
            JsonTypes.listOf(GitHubRepositoryInvitation.class);

    /**
     * Invitations resource over {@code caller}.
     *
     * @param caller executes and decodes the calls of this resource
     * @throws NullPointerException if {@code caller} is {@code null}
     */
    public GitHubCollaboratorsInvitationsApi(final GitHubCaller caller) {
        super(caller);
    }

    /**
     * List repository invitations ({@code GET /repos/{owner}/{repo}/invitations}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @return first HTTP page of invitations
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubRepositoryInvitation> listInvitations(final String owner, final String repo)
            throws IOException {
        return listInvitations(owner, repo, GitHubPageRequest.byPage());
    }

    /**
     * List repository invitations ({@code GET /repos/{owner}/{repo}/invitations}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param request pagination token
     * @return first HTTP page of invitations
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank, or {@code request} is invalid
     * @throws NullPointerException if {@code request} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubRepositoryInvitation> listInvitations(
            final String owner, final String repo, final GitHubPageRequest request) throws IOException {
        requireNonNull(request, PARAM_REQUEST);

        return pageList(REPO_INVITATIONS, Map.of(), request, INVITATIONS_TYPE, owner, repo);
    }

    /**
     * Update a repository invitation ({@code PATCH /repos/{owner}/{repo}/invitations/{invitation_id}}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param invitationId invitation id
     * @param payload fields to update
     * @return updated invitation
     * @throws NullPointerException if {@code payload} is {@code null}
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubRepositoryInvitation updateInvitation(
            final String owner,
            final String repo,
            final long invitationId,
            final GitHubUpdateRepositoryInvitationPayload payload)
            throws IOException {
        requireNonNull(payload, PARAM_PAYLOAD);

        return caller.fetch(
                caller.json(patch(REPO_INVITATION, owner, repo, invitationId), payload),
                GitHubRepositoryInvitation.class);
    }

    /**
     * Delete a repository invitation ({@code DELETE /repos/{owner}/{repo}/invitations/{invitation_id}}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param invitationId invitation id
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public void deleteInvitation(final String owner, final String repo, final long invitationId) throws IOException {
        caller.send(delete(REPO_INVITATION, owner, repo, invitationId));
    }

    /**
     * List repository invitations for the authenticated user ({@code GET /user/repository_invitations}).
     *
     * @return first HTTP page of invitations
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubRepositoryInvitation> listInvitationsForAuthenticatedUser() throws IOException {
        return listInvitationsForAuthenticatedUser(GitHubPageRequest.byPage());
    }

    /**
     * List repository invitations for the authenticated user ({@code GET /user/repository_invitations}).
     *
     * @param request pagination token
     * @return first HTTP page of invitations
     * @throws NullPointerException if {@code request} is {@code null}
     * @throws IllegalArgumentException if {@code request} is invalid
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubRepositoryInvitation> listInvitationsForAuthenticatedUser(final GitHubPageRequest request)
            throws IOException {
        requireNonNull(request, PARAM_REQUEST);

        return pageList(USER_INVITATIONS, Map.of(), request, INVITATIONS_TYPE);
    }

    /**
     * Accept a repository invitation ({@code PATCH /user/repository_invitations/{invitation_id}}).
     *
     * @param invitationId invitation id
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public void acceptInvitationForAuthenticatedUser(final long invitationId) throws IOException {
        caller.send(patch(USER_INVITATION, invitationId));
    }

    /**
     * Decline a repository invitation ({@code DELETE /user/repository_invitations/{invitation_id}}).
     *
     * @param invitationId invitation id
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public void declineInvitationForAuthenticatedUser(final long invitationId) throws IOException {
        caller.send(delete(USER_INVITATION, invitationId));
    }
}
