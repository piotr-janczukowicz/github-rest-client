package io.github.piotr_janczukowicz.github.api.secretscanning;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubCaller;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.github.client.GitHubResourceApi;
import io.github.piotr_janczukowicz.github.client.exception.GitHubApiException;
import io.github.piotr_janczukowicz.uri.PathTemplate;
import java.io.IOException;

/**
 * GitHub Secret Scanning push protection REST resource for API version {@code 2026-03-10}.
 *
 * <p>Manage push protection pattern configurations for organizations and enterprises.
 */
public final class GitHubSecretScanningPushProtectionApi extends GitHubResourceApi {

    private static final PathTemplate SECRET_SCANNING_PATTERN_CONFIGURATIONS =
            PathTemplate.of("/orgs/{org}/secret-scanning/pattern-configurations");
    private static final PathTemplate ENTERPRISE_SECRET_SCANNING_PATTERN_CONFIGURATIONS =
            PathTemplate.of("/enterprises/{enterprise}/secret-scanning/pattern-configurations");

    /**
     * Secret scanning push protection resource over {@code caller}.
     *
     * @param caller executes and decodes the calls of this resource
     * @throws NullPointerException if {@code caller} is {@code null}
     */
    public GitHubSecretScanningPushProtectionApi(final GitHubCaller caller) {
        super(caller);
    }

    /**
     * List organization pattern configurations ({@code GET /orgs/{org}/secret-scanning/pattern-configurations}).
     *
     * @param org organization login
     * @return pattern configuration
     * @throws IllegalArgumentException if {@code org} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is HTTP 404
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubSecretScanningPatternConfiguration listOrgPatternConfigs(final String org) throws IOException {
        return caller.fetch(
                get(SECRET_SCANNING_PATTERN_CONFIGURATIONS, org), GitHubSecretScanningPatternConfiguration.class);
    }

    /**
     * Update organization pattern configurations ({@code PATCH /orgs/{org}/secret-scanning/pattern-configurations}).
     *
     * @param org organization login
     * @param payload pattern settings to update
     * @return updated pattern configuration version
     * @throws IllegalArgumentException if {@code org} is blank
     * @throws NullPointerException if {@code payload} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is HTTP 404
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubUpdateSecretScanningPatternConfigsResult updateOrgPatternConfigs(
            final String org, final GitHubUpdateSecretScanningPatternConfigsPayload payload) throws IOException {
        requireNonNull(payload, PARAM_PAYLOAD);

        return caller.fetch(
                caller.json(patch(SECRET_SCANNING_PATTERN_CONFIGURATIONS, org), payload),
                GitHubUpdateSecretScanningPatternConfigsResult.class);
    }

    /**
     * List enterprise pattern configurations ({@code GET
     * /enterprises/{enterprise}/secret-scanning/pattern-configurations}).
     *
     * @param enterprise enterprise slug
     * @return pattern configuration
     * @throws IllegalArgumentException if {@code enterprise} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHEC} or
     *     {@code GHES}
     */
    @Flavors({GitHubFlavor.GHEC, GitHubFlavor.GHES})
    public GitHubSecretScanningPatternConfiguration listEnterprisePatternConfigs(final String enterprise)
            throws IOException {
        requireFlavor(GitHubFlavor.GHEC, GitHubFlavor.GHES);
        return caller.fetch(
                get(ENTERPRISE_SECRET_SCANNING_PATTERN_CONFIGURATIONS, enterprise),
                GitHubSecretScanningPatternConfiguration.class);
    }

    /**
     * Update enterprise pattern configurations ({@code PATCH
     * /enterprises/{enterprise}/secret-scanning/pattern-configurations}).
     *
     * @param enterprise enterprise slug
     * @param payload pattern settings to update
     * @return updated pattern configuration version
     * @throws IllegalArgumentException if {@code enterprise} is blank
     * @throws NullPointerException if {@code payload} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is HTTP 404
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHEC} or
     *     {@code GHES}
     */
    @Flavors({GitHubFlavor.GHEC, GitHubFlavor.GHES})
    public GitHubUpdateEnterprisePatternConfigsResult updateEnterprisePatternConfigs(
            final String enterprise, final GitHubUpdateEnterprisePatternConfigsPayload payload) throws IOException {
        requireFlavor(GitHubFlavor.GHEC, GitHubFlavor.GHES);
        requireNonNull(payload, PARAM_PAYLOAD);

        return caller.fetch(
                caller.json(patch(ENTERPRISE_SECRET_SCANNING_PATTERN_CONFIGURATIONS, enterprise), payload),
                GitHubUpdateEnterprisePatternConfigsResult.class);
    }
}
