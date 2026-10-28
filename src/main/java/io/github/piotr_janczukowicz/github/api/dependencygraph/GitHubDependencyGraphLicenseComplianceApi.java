package io.github.piotr_janczukowicz.github.api.dependencygraph;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubCaller;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.github.client.GitHubResourceApi;
import io.github.piotr_janczukowicz.github.client.exception.GitHubApiException;
import io.github.piotr_janczukowicz.uri.PathTemplate;
import java.io.IOException;
import java.util.Optional;

/**
 * GitHub dependency graph license compliance REST resource for API version {@code 2026-03-10}.
 *
 * <p>Read and set the enterprise license policy and check packages of a repository against it.
 */
public final class GitHubDependencyGraphLicenseComplianceApi extends GitHubResourceApi {

    private static final PathTemplate ENTERPRISE_LICENSE_POLICY =
            PathTemplate.of("/enterprises/{enterprise}/license-compliance/policies/{policy_id}");
    private static final PathTemplate REPO_PACKAGE_CHECKS =
            PathTemplate.of("/repos/{owner}/{repo}/license-compliance/package-checks");

    /**
     * License compliance resource over {@code caller}.
     *
     * @param caller executes and decodes the calls of this resource
     * @throws NullPointerException if {@code caller} is {@code null}
     */
    public GitHubDependencyGraphLicenseComplianceApi(final GitHubCaller caller) {
        super(caller);
    }

    /**
     * Get an enterprise license policy ({@code GET /enterprises/{enterprise}/license-compliance/policies/{policy_id}}).
     *
     * @param enterprise enterprise slug
     * @param policyId policy identifier
     * @return the license policy, or empty if GitHub has no such policy
     * @throws IllegalArgumentException if {@code enterprise} or {@code policyId} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful (HTTP 404 yields empty, not thrown)
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHEC}
     */
    @Flavors(GitHubFlavor.GHEC)
    public Optional<GitHubEnterpriseLicensePolicyResponse> getEnterpriseLicensePolicy(
            final String enterprise, final String policyId) throws IOException {
        requireFlavor(GitHubFlavor.GHEC);
        return caller.fetchOrEmpty(
                get(ENTERPRISE_LICENSE_POLICY, enterprise, policyId), GitHubEnterpriseLicensePolicyResponse.class);
    }

    /**
     * Set an enterprise license policy ({@code PUT /enterprises/{enterprise}/license-compliance/policies/{policy_id}}).
     *
     * @param enterprise enterprise slug
     * @param policyId policy identifier
     * @param payload the licenses and package rules of the policy
     * @throws IllegalArgumentException if {@code enterprise} or {@code policyId} is blank
     * @throws NullPointerException if {@code payload} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHEC}
     */
    @Flavors(GitHubFlavor.GHEC)
    public void setEnterpriseLicensePolicy(
            final String enterprise, final String policyId, final GitHubEnterpriseLicensePolicyItem payload)
            throws IOException {
        requireFlavor(GitHubFlavor.GHEC);
        requireNonNull(payload, PARAM_PAYLOAD);

        caller.send(caller.json(put(ENTERPRISE_LICENSE_POLICY, enterprise, policyId), payload));
    }

    /**
     * Check packages for license compliance ({@code POST /repos/{owner}/{repo}/license-compliance/package-checks}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param payload package URLs to check, at most 40
     * @return license compliance result for each package URL
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws NullPointerException if {@code payload} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHEC}
     */
    @Flavors(GitHubFlavor.GHEC)
    public GitHubCheckLicenseComplianceForPackagesResponse checkLicenseComplianceForPackages(
            final String owner, final String repo, final GitHubCheckLicenseComplianceForPackagesPayload payload)
            throws IOException {
        requireFlavor(GitHubFlavor.GHEC);
        requireNonNull(payload, PARAM_PAYLOAD);

        return caller.fetch(
                caller.json(post(REPO_PACKAGE_CHECKS, owner, repo), payload),
                GitHubCheckLicenseComplianceForPackagesResponse.class);
    }
}
