package io.github.piotr_janczukowicz.github.api.apps;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import io.github.piotr_janczukowicz.github.api.organizations.GitHubInstallation;
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

/**
 * GitHub App installations REST resource for API version {@code 2026-03-10}.
 *
 * <p>{@code DELETE /installation/token} revokes the installation access token that the call itself authenticates with,
 * and {@code GET /installation/repositories} also acts as the installation: both need an installation access token, not
 * a JWT of the app.
 */
public final class GitHubAppsInstallationsApi extends GitHubResourceApi {

    private static final PathTemplate INSTALLATION_REPOSITORIES = PathTemplate.of("/installation/repositories");
    private static final PathTemplate INSTALLATION_TOKEN = PathTemplate.of("/installation/token");
    private static final PathTemplate USER_INSTALLATIONS = PathTemplate.of("/user/installations");
    private static final PathTemplate USER_INSTALLATION_REPOSITORIES =
            PathTemplate.of("/user/installations/{installation_id}/repositories");
    private static final PathTemplate USER_INSTALLATION_REPOSITORY =
            PathTemplate.of("/user/installations/{installation_id}/repositories/{repository_id}");

    private static final GitHubListSpec<GitHubListReposAccessibleToInstallationRepository>
            REPOSITORIES_ACCESSIBLE_SPEC = GitHubListSpec.wrapper(
                    GitHubPageRequestKindValidators.PAGE,
                    GitHubListReposAccessibleToInstallationPage.class,
                    GitHubListReposAccessibleToInstallationPage::repositories,
                    GitHubListReposAccessibleToInstallationPage::totalCount);

    private static final GitHubListSpec<GitHubInstallation> USER_INSTALLATIONS_SPEC = GitHubListSpec.wrapper(
            GitHubPageRequestKindValidators.PAGE,
            GitHubListInstallationsForAuthenticatedUserPage.class,
            GitHubListInstallationsForAuthenticatedUserPage::installations,
            GitHubListInstallationsForAuthenticatedUserPage::totalCount);

    private static final GitHubListSpec<GitHubListInstallationReposForAuthenticatedUserRepository>
            USER_INSTALLATION_REPOSITORIES_SPEC = GitHubListSpec.wrapper(
                    GitHubPageRequestKindValidators.PAGE,
                    GitHubListInstallationReposForAuthenticatedUserPage.class,
                    GitHubListInstallationReposForAuthenticatedUserPage::repositories,
                    GitHubListInstallationReposForAuthenticatedUserPage::totalCount);

    /**
     * GitHub App installations resource over {@code caller}.
     *
     * @param caller executes and decodes the calls of this resource
     * @throws NullPointerException if {@code caller} is {@code null}
     */
    public GitHubAppsInstallationsApi(final GitHubCaller caller) {
        super(caller);
    }

    /**
     * List repositories accessible to the app installation ({@code GET /installation/repositories}).
     *
     * @param request pagination token
     * @return HTTP page of repositories
     * @throws NullPointerException if {@code request} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubListReposAccessibleToInstallationRepository> listReposAccessibleToInstallation(
            final GitHubPageRequest request) throws IOException {
        requireNonNull(request, PARAM_REQUEST);

        return list(INSTALLATION_REPOSITORIES, Map.of(), request, REPOSITORIES_ACCESSIBLE_SPEC);
    }

    /**
     * Revoke an installation access token ({@code DELETE /installation/token}).
     *
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public void revokeInstallationAccessToken() throws IOException {
        caller.send(delete(INSTALLATION_TOKEN));
    }

    /**
     * List app installations accessible to the user access token ({@code GET /user/installations}).
     *
     * @param request pagination token
     * @return HTTP page of installations
     * @throws NullPointerException if {@code request} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubInstallation> listInstallationsForAuthenticatedUser(final GitHubPageRequest request)
            throws IOException {
        requireNonNull(request, PARAM_REQUEST);

        return list(USER_INSTALLATIONS, Map.of(), request, USER_INSTALLATIONS_SPEC);
    }

    /**
     * List repositories accessible to the user access token ({@code GET
     * /user/installations/{installation_id}/repositories}).
     *
     * @param installationId installation id
     * @param request pagination token
     * @return HTTP page of repositories
     * @throws NullPointerException if {@code request} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubListInstallationReposForAuthenticatedUserRepository>
            listInstallationReposForAuthenticatedUser(final long installationId, final GitHubPageRequest request)
                    throws IOException {
        requireNonNull(request, PARAM_REQUEST);

        return list(
                USER_INSTALLATION_REPOSITORIES, Map.of(), request, USER_INSTALLATION_REPOSITORIES_SPEC, installationId);
    }

    /**
     * Add a repository to an app installation ({@code PUT
     * /user/installations/{installation_id}/repositories/{repository_id}}).
     *
     * @param installationId installation id
     * @param repositoryId repository id
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public void addRepoToInstallationForAuthenticatedUser(final long installationId, final long repositoryId)
            throws IOException {
        caller.send(put(USER_INSTALLATION_REPOSITORY, installationId, repositoryId));
    }

    /**
     * Remove a repository from an app installation ({@code DELETE
     * /user/installations/{installation_id}/repositories/{repository_id}}).
     *
     * @param installationId installation id
     * @param repositoryId repository id
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public void removeRepoFromInstallationForAuthenticatedUser(final long installationId, final long repositoryId)
            throws IOException {
        caller.send(delete(USER_INSTALLATION_REPOSITORY, installationId, repositoryId));
    }
}
