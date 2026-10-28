package io.github.piotr_janczukowicz.github.api.actions;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.api.organizations.GitHubOrganizationSimple;
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
import java.util.Optional;

/** GitHub Actions self-hosted runner groups REST resource for API version {@code 2026-03-10}. */
public final class GitHubActionsSelfHostedRunnerGroupsApi extends GitHubResourceApi {

    private static final PathTemplate ORG_RUNNER_GROUPS = PathTemplate.of("/orgs/{org}/actions/runner-groups");
    private static final PathTemplate ORG_RUNNER_GROUP =
            PathTemplate.of("/orgs/{org}/actions/runner-groups/{runner_group_id}");
    private static final PathTemplate ORG_RUNNER_GROUP_HOSTED_RUNNERS =
            PathTemplate.of("/orgs/{org}/actions/runner-groups/{runner_group_id}/hosted-runners");
    private static final PathTemplate ORG_RUNNER_GROUP_REPOSITORIES =
            PathTemplate.of("/orgs/{org}/actions/runner-groups/{runner_group_id}/repositories");
    private static final PathTemplate ORG_RUNNER_GROUP_REPOSITORY =
            PathTemplate.of("/orgs/{org}/actions/runner-groups/{runner_group_id}/repositories/{repository_id}");
    private static final PathTemplate ORG_RUNNER_GROUP_RUNNERS =
            PathTemplate.of("/orgs/{org}/actions/runner-groups/{runner_group_id}/runners");
    private static final PathTemplate ORG_RUNNER_GROUP_RUNNER =
            PathTemplate.of("/orgs/{org}/actions/runner-groups/{runner_group_id}/runners/{runner_id}");
    private static final PathTemplate ENTERPRISE_RUNNER_GROUPS =
            PathTemplate.of("/enterprises/{enterprise}/actions/runner-groups");
    private static final PathTemplate ENTERPRISE_RUNNER_GROUP =
            PathTemplate.of("/enterprises/{enterprise}/actions/runner-groups/{runner_group_id}");
    private static final PathTemplate ENTERPRISE_RUNNER_GROUP_ORGANIZATIONS =
            PathTemplate.of("/enterprises/{enterprise}/actions/runner-groups/{runner_group_id}/organizations");
    private static final PathTemplate ENTERPRISE_RUNNER_GROUP_ORGANIZATION =
            PathTemplate.of("/enterprises/{enterprise}/actions/runner-groups/{runner_group_id}/organizations/{org_id}");
    private static final PathTemplate ENTERPRISE_RUNNER_GROUP_RUNNERS =
            PathTemplate.of("/enterprises/{enterprise}/actions/runner-groups/{runner_group_id}/runners");
    private static final PathTemplate ENTERPRISE_RUNNER_GROUP_RUNNER =
            PathTemplate.of("/enterprises/{enterprise}/actions/runner-groups/{runner_group_id}/runners/{runner_id}");

    private static final GitHubListSpec<GitHubRunnerGroupsOrg> RUNNER_GROUPS_SPEC = GitHubListSpec.wrapper(
            GitHubPageRequestKindValidators.PAGE,
            GitHubRunnerGroups.class,
            GitHubRunnerGroups::runnerGroups,
            GitHubRunnerGroups::totalCount);
    private static final GitHubListSpec<GitHubActionsHostedRunner> HOSTED_RUNNERS_SPEC = GitHubListSpec.wrapper(
            GitHubPageRequestKindValidators.PAGE,
            GitHubRunnerGroupHostedRunners.class,
            GitHubRunnerGroupHostedRunners::runners,
            GitHubRunnerGroupHostedRunners::totalCount);
    private static final GitHubListSpec<GitHubMinimalRepository> REPOSITORIES_SPEC = GitHubListSpec.wrapper(
            GitHubPageRequestKindValidators.PAGE,
            GitHubRunnerGroupRepositories.class,
            GitHubRunnerGroupRepositories::repositories,
            GitHubRunnerGroupRepositories::totalCount);
    private static final GitHubListSpec<GitHubRunner> RUNNERS_SPEC = GitHubListSpec.wrapper(
            GitHubPageRequestKindValidators.PAGE,
            GitHubRunnerGroupRunners.class,
            GitHubRunnerGroupRunners::runners,
            GitHubRunnerGroupRunners::totalCount);
    private static final GitHubListSpec<GitHubRunnerGroupsEnterprise> ENTERPRISE_RUNNER_GROUPS_SPEC =
            GitHubListSpec.wrapper(
                    GitHubPageRequestKindValidators.PAGE,
                    GitHubRunnerGroupsForEnterprise.class,
                    GitHubRunnerGroupsForEnterprise::runnerGroups,
                    GitHubRunnerGroupsForEnterprise::totalCount);
    private static final GitHubListSpec<GitHubOrganizationSimple> ENTERPRISE_ORGANIZATIONS_SPEC =
            GitHubListSpec.wrapper(
                    GitHubPageRequestKindValidators.PAGE,
                    GitHubRunnerGroupOrganizationsForEnterprise.class,
                    GitHubRunnerGroupOrganizationsForEnterprise::organizations,
                    GitHubRunnerGroupOrganizationsForEnterprise::totalCount);
    private static final GitHubListSpec<GitHubRunner> ENTERPRISE_RUNNERS_SPEC = GitHubListSpec.wrapper(
            GitHubPageRequestKindValidators.PAGE,
            GitHubRunnerGroupRunnersForEnterprise.class,
            GitHubRunnerGroupRunnersForEnterprise::runners,
            GitHubRunnerGroupRunnersForEnterprise::totalCount);

