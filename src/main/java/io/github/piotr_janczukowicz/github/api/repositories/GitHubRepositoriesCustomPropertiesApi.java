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

/** GitHub repository custom properties REST resource for API version {@code 2026-03-10}. */
public final class GitHubRepositoriesCustomPropertiesApi extends GitHubResourceApi {

    private static final PathTemplate PROPERTIES_VALUES = PathTemplate.of("/repos/{owner}/{repo}/properties/values");

    private static final JsonType<List<GitHubCustomPropertyValue>> CUSTOM_PROPERTY_VALUES_TYPE =
            JsonTypes.listOf(GitHubCustomPropertyValue.class);

    /**
     * Custom properties resource over {@code caller}.
     *
     * @param caller executes and decodes the calls of this resource
     * @throws NullPointerException if {@code caller} is {@code null}
     */
    public GitHubRepositoriesCustomPropertiesApi(final GitHubCaller caller) {
        super(caller);
    }

    /**
     * Get all custom property values for a repository ({@code GET /repos/{owner}/{repo}/properties/values}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @return custom property values
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public List<GitHubCustomPropertyValue> getRepositoryValues(final String owner, final String repo)
            throws IOException {

        return caller.fetch(get(PROPERTIES_VALUES, owner, repo), CUSTOM_PROPERTY_VALUES_TYPE);
    }

    /**
     * Create or update custom property values for a repository ({@code PATCH /repos/{owner}/{repo}/properties/values}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param payload property values
     * @throws NullPointerException if {@code payload} is {@code null}
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public void createOrUpdateRepositoryValues(
            final String owner,
            final String repo,
            final GitHubCustomPropertiesForReposCreateOrUpdateRepositoryValuesPayload payload)
            throws IOException {
        requireNonNull(payload, PARAM_PAYLOAD);

        caller.send(caller.json(patch(PROPERTIES_VALUES, owner, repo), payload));
    }
}
