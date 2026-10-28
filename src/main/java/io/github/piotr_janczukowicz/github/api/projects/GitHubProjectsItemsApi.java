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

/** GitHub Projects item REST resource for API version {@code 2026-03-10}. */
public final class GitHubProjectsItemsApi extends GitHubResourceApi {

    private static final PathTemplate ORG_PROJECT_ITEMS =
            PathTemplate.of("/orgs/{org}/projectsV2/{project_number}/items");
    private static final PathTemplate ORG_PROJECT_ITEM =
            PathTemplate.of("/orgs/{org}/projectsV2/{project_number}/items/{item_id}");
    private static final PathTemplate ORG_PROJECT_VIEW_ITEMS =
            PathTemplate.of("/orgs/{org}/projectsV2/{project_number}/views/{view_number}/items");
    private static final PathTemplate USERS_PROJECT_ITEMS =
            PathTemplate.of("/users/{username}/projectsV2/{project_number}/items");
    private static final PathTemplate USERS_PROJECT_ITEM =
            PathTemplate.of("/users/{username}/projectsV2/{project_number}/items/{item_id}");
    private static final PathTemplate USERS_PROJECT_VIEW_ITEMS =
            PathTemplate.of("/users/{username}/projectsV2/{project_number}/views/{view_number}/items");

    private static final JsonType<List<GitHubProjectItem>> ITEMS_TYPE = JsonTypes.listOf(GitHubProjectItem.class);

    /**
     * Project item resource over {@code caller}.
     *
     * @param caller executes and decodes the calls of this resource
     * @throws NullPointerException if {@code caller} is {@code null}
     */
    public GitHubProjectsItemsApi(final GitHubCaller caller) {
        super(caller);
    }

    /**
     * List items in an organization project ({@code GET /orgs/{org}/projectsV2/{project_number}/items}).
     *
     * @param org organization login
     * @param projectNumber project number
     * @return first HTTP page of project items
     * @throws IllegalArgumentException if {@code org} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubProjectItem> listForOrg(final String org, final long projectNumber) throws IOException {
        return listForOrg(org, projectNumber, GitHubListProjectItemsFilter.empty(), GitHubPageRequest.byCursor());
    }

    /**
     * List items in an organization project ({@code GET /orgs/{org}/projectsV2/{project_number}/items}).
     *
     * @param org organization login
     * @param projectNumber project number
     * @param request pagination token
     * @return first HTTP page of project items
     * @throws IllegalArgumentException if {@code org} is blank, or {@code request} is a page or since variant
     * @throws NullPointerException if {@code request} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubProjectItem> listForOrg(
            final String org, final long projectNumber, final GitHubPageRequest request) throws IOException {
        return listForOrg(org, projectNumber, GitHubListProjectItemsFilter.empty(), request);
    }

    /**
     * List items in an organization project ({@code GET /orgs/{org}/projectsV2/{project_number}/items}).
     *
     * @param org organization login
     * @param projectNumber project number
     * @param filter query filters
     * @param request pagination token
     * @return first HTTP page of project items
     * @throws IllegalArgumentException if {@code org} is blank, or {@code request} is a page or since variant
     * @throws NullPointerException if {@code filter} or {@code request} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubProjectItem> listForOrg(
            final String org,
            final long projectNumber,
            final GitHubListProjectItemsFilter filter,
            final GitHubPageRequest request)
            throws IOException {
        requireNonNull(filter, PARAM_FILTER);
        requireNonNull(request, PARAM_REQUEST);

        return list(
                ORG_PROJECT_ITEMS,
                filter.toQueryParameters(),
                request,
                GitHubListSpec.array(GitHubPageRequestKindValidators.CURSOR, ITEMS_TYPE),
                org,
                projectNumber);
    }

    /**
     * Add an item to an organization project ({@code POST /orgs/{org}/projectsV2/{project_number}/items}).
     *
     * @param org organization login
     * @param projectNumber project number
     * @param payload item to add
     * @return created project item
     * @throws IllegalArgumentException if {@code org} is blank
     * @throws NullPointerException if {@code payload} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubProjectItemSimple addForOrg(
            final String org, final long projectNumber, final GitHubAddProjectItemPayload payload) throws IOException {
        requireNonNull(payload, PARAM_PAYLOAD);

        return caller.fetch(
                caller.json(post(ORG_PROJECT_ITEMS, org, projectNumber), payload), GitHubProjectItemSimple.class);
    }

    /**
     * Get an item from an organization project ({@code GET /orgs/{org}/projectsV2/{project_number}/items/{item_id}}).
     *
     * @param org organization login
     * @param projectNumber project number
     * @param itemId item id
     * @return project item, or empty on HTTP 404
     * @throws IllegalArgumentException if {@code org} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful (HTTP 404 yields empty)
     */
    public Optional<GitHubProjectItem> getForOrg(final String org, final long projectNumber, final long itemId)
            throws IOException {
        return getForOrg(org, projectNumber, itemId, GitHubListProjectViewItemsFilter.empty());
    }

