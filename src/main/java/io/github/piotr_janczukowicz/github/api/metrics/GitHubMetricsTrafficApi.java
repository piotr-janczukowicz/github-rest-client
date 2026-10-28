package io.github.piotr_janczukowicz.github.api.metrics;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

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

/** GitHub repository traffic REST resource for API version {@code 2026-03-10}. */
public final class GitHubMetricsTrafficApi extends GitHubResourceApi {

    private static final PathTemplate TRAFFIC_POPULAR_REFERRERS =
            PathTemplate.of("/repos/{owner}/{repo}/traffic/popular/referrers");
    private static final PathTemplate TRAFFIC_POPULAR_PATHS =
            PathTemplate.of("/repos/{owner}/{repo}/traffic/popular/paths");
    private static final PathTemplate TRAFFIC_VIEWS = PathTemplate.of("/repos/{owner}/{repo}/traffic/views");
    private static final PathTemplate TRAFFIC_CLONES = PathTemplate.of("/repos/{owner}/{repo}/traffic/clones");

    private static final JsonType<List<GitHubContentTraffic>> CONTENT_TRAFFIC_TYPE =
            JsonTypes.listOf(GitHubContentTraffic.class);
    private static final JsonType<List<GitHubReferrerTraffic>> REFERRER_TRAFFIC_TYPE =
            JsonTypes.listOf(GitHubReferrerTraffic.class);

    /**
     * Traffic resource over {@code caller}.
     *
     * @param caller executes and decodes the calls of this resource
     * @throws NullPointerException if {@code caller} is {@code null}
     */
    public GitHubMetricsTrafficApi(final GitHubCaller caller) {
        super(caller);
    }

    /**
     * Get repository clones ({@code GET /repos/{owner}/{repo}/traffic/clones}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @return clone traffic
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public GitHubCloneTraffic getClones(final String owner, final String repo) throws IOException {
        return getClones(owner, repo, GitHubGetClonesFilter.empty());
    }

    /**
     * Get repository clones ({@code GET /repos/{owner}/{repo}/traffic/clones}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param filter time frame filter
     * @return clone traffic
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws NullPointerException if {@code filter} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public GitHubCloneTraffic getClones(final String owner, final String repo, final GitHubGetClonesFilter filter)
            throws IOException {
        requireFlavor(GitHubFlavor.CLOUD, GitHubFlavor.GHEC);
        requireNonNull(filter, PARAM_FILTER);

        return caller.fetch(
                get(TRAFFIC_CLONES, owner, repo).withQuery(filter.toQueryParameters()), GitHubCloneTraffic.class);
    }

    /**
     * Get page views ({@code GET /repos/{owner}/{repo}/traffic/views}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @return view traffic
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public GitHubViewTraffic getViews(final String owner, final String repo) throws IOException {
        return getViews(owner, repo, GitHubGetClonesFilter.empty());
    }

    /**
     * Get page views ({@code GET /repos/{owner}/{repo}/traffic/views}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param filter time frame filter
     * @return view traffic
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws NullPointerException if {@code filter} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public GitHubViewTraffic getViews(final String owner, final String repo, final GitHubGetClonesFilter filter)
            throws IOException {
        requireFlavor(GitHubFlavor.CLOUD, GitHubFlavor.GHEC);
        requireNonNull(filter, PARAM_FILTER);

        return caller.fetch(
                get(TRAFFIC_VIEWS, owner, repo).withQuery(filter.toQueryParameters()), GitHubViewTraffic.class);
    }

    /**
     * Get top referral paths ({@code GET /repos/{owner}/{repo}/traffic/popular/paths}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @return top paths
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public List<GitHubContentTraffic> getTopPaths(final String owner, final String repo) throws IOException {
        requireFlavor(GitHubFlavor.CLOUD, GitHubFlavor.GHEC);
        return caller.fetch(get(TRAFFIC_POPULAR_PATHS, owner, repo), CONTENT_TRAFFIC_TYPE);
    }

    /**
     * Get top referral sources ({@code GET /repos/{owner}/{repo}/traffic/popular/referrers}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @return top referrers
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public List<GitHubReferrerTraffic> getTopReferrers(final String owner, final String repo) throws IOException {
        requireFlavor(GitHubFlavor.CLOUD, GitHubFlavor.GHEC);
        return caller.fetch(get(TRAFFIC_POPULAR_REFERRERS, owner, repo), REFERRER_TRAFFIC_TYPE);
    }
}