    /**
     * Self-hosted runner groups resource over {@code caller}.
     *
     * @param caller executes and decodes the calls of this resource
     * @throws NullPointerException if {@code caller} is {@code null}
     */
    public GitHubActionsSelfHostedRunnerGroupsApi(final GitHubCaller caller) {
        super(caller);
    }

    /**
     * List self-hosted runner groups of an organization ({@code GET /orgs/{org}/actions/runner-groups}).
     *
     * <p>Uses {@link GitHubPageRequest#byPage()} and an empty filter.
     *
     * @param org organization name
     * @return first HTTP page of runner groups
     * @throws IllegalArgumentException if {@code org} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubRunnerGroupsOrg> listSelfHostedRunnerGroupsForOrg(final String org) throws IOException {
        return listSelfHostedRunnerGroupsForOrg(org, GitHubPageRequest.byPage());
    }

    /**
     * List self-hosted runner groups of an organization ({@code GET /orgs/{org}/actions/runner-groups}).
     *
     * @param org organization name
     * @param request pagination token
     * @return first HTTP page of runner groups
     * @throws IllegalArgumentException if {@code org} is blank, or {@code request} is invalid
     * @throws NullPointerException if {@code request} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubRunnerGroupsOrg> listSelfHostedRunnerGroupsForOrg(
            final String org, final GitHubPageRequest request) throws IOException {
        return listSelfHostedRunnerGroupsForOrg(org, GitHubListSelfHostedRunnerGroupsForOrgFilter.empty(), request);
    }

    /**
     * List self-hosted runner groups of an organization ({@code GET /orgs/{org}/actions/runner-groups}).
     *
     * @param org organization name
     * @param filter query filters
     * @param request pagination token
     * @return first HTTP page of runner groups
     * @throws IllegalArgumentException if {@code org} is blank, or {@code request} is invalid
     * @throws NullPointerException if {@code filter} or {@code request} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubRunnerGroupsOrg> listSelfHostedRunnerGroupsForOrg(
            final String org,
            final GitHubListSelfHostedRunnerGroupsForOrgFilter filter,
            final GitHubPageRequest request)
            throws IOException {
        requireNonNull(filter, PARAM_FILTER);
        requireNonNull(request, PARAM_REQUEST);

        return list(ORG_RUNNER_GROUPS, filter.toQueryParameters(), request, RUNNER_GROUPS_SPEC, org);
    }

    /**
     * Create a self-hosted runner group for an organization ({@code POST /orgs/{org}/actions/runner-groups}).
     *
     * @param org organization name
     * @param payload group settings
     * @return created runner group
     * @throws IllegalArgumentException if {@code org} is blank
     * @throws NullPointerException if {@code payload} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubRunnerGroupsOrg createSelfHostedRunnerGroupForOrg(
            final String org, final GitHubCreateSelfHostedRunnerGroupForOrgPayload payload) throws IOException {
        requireNonNull(payload, PARAM_PAYLOAD);

        return caller.fetch(caller.json(post(ORG_RUNNER_GROUPS, org), payload), GitHubRunnerGroupsOrg.class);
    }

    /**
     * Get a self-hosted runner group for an organization ({@code GET
     * /orgs/{org}/actions/runner-groups/{runner_group_id}}).
     *
     * @param org organization name
     * @param runnerGroupId runner group ID
     * @return runner group, or empty on HTTP 404
     * @throws IllegalArgumentException if {@code org} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public Optional<GitHubRunnerGroupsOrg> getSelfHostedRunnerGroupForOrg(final String org, final long runnerGroupId)
            throws IOException {
        return caller.fetchOrEmpty(get(ORG_RUNNER_GROUP, org, runnerGroupId), GitHubRunnerGroupsOrg.class);
    }

    /**
     * Update a self-hosted runner group for an organization ({@code PATCH
     * /orgs/{org}/actions/runner-groups/{runner_group_id}}).
     *
     * @param org organization name
     * @param runnerGroupId runner group ID
     * @param payload changed settings
     * @return updated runner group
     * @throws IllegalArgumentException if {@code org} is blank
     * @throws NullPointerException if {@code payload} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubRunnerGroupsOrg updateSelfHostedRunnerGroupForOrg(
            final String org, final long runnerGroupId, final GitHubUpdateSelfHostedRunnerGroupForOrgPayload payload)
            throws IOException {
        requireNonNull(payload, PARAM_PAYLOAD);

        return caller.fetch(
                caller.json(patch(ORG_RUNNER_GROUP, org, runnerGroupId), payload), GitHubRunnerGroupsOrg.class);
    }

    /**
     * Delete a self-hosted runner group from an organization ({@code DELETE
     * /orgs/{org}/actions/runner-groups/{runner_group_id}}).
     *
     * @param org organization name
     * @param runnerGroupId runner group ID
     * @throws IllegalArgumentException if {@code org} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public void deleteSelfHostedRunnerGroupFromOrg(final String org, final long runnerGroupId) throws IOException {
        caller.send(delete(ORG_RUNNER_GROUP, org, runnerGroupId));
    }

    /**
     * List GitHub-hosted runners in a runner group ({@code GET
     * /orgs/{org}/actions/runner-groups/{runner_group_id}/hosted-runners}).
     *
     * <p>Uses {@link GitHubPageRequest#byPage()}.
     *
     * @param org organization name
     * @param runnerGroupId runner group ID
     * @return first HTTP page of hosted runners
     * @throws IllegalArgumentException if {@code org} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public GitHubPage<GitHubActionsHostedRunner> listGitHubHostedRunnersInGroupForOrg(
            final String org, final long runnerGroupId) throws IOException {
        return listGitHubHostedRunnersInGroupForOrg(org, runnerGroupId, GitHubPageRequest.byPage());
    }

    /**
     * List GitHub-hosted runners in a runner group ({@code GET
     * /orgs/{org}/actions/runner-groups/{runner_group_id}/hosted-runners}).
     *
     * @param org organization name
     * @param runnerGroupId runner group ID
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
    public GitHubPage<GitHubActionsHostedRunner> listGitHubHostedRunnersInGroupForOrg(
            final String org, final long runnerGroupId, final GitHubPageRequest request) throws IOException {
        requireFlavor(GitHubFlavor.CLOUD, GitHubFlavor.GHEC);
        requireNonNull(request, PARAM_REQUEST);

        return list(ORG_RUNNER_GROUP_HOSTED_RUNNERS, Map.of(), request, HOSTED_RUNNERS_SPEC, org, runnerGroupId);
    }

    /**
     * List repositories with access to a self-hosted runner group ({@code GET
     * /orgs/{org}/actions/runner-groups/{runner_group_id}/repositories}).
     *
     * <p>Uses {@link GitHubPageRequest#byPage()}.
     *
     * @param org organization name
     * @param runnerGroupId runner group ID
     * @return first HTTP page of repositories
     * @throws IllegalArgumentException if {@code org} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubMinimalRepository> listRepoAccessToSelfHostedRunnerGroupInOrg(
            final String org, final long runnerGroupId) throws IOException {
        return listRepoAccessToSelfHostedRunnerGroupInOrg(org, runnerGroupId, GitHubPageRequest.byPage());
    }

    /**
     * List repositories with access to a self-hosted runner group ({@code GET
     * /orgs/{org}/actions/runner-groups/{runner_group_id}/repositories}).
     *
     * @param org organization name
     * @param runnerGroupId runner group ID
     * @param request pagination token
     * @return first HTTP page of repositories
     * @throws IllegalArgumentException if {@code org} is blank, or {@code request} is invalid
     * @throws NullPointerException if {@code request} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubMinimalRepository> listRepoAccessToSelfHostedRunnerGroupInOrg(
            final String org, final long runnerGroupId, final GitHubPageRequest request) throws IOException {
        requireNonNull(request, PARAM_REQUEST);

        return list(ORG_RUNNER_GROUP_REPOSITORIES, Map.of(), request, REPOSITORIES_SPEC, org, runnerGroupId);
    }

    /**
     * Set repositories with access to a self-hosted runner group ({@code PUT
     * /orgs/{org}/actions/runner-groups/{runner_group_id}/repositories}).
     *
     * @param org organization name
     * @param runnerGroupId runner group ID
     * @param payload repository IDs
     * @throws IllegalArgumentException if {@code org} is blank
     * @throws NullPointerException if {@code payload} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public void setRepoAccessToSelfHostedRunnerGroupInOrg(
            final String org,
            final long runnerGroupId,
            final GitHubSetRepoAccessToSelfHostedRunnerGroupInOrgPayload payload)
            throws IOException {
        requireNonNull(payload, PARAM_PAYLOAD);

        caller.send(caller.json(put(ORG_RUNNER_GROUP_REPOSITORIES, org, runnerGroupId), payload));
    }

    /**
     * Add a repository to a self-hosted runner group ({@code PUT
     * /orgs/{org}/actions/runner-groups/{runner_group_id}/repositories/{repository_id}}).
     *
     * @param org organization name
     * @param runnerGroupId runner group ID
     * @param repositoryId repository ID
     * @throws IllegalArgumentException if {@code org} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public void addRepoAccessToSelfHostedRunnerGroupInOrg(
            final String org, final long runnerGroupId, final long repositoryId) throws IOException {
        caller.send(put(ORG_RUNNER_GROUP_REPOSITORY, org, runnerGroupId, repositoryId));
    }

    /**
     * Remove a repository from a self-hosted runner group ({@code DELETE
     * /orgs/{org}/actions/runner-groups/{runner_group_id}/repositories/{repository_id}}).
     *
     * @param org organization name
     * @param runnerGroupId runner group ID
     * @param repositoryId repository ID
     * @throws IllegalArgumentException if {@code org} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public void removeRepoAccessToSelfHostedRunnerGroupInOrg(
            final String org, final long runnerGroupId, final long repositoryId) throws IOException {
        caller.send(delete(ORG_RUNNER_GROUP_REPOSITORY, org, runnerGroupId, repositoryId));
    }

    /**
     * List self-hosted runners in a runner group ({@code GET
     * /orgs/{org}/actions/runner-groups/{runner_group_id}/runners}).
     *
     * <p>Uses {@link GitHubPageRequest#byPage()}.
     *
     * @param org organization name
     * @param runnerGroupId runner group ID
     * @return first HTTP page of runners
     * @throws IllegalArgumentException if {@code org} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubRunner> listSelfHostedRunnersInGroupForOrg(final String org, final long runnerGroupId)
            throws IOException {
        return listSelfHostedRunnersInGroupForOrg(org, runnerGroupId, GitHubPageRequest.byPage());
    }

    /**
     * List self-hosted runners in a runner group ({@code GET
     * /orgs/{org}/actions/runner-groups/{runner_group_id}/runners}).
     *
     * @param org organization name
     * @param runnerGroupId runner group ID
     * @param request pagination token
     * @return first HTTP page of runners
     * @throws IllegalArgumentException if {@code org} is blank, or {@code request} is invalid
     * @throws NullPointerException if {@code request} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubRunner> listSelfHostedRunnersInGroupForOrg(
            final String org, final long runnerGroupId, final GitHubPageRequest request) throws IOException {
        requireNonNull(request, PARAM_REQUEST);

        return list(ORG_RUNNER_GROUP_RUNNERS, Map.of(), request, RUNNERS_SPEC, org, runnerGroupId);
    }

    /**
     * Set self-hosted runners in a runner group ({@code PUT
     * /orgs/{org}/actions/runner-groups/{runner_group_id}/runners}).
     *
     * @param org organization name
     * @param runnerGroupId runner group ID
     * @param payload runner IDs
     * @throws IllegalArgumentException if {@code org} is blank
     * @throws NullPointerException if {@code payload} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public void setSelfHostedRunnersInGroupForOrg(
            final String org, final long runnerGroupId, final GitHubSetSelfHostedRunnersInGroupForOrgPayload payload)
            throws IOException {
        requireNonNull(payload, PARAM_PAYLOAD);

        caller.send(caller.json(put(ORG_RUNNER_GROUP_RUNNERS, org, runnerGroupId), payload));
    }

    /**
     * Add a self-hosted runner to a runner group ({@code PUT
     * /orgs/{org}/actions/runner-groups/{runner_group_id}/runners/{runner_id}}).
     *
     * @param org organization name
     * @param runnerGroupId runner group ID
     * @param runnerId runner ID
     * @throws IllegalArgumentException if {@code org} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public void addSelfHostedRunnerToGroupForOrg(final String org, final long runnerGroupId, final long runnerId)
            throws IOException {
        caller.send(put(ORG_RUNNER_GROUP_RUNNER, org, runnerGroupId, runnerId));
    }

    /**
     * Remove a self-hosted runner from a runner group ({@code DELETE
     * /orgs/{org}/actions/runner-groups/{runner_group_id}/runners/{runner_id}}).
     *
     * @param org organization name
     * @param runnerGroupId runner group ID
     * @param runnerId runner ID
     * @throws IllegalArgumentException if {@code org} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public void removeSelfHostedRunnerFromGroupForOrg(final String org, final long runnerGroupId, final long runnerId)
            throws IOException {
        caller.send(delete(ORG_RUNNER_GROUP_RUNNER, org, runnerGroupId, runnerId));
    }

    /**
     * List self-hosted runner groups of an enterprise ({@code GET /enterprises/{enterprise}/actions/runner-groups}).
     *
     * <p>Uses {@link GitHubPageRequest#byPage()} and an empty filter.
     *
     * @param enterprise enterprise slug
     * @return first HTTP page of runner groups
     * @throws IllegalArgumentException if {@code enterprise} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHEC} or
     *     {@code GHES}
     */
    @Flavors({GitHubFlavor.GHEC, GitHubFlavor.GHES})
    public GitHubPage<GitHubRunnerGroupsEnterprise> listSelfHostedRunnerGroupsForEnterprise(final String enterprise)
            throws IOException {
        return listSelfHostedRunnerGroupsForEnterprise(enterprise, GitHubPageRequest.byPage());
    }

