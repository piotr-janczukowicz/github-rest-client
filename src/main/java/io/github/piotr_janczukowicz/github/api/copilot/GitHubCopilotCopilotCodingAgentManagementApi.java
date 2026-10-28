package io.github.piotr_janczukowicz.github.api.copilot;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.api.repositories.GitHubMinimalRepository;
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

/** GitHub Copilot coding agent management REST resource for API version {@code 2026-03-10}. */
public final class GitHubCopilotCopilotCodingAgentManagementApi extends GitHubResourceApi {

    private static final PathTemplate ENTERPRISE_CODING_AGENT_POLICY =
            PathTemplate.of("/enterprises/{enterprise}/copilot/policies/coding_agent");
    private static final PathTemplate ENTERPRISE_CODING_AGENT_POLICY_ORGANIZATIONS =
            PathTemplate.of("/enterprises/{enterprise}/copilot/policies/coding_agent/organizations");
    private static final PathTemplate ORG_CODING_AGENT_PERMISSIONS =
            PathTemplate.of("/orgs/{org}/copilot/coding-agent/permissions");
    private static final PathTemplate ORG_CODING_AGENT_PERMISSIONS_REPOSITORIES =
            PathTemplate.of("/orgs/{org}/copilot/coding-agent/permissions/repositories");
    private static final PathTemplate ORG_CODING_AGENT_PERMISSIONS_REPOSITORY =
            PathTemplate.of("/orgs/{org}/copilot/coding-agent/permissions/repositories/{repository_id}");

    /**
     * Copilot coding agent management resource over {@code caller}.
     *
     * @param caller executes and decodes the calls of this resource
     * @throws NullPointerException if {@code caller} is {@code null}
     */
    public GitHubCopilotCopilotCodingAgentManagementApi(final GitHubCaller caller) {
        super(caller);
    }

