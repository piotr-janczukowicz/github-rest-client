package io.github.piotr_janczukowicz.github.api.dependencygraph;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import io.github.piotr_janczukowicz.github.client.GitHubCaller;
import io.github.piotr_janczukowicz.github.client.GitHubResourceApi;
import io.github.piotr_janczukowicz.github.client.exception.GitHubApiException;
import io.github.piotr_janczukowicz.uri.PathTemplate;
import java.io.IOException;

/** GitHub dependency submission REST resource for API version {@code 2026-03-10}. */
public final class GitHubDependencyGraphDependencySubmissionApi extends GitHubResourceApi {

    private static final PathTemplate SNAPSHOTS = PathTemplate.of("/repos/{owner}/{repo}/dependency-graph/snapshots");

    /**
     * Dependency submission resource over {@code caller}.
     *
     * @param caller executes and decodes the calls of this resource
     * @throws NullPointerException if {@code caller} is {@code null}
     */
    public GitHubDependencyGraphDependencySubmissionApi(final GitHubCaller caller) {
        super(caller);
    }

    /**
     * Create a snapshot of dependencies for a repository ({@code POST
     * /repos/{owner}/{repo}/dependency-graph/snapshots}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param payload snapshot to submit
     * @return created snapshot
     * @throws NullPointerException if {@code payload} is {@code null}
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubCreateRepositorySnapshotResponse createRepositorySnapshot(
            final String owner, final String repo, final GitHubCreateRepositorySnapshotPayload payload)
            throws IOException {
        requireNonNull(payload, PARAM_PAYLOAD);

        return caller.fetch(
                caller.json(post(SNAPSHOTS, owner, repo), payload), GitHubCreateRepositorySnapshotResponse.class);
    }
}