    /**
     * List self-hosted runner groups of an enterprise ({@code GET /enterprises/{enterprise}/actions/runner-groups}).
     *
     * @param enterprise enterprise slug
     * @param request pagination token
     * @return first HTTP page of runner groups
     * @throws IllegalArgumentException if {@code enterprise} is blank, or {@code request} is invalid
     * @throws NullPointerException if {@code request} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHEC} or
     *     {@code GHES}
     */
    @Flavors({GitHubFlavor.GHEC, GitHubFlavor.GHES})
    public GitHubPage<GitHubRunnerGroupsEnterprise> listSelfHostedRunnerGroupsForEnterprise(
            final String enterprise, final GitHubPageRequest request) throws IOException {
        return listSelfHostedRunnerGroupsForEnterprise(
                enterprise, GitHubListSelfHostedRunnerGroupsForEnterpriseFilter.empty(), request);
    }

    /**
     * List self-hosted runner groups of an enterprise ({@code GET /enterprises/{enterprise}/actions/runner-groups}).
     *
     * @param enterprise enterprise slug
     * @param filter query filters
     * @param request pagination token
     * @return first HTTP page of runner groups
     * @throws IllegalArgumentException if {@code enterprise} is blank, or {@code request} is invalid
     * @throws NullPointerException if {@code filter} or {@code request} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHEC} or
     *     {@code GHES}
     */
    @Flavors({GitHubFlavor.GHEC, GitHubFlavor.GHES})
    public GitHubPage<GitHubRunnerGroupsEnterprise> listSelfHostedRunnerGroupsForEnterprise(
            final String enterprise,
            final GitHubListSelfHostedRunnerGroupsForEnterpriseFilter filter,
            final GitHubPageRequest request)
            throws IOException {
        requireFlavor(GitHubFlavor.GHEC, GitHubFlavor.GHES);
        requireNonNull(filter, PARAM_FILTER);
        requireNonNull(request, PARAM_REQUEST);

        return list(
                ENTERPRISE_RUNNER_GROUPS,
                filter.toQueryParameters(),
                request,
                ENTERPRISE_RUNNER_GROUPS_SPEC,
                enterprise);
    }

