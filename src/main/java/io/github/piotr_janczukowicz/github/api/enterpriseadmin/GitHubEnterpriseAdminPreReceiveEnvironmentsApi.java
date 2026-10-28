package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubCaller;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.github.client.GitHubResourceApi;
import io.github.piotr_janczukowicz.github.client.exception.GitHubApiException;
import io.github.piotr_janczukowicz.github.pagination.GitHubPage;
import io.github.piotr_janczukowicz.github.pagination.GitHubPageRequest;
import io.github.piotr_janczukowicz.json.JsonType;
import io.github.piotr_janczukowicz.json.JsonTypes;
import io.github.piotr_janczukowicz.uri.PathTemplate;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

/** GitHub Enterprise Server pre-receive environment REST resource for API version {@code 2026-03-10}. */
public final class GitHubEnterpriseAdminPreReceiveEnvironmentsApi extends GitHubResourceApi {

    private static final PathTemplate ENVIRONMENTS = PathTemplate.of("/admin/pre-receive-environments");
    private static final PathTemplate ENVIRONMENT =
            PathTemplate.of("/admin/pre-receive-environments/{pre_receive_environment_id}");
    private static final PathTemplate DOWNLOADS =
            PathTemplate.of("/admin/pre-receive-environments/{pre_receive_environment_id}/downloads");
    private static final PathTemplate LATEST_DOWNLOAD =
            PathTemplate.of("/admin/pre-receive-environments/{pre_receive_environment_id}/downloads/latest");

    private static final JsonType<List<GitHubPreReceiveEnvironment>> ENVIRONMENTS_TYPE =
            JsonTypes.listOf(GitHubPreReceiveEnvironment.class);

    /**
     * Resource over {@code caller}.
     *
     * @param caller executes and decodes the calls of this resource
     * @throws NullPointerException if {@code caller} is {@code null}
     */
    public GitHubEnterpriseAdminPreReceiveEnvironmentsApi(final GitHubCaller caller) {
        super(caller);
    }

    /**
     * List pre-receive environments ({@code GET /admin/pre-receive-environments}).
     *
     * @return first HTTP page
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHES}
     */
    @Flavors(GitHubFlavor.GHES)
    public GitHubPage<GitHubPreReceiveEnvironment> listPreReceiveEnvironments() throws IOException {
        return listPreReceiveEnvironments(GitHubListPreReceiveEnvironmentsFilter.empty());
    }

    /**
     * List pre-receive environments ({@code GET /admin/pre-receive-environments}).
     *
     * @param filter sort and direction
     * @return first HTTP page
     * @throws NullPointerException if {@code filter} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHES}
     */
    @Flavors(GitHubFlavor.GHES)
    public GitHubPage<GitHubPreReceiveEnvironment> listPreReceiveEnvironments(
            final GitHubListPreReceiveEnvironmentsFilter filter) throws IOException {
        return listPreReceiveEnvironments(filter, GitHubPageRequest.byPage());
    }

    /**
     * List pre-receive environments ({@code GET /admin/pre-receive-environments}).
     *
     * @param filter sort and direction
     * @param request pagination token
     * @return first HTTP page
     * @throws NullPointerException if {@code filter} or {@code request} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHES}
     */
    @Flavors(GitHubFlavor.GHES)
    public GitHubPage<GitHubPreReceiveEnvironment> listPreReceiveEnvironments(
            final GitHubListPreReceiveEnvironmentsFilter filter, final GitHubPageRequest request) throws IOException {
        requireFlavor(GitHubFlavor.GHES);
        requireNonNull(filter, PARAM_FILTER);
        requireNonNull(request, PARAM_REQUEST);

        return pageList(ENVIRONMENTS, filter.toQueryParameters(), request, ENVIRONMENTS_TYPE);
    }

