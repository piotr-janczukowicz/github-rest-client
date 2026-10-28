package io.github.piotr_janczukowicz.github.api.gitdatabase;

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
import java.util.Optional;

/** GitHub Git database refs REST resource for API version {@code 2026-03-10}. */
public final class GitHubGitRefsApi extends GitHubResourceApi {

    private static final PathTemplate MATCHING_REFS = PathTemplate.of("/repos/{owner}/{repo}/git/matching-refs/{+ref}");
    private static final PathTemplate REF_LOOKUP = PathTemplate.of("/repos/{owner}/{repo}/git/ref/{+ref}");
    private static final PathTemplate REFS = PathTemplate.of("/repos/{owner}/{repo}/git/refs");
    private static final PathTemplate REF = PathTemplate.of("/repos/{owner}/{repo}/git/refs/{+ref}");

    private static final JsonType<List<GitHubGitRef>> GIT_REFS_TYPE = JsonTypes.listOf(GitHubGitRef.class);

    /**
     * Refs resource over {@code caller}.
     *
     * @param caller executes and decodes the calls of this resource
     * @throws NullPointerException if {@code caller} is {@code null}
     */
    public GitHubGitRefsApi(final GitHubCaller caller) {
        super(caller);
    }

    /**
     * List matching references ({@code GET /repos/{owner}/{repo}/git/matching-refs/{ref}}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param ref reference prefix, such as {@code heads/feature}
     * @return HTTP page of matching references
     * @throws IllegalArgumentException if any argument is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubGitRef> listMatchingRefs(final String owner, final String repo, final String ref)
            throws IOException {
        return pageList(MATCHING_REFS, Map.of(), GitHubPageRequest.none(), GIT_REFS_TYPE, owner, repo, ref);
    }

    /**
     * Get a reference ({@code GET /repos/{owner}/{repo}/git/ref/{ref}}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param ref reference, such as {@code heads/feature-a}
     * @return reference, or empty on HTTP 404
     * @throws IllegalArgumentException if any argument is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful (HTTP 404 yields empty)
     */
    public Optional<GitHubGitRef> getRef(final String owner, final String repo, final String ref) throws IOException {

        return caller.fetchOrEmpty(get(REF_LOOKUP, owner, repo, ref), GitHubGitRef.class);
    }

    /**
     * Create a reference ({@code POST /repos/{owner}/{repo}/git/refs}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param payload reference to create
     * @return created reference
     * @throws NullPointerException if {@code payload} is {@code null}
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubGitRef createRef(final String owner, final String repo, final GitHubCreateRefPayload payload)
            throws IOException {
        requireNonNull(payload, PARAM_PAYLOAD);

        return caller.fetch(caller.json(post(REFS, owner, repo), payload), GitHubGitRef.class);
    }

    /**
     * Update a reference ({@code PATCH /repos/{owner}/{repo}/git/refs/{ref}}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param ref reference, such as {@code heads/feature-a}
     * @param payload reference update
     * @return updated reference
     * @throws NullPointerException if {@code payload} is {@code null}
     * @throws IllegalArgumentException if any argument is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubGitRef updateRef(
            final String owner, final String repo, final String ref, final GitHubUpdateRefPayload payload)
            throws IOException {
        requireNonNull(payload, PARAM_PAYLOAD);

        return caller.fetch(caller.json(patch(REF, owner, repo, ref), payload), GitHubGitRef.class);
    }

    /**
     * Delete a reference ({@code DELETE /repos/{owner}/{repo}/git/refs/{ref}}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param ref reference, such as {@code heads/feature-a}
     * @throws IllegalArgumentException if any argument is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public void deleteRef(final String owner, final String repo, final String ref) throws IOException {
        caller.send(delete(REF, owner, repo, ref));
    }
}
