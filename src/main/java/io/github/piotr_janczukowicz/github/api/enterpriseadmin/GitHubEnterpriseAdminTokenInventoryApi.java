package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubCaller;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.github.client.GitHubResourceApi;
import io.github.piotr_janczukowicz.github.client.exception.GitHubApiException;
import io.github.piotr_janczukowicz.github.client.pagination.GitHubListSpec;
import io.github.piotr_janczukowicz.github.pagination.GitHubPage;
import io.github.piotr_janczukowicz.github.pagination.GitHubPageRequest;
import io.github.piotr_janczukowicz.github.pagination.GitHubPageRequestKindValidators;
import io.github.piotr_janczukowicz.json.JsonTypes;
import io.github.piotr_janczukowicz.uri.PathTemplate;
import java.io.IOException;
import java.net.URI;
import java.util.Optional;
import org.jspecify.annotations.Nullable;

/** GitHub Enterprise Cloud token inventory REST resource for API version {@code 2026-03-10}. */
public final class GitHubEnterpriseAdminTokenInventoryApi extends GitHubResourceApi {

    private static final String DEPRECATED_SINCE = "2026-09-29";

    private static final PathTemplate CREDENTIALS = PathTemplate.of("/enterprises/{enterprise}/credentials");
    private static final PathTemplate CREDENTIAL =
            PathTemplate.of("/enterprises/{enterprise}/credentials/{inventory_id}");
    private static final PathTemplate EXPORTS = PathTemplate.of("/enterprises/{enterprise}/credentials/exports");
    private static final PathTemplate EXPORT =
            PathTemplate.of("/enterprises/{enterprise}/credentials/exports/{export_id}");

    private static final GitHubListSpec<GitHubEnterpriseTokenInventoryItem> CREDENTIALS_SPEC = GitHubListSpec.array(
            GitHubPageRequestKindValidators.CURSOR, JsonTypes.listOf(GitHubEnterpriseTokenInventoryItem.class));

    /**
     * Token inventory resource over {@code caller}.
     *
     * @param caller executes and decodes the calls of this resource
     * @throws NullPointerException if {@code caller} is {@code null}
     */
    public GitHubEnterpriseAdminTokenInventoryApi(final GitHubCaller caller) {
        super(caller);
    }

    /**
     * List the credentials of an enterprise token inventory ({@code GET /enterprises/{enterprise}/credentials}) with no
     * filter.
     *
     * <p>Uses {@link GitHubPageRequest#byCursor()} and an empty filter. See
     * {@link #listEnterpriseTokenInventory(String, GitHubListEnterpriseTokenInventoryFilter, GitHubPageRequest)}.
     *
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHEC}
     * @deprecated Closing down: use the asynchronous token inventory export instead. Removed on 2026-10-02.
     * @param enterprise the slug version of the enterprise name
     * @return first HTTP page of token inventory items
     * @throws IllegalArgumentException if {@code enterprise} is empty
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    @Deprecated(since = DEPRECATED_SINCE)
    @Flavors(GitHubFlavor.GHEC)
    public GitHubPage<GitHubEnterpriseTokenInventoryItem> listEnterpriseTokenInventory(final String enterprise)
            throws IOException {
        return listEnterpriseTokenInventory(
                enterprise, GitHubListEnterpriseTokenInventoryFilter.empty(), GitHubPageRequest.byCursor());
    }

    /**
     * List the credentials of an enterprise token inventory ({@code GET /enterprises/{enterprise}/credentials}).
     *
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHEC}
     * @deprecated Closing down: use the asynchronous token inventory export instead. Removed on 2026-10-02.
     * @param enterprise the slug version of the enterprise name
     * @param filter query filters
     * @param request cursor pagination token
     * @return page of token inventory items
     * @throws IllegalArgumentException if {@code enterprise} is empty, or {@code request} is a page variant
     * @throws NullPointerException if {@code filter} or {@code request} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    @Deprecated(since = DEPRECATED_SINCE)
    @Flavors(GitHubFlavor.GHEC)
    public GitHubPage<GitHubEnterpriseTokenInventoryItem> listEnterpriseTokenInventory(
            final String enterprise,
            final GitHubListEnterpriseTokenInventoryFilter filter,
            final GitHubPageRequest request)
            throws IOException {
        requireFlavor(GitHubFlavor.GHEC);
        requireNonNull(filter, PARAM_FILTER);
        requireNonNull(request, PARAM_REQUEST);

        return list(CREDENTIALS, filter.toQueryParameters(), request, CREDENTIALS_SPEC, enterprise);
    }

    /**
     * Create an asynchronous export of the enterprise token inventory ({@code POST
     * /enterprises/{enterprise}/credentials/exports}).
     *
     * @param enterprise the slug version of the enterprise name
     * @param payload optional filters that scope the export, or {@code null} to send no body
     * @return the export job
     * @throws IllegalArgumentException if {@code enterprise} is empty
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHEC}
     */
    @Flavors(GitHubFlavor.GHEC)
    public GitHubEnterpriseTokenInventoryExport createEnterpriseTokenInventoryExport(
            final String enterprise, final @Nullable GitHubCreateEnterpriseTokenInventoryExportPayload payload)
            throws IOException {
        requireFlavor(GitHubFlavor.GHEC);
        return caller.fetch(
                caller.jsonIfPresent(post(EXPORTS, enterprise), payload), GitHubEnterpriseTokenInventoryExport.class);
    }

