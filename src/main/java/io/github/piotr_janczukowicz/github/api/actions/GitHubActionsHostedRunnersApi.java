package io.github.piotr_janczukowicz.github.api.actions;

import static io.github.piotr_janczukowicz.github.api.GitHubSchemaPatterns.CUSTOM_IMAGE_VERSION;
import static io.github.piotr_janczukowicz.util.ValidationUtils.requireMatches;
import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;
import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNotBlank;

import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubCaller;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
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

/** GitHub-hosted runners REST resource for API version {@code 2026-03-10}. */
public final class GitHubActionsHostedRunnersApi extends GitHubResourceApi {

    private static final PathTemplate ORG_HOSTED_RUNNERS = PathTemplate.of("/orgs/{org}/actions/hosted-runners");
    private static final PathTemplate ORG_HOSTED_RUNNER =
            PathTemplate.of("/orgs/{org}/actions/hosted-runners/{hosted_runner_id}");
    private static final PathTemplate ORG_CUSTOM_IMAGES =
            PathTemplate.of("/orgs/{org}/actions/hosted-runners/images/custom");
    private static final PathTemplate ORG_CUSTOM_IMAGE =
            PathTemplate.of("/orgs/{org}/actions/hosted-runners/images/custom/{image_definition_id}");
    private static final PathTemplate ORG_CUSTOM_IMAGE_VERSIONS =
            PathTemplate.of("/orgs/{org}/actions/hosted-runners/images/custom/{image_definition_id}/versions");
    private static final PathTemplate ORG_CUSTOM_IMAGE_VERSION = PathTemplate.of(
            "/orgs/{org}/actions/hosted-runners/images/custom/{image_definition_id}/versions/{version}");
    private static final PathTemplate ORG_GITHUB_OWNED_IMAGES =
            PathTemplate.of("/orgs/{org}/actions/hosted-runners/images/github-owned");
    private static final PathTemplate ORG_PARTNER_IMAGES =
            PathTemplate.of("/orgs/{org}/actions/hosted-runners/images/partner");
    private static final PathTemplate ORG_LIMITS = PathTemplate.of("/orgs/{org}/actions/hosted-runners/limits");
    private static final PathTemplate ORG_MACHINE_SIZES =
            PathTemplate.of("/orgs/{org}/actions/hosted-runners/machine-sizes");
    private static final PathTemplate ORG_PLATFORMS = PathTemplate.of("/orgs/{org}/actions/hosted-runners/platforms");
    private static final PathTemplate ENTERPRISE_HOSTED_RUNNERS =
            PathTemplate.of("/enterprises/{enterprise}/actions/hosted-runners");
    private static final PathTemplate ENTERPRISE_HOSTED_RUNNER =
            PathTemplate.of("/enterprises/{enterprise}/actions/hosted-runners/{hosted_runner_id}");
    private static final PathTemplate ENTERPRISE_CUSTOM_IMAGES =
            PathTemplate.of("/enterprises/{enterprise}/actions/hosted-runners/images/custom");
    private static final PathTemplate ENTERPRISE_CUSTOM_IMAGE =
            PathTemplate.of("/enterprises/{enterprise}/actions/hosted-runners/images/custom/{image_definition_id}");
    private static final PathTemplate ENTERPRISE_CUSTOM_IMAGE_VERSIONS = PathTemplate.of(
            "/enterprises/{enterprise}/actions/hosted-runners/images/custom/{image_definition_id}/versions");
    private static final PathTemplate ENTERPRISE_CUSTOM_IMAGE_VERSION = PathTemplate.of(
            "/enterprises/{enterprise}/actions/hosted-runners/images/custom/{image_definition_id}/versions/{version}");
    private static final PathTemplate ENTERPRISE_GITHUB_OWNED_IMAGES =
            PathTemplate.of("/enterprises/{enterprise}/actions/hosted-runners/images/github-owned");
    private static final PathTemplate ENTERPRISE_PARTNER_IMAGES =
            PathTemplate.of("/enterprises/{enterprise}/actions/hosted-runners/images/partner");
    private static final PathTemplate ENTERPRISE_LIMITS =
            PathTemplate.of("/enterprises/{enterprise}/actions/hosted-runners/limits");
    private static final PathTemplate ENTERPRISE_MACHINE_SIZES =
            PathTemplate.of("/enterprises/{enterprise}/actions/hosted-runners/machine-sizes");
    private static final PathTemplate ENTERPRISE_PLATFORMS =
            PathTemplate.of("/enterprises/{enterprise}/actions/hosted-runners/platforms");

