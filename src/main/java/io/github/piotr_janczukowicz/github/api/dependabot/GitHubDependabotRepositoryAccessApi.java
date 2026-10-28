package io.github.piotr_janczukowicz.github.api.dependabot;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubCaller;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.github.client.GitHubResourceApi;
import io.github.piotr_janczukowicz.github.client.exception.GitHubApiException;
import io.github.piotr_janczukowicz.github.pagination.GitHubPageRequest;
import io.github.piotr_janczukowicz.github.pagination.GitHubPageRequestKindValidators;
import io.github.piotr_janczukowicz.uri.PathTemplate;
import java.io.IOException;

/** GitHub Dependabot repository access REST resource for API version {@code 2026-03-10}. */
public final class GitHubDependabotRepositoryAccessApi extends GitHubResourceApi {

    private static final PathTemplate ENTERPRISE_REPOSITORY_ACCESS =
            PathTemplate.of("/enterprises/{enterprise}/dependabot/repository-access");
    private static final PathTemplate ENTERPRISE_ACCESS_DEFAULT_LEVEL =
            PathTemplate.of("/enterprises/{enterprise}/dependabot/repository-access/default-level");
    private static final PathTemplate ORG_REPOSITORY_ACCESS =
            PathTemplate.of("/orgs/{org}/dependabot/repository-access");
    private static final PathTemplate ORG_ACCESS_DEFAULT_LEVEL =
            PathTemplate.of("/orgs/{org}/dependabot/repository-access/default-level");

    /**
     * Dependabot repository access resource over {@code caller}.
     *
     * @param caller executes and decodes the calls of this resource
     * @throws NullPointerException if {@code caller} is {@code null}
     */
    public GitHubDependabotRepositoryAccessApi(final GitHubCaller caller) {
        super(caller);
    }

    /**
     * Lists the repositories Dependabot can access in an enterprise ({@code GET
     * /enterprises/{enterprise}/dependabot/repository-access}).
     *
     * @param enterprise enterprise slug or ID
     * @return repository access details
     * @throws IllegalArgumentException if {@code enterprise} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public GitHubDependabotRepositoryAccessDetails repositoryAccessForEnterprise(final String enterprise)
            throws IOException {
        return repositoryAccessForEnterprise(enterprise, GitHubPageRequest.byPage());
    }

    /**
     * Lists the repositories Dependabot can access in an enterprise ({@code GET
     * /enterprises/{enterprise}/dependabot/repository-access}).
     *
     * @param enterprise enterprise slug or ID
     * @param request pagination token
     * @return repository access details
     * @throws IllegalArgumentException if {@code enterprise} is blank, or {@code request} is invalid
     * @throws NullPointerException if {@code request} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public GitHubDependabotRepositoryAccessDetails repositoryAccessForEnterprise(
            final String enterprise, final GitHubPageRequest request) throws IOException {
        requireFlavor(GitHubFlavor.CLOUD, GitHubFlavor.GHEC);
        requireNonNull(request, PARAM_REQUEST);

        return caller.fetch(
                caller.pageParameters(
                        get(ENTERPRISE_REPOSITORY_ACCESS, enterprise), request, GitHubPageRequestKindValidators.PAGE),
                GitHubDependabotRepositoryAccessDetails.class);
    }

    /**
     * Updates the Dependabot repository access list for an enterprise ({@code PATCH
     * /enterprises/{enterprise}/dependabot/repository-access}).
     *
     * @param enterprise enterprise slug or ID
     * @param payload repositories to add and remove
     * @throws IllegalArgumentException if {@code enterprise} is blank
     * @throws NullPointerException if {@code payload} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public void updateRepositoryAccessForEnterprise(
            final String enterprise, final GitHubUpdateRepositoryAccessPayload payload) throws IOException {
        requireFlavor(GitHubFlavor.CLOUD, GitHubFlavor.GHEC);
        requireNonNull(payload, PARAM_PAYLOAD);

        caller.send(caller.json(patch(ENTERPRISE_REPOSITORY_ACCESS, enterprise), payload));
    }

    /**
     * Set the default repository access level for Dependabot in an enterprise ({@code PUT
     * /enterprises/{enterprise}/dependabot/repository-access/default-level}).
     *
     * @param enterprise enterprise slug or ID
     * @param payload default access level
     * @throws IllegalArgumentException if {@code enterprise} is blank
     * @throws NullPointerException if {@code payload} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public void setRepositoryAccessDefaultLevelForEnterprise(
            final String enterprise, final GitHubSetRepositoryAccessDefaultLevelPayload payload) throws IOException {
        requireFlavor(GitHubFlavor.CLOUD, GitHubFlavor.GHEC);
        requireNonNull(payload, PARAM_PAYLOAD);

        caller.send(caller.json(put(ENTERPRISE_ACCESS_DEFAULT_LEVEL, enterprise), payload));
    }

    /**
     * Lists the repositories Dependabot can access in an organization ({@code GET
     * /orgs/{org}/dependabot/repository-access}).
     *
     * @param org organization name
     * @return repository access details
     * @throws IllegalArgumentException if {@code org} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubDependabotRepositoryAccessDetails repositoryAccessForOrg(final String org) throws IOException {
        return repositoryAccessForOrg(org, GitHubPageRequest.byPage());
    }

    /**
     * Lists the repositories Dependabot can access in an organization ({@code GET
     * /orgs/{org}/dependabot/repository-access}).
     *
     * @param org organization name
     * @param request pagination token
     * @return repository access details
     * @throws IllegalArgumentException if {@code org} is blank, or {@code request} is invalid
     * @throws NullPointerException if {@code request} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubDependabotRepositoryAccessDetails repositoryAccessForOrg(
            final String org, final GitHubPageRequest request) throws IOException {
        requireNonNull(request, PARAM_REQUEST);

        return caller.fetch(
                caller.pageParameters(get(ORG_REPOSITORY_ACCESS, org), request, GitHubPageRequestKindValidators.PAGE),
                GitHubDependabotRepositoryAccessDetails.class);
    }

    /**
     * Updates the Dependabot repository access list for an organization ({@code PATCH
     * /orgs/{org}/dependabot/repository-access}).
     *
     * @param org organization name
     * @param payload repositories to add and remove
     * @throws IllegalArgumentException if {@code org} is blank
     * @throws NullPointerException if {@code payload} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public void updateRepositoryAccessForOrg(final String org, final GitHubUpdateRepositoryAccessPayload payload)
            throws IOException {
        requireNonNull(payload, PARAM_PAYLOAD);

        caller.send(caller.json(patch(ORG_REPOSITORY_ACCESS, org), payload));
    }

    /**
     * Set the default repository access level for Dependabot ({@code PUT
     * /orgs/{org}/dependabot/repository-access/default-level}).
     *
     * @param org organization name
     * @param payload default access level
     * @throws IllegalArgumentException if {@code org} is blank
     * @throws NullPointerException if {@code payload} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public void setRepositoryAccessDefaultLevel(
            final String org, final GitHubSetRepositoryAccessDefaultLevelPayload payload) throws IOException {
        requireNonNull(payload, PARAM_PAYLOAD);

        caller.send(caller.json(put(ORG_ACCESS_DEFAULT_LEVEL, org), payload));
    }
}
