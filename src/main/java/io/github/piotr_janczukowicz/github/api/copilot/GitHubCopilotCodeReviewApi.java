package io.github.piotr_janczukowicz.github.api.copilot;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubCaller;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.github.client.GitHubResourceApi;
import io.github.piotr_janczukowicz.github.client.exception.GitHubApiException;
import io.github.piotr_janczukowicz.uri.PathTemplate;
import java.io.IOException;

/** GitHub Copilot code review REST resource for API version {@code 2026-03-10}. */
@SuppressWarnings("PMD.LinguisticNaming")
public final class GitHubCopilotCodeReviewApi extends GitHubResourceApi {

    private static final PathTemplate ENTERPRISE_CODE_REVIEW_POLICY =
            PathTemplate.of("/enterprises/{enterprise}/copilot/policies/code-review");
    private static final PathTemplate ORG_CODE_REVIEW_RUNNER =
            PathTemplate.of("/orgs/{org}/copilot/code-review/runner");
    private static final PathTemplate ORG_CODE_REVIEW_SETTINGS =
            PathTemplate.of("/orgs/{org}/copilot/code-review/settings");
    private static final PathTemplate ORG_CODE_REVIEW_POLICY =
            PathTemplate.of("/orgs/{org}/copilot/policies/code-review");
    private static final PathTemplate REPO_CODE_REVIEW_SETTINGS =
            PathTemplate.of("/repos/{owner}/{repo}/copilot/code-review/settings");

    /**
     * Copilot code review resource over {@code caller}.
     *
     * @param caller executes and decodes the calls of this resource
     * @throws NullPointerException if {@code caller} is {@code null}
     */
    public GitHubCopilotCodeReviewApi(final GitHubCaller caller) {
        super(caller);
    }

