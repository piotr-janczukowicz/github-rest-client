package io.github.piotr_janczukowicz.github.api.gitdatabase;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import io.github.piotr_janczukowicz.github.client.GitHubCaller;
import io.github.piotr_janczukowicz.github.client.GitHubResourceApi;
import io.github.piotr_janczukowicz.github.client.exception.GitHubApiException;
import io.github.piotr_janczukowicz.uri.PathTemplate;
import java.io.IOException;
import java.util.Optional;

/** GitHub Git database commits REST resource for API version {@code 2026-03-10}. */
public final class GitHubGitCommitsApi extends GitHubResourceApi {

    private static final PathTemplate COMMITS = PathTemplate.of("/repos/{owner}/{repo}/git/commits");
    private static final PathTemplate COMMIT = PathTemplate.of("/repos/{owner}/{repo}/git/commits/{+commit_sha}");

    /**
     * Commits resource over {@code caller}.
     *
     * @param caller executes and decodes the calls of this resource
     * @throws NullPointerException if {@code caller} is {@code null}
     */
    public GitHubGitCommitsApi(final GitHubCaller caller) {
        super(caller);
    }

    /**
     * Create a commit ({@code POST /repos/{owner}/{repo}/git/commits}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param payload commit to create
     * @return created commit
     * @throws NullPointerException if {@code payload} is {@code null}
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubGitCommit createCommit(final String owner, final String repo, final GitHubCreateCommitPayload payload)
            throws IOException {
        requireNonNull(payload, PARAM_PAYLOAD);

        return caller.fetch(caller.json(post(COMMITS, owner, repo), payload), GitHubGitCommit.class);
    }

    /**
     * Get a commit object ({@code GET /repos/{owner}/{repo}/git/commits/{commit_sha}}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param commitSha commit SHA
     * @return commit, or empty on HTTP 404
     * @throws IllegalArgumentException if any argument is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful (HTTP 404 yields empty)
     */
    public Optional<GitHubGitCommit> getCommit(final String owner, final String repo, final String commitSha)
            throws IOException {

        return caller.fetchOrEmpty(get(COMMIT, owner, repo, commitSha), GitHubGitCommit.class);
    }
}