    private static final String PARAM_VERSION = "version";

    private static final GitHubListSpec<GitHubActionsHostedRunner> RUNNERS_SPEC = GitHubListSpec.wrapper(
            GitHubPageRequestKindValidators.PAGE,
            GitHubActionsHostedRunners.class,
            GitHubActionsHostedRunners::runners,
            GitHubActionsHostedRunners::totalCount);

    private static final GitHubListSpec<GitHubActionsHostedRunner> ENTERPRISE_RUNNERS_SPEC = GitHubListSpec.wrapper(
            GitHubPageRequestKindValidators.PAGE,
            GitHubActionsHostedRunnersForEnterprise.class,
            GitHubActionsHostedRunnersForEnterprise::runners,
            GitHubActionsHostedRunnersForEnterprise::totalCount);

    /**
     * Hosted runners resource over {@code caller}.
     *
     * @param caller executes and decodes the calls of this resource
     * @throws NullPointerException if {@code caller} is {@code null}
     */
    public GitHubActionsHostedRunnersApi(final GitHubCaller caller) {
        super(caller);
    }

    /**
     * List GitHub-hosted runners for an organization ({@code GET /orgs/{org}/actions/hosted-runners}).
     *
     * <p>Uses {@link GitHubPageRequest#byPage()}.
     *
     * @param org organization name
     * @return first HTTP page of hosted runners
     * @throws IllegalArgumentException if {@code org} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public GitHubPage<GitHubActionsHostedRunner> listHostedRunnersForOrg(final String org) throws IOException {
        return listHostedRunnersForOrg(org, GitHubPageRequest.byPage());
    }

    /**
     * List GitHub-hosted runners for an organization ({@code GET /orgs/{org}/actions/hosted-runners}).
     *
     * @param org organization name
     * @param request pagination token
     * @return first HTTP page of hosted runners
     * @throws IllegalArgumentException if {@code org} is blank, or {@code request} is invalid
     * @throws NullPointerException if {@code request} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public GitHubPage<GitHubActionsHostedRunner> listHostedRunnersForOrg(
            final String org, final GitHubPageRequest request) throws IOException {
        requireFlavor(GitHubFlavor.CLOUD, GitHubFlavor.GHEC);
        requireNonNull(request, PARAM_REQUEST);

        return list(ORG_HOSTED_RUNNERS, Map.of(), request, RUNNERS_SPEC, org);
    }

    /**
     * Create a GitHub-hosted runner for an organization ({@code POST /orgs/{org}/actions/hosted-runners}).
     *
     * @param org organization name
     * @param payload runner name, image, size and group
     * @return created hosted runner
     * @throws IllegalArgumentException if {@code org} is blank
     * @throws NullPointerException if {@code payload} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public GitHubActionsHostedRunner createHostedRunnerForOrg(
            final String org, final GitHubCreateHostedRunnerForOrgPayload payload) throws IOException {
        requireFlavor(GitHubFlavor.CLOUD, GitHubFlavor.GHEC);
        requireNonNull(payload, PARAM_PAYLOAD);

        return caller.fetch(caller.json(post(ORG_HOSTED_RUNNERS, org), payload), GitHubActionsHostedRunner.class);
    }

    /**
     * List custom images for an organization ({@code GET /orgs/{org}/actions/hosted-runners/images/custom}).
     *
     * @param org organization name
     * @return custom images
     * @throws IllegalArgumentException if {@code org} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public GitHubActionsCustomImages listCustomImagesForOrg(final String org) throws IOException {
        requireFlavor(GitHubFlavor.CLOUD, GitHubFlavor.GHEC);
        return caller.fetch(get(ORG_CUSTOM_IMAGES, org), GitHubActionsCustomImages.class);
    }

    /**
     * Get a custom image for an organization ({@code GET
     * /orgs/{org}/actions/hosted-runners/images/custom/{image_definition_id}}).
     *
     * @param org organization name
     * @param imageDefinitionId image definition ID
     * @return custom image, or empty on HTTP 404
     * @throws IllegalArgumentException if {@code org} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public Optional<GitHubActionsHostedRunnerCustomImage> getCustomImageForOrg(
            final String org, final long imageDefinitionId) throws IOException {
        requireFlavor(GitHubFlavor.CLOUD, GitHubFlavor.GHEC);
        return caller.fetchOrEmpty(
                get(ORG_CUSTOM_IMAGE, org, imageDefinitionId), GitHubActionsHostedRunnerCustomImage.class);
    }

    /**
     * Delete a custom image from an organization ({@code DELETE
     * /orgs/{org}/actions/hosted-runners/images/custom/{image_definition_id}}).
     *
     * @param org organization name
     * @param imageDefinitionId image definition ID
     * @throws IllegalArgumentException if {@code org} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public void deleteCustomImageFromOrg(final String org, final long imageDefinitionId) throws IOException {
        requireFlavor(GitHubFlavor.CLOUD, GitHubFlavor.GHEC);
        caller.send(delete(ORG_CUSTOM_IMAGE, org, imageDefinitionId));
    }

    /**
     * List image versions of a custom image ({@code GET
     * /orgs/{org}/actions/hosted-runners/images/custom/{image_definition_id}/versions}).
     *
     * @param org organization name
     * @param imageDefinitionId image definition ID
     * @return image versions
     * @throws IllegalArgumentException if {@code org} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public GitHubActionsCustomImageVersions listCustomImageVersionsForOrg(
            final String org, final long imageDefinitionId) throws IOException {
        requireFlavor(GitHubFlavor.CLOUD, GitHubFlavor.GHEC);
        return caller.fetch(
                get(ORG_CUSTOM_IMAGE_VERSIONS, org, imageDefinitionId), GitHubActionsCustomImageVersions.class);
    }

    /**
     * Get an image version of a custom image ({@code GET
     * /orgs/{org}/actions/hosted-runners/images/custom/{image_definition_id}/versions/{version}}).
     *
     * @param org organization name
     * @param imageDefinitionId image definition ID
     * @param version image version, {@code major.minor.patch}
     * @return image version, or empty on HTTP 404
     * @throws IllegalArgumentException if {@code org} is blank, or {@code version} is invalid
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful (HTTP 404 yields empty)
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public Optional<GitHubActionsHostedRunnerCustomImageVersion> getCustomImageVersionForOrg(
            final String org, final long imageDefinitionId, final String version) throws IOException {
        requireFlavor(GitHubFlavor.CLOUD, GitHubFlavor.GHEC);
        return caller.fetchOrEmpty(
                get(ORG_CUSTOM_IMAGE_VERSION, org, imageDefinitionId, requireVersion(version)),
                GitHubActionsHostedRunnerCustomImageVersion.class);
    }

    /**
     * Delete an image version of a custom image ({@code DELETE
     * /orgs/{org}/actions/hosted-runners/images/custom/{image_definition_id}/versions/{version}}).
     *
     * @param org organization name
     * @param imageDefinitionId image definition ID
     * @param version image version, {@code major.minor.patch}
     * @throws IllegalArgumentException if {@code org} is blank, or {@code version} is invalid
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public void deleteCustomImageVersionFromOrg(final String org, final long imageDefinitionId, final String version)
            throws IOException {
        requireFlavor(GitHubFlavor.CLOUD, GitHubFlavor.GHEC);
        caller.send(delete(ORG_CUSTOM_IMAGE_VERSION, org, imageDefinitionId, requireVersion(version)));
    }

    /**
     * List GitHub-owned images for an organization ({@code GET
     * /orgs/{org}/actions/hosted-runners/images/github-owned}).
     *
     * @param org organization name
     * @return GitHub-owned images
     * @throws IllegalArgumentException if {@code org} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public GitHubActionsHostedRunnerImages getGitHubOwnedImagesForOrg(final String org) throws IOException {
        requireFlavor(GitHubFlavor.CLOUD, GitHubFlavor.GHEC);
        return caller.fetch(get(ORG_GITHUB_OWNED_IMAGES, org), GitHubActionsHostedRunnerImages.class);
    }

    /**
     * List partner images for an organization ({@code GET /orgs/{org}/actions/hosted-runners/images/partner}).
     *
     * @param org organization name
     * @return partner images
     * @throws IllegalArgumentException if {@code org} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public GitHubActionsHostedRunnerImages getPartnerImagesForOrg(final String org) throws IOException {
        requireFlavor(GitHubFlavor.CLOUD, GitHubFlavor.GHEC);
        return caller.fetch(get(ORG_PARTNER_IMAGES, org), GitHubActionsHostedRunnerImages.class);
    }

    /**
     * Get limits for GitHub-hosted runners of an organization ({@code GET /orgs/{org}/actions/hosted-runners/limits}).
     *
     * @param org organization name
     * @return hosted runner limits
     * @throws IllegalArgumentException if {@code org} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public GitHubActionsHostedRunnerLimits getHostedRunnersLimitsForOrg(final String org) throws IOException {
        requireFlavor(GitHubFlavor.CLOUD, GitHubFlavor.GHEC);
        return caller.fetch(get(ORG_LIMITS, org), GitHubActionsHostedRunnerLimits.class);
    }

    /**
     * List machine sizes available to an organization ({@code GET /orgs/{org}/actions/hosted-runners/machine-sizes}).
     *
     * @param org organization name
     * @return machine specs
     * @throws IllegalArgumentException if {@code org} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public GitHubActionsHostedRunnerMachineSpecs getHostedRunnersMachineSpecsForOrg(final String org)
            throws IOException {
        requireFlavor(GitHubFlavor.CLOUD, GitHubFlavor.GHEC);
        return caller.fetch(get(ORG_MACHINE_SIZES, org), GitHubActionsHostedRunnerMachineSpecs.class);
    }

    /**
     * List platforms available to an organization ({@code GET /orgs/{org}/actions/hosted-runners/platforms}).
     *
     * @param org organization name
     * @return platforms
     * @throws IllegalArgumentException if {@code org} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public GitHubActionsHostedRunnerPlatforms getHostedRunnersPlatformsForOrg(final String org) throws IOException {
        requireFlavor(GitHubFlavor.CLOUD, GitHubFlavor.GHEC);
        return caller.fetch(get(ORG_PLATFORMS, org), GitHubActionsHostedRunnerPlatforms.class);
    }

    /**
     * Get a GitHub-hosted runner for an organization ({@code GET
     * /orgs/{org}/actions/hosted-runners/{hosted_runner_id}}).
     *
     * @param org organization name
     * @param hostedRunnerId hosted runner ID
     * @return hosted runner, or empty on HTTP 404
     * @throws IllegalArgumentException if {@code org} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public Optional<GitHubActionsHostedRunner> getHostedRunnerForOrg(final String org, final long hostedRunnerId)
            throws IOException {
        requireFlavor(GitHubFlavor.CLOUD, GitHubFlavor.GHEC);
        return caller.fetchOrEmpty(get(ORG_HOSTED_RUNNER, org, hostedRunnerId), GitHubActionsHostedRunner.class);
    }

    /**
     * Update a GitHub-hosted runner for an organization ({@code PATCH
     * /orgs/{org}/actions/hosted-runners/{hosted_runner_id}}).
     *
     * @param org organization name
     * @param hostedRunnerId hosted runner ID
     * @param payload changed settings
     * @return updated hosted runner
     * @throws IllegalArgumentException if {@code org} is blank
     * @throws NullPointerException if {@code payload} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public GitHubActionsHostedRunner updateHostedRunnerForOrg(
            final String org, final long hostedRunnerId, final GitHubUpdateHostedRunnerForOrgPayload payload)
            throws IOException {
        requireFlavor(GitHubFlavor.CLOUD, GitHubFlavor.GHEC);
        requireNonNull(payload, PARAM_PAYLOAD);

        return caller.fetch(
                caller.json(patch(ORG_HOSTED_RUNNER, org, hostedRunnerId), payload), GitHubActionsHostedRunner.class);
    }

    /**
     * Delete a GitHub-hosted runner for an organization ({@code DELETE
     * /orgs/{org}/actions/hosted-runners/{hosted_runner_id}}).
     *
     * @param org organization name
     * @param hostedRunnerId hosted runner ID
     * @return the runner that is being deleted
     * @throws IllegalArgumentException if {@code org} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public GitHubActionsHostedRunner deleteHostedRunnerForOrg(final String org, final long hostedRunnerId)
            throws IOException {
        requireFlavor(GitHubFlavor.CLOUD, GitHubFlavor.GHEC);
        return caller.fetch(delete(ORG_HOSTED_RUNNER, org, hostedRunnerId), GitHubActionsHostedRunner.class);
    }

    /**
     * List GitHub-hosted runners for an enterprise ({@code GET /enterprises/{enterprise}/actions/hosted-runners}).
     *
     * <p>Uses {@link GitHubPageRequest#byPage()}.
     *
     * @param enterprise enterprise slug
     * @return first HTTP page of hosted runners
     * @throws IllegalArgumentException if {@code enterprise} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHEC}
     */
    @Flavors(GitHubFlavor.GHEC)
    public GitHubPage<GitHubActionsHostedRunner> listHostedRunnersForEnterprise(final String enterprise)
            throws IOException {
        return listHostedRunnersForEnterprise(enterprise, GitHubPageRequest.byPage());
    }