    /**
     * Create a self-hosted runner group for an enterprise ({@code POST
     * /enterprises/{enterprise}/actions/runner-groups}).
     *
     * @param enterprise enterprise slug
     * @param payload group settings
     * @return created runner group
     * @throws IllegalArgumentException if {@code enterprise} is blank
     * @throws NullPointerException if {@code payload} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHEC} or
     *     {@code GHES}
     */
    @Flavors({GitHubFlavor.GHEC, GitHubFlavor.GHES})
    public GitHubRunnerGroupsEnterprise createSelfHostedRunnerGroupForEnterprise(
            final String enterprise, final GitHubCreateSelfHostedRunnerGroupForEnterprisePayload payload)
            throws IOException {
        requireFlavor(GitHubFlavor.GHEC, GitHubFlavor.GHES);
        requireNonNull(payload, PARAM_PAYLOAD);

        return caller.fetch(
                caller.json(post(ENTERPRISE_RUNNER_GROUPS, enterprise), payload), GitHubRunnerGroupsEnterprise.class);
    }

    /**
     * Get a self-hosted runner group for an enterprise ({@code GET
     * /enterprises/{enterprise}/actions/runner-groups/{runner_group_id}}).
     *
     * @param enterprise enterprise slug
     * @param runnerGroupId runner group ID
     * @return runner group, or empty on HTTP 404
     * @throws IllegalArgumentException if {@code enterprise} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHEC} or
     *     {@code GHES}
     */
    @Flavors({GitHubFlavor.GHEC, GitHubFlavor.GHES})
    public Optional<GitHubRunnerGroupsEnterprise> getSelfHostedRunnerGroupForEnterprise(
            final String enterprise, final long runnerGroupId) throws IOException {
        requireFlavor(GitHubFlavor.GHEC, GitHubFlavor.GHES);
        return caller.fetchOrEmpty(
                get(ENTERPRISE_RUNNER_GROUP, enterprise, runnerGroupId), GitHubRunnerGroupsEnterprise.class);
    }

