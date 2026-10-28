package io.github.piotr_janczukowicz.github.api.actions;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.api.organizations.GitHubOrganizationSimple;
import io.github.piotr_janczukowicz.github.api.repositories.GitHubRepository;
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

/** GitHub Actions permissions REST resource for API version {@code 2026-03-10}. */
public final class GitHubActionsPermissionsApi extends GitHubResourceApi {

    private static final PathTemplate ORG_PERMISSIONS = PathTemplate.of("/orgs/{org}/actions/permissions");
    private static final PathTemplate ORG_ARTIFACT_LOG_RETENTION =
            PathTemplate.of("/orgs/{org}/actions/permissions/artifact-and-log-retention");
    private static final PathTemplate ORG_FORK_PR_CONTRIBUTOR_APPROVAL =
            PathTemplate.of("/orgs/{org}/actions/permissions/fork-pr-contributor-approval");
    private static final PathTemplate ORG_FORK_PR_WORKFLOWS_PRIVATE_REPOS =
            PathTemplate.of("/orgs/{org}/actions/permissions/fork-pr-workflows-private-repos");
    private static final PathTemplate ORG_ENABLED_REPOSITORIES =
            PathTemplate.of("/orgs/{org}/actions/permissions/repositories");
    private static final PathTemplate ORG_ENABLED_REPOSITORY =
            PathTemplate.of("/orgs/{org}/actions/permissions/repositories/{repository_id}");
    private static final PathTemplate ORG_SELECTED_ACTIONS =
            PathTemplate.of("/orgs/{org}/actions/permissions/selected-actions");
    private static final PathTemplate ORG_SELF_HOSTED_RUNNERS =
            PathTemplate.of("/orgs/{org}/actions/permissions/self-hosted-runners");
    private static final PathTemplate ORG_SELF_HOSTED_RUNNERS_REPOSITORIES =
            PathTemplate.of("/orgs/{org}/actions/permissions/self-hosted-runners/repositories");
    private static final PathTemplate ORG_SELF_HOSTED_RUNNERS_REPOSITORY =
            PathTemplate.of("/orgs/{org}/actions/permissions/self-hosted-runners/repositories/{repository_id}");
    private static final PathTemplate ORG_DEFAULT_WORKFLOW =
            PathTemplate.of("/orgs/{org}/actions/permissions/workflow");
    private static final PathTemplate REPO_PERMISSIONS = PathTemplate.of("/repos/{owner}/{repo}/actions/permissions");
    private static final PathTemplate REPO_WORKFLOW_ACCESS =
            PathTemplate.of("/repos/{owner}/{repo}/actions/permissions/access");
    private static final PathTemplate REPO_ARTIFACT_LOG_RETENTION =
            PathTemplate.of("/repos/{owner}/{repo}/actions/permissions/artifact-and-log-retention");
    private static final PathTemplate REPO_FORK_PR_CONTRIBUTOR_APPROVAL =
            PathTemplate.of("/repos/{owner}/{repo}/actions/permissions/fork-pr-contributor-approval");
    private static final PathTemplate REPO_FORK_PR_WORKFLOWS_PRIVATE_REPOS =
            PathTemplate.of("/repos/{owner}/{repo}/actions/permissions/fork-pr-workflows-private-repos");
    private static final PathTemplate REPO_SELECTED_ACTIONS =
            PathTemplate.of("/repos/{owner}/{repo}/actions/permissions/selected-actions");
    private static final PathTemplate REPO_DEFAULT_WORKFLOW =
            PathTemplate.of("/repos/{owner}/{repo}/actions/permissions/workflow");
    private static final PathTemplate ENTERPRISE_PERMISSIONS =
            PathTemplate.of("/enterprises/{enterprise}/actions/permissions");
    private static final PathTemplate ENTERPRISE_ARTIFACT_LOG_RETENTION =
            PathTemplate.of("/enterprises/{enterprise}/actions/permissions/artifact-and-log-retention");
    private static final PathTemplate ENTERPRISE_FORK_PR_CONTRIBUTOR_APPROVAL =
            PathTemplate.of("/enterprises/{enterprise}/actions/permissions/fork-pr-contributor-approval");
    private static final PathTemplate ENTERPRISE_FORK_PR_WORKFLOWS_PRIVATE_REPOS =
            PathTemplate.of("/enterprises/{enterprise}/actions/permissions/fork-pr-workflows-private-repos");
    private static final PathTemplate ENTERPRISE_ORGANIZATIONS =
            PathTemplate.of("/enterprises/{enterprise}/actions/permissions/organizations");
    private static final PathTemplate ENTERPRISE_ORGANIZATION =
            PathTemplate.of("/enterprises/{enterprise}/actions/permissions/organizations/{org_id}");
    private static final PathTemplate ENTERPRISE_SELECTED_ACTIONS =
            PathTemplate.of("/enterprises/{enterprise}/actions/permissions/selected-actions");
    private static final PathTemplate ENTERPRISE_SELF_HOSTED_RUNNERS =
            PathTemplate.of("/enterprises/{enterprise}/actions/permissions/self-hosted-runners");
    private static final PathTemplate ENTERPRISE_DEFAULT_WORKFLOW =
            PathTemplate.of("/enterprises/{enterprise}/actions/permissions/workflow");