    /**
     * Get an item from an organization project ({@code GET /orgs/{org}/projectsV2/{project_number}/items/{item_id}}).
     *
     * @param org organization login
     * @param projectNumber project number
     * @param itemId item id
     * @param filter field ids to include (`fields` query only)
     * @return project item, or empty on HTTP 404
     * @throws IllegalArgumentException if {@code org} is blank
     * @throws NullPointerException if {@code filter} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful (HTTP 404 yields empty)
     */
    public Optional<GitHubProjectItem> getForOrg(
            final String org,
            final long projectNumber,
            final long itemId,
            final GitHubListProjectViewItemsFilter filter)
            throws IOException {
        requireNonNull(filter, PARAM_FILTER);

        return caller.fetchOrEmpty(
                get(ORG_PROJECT_ITEM, org, projectNumber, itemId).withQuery(filter.toQueryParameters()),
                GitHubProjectItem.class);
    }

    /**
     * Update an item in an organization project ({@code PATCH
     * /orgs/{org}/projectsV2/{project_number}/items/{item_id}}).
     *
     * @param org organization login
     * @param projectNumber project number
     * @param itemId item id
     * @param payload field updates
     * @return updated project item
     * @throws IllegalArgumentException if {@code org} is blank
     * @throws NullPointerException if {@code payload} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubProjectItem updateForOrg(
            final String org, final long projectNumber, final long itemId, final GitHubUpdateProjectItemPayload payload)
            throws IOException {
        requireNonNull(payload, PARAM_PAYLOAD);

        return caller.fetch(
                caller.json(patch(ORG_PROJECT_ITEM, org, projectNumber, itemId), payload), GitHubProjectItem.class);
    }

    /**
     * Delete an item from an organization project ({@code DELETE
     * /orgs/{org}/projectsV2/{project_number}/items/{item_id}}).
     *
     * @param org organization login
     * @param projectNumber project number
     * @param itemId item id
     * @throws IllegalArgumentException if {@code org} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public void deleteForOrg(final String org, final long projectNumber, final long itemId) throws IOException {
        caller.send(delete(ORG_PROJECT_ITEM, org, projectNumber, itemId));
    }

    /**
     * List items in an organization project view ({@code GET
     * /orgs/{org}/projectsV2/{project_number}/views/{view_number}/items}).
     *
     * @param org organization login
     * @param projectNumber project number
     * @param viewNumber view number
     * @return first HTTP page of project items
     * @throws IllegalArgumentException if {@code org} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubProjectItem> listViewItemsForOrg(
            final String org, final long projectNumber, final long viewNumber) throws IOException {
        return listViewItemsForOrg(
                org, projectNumber, viewNumber, GitHubListProjectViewItemsFilter.empty(), GitHubPageRequest.byCursor());
    }

    /**
     * List items in an organization project view ({@code GET
     * /orgs/{org}/projectsV2/{project_number}/views/{view_number}/items}).
     *
     * @param org organization login
     * @param projectNumber project number
     * @param viewNumber view number
     * @param request pagination token
     * @return first HTTP page of project items
     * @throws IllegalArgumentException if {@code org} is blank, or {@code request} is a page or since variant
     * @throws NullPointerException if {@code request} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubProjectItem> listViewItemsForOrg(
            final String org, final long projectNumber, final long viewNumber, final GitHubPageRequest request)
            throws IOException {
        return listViewItemsForOrg(org, projectNumber, viewNumber, GitHubListProjectViewItemsFilter.empty(), request);
    }

    /**
     * List items in an organization project view ({@code GET
     * /orgs/{org}/projectsV2/{project_number}/views/{view_number}/items}).
     *
     * @param org organization login
     * @param projectNumber project number
     * @param viewNumber view number
     * @param filter query filters
     * @param request pagination token
     * @return first HTTP page of project items
     * @throws IllegalArgumentException if {@code org} is blank, or {@code request} is a page or since variant
     * @throws NullPointerException if {@code filter} or {@code request} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubProjectItem> listViewItemsForOrg(
            final String org,
            final long projectNumber,
            final long viewNumber,
            final GitHubListProjectViewItemsFilter filter,
            final GitHubPageRequest request)
            throws IOException {
        requireNonNull(filter, PARAM_FILTER);
        requireNonNull(request, PARAM_REQUEST);

        return list(
                ORG_PROJECT_VIEW_ITEMS,
                filter.toQueryParameters(),
                request,
                GitHubListSpec.array(GitHubPageRequestKindValidators.CURSOR, ITEMS_TYPE),
                org,
                projectNumber,
                viewNumber);
    }

    /**
     * List items in a user project ({@code GET /users/{username}/projectsV2/{project_number}/items}).
     *
     * @param username user login
     * @param projectNumber project number
     * @return first HTTP page of project items
     * @throws IllegalArgumentException if {@code username} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubProjectItem> listForUser(final String username, final long projectNumber)
            throws IOException {
        return listForUser(username, projectNumber, GitHubListProjectItemsFilter.empty(), GitHubPageRequest.byCursor());
    }

    /**
     * List items in a user project ({@code GET /users/{username}/projectsV2/{project_number}/items}).
     *
     * @param username user login
     * @param projectNumber project number
     * @param request pagination token
     * @return first HTTP page of project items
     * @throws IllegalArgumentException if {@code username} is blank, or {@code request} is a page or since variant
     * @throws NullPointerException if {@code request} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubProjectItem> listForUser(
            final String username, final long projectNumber, final GitHubPageRequest request) throws IOException {
        return listForUser(username, projectNumber, GitHubListProjectItemsFilter.empty(), request);
    }

    /**
     * List items in a user project ({@code GET /users/{username}/projectsV2/{project_number}/items}).
     *
     * @param username user login
     * @param projectNumber project number
     * @param filter query filters
     * @param request pagination token
     * @return first HTTP page of project items
     * @throws IllegalArgumentException if {@code username} is blank, or {@code request} is a page or since variant
     * @throws NullPointerException if {@code filter} or {@code request} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubProjectItem> listForUser(
            final String username,
            final long projectNumber,
            final GitHubListProjectItemsFilter filter,
            final GitHubPageRequest request)
            throws IOException {
        requireNonNull(filter, PARAM_FILTER);
        requireNonNull(request, PARAM_REQUEST);

        return list(
                USERS_PROJECT_ITEMS,
                filter.toQueryParameters(),
                request,
                GitHubListSpec.array(GitHubPageRequestKindValidators.CURSOR, ITEMS_TYPE),
                username,
                projectNumber);
    }

    /**
     * Add an item to a user project ({@code POST /users/{username}/projectsV2/{project_number}/items}).
     *
     * @param username user login
     * @param projectNumber project number
     * @param payload item to add
     * @return created project item
     * @throws IllegalArgumentException if {@code username} is blank
     * @throws NullPointerException if {@code payload} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubProjectItemSimple addForUser(
            final String username, final long projectNumber, final GitHubAddProjectItemPayload payload)
            throws IOException {
        requireNonNull(payload, PARAM_PAYLOAD);

        return caller.fetch(
                caller.json(post(USERS_PROJECT_ITEMS, username, projectNumber), payload),
                GitHubProjectItemSimple.class);
    }

    /**
     * Get an item from a user project ({@code GET /users/{username}/projectsV2/{project_number}/items/{item_id}}).
     *
     * @param username user login
     * @param projectNumber project number
     * @param itemId item id
     * @return project item, or empty on HTTP 404
     * @throws IllegalArgumentException if {@code username} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful (HTTP 404 yields empty)
     */
    public Optional<GitHubProjectItem> getForUser(final String username, final long projectNumber, final long itemId)
            throws IOException {
        return getForUser(username, projectNumber, itemId, GitHubListProjectViewItemsFilter.empty());
    }

