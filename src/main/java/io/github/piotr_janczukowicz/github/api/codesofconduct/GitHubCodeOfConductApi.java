package io.github.piotr_janczukowicz.github.api.codesofconduct;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNotBlank;

import io.github.piotr_janczukowicz.github.client.GitHubCaller;
import io.github.piotr_janczukowicz.github.client.GitHubResourceApi;
import io.github.piotr_janczukowicz.github.client.exception.GitHubApiException;
import io.github.piotr_janczukowicz.json.JsonType;
import io.github.piotr_janczukowicz.json.JsonTypes;
import io.github.piotr_janczukowicz.uri.PathTemplate;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

/** GitHub codes of conduct REST resource for API version {@code 2026-03-10}. */
public final class GitHubCodeOfConductApi extends GitHubResourceApi {

    private static final PathTemplate CODES_OF_CONDUCT = PathTemplate.of("/codes_of_conduct");
    private static final PathTemplate CODE_OF_CONDUCT = PathTemplate.of("/codes_of_conduct/{key}");

    private static final String PARAM_KEY = "key";

    private static final JsonType<List<GitHubCodeOfConduct>> CODES_OF_CONDUCT_TYPE =
            JsonTypes.listOf(GitHubCodeOfConduct.class);

    /**
     * Codes of conduct resource over {@code caller}.
     *
     * @param caller executes and decodes the calls of this resource
     * @throws NullPointerException if {@code caller} is {@code null}
     */
    public GitHubCodeOfConductApi(final GitHubCaller caller) {
        super(caller);
    }

    /**
     * Get all codes of conduct ({@code GET /codes_of_conduct}).
     *
     * @return all GitHub codes of conduct
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public List<GitHubCodeOfConduct> getAllCodesOfConduct() throws IOException {
        return caller.fetch(get(CODES_OF_CONDUCT), CODES_OF_CONDUCT_TYPE);
    }

    /**
     * Get a code of conduct ({@code GET /codes_of_conduct/{key}}).
     *
     * @param key code of conduct key
     * @return code of conduct, or empty on HTTP 404
     * @throws IllegalArgumentException if {@code key} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful (HTTP 404 yields empty)
     */
    public Optional<GitHubCodeOfConduct> getConductCode(final String key) throws IOException {
        return caller.fetchOrEmpty(get(CODE_OF_CONDUCT, requireNotBlank(key, PARAM_KEY)), GitHubCodeOfConduct.class);
    }
}
