package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubCaller;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.github.client.GitHubResourceApi;
import io.github.piotr_janczukowicz.github.client.exception.GitHubApiException;
import io.github.piotr_janczukowicz.uri.PathTemplate;
import java.io.IOException;
import java.util.Optional;

/** GitHub Enterprise Cloud innersource vulnerability sync REST resource for API version {@code 2026-03-10}. */
public final class GitHubEnterpriseAdminSecurityAdvisoriesApi extends GitHubResourceApi {

    private static final PathTemplate SYNC =
            PathTemplate.of("/enterprises/{enterprise}/innersource-vulnerabilities/sync");
    private static final PathTemplate SYNC_STATUS =
            PathTemplate.of("/enterprises/{enterprise}/innersource-vulnerabilities/sync/status/{job_id}");

    /**
     * Innersource vulnerability sync resource over {@code caller}.
     *
     * @param caller executes and decodes the calls of this resource
     * @throws NullPointerException if {@code caller} is {@code null}
     */
    public GitHubEnterpriseAdminSecurityAdvisoriesApi(final GitHubCaller caller) {
        super(caller);
    }

    /**
     * Synchronize innersource vulnerabilities of an enterprise ({@code POST
     * /enterprises/{enterprise}/innersource-vulnerabilities/sync}). The sync runs asynchronously; poll the job with
     * {@link #getEnterpriseInnersourceVulnerabilitySyncStatus(String, String)}.
     *
     * @param enterprise the slug version of the enterprise name
     * @param payload the vulnerabilities in OSV format to synchronize
     * @return the accepted sync job
     * @throws NullPointerException if {@code payload} is {@code null}
     * @throws IllegalArgumentException if {@code enterprise} is empty
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHEC}
     */
    @Flavors(GitHubFlavor.GHEC)
    public GitHubExternalVulnerabilitySyncAccepted syncEnterpriseInnersourceVulnerabilities(
            final String enterprise, final GitHubSyncEnterpriseInnersourceVulnerabilitiesPayload payload)
            throws IOException {
        requireFlavor(GitHubFlavor.GHEC);
        requireNonNull(payload, PARAM_PAYLOAD);

        return caller.fetch(
                caller.json(post(SYNC, enterprise), payload), GitHubExternalVulnerabilitySyncAccepted.class);
    }

    /**
     * Get the status of an innersource vulnerability sync job ({@code GET
     * /enterprises/{enterprise}/innersource-vulnerabilities/sync/status/{job_id}}). The body is the completed result or
     * the error (HTTP 200), or the in-progress status (HTTP 202).
     *
     * @param enterprise the slug version of the enterprise name
     * @param jobId the unique identifier of the sync job
     * @return the sync job status, or empty on HTTP 404
     * @throws IllegalArgumentException if a path argument is empty
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHEC}
     */
    @Flavors(GitHubFlavor.GHEC)
    public Optional<GitHubGetEnterpriseInnersourceVulnerabilitySyncStatusResponse>
            getEnterpriseInnersourceVulnerabilitySyncStatus(final String enterprise, final String jobId)
                    throws IOException {
        requireFlavor(GitHubFlavor.GHEC);
        return caller.fetchOrEmpty(
                get(SYNC_STATUS, enterprise, jobId),
                GitHubGetEnterpriseInnersourceVulnerabilitySyncStatusResponse.class);
    }
}
