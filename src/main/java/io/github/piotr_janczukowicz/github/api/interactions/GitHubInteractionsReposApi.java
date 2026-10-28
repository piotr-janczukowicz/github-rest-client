package io.github.piotr_janczukowicz.github.api.interactions;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.api.users.GitHubSimpleUser;
import io.github.piotr_janczukowicz.github.client.GitHubCaller;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.github.client.GitHubResourceApi;
import io.github.piotr_janczukowicz.github.client.exception.GitHubApiException;
import io.github.piotr_janczukowicz.http.HttpStatus;
import io.github.piotr_janczukowicz.json.JsonType;
import io.github.piotr_janczukowicz.json.JsonTypes;
import io.github.piotr_janczukowicz.uri.PathTemplate;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

/** GitHub repository interaction restrictions REST resource for API version {@code 2026-03-10}. */
public final class GitHubInteractionsReposApi extends GitHubResourceApi {

    private static final PathTemplate INTERACTION_LIMITS = PathTemplate.of("/repos/{owner}/{repo}/interaction-limits");
    private static final PathTemplate PULLS_BYPASS_LIST =
            PathTemplate.of("/repos/{owner}/{repo}/interaction-limits/pulls/bypass-list");
    private static final PathTemplate PULLS_CREATION_CAP =
            PathTemplate.of("/repos/{owner}/{repo}/interaction-limits/pulls/creation-cap");

    private static final JsonType<List<GitHubSimpleUser>> BYPASS_LIST_TYPE = JsonTypes.listOf(GitHubSimpleUser.class);

    /**
     * Repository interactions resource over {@code caller}.
     *
     * @param caller executes and decodes the calls of this resource
     * @throws NullPointerException if {@code caller} is {@code null}
     */
    public GitHubInteractionsReposApi(final GitHubCaller caller) {
        super(caller);
    }

    /**
     * Get interaction restrictions for a repository ({@code GET /repos/{owner}/{repo}/interaction-limits}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @return restrictions, or empty when GitHub returns HTTP 204, no body, or an empty JSON object
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public Optional<GitHubInteractionLimitResponse> getRestrictionsForRepo(final String owner, final String repo)
            throws IOException {
        requireFlavor(GitHubFlavor.CLOUD, GitHubFlavor.GHEC);

        return caller.fetchUnless(
                get(INTERACTION_LIMITS, owner, repo), GitHubInteractionLimitResponse.class, HttpStatus.NO_CONTENT);
    }

    /**
     * Set interaction restrictions for a repository ({@code PUT /repos/{owner}/{repo}/interaction-limits}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param payload interaction limit
     * @return applied restrictions
     * @throws NullPointerException if {@code payload} is {@code null}
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @SuppressWarnings("PMD.LinguisticNaming")
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public GitHubInteractionLimitResponse setRestrictionsForRepo(
            final String owner, final String repo, final GitHubInteractionLimit payload) throws IOException {
        requireFlavor(GitHubFlavor.CLOUD, GitHubFlavor.GHEC);
        requireNonNull(payload, PARAM_PAYLOAD);

        return caller.fetch(
                caller.json(put(INTERACTION_LIMITS, owner, repo), payload), GitHubInteractionLimitResponse.class);
    }

    /**
     * Remove interaction restrictions for a repository ({@code DELETE /repos/{owner}/{repo}/interaction-limits}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public void removeRestrictionsForRepo(final String owner, final String repo) throws IOException {
        requireFlavor(GitHubFlavor.CLOUD, GitHubFlavor.GHEC);
        caller.send(delete(INTERACTION_LIMITS, owner, repo));
    }

    /**
     * Get pull request creation cap bypass list for a repository ({@code GET
     * /repos/{owner}/{repo}/interaction-limits/pulls/bypass-list}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @return bypass list users
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public List<GitHubSimpleUser> getPullRequestBypassListForRepo(final String owner, final String repo)
            throws IOException {
        requireFlavor(GitHubFlavor.CLOUD, GitHubFlavor.GHEC);

        return caller.fetch(get(PULLS_BYPASS_LIST, owner, repo), BYPASS_LIST_TYPE);
    }

    /**
     * Add users to the pull request creation cap bypass list for a repository ({@code PUT
     * /repos/{owner}/{repo}/interaction-limits/pulls/bypass-list}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param payload users to add
     * @throws NullPointerException if {@code payload} is {@code null}
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public void setPullRequestBypassListForRepo(
            final String owner, final String repo, final GitHubInteractionLimitPullRequestBypassList payload)
            throws IOException {
        requireFlavor(GitHubFlavor.CLOUD, GitHubFlavor.GHEC);
        requireNonNull(payload, PARAM_PAYLOAD);

        caller.send(caller.json(put(PULLS_BYPASS_LIST, owner, repo), payload));
    }

    /**
     * Remove users from the pull request creation cap bypass list for a repository ({@code DELETE
     * /repos/{owner}/{repo}/interaction-limits/pulls/bypass-list}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param payload users to remove
     * @throws NullPointerException if {@code payload} is {@code null}
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public void removePullRequestBypassListForRepo(
            final String owner, final String repo, final GitHubInteractionLimitPullRequestBypassList payload)
            throws IOException {
        requireFlavor(GitHubFlavor.CLOUD, GitHubFlavor.GHEC);
        requireNonNull(payload, PARAM_PAYLOAD);

        caller.send(caller.json(delete(PULLS_BYPASS_LIST, owner, repo), payload));
    }

    /**
     * Get pull request creation cap for a repository ({@code GET
     * /repos/{owner}/{repo}/interaction-limits/pulls/creation-cap}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @return creation cap
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public GitHubPullRequestCreationCap getPullRequestCreationCapForRepo(final String owner, final String repo)
            throws IOException {
        requireFlavor(GitHubFlavor.CLOUD, GitHubFlavor.GHEC);

        return caller.fetch(get(PULLS_CREATION_CAP, owner, repo), GitHubPullRequestCreationCap.class);
    }

    /**
     * Update pull request creation cap for a repository ({@code PATCH
     * /repos/{owner}/{repo}/interaction-limits/pulls/creation-cap}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param payload creation cap update
     * @return updated creation cap
     * @throws NullPointerException if {@code payload} is {@code null}
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public GitHubPullRequestCreationCap updatePullRequestCreationCapForRepo(
            final String owner, final String repo, final GitHubUpdatePullRequestCreationCapForOrgPayload payload)
            throws IOException {
        requireFlavor(GitHubFlavor.CLOUD, GitHubFlavor.GHEC);
        requireNonNull(payload, PARAM_PAYLOAD);

        return caller.fetch(
                caller.json(patch(PULLS_CREATION_CAP, owner, repo), payload), GitHubPullRequestCreationCap.class);
    }
}
