package io.github.piotr_janczukowicz.github.api.emojis;

import io.github.piotr_janczukowicz.github.client.GitHubCaller;
import io.github.piotr_janczukowicz.github.client.GitHubResourceApi;
import io.github.piotr_janczukowicz.github.client.exception.GitHubApiException;
import io.github.piotr_janczukowicz.json.JsonType;
import io.github.piotr_janczukowicz.json.JsonTypes;
import io.github.piotr_janczukowicz.uri.PathTemplate;
import java.io.IOException;
import java.util.Map;

/** GitHub emojis REST resource for API version {@code 2026-03-10}. */
public final class GitHubEmojisApi extends GitHubResourceApi {

    private static final PathTemplate EMOJIS = PathTemplate.of("/emojis");

    private static final JsonType<Map<String, String>> EMOJIS_TYPE = JsonTypes.mapOf(String.class);

    /**
     * Emojis resource over {@code caller}.
     *
     * @param caller executes and decodes the calls of this resource
     * @throws NullPointerException if {@code caller} is {@code null}
     */
    public GitHubEmojisApi(final GitHubCaller caller) {
        super(caller);
    }

    /**
     * Get emojis ({@code GET /emojis}): lists all the emojis available to use on GitHub.
     *
     * @return emoji names mapped to image URLs
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public Map<String, String> get() throws IOException {
        return caller.fetch(super.get(EMOJIS), EMOJIS_TYPE);
    }
}
