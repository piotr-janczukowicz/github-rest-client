package io.github.piotr_janczukowicz.github.api.codesecurity;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import io.github.piotr_janczukowicz.github.client.GitHubCaller;
import io.github.piotr_janczukowicz.github.client.GitHubResourceApi;
import io.github.piotr_janczukowicz.github.client.exception.GitHubApiException;
import io.github.piotr_janczukowicz.github.client.pagination.GitHubListSpec;
import io.github.piotr_janczukowicz.github.pagination.GitHubPage;
import io.github.piotr_janczukowicz.github.pagination.GitHubPageRequest;
import io.github.piotr_janczukowicz.github.pagination.GitHubPageRequestKindValidators;
import io.github.piotr_janczukowicz.json.JsonType;
import io.github.piotr_janczukowicz.json.JsonTypes;
import io.github.piotr_janczukowicz.uri.PathTemplate;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/** GitHub code security configurations REST resource for API version {@code 2026-03-10}. */
public final class GitHubCodeSecurityApi extends GitHubResourceApi {

    private static final PathTemplate ENTERPRISE_CONFIGURATIONS =
            PathTemplate.of("/enterprises/{enterprise}/code-security/configurations");
    private static final PathTemplate ENTERPRISE_CONFIGURATION =
            PathTemplate.of("/enterprises/{enterprise}/code-security/configurations/{configuration_id}");
    private static final PathTemplate ENTERPRISE_DEFAULT_CONFIGURATIONS =
            PathTemplate.of("/enterprises/{enterprise}/code-security/configurations/defaults");
    private static final PathTemplate ENTERPRISE_CONFIGURATION_ATTACH =
            PathTemplate.of("/enterprises/{enterprise}/code-security/configurations/{configuration_id}/attach");
    private static final PathTemplate ENTERPRISE_CONFIGURATION_DEFAULTS =
            PathTemplate.of("/enterprises/{enterprise}/code-security/configurations/{configuration_id}/defaults");
    private static final PathTemplate ENTERPRISE_CONFIGURATION_REPOSITORIES =
            PathTemplate.of("/enterprises/{enterprise}/code-security/configurations/{configuration_id}/repositories");
    private static final PathTemplate ORG_CONFIGURATIONS = PathTemplate.of("/orgs/{org}/code-security/configurations");
    private static final PathTemplate ORG_DEFAULT_CONFIGURATIONS =
            PathTemplate.of("/orgs/{org}/code-security/configurations/defaults");
    private static final PathTemplate ORG_CONFIGURATIONS_DETACH =
            PathTemplate.of("/orgs/{org}/code-security/configurations/detach");
    private static final PathTemplate ORG_CONFIGURATION =
            PathTemplate.of("/orgs/{org}/code-security/configurations/{configuration_id}");
    private static final PathTemplate ORG_CONFIGURATION_ATTACH =
            PathTemplate.of("/orgs/{org}/code-security/configurations/{configuration_id}/attach");
    private static final PathTemplate ORG_CONFIGURATION_DEFAULTS =
            PathTemplate.of("/orgs/{org}/code-security/configurations/{configuration_id}/defaults");
    private static final PathTemplate ORG_CONFIGURATION_REPOSITORIES =
            PathTemplate.of("/orgs/{org}/code-security/configurations/{configuration_id}/repositories");
    private static final PathTemplate REPO_CODE_SECURITY_CONFIGURATION =
            PathTemplate.of("/repos/{owner}/{repo}/code-security-configuration");

    private static final JsonType<List<GitHubCodeSecurityDefaultConfiguration>> DEFAULT_CONFIGURATIONS_TYPE =
            JsonTypes.listOf(GitHubCodeSecurityDefaultConfiguration.class);

    private static final GitHubListSpec<GitHubCodeSecurityConfiguration> CONFIGURATIONS_SPEC = GitHubListSpec.array(
            GitHubPageRequestKindValidators.CURSOR, JsonTypes.listOf(GitHubCodeSecurityConfiguration.class));
    private static final GitHubListSpec<GitHubCodeSecurityConfigurationRepositories> REPOSITORIES_SPEC =
            GitHubListSpec.array(
                    GitHubPageRequestKindValidators.CURSOR,
                    JsonTypes.listOf(GitHubCodeSecurityConfigurationRepositories.class));

