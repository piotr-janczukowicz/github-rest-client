package io.github.piotr_janczukowicz.github.api.projects;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import io.github.piotr_janczukowicz.github.client.GitHubCaller;
import io.github.piotr_janczukowicz.github.client.GitHubResourceApi;
import io.github.piotr_janczukowicz.github.client.exception.GitHubApiException;
import io.github.piotr_janczukowicz.github.client.pagination.GitHubListSpec;
import io.github.piotr_janczukowicz.github.pagination.GitHubPage;
import io.github.piotr_janczukowicz.github.pagination.GitHubPageRequest;
import io.github.piotr_janczukowicz.github.pagination.GitHubPageRequestKindValidators;
import io.github.piotr_janczukowicz.json.JsonType;
import io.github.piotr_janczukowicz.json.JsonTypes;
import io.github.piotr_janczukowicz.uri.PathTemplate;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

/** GitHub Projects REST resource for API version {@code 2026-03-10}. */
public final class GitHubProjectApi extends GitHubResourceApi {

    private static final PathTemplate ORG_PROJECT = PathTemplate.of("/orgs/{org}/projectsV2/{project_number}");
    private static final PathTemplate USERS_PROJECT = PathTemplate.of("/users/{username}/projectsV2/{project_number}");
    private static final PathTemplate USERS_PROJECTS = PathTemplate.of("/users/{username}/projectsV2");
    private static final PathTemplate ORG_PROJECTS = PathTemplate.of("/orgs/{org}/projectsV2");

    private static final JsonType<List<GitHubProject>> PROJECTS_TYPE = JsonTypes.listOf(GitHubProject.class);

    /**
     * Projects resource over {@code caller}.
     *
     * @param caller executes and decodes the calls of this resource
     * @throws NullPointerException if {@code caller} is {@code null}
     */
    public GitHubProjectApi(final GitHubCaller caller) {
        super(caller);
    }

    /**
     * List organization projects ({@code GET /orgs/{org}/projectsV2}).
     *
     * @param org organization login
     * @return first HTTP page of projects
     * @throws IllegalArgumentException if {@code org} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubProject> listForOrg(final String org) throws IOException {
        return listForOrg(org, GitHubListProjectsFilter.empty(), GitHubPageRequest.byCursor());
    }

    /**
     * List organization projects ({@code GET /orgs/{org}/projectsV2}).
     *
     * @param org organization login
     * @param request pagination token
     * @return first HTTP page of projects
     * @throws IllegalArgumentException if {@code org} is blank, or {@code request} is a page or since variant
     * @throws NullPointerException if {@code request} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubProject> listForOrg(final String org, final GitHubPageRequest request) throws IOException {
        return listForOrg(org, GitHubListProjectsFilter.empty(), request);
    }

    /**
     * List organization projects ({@code GET /orgs/{org}/projectsV2}).
     *
     * @param org organization login
     * @param filter query filters
     * @param request pagination token
     * @return first HTTP page of projects
     * @throws IllegalArgumentException if {@code org} is blank, or {@code request} is a page or since variant
     * @throws NullPointerException if {@code filter} or {@code request} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubProject> listForOrg(
            final String org, final GitHubListProjectsFilter filter, final GitHubPageRequest request)
            throws IOException {
        requireNonNull(filter, PARAM_FILTER);
        requireNonNull(request, PARAM_REQUEST);

        return list(
                ORG_PROJECTS,
                filter.toQueryParameters(),
                request,
                GitHubListSpec.array(GitHubPageRequestKindValidators.CURSOR, PROJECTS_TYPE),
                org);
    }

    /**
     * Get an organization project ({@code GET /orgs/{org}/projectsV2/{project_number}}).
     *
     * @param org organization login
     * @param projectNumber project number
     * @return project, or empty on HTTP 404
     * @throws IllegalArgumentException if {@code org} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful (HTTP 404 yields empty)
     */
    public Optional<GitHubProject> getForOrg(final String org, final long projectNumber) throws IOException {
        return caller.fetchOrEmpty(get(ORG_PROJECT, org, projectNumber), GitHubProject.class);
    }

    /**
     * List user projects ({@code GET /users/{username}/projectsV2}).
     *
     * @param username user login
     * @return first HTTP page of projects
     * @throws IllegalArgumentException if {@code username} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubProject> listForUser(final String username) throws IOException {
        return listForUser(username, GitHubListProjectsFilter.empty(), GitHubPageRequest.byCursor());
    }

    /**
     * List user projects ({@code GET /users/{username}/projectsV2}).
     *
     * @param username user login
     * @param request pagination token
     * @return first HTTP page of projects
     * @throws IllegalArgumentException if {@code username} is blank, or {@code request} is a page or since variant
     * @throws NullPointerException if {@code request} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubProject> listForUser(final String username, final GitHubPageRequest request)
            throws IOException {
        return listForUser(username, GitHubListProjectsFilter.empty(), request);
    }

    /**
     * List user projects ({@code GET /users/{username}/projectsV2}).
     *
     * @param username user login
     * @param filter query filters
     * @param request pagination token
     * @return first HTTP page of projects
     * @throws IllegalArgumentException if {@code username} is blank, or {@code request} is a page or since variant
     * @throws NullPointerException if {@code filter} or {@code request} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubProject> listForUser(
            final String username, final GitHubListProjectsFilter filter, final GitHubPageRequest request)
            throws IOException {
        requireNonNull(filter, PARAM_FILTER);
        requireNonNull(request, PARAM_REQUEST);

        return list(
                USERS_PROJECTS,
                filter.toQueryParameters(),
                request,
                GitHubListSpec.array(GitHubPageRequestKindValidators.CURSOR, PROJECTS_TYPE),
                username);
    }

    /**
     * Get a user project ({@code GET /users/{username}/projectsV2/{project_number}}).
     *
     * @param username user login
     * @param projectNumber project number
     * @return project, or empty on HTTP 404
     * @throws IllegalArgumentException if {@code username} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful (HTTP 404 yields empty)
     */
    public Optional<GitHubProject> getForUser(final String username, final long projectNumber) throws IOException {
        return caller.fetchOrEmpty(get(USERS_PROJECT, username, projectNumber), GitHubProject.class);
    }
}