    /**
     * Update a self-hosted runner group for an enterprise ({@code PATCH
     * /enterprises/{enterprise}/actions/runner-groups/{runner_group_id}}).
     *
     * @param enterprise enterprise slug
     * @param runnerGroupId runner group ID
     * @param payload changed settings
     * @return updated runner group
     * @throws IllegalArgumentException if {@code enterprise} is blank
     * @throws NullPointerException if {@code payload} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHEC} or
     *     {@code GHES}
     */
    @Flavors({GitHubFlavor.GHEC, GitHubFlavor.GHES})
    public GitHubRunnerGroupsEnterprise updateSelfHostedRunnerGroupForEnterprise(
            final String enterprise,
            final long runnerGroupId,
            final GitHubUpdateSelfHostedRunnerGroupForEnterprisePayload payload)
            throws IOException {
        requireFlavor(GitHubFlavor.GHEC, GitHubFlavor.GHES);
        requireNonNull(payload, PARAM_PAYLOAD);

        return caller.fetch(
                caller.json(patch(ENTERPRISE_RUNNER_GROUP, enterprise, runnerGroupId), payload),
                GitHubRunnerGroupsEnterprise.class);
    }

    /**
     * Delete a self-hosted runner group from an enterprise ({@code DELETE
     * /enterprises/{enterprise}/actions/runner-groups/{runner_group_id}}).
     *
     * @param enterprise enterprise slug
     * @param runnerGroupId runner group ID
     * @throws IllegalArgumentException if {@code enterprise} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHEC} or
     *     {@code GHES}
     */
    @Flavors({GitHubFlavor.GHEC, GitHubFlavor.GHES})
    public void deleteSelfHostedRunnerGroupFromEnterprise(final String enterprise, final long runnerGroupId)
            throws IOException {
        requireFlavor(GitHubFlavor.GHEC, GitHubFlavor.GHES);
        caller.send(delete(ENTERPRISE_RUNNER_GROUP, enterprise, runnerGroupId));
    }

