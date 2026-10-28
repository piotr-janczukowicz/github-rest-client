package io.github.piotr_janczukowicz.github.api.codespaces;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubCaller;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.github.client.GitHubResourceApi;
import io.github.piotr_janczukowicz.github.client.exception.GitHubApiException;
import io.github.piotr_janczukowicz.uri.PathTemplate;
import java.io.IOException;

/** GitHub Codespaces machines REST resource for API version {@code 2026-03-10}. */
public final class GitHubCodespacesMachinesApi extends GitHubResourceApi {

    private static final PathTemplate REPO_CODESPACES_MACHINES =
            PathTemplate.of("/repos/{owner}/{repo}/codespaces/machines");
    private static final PathTemplate USER_CODESPACE_MACHINES =
            PathTemplate.of("/user/codespaces/{codespace_name}/machines");

    /**
     * Codespaces machines resource over {@code caller}.
     *
     * @param caller executes and decodes the calls of this resource
     * @throws NullPointerException if {@code caller} is {@code null}
     */
    public GitHubCodespacesMachinesApi(final GitHubCaller caller) {
        super(caller);
    }

    /**
     * List available machine types for a repository ({@code GET /repos/{owner}/{repo}/codespaces/machines}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @return machine types with their total count
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public GitHubCodespaceMachinesPage listRepoMachines(final String owner, final String repo) throws IOException {
        return listRepoMachines(owner, repo, GitHubRepoMachinesForAuthenticatedUserFilter.empty());
    }

    /**
     * List available machine types for a repository ({@code GET /repos/{owner}/{repo}/codespaces/machines}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param filter query filters ({@code location}, {@code client_ip}, {@code ref})
     * @return machine types with their total count
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws NullPointerException if {@code filter} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public GitHubCodespaceMachinesPage listRepoMachines(
            final String owner, final String repo, final GitHubRepoMachinesForAuthenticatedUserFilter filter)
            throws IOException {
        requireFlavor(GitHubFlavor.CLOUD, GitHubFlavor.GHEC);
        requireNonNull(filter, PARAM_FILTER);

        return caller.fetch(
                get(REPO_CODESPACES_MACHINES, owner, repo).withQuery(filter.toQueryParameters()),
                GitHubCodespaceMachinesPage.class);
    }

    /**
     * List machine types for a codespace ({@code GET /user/codespaces/{codespace_name}/machines}).
     *
     * @param codespaceName codespace name
     * @return machine types with their total count
     * @throws IllegalArgumentException if {@code codespaceName} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public GitHubCodespaceMachinesPage listCodespaceMachines(final String codespaceName) throws IOException {
        requireFlavor(GitHubFlavor.CLOUD, GitHubFlavor.GHEC);
        return caller.fetch(get(USER_CODESPACE_MACHINES, codespaceName), GitHubCodespaceMachinesPage.class);
    }
}
