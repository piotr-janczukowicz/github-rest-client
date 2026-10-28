package io.github.piotr_janczukowicz.github.api.billing;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubCaller;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.github.client.GitHubResourceApi;
import io.github.piotr_janczukowicz.github.client.exception.GitHubApiException;
import io.github.piotr_janczukowicz.uri.PathTemplate;
import java.io.IOException;
import java.util.Optional;

/** GitHub Enterprise Cloud billing usage report exports REST resource for API version {@code 2026-03-10}. */
public final class GitHubBillingUsageReportsApi extends GitHubResourceApi {

    private static final PathTemplate ENTERPRISE_REPORTS =
            PathTemplate.of("/enterprises/{enterprise}/settings/billing/reports");
    private static final PathTemplate ENTERPRISE_REPORT =
            PathTemplate.of("/enterprises/{enterprise}/settings/billing/reports/{report_id}");

    /**
     * Billing usage report exports resource over {@code caller}.
     *
     * @param caller executes and decodes the calls of this resource
     * @throws NullPointerException if {@code caller} is {@code null}
     */
    public GitHubBillingUsageReportsApi(final GitHubCaller caller) {
        super(caller);
    }

    /**
     * List usage report exports for an enterprise ({@code GET /enterprises/{enterprise}/settings/billing/reports}).
     *
     * @param enterprise the slug version of the enterprise name
     * @return usage report exports
     * @throws IllegalArgumentException if {@code enterprise} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHEC}
     */
    @Flavors(GitHubFlavor.GHEC)
    public GitHubUsageReportExportList listUsageReportExports(final String enterprise) throws IOException {
        requireFlavor(GitHubFlavor.GHEC);
        return caller.fetch(get(ENTERPRISE_REPORTS, enterprise), GitHubUsageReportExportList.class);
    }

    /**
     * Request a usage report export for an enterprise ({@code POST
     * /enterprises/{enterprise}/settings/billing/reports}).
     *
     * @param enterprise the slug version of the enterprise name
     * @param payload report export to request
     * @return accepted usage report export
     * @throws IllegalArgumentException if {@code enterprise} is blank
     * @throws NullPointerException if {@code payload} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHEC}
     */
    @Flavors(GitHubFlavor.GHEC)
    public GitHubUsageReportExport createUsageReportExport(
            final String enterprise, final GitHubCreateUsageReportExportPayload payload) throws IOException {
        requireFlavor(GitHubFlavor.GHEC);
        requireNonNull(payload, PARAM_PAYLOAD);

        return caller.fetch(caller.json(post(ENTERPRISE_REPORTS, enterprise), payload), GitHubUsageReportExport.class);
    }

    /**
     * Get a usage report export for an enterprise ({@code GET
     * /enterprises/{enterprise}/settings/billing/reports/{report_id}}).
     *
     * @param enterprise the slug version of the enterprise name
     * @param reportId usage report export ID (UUID)
     * @return usage report export, or empty on HTTP 404
     * @throws IllegalArgumentException if {@code enterprise} or {@code reportId} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHEC}
     */
    @Flavors(GitHubFlavor.GHEC)
    public Optional<GitHubUsageReportExport> getUsageReportExport(final String enterprise, final String reportId)
            throws IOException {
        requireFlavor(GitHubFlavor.GHEC);
        return caller.fetchOrEmpty(get(ENTERPRISE_REPORT, enterprise, reportId), GitHubUsageReportExport.class);
    }
}