    /**
     * List organizations with access to a self-hosted runner group of an enterprise ({@code GET
     * /enterprises/{enterprise}/actions/runner-groups/{runner_group_id}/organizations}).
     *
     * <p>Uses {@link GitHubPageRequest#byPage()}.
     *
     * @param enterprise enterprise slug
     * @param runnerGroupId runner group ID
     * @return first HTTP page of organizations
     * @throws IllegalArgumentException if {@code enterprise} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHEC} or
     *     {@code GHES}
     */
    @Flavors({GitHubFlavor.GHEC, GitHubFlavor.GHES})
    public GitHubPage<GitHubOrganizationSimple> listOrgAccessToSelfHostedRunnerGroupInEnterprise(
            final String enterprise, final long runnerGroupId) throws IOException {
        return listOrgAccessToSelfHostedRunnerGroupInEnterprise(enterprise, runnerGroupId, GitHubPageRequest.byPage());
    }

    /**
     * List organizations with access to a self-hosted runner group of an enterprise ({@code GET
     * /enterprises/{enterprise}/actions/runner-groups/{runner_group_id}/organizations}).
     *
     * @param enterprise enterprise slug
     * @param runnerGroupId runner group ID
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
    public GitHubPage<GitHubOrganizationSimple> listOrgAccessToSelfHostedRunnerGroupInEnterprise(
            final String enterprise, final long runnerGroupId, final GitHubPageRequest request) throws IOException {
        requireFlavor(GitHubFlavor.GHEC, GitHubFlavor.GHES);
        requireNonNull(request, PARAM_REQUEST);

        return list(
                ENTERPRISE_RUNNER_GROUP_ORGANIZATIONS,
                Map.of(),
                request,
                ENTERPRISE_ORGANIZATIONS_SPEC,
                enterprise,
                runnerGroupId);
    }

    /**
     * Set organizations with access to a self-hosted runner group of an enterprise ({@code PUT
     * /enterprises/{enterprise}/actions/runner-groups/{runner_group_id}/organizations}).
     *
     * @param enterprise enterprise slug
     * @param runnerGroupId runner group ID
     * @param payload organization IDs
     * @throws IllegalArgumentException if {@code enterprise} is blank
     * @throws NullPointerException if {@code payload} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHEC} or
     *     {@code GHES}
     */
    @Flavors({GitHubFlavor.GHEC, GitHubFlavor.GHES})
    public void setOrgAccessToSelfHostedRunnerGroupInEnterprise(
            final String enterprise,
            final long runnerGroupId,
            final GitHubSetOrgAccessToSelfHostedRunnerGroupInEnterprisePayload payload)
            throws IOException {
        requireFlavor(GitHubFlavor.GHEC, GitHubFlavor.GHES);
        requireNonNull(payload, PARAM_PAYLOAD);

        caller.send(caller.json(put(ENTERPRISE_RUNNER_GROUP_ORGANIZATIONS, enterprise, runnerGroupId), payload));
    }