    /**
     * List GitHub-hosted runners for an enterprise ({@code GET /enterprises/{enterprise}/actions/hosted-runners}).
     *
     * @param enterprise enterprise slug
     * @param request pagination token
     * @return first HTTP page of hosted runners
     * @throws IllegalArgumentException if {@code enterprise} is blank, or {@code request} is invalid
     * @throws NullPointerException if {@code request} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHEC}
     */
    @Flavors(GitHubFlavor.GHEC)
    public GitHubPage<GitHubActionsHostedRunner> listHostedRunnersForEnterprise(
            final String enterprise, final GitHubPageRequest request) throws IOException {
        requireFlavor(GitHubFlavor.GHEC);
        requireNonNull(request, PARAM_REQUEST);

        return list(ENTERPRISE_HOSTED_RUNNERS, Map.of(), request, ENTERPRISE_RUNNERS_SPEC, enterprise);
    }

    /**
     * Create a GitHub-hosted runner for an enterprise ({@code POST /enterprises/{enterprise}/actions/hosted-runners}).
     *
     * @param enterprise enterprise slug
     * @param payload runner name, image, size and group
     * @return created hosted runner
     * @throws IllegalArgumentException if {@code enterprise} is blank
     * @throws NullPointerException if {@code payload} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHEC}
     */
    @Flavors(GitHubFlavor.GHEC)
    public GitHubActionsHostedRunner createHostedRunnerForEnterprise(
            final String enterprise, final GitHubCreateHostedRunnerForEnterprisePayload payload) throws IOException {
        requireFlavor(GitHubFlavor.GHEC);
        requireNonNull(payload, PARAM_PAYLOAD);

        return caller.fetch(
                caller.json(post(ENTERPRISE_HOSTED_RUNNERS, enterprise), payload), GitHubActionsHostedRunner.class);
    }

