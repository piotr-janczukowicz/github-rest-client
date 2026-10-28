package io.github.piotr_janczukowicz.github.api.gitdatabase;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import io.github.piotr_janczukowicz.github.client.GitHubCaller;
import io.github.piotr_janczukowicz.github.client.GitHubResourceApi;
import io.github.piotr_janczukowicz.github.client.exception.GitHubApiException;
import io.github.piotr_janczukowicz.uri.PathTemplate;
import java.io.IOException;
import java.util.Optional;

/** GitHub Git database tags REST resource for API version {@code 2026-03-10}. */
public final class GitHubGitTagsApi extends GitHubResourceApi {

    private static final PathTemplate TAGS = PathTemplate.of("/repos/{owner}/{repo}/git/tags");
    private static final PathTemplate TAG = PathTemplate.of("/repos/{owner}/{repo}/git/tags/{tag_sha}");

    /**
     * Tags resource over {@code caller}.
     *
     * @param caller executes and decodes the calls of this resource
     * @throws NullPointerException if {@code caller} is {@code null}
     */
    public GitHubGitTagsApi(final GitHubCaller caller) {
        super(caller);
    }

    /**
     * Create a tag object ({@code POST /repos/{owner}/{repo}/git/tags}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param payload tag object to create
     * @return created tag object
     * @throws NullPointerException if {@code payload} is {@code null}
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubGitTag createTag(final String owner, final String repo, final GitHubCreateTagPayload payload)
            throws IOException {
        requireNonNull(payload, PARAM_PAYLOAD);

        return caller.fetch(caller.json(post(TAGS, owner, repo), payload), GitHubGitTag.class);
    }

    /**
     * Get a tag ({@code GET /repos/{owner}/{repo}/git/tags/{tag_sha}}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param tagSha tag object SHA
     * @return tag object, or empty on HTTP 404
     * @throws IllegalArgumentException if any argument is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful (HTTP 404 yields empty)
     */
    public Optional<GitHubGitTag> getTag(final String owner, final String repo, final String tagSha)
            throws IOException {

        return caller.fetchOrEmpty(get(TAG, owner, repo, tagSha), GitHubGitTag.class);
    }
}