    /**
     * Add an organization to a self-hosted runner group of an enterprise ({@code PUT
     * /enterprises/{enterprise}/actions/runner-groups/{runner_group_id}/organizations/{org_id}}).
     *
     * @param enterprise enterprise slug
     * @param runnerGroupId runner group ID
     * @param orgId organization ID
     * @throws IllegalArgumentException if {@code enterprise} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHEC} or
     *     {@code GHES}
     */
    @Flavors({GitHubFlavor.GHEC, GitHubFlavor.GHES})
    public void addOrgAccessToSelfHostedRunnerGroupInEnterprise(
            final String enterprise, final long runnerGroupId, final long orgId) throws IOException {
        requireFlavor(GitHubFlavor.GHEC, GitHubFlavor.GHES);
        caller.send(put(ENTERPRISE_RUNNER_GROUP_ORGANIZATION, enterprise, runnerGroupId, orgId));
    }

    /**
     * Remove an organization from a self-hosted runner group of an enterprise ({@code DELETE
     * /enterprises/{enterprise}/actions/runner-groups/{runner_group_id}/organizations/{org_id}}).
     *
     * @param enterprise enterprise slug
     * @param runnerGroupId runner group ID
     * @param orgId organization ID
     * @throws IllegalArgumentException if {@code enterprise} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHEC} or
     *     {@code GHES}
     */
    @Flavors({GitHubFlavor.GHEC, GitHubFlavor.GHES})
    public void removeOrgAccessToSelfHostedRunnerGroupInEnterprise(
            final String enterprise, final long runnerGroupId, final long orgId) throws IOException {
        requireFlavor(GitHubFlavor.GHEC, GitHubFlavor.GHES);
        caller.send(delete(ENTERPRISE_RUNNER_GROUP_ORGANIZATION, enterprise, runnerGroupId, orgId));
    }

    /**
     * List self-hosted runners in a runner group of an enterprise ({@code GET
     * /enterprises/{enterprise}/actions/runner-groups/{runner_group_id}/runners}).
     *
     * <p>Uses {@link GitHubPageRequest#byPage()}.
     *
     * @param enterprise enterprise slug
     * @param runnerGroupId runner group ID
     * @return first HTTP page of runners
     * @throws IllegalArgumentException if {@code enterprise} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHEC} or
     *     {@code GHES}
     */
    @Flavors({GitHubFlavor.GHEC, GitHubFlavor.GHES})
    public GitHubPage<GitHubRunner> listSelfHostedRunnersInGroupForEnterprise(
            final String enterprise, final long runnerGroupId) throws IOException {
        return listSelfHostedRunnersInGroupForEnterprise(enterprise, runnerGroupId, GitHubPageRequest.byPage());
    }

