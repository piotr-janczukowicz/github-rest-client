package io.github.piotr_janczukowicz.github.api.organizations;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import io.github.piotr_janczukowicz.github.api.users.GitHubSimpleUser;
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
import org.jspecify.annotations.Nullable;

/** GitHub organization outside collaborators REST resource for API version {@code 2026-03-10}. */
public final class GitHubOrganizationsOutsideCollaboratorsApi extends GitHubResourceApi {

    private static final PathTemplate OUTSIDE_COLLABORATOR =
            PathTemplate.of("/orgs/{org}/outside_collaborators/{username}");
    private static final PathTemplate OUTSIDE_COLLABORATORS = PathTemplate.of("/orgs/{org}/outside_collaborators");

    private static final JsonType<List<GitHubSimpleUser>> USERS_TYPE = JsonTypes.listOf(GitHubSimpleUser.class);

    /**
     * Outside collaborators resource over {@code caller}.
     *
     * @param caller executes and decodes the calls of this resource
     * @throws NullPointerException if {@code caller} is {@code null}
     */
    public GitHubOrganizationsOutsideCollaboratorsApi(final GitHubCaller caller) {
        super(caller);
    }

    /**
     * List outside collaborators for an organization ({@code GET /orgs/{org}/outside_collaborators}).
     *
     * <p>Uses {@link GitHubPageRequest#byPage()}. See {@link #listOutsideCollaborators(String,
     * GitHubOrganizationsOutsideCollaboratorsFilter, GitHubPageRequest)}.
     *
     * @param org organization name
     * @return first HTTP page of outside collaborators
     * @throws IllegalArgumentException if {@code org} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubSimpleUser> listOutsideCollaborators(final String org) throws IOException {
        return listOutsideCollaborators(
                org, GitHubOrganizationsOutsideCollaboratorsFilter.empty(), GitHubPageRequest.byPage());
    }

    /**
     * List outside collaborators for an organization ({@code GET /orgs/{org}/outside_collaborators}).
     *
     * <p>Uses {@link GitHubPageRequest#byPage()}. See {@link #listOutsideCollaborators(String,
     * GitHubOrganizationsOutsideCollaboratorsFilter, GitHubPageRequest)}.
     *
     * @param org organization name
     * @param filter optional query filters
     * @return first HTTP page of outside collaborators
     * @throws NullPointerException if {@code filter} is {@code null}
     * @throws IllegalArgumentException if {@code org} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubSimpleUser> listOutsideCollaborators(
            final String org, final GitHubOrganizationsOutsideCollaboratorsFilter filter) throws IOException {
        return listOutsideCollaborators(org, filter, GitHubPageRequest.byPage());
    }

    /**
     * List outside collaborators for an organization ({@code GET /orgs/{org}/outside_collaborators}).
     *
     * <p>Accepts {@link io.github.piotr_janczukowicz.github.pagination.GitHubPageNumberRequest} and
     * {@link io.github.piotr_janczukowicz.github.pagination.GitHubNoneRequest} only.
     *
     * @param org organization name
     * @param request pagination token
     * @return first HTTP page of outside collaborators
     * @throws NullPointerException if {@code request} is {@code null}
     * @throws IllegalArgumentException if {@code org} is blank, or {@code request} is a since or cursor variant
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubSimpleUser> listOutsideCollaborators(final String org, final GitHubPageRequest request)
            throws IOException {
        return listOutsideCollaborators(org, GitHubOrganizationsOutsideCollaboratorsFilter.empty(), request);
    }

    /**
     * List outside collaborators for an organization ({@code GET /orgs/{org}/outside_collaborators}).
     *
     * <p>Accepts {@link io.github.piotr_janczukowicz.github.pagination.GitHubPageNumberRequest} and
     * {@link io.github.piotr_janczukowicz.github.pagination.GitHubNoneRequest} only.
     *
     * @param org organization name
     * @param filter optional query filters
     * @param request pagination token
     * @return first HTTP page of outside collaborators
     * @throws NullPointerException if {@code filter} or {@code request} is {@code null}
     * @throws IllegalArgumentException if {@code org} is blank, or {@code request} is a since or cursor variant
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubSimpleUser> listOutsideCollaborators(
            final String org,
            final GitHubOrganizationsOutsideCollaboratorsFilter filter,
            final GitHubPageRequest request)
            throws IOException {
        requireNonNull(filter, PARAM_FILTER);

        return pageList(OUTSIDE_COLLABORATORS, filter.toQueryParameters(), request, USERS_TYPE, org);
    }

    /**
     * Convert an organization member to an outside collaborator ({@code PUT
     * /orgs/{org}/outside_collaborators/{username}}).
     *
     * <p>Succeeds on HTTP 204 (synchronous) and HTTP 202 (asynchronous).
     *
     * @param org organization name
     * @param username GitHub username
     * @throws IllegalArgumentException if {@code org} or {@code username} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public void convertMemberToOutsideCollaborator(final String org, final String username) throws IOException {
        convertMemberToOutsideCollaborator(org, username, null);
    }

    /**
     * Convert an organization member to an outside collaborator ({@code PUT
     * /orgs/{org}/outside_collaborators/{username}}).
     *
     * <p>Succeeds on HTTP 204 (synchronous) and HTTP 202 (asynchronous).
     *
     * @param org organization name
     * @param username GitHub username
     * @param payload optional request body
     * @throws IllegalArgumentException if {@code org} or {@code username} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public void convertMemberToOutsideCollaborator(
            final String org,
            final String username,
            final @Nullable GitHubConvertMemberToOutsideCollaboratorPayload payload)
            throws IOException {

        caller.send(caller.jsonIfPresent(put(OUTSIDE_COLLABORATOR, org, username), payload));
    }

    /**
     * Remove an outside collaborator from an organization ({@code DELETE
     * /orgs/{org}/outside_collaborators/{username}}).
     *
     * @param org organization name
     * @param username GitHub username
     * @throws IllegalArgumentException if {@code org} or {@code username} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public void removeOutsideCollaborator(final String org, final String username) throws IOException {
        caller.send(delete(OUTSIDE_COLLABORATOR, org, username));
    }
}
