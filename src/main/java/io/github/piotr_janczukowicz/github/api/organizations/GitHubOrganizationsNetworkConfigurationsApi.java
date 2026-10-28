package io.github.piotr_janczukowicz.github.api.organizations;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

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

/** GitHub organization network configurations REST resource for API version {@code 2026-03-10}. */
public final class GitHubOrganizationsNetworkConfigurationsApi extends GitHubResourceApi {

    private static final PathTemplate NETWORK_CONFIGURATIONS =
            PathTemplate.of("/orgs/{org}/settings/network-configurations");
    private static final PathTemplate NETWORK_CONFIGURATION =
            PathTemplate.of("/orgs/{org}/settings/network-configurations/{network_configuration_id}");
    private static final PathTemplate NETWORK_SETTINGS =
            PathTemplate.of("/orgs/{org}/settings/network-settings/{network_settings_id}");

    /**
     * Network configurations resource over {@code caller}.
     *
     * @param caller executes and decodes the calls of this resource
     * @throws NullPointerException if {@code caller} is {@code null}
     */
    public GitHubOrganizationsNetworkConfigurationsApi(final GitHubCaller caller) {
        super(caller);
    }

    /**
     * List hosted compute network configurations for an organization ({@code GET
     * /orgs/{org}/settings/network-configurations}).
     *
     * <p>Uses {@link GitHubPageRequest#byPage()}. See {@link #list(String, GitHubPageRequest)}.
     *
     * @param org organization name
     * @return first HTTP page of network configurations
     * @throws IllegalArgumentException if {@code org} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public GitHubPage<GitHubNetworkConfiguration> list(final String org) throws IOException {
        return list(org, GitHubPageRequest.byPage());
    }

    /**
     * List hosted compute network configurations for an organization ({@code GET
     * /orgs/{org}/settings/network-configurations}).
     *
     * @param org organization name
     * @param request pagination token
     * @return first HTTP page of network configurations
     * @throws IllegalArgumentException if {@code org} is blank, or {@code request} is a since or cursor variant
     * @throws NullPointerException if {@code request} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public GitHubPage<GitHubNetworkConfiguration> list(final String org, final GitHubPageRequest request)
            throws IOException {
        requireFlavor(GitHubFlavor.CLOUD, GitHubFlavor.GHEC);
        requireNonNull(request, PARAM_REQUEST);

        return super.list(
                NETWORK_CONFIGURATIONS,
                Map.of(),
                request,
                GitHubListSpec.wrapper(
                        GitHubPageRequestKindValidators.PAGE,
                        GitHubNetworkConfigurationsPage.class,
                        GitHubNetworkConfigurationsPage::networkConfigurations,
                        GitHubNetworkConfigurationsPage::totalCount),
                org);
    }

    /**
     * Create a hosted compute network configuration for an organization ({@code POST
     * /orgs/{org}/settings/network-configurations}).
     *
     * @param org organization name
     * @param payload network configuration to create
     * @return created network configuration
     * @throws NullPointerException if {@code payload} is {@code null}
     * @throws IllegalArgumentException if {@code org} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public GitHubNetworkConfiguration create(final String org, final GitHubCreateNetworkConfigurationPayload payload)
            throws IOException {
        requireFlavor(GitHubFlavor.CLOUD, GitHubFlavor.GHEC);
        requireNonNull(payload, PARAM_PAYLOAD);

        return caller.fetch(caller.json(post(NETWORK_CONFIGURATIONS, org), payload), GitHubNetworkConfiguration.class);
    }

    /**
     * Get a hosted compute network configuration for an organization ({@code GET
     * /orgs/{org}/settings/network-configurations/{network_configuration_id}}).
     *
     * @param org organization name
     * @param networkConfigurationId network configuration id
     * @return network configuration, or empty if not found (HTTP 404)
     * @throws IllegalArgumentException if {@code org} or {@code networkConfigurationId} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public Optional<GitHubNetworkConfiguration> get(final String org, final String networkConfigurationId)
            throws IOException {
        requireFlavor(GitHubFlavor.CLOUD, GitHubFlavor.GHEC);

        return caller.fetchOrEmpty(
                get(NETWORK_CONFIGURATION, org, networkConfigurationId), GitHubNetworkConfiguration.class);
    }

    /**
     * Update a hosted compute network configuration for an organization ({@code PATCH
     * /orgs/{org}/settings/network-configurations/{network_configuration_id}}).
     *
     * @param org organization name
     * @param networkConfigurationId network configuration id
     * @param payload network configuration fields to update
     * @return updated network configuration
     * @throws NullPointerException if {@code payload} is {@code null}
     * @throws IllegalArgumentException if {@code org} or {@code networkConfigurationId} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public GitHubNetworkConfiguration update(
            final String org,
            final String networkConfigurationId,
            final GitHubUpdateNetworkConfigurationPayload payload)
            throws IOException {
        requireFlavor(GitHubFlavor.CLOUD, GitHubFlavor.GHEC);
        requireNonNull(payload, PARAM_PAYLOAD);

        return caller.fetch(
                caller.json(patch(NETWORK_CONFIGURATION, org, networkConfigurationId), payload),
                GitHubNetworkConfiguration.class);
    }

    /**
     * Delete a hosted compute network configuration from an organization ({@code DELETE
     * /orgs/{org}/settings/network-configurations/{network_configuration_id}}).
     *
     * @param org organization name
     * @param networkConfigurationId network configuration id
     * @throws IllegalArgumentException if {@code org} or {@code networkConfigurationId} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public void delete(final String org, final String networkConfigurationId) throws IOException {
        requireFlavor(GitHubFlavor.CLOUD, GitHubFlavor.GHEC);
        caller.send(delete(NETWORK_CONFIGURATION, org, networkConfigurationId));
    }

    /**
     * Get a hosted compute network settings resource for an organization ({@code GET
     * /orgs/{org}/settings/network-settings/{network_settings_id}}).
     *
     * @param org organization name
     * @param networkSettingsId network settings id
     * @return network settings, or empty if not found (HTTP 404)
     * @throws IllegalArgumentException if {@code org} or {@code networkSettingsId} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public Optional<GitHubNetworkSettings> getNetworkSettings(final String org, final String networkSettingsId)
            throws IOException {
        requireFlavor(GitHubFlavor.CLOUD, GitHubFlavor.GHEC);

        return caller.fetchOrEmpty(get(NETWORK_SETTINGS, org, networkSettingsId), GitHubNetworkSettings.class);
    }
}