    /**
     * Get the Copilot code review policy for an enterprise ({@code GET
     * /enterprises/{enterprise}/copilot/policies/code-review}).
     *
     * @param enterprise enterprise slug
     * @return configured enterprise policy
     * @throws IllegalArgumentException if {@code enterprise} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public GitHubEnterpriseCopilotCodeReviewPolicy getEnterprisePolicy(final String enterprise) throws IOException {
        requireFlavor(GitHubFlavor.CLOUD, GitHubFlavor.GHEC);
        return caller.fetch(
                get(ENTERPRISE_CODE_REVIEW_POLICY, enterprise), GitHubEnterpriseCopilotCodeReviewPolicy.class);
    }

    /**
     * Set the Copilot code review policy for an enterprise ({@code PUT
     * /enterprises/{enterprise}/copilot/policies/code-review}).
     *
     * @param enterprise enterprise slug
     * @param payload policy state to set
     * @throws IllegalArgumentException if {@code enterprise} is blank
     * @throws NullPointerException if {@code payload} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public void setEnterprisePolicy(final String enterprise, final GitHubSetEnterpriseCodeReviewPolicyPayload payload)
            throws IOException {
        requireFlavor(GitHubFlavor.CLOUD, GitHubFlavor.GHEC);
        requireNonNull(payload, PARAM_PAYLOAD);

        caller.send(caller.json(put(ENTERPRISE_CODE_REVIEW_POLICY, enterprise), payload));
    }

    /**
     * Get the Copilot code review runner configuration for an organization ({@code GET
     * /orgs/{org}/copilot/code-review/runner}).
     *
     * @param org organization name
     * @return effective runner configuration
     * @throws IllegalArgumentException if {@code org} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public GitHubOrganizationCopilotCodeReviewRunner getOrganizationRunner(final String org) throws IOException {
        requireFlavor(GitHubFlavor.CLOUD, GitHubFlavor.GHEC);
        return caller.fetch(get(ORG_CODE_REVIEW_RUNNER, org), GitHubOrganizationCopilotCodeReviewRunner.class);
    }

    /**
     * Set the Copilot code review runner configuration for an organization ({@code PUT
     * /orgs/{org}/copilot/code-review/runner}).
     *
     * @param org organization name
     * @param payload runner configuration to set
     * @return effective runner configuration after the update
     * @throws IllegalArgumentException if {@code org} is blank
     * @throws NullPointerException if {@code payload} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public GitHubOrganizationCopilotCodeReviewRunner setOrganizationRunner(
            final String org, final GitHubSetOrganizationCodeReviewRunnerPayload payload) throws IOException {
        requireFlavor(GitHubFlavor.CLOUD, GitHubFlavor.GHEC);
        requireNonNull(payload, PARAM_PAYLOAD);

        return caller.fetch(
                caller.json(put(ORG_CODE_REVIEW_RUNNER, org), payload),
                GitHubOrganizationCopilotCodeReviewRunner.class);
    }

    /**
     * Get Copilot code review settings for an organization ({@code GET /orgs/{org}/copilot/code-review/settings}).
     *
     * @param org organization name
     * @return organization settings
     * @throws IllegalArgumentException if {@code org} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public GitHubOrganizationCopilotCodeReviewSettings getOrganizationSettings(final String org) throws IOException {
        requireFlavor(GitHubFlavor.CLOUD, GitHubFlavor.GHEC);
        return caller.fetch(get(ORG_CODE_REVIEW_SETTINGS, org), GitHubOrganizationCopilotCodeReviewSettings.class);
    }

    /**
     * Update Copilot code review settings for an organization ({@code PATCH /orgs/{org}/copilot/code-review/settings}).
     *
     * @param org organization name
     * @param payload settings changes
     * @return organization settings after the update
     * @throws IllegalArgumentException if {@code org} is blank
     * @throws NullPointerException if {@code payload} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public GitHubOrganizationCopilotCodeReviewSettings updateOrganizationSettings(
            final String org, final GitHubUpdateOrganizationCodeReviewSettingsPayload payload) throws IOException {
        requireFlavor(GitHubFlavor.CLOUD, GitHubFlavor.GHEC);
        requireNonNull(payload, PARAM_PAYLOAD);

        return caller.fetch(
                caller.json(patch(ORG_CODE_REVIEW_SETTINGS, org), payload),
                GitHubOrganizationCopilotCodeReviewSettings.class);
    }

    /**
     * Get the Copilot code review policy for an organization ({@code GET /orgs/{org}/copilot/policies/code-review}).
     *
     * @param org organization name
     * @return configured and effective policy
     * @throws IllegalArgumentException if {@code org} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public GitHubOrganizationCopilotCodeReviewPolicy getOrganizationPolicy(final String org) throws IOException {
        requireFlavor(GitHubFlavor.CLOUD, GitHubFlavor.GHEC);
        return caller.fetch(get(ORG_CODE_REVIEW_POLICY, org), GitHubOrganizationCopilotCodeReviewPolicy.class);
    }

    /**
     * Set the Copilot code review policy for an organization ({@code PUT /orgs/{org}/copilot/policies/code-review}).
     *
     * @param org organization name
     * @param payload policy state to set
     * @throws IllegalArgumentException if {@code org} is blank
     * @throws NullPointerException if {@code payload} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public void setOrganizationPolicy(final String org, final GitHubSetOrganizationCodeReviewPolicyPayload payload)
            throws IOException {
        requireFlavor(GitHubFlavor.CLOUD, GitHubFlavor.GHEC);
        requireNonNull(payload, PARAM_PAYLOAD);

        caller.send(caller.json(put(ORG_CODE_REVIEW_POLICY, org), payload));
    }

    /**
     * Get Copilot code review settings for a repository ({@code GET
     * /repos/{owner}/{repo}/copilot/code-review/settings}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @return repository settings
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public GitHubRepositoryCopilotCodeReviewSettings getRepositorySettings(final String owner, final String repo)
            throws IOException {
        requireFlavor(GitHubFlavor.CLOUD, GitHubFlavor.GHEC);
        return caller.fetch(
                get(REPO_CODE_REVIEW_SETTINGS, owner, repo), GitHubRepositoryCopilotCodeReviewSettings.class);
    }

    /**
     * Update Copilot code review settings for a repository ({@code PATCH
     * /repos/{owner}/{repo}/copilot/code-review/settings}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param payload settings changes
     * @return repository settings after the update
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws NullPointerException if {@code payload} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public GitHubRepositoryCopilotCodeReviewSettings updateRepositorySettings(
            final String owner, final String repo, final GitHubUpdateRepositoryCodeReviewSettingsPayload payload)
            throws IOException {
        requireFlavor(GitHubFlavor.CLOUD, GitHubFlavor.GHEC);
        requireNonNull(payload, PARAM_PAYLOAD);

        return caller.fetch(
                caller.json(patch(REPO_CODE_REVIEW_SETTINGS, owner, repo), payload),
                GitHubRepositoryCopilotCodeReviewSettings.class);
    }
}