    /**
     * Create a pre-receive environment ({@code POST /admin/pre-receive-environments}).
     *
     * @param payload request body
     * @return the resource
     * @throws NullPointerException if {@code payload} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHES}
     */
    @Flavors(GitHubFlavor.GHES)
    public GitHubPreReceiveEnvironment createPreReceiveEnvironment(
            final GitHubCreatePreReceiveEnvironmentPayload payload) throws IOException {
        requireFlavor(GitHubFlavor.GHES);
        requireNonNull(payload, PARAM_PAYLOAD);

        return caller.fetch(caller.json(post(ENVIRONMENTS), payload), GitHubPreReceiveEnvironment.class);
    }

    /**
     * Get a pre-receive environment ({@code GET /admin/pre-receive-environments/{pre_receive_environment_id}}).
     *
     * @param preReceiveEnvironmentId pre-receive environment id
     * @return the resource, or empty if it does not exist
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHES}
     */
    @Flavors(GitHubFlavor.GHES)
    public Optional<GitHubPreReceiveEnvironment> getPreReceiveEnvironment(final long preReceiveEnvironmentId)
            throws IOException {
        requireFlavor(GitHubFlavor.GHES);
        return caller.fetchOrEmpty(get(ENVIRONMENT, preReceiveEnvironmentId), GitHubPreReceiveEnvironment.class);
    }

    /**
     * Update a pre-receive environment ({@code PATCH /admin/pre-receive-environments/{pre_receive_environment_id}}).
     *
     * @param preReceiveEnvironmentId pre-receive environment id
     * @param payload request body
     * @return the resource
     * @throws NullPointerException if {@code payload} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHES}
     */
    @Flavors(GitHubFlavor.GHES)
    public GitHubPreReceiveEnvironment updatePreReceiveEnvironment(
            final long preReceiveEnvironmentId, final GitHubUpdatePreReceiveEnvironmentPayload payload)
            throws IOException {
        requireFlavor(GitHubFlavor.GHES);
        requireNonNull(payload, PARAM_PAYLOAD);

        return caller.fetch(
                caller.json(patch(ENVIRONMENT, preReceiveEnvironmentId), payload), GitHubPreReceiveEnvironment.class);
    }

    /**
     * Delete a pre-receive environment ({@code DELETE /admin/pre-receive-environments/{pre_receive_environment_id}}).
     *
     * @param preReceiveEnvironmentId pre-receive environment id
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHES}
     */
    @Flavors(GitHubFlavor.GHES)
    public void deletePreReceiveEnvironment(final long preReceiveEnvironmentId) throws IOException {
        requireFlavor(GitHubFlavor.GHES);
        caller.send(delete(ENVIRONMENT, preReceiveEnvironmentId));
    }

    /**
     * Start a pre-receive environment download ({@code POST
     * /admin/pre-receive-environments/{pre_receive_environment_id}/downloads}).
     *
     * @param preReceiveEnvironmentId pre-receive environment id
     * @return result
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHES}
     */
    @Flavors(GitHubFlavor.GHES)
    public GitHubPreReceiveEnvironmentDownloadStatus startPreReceiveEnvironmentDownload(
            final long preReceiveEnvironmentId) throws IOException {
        requireFlavor(GitHubFlavor.GHES);
        return caller.fetch(post(DOWNLOADS, preReceiveEnvironmentId), GitHubPreReceiveEnvironmentDownloadStatus.class);
    }

    /**
     * Get the download status of a pre-receive environment ({@code GET
     * /admin/pre-receive-environments/{pre_receive_environment_id}/downloads/latest}).
     *
     * @param preReceiveEnvironmentId pre-receive environment id
     * @return the resource, or empty if it does not exist
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHES}
     */
    @Flavors(GitHubFlavor.GHES)
    public Optional<GitHubPreReceiveEnvironmentDownloadStatus> getDownloadStatusForPreReceiveEnvironment(
            final long preReceiveEnvironmentId) throws IOException {
        requireFlavor(GitHubFlavor.GHES);
        return caller.fetchOrEmpty(
                get(LATEST_DOWNLOAD, preReceiveEnvironmentId), GitHubPreReceiveEnvironmentDownloadStatus.class);
    }
}
