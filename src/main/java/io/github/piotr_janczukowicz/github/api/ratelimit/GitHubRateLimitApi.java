package io.github.piotr_janczukowicz.github.api.ratelimit;

import io.github.piotr_janczukowicz.github.client.GitHubCaller;
import io.github.piotr_janczukowicz.github.client.GitHubResourceApi;
import io.github.piotr_janczukowicz.github.client.exception.GitHubApiException;
import io.github.piotr_janczukowicz.uri.PathTemplate;
import java.io.IOException;
import java.util.Optional;

/** GitHub rate limit REST resource for API version {@code 2026-03-10}. */
public final class GitHubRateLimitApi extends GitHubResourceApi {

    private static final PathTemplate RATE_LIMIT = PathTemplate.of("/rate_limit");

    /**
     * Rate limit resource over {@code caller}.
     *
     * @param caller executes and decodes the calls of this resource
     * @throws NullPointerException if {@code caller} is {@code null}
     */
    public GitHubRateLimitApi(final GitHubCaller caller) {
        super(caller);
    }

    /**
     * Get rate limit status for the authenticated user ({@code GET /rate_limit}).
     *
     * @return rate limit overview, or empty on HTTP 404
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful (HTTP 404 yields empty)
     */
    public Optional<GitHubRateLimitOverview> get() throws IOException {
        return caller.fetchOrEmpty(get(RATE_LIMIT), GitHubRateLimitOverview.class);
    }
}