    private static final GitHubListSpec<GitHubRepository> ENABLED_REPOSITORIES_SPEC = GitHubListSpec.wrapper(
            GitHubPageRequestKindValidators.PAGE,
            GitHubSelectedRepositoriesEnabledActions.class,
            GitHubSelectedRepositoriesEnabledActions::repositories,
            GitHubSelectedRepositoriesEnabledActions::totalCount);
    private static final GitHubListSpec<GitHubRepository> SELF_HOSTED_REPOSITORIES_SPEC = GitHubListSpec.wrapper(
            GitHubPageRequestKindValidators.PAGE,
            GitHubSelectedRepositoriesSelfHostedRunners.class,
            GitHubSelectedRepositoriesSelfHostedRunners::repositories,
            GitHubSelectedRepositoriesSelfHostedRunners::totalCount);
    private static final GitHubListSpec<GitHubOrganizationSimple> ENTERPRISE_ORGANIZATIONS_SPEC =
            GitHubListSpec.wrapper(
                    GitHubPageRequestKindValidators.PAGE,
                    GitHubSelectedOrganizationsEnabledActions.class,
                    GitHubSelectedOrganizationsEnabledActions::organizations,
                    GitHubSelectedOrganizationsEnabledActions::totalCount);

    /**
     * Permissions resource over {@code caller}.
     *
     * @param caller executes and decodes the calls of this resource
     * @throws NullPointerException if {@code caller} is {@code null}
     */
    public GitHubActionsPermissionsApi(final GitHubCaller caller) {
        super(caller);
    }

    /**
     * Get GitHub Actions permissions of an enterprise ({@code GET /enterprises/{enterprise}/actions/permissions}).
     *
     * @param enterprise the slug version of the enterprise name
     * @return permissions
     * @throws IllegalArgumentException if {@code enterprise} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHEC} or
     *     {@code GHES}
     */
    @Flavors({GitHubFlavor.GHEC, GitHubFlavor.GHES})
    public GitHubActionsEnterprisePermissions getEnterpriseActionsPermissions(final String enterprise)
            throws IOException {
        requireFlavor(GitHubFlavor.GHEC, GitHubFlavor.GHES);
        return caller.fetch(get(ENTERPRISE_PERMISSIONS, enterprise), GitHubActionsEnterprisePermissions.class);
    }

    /**
     * Set GitHub Actions permissions of an enterprise ({@code PUT /enterprises/{enterprise}/actions/permissions}).
     *
     * @param enterprise the slug version of the enterprise name
     * @param payload permissions
     * @throws IllegalArgumentException if {@code enterprise} is blank
     * @throws NullPointerException if {@code payload} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHEC} or
     *     {@code GHES}
     */
    @Flavors({GitHubFlavor.GHEC, GitHubFlavor.GHES})
    public void setEnterpriseActionsPermissions(
            final String enterprise, final GitHubSetGithubActionsPermissionsEnterprisePayload payload)
            throws IOException {
        requireFlavor(GitHubFlavor.GHEC, GitHubFlavor.GHES);
        requireNonNull(payload, PARAM_PAYLOAD);

        caller.send(caller.json(put(ENTERPRISE_PERMISSIONS, enterprise), payload));
    }

    /**
     * Get retention settings for checks, workflow runs, commit statuses, artifacts, and logs of an enterprise
     * ({@code GET /enterprises/{enterprise}/actions/permissions/artifact-and-log-retention}).
     *
     * @param enterprise the slug version of the enterprise name
     * @return retention settings
     * @throws IllegalArgumentException if {@code enterprise} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHEC} or
     *     {@code GHES}
     */
    @Flavors({GitHubFlavor.GHEC, GitHubFlavor.GHES})
    public GitHubActionsArtifactAndLogRetentionResponse getEnterpriseArtifactAndLogRetentionSettings(
            final String enterprise) throws IOException {
        requireFlavor(GitHubFlavor.GHEC, GitHubFlavor.GHES);
        return caller.fetch(
                get(ENTERPRISE_ARTIFACT_LOG_RETENTION, enterprise), GitHubActionsArtifactAndLogRetentionResponse.class);
    }

    /**
     * Set retention settings for checks, workflow runs, commit statuses, artifacts, and logs of an enterprise
     * ({@code PUT /enterprises/{enterprise}/actions/permissions/artifact-and-log-retention}).
     *
     * @param enterprise the slug version of the enterprise name
     * @param payload retention days
     * @throws IllegalArgumentException if {@code enterprise} is blank
     * @throws NullPointerException if {@code payload} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHEC} or
     *     {@code GHES}
     */
    @Flavors({GitHubFlavor.GHEC, GitHubFlavor.GHES})
    public void setEnterpriseArtifactAndLogRetentionSettings(
            final String enterprise, final GitHubActionsArtifactAndLogRetention payload) throws IOException {
        requireFlavor(GitHubFlavor.GHEC, GitHubFlavor.GHES);
        requireNonNull(payload, PARAM_PAYLOAD);

        caller.send(caller.json(put(ENTERPRISE_ARTIFACT_LOG_RETENTION, enterprise), payload));
    }

    /**
     * Get fork PR contributor approval policy of an enterprise ({@code GET
     * /enterprises/{enterprise}/actions/permissions/fork-pr-contributor-approval}).
     *
     * @param enterprise the slug version of the enterprise name
     * @return approval policy
     * @throws IllegalArgumentException if {@code enterprise} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHEC} or
     *     {@code GHES}
     */
    @Flavors({GitHubFlavor.GHEC, GitHubFlavor.GHES})
    public GitHubActionsForkPrContributorApproval getEnterpriseForkPrContributorApprovalPermissions(
            final String enterprise) throws IOException {
        requireFlavor(GitHubFlavor.GHEC, GitHubFlavor.GHES);
        return caller.fetch(
                get(ENTERPRISE_FORK_PR_CONTRIBUTOR_APPROVAL, enterprise), GitHubActionsForkPrContributorApproval.class);
    }

