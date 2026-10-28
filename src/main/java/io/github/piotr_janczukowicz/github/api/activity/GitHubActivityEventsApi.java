package io.github.piotr_janczukowicz.github.api.activity;

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

/** GitHub activity events REST resource for API version {@code 2026-03-10}. */
public final class GitHubActivityEventsApi extends GitHubResourceApi {

    private static final PathTemplate PUBLIC_EVENTS = PathTemplate.of("/events");
    private static final PathTemplate NETWORK_EVENTS = PathTemplate.of("/networks/{owner}/{repo}/events");
    private static final PathTemplate ORG_EVENTS = PathTemplate.of("/orgs/{org}/events");
    private static final PathTemplate REPO_EVENTS = PathTemplate.of("/repos/{owner}/{repo}/events");
    private static final PathTemplate USERS_EVENTS = PathTemplate.of("/users/{username}/events");
    private static final PathTemplate USERS_ORG_EVENTS = PathTemplate.of("/users/{username}/events/orgs/{org}");
    private static final PathTemplate USERS_PUBLIC_EVENTS = PathTemplate.of("/users/{username}/events/public");
    private static final PathTemplate USERS_RECEIVED_EVENTS = PathTemplate.of("/users/{username}/received_events");
    private static final PathTemplate USERS_RECEIVED_PUBLIC_EVENTS =
            PathTemplate.of("/users/{username}/received_events/public");

    private static final JsonType<List<GitHubEvent>> EVENTS_TYPE = JsonTypes.listOf(GitHubEvent.class);

    /**
     * Events resource over {@code caller}.
     *
     * @param caller executes and decodes the calls of this resource
     * @throws NullPointerException if {@code caller} is {@code null}
     */
    public GitHubActivityEventsApi(final GitHubCaller caller) {
        super(caller);
    }