    /**
     * Get an item from a user project ({@code GET /users/{username}/projectsV2/{project_number}/items/{item_id}}).
     *
     * @param username user login
     * @param projectNumber project number
     * @param itemId item id
     * @param filter field ids to include (`fields` query only)
     * @return project item, or empty on HTTP 404
     * @throws IllegalArgumentException if {@code username} is blank
     * @throws NullPointerException if {@code filter} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful (HTTP 404 yields empty)
     */
    public Optional<GitHubProjectItem> getForUser(
            final String username,
            final long projectNumber,
            final long itemId,
            final GitHubListProjectViewItemsFilter filter)
            throws IOException {
        requireNonNull(filter, PARAM_FILTER);

        return caller.fetchOrEmpty(
                get(USERS_PROJECT_ITEM, username, projectNumber, itemId).withQuery(filter.toQueryParameters()),
                GitHubProjectItem.class);
    }

    /**
     * Update an item in a user project ({@code PATCH /users/{username}/projectsV2/{project_number}/items/{item_id}}).
     *
     * @param username user login
     * @param projectNumber project number
     * @param itemId item id
     * @param payload field updates
     * @return updated project item
     * @throws IllegalArgumentException if {@code username} is blank
     * @throws NullPointerException if {@code payload} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubProjectItem updateForUser(
            final String username,
            final long projectNumber,
            final long itemId,
            final GitHubUpdateProjectItemPayload payload)
            throws IOException {
        requireNonNull(payload, PARAM_PAYLOAD);

        return caller.fetch(
                caller.json(patch(USERS_PROJECT_ITEM, username, projectNumber, itemId), payload),
                GitHubProjectItem.class);
    }

    /**
     * Delete an item from a user project ({@code DELETE
     * /users/{username}/projectsV2/{project_number}/items/{item_id}}).
     *
     * @param username user login
     * @param projectNumber project number
     * @param itemId item id
     * @throws IllegalArgumentException if {@code username} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public void deleteForUser(final String username, final long projectNumber, final long itemId) throws IOException {
        caller.send(delete(USERS_PROJECT_ITEM, username, projectNumber, itemId));
    }

    /**
     * List items in a user project view ({@code GET
     * /users/{username}/projectsV2/{project_number}/views/{view_number}/items}).
     *
     * @param username user login
     * @param projectNumber project number
     * @param viewNumber view number
     * @return first HTTP page of project items
     * @throws IllegalArgumentException if {@code username} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubProjectItem> listViewItemsForUser(
            final String username, final long projectNumber, final long viewNumber) throws IOException {
        return listViewItemsForUser(
                username,
                projectNumber,
                viewNumber,
                GitHubListProjectViewItemsFilter.empty(),
                GitHubPageRequest.byCursor());
    }

    /**
     * List items in a user project view ({@code GET
     * /users/{username}/projectsV2/{project_number}/views/{view_number}/items}).
     *
     * @param username user login
     * @param projectNumber project number
     * @param viewNumber view number
     * @param request pagination token
     * @return first HTTP page of project items
     * @throws IllegalArgumentException if {@code username} is blank, or {@code request} is a page or since variant
     * @throws NullPointerException if {@code request} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubProjectItem> listViewItemsForUser(
            final String username, final long projectNumber, final long viewNumber, final GitHubPageRequest request)
            throws IOException {
        return listViewItemsForUser(
                username, projectNumber, viewNumber, GitHubListProjectViewItemsFilter.empty(), request);
    }

    /**
     * List items in a user project view ({@code GET
     * /users/{username}/projectsV2/{project_number}/views/{view_number}/items}).
     *
     * @param username user login
     * @param projectNumber project number
     * @param viewNumber view number
     * @param filter query filters
     * @param request pagination token
     * @return first HTTP page of project items
     * @throws IllegalArgumentException if {@code username} is blank, or {@code request} is a page or since variant
     * @throws NullPointerException if {@code filter} or {@code request} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubProjectItem> listViewItemsForUser(
            final String username,
            final long projectNumber,
            final long viewNumber,
            final GitHubListProjectViewItemsFilter filter,
            final GitHubPageRequest request)
            throws IOException {
        requireNonNull(filter, PARAM_FILTER);
        requireNonNull(request, PARAM_REQUEST);

        return list(
                USERS_PROJECT_VIEW_ITEMS,
                filter.toQueryParameters(),
                request,
                GitHubListSpec.array(GitHubPageRequestKindValidators.CURSOR, ITEMS_TYPE),
                username,
                projectNumber,
                viewNumber);
    }
}