    /**
     * List custom images for an enterprise ({@code GET
     * /enterprises/{enterprise}/actions/hosted-runners/images/custom}).
     *
     * @param enterprise enterprise slug
     * @return custom images
     * @throws IllegalArgumentException if {@code enterprise} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHEC}
     */
    @Flavors(GitHubFlavor.GHEC)
    public GitHubActionsCustomImagesForEnterprise listCustomImagesForEnterprise(final String enterprise)
            throws IOException {
        requireFlavor(GitHubFlavor.GHEC);
        return caller.fetch(get(ENTERPRISE_CUSTOM_IMAGES, enterprise), GitHubActionsCustomImagesForEnterprise.class);
    }

    /**
     * Get a custom image for an enterprise ({@code GET
     * /enterprises/{enterprise}/actions/hosted-runners/images/custom/{image_definition_id}}).
     *
     * @param enterprise enterprise slug
     * @param imageDefinitionId image definition ID
     * @return custom image, or empty on HTTP 404
     * @throws IllegalArgumentException if {@code enterprise} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHEC}
     */
    @Flavors(GitHubFlavor.GHEC)
    public Optional<GitHubActionsHostedRunnerCustomImage> getCustomImageForEnterprise(
            final String enterprise, final long imageDefinitionId) throws IOException {
        requireFlavor(GitHubFlavor.GHEC);
        return caller.fetchOrEmpty(
                get(ENTERPRISE_CUSTOM_IMAGE, enterprise, imageDefinitionId),
                GitHubActionsHostedRunnerCustomImage.class);
    }