    /**
     * Set fork PR contributor approval policy of an enterprise ({@code PUT
     * /enterprises/{enterprise}/actions/permissions/fork-pr-contributor-approval}).
     *
     * @param enterprise the slug version of the enterprise name
     * @param payload approval policy
     * @throws IllegalArgumentException if {@code enterprise} is blank
     * @throws NullPointerException if {@code payload} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHEC} or
     *     {@code GHES}
     */
    @Flavors({GitHubFlavor.GHEC, GitHubFlavor.GHES})
    public void setEnterpriseForkPrContributorApprovalPermissions(
            final String enterprise, final GitHubActionsForkPrContributorApproval payload) throws IOException {
        requireFlavor(GitHubFlavor.GHEC, GitHubFlavor.GHES);
        requireNonNull(payload, PARAM_PAYLOAD);

        caller.send(caller.json(put(ENTERPRISE_FORK_PR_CONTRIBUTOR_APPROVAL, enterprise), payload));
    }

    /**
     * Get fork PR workflow settings for private repositories of an enterprise ({@code GET
     * /enterprises/{enterprise}/actions/permissions/fork-pr-workflows-private-repos}).
     *
     * @param enterprise the slug version of the enterprise name
     * @return settings
     * @throws IllegalArgumentException if {@code enterprise} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHEC} or
     *     {@code GHES}
     */
    @Flavors({GitHubFlavor.GHEC, GitHubFlavor.GHES})
    public GitHubActionsForkPrWorkflowsPrivateRepos getEnterprisePrivateRepoForkPrWorkflowsSettings(
            final String enterprise) throws IOException {
        requireFlavor(GitHubFlavor.GHEC, GitHubFlavor.GHES);
        return caller.fetch(
                get(ENTERPRISE_FORK_PR_WORKFLOWS_PRIVATE_REPOS, enterprise),
                GitHubActionsForkPrWorkflowsPrivateRepos.class);
    }

    /**
     * Set fork PR workflow settings for private repositories of an enterprise ({@code PUT
     * /enterprises/{enterprise}/actions/permissions/fork-pr-workflows-private-repos}).
     *
     * @param enterprise the slug version of the enterprise name
     * @param payload settings
     * @throws IllegalArgumentException if {@code enterprise} is blank
     * @throws NullPointerException if {@code payload} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHEC} or
     *     {@code GHES}
     */
    @Flavors({GitHubFlavor.GHEC, GitHubFlavor.GHES})
    public void setEnterprisePrivateRepoForkPrWorkflowsSettings(
            final String enterprise, final GitHubActionsForkPrWorkflowsPrivateReposRequest payload) throws IOException {
        requireFlavor(GitHubFlavor.GHEC, GitHubFlavor.GHES);
        requireNonNull(payload, PARAM_PAYLOAD);

        caller.send(caller.json(put(ENTERPRISE_FORK_PR_WORKFLOWS_PRIVATE_REPOS, enterprise), payload));
    }

    /**
     * List organizations selected to have GitHub Actions enabled in an enterprise ({@code GET
     * /enterprises/{enterprise}/actions/permissions/organizations}).
     *
     * <p>Uses {@link GitHubPageRequest#byPage()}.
     *
     * @param enterprise the slug version of the enterprise name
     * @return first HTTP page of organizations
     * @throws IllegalArgumentException if {@code enterprise} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHEC} or
     *     {@code GHES}
     */
    @Flavors({GitHubFlavor.GHEC, GitHubFlavor.GHES})
    public GitHubPage<GitHubOrganizationSimple> listSelectedOrganizationsEnabledGitHubActionsEnterprise(
            final String enterprise) throws IOException {
        return listSelectedOrganizationsEnabledGitHubActionsEnterprise(enterprise, GitHubPageRequest.byPage());
    }

    /**
     * List organizations selected to have GitHub Actions enabled in an enterprise ({@code GET
     * /enterprises/{enterprise}/actions/permissions/organizations}).
     *
     * @param enterprise the slug version of the enterprise name
     * @param request pagination token
     * @return first HTTP page of organizations
     * @throws IllegalArgumentException if {@code enterprise} is blank, or {@code request} is invalid
     * @throws NullPointerException if {@code request} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHEC} or
     *     {@code GHES}
     */
    @Flavors({GitHubFlavor.GHEC, GitHubFlavor.GHES})
    public GitHubPage<GitHubOrganizationSimple> listSelectedOrganizationsEnabledGitHubActionsEnterprise(
            final String enterprise, final GitHubPageRequest request) throws IOException {
        requireFlavor(GitHubFlavor.GHEC, GitHubFlavor.GHES);
        requireNonNull(request, PARAM_REQUEST);

        return list(ENTERPRISE_ORGANIZATIONS, Map.of(), request, ENTERPRISE_ORGANIZATIONS_SPEC, enterprise);
    }

    /**
     * Set organizations selected to have GitHub Actions enabled in an enterprise ({@code PUT
     * /enterprises/{enterprise}/actions/permissions/organizations}).
     *
     * @param enterprise the slug version of the enterprise name
     * @param payload organization IDs
     * @throws IllegalArgumentException if {@code enterprise} is blank
     * @throws NullPointerException if {@code payload} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHEC} or
     *     {@code GHES}
     */
    @Flavors({GitHubFlavor.GHEC, GitHubFlavor.GHES})
    public void setSelectedOrganizationsEnabledGitHubActionsEnterprise(
            final String enterprise, final GitHubSetSelectedOrganizationsEnabledGithubActionsEnterprisePayload payload)
            throws IOException {
        requireFlavor(GitHubFlavor.GHEC, GitHubFlavor.GHES);
        requireNonNull(payload, PARAM_PAYLOAD);

        caller.send(caller.json(put(ENTERPRISE_ORGANIZATIONS, enterprise), payload));
    }