    /**
     * List self-hosted runners in a runner group of an enterprise ({@code GET
     * /enterprises/{enterprise}/actions/runner-groups/{runner_group_id}/runners}).
     *
     * @param enterprise enterprise slug
     * @param runnerGroupId runner group ID
     * @param request pagination token
     * @return first HTTP page of runners
     * @throws IllegalArgumentException if {@code enterprise} is blank, or {@code request} is invalid
     * @throws NullPointerException if {@code request} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHEC} or
     *     {@code GHES}
     */
    @Flavors({GitHubFlavor.GHEC, GitHubFlavor.GHES})
    public GitHubPage<GitHubRunner> listSelfHostedRunnersInGroupForEnterprise(
            final String enterprise, final long runnerGroupId, final GitHubPageRequest request) throws IOException {
        requireFlavor(GitHubFlavor.GHEC, GitHubFlavor.GHES);
        requireNonNull(request, PARAM_REQUEST);

        return list(
                ENTERPRISE_RUNNER_GROUP_RUNNERS, Map.of(), request, ENTERPRISE_RUNNERS_SPEC, enterprise, runnerGroupId);
    }

    /**
     * Set self-hosted runners in a runner group of an enterprise ({@code PUT
     * /enterprises/{enterprise}/actions/runner-groups/{runner_group_id}/runners}).
     *
     * @param enterprise enterprise slug
     * @param runnerGroupId runner group ID
     * @param payload runner IDs
     * @throws IllegalArgumentException if {@code enterprise} is blank
     * @throws NullPointerException if {@code payload} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHEC} or
     *     {@code GHES}
     */
    @Flavors({GitHubFlavor.GHEC, GitHubFlavor.GHES})
    public void setSelfHostedRunnersInGroupForEnterprise(
            final String enterprise,
            final long runnerGroupId,
            final GitHubSetSelfHostedRunnersInGroupForEnterprisePayload payload)
            throws IOException {
        requireFlavor(GitHubFlavor.GHEC, GitHubFlavor.GHES);
        requireNonNull(payload, PARAM_PAYLOAD);

        caller.send(caller.json(put(ENTERPRISE_RUNNER_GROUP_RUNNERS, enterprise, runnerGroupId), payload));
    }

    /**
     * Add a self-hosted runner to a runner group of an enterprise ({@code PUT
     * /enterprises/{enterprise}/actions/runner-groups/{runner_group_id}/runners/{runner_id}}).
     *
     * @param enterprise enterprise slug
     * @param runnerGroupId runner group ID
     * @param runnerId runner ID
     * @throws IllegalArgumentException if {@code enterprise} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHEC} or
     *     {@code GHES}
     */
    @Flavors({GitHubFlavor.GHEC, GitHubFlavor.GHES})
    public void addSelfHostedRunnerToGroupForEnterprise(
            final String enterprise, final long runnerGroupId, final long runnerId) throws IOException {
        requireFlavor(GitHubFlavor.GHEC, GitHubFlavor.GHES);
        caller.send(put(ENTERPRISE_RUNNER_GROUP_RUNNER, enterprise, runnerGroupId, runnerId));
    }

    /**
     * Remove a self-hosted runner from a runner group of an enterprise ({@code DELETE
     * /enterprises/{enterprise}/actions/runner-groups/{runner_group_id}/runners/{runner_id}}).
     *
     * @param enterprise enterprise slug
     * @param runnerGroupId runner group ID
     * @param runnerId runner ID
     * @throws IllegalArgumentException if {@code enterprise} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHEC} or
     *     {@code GHES}
     */
    @Flavors({GitHubFlavor.GHEC, GitHubFlavor.GHES})
    public void removeSelfHostedRunnerFromGroupForEnterprise(
            final String enterprise, final long runnerGroupId, final long runnerId) throws IOException {
        requireFlavor(GitHubFlavor.GHEC, GitHubFlavor.GHES);
        caller.send(delete(ENTERPRISE_RUNNER_GROUP_RUNNER, enterprise, runnerGroupId, runnerId));
    }
}