    /**
     * Delete a custom image from an enterprise ({@code DELETE
     * /enterprises/{enterprise}/actions/hosted-runners/images/custom/{image_definition_id}}).
     *
     * @param enterprise enterprise slug
     * @param imageDefinitionId image definition ID
     * @throws IllegalArgumentException if {@code enterprise} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHEC}
     */
    @Flavors(GitHubFlavor.GHEC)
    public void deleteCustomImageFromEnterprise(final String enterprise, final long imageDefinitionId)
            throws IOException {
        requireFlavor(GitHubFlavor.GHEC);
        caller.send(delete(ENTERPRISE_CUSTOM_IMAGE, enterprise, imageDefinitionId));
    }

    /**
     * List image versions of a custom image of an enterprise ({@code GET
     * /enterprises/{enterprise}/actions/hosted-runners/images/custom/{image_definition_id}/versions}).
     *
     * @param enterprise enterprise slug
     * @param imageDefinitionId image definition ID
     * @return image versions
     * @throws IllegalArgumentException if {@code enterprise} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHEC}
     */
    @Flavors(GitHubFlavor.GHEC)
    public GitHubActionsCustomImageVersionsForEnterprise listCustomImageVersionsForEnterprise(
            final String enterprise, final long imageDefinitionId) throws IOException {
        requireFlavor(GitHubFlavor.GHEC);
        return caller.fetch(
                get(ENTERPRISE_CUSTOM_IMAGE_VERSIONS, enterprise, imageDefinitionId),
                GitHubActionsCustomImageVersionsForEnterprise.class);
    }