    /**
     * List public events ({@code GET /events}).
     *
     * @return first HTTP page of events
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubEvent> listPublicEvents() throws IOException {
        return listPublicEvents(GitHubPageRequest.byPage());
    }

    /**
     * List public events ({@code GET /events}).
     *
     * @param request pagination token
     * @return HTTP page of events
     * @throws NullPointerException if {@code request} is {@code null}
     * @throws IllegalArgumentException if {@code request} is invalid
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubEvent> listPublicEvents(final GitHubPageRequest request) throws IOException {
        requireNonNull(request, PARAM_REQUEST);

        return pageList(PUBLIC_EVENTS, Map.of(), request, EVENTS_TYPE);
    }

    /**
     * List public events for a network of repositories ({@code GET /networks/{owner}/{repo}/events}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @return first HTTP page of events
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubEvent> listPublicEventsForRepoNetwork(final String owner, final String repo)
            throws IOException {
        return listPublicEventsForRepoNetwork(owner, repo, GitHubPageRequest.byPage());
    }

    /**
     * List public events for a network of repositories ({@code GET /networks/{owner}/{repo}/events}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param request pagination token
     * @return HTTP page of events
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank, or {@code request} is invalid
     * @throws NullPointerException if {@code request} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubEvent> listPublicEventsForRepoNetwork(
            final String owner, final String repo, final GitHubPageRequest request) throws IOException {
        requireNonNull(request, PARAM_REQUEST);

        return pageList(NETWORK_EVENTS, Map.of(), request, EVENTS_TYPE, owner, repo);
    }

    /**
     * List public organization events ({@code GET /orgs/{org}/events}).
     *
     * @param org organization login
     * @return first HTTP page of events
     * @throws IllegalArgumentException if {@code org} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubEvent> listPublicOrgEvents(final String org) throws IOException {
        return listPublicOrgEvents(org, GitHubPageRequest.byPage());
    }

    /**
     * List public organization events ({@code GET /orgs/{org}/events}).
     *
     * @param org organization login
     * @param request pagination token
     * @return HTTP page of events
     * @throws IllegalArgumentException if {@code org} is blank, or {@code request} is invalid
     * @throws NullPointerException if {@code request} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubEvent> listPublicOrgEvents(final String org, final GitHubPageRequest request)
            throws IOException {
        requireNonNull(request, PARAM_REQUEST);

        return pageList(ORG_EVENTS, Map.of(), request, EVENTS_TYPE, org);
    }

    /**
     * List repository events ({@code GET /repos/{owner}/{repo}/events}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @return first HTTP page of events
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubEvent> listRepoEvents(final String owner, final String repo) throws IOException {
        return listRepoEvents(owner, repo, GitHubPageRequest.byPage());
    }

    /**
     * List repository events ({@code GET /repos/{owner}/{repo}/events}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param request pagination token
     * @return HTTP page of events
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank, or {@code request} is invalid
     * @throws NullPointerException if {@code request} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubEvent> listRepoEvents(
            final String owner, final String repo, final GitHubPageRequest request) throws IOException {
        requireNonNull(request, PARAM_REQUEST);

        return pageList(REPO_EVENTS, Map.of(), request, EVENTS_TYPE, owner, repo);
    }

    /**
     * List events of the user {@code username} ({@code GET /users/{username}/events}). The spec names the operation
     * "for the authenticated user": private events appear only when the caller is authenticated as that user.
     *
     * @param username user login
     * @return first HTTP page of events
     * @throws IllegalArgumentException if {@code username} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubEvent> listEventsForAuthenticatedUser(final String username) throws IOException {
        return listEventsForAuthenticatedUser(username, GitHubPageRequest.byPage());
    }

    /**
     * List events of the user {@code username} ({@code GET /users/{username}/events}). The spec names the operation
     * "for the authenticated user": private events appear only when the caller is authenticated as that user.
     *
     * @param username user login
     * @param request pagination token
     * @return HTTP page of events
     * @throws IllegalArgumentException if {@code username} is blank, or {@code request} is invalid
     * @throws NullPointerException if {@code request} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubEvent> listEventsForAuthenticatedUser(
            final String username, final GitHubPageRequest request) throws IOException {
        requireNonNull(request, PARAM_REQUEST);

        return pageList(USERS_EVENTS, Map.of(), request, EVENTS_TYPE, username);
    }

    /**
     * List organization events of the user {@code username} ({@code GET /users/{username}/events/orgs/{org}}).
     *
     * @param username user login
     * @param org organization login
     * @return first HTTP page of events
     * @throws IllegalArgumentException if {@code username} or {@code org} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubEvent> listOrgEventsForAuthenticatedUser(final String username, final String org)
            throws IOException {
        return listOrgEventsForAuthenticatedUser(username, org, GitHubPageRequest.byPage());
    }

    /**
     * List organization events of the user {@code username} ({@code GET /users/{username}/events/orgs/{org}}).
     *
     * @param username user login
     * @param org organization login
     * @param request pagination token
     * @return HTTP page of events
     * @throws IllegalArgumentException if {@code username} or {@code org} is blank, or {@code request} is invalid
     * @throws NullPointerException if {@code request} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubEvent> listOrgEventsForAuthenticatedUser(
            final String username, final String org, final GitHubPageRequest request) throws IOException {
        requireNonNull(request, PARAM_REQUEST);

        return pageList(USERS_ORG_EVENTS, Map.of(), request, EVENTS_TYPE, username, org);
    }

    /**
     * List public events for a user ({@code GET /users/{username}/events/public}).
     *
     * @param username user login
     * @return first HTTP page of events
     * @throws IllegalArgumentException if {@code username} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubEvent> listPublicEventsForUser(final String username) throws IOException {
        return listPublicEventsForUser(username, GitHubPageRequest.byPage());
    }

    /**
     * List public events for a user ({@code GET /users/{username}/events/public}).
     *
     * @param username user login
     * @param request pagination token
     * @return HTTP page of events
     * @throws IllegalArgumentException if {@code username} is blank, or {@code request} is invalid
     * @throws NullPointerException if {@code request} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubEvent> listPublicEventsForUser(final String username, final GitHubPageRequest request)
            throws IOException {
        requireNonNull(request, PARAM_REQUEST);

        return pageList(USERS_PUBLIC_EVENTS, Map.of(), request, EVENTS_TYPE, username);
    }

    /**
     * List events received by a user ({@code GET /users/{username}/received_events}).
     *
     * @param username user login
     * @return first HTTP page of events
     * @throws IllegalArgumentException if {@code username} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubEvent> listReceivedEventsForUser(final String username) throws IOException {
        return listReceivedEventsForUser(username, GitHubPageRequest.byPage());
    }

    /**
     * List events received by a user ({@code GET /users/{username}/received_events}).
     *
     * @param username user login
     * @param request pagination token
     * @return HTTP page of events
     * @throws IllegalArgumentException if {@code username} is blank, or {@code request} is invalid
     * @throws NullPointerException if {@code request} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubEvent> listReceivedEventsForUser(final String username, final GitHubPageRequest request)
            throws IOException {
        requireNonNull(request, PARAM_REQUEST);

        return pageList(USERS_RECEIVED_EVENTS, Map.of(), request, EVENTS_TYPE, username);
    }

    /**
     * List public events received by a user ({@code GET /users/{username}/received_events/public}).
     *
     * @param username user login
     * @return first HTTP page of events
     * @throws IllegalArgumentException if {@code username} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubEvent> listReceivedPublicEventsForUser(final String username) throws IOException {
        return listReceivedPublicEventsForUser(username, GitHubPageRequest.byPage());
    }

    /**
     * List public events received by a user ({@code GET /users/{username}/received_events/public}).
     *
     * @param username user login
     * @param request pagination token
     * @return HTTP page of events
     * @throws IllegalArgumentException if {@code username} is blank, or {@code request} is invalid
     * @throws NullPointerException if {@code request} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubEvent> listReceivedPublicEventsForUser(
            final String username, final GitHubPageRequest request) throws IOException {
        requireNonNull(request, PARAM_REQUEST);

        return pageList(USERS_RECEIVED_PUBLIC_EVENTS, Map.of(), request, EVENTS_TYPE, username);
    }
}
