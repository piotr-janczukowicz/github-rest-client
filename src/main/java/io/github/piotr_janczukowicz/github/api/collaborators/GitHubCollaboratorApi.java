package io.github.piotr_janczukowicz.github.api.collaborators;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import io.github.piotr_janczukowicz.github.client.GitHubCaller;
import io.github.piotr_janczukowicz.github.client.GitHubResourceApi;
import io.github.piotr_janczukowicz.github.client.exception.GitHubApiException;
import io.github.piotr_janczukowicz.github.pagination.GitHubPage;
import io.github.piotr_janczukowicz.github.pagination.GitHubPageRequest;
import io.github.piotr_janczukowicz.http.HttpStatus;
import io.github.piotr_janczukowicz.json.JsonType;
import io.github.piotr_janczukowicz.json.JsonTypes;
import io.github.piotr_janczukowicz.uri.PathTemplate;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import org.jspecify.annotations.Nullable;

/** GitHub repository collaborators REST resource for API version {@code 2026-03-10}. */
public final class GitHubCollaboratorApi extends GitHubResourceApi {

    private static final PathTemplate COLLABORATORS = PathTemplate.of("/repos/{owner}/{repo}/collaborators");
    private static final PathTemplate COLLABORATOR = PathTemplate.of("/repos/{owner}/{repo}/collaborators/{username}");
    private static final PathTemplate COLLABORATOR_PERMISSION =
            PathTemplate.of("/repos/{owner}/{repo}/collaborators/{username}/permission");

    private static final JsonType<List<GitHubCollaborator>> COLLABORATORS_TYPE =
            JsonTypes.listOf(GitHubCollaborator.class);

    /**
     * Collaborators resource over {@code caller}.
     *
     * @param caller executes and decodes the calls of this resource
     * @throws NullPointerException if {@code caller} is {@code null}
     */
    public GitHubCollaboratorApi(final GitHubCaller caller) {
        super(caller);
    }

    /**
     * List repository collaborators ({@code GET /repos/{owner}/{repo}/collaborators}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @return first HTTP page of collaborators
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubCollaborator> listCollaborators(final String owner, final String repo) throws IOException {
        return listCollaborators(owner, repo, GitHubListCollaboratorsFilter.empty(), GitHubPageRequest.byPage());
    }

    /**
     * List repository collaborators ({@code GET /repos/{owner}/{repo}/collaborators}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param filter query filters
     * @param request pagination token
     * @return first HTTP page of collaborators
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank, or {@code request} is invalid
     * @throws NullPointerException if {@code filter} or {@code request} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubCollaborator> listCollaborators(
            final String owner,
            final String repo,
            final GitHubListCollaboratorsFilter filter,
            final GitHubPageRequest request)
            throws IOException {
        requireNonNull(filter, PARAM_FILTER);
        requireNonNull(request, PARAM_REQUEST);

        return pageList(COLLABORATORS, filter.toQueryParameters(), request, COLLABORATORS_TYPE, owner, repo);
    }

    /**
     * Check if a user is a repository collaborator ({@code GET /repos/{owner}/{repo}/collaborators/{username}}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param username collaborator login
     * @return {@code true} when GitHub returns HTTP 204
     * @throws IllegalArgumentException if any argument is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful (HTTP 404 yields {@code false})
     */
    public boolean checkCollaborator(final String owner, final String repo, final String username) throws IOException {
        return caller.isExisting(get(COLLABORATOR, owner, repo, username));
    }

    /**
     * Add a repository collaborator ({@code PUT /repos/{owner}/{repo}/collaborators/{username}}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param username collaborator login
     * @return invitation when GitHub returns HTTP 201, or empty on HTTP 204
     * @throws IllegalArgumentException if any argument is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public Optional<GitHubRepositoryInvitation> addCollaborator(
            final String owner, final String repo, final String username) throws IOException {
        return addCollaborator(owner, repo, username, null);
    }

    /**
     * Add a repository collaborator ({@code PUT /repos/{owner}/{repo}/collaborators/{username}}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param username collaborator login
     * @param payload optional permission to grant
     * @return invitation when GitHub returns HTTP 201, or empty on HTTP 204 or no body or {@code {}}
     * @throws IllegalArgumentException if any argument is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public Optional<GitHubRepositoryInvitation> addCollaborator(
            final String owner,
            final String repo,
            final String username,
            final @Nullable GitHubAddCollaboratorPayload payload)
            throws IOException {

        return caller.fetchUnless(
                caller.jsonIfPresent(put(COLLABORATOR, owner, repo, username), payload),
                GitHubRepositoryInvitation.class,
                HttpStatus.NO_CONTENT);
    }

    /**
     * Remove a repository collaborator ({@code DELETE /repos/{owner}/{repo}/collaborators/{username}}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param username collaborator login
     * @throws IllegalArgumentException if any argument is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public void removeCollaborator(final String owner, final String repo, final String username) throws IOException {
        caller.send(delete(COLLABORATOR, owner, repo, username));
    }

    /**
     * Get repository permissions for a user ({@code GET /repos/{owner}/{repo}/collaborators/{username}/permission}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param username collaborator login
     * @return collaborator permission, or empty on HTTP 404
     * @throws IllegalArgumentException if any argument is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful (HTTP 404 yields empty)
     */
    public Optional<GitHubRepositoryCollaboratorPermission> getCollaboratorPermissionLevel(
            final String owner, final String repo, final String username) throws IOException {

        return caller.fetchOrEmpty(
                get(COLLABORATOR_PERMISSION, owner, repo, username), GitHubRepositoryCollaboratorPermission.class);
    }
}