    /**
     * Enable an organization for GitHub Actions in an enterprise ({@code PUT
     * /enterprises/{enterprise}/actions/permissions/organizations/{org_id}}).
     *
     * @param enterprise the slug version of the enterprise name
     * @param orgId organization ID
     * @throws IllegalArgumentException if {@code enterprise} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHEC} or
     *     {@code GHES}
     */
    @Flavors({GitHubFlavor.GHEC, GitHubFlavor.GHES})
    public void enableSelectedOrganizationGitHubActionsEnterprise(final String enterprise, final long orgId)
            throws IOException {
        requireFlavor(GitHubFlavor.GHEC, GitHubFlavor.GHES);
        caller.send(put(ENTERPRISE_ORGANIZATION, enterprise, orgId));
    }

    /**
     * Disable an organization for GitHub Actions in an enterprise ({@code DELETE
     * /enterprises/{enterprise}/actions/permissions/organizations/{org_id}}).
     *
     * @param enterprise the slug version of the enterprise name
     * @param orgId organization ID
     * @throws IllegalArgumentException if {@code enterprise} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHEC} or
     *     {@code GHES}
     */
    @Flavors({GitHubFlavor.GHEC, GitHubFlavor.GHES})
    public void disableSelectedOrganizationGitHubActionsEnterprise(final String enterprise, final long orgId)
            throws IOException {
        requireFlavor(GitHubFlavor.GHEC, GitHubFlavor.GHES);
        caller.send(delete(ENTERPRISE_ORGANIZATION, enterprise, orgId));
    }

    /**
     * Get allowed actions and reusable workflows of an enterprise ({@code GET
     * /enterprises/{enterprise}/actions/permissions/selected-actions}).
     *
     * @param enterprise the slug version of the enterprise name
     * @return allowed actions
     * @throws IllegalArgumentException if {@code enterprise} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHEC} or
     *     {@code GHES}
     */
    @Flavors({GitHubFlavor.GHEC, GitHubFlavor.GHES})
    public GitHubSelectedActions getEnterpriseAllowedActions(final String enterprise) throws IOException {
        requireFlavor(GitHubFlavor.GHEC, GitHubFlavor.GHES);
        return caller.fetch(get(ENTERPRISE_SELECTED_ACTIONS, enterprise), GitHubSelectedActions.class);
    }

    /**
     * Set allowed actions and reusable workflows of an enterprise ({@code PUT
     * /enterprises/{enterprise}/actions/permissions/selected-actions}).
     *
     * @param enterprise the slug version of the enterprise name
     * @param payload allowed actions
     * @throws IllegalArgumentException if {@code enterprise} is blank
     * @throws NullPointerException if {@code payload} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHEC} or
     *     {@code GHES}
     */
    @Flavors({GitHubFlavor.GHEC, GitHubFlavor.GHES})
    public void setEnterpriseAllowedActions(final String enterprise, final GitHubSelectedActions payload)
            throws IOException {
        requireFlavor(GitHubFlavor.GHEC, GitHubFlavor.GHES);
        requireNonNull(payload, PARAM_PAYLOAD);

        caller.send(caller.json(put(ENTERPRISE_SELECTED_ACTIONS, enterprise), payload));
    }

    /**
     * Get self-hosted runner permissions of an enterprise ({@code GET
     * /enterprises/{enterprise}/actions/permissions/self-hosted-runners}).
     *
     * @param enterprise the slug version of the enterprise name
     * @return settings
     * @throws IllegalArgumentException if {@code enterprise} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHEC} or
     *     {@code GHES}
     */
    @Flavors({GitHubFlavor.GHEC, GitHubFlavor.GHES})
    public GitHubSelfHostedRunnersPermissionsForEnterprise getEnterpriseSelfHostedRunnersPermissions(
            final String enterprise) throws IOException {
        requireFlavor(GitHubFlavor.GHEC, GitHubFlavor.GHES);
        return caller.fetch(
                get(ENTERPRISE_SELF_HOSTED_RUNNERS, enterprise), GitHubSelfHostedRunnersPermissionsForEnterprise.class);
    }

    /**
     * Set self-hosted runner permissions of an enterprise ({@code PUT
     * /enterprises/{enterprise}/actions/permissions/self-hosted-runners}).
     *
     * @param enterprise the slug version of the enterprise name
     * @param payload settings
     * @throws IllegalArgumentException if {@code enterprise} is blank
     * @throws NullPointerException if {@code payload} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHEC} or
     *     {@code GHES}
     */
    @Flavors({GitHubFlavor.GHEC, GitHubFlavor.GHES})
    public void setEnterpriseSelfHostedRunnersPermissions(
            final String enterprise, final GitHubSetSelfHostedRunnersPermissionsEnterprisePayload payload)
            throws IOException {
        requireFlavor(GitHubFlavor.GHEC, GitHubFlavor.GHES);
        requireNonNull(payload, PARAM_PAYLOAD);

        caller.send(caller.json(put(ENTERPRISE_SELF_HOSTED_RUNNERS, enterprise), payload));
    }

    /**
     * Get default workflow permissions of an enterprise ({@code GET
     * /enterprises/{enterprise}/actions/permissions/workflow}).
     *
     * @param enterprise the slug version of the enterprise name
     * @return default workflow permissions
     * @throws IllegalArgumentException if {@code enterprise} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHEC} or
     *     {@code GHES}
     */
    @Flavors({GitHubFlavor.GHEC, GitHubFlavor.GHES})
    public GitHubActionsGetDefaultWorkflowPermissions getEnterpriseDefaultWorkflowPermissions(final String enterprise)
            throws IOException {
        requireFlavor(GitHubFlavor.GHEC, GitHubFlavor.GHES);
        return caller.fetch(
                get(ENTERPRISE_DEFAULT_WORKFLOW, enterprise), GitHubActionsGetDefaultWorkflowPermissions.class);
    }