    /**
     * Get an image version of a custom image of an enterprise ({@code GET
     * /enterprises/{enterprise}/actions/hosted-runners/images/custom/{image_definition_id}/versions/{version}}).
     *
     * @param enterprise enterprise slug
     * @param imageDefinitionId image definition ID
     * @param version image version, {@code major.minor.patch}
     * @return image version, or empty on HTTP 404
     * @throws IllegalArgumentException if {@code enterprise} is blank, or {@code version} is invalid
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful (HTTP 404 yields empty)
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHEC}
     */
    @Flavors(GitHubFlavor.GHEC)
    public Optional<GitHubActionsHostedRunnerCustomImageVersion> getCustomImageVersionForEnterprise(
            final String enterprise, final long imageDefinitionId, final String version) throws IOException {
        requireFlavor(GitHubFlavor.GHEC);
        return caller.fetchOrEmpty(
                get(ENTERPRISE_CUSTOM_IMAGE_VERSION, enterprise, imageDefinitionId, requireVersion(version)),
                GitHubActionsHostedRunnerCustomImageVersion.class);
    }

    /**
     * Delete an image version of a custom image of an enterprise ({@code DELETE
     * /enterprises/{enterprise}/actions/hosted-runners/images/custom/{image_definition_id}/versions/{version}}).
     *
     * @param enterprise enterprise slug
     * @param imageDefinitionId image definition ID
     * @param version image version, {@code major.minor.patch}
     * @throws IllegalArgumentException if {@code enterprise} is blank, or {@code version} is invalid
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHEC}
     */
    @Flavors(GitHubFlavor.GHEC)
    public void deleteCustomImageVersionFromEnterprise(
            final String enterprise, final long imageDefinitionId, final String version) throws IOException {
        requireFlavor(GitHubFlavor.GHEC);
        caller.send(delete(ENTERPRISE_CUSTOM_IMAGE_VERSION, enterprise, imageDefinitionId, requireVersion(version)));
    }

