package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubCaller;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.github.client.GitHubResourceApi;
import io.github.piotr_janczukowicz.github.client.exception.GitHubApiException;
import io.github.piotr_janczukowicz.uri.PathTemplate;
import java.io.IOException;

/** GitHub Enterprise Cloud code security and analysis REST resource for API version {@code 2026-03-10}. */
public final class GitHubEnterpriseAdminCodeSecurityAndAnalysisApi extends GitHubResourceApi {

    private static final String DEPRECATED_SINCE = "2024-09-27";

    private static final PathTemplate SECURITY_ANALYSIS =
            PathTemplate.of("/enterprises/{enterprise}/code_security_and_analysis");
    private static final PathTemplate SECURITY_PRODUCT_ENABLEMENT =
            PathTemplate.of("/enterprises/{enterprise}/{security_product}/{enablement}");

    /**
     * Code security and analysis resource over {@code caller}.
     *
     * @param caller executes and decodes the calls of this resource
     * @throws NullPointerException if {@code caller} is {@code null}
     */
    public GitHubEnterpriseAdminCodeSecurityAndAnalysisApi(final GitHubCaller caller) {
        super(caller);
    }

    /**
     * Get code security and analysis features for an enterprise ({@code GET
     * /enterprises/{enterprise}/code_security_and_analysis}).
     *
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHEC} or
     *     {@code GHES}
     * @deprecated Closing down: use code security configurations instead.
     * @param enterprise the slug version of the enterprise name
     * @return the enterprise security analysis settings
     * @throws IllegalArgumentException if {@code enterprise} is empty
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    @Deprecated(since = DEPRECATED_SINCE)
    @Flavors({GitHubFlavor.GHEC, GitHubFlavor.GHES})
    public GitHubEnterpriseSecurityAnalysisSettings getSecurityAnalysisSettingsForEnterprise(final String enterprise)
            throws IOException {
        requireFlavor(GitHubFlavor.GHEC, GitHubFlavor.GHES);
        return caller.fetch(get(SECURITY_ANALYSIS, enterprise), GitHubEnterpriseSecurityAnalysisSettings.class);
    }

    /**
     * Update code security and analysis features for an enterprise ({@code PATCH
     * /enterprises/{enterprise}/code_security_and_analysis}).
     *
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHEC} or
     *     {@code GHES}
     * @deprecated Closing down: use code security configurations instead.
     * @param enterprise the slug version of the enterprise name
     * @param payload the settings to update
     * @throws NullPointerException if {@code payload} is {@code null}
     * @throws IllegalArgumentException if {@code enterprise} is empty
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    @Deprecated(since = DEPRECATED_SINCE)
    @Flavors({GitHubFlavor.GHEC, GitHubFlavor.GHES})
    public void patchSecurityAnalysisSettingsForEnterprise(
            final String enterprise, final GitHubPatchSecurityAnalysisSettingsForEnterprisePayload payload)
            throws IOException {
        requireFlavor(GitHubFlavor.GHEC, GitHubFlavor.GHES);
        requireNonNull(payload, PARAM_PAYLOAD);

        caller.send(caller.json(patch(SECURITY_ANALYSIS, enterprise), payload));
    }

    /**
     * Enable or disable a security feature for all repositories of an enterprise ({@code POST
     * /enterprises/{enterprise}/{security_product}/{enablement}}).
     *
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHEC} or
     *     {@code GHES}
     * @deprecated Closing down: use code security configurations instead.
     * @param enterprise the slug version of the enterprise name
     * @param securityProduct the security feature to change
     * @param enablement the action to take
     * @throws NullPointerException if {@code securityProduct} or {@code enablement} is {@code null}
     * @throws IllegalArgumentException if {@code enterprise} is empty
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    @Deprecated(since = DEPRECATED_SINCE)
    @Flavors({GitHubFlavor.GHEC, GitHubFlavor.GHES})
    public void postSecurityProductEnablementForEnterprise(
            final String enterprise,
            final GitHubEnterpriseSecurityProduct securityProduct,
            final GitHubEnterpriseSecurityProductEnablement enablement)
            throws IOException {
        requireFlavor(GitHubFlavor.GHEC, GitHubFlavor.GHES);
        requireNonNull(securityProduct, "securityProduct");
        requireNonNull(enablement, "enablement");

        caller.send(post(SECURITY_PRODUCT_ENABLEMENT, enterprise, securityProduct.wireValue(), enablement.wireValue()));
    }
}
