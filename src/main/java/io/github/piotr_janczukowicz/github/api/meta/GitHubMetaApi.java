package io.github.piotr_janczukowicz.github.api.meta;

import static io.github.piotr_janczukowicz.http.HttpHeaderNames.ACCEPT;
import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubCaller;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.github.client.GitHubResourceApi;
import io.github.piotr_janczukowicz.github.client.exception.GitHubApiException;
import io.github.piotr_janczukowicz.http.HttpResponse;
import io.github.piotr_janczukowicz.json.JsonType;
import io.github.piotr_janczukowicz.json.JsonTypes;
import io.github.piotr_janczukowicz.uri.PathTemplate;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * GitHub meta REST resource for API version {@code 2026-03-10}.
 *
 * <p>Endpoints that give information about the API.
 */
public final class GitHubMetaApi extends GitHubResourceApi {

    private static final PathTemplate ROOT = PathTemplate.of("/");
    private static final PathTemplate META = PathTemplate.of("/meta");
    private static final PathTemplate OCTOCAT = PathTemplate.of("/octocat");
    private static final PathTemplate VERSIONS = PathTemplate.of("/versions");
    private static final PathTemplate ZEN = PathTemplate.of("/zen");

    private static final String OCTOCAT_MEDIA_TYPE = "application/octocat-stream";

    private static final JsonType<List<String>> VERSIONS_TYPE = JsonTypes.listOf(String.class);

    /**
     * Meta resource over {@code caller}.
     *
     * @param caller executes and decodes the calls of this resource
     * @throws NullPointerException if {@code caller} is {@code null}
     */
    public GitHubMetaApi(final GitHubCaller caller) {
        super(caller);
    }

    /**
     * Get hypermedia links to resources accessible in GitHub's REST API ({@code GET /}).
     *
     * @return API root links
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubApiRoot root() throws IOException {
        return caller.fetch(get(ROOT), GitHubApiRoot.class);
    }

    /**
     * Get GitHub meta information ({@code GET /meta}).
     *
     * @return meta overview
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubApiOverview get() throws IOException {
        return caller.fetch(get(META), GitHubApiOverview.class);
    }

    /**
     * Get Octocat ({@code GET /octocat}).
     *
     * @return octocat ASCII art
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public String octocat() throws IOException {
        return octocat(GitHubGetOctocatFilter.empty());
    }

    /**
     * Get Octocat ({@code GET /octocat}).
     *
     * @param filter optional speech bubble text
     * @return octocat ASCII art
     * @throws NullPointerException if {@code filter} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public String octocat(final GitHubGetOctocatFilter filter) throws IOException {
        requireNonNull(filter, PARAM_FILTER);
        final HttpResponse response =
                caller.send(get(OCTOCAT).withQuery(filter.toQueryParameters()).withHeader(ACCEPT, OCTOCAT_MEDIA_TYPE));
        return new String(response.requireBodyBytes(), StandardCharsets.UTF_8);
    }

    /**
     * Get all API versions ({@code GET /versions}).
     *
     * @return API version date strings
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public List<String> versions() throws IOException {
        requireFlavor(GitHubFlavor.CLOUD, GitHubFlavor.GHEC);
        return caller.fetch(get(VERSIONS), VERSIONS_TYPE);
    }

    /**
     * Get the Zen of GitHub ({@code GET /zen}). The request keeps the default {@code Accept} header: the spec lists
     * {@code text/plain}, but GitHub answers 415 to that value.
     *
     * @return zen message
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public String zen() throws IOException {
        final HttpResponse response = caller.send(get(ZEN));
        return new String(response.requireBodyBytes(), StandardCharsets.UTF_8);
    }
}