    /**
     * List GitHub-owned images for an enterprise ({@code GET
     * /enterprises/{enterprise}/actions/hosted-runners/images/github-owned}).
     *
     * @param enterprise enterprise slug
     * @return GitHub-owned images
     * @throws IllegalArgumentException if {@code enterprise} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHEC}
     */
    @Flavors(GitHubFlavor.GHEC)
    public GitHubActionsGitHubOwnedImagesForEnterprise getGitHubOwnedImagesForEnterprise(final String enterprise)
            throws IOException {
        requireFlavor(GitHubFlavor.GHEC);
        return caller.fetch(
                get(ENTERPRISE_GITHUB_OWNED_IMAGES, enterprise), GitHubActionsGitHubOwnedImagesForEnterprise.class);
    }

    /**
     * List partner images for an enterprise ({@code GET
     * /enterprises/{enterprise}/actions/hosted-runners/images/partner}).
     *
     * @param enterprise enterprise slug
     * @return partner images
     * @throws IllegalArgumentException if {@code enterprise} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHEC}
     */
    @Flavors(GitHubFlavor.GHEC)
    public GitHubActionsPartnerImagesForEnterprise getPartnerImagesForEnterprise(final String enterprise)
            throws IOException {
        requireFlavor(GitHubFlavor.GHEC);
        return caller.fetch(get(ENTERPRISE_PARTNER_IMAGES, enterprise), GitHubActionsPartnerImagesForEnterprise.class);
    }

    /**
     * Get limits for GitHub-hosted runners of an enterprise ({@code GET
     * /enterprises/{enterprise}/actions/hosted-runners/limits}).
     *
     * @param enterprise enterprise slug
     * @return hosted runner limits
     * @throws IllegalArgumentException if {@code enterprise} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHEC}
     */
    @Flavors(GitHubFlavor.GHEC)
    public GitHubActionsHostedRunnerLimits getHostedRunnersLimitsForEnterprise(final String enterprise)
            throws IOException {
        requireFlavor(GitHubFlavor.GHEC);
        return caller.fetch(get(ENTERPRISE_LIMITS, enterprise), GitHubActionsHostedRunnerLimits.class);
    }

    /**
     * List machine sizes available to an enterprise ({@code GET
     * /enterprises/{enterprise}/actions/hosted-runners/machine-sizes}).
     *
     * @param enterprise enterprise slug
     * @return machine specs
     * @throws IllegalArgumentException if {@code enterprise} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHEC}
     */
    @Flavors(GitHubFlavor.GHEC)
    public GitHubActionsHostedRunnerMachineSpecsForEnterprise getHostedRunnersMachineSpecsForEnterprise(
            final String enterprise) throws IOException {
        requireFlavor(GitHubFlavor.GHEC);
        return caller.fetch(
                get(ENTERPRISE_MACHINE_SIZES, enterprise), GitHubActionsHostedRunnerMachineSpecsForEnterprise.class);
    }

