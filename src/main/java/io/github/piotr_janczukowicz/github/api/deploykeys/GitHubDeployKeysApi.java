package io.github.piotr_janczukowicz.github.api.deploykeys;

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

/** GitHub repository deploy keys REST resource for API version {@code 2026-03-10}. */
public final class GitHubDeployKeysApi extends GitHubResourceApi {

    private static final PathTemplate KEY = PathTemplate.of("/repos/{owner}/{repo}/keys/{key_id}");
    private static final PathTemplate KEYS = PathTemplate.of("/repos/{owner}/{repo}/keys");

    private static final JsonType<List<GitHubDeployKey>> DEPLOY_KEYS_TYPE = JsonTypes.listOf(GitHubDeployKey.class);

    /**
     * Deploy keys resource over {@code caller}.
     *
     * @param caller executes and decodes the calls of this resource
     * @throws NullPointerException if {@code caller} is {@code null}
     */
    public GitHubDeployKeysApi(final GitHubCaller caller) {
        super(caller);
    }

    /**
     * List deploy keys ({@code GET /repos/{owner}/{repo}/keys}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @return first HTTP page of deploy keys
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubDeployKey> listDeployKeys(final String owner, final String repo) throws IOException {
        return listDeployKeys(owner, repo, GitHubPageRequest.byPage());
    }

    /**
     * List deploy keys ({@code GET /repos/{owner}/{repo}/keys}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param request pagination token
     * @return first HTTP page of deploy keys
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank, or {@code request} is invalid
     * @throws NullPointerException if {@code request} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubDeployKey> listDeployKeys(
            final String owner, final String repo, final GitHubPageRequest request) throws IOException {
        requireNonNull(request, PARAM_REQUEST);

        return pageList(KEYS, Map.of(), request, DEPLOY_KEYS_TYPE, owner, repo);
    }

    /**
     * Create a deploy key ({@code POST /repos/{owner}/{repo}/keys}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param payload deploy key to create
     * @return created deploy key
     * @throws NullPointerException if {@code payload} is {@code null}
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubDeployKey createDeployKey(
            final String owner, final String repo, final GitHubCreateDeployKeyPayload payload) throws IOException {
        requireNonNull(payload, PARAM_PAYLOAD);

        return caller.fetch(caller.json(post(KEYS, owner, repo), payload), GitHubDeployKey.class);
    }

    /**
     * Get a deploy key ({@code GET /repos/{owner}/{repo}/keys/{key_id}}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param keyId deploy key id
     * @return deploy key, or empty on HTTP 404
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful (HTTP 404 yields empty)
     */
    public Optional<GitHubDeployKey> getDeployKey(final String owner, final String repo, final long keyId)
            throws IOException {

        return caller.fetchOrEmpty(get(KEY, owner, repo, keyId), GitHubDeployKey.class);
    }

    /**
     * Delete a deploy key ({@code DELETE /repos/{owner}/{repo}/keys/{key_id}}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param keyId deploy key id
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public void deleteDeployKey(final String owner, final String repo, final long keyId) throws IOException {
        caller.send(delete(KEY, owner, repo, keyId));
    }
}
