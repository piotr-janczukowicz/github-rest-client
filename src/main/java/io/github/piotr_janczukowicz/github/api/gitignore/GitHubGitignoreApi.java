package io.github.piotr_janczukowicz.github.api.gitignore;

import io.github.piotr_janczukowicz.github.client.GitHubCaller;
import io.github.piotr_janczukowicz.github.client.GitHubResourceApi;
import io.github.piotr_janczukowicz.github.client.exception.GitHubApiException;
import io.github.piotr_janczukowicz.json.JsonType;
import io.github.piotr_janczukowicz.json.JsonTypes;
import io.github.piotr_janczukowicz.uri.PathTemplate;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

/** GitHub gitignore REST resource for API version {@code 2026-03-10}. */
public final class GitHubGitignoreApi extends GitHubResourceApi {

    private static final PathTemplate TEMPLATES = PathTemplate.of("/gitignore/templates");
    private static final PathTemplate TEMPLATE = PathTemplate.of("/gitignore/templates/{name}");

    private static final JsonType<List<String>> TEMPLATES_TYPE = JsonTypes.listOf(String.class);

    /**
     * Gitignore resource over {@code caller}.
     *
     * @param caller executes and decodes the calls of this resource
     * @throws NullPointerException if {@code caller} is {@code null}
     */
    public GitHubGitignoreApi(final GitHubCaller caller) {
        super(caller);
    }

    /**
     * Get all gitignore templates ({@code GET /gitignore/templates}).
     *
     * @return template names
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public List<String> templates() throws IOException {
        return caller.fetch(super.get(TEMPLATES), TEMPLATES_TYPE);
    }

    /**
     * Get a gitignore template ({@code GET /gitignore/templates/{name}}).
     *
     * @param name template name
     * @return template, or empty on HTTP 404
     * @throws IllegalArgumentException if {@code name} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful (HTTP 404 yields empty)
     */
    public Optional<GitHubGitignoreTemplate> get(final String name) throws IOException {
        return caller.fetchOrEmpty(super.get(TEMPLATE, name), GitHubGitignoreTemplate.class);
    }
}
