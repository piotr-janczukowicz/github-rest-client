package io.github.piotr_janczukowicz.github.api.activity;

import io.github.piotr_janczukowicz.github.client.GitHubCaller;
import io.github.piotr_janczukowicz.github.client.GitHubResourceApi;
import io.github.piotr_janczukowicz.github.client.exception.GitHubApiException;
import io.github.piotr_janczukowicz.uri.PathTemplate;
import java.io.IOException;

/** GitHub activity feeds REST resource for API version {@code 2026-03-10}. */
public final class GitHubActivityFeedsApi extends GitHubResourceApi {

    private static final PathTemplate FEEDS = PathTemplate.of("/feeds");

    /**
     * Feeds resource over {@code caller}.
     *
     * @param caller executes and decodes the calls of this resource
     * @throws NullPointerException if {@code caller} is {@code null}
     */
    public GitHubActivityFeedsApi(final GitHubCaller caller) {
        super(caller);
    }

    /**
     * Get feeds ({@code GET /feeds}).
     *
     * @return feed links of the authenticated user
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubFeed getFeeds() throws IOException {
        return caller.fetch(get(FEEDS), GitHubFeed.class);
    }
}