    /**
     * Set default workflow permissions of an enterprise ({@code PUT
     * /enterprises/{enterprise}/actions/permissions/workflow}).
     *
     * @param enterprise the slug version of the enterprise name
     * @param payload default workflow permissions
     * @throws IllegalArgumentException if {@code enterprise} is blank
     * @throws NullPointerException if {@code payload} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHEC} or
     *     {@code GHES}
     */
    @Flavors({GitHubFlavor.GHEC, GitHubFlavor.GHES})
    public void setEnterpriseDefaultWorkflowPermissions(
            final String enterprise, final GitHubActionsSetDefaultWorkflowPermissions payload) throws IOException {
        requireFlavor(GitHubFlavor.GHEC, GitHubFlavor.GHES);
        requireNonNull(payload, PARAM_PAYLOAD);

        caller.send(caller.json(put(ENTERPRISE_DEFAULT_WORKFLOW, enterprise), payload));
    }

    /**
     * Get GitHub Actions permissions of an organization ({@code GET /orgs/{org}/actions/permissions}).
     *
     * @param org organization name
     * @return permissions
     * @throws IllegalArgumentException if {@code org} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubActionsOrganizationPermissions getOrgActionsPermissions(final String org) throws IOException {
        return caller.fetch(get(ORG_PERMISSIONS, org), GitHubActionsOrganizationPermissions.class);
    }

    /**
     * Set GitHub Actions permissions of an organization ({@code PUT /orgs/{org}/actions/permissions}).
     *
     * @param org organization name
     * @param payload permissions
     * @throws IllegalArgumentException if {@code org} is blank
     * @throws NullPointerException if {@code payload} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public void setOrgActionsPermissions(
            final String org, final GitHubSetGithubActionsPermissionsOrganizationPayload payload) throws IOException {
        requireNonNull(payload, PARAM_PAYLOAD);

        caller.send(caller.json(put(ORG_PERMISSIONS, org), payload));
    }

    /**
     * Get retention settings for checks, workflow runs, commit statuses, artifacts, and logs of an organization
     * ({@code GET /orgs/{org}/actions/permissions/artifact-and-log-retention}).
     *
     * <p>Checks include check suites, check runs, and check annotations. These settings also apply to checks and commit
     * statuses created by third-party integrations, and are not limited to data created by GitHub Actions.
     *
     * @param org organization name
     * @return retention settings
     * @throws IllegalArgumentException if {@code org} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubActionsArtifactAndLogRetentionResponse getOrgArtifactAndLogRetentionSettings(final String org)
            throws IOException {
        return caller.fetch(get(ORG_ARTIFACT_LOG_RETENTION, org), GitHubActionsArtifactAndLogRetentionResponse.class);
    }

    /**
     * Set retention settings for checks, workflow runs, commit statuses, artifacts, and logs of an organization
     * ({@code PUT /orgs/{org}/actions/permissions/artifact-and-log-retention}).
     *
     * <p>Checks include check suites, check runs, and check annotations. These settings also apply to checks and commit
     * statuses created by third-party integrations, and are not limited to data created by GitHub Actions.
     *
     * @param org organization name
     * @param payload retention days
     * @throws IllegalArgumentException if {@code org} is blank
     * @throws NullPointerException if {@code payload} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public void setOrgArtifactAndLogRetentionSettings(
            final String org, final GitHubActionsArtifactAndLogRetention payload) throws IOException {
        requireNonNull(payload, PARAM_PAYLOAD);

        caller.send(caller.json(put(ORG_ARTIFACT_LOG_RETENTION, org), payload));
    }

    /**
     * Get fork PR contributor approval policy of an organization ({@code GET
     * /orgs/{org}/actions/permissions/fork-pr-contributor-approval}).
     *
     * @param org organization name
     * @return approval policy
     * @throws IllegalArgumentException if {@code org} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubActionsForkPrContributorApproval getOrgForkPrContributorApprovalPermissions(final String org)
            throws IOException {
        return caller.fetch(get(ORG_FORK_PR_CONTRIBUTOR_APPROVAL, org), GitHubActionsForkPrContributorApproval.class);
    }

    /**
     * Set fork PR contributor approval policy of an organization ({@code PUT
     * /orgs/{org}/actions/permissions/fork-pr-contributor-approval}).
     *
     * @param org organization name
     * @param payload approval policy
     * @throws IllegalArgumentException if {@code org} is blank
     * @throws NullPointerException if {@code payload} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public void setOrgForkPrContributorApprovalPermissions(
            final String org, final GitHubActionsForkPrContributorApproval payload) throws IOException {
        requireNonNull(payload, PARAM_PAYLOAD);

        caller.send(caller.json(put(ORG_FORK_PR_CONTRIBUTOR_APPROVAL, org), payload));
    }

    /**
     * Get fork PR workflow settings for private repositories of an organization ({@code GET
     * /orgs/{org}/actions/permissions/fork-pr-workflows-private-repos}).
     *
     * @param org organization name
     * @return settings
     * @throws IllegalArgumentException if {@code org} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubActionsForkPrWorkflowsPrivateRepos getOrgPrivateRepoForkPrWorkflowsSettings(final String org)
            throws IOException {
        return caller.fetch(
                get(ORG_FORK_PR_WORKFLOWS_PRIVATE_REPOS, org), GitHubActionsForkPrWorkflowsPrivateRepos.class);
    }

    /**
     * Set fork PR workflow settings for private repositories of an organization ({@code PUT
     * /orgs/{org}/actions/permissions/fork-pr-workflows-private-repos}).
     *
     * @param org organization name
     * @param payload settings
     * @throws IllegalArgumentException if {@code org} is blank
     * @throws NullPointerException if {@code payload} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public void setOrgPrivateRepoForkPrWorkflowsSettings(
            final String org, final GitHubActionsForkPrWorkflowsPrivateReposRequest payload) throws IOException {
        requireNonNull(payload, PARAM_PAYLOAD);

        caller.send(caller.json(put(ORG_FORK_PR_WORKFLOWS_PRIVATE_REPOS, org), payload));
    }

    /**
     * List repositories enabled for GitHub Actions in an organization ({@code GET
     * /orgs/{org}/actions/permissions/repositories}).
     *
     * <p>Uses {@link GitHubPageRequest#byPage()}.
     *
     * @param org organization name
     * @return first HTTP page of repositories
     * @throws IllegalArgumentException if {@code org} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubRepository> listSelectedRepositoriesEnabledGitHubActionsOrg(final String org)
            throws IOException {
        return listSelectedRepositoriesEnabledGitHubActionsOrg(org, GitHubPageRequest.byPage());
    }

    /**
     * List repositories enabled for GitHub Actions in an organization ({@code GET
     * /orgs/{org}/actions/permissions/repositories}).
     *
     * @param org organization name
     * @param request pagination token
     * @return first HTTP page of repositories
     * @throws IllegalArgumentException if {@code org} is blank, or {@code request} is invalid
     * @throws NullPointerException if {@code request} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubRepository> listSelectedRepositoriesEnabledGitHubActionsOrg(
            final String org, final GitHubPageRequest request) throws IOException {
        requireNonNull(request, PARAM_REQUEST);

        return list(ORG_ENABLED_REPOSITORIES, Map.of(), request, ENABLED_REPOSITORIES_SPEC, org);
    }

    /**
     * Set repositories enabled for GitHub Actions in an organization ({@code PUT
     * /orgs/{org}/actions/permissions/repositories}).
     *
     * @param org organization name
     * @param payload repository IDs
     * @throws IllegalArgumentException if {@code org} is blank
     * @throws NullPointerException if {@code payload} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public void setSelectedRepositoriesEnabledGitHubActionsOrg(
            final String org, final GitHubSetSelectedRepositoriesEnabledGithubActionsOrganizationPayload payload)
            throws IOException {
        requireNonNull(payload, PARAM_PAYLOAD);

        caller.send(caller.json(put(ORG_ENABLED_REPOSITORIES, org), payload));
    }

    /**
     * Enable a repository for GitHub Actions in an organization ({@code PUT
     * /orgs/{org}/actions/permissions/repositories/{repository_id}}).
     *
     * @param org organization name
     * @param repositoryId repository ID
     * @throws IllegalArgumentException if {@code org} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public void enableSelectedRepositoryGitHubActionsOrg(final String org, final long repositoryId) throws IOException {
        caller.send(put(ORG_ENABLED_REPOSITORY, org, repositoryId));
    }

    /**
     * Disable a repository for GitHub Actions in an organization ({@code DELETE
     * /orgs/{org}/actions/permissions/repositories/{repository_id}}).
     *
     * @param org organization name
     * @param repositoryId repository ID
     * @throws IllegalArgumentException if {@code org} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public void disableSelectedRepositoryGitHubActionsOrg(final String org, final long repositoryId)
            throws IOException {
        caller.send(delete(ORG_ENABLED_REPOSITORY, org, repositoryId));
    }

    /**
     * Get allowed actions of an organization ({@code GET /orgs/{org}/actions/permissions/selected-actions}).
     *
     * @param org organization name
     * @return allowed actions
     * @throws IllegalArgumentException if {@code org} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubSelectedActions getAllowedActionsOrg(final String org) throws IOException {
        return caller.fetch(get(ORG_SELECTED_ACTIONS, org), GitHubSelectedActions.class);
    }

    /**
     * Set allowed actions of an organization ({@code PUT /orgs/{org}/actions/permissions/selected-actions}).
     *
     * @param org organization name
     * @param payload allowed actions
     * @throws IllegalArgumentException if {@code org} is blank
     * @throws NullPointerException if {@code payload} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public void setAllowedActionsOrg(final String org, final GitHubSelectedActions payload) throws IOException {
        requireNonNull(payload, PARAM_PAYLOAD);

        caller.send(caller.json(put(ORG_SELECTED_ACTIONS, org), payload));
    }

    /**
     * Get self-hosted runner permissions of an organization ({@code GET
     * /orgs/{org}/actions/permissions/self-hosted-runners}).
     *
     * @param org organization name
     * @return settings
     * @throws IllegalArgumentException if {@code org} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubSelfHostedRunnersSettings getOrgSelfHostedRunnersPermissions(final String org) throws IOException {
        return caller.fetch(get(ORG_SELF_HOSTED_RUNNERS, org), GitHubSelfHostedRunnersSettings.class);
    }

    /**
     * Set self-hosted runner permissions of an organization ({@code PUT
     * /orgs/{org}/actions/permissions/self-hosted-runners}).
     *
     * @param org organization name
     * @param payload settings
     * @throws IllegalArgumentException if {@code org} is blank
     * @throws NullPointerException if {@code payload} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public void setOrgSelfHostedRunnersPermissions(
            final String org, final GitHubSetSelfHostedRunnersPermissionsOrganizationPayload payload)
            throws IOException {
        requireNonNull(payload, PARAM_PAYLOAD);

        caller.send(caller.json(put(ORG_SELF_HOSTED_RUNNERS, org), payload));
    }

    /**
     * List repositories allowed to use self-hosted runners in an organization ({@code GET
     * /orgs/{org}/actions/permissions/self-hosted-runners/repositories}).
     *
     * <p>Uses {@link GitHubPageRequest#byPage()}.
     *
     * @param org organization name
     * @return first HTTP page of repositories
     * @throws IllegalArgumentException if {@code org} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubRepository> listSelectedRepositoriesSelfHostedRunnersOrg(final String org)
            throws IOException {
        return listSelectedRepositoriesSelfHostedRunnersOrg(org, GitHubPageRequest.byPage());
    }

    /**
     * List repositories allowed to use self-hosted runners in an organization ({@code GET
     * /orgs/{org}/actions/permissions/self-hosted-runners/repositories}).
     *
     * @param org organization name
     * @param request pagination token
     * @return first HTTP page of repositories
     * @throws IllegalArgumentException if {@code org} is blank, or {@code request} is invalid
     * @throws NullPointerException if {@code request} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubRepository> listSelectedRepositoriesSelfHostedRunnersOrg(
            final String org, final GitHubPageRequest request) throws IOException {
        requireNonNull(request, PARAM_REQUEST);

        return list(ORG_SELF_HOSTED_RUNNERS_REPOSITORIES, Map.of(), request, SELF_HOSTED_REPOSITORIES_SPEC, org);
    }

    /**
     * Set repositories allowed to use self-hosted runners in an organization ({@code PUT
     * /orgs/{org}/actions/permissions/self-hosted-runners/repositories}).
     *
     * @param org organization name
     * @param payload repository IDs
     * @throws IllegalArgumentException if {@code org} is blank
     * @throws NullPointerException if {@code payload} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public void setSelectedRepositoriesSelfHostedRunnersOrg(
            final String org, final GitHubSetSelectedRepositoriesSelfHostedRunnersOrganizationPayload payload)
            throws IOException {
        requireNonNull(payload, PARAM_PAYLOAD);

        caller.send(caller.json(put(ORG_SELF_HOSTED_RUNNERS_REPOSITORIES, org), payload));
    }

    /**
     * Allow a repository to use self-hosted runners in an organization ({@code PUT
     * /orgs/{org}/actions/permissions/self-hosted-runners/repositories/{repository_id}}).
     *
     * @param org organization name
     * @param repositoryId repository ID
     * @throws IllegalArgumentException if {@code org} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public void enableSelectedRepositorySelfHostedRunnersOrg(final String org, final long repositoryId)
            throws IOException {
        caller.send(put(ORG_SELF_HOSTED_RUNNERS_REPOSITORY, org, repositoryId));
    }

    /**
     * Remove a repository from using self-hosted runners in an organization ({@code DELETE
     * /orgs/{org}/actions/permissions/self-hosted-runners/repositories/{repository_id}}).
     *
     * @param org organization name
     * @param repositoryId repository ID
     * @throws IllegalArgumentException if {@code org} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public void disableSelectedRepositorySelfHostedRunnersOrg(final String org, final long repositoryId)
            throws IOException {
        caller.send(delete(ORG_SELF_HOSTED_RUNNERS_REPOSITORY, org, repositoryId));
    }

    /**
     * Get default workflow permissions of an organization ({@code GET /orgs/{org}/actions/permissions/workflow}).
     *
     * @param org organization name
     * @return default workflow permissions
     * @throws IllegalArgumentException if {@code org} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubActionsGetDefaultWorkflowPermissions getOrgDefaultWorkflowPermissions(final String org)
            throws IOException {
        return caller.fetch(get(ORG_DEFAULT_WORKFLOW, org), GitHubActionsGetDefaultWorkflowPermissions.class);
    }

    /**
     * Set default workflow permissions of an organization ({@code PUT /orgs/{org}/actions/permissions/workflow}).
     *
     * @param org organization name
     * @param payload default workflow permissions
     * @throws IllegalArgumentException if {@code org} is blank
     * @throws NullPointerException if {@code payload} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public void setOrgDefaultWorkflowPermissions(
            final String org, final GitHubActionsSetDefaultWorkflowPermissions payload) throws IOException {
        requireNonNull(payload, PARAM_PAYLOAD);

        caller.send(caller.json(put(ORG_DEFAULT_WORKFLOW, org), payload));
    }

    /**
     * Get GitHub Actions permissions of a repository ({@code GET /repos/{owner}/{repo}/actions/permissions}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @return permissions
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubActionsRepositoryPermissions getRepoActionsPermissions(final String owner, final String repo)
            throws IOException {
        return caller.fetch(get(REPO_PERMISSIONS, owner, repo), GitHubActionsRepositoryPermissions.class);
    }

    /**
     * Set GitHub Actions permissions of a repository ({@code PUT /repos/{owner}/{repo}/actions/permissions}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param payload permissions
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws NullPointerException if {@code payload} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public void setRepoActionsPermissions(
            final String owner, final String repo, final GitHubSetGithubActionsPermissionsRepositoryPayload payload)
            throws IOException {
        requireNonNull(payload, PARAM_PAYLOAD);

        caller.send(caller.json(put(REPO_PERMISSIONS, owner, repo), payload));
    }

    /**
     * Get the workflow access level of a repository ({@code GET /repos/{owner}/{repo}/actions/permissions/access}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @return access level
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubActionsWorkflowAccessToRepository getRepoWorkflowAccess(final String owner, final String repo)
            throws IOException {
        return caller.fetch(get(REPO_WORKFLOW_ACCESS, owner, repo), GitHubActionsWorkflowAccessToRepository.class);
    }

    /**
     * Set the workflow access level of a repository ({@code PUT /repos/{owner}/{repo}/actions/permissions/access}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param payload access level
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws NullPointerException if {@code payload} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public void setRepoWorkflowAccess(
            final String owner, final String repo, final GitHubActionsWorkflowAccessToRepository payload)
            throws IOException {
        requireNonNull(payload, PARAM_PAYLOAD);

        caller.send(caller.json(put(REPO_WORKFLOW_ACCESS, owner, repo), payload));
    }

    /**
     * Get retention settings for checks, workflow runs, commit statuses, artifacts, and logs of a repository
     * ({@code GET /repos/{owner}/{repo}/actions/permissions/artifact-and-log-retention}).
     *
     * <p>Checks include check suites, check runs, and check annotations. These settings also apply to checks and commit
     * statuses created by third-party integrations, and are not limited to data created by GitHub Actions.
     *
     * @param owner repository owner
     * @param repo repository name
     * @return retention settings
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubActionsArtifactAndLogRetentionResponse getRepoArtifactAndLogRetentionSettings(
            final String owner, final String repo) throws IOException {
        return caller.fetch(
                get(REPO_ARTIFACT_LOG_RETENTION, owner, repo), GitHubActionsArtifactAndLogRetentionResponse.class);
    }

    /**
     * Set retention settings for checks, workflow runs, commit statuses, artifacts, and logs of a repository
     * ({@code PUT /repos/{owner}/{repo}/actions/permissions/artifact-and-log-retention}).
     *
     * <p>Checks include check suites, check runs, and check annotations. These settings also apply to checks and commit
     * statuses created by third-party integrations, and are not limited to data created by GitHub Actions.
     *
     * @param owner repository owner
     * @param repo repository name
     * @param payload retention days
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws NullPointerException if {@code payload} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public void setRepoArtifactAndLogRetentionSettings(
            final String owner, final String repo, final GitHubActionsArtifactAndLogRetention payload)
            throws IOException {
        requireNonNull(payload, PARAM_PAYLOAD);

        caller.send(caller.json(put(REPO_ARTIFACT_LOG_RETENTION, owner, repo), payload));
    }

    /**
     * Get fork PR contributor approval policy of a repository ({@code GET
     * /repos/{owner}/{repo}/actions/permissions/fork-pr-contributor-approval}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @return approval policy
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubActionsForkPrContributorApproval getRepoForkPrContributorApprovalPermissions(
            final String owner, final String repo) throws IOException {
        return caller.fetch(
                get(REPO_FORK_PR_CONTRIBUTOR_APPROVAL, owner, repo), GitHubActionsForkPrContributorApproval.class);
    }

    /**
     * Set fork PR contributor approval policy of a repository ({@code PUT
     * /repos/{owner}/{repo}/actions/permissions/fork-pr-contributor-approval}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param payload approval policy
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws NullPointerException if {@code payload} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public void setRepoForkPrContributorApprovalPermissions(
            final String owner, final String repo, final GitHubActionsForkPrContributorApproval payload)
            throws IOException {
        requireNonNull(payload, PARAM_PAYLOAD);

        caller.send(caller.json(put(REPO_FORK_PR_CONTRIBUTOR_APPROVAL, owner, repo), payload));
    }

    /**
     * Get fork PR workflow settings for private repositories of a repository ({@code GET
     * /repos/{owner}/{repo}/actions/permissions/fork-pr-workflows-private-repos}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @return settings
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubActionsForkPrWorkflowsPrivateRepos getRepoPrivateRepoForkPrWorkflowsSettings(
            final String owner, final String repo) throws IOException {
        return caller.fetch(
                get(REPO_FORK_PR_WORKFLOWS_PRIVATE_REPOS, owner, repo), GitHubActionsForkPrWorkflowsPrivateRepos.class);
    }

    /**
     * Set fork PR workflow settings for private repositories of a repository ({@code PUT
     * /repos/{owner}/{repo}/actions/permissions/fork-pr-workflows-private-repos}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param payload settings
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws NullPointerException if {@code payload} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public void setRepoPrivateRepoForkPrWorkflowsSettings(
            final String owner, final String repo, final GitHubActionsForkPrWorkflowsPrivateReposRequest payload)
            throws IOException {
        requireNonNull(payload, PARAM_PAYLOAD);

        caller.send(caller.json(put(REPO_FORK_PR_WORKFLOWS_PRIVATE_REPOS, owner, repo), payload));
    }

    /**
     * Get allowed actions of a repository ({@code GET /repos/{owner}/{repo}/actions/permissions/selected-actions}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @return allowed actions
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubSelectedActions getRepoAllowedActions(final String owner, final String repo) throws IOException {
        return caller.fetch(get(REPO_SELECTED_ACTIONS, owner, repo), GitHubSelectedActions.class);
    }

    /**
     * Set allowed actions of a repository ({@code PUT /repos/{owner}/{repo}/actions/permissions/selected-actions}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param payload allowed actions
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws NullPointerException if {@code payload} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public void setRepoAllowedActions(final String owner, final String repo, final GitHubSelectedActions payload)
            throws IOException {
        requireNonNull(payload, PARAM_PAYLOAD);

        caller.send(caller.json(put(REPO_SELECTED_ACTIONS, owner, repo), payload));
    }

    /**
     * Get default workflow permissions of a repository ({@code GET
     * /repos/{owner}/{repo}/actions/permissions/workflow}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @return default workflow permissions
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubActionsGetDefaultWorkflowPermissions getRepoDefaultWorkflowPermissions(
            final String owner, final String repo) throws IOException {
        return caller.fetch(get(REPO_DEFAULT_WORKFLOW, owner, repo), GitHubActionsGetDefaultWorkflowPermissions.class);
    }

    /**
     * Set default workflow permissions of a repository ({@code PUT
     * /repos/{owner}/{repo}/actions/permissions/workflow}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param payload default workflow permissions
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws NullPointerException if {@code payload} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public void setRepoDefaultWorkflowPermissions(
            final String owner, final String repo, final GitHubActionsSetDefaultWorkflowPermissions payload)
            throws IOException {
        requireNonNull(payload, PARAM_PAYLOAD);

        caller.send(caller.json(put(REPO_DEFAULT_WORKFLOW, owner, repo), payload));
    }
}
