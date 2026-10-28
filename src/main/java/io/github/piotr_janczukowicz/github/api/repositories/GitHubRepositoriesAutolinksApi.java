package io.github.piotr_janczukowicz.github.api.repositories;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import io.github.piotr_janczukowicz.github.client.GitHubCaller;
import io.github.piotr_janczukowicz.github.client.GitHubResourceApi;
import io.github.piotr_janczukowicz.github.client.exception.GitHubApiException;
import io.github.piotr_janczukowicz.json.JsonType;
import io.github.piotr_janczukowicz.json.JsonTypes;
import io.github.piotr_janczukowicz.uri.PathTemplate;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

/** GitHub repository autolinks REST resource for API version {@code 2026-03-10}. */
public final class GitHubRepositoriesAutolinksApi extends GitHubResourceApi {

    private static final PathTemplate AUTOLINK = PathTemplate.of("/repos/{owner}/{repo}/autolinks/{autolink_id}");
    private static final PathTemplate AUTOLINKS = PathTemplate.of("/repos/{owner}/{repo}/autolinks");

    private static final JsonType<List<GitHubAutolink>> AUTOLINKS_TYPE = JsonTypes.listOf(GitHubAutolink.class);

    /**
     * Autolinks resource over {@code caller}.
     *
     * @param caller executes and decodes the calls of this resource
     * @throws NullPointerException if {@code caller} is {@code null}
     */
    public GitHubRepositoriesAutolinksApi(final GitHubCaller caller) {
        super(caller);
    }

    /**
     * List autolinks ({@code GET /repos/{owner}/{repo}/autolinks}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @return autolinks
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public List<GitHubAutolink> listAutolinks(final String owner, final String repo) throws IOException {
        return caller.fetch(get(AUTOLINKS, owner, repo), AUTOLINKS_TYPE);
    }

    /**
     * Create an autolink ({@code POST /repos/{owner}/{repo}/autolinks}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param payload autolink to create
     * @return created autolink
     * @throws NullPointerException if {@code payload} is {@code null}
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubAutolink createAutolink(
            final String owner, final String repo, final GitHubCreateAutolinkPayload payload) throws IOException {
        requireNonNull(payload, PARAM_PAYLOAD);

        return caller.fetch(caller.json(post(AUTOLINKS, owner, repo), payload), GitHubAutolink.class);
    }

    /**
     * Get an autolink ({@code GET /repos/{owner}/{repo}/autolinks/{autolink_id}}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param autolinkId identifier of the autolink reference
     * @return autolink, or empty on HTTP 404
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful (HTTP 404 yields empty)
     */
    public Optional<GitHubAutolink> getAutolink(final String owner, final String repo, final long autolinkId)
            throws IOException {

        return caller.fetchOrEmpty(get(AUTOLINK, owner, repo, autolinkId), GitHubAutolink.class);
    }

    /**
     * Delete an autolink ({@code DELETE /repos/{owner}/{repo}/autolinks/{autolink_id}}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param autolinkId identifier of the autolink reference
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public void deleteAutolink(final String owner, final String repo, final long autolinkId) throws IOException {
        caller.send(delete(AUTOLINK, owner, repo, autolinkId));
    }
}