    /**
     * List platforms available to an enterprise ({@code GET
     * /enterprises/{enterprise}/actions/hosted-runners/platforms}).
     *
     * @param enterprise enterprise slug
     * @return platforms
     * @throws IllegalArgumentException if {@code enterprise} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHEC}
     */
    @Flavors(GitHubFlavor.GHEC)
    public GitHubActionsHostedRunnerPlatformsForEnterprise getHostedRunnersPlatformsForEnterprise(
            final String enterprise) throws IOException {
        requireFlavor(GitHubFlavor.GHEC);
        return caller.fetch(
                get(ENTERPRISE_PLATFORMS, enterprise), GitHubActionsHostedRunnerPlatformsForEnterprise.class);
    }

    /**
     * Get a GitHub-hosted runner for an enterprise ({@code GET
     * /enterprises/{enterprise}/actions/hosted-runners/{hosted_runner_id}}).
     *
     * @param enterprise enterprise slug
     * @param hostedRunnerId hosted runner ID
     * @return hosted runner, or empty on HTTP 404
     * @throws IllegalArgumentException if {@code enterprise} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHEC}
     */
    @Flavors(GitHubFlavor.GHEC)
    public Optional<GitHubActionsHostedRunner> getHostedRunnerForEnterprise(
            final String enterprise, final long hostedRunnerId) throws IOException {
        requireFlavor(GitHubFlavor.GHEC);
        return caller.fetchOrEmpty(
                get(ENTERPRISE_HOSTED_RUNNER, enterprise, hostedRunnerId), GitHubActionsHostedRunner.class);
    }

    /**
     * Update a GitHub-hosted runner for an enterprise ({@code PATCH
     * /enterprises/{enterprise}/actions/hosted-runners/{hosted_runner_id}}).
     *
     * @param enterprise enterprise slug
     * @param hostedRunnerId hosted runner ID
     * @param payload changed settings
     * @return updated hosted runner
     * @throws IllegalArgumentException if {@code enterprise} is blank
     * @throws NullPointerException if {@code payload} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHEC}
     */
    @Flavors(GitHubFlavor.GHEC)
    public GitHubActionsHostedRunner updateHostedRunnerForEnterprise(
            final String enterprise,
            final long hostedRunnerId,
            final GitHubUpdateHostedRunnerForEnterprisePayload payload)
            throws IOException {
        requireFlavor(GitHubFlavor.GHEC);
        requireNonNull(payload, PARAM_PAYLOAD);

        return caller.fetch(
                caller.json(patch(ENTERPRISE_HOSTED_RUNNER, enterprise, hostedRunnerId), payload),
                GitHubActionsHostedRunner.class);
    }

    /**
     * Delete a GitHub-hosted runner for an enterprise ({@code DELETE
     * /enterprises/{enterprise}/actions/hosted-runners/{hosted_runner_id}}).
     *
     * @param enterprise enterprise slug
     * @param hostedRunnerId hosted runner ID
     * @return the runner that is being deleted
     * @throws IllegalArgumentException if {@code enterprise} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHEC}
     */
    @Flavors(GitHubFlavor.GHEC)
    public GitHubActionsHostedRunner deleteHostedRunnerForEnterprise(final String enterprise, final long hostedRunnerId)
            throws IOException {
        requireFlavor(GitHubFlavor.GHEC);
        return caller.fetch(
                delete(ENTERPRISE_HOSTED_RUNNER, enterprise, hostedRunnerId), GitHubActionsHostedRunner.class);
    }

    private static String requireVersion(final String version) {
        return requireMatches(requireNotBlank(version, PARAM_VERSION), CUSTOM_IMAGE_VERSION, PARAM_VERSION);
    }
}