    /**
     * Set the coding agent policy for an enterprise ({@code PUT
     * /enterprises/{enterprise}/copilot/policies/coding_agent}).
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
    public void setEnterprisePolicy(final String enterprise, final GitHubSetEnterpriseCodingAgentPolicyPayload payload)
            throws IOException {
        requireFlavor(GitHubFlavor.CLOUD, GitHubFlavor.GHEC);
        requireNonNull(payload, PARAM_PAYLOAD);

        caller.send(caller.json(put(ENTERPRISE_CODING_AGENT_POLICY, enterprise), payload));
    }

    /**
     * Add organizations to the coding agent policy of an enterprise ({@code POST
     * /enterprises/{enterprise}/copilot/policies/coding_agent/organizations}).
     *
     * @param enterprise enterprise slug
     * @param payload organizations to add
     * @throws IllegalArgumentException if {@code enterprise} is blank
     * @throws NullPointerException if {@code payload} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public void addOrganizationsToEnterprisePolicy(
            final String enterprise, final GitHubAddOrganizationsToEnterpriseCodingAgentPolicyPayload payload)
            throws IOException {
        requireFlavor(GitHubFlavor.CLOUD, GitHubFlavor.GHEC);
        requireNonNull(payload, PARAM_PAYLOAD);

        caller.send(caller.json(post(ENTERPRISE_CODING_AGENT_POLICY_ORGANIZATIONS, enterprise), payload));
    }

    /**
     * Remove organizations from the coding agent policy of an enterprise ({@code DELETE
     * /enterprises/{enterprise}/copilot/policies/coding_agent/organizations}).
     *
     * @param enterprise enterprise slug
     * @param payload organizations to remove
     * @throws IllegalArgumentException if {@code enterprise} is blank
     * @throws NullPointerException if {@code payload} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public void removeOrganizationsFromEnterprisePolicy(
            final String enterprise, final GitHubRemoveOrganizationsFromEnterpriseCodingAgentPolicyPayload payload)
            throws IOException {
        requireFlavor(GitHubFlavor.CLOUD, GitHubFlavor.GHEC);
        requireNonNull(payload, PARAM_PAYLOAD);

        caller.send(caller.json(delete(ENTERPRISE_CODING_AGENT_POLICY_ORGANIZATIONS, enterprise), payload));
    }

    /**
     * Get the coding agent permissions of an organization ({@code GET /orgs/{org}/copilot/coding-agent/permissions}).
     *
     * @param org organization name
     * @return coding agent permissions
     * @throws IllegalArgumentException if {@code org} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public GitHubGetCopilotCodingAgentPermissionsOrganizationResponse getOrganizationPermissions(final String org)
            throws IOException {
        requireFlavor(GitHubFlavor.CLOUD, GitHubFlavor.GHEC);
        return caller.fetch(
                get(ORG_CODING_AGENT_PERMISSIONS, org),
                GitHubGetCopilotCodingAgentPermissionsOrganizationResponse.class);
    }

    /**
     * Set the coding agent permissions of an organization ({@code PUT /orgs/{org}/copilot/coding-agent/permissions}).
     *
     * @param org organization name
     * @param payload permissions to set
     * @throws IllegalArgumentException if {@code org} is blank
     * @throws NullPointerException if {@code payload} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public void setOrganizationPermissions(
            final String org, final GitHubSetCopilotCodingAgentPermissionsOrganizationPayload payload)
            throws IOException {
        requireFlavor(GitHubFlavor.CLOUD, GitHubFlavor.GHEC);
        requireNonNull(payload, PARAM_PAYLOAD);

        caller.send(caller.json(put(ORG_CODING_AGENT_PERMISSIONS, org), payload));
    }

    /**
     * List repositories enabled for the coding agent in an organization ({@code GET
     * /orgs/{org}/copilot/coding-agent/permissions/repositories}).
     *
     * <p>Uses {@link GitHubPageRequest#byPage()}. See {@link #listOrganizationSelectedRepositories(String,
     * GitHubPageRequest)}.
     *
     * @param org organization name
     * @return first HTTP page of repositories
     * @throws IllegalArgumentException if {@code org} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public GitHubPage<GitHubMinimalRepository> listOrganizationSelectedRepositories(final String org)
            throws IOException {
        return listOrganizationSelectedRepositories(org, GitHubPageRequest.byPage());
    }

    /**
     * List repositories enabled for the coding agent in an organization ({@code GET
     * /orgs/{org}/copilot/coding-agent/permissions/repositories}).
     *
     * @param org organization name
     * @param request pagination token
     * @return first HTTP page of repositories
     * @throws IllegalArgumentException if {@code org} is blank, or {@code request} is a since or cursor variant
     * @throws NullPointerException if {@code request} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public GitHubPage<GitHubMinimalRepository> listOrganizationSelectedRepositories(
            final String org, final GitHubPageRequest request) throws IOException {
        requireFlavor(GitHubFlavor.CLOUD, GitHubFlavor.GHEC);
        requireNonNull(request, PARAM_REQUEST);

        return list(
                ORG_CODING_AGENT_PERMISSIONS_REPOSITORIES,
                Map.of(),
                request,
                GitHubListSpec.wrapper(
                        GitHubPageRequestKindValidators.PAGE,
                        GitHubListCopilotCodingAgentSelectedRepositoriesForOrganizationResponse.class,
                        GitHubListCopilotCodingAgentSelectedRepositoriesForOrganizationResponse::repositories,
                        GitHubListCopilotCodingAgentSelectedRepositoriesForOrganizationResponse::totalCount),
                org);
    }

    /**
     * Set the repositories enabled for the coding agent in an organization ({@code PUT
     * /orgs/{org}/copilot/coding-agent/permissions/repositories}).
     *
     * @param org organization name
     * @param payload repository ids to enable
     * @throws IllegalArgumentException if {@code org} is blank
     * @throws NullPointerException if {@code payload} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public void setOrganizationSelectedRepositories(
            final String org, final GitHubSetCopilotCodingAgentSelectedRepositoriesForOrganizationPayload payload)
            throws IOException {
        requireFlavor(GitHubFlavor.CLOUD, GitHubFlavor.GHEC);
        requireNonNull(payload, PARAM_PAYLOAD);

        caller.send(caller.json(put(ORG_CODING_AGENT_PERMISSIONS_REPOSITORIES, org), payload));
    }

    /**
     * Enable the coding agent for a repository in an organization ({@code PUT
     * /orgs/{org}/copilot/coding-agent/permissions/repositories/{repository_id}}).
     *
     * @param org organization name
     * @param repositoryId repository id
     * @throws IllegalArgumentException if {@code org} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public void enableRepositoryForOrganization(final String org, final long repositoryId) throws IOException {
        requireFlavor(GitHubFlavor.CLOUD, GitHubFlavor.GHEC);
        caller.send(put(ORG_CODING_AGENT_PERMISSIONS_REPOSITORY, org, repositoryId));
    }

    /**
     * Disable the coding agent for a repository in an organization ({@code DELETE
     * /orgs/{org}/copilot/coding-agent/permissions/repositories/{repository_id}}).
     *
     * @param org organization name
     * @param repositoryId repository id
     * @throws IllegalArgumentException if {@code org} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public void disableRepositoryForOrganization(final String org, final long repositoryId) throws IOException {
        requireFlavor(GitHubFlavor.CLOUD, GitHubFlavor.GHEC);
        caller.send(delete(ORG_CODING_AGENT_PERMISSIONS_REPOSITORY, org, repositoryId));
    }
}