    /**
     * Code security configurations resource over {@code caller}.
     *
     * @param caller executes and decodes the calls of this resource
     * @throws NullPointerException if {@code caller} is {@code null}
     */
    public GitHubCodeSecurityApi(final GitHubCaller caller) {
        super(caller);
    }

    /**
     * List code security configurations for an enterprise ({@code GET
     * /enterprises/{enterprise}/code-security/configurations}).
     *
     * @param enterprise enterprise slug
     * @return first page of configurations
     * @throws IllegalArgumentException if {@code enterprise} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubCodeSecurityConfiguration> listConfigurationsForEnterprise(final String enterprise)
            throws IOException {
        return listConfigurationsForEnterprise(enterprise, GitHubPageRequest.byCursor());
    }

    /**
     * List code security configurations for an enterprise ({@code GET
     * /enterprises/{enterprise}/code-security/configurations}).
     *
     * @param enterprise enterprise slug
     * @param request cursor pagination token
     * @return page of configurations
     * @throws IllegalArgumentException if {@code enterprise} is blank or {@code request} is not a cursor
     * @throws NullPointerException if {@code request} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubCodeSecurityConfiguration> listConfigurationsForEnterprise(
            final String enterprise, final GitHubPageRequest request) throws IOException {
        requireNonNull(request, PARAM_REQUEST);

        return list(ENTERPRISE_CONFIGURATIONS, Map.of(), request, CONFIGURATIONS_SPEC, enterprise);
    }

    /**
     * Create a code security configuration for an enterprise ({@code POST
     * /enterprises/{enterprise}/code-security/configurations}).
     *
     * @param enterprise enterprise slug
     * @param payload configuration to create
     * @return created configuration
     * @throws IllegalArgumentException if {@code enterprise} is blank
     * @throws NullPointerException if {@code payload} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubCodeSecurityConfiguration createConfigurationForEnterprise(
            final String enterprise, final GitHubCreateConfigurationForEnterprisePayload payload) throws IOException {
        requireNonNull(payload, PARAM_PAYLOAD);

        return caller.fetch(
                caller.json(post(ENTERPRISE_CONFIGURATIONS, enterprise), payload),
                GitHubCodeSecurityConfiguration.class);
    }

    /**
     * Get default code security configurations for an enterprise ({@code GET
     * /enterprises/{enterprise}/code-security/configurations/defaults}).
     *
     * @param enterprise enterprise slug
     * @return default configurations
     * @throws IllegalArgumentException if {@code enterprise} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public List<GitHubCodeSecurityDefaultConfiguration> getDefaultConfigurationsForEnterprise(final String enterprise)
            throws IOException {
        return caller.fetch(get(ENTERPRISE_DEFAULT_CONFIGURATIONS, enterprise), DEFAULT_CONFIGURATIONS_TYPE);
    }

    /**
     * Get a code security configuration of an enterprise ({@code GET
     * /enterprises/{enterprise}/code-security/configurations/{configuration_id}}).
     *
     * @param enterprise enterprise slug
     * @param configurationId configuration id
     * @return configuration, or empty on HTTP 304 or 404
     * @throws IllegalArgumentException if {@code enterprise} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public Optional<GitHubCodeSecurityConfiguration> getSingleConfigurationForEnterprise(
            final String enterprise, final long configurationId) throws IOException {
        return caller.fetchOrEmpty(
                get(ENTERPRISE_CONFIGURATION, enterprise, configurationId), GitHubCodeSecurityConfiguration.class);
    }

    /**
     * Update a code security configuration of an enterprise ({@code PATCH
     * /enterprises/{enterprise}/code-security/configurations/{configuration_id}}).
     *
     * @param enterprise enterprise slug
     * @param configurationId configuration id
     * @param payload fields to update
     * @return updated configuration
     * @throws IllegalArgumentException if {@code enterprise} is blank
     * @throws NullPointerException if {@code payload} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubCodeSecurityConfiguration updateEnterpriseConfiguration(
            final String enterprise,
            final long configurationId,
            final GitHubUpdateEnterpriseConfigurationPayload payload)
            throws IOException {
        requireNonNull(payload, PARAM_PAYLOAD);

        return caller.fetch(
                caller.json(patch(ENTERPRISE_CONFIGURATION, enterprise, configurationId), payload),
                GitHubCodeSecurityConfiguration.class);
    }

    /**
     * Delete a code security configuration of an enterprise ({@code DELETE
     * /enterprises/{enterprise}/code-security/configurations/{configuration_id}}).
     *
     * @param enterprise enterprise slug
     * @param configurationId configuration id
     * @throws IllegalArgumentException if {@code enterprise} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public void deleteConfigurationForEnterprise(final String enterprise, final long configurationId)
            throws IOException {
        caller.send(delete(ENTERPRISE_CONFIGURATION, enterprise, configurationId));
    }

    /**
     * Attach an enterprise configuration to repositories ({@code POST
     * /enterprises/{enterprise}/code-security/configurations/{configuration_id}/attach}). The API answers HTTP 202
     * without a typed body.
     *
     * @param enterprise enterprise slug
     * @param configurationId configuration id
     * @param payload attachment scope
     * @throws IllegalArgumentException if {@code enterprise} is blank
     * @throws NullPointerException if {@code payload} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public void attachEnterpriseConfiguration(
            final String enterprise,
            final long configurationId,
            final GitHubAttachEnterpriseConfigurationPayload payload)
            throws IOException {
        requireNonNull(payload, PARAM_PAYLOAD);

        caller.send(caller.json(post(ENTERPRISE_CONFIGURATION_ATTACH, enterprise, configurationId), payload));
    }

    /**
     * Set a code security configuration as a default for an enterprise ({@code PUT
     * /enterprises/{enterprise}/code-security/configurations/{configuration_id}/defaults}).
     *
     * @param enterprise enterprise slug
     * @param configurationId configuration id
     * @param payload default setting
     * @return result of the default change
     * @throws IllegalArgumentException if {@code enterprise} is blank
     * @throws NullPointerException if {@code payload} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubSetConfigurationAsDefaultForEnterpriseResponse makeConfigurationDefaultForEnterprise(
            final String enterprise,
            final long configurationId,
            final GitHubSetConfigurationAsDefaultForEnterprisePayload payload)
            throws IOException {
        requireNonNull(payload, PARAM_PAYLOAD);

        return caller.fetch(
                caller.json(put(ENTERPRISE_CONFIGURATION_DEFAULTS, enterprise, configurationId), payload),
                GitHubSetConfigurationAsDefaultForEnterpriseResponse.class);
    }

    /**
     * List repositories associated with an enterprise code security configuration ({@code GET
     * /enterprises/{enterprise}/code-security/configurations/{configuration_id}/repositories}).
     *
     * <p>Uses {@link GitHubPageRequest#byCursor()} and an empty filter. See
     * {@link #listRepositoriesForEnterpriseConfiguration(String, long,
     * GitHubGetRepositoriesForEnterpriseConfigurationFilter, GitHubPageRequest)}.
     *
     * @param enterprise enterprise slug
     * @param configurationId configuration id
     * @return first page of repositories
     * @throws IllegalArgumentException if {@code enterprise} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubCodeSecurityConfigurationRepositories> listRepositoriesForEnterpriseConfiguration(
            final String enterprise, final long configurationId) throws IOException {
        return listRepositoriesForEnterpriseConfiguration(
                enterprise,
                configurationId,
                GitHubGetRepositoriesForEnterpriseConfigurationFilter.empty(),
                GitHubPageRequest.byCursor());
    }

    /**
     * List repositories associated with an enterprise code security configuration ({@code GET
     * /enterprises/{enterprise}/code-security/configurations/{configuration_id}/repositories}).
     *
     * @param enterprise enterprise slug
     * @param configurationId configuration id
     * @param filter query filters (attachment status)
     * @param request cursor pagination token
     * @return page of repositories
     * @throws IllegalArgumentException if {@code enterprise} is blank or {@code request} is not a cursor
     * @throws NullPointerException if {@code filter} or {@code request} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubCodeSecurityConfigurationRepositories> listRepositoriesForEnterpriseConfiguration(
            final String enterprise,
            final long configurationId,
            final GitHubGetRepositoriesForEnterpriseConfigurationFilter filter,
            final GitHubPageRequest request)
            throws IOException {
        requireNonNull(filter, PARAM_FILTER);
        requireNonNull(request, PARAM_REQUEST);

        return list(
                ENTERPRISE_CONFIGURATION_REPOSITORIES,
                filter.toQueryParameters(),
                request,
                REPOSITORIES_SPEC,
                enterprise,
                configurationId);
    }

    /**
     * List code security configurations for an organization ({@code GET /orgs/{org}/code-security/configurations}).
     *
     * <p>Uses {@link GitHubPageRequest#byCursor()} and an empty filter. See {@link #listConfigurationsForOrg(String,
     * GitHubGetConfigurationsForOrgFilter, GitHubPageRequest)}.
     *
     * @param org organization name
     * @return first page of configurations
     * @throws IllegalArgumentException if {@code org} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubCodeSecurityConfiguration> listConfigurationsForOrg(final String org) throws IOException {
        return listConfigurationsForOrg(org, GitHubGetConfigurationsForOrgFilter.empty(), GitHubPageRequest.byCursor());
    }

    /**
     * List code security configurations for an organization ({@code GET /orgs/{org}/code-security/configurations}).
     *
     * @param org organization name
     * @param filter query filters (target type)
     * @param request cursor pagination token
     * @return page of configurations
     * @throws IllegalArgumentException if {@code org} is blank or {@code request} is not a cursor
     * @throws NullPointerException if {@code filter} or {@code request} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubCodeSecurityConfiguration> listConfigurationsForOrg(
            final String org, final GitHubGetConfigurationsForOrgFilter filter, final GitHubPageRequest request)
            throws IOException {
        requireNonNull(filter, PARAM_FILTER);
        requireNonNull(request, PARAM_REQUEST);

        return list(ORG_CONFIGURATIONS, filter.toQueryParameters(), request, CONFIGURATIONS_SPEC, org);
    }

    /**
     * Create a code security configuration for an organization ({@code POST /orgs/{org}/code-security/configurations}).
     *
     * @param org organization name
     * @param payload configuration to create
     * @return created configuration
     * @throws IllegalArgumentException if {@code org} is blank
     * @throws NullPointerException if {@code payload} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubCodeSecurityConfiguration createConfiguration(
            final String org, final GitHubCreateConfigurationPayload payload) throws IOException {
        requireNonNull(payload, PARAM_PAYLOAD);

        return caller.fetch(caller.json(post(ORG_CONFIGURATIONS, org), payload), GitHubCodeSecurityConfiguration.class);
    }

    /**
     * Get default code security configurations for an organization ({@code GET
     * /orgs/{org}/code-security/configurations/defaults}).
     *
     * @param org organization name
     * @return default configurations
     * @throws IllegalArgumentException if {@code org} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public List<GitHubCodeSecurityDefaultConfiguration> getDefaultConfigurations(final String org) throws IOException {
        return caller.fetch(get(ORG_DEFAULT_CONFIGURATIONS, org), DEFAULT_CONFIGURATIONS_TYPE);
    }

    /**
     * Detach configurations from repositories ({@code DELETE /orgs/{org}/code-security/configurations/detach}).
     *
     * @param org organization name
     * @param payload repositories to detach
     * @throws IllegalArgumentException if {@code org} is blank
     * @throws NullPointerException if {@code payload} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public void detachConfiguration(final String org, final GitHubDetachConfigurationPayload payload)
            throws IOException {
        requireNonNull(payload, PARAM_PAYLOAD);

        caller.send(caller.json(delete(ORG_CONFIGURATIONS_DETACH, org), payload));
    }

    /**
     * Get a code security configuration ({@code GET /orgs/{org}/code-security/configurations/{configuration_id}}).
     *
     * @param org organization name
     * @param configurationId configuration id
     * @return configuration, or empty on HTTP 304 or 404
     * @throws IllegalArgumentException if {@code org} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public Optional<GitHubCodeSecurityConfiguration> getConfiguration(final String org, final long configurationId)
            throws IOException {
        return caller.fetchOrEmpty(get(ORG_CONFIGURATION, org, configurationId), GitHubCodeSecurityConfiguration.class);
    }

    /**
     * Update a code security configuration ({@code PATCH /orgs/{org}/code-security/configurations/{configuration_id}}).
     *
     * @param org organization name
     * @param configurationId configuration id
     * @param payload fields to update
     * @return updated configuration, or empty on HTTP 204 when no new updates are made
     * @throws IllegalArgumentException if {@code org} is blank
     * @throws NullPointerException if {@code payload} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public Optional<GitHubCodeSecurityConfiguration> updateConfiguration(
            final String org, final long configurationId, final GitHubUpdateConfigurationPayload payload)
            throws IOException {
        requireNonNull(payload, PARAM_PAYLOAD);

        return caller.fetchOptional(
                caller.json(patch(ORG_CONFIGURATION, org, configurationId), payload),
                GitHubCodeSecurityConfiguration.class);
    }

    /**
     * Delete a code security configuration ({@code DELETE
     * /orgs/{org}/code-security/configurations/{configuration_id}}).
     *
     * @param org organization name
     * @param configurationId configuration id
     * @throws IllegalArgumentException if {@code org} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public void deleteConfiguration(final String org, final long configurationId) throws IOException {
        caller.send(delete(ORG_CONFIGURATION, org, configurationId));
    }

    /**
     * Attach a configuration to repositories ({@code POST
     * /orgs/{org}/code-security/configurations/{configuration_id}/attach}). The API answers HTTP 202 without a typed
     * body.
     *
     * @param org organization name
     * @param configurationId configuration id
     * @param payload attachment scope
     * @throws IllegalArgumentException if {@code org} is blank
     * @throws NullPointerException if {@code payload} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public void attachConfiguration(
            final String org, final long configurationId, final GitHubAttachConfigurationPayload payload)
            throws IOException {
        requireNonNull(payload, PARAM_PAYLOAD);

        caller.send(caller.json(post(ORG_CONFIGURATION_ATTACH, org, configurationId), payload));
    }

    /**
     * Set a code security configuration as a default for an organization ({@code PUT
     * /orgs/{org}/code-security/configurations/{configuration_id}/defaults}).
     *
     * @param org organization name
     * @param configurationId configuration id
     * @param payload default setting
     * @return result of the default change
     * @throws IllegalArgumentException if {@code org} is blank
     * @throws NullPointerException if {@code payload} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubSetConfigurationAsDefaultResponse makeConfigurationDefault(
            final String org, final long configurationId, final GitHubSetConfigurationAsDefaultPayload payload)
            throws IOException {
        requireNonNull(payload, PARAM_PAYLOAD);

        return caller.fetch(
                caller.json(put(ORG_CONFIGURATION_DEFAULTS, org, configurationId), payload),
                GitHubSetConfigurationAsDefaultResponse.class);
    }

    /**
     * List repositories associated with a code security configuration ({@code GET
     * /orgs/{org}/code-security/configurations/{configuration_id}/repositories}).
     *
     * <p>Uses {@link GitHubPageRequest#byCursor()} and an empty filter. See
     * {@link #listRepositoriesForConfiguration(String, long, GitHubGetRepositoriesForConfigurationFilter,
     * GitHubPageRequest)}.
     *
     * @param org organization name
     * @param configurationId configuration id
     * @return first page of repositories
     * @throws IllegalArgumentException if {@code org} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubCodeSecurityConfigurationRepositories> listRepositoriesForConfiguration(
            final String org, final long configurationId) throws IOException {
        return listRepositoriesForConfiguration(
                org,
                configurationId,
                GitHubGetRepositoriesForConfigurationFilter.empty(),
                GitHubPageRequest.byCursor());
    }

    /**
     * List repositories associated with a code security configuration ({@code GET
     * /orgs/{org}/code-security/configurations/{configuration_id}/repositories}).
     *
     * @param org organization name
     * @param configurationId configuration id
     * @param filter query filters (attachment status)
     * @param request cursor pagination token
     * @return page of repositories
     * @throws IllegalArgumentException if {@code org} is blank or {@code request} is not a cursor
     * @throws NullPointerException if {@code filter} or {@code request} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubCodeSecurityConfigurationRepositories> listRepositoriesForConfiguration(
            final String org,
            final long configurationId,
            final GitHubGetRepositoriesForConfigurationFilter filter,
            final GitHubPageRequest request)
            throws IOException {
        requireNonNull(filter, PARAM_FILTER);
        requireNonNull(request, PARAM_REQUEST);

        return list(
                ORG_CONFIGURATION_REPOSITORIES,
                filter.toQueryParameters(),
                request,
                REPOSITORIES_SPEC,
                org,
                configurationId);
    }

    /**
     * Get the code security configuration associated with a repository ({@code GET
     * /repos/{owner}/{repo}/code-security-configuration}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @return configuration attachment, or empty on HTTP 204 or 304
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public Optional<GitHubCodeSecurityConfigurationForRepository> getConfigurationForRepository(
            final String owner, final String repo) throws IOException {
        return caller.fetchOptional(
                get(REPO_CODE_SECURITY_CONFIGURATION, owner, repo), GitHubCodeSecurityConfigurationForRepository.class);
    }
}
