package io.github.piotr_janczukowicz.github.api.metrics;

import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubCaller;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.github.client.GitHubResourceApi;
import io.github.piotr_janczukowicz.github.client.exception.GitHubApiException;
import io.github.piotr_janczukowicz.uri.PathTemplate;
import java.io.IOException;

/** GitHub community profile metrics REST resource for API version {@code 2026-03-10}. */
public final class GitHubMetricsCommunityApi extends GitHubResourceApi {

    private static final PathTemplate COMMUNITY_PROFILE = PathTemplate.of("/repos/{owner}/{repo}/community/profile");

    /**
     * Community metrics resource over {@code caller}.
     *
     * @param caller executes and decodes the calls of this resource
     * @throws NullPointerException if {@code caller} is {@code null}
     */
    public GitHubMetricsCommunityApi(final GitHubCaller caller) {
        super(caller);
    }

    /**
     * Get community profile metrics ({@code GET /repos/{owner}/{repo}/community/profile}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @return community profile
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public GitHubCommunityProfile getCommunityProfileMetrics(final String owner, final String repo) throws IOException {
        requireFlavor(GitHubFlavor.CLOUD, GitHubFlavor.GHEC);
        return caller.fetch(get(COMMUNITY_PROFILE, owner, repo), GitHubCommunityProfile.class);
    }
}
