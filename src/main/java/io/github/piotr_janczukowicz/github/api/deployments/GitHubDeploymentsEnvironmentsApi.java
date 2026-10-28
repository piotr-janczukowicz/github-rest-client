package io.github.piotr_janczukowicz.github.api.deployments;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import io.github.piotr_janczukowicz.github.client.GitHubCaller;
import io.github.piotr_janczukowicz.github.client.GitHubResourceApi;
import io.github.piotr_janczukowicz.github.client.exception.GitHubApiException;
import io.github.piotr_janczukowicz.github.client.pagination.GitHubListSpec;
import io.github.piotr_janczukowicz.github.pagination.GitHubPage;
import io.github.piotr_janczukowicz.github.pagination.GitHubPageRequest;
import io.github.piotr_janczukowicz.github.pagination.GitHubPageRequestKindValidators;
import io.github.piotr_janczukowicz.uri.PathTemplate;
import java.io.IOException;
import java.util.Map;
import java.util.Optional;
import org.jspecify.annotations.Nullable;

/** GitHub deployment environments REST resource for API version {@code 2026-03-10}. */
public final class GitHubDeploymentsEnvironmentsApi extends GitHubResourceApi {

    private static final PathTemplate ENVIRONMENT =
            PathTemplate.of("/repos/{owner}/{repo}/environments/{environment_name}");
    private static final PathTemplate ENVIRONMENTS = PathTemplate.of("/repos/{owner}/{repo}/environments");

    /**
     * Deployment environments resource over {@code caller}.
     *
     * @param caller executes and decodes the calls of this resource
     * @throws NullPointerException if {@code caller} is {@code null}
     */
    public GitHubDeploymentsEnvironmentsApi(final GitHubCaller caller) {
        super(caller);
    }

    /**
     * List environments ({@code GET /repos/{owner}/{repo}/environments}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @return first HTTP page of environments
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubEnvironment> getAllEnvironments(final String owner, final String repo) throws IOException {
        return getAllEnvironments(owner, repo, GitHubPageRequest.byPage());
    }

    /**
     * List environments ({@code GET /repos/{owner}/{repo}/environments}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param request pagination token
     * @return first HTTP page of environments
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank, or {@code request} is invalid
     * @throws NullPointerException if {@code request} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubEnvironment> getAllEnvironments(
            final String owner, final String repo, final GitHubPageRequest request) throws IOException {
        requireNonNull(request, PARAM_REQUEST);

        return list(
                ENVIRONMENTS,
                Map.of(),
                request,
                GitHubListSpec.wrapper(
                        GitHubPageRequestKindValidators.PAGE,
                        GitHubEnvironmentsPage.class,
                        GitHubEnvironmentsPage::environments,
                        GitHubEnvironmentsPage::totalCount),
                owner,
                repo);
    }

    /**
     * Get an environment ({@code GET /repos/{owner}/{repo}/environments/{environment_name}}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param environmentName environment name
     * @return environment, or empty on HTTP 404
     * @throws IllegalArgumentException if any argument is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful (HTTP 404 yields empty)
     */
    public Optional<GitHubEnvironment> getEnvironment(
            final String owner, final String repo, final String environmentName) throws IOException {

        return caller.fetchOrEmpty(get(ENVIRONMENT, owner, repo, environmentName), GitHubEnvironment.class);
    }

    /**
     * Create or update an environment ({@code PUT /repos/{owner}/{repo}/environments/{environment_name}}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param environmentName environment name
     * @param payload optional environment settings
     * @return created or updated environment
     * @throws IllegalArgumentException if any argument is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubEnvironment createOrUpdateEnvironment(
            final String owner,
            final String repo,
            final String environmentName,
            final @Nullable GitHubCreateOrUpdateEnvironmentPayload payload)
            throws IOException {

        return caller.fetch(
                caller.jsonIfPresent(put(ENVIRONMENT, owner, repo, environmentName), payload), GitHubEnvironment.class);
    }

    /**
     * Delete an environment ({@code DELETE /repos/{owner}/{repo}/environments/{environment_name}}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param environmentName environment name
     * @throws IllegalArgumentException if any argument is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public void deleteAnEnvironment(final String owner, final String repo, final String environmentName)
            throws IOException {

        caller.send(delete(ENVIRONMENT, owner, repo, environmentName));
    }
}
