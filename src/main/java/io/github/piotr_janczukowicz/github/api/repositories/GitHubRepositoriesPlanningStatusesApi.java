package io.github.piotr_janczukowicz.github.api.repositories;

import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubCaller;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.github.client.GitHubResourceApi;
import io.github.piotr_janczukowicz.github.client.exception.GitHubApiException;
import io.github.piotr_janczukowicz.json.JsonType;
import io.github.piotr_janczukowicz.json.JsonTypes;
import io.github.piotr_janczukowicz.uri.PathTemplate;
import java.io.IOException;
import java.util.List;

/** GitHub repository planning statuses REST resource for API version {@code 2026-03-10}. */
public final class GitHubRepositoriesPlanningStatusesApi extends GitHubResourceApi {

    private static final PathTemplate PLANNING_STATUSES = PathTemplate.of("/repos/{owner}/{repo}/planning-statuses");

    private static final JsonType<List<GitHubPlanningStatus>> PLANNING_STATUSES_TYPE =
            JsonTypes.listOf(GitHubPlanningStatus.class);

    /**
     * Planning statuses resource over {@code caller}.
     *
     * @param caller executes and decodes the calls of this resource
     * @throws NullPointerException if {@code caller} is {@code null}
     */
    public GitHubRepositoriesPlanningStatusesApi(final GitHubCaller caller) {
        super(caller);
    }

    /**
     * List planning statuses for a repository ({@code GET /repos/{owner}/{repo}/planning-statuses}).
     *
     * <p>GitHub answers HTTP 404 when issue statuses are not available in the repository.
     *
     * @param owner repository owner
     * @param repo repository name
     * @return planning statuses in display order
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public List<GitHubPlanningStatus> listPlanningStatuses(final String owner, final String repo) throws IOException {
        requireFlavor(GitHubFlavor.CLOUD, GitHubFlavor.GHEC);
        return caller.fetch(get(PLANNING_STATUSES, owner, repo), PLANNING_STATUSES_TYPE);
    }
}
