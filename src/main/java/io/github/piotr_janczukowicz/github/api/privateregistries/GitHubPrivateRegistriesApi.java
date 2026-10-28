package io.github.piotr_janczukowicz.github.api.privateregistries;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import io.github.piotr_janczukowicz.github.client.GitHubCaller;
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

/** GitHub organization private registry configurations REST resource for API version {@code 2026-03-10}. */
public final class GitHubPrivateRegistriesApi extends GitHubResourceApi {

    private static final PathTemplate PRIVATE_REGISTRY =
            PathTemplate.of("/orgs/{org}/private-registries/{secret_name}");
    private static final PathTemplate PRIVATE_REGISTRIES_PUBLIC_KEY =
            PathTemplate.of("/orgs/{org}/private-registries/public-key");
    private static final PathTemplate PRIVATE_REGISTRIES = PathTemplate.of("/orgs/{org}/private-registries");

    /**
     * Organization private registry configurations resource over {@code caller}.
     *
     * @param caller executes and decodes the calls of this resource
     * @throws NullPointerException if {@code caller} is {@code null}
     */
    public GitHubPrivateRegistriesApi(final GitHubCaller caller) {
        super(caller);
    }

    /**
     * List private registries for an organization ({@code GET /orgs/{org}/private-registries}).
     *
     * @param org organization name
     * @return first HTTP page of private registry configurations
     * @throws IllegalArgumentException if {@code org} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubOrgPrivateRegistryConfiguration> listOrgPrivateRegistries(final String org)
            throws IOException {
        return listOrgPrivateRegistries(org, GitHubPageRequest.byPage());
    }

    /**
     * List private registries for an organization ({@code GET /orgs/{org}/private-registries}).
     *
     * @param org organization name
     * @param request pagination token
     * @return first HTTP page of private registry configurations
     * @throws IllegalArgumentException if {@code org} is blank, or {@code request} is a since or cursor variant
     * @throws NullPointerException if {@code request} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubOrgPrivateRegistryConfiguration> listOrgPrivateRegistries(
            final String org, final GitHubPageRequest request) throws IOException {
        requireNonNull(request, PARAM_REQUEST);

        return list(
                PRIVATE_REGISTRIES,
                Map.of(),
                request,
                GitHubListSpec.wrapper(
                        GitHubPageRequestKindValidators.PAGE,
                        GitHubOrgPrivateRegistryConfigurationsPage.class,
                        GitHubOrgPrivateRegistryConfigurationsPage::configurations,
                        GitHubOrgPrivateRegistryConfigurationsPage::totalCount),
                org);
    }

    /**
     * Create a private registry for an organization ({@code POST /orgs/{org}/private-registries}).
     *
     * @param org organization name
     * @param payload private registry configuration
     * @return created private registry configuration
     * @throws NullPointerException if {@code payload} is {@code null}
     * @throws IllegalArgumentException if {@code org} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubOrgPrivateRegistryConfigurationWithSelectedRepositories createOrgPrivateRegistry(
            final String org, final GitHubCreateOrgPrivateRegistryPayload payload) throws IOException {
        requireNonNull(payload, PARAM_PAYLOAD);

        return caller.fetch(
                caller.json(post(PRIVATE_REGISTRIES, org), payload),
                GitHubOrgPrivateRegistryConfigurationWithSelectedRepositories.class);
    }

    /**
     * Get private registries public key for an organization ({@code GET /orgs/{org}/private-registries/public-key}).
     *
     * @param org organization name
     * @return public key
     * @throws IllegalArgumentException if {@code org} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPrivateRegistriesPublicKey getOrgPublicKey(final String org) throws IOException {
        return caller.fetch(get(PRIVATE_REGISTRIES_PUBLIC_KEY, org), GitHubPrivateRegistriesPublicKey.class);
    }

    /**
     * Get a private registry for an organization ({@code GET /orgs/{org}/private-registries/{secret_name}}).
     *
     * @param org organization name
     * @param secretName secret name
     * @return private registry configuration, or empty on HTTP 404
     * @throws IllegalArgumentException if {@code org} or {@code secretName} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful (HTTP 404 yields empty)
     */
    public Optional<GitHubOrgPrivateRegistryConfiguration> getOrgPrivateRegistry(
            final String org, final String secretName) throws IOException {

        return caller.fetchOrEmpty(get(PRIVATE_REGISTRY, org, secretName), GitHubOrgPrivateRegistryConfiguration.class);
    }

    /**
     * Update a private registry for an organization ({@code PATCH /orgs/{org}/private-registries/{secret_name}}).
     *
     * @param org organization name
     * @param secretName secret name
     * @param payload private registry updates
     * @throws NullPointerException if {@code payload} is {@code null}
     * @throws IllegalArgumentException if {@code org} or {@code secretName} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public void updateOrgPrivateRegistry(
            final String org, final String secretName, final GitHubUpdateOrgPrivateRegistryPayload payload)
            throws IOException {
        requireNonNull(payload, PARAM_PAYLOAD);

        caller.send(caller.json(patch(PRIVATE_REGISTRY, org, secretName), payload));
    }

    /**
     * Delete a private registry for an organization ({@code DELETE /orgs/{org}/private-registries/{secret_name}}).
     *
     * @param org organization name
     * @param secretName secret name
     * @throws IllegalArgumentException if {@code org} or {@code secretName} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public void deleteOrgPrivateRegistry(final String org, final String secretName) throws IOException {
        caller.send(delete(PRIVATE_REGISTRY, org, secretName));
    }
}
