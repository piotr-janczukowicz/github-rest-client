package io.github.piotr_janczukowicz.github.api.repositories;

import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.api.organizations.GitHubIssueType;
import io.github.piotr_janczukowicz.github.client.GitHubCaller;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.github.client.GitHubResourceApi;
import io.github.piotr_janczukowicz.github.client.exception.GitHubApiException;
import io.github.piotr_janczukowicz.json.JsonType;
import io.github.piotr_janczukowicz.json.JsonTypes;
import io.github.piotr_janczukowicz.uri.PathTemplate;
import java.io.IOException;
import java.util.List;

/** GitHub repository issue types REST resource for API version {@code 2026-03-10}. */
public final class GitHubRepositoriesIssueTypesApi extends GitHubResourceApi {

    private static final PathTemplate ISSUE_TYPES = PathTemplate.of("/repos/{owner}/{repo}/issue-types");

    private static final JsonType<List<GitHubIssueType>> ISSUE_TYPES_TYPE = JsonTypes.listOf(GitHubIssueType.class);

    /**
     * Issue types resource over {@code caller}.
     *
     * @param caller executes and decodes the calls of this resource
     * @throws NullPointerException if {@code caller} is {@code null}
     */
    public GitHubRepositoriesIssueTypesApi(final GitHubCaller caller) {
        super(caller);
    }

    /**
     * List issue types ({@code GET /repos/{owner}/{repo}/issue-types}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @return issue types
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public List<GitHubIssueType> listIssueTypes(final String owner, final String repo) throws IOException {
        requireFlavor(GitHubFlavor.CLOUD, GitHubFlavor.GHEC);
        return caller.fetch(get(ISSUE_TYPES, owner, repo), ISSUE_TYPES_TYPE);
    }
}
