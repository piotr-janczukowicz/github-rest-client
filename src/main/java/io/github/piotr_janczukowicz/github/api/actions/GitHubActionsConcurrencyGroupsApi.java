package io.github.piotr_janczukowicz.github.api.actions;

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
import io.github.piotr_janczukowicz.uri.PathTemplate;
import java.io.IOException;
import java.util.Map;
import java.util.Optional;

/** GitHub Actions concurrency groups REST resource for API version {@code 2026-03-10}. */
public final class GitHubActionsConcurrencyGroupsApi extends GitHubResourceApi {

    private static final PathTemplate REPO_CONCURRENCY_GROUPS =
            PathTemplate.of("/repos/{owner}/{repo}/actions/concurrency_groups");
    private static final PathTemplate REPO_CONCURRENCY_GROUP =
            PathTemplate.of("/repos/{owner}/{repo}/actions/concurrency_groups/{concurrency_group_name}");
    private static final PathTemplate REPO_RUN_CONCURRENCY_GROUPS =
            PathTemplate.of("/repos/{owner}/{repo}/actions/runs/{run_id}/concurrency_groups");

    private static final GitHubListSpec<GitHubConcurrencyGroupListEntry> REPO_GROUPS_SPEC = GitHubListSpec.wrapper(
            GitHubPageRequestKindValidators.CURSOR,
            GitHubConcurrencyGroupList.class,
            GitHubConcurrencyGroupList::concurrencyGroups,
            GitHubConcurrencyGroupList::totalCount);
    private static final GitHubListSpec<GitHubConcurrencyGroupRunListEntry> RUN_GROUPS_SPEC = GitHubListSpec.wrapper(
            GitHubPageRequestKindValidators.CURSOR,
            GitHubConcurrencyGroupRunList.class,
            GitHubConcurrencyGroupRunList::concurrencyGroups,
            GitHubConcurrencyGroupRunList::totalCount);

    /**
     * Concurrency groups resource over {@code caller}.
     *
     * @param caller executes and decodes the calls of this resource
     * @throws NullPointerException if {@code caller} is {@code null}
     */
    public GitHubActionsConcurrencyGroupsApi(final GitHubCaller caller) {
        super(caller);
    }

    /**
     * List concurrency groups of a repository ({@code GET /repos/{owner}/{repo}/actions/concurrency_groups}).
     *
     * <p>Uses {@link GitHubPageRequest#byCursor()}.
     *
     * @param owner repository owner
     * @param repo repository name
     * @return first HTTP page of concurrency groups
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public GitHubPage<GitHubConcurrencyGroupListEntry> listConcurrencyGroupsForRepository(
            final String owner, final String repo) throws IOException {
        return listConcurrencyGroupsForRepository(owner, repo, GitHubPageRequest.byCursor());
    }

    /**
     * List concurrency groups of a repository ({@code GET /repos/{owner}/{repo}/actions/concurrency_groups}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param request cursor token
     * @return first HTTP page of concurrency groups
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank, or {@code request} is invalid
     * @throws NullPointerException if {@code request} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public GitHubPage<GitHubConcurrencyGroupListEntry> listConcurrencyGroupsForRepository(
            final String owner, final String repo, final GitHubPageRequest request) throws IOException {
        requireFlavor(GitHubFlavor.CLOUD, GitHubFlavor.GHEC);
        requireNonNull(request, PARAM_REQUEST);

        return list(REPO_CONCURRENCY_GROUPS, Map.of(), request, REPO_GROUPS_SPEC, owner, repo);
    }

    /**
     * Get a concurrency group of a repository ({@code GET
     * /repos/{owner}/{repo}/actions/concurrency_groups/{concurrency_group_name}}).
     *
     * <p>Uses an empty filter.
     *
     * @param owner repository owner
     * @param repo repository name
     * @param concurrencyGroupName concurrency group name
     * @return concurrency group, or empty on HTTP 404
     * @throws IllegalArgumentException if any argument is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful (HTTP 404 yields empty)
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public Optional<GitHubConcurrencyGroup> getConcurrencyGroupForRepository(
            final String owner, final String repo, final String concurrencyGroupName) throws IOException {
        return getConcurrencyGroupForRepository(
                owner, repo, concurrencyGroupName, GitHubGetConcurrencyGroupForRepositoryFilter.empty());
    }

    /**
     * Get a concurrency group of a repository ({@code GET
     * /repos/{owner}/{repo}/actions/concurrency_groups/{concurrency_group_name}}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param concurrencyGroupName concurrency group name
     * @param filter query filters
     * @return concurrency group, or empty on HTTP 404
     * @throws IllegalArgumentException if any argument is blank
     * @throws NullPointerException if {@code filter} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful (HTTP 404 yields empty)
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public Optional<GitHubConcurrencyGroup> getConcurrencyGroupForRepository(
            final String owner,
            final String repo,
            final String concurrencyGroupName,
            final GitHubGetConcurrencyGroupForRepositoryFilter filter)
            throws IOException {
        requireFlavor(GitHubFlavor.CLOUD, GitHubFlavor.GHEC);
        requireNonNull(filter, PARAM_FILTER);

        return caller.fetchOrEmpty(
                get(REPO_CONCURRENCY_GROUP, owner, repo, concurrencyGroupName).withQuery(filter.toQueryParameters()),
                GitHubConcurrencyGroup.class);
    }

    /**
     * List concurrency groups of a workflow run ({@code GET
     * /repos/{owner}/{repo}/actions/runs/{run_id}/concurrency_groups}).
     *
     * <p>Uses {@link GitHubPageRequest#byCursor()}.
     *
     * @param owner repository owner
     * @param repo repository name
     * @param runId workflow run ID
     * @return first HTTP page of concurrency groups of the run
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public GitHubPage<GitHubConcurrencyGroupRunListEntry> listConcurrencyGroupsForWorkflowRun(
            final String owner, final String repo, final long runId) throws IOException {
        return listConcurrencyGroupsForWorkflowRun(owner, repo, runId, GitHubPageRequest.byCursor());
    }

    /**
     * List concurrency groups of a workflow run ({@code GET
     * /repos/{owner}/{repo}/actions/runs/{run_id}/concurrency_groups}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param runId workflow run ID
     * @param request cursor token
     * @return first HTTP page of concurrency groups of the run
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank, or {@code request} is invalid
     * @throws NullPointerException if {@code request} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public GitHubPage<GitHubConcurrencyGroupRunListEntry> listConcurrencyGroupsForWorkflowRun(
            final String owner, final String repo, final long runId, final GitHubPageRequest request)
            throws IOException {
        requireFlavor(GitHubFlavor.CLOUD, GitHubFlavor.GHEC);
        requireNonNull(request, PARAM_REQUEST);

        return list(REPO_RUN_CONCURRENCY_GROUPS, Map.of(), request, RUN_GROUPS_SPEC, owner, repo, runId);
    }
}