    /**
     * Get the status of a token inventory export ({@code GET
     * /enterprises/{enterprise}/credentials/exports/{export_id}}). When the export is ready the endpoint answers with a
     * redirect; see {@link #getEnterpriseTokenInventoryExportLocation(String, String)}.
     *
     * @param enterprise the slug version of the enterprise name
     * @param exportId the opaque id of the export
     * @return the export job status, or empty on HTTP 404
     * @throws IllegalArgumentException if a path argument is empty
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHEC}
     */
    @Flavors(GitHubFlavor.GHEC)
    public Optional<GitHubEnterpriseTokenInventoryExport> getEnterpriseTokenInventoryExport(
            final String enterprise, final String exportId) throws IOException {
        requireFlavor(GitHubFlavor.GHEC);
        return caller.fetchOrEmpty(get(EXPORT, enterprise, exportId), GitHubEnterpriseTokenInventoryExport.class);
    }

    /**
     * Get the short-lived download URL of a ready token inventory export ({@code GET
     * /enterprises/{enterprise}/credentials/exports/{export_id}}, HTTP 302).
     *
     * @param enterprise the slug version of the enterprise name
     * @param exportId the opaque id of the export
     * @return the download location the endpoint redirects to. It may embed a signature: do not log it or send
     *     credentials to it
     * @throws IllegalArgumentException if a path argument is empty
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is not a redirect
     * @throws IllegalStateException if the response is successful (2xx) rather than a redirect
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHEC}
     */
    @Flavors(GitHubFlavor.GHEC)
    public URI getEnterpriseTokenInventoryExportLocation(final String enterprise, final String exportId)
            throws IOException {
        requireFlavor(GitHubFlavor.GHEC);
        return caller.redirectLocation(get(EXPORT, enterprise, exportId));
    }

    /**
     * Get a credential or GitHub App installation of an enterprise token inventory ({@code GET
     * /enterprises/{enterprise}/credentials/{inventory_id}}).
     *
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHEC}
     * @deprecated Closing down: use the asynchronous token inventory export instead. Removed on 2026-10-02.
     * @param enterprise the slug version of the enterprise name
     * @param inventoryId the opaque inventory id returned by the list endpoint
     * @return token inventory item, or empty on HTTP 404
     * @throws IllegalArgumentException if a path argument is empty
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    @Deprecated(since = DEPRECATED_SINCE)
    @Flavors(GitHubFlavor.GHEC)
    public Optional<GitHubEnterpriseTokenInventoryItem> getEnterpriseTokenInventoryItem(
            final String enterprise, final String inventoryId) throws IOException {
        requireFlavor(GitHubFlavor.GHEC);
        return caller.fetchOrEmpty(get(CREDENTIAL, enterprise, inventoryId), GitHubEnterpriseTokenInventoryItem.class);
    }
}
