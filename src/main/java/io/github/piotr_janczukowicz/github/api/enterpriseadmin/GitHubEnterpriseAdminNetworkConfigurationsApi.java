package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.api.organizations.GitHubNetworkConfiguration;
import io.github.piotr_janczukowicz.github.api.organizations.GitHubNetworkSettings;
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

/** GitHub Enterprise Cloud hosted compute network configurations REST resource for API version {@code 2026-03-10}. */
public final class GitHubEnterpriseAdminNetworkConfigurationsApi extends GitHubResourceApi {

    private static final PathTemplate NETWORK_CONFIGURATIONS =
            PathTemplate.of("/enterprises/{enterprise}/network-configurations");
    private static final PathTemplate NETWORK_CONFIGURATION =
            PathTemplate.of("/enterprises/{enterprise}/network-configurations/{network_configuration_id}");
    private static final PathTemplate NETWORK_SETTINGS =
            PathTemplate.of("/enterprises/{enterprise}/network-settings/{network_settings_id}");

    private static final GitHubListSpec<GitHubNetworkConfiguration> NETWORK_CONFIGURATIONS_SPEC =
            GitHubListSpec.wrapper(
                    GitHubPageRequestKindValidators.PAGE,
                    GitHubEnterpriseNetworkConfigurationsPage.class,
                    GitHubEnterpriseNetworkConfigurationsPage::networkConfigurations,
                    GitHubEnterpriseNetworkConfigurationsPage::totalCount);

    /**
     * Network configurations resource over {@code caller}.
     *
     * @param caller executes and decodes the calls of this resource
     * @throws NullPointerException if {@code caller} is {@code null}
     */
    public GitHubEnterpriseAdminNetworkConfigurationsApi(final GitHubCaller caller) {
        super(caller);
    }

    /**
     * List hosted compute network configurations for an enterprise ({@code GET
     * /enterprises/{enterprise}/network-configurations}).
     *
     * <p>Uses {@link GitHubPageRequest#byPage()}. See {@link #list(String, GitHubPageRequest)}.
     *
     * @param enterprise the slug version of the enterprise name
     * @return first HTTP page of network configurations
     * @throws IllegalArgumentException if {@code enterprise} is empty
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHEC}
     */
    @Flavors(GitHubFlavor.GHEC)
    public GitHubPage<GitHubNetworkConfiguration> list(final String enterprise) throws IOException {
        return list(enterprise, GitHubPageRequest.byPage());
    }

    /**
     * List hosted compute network configurations for an enterprise ({@code GET
     * /enterprises/{enterprise}/network-configurations}).
     *
     * <p>Accepts {@link io.github.piotr_janczukowicz.github.pagination.GitHubPageNumberRequest} and
     * {@link io.github.piotr_janczukowicz.github.pagination.GitHubNoneRequest} only.
     *
     * @param enterprise the slug version of the enterprise name
     * @param request pagination token
     * @return HTTP page of network configurations
     * @throws IllegalArgumentException if {@code enterprise} is empty, or {@code request} is a since or cursor variant
     * @throws NullPointerException if {@code request} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHEC}
     */
    @Flavors(GitHubFlavor.GHEC)
    public GitHubPage<GitHubNetworkConfiguration> list(final String enterprise, final GitHubPageRequest request)
            throws IOException {
        requireFlavor(GitHubFlavor.GHEC);
        requireNonNull(request, PARAM_REQUEST);

        return list(NETWORK_CONFIGURATIONS, Map.of(), request, NETWORK_CONFIGURATIONS_SPEC, enterprise);
    }

    /**
     * Create a hosted compute network configuration for an enterprise ({@code POST
     * /enterprises/{enterprise}/network-configurations}).
     *
     * @param enterprise the slug version of the enterprise name
     * @param payload network configuration to create
     * @return created network configuration
     * @throws NullPointerException if {@code payload} is {@code null}
     * @throws IllegalArgumentException if {@code enterprise} is empty
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHEC}
     */
    @Flavors(GitHubFlavor.GHEC)
    public GitHubNetworkConfiguration create(
            final String enterprise, final GitHubCreateNetworkConfigurationForEnterprisePayload payload)
            throws IOException {
        requireFlavor(GitHubFlavor.GHEC);
        requireNonNull(payload, PARAM_PAYLOAD);

        return caller.fetch(
                caller.json(post(NETWORK_CONFIGURATIONS, enterprise), payload), GitHubNetworkConfiguration.class);
    }

    /**
     * Get a hosted compute network configuration for an enterprise ({@code GET
     * /enterprises/{enterprise}/network-configurations/{network_configuration_id}}).
     *
     * @param enterprise the slug version of the enterprise name
     * @param networkConfigurationId network configuration id
     * @return network configuration, or empty if not found (HTTP 404)
     * @throws IllegalArgumentException if {@code enterprise} or {@code networkConfigurationId} is empty
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHEC}
     */
    @Flavors(GitHubFlavor.GHEC)
    public Optional<GitHubNetworkConfiguration> get(final String enterprise, final String networkConfigurationId)
            throws IOException {
        requireFlavor(GitHubFlavor.GHEC);
        return caller.fetchOrEmpty(
                get(NETWORK_CONFIGURATION, enterprise, networkConfigurationId), GitHubNetworkConfiguration.class);
    }

    /**
     * Update a hosted compute network configuration for an enterprise ({@code PATCH
     * /enterprises/{enterprise}/network-configurations/{network_configuration_id}}).
     *
     * @param enterprise the slug version of the enterprise name
     * @param networkConfigurationId network configuration id
     * @param payload network configuration fields to update
     * @return updated network configuration
     * @throws NullPointerException if {@code payload} is {@code null}
     * @throws IllegalArgumentException if {@code enterprise} or {@code networkConfigurationId} is empty
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHEC}
     */
    @Flavors(GitHubFlavor.GHEC)
    public GitHubNetworkConfiguration update(
            final String enterprise,
            final String networkConfigurationId,
            final GitHubUpdateNetworkConfigurationForEnterprisePayload payload)
            throws IOException {
        requireFlavor(GitHubFlavor.GHEC);
        requireNonNull(payload, PARAM_PAYLOAD);

        return caller.fetch(
                caller.json(patch(NETWORK_CONFIGURATION, enterprise, networkConfigurationId), payload),
                GitHubNetworkConfiguration.class);
    }

    /**
     * Delete a hosted compute network configuration from an enterprise ({@code DELETE
     * /enterprises/{enterprise}/network-configurations/{network_configuration_id}}).
     *
     * @param enterprise the slug version of the enterprise name
     * @param networkConfigurationId network configuration id
     * @throws IllegalArgumentException if {@code enterprise} or {@code networkConfigurationId} is empty
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHEC}
     */
    @Flavors(GitHubFlavor.GHEC)
    public void delete(final String enterprise, final String networkConfigurationId) throws IOException {
        requireFlavor(GitHubFlavor.GHEC);
        caller.send(delete(NETWORK_CONFIGURATION, enterprise, networkConfigurationId));
    }

    /**
     * Get a hosted compute network settings resource for an enterprise ({@code GET
     * /enterprises/{enterprise}/network-settings/{network_settings_id}}).
     *
     * @param enterprise the slug version of the enterprise name
     * @param networkSettingsId network settings id
     * @return network settings, or empty if not found (HTTP 404)
     * @throws IllegalArgumentException if {@code enterprise} or {@code networkSettingsId} is empty
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHEC}
     */
    @Flavors(GitHubFlavor.GHEC)
    public Optional<GitHubNetworkSettings> getNetworkSettings(final String enterprise, final String networkSettingsId)
            throws IOException {
        requireFlavor(GitHubFlavor.GHEC);
        return caller.fetchOrEmpty(get(NETWORK_SETTINGS, enterprise, networkSettingsId), GitHubNetworkSettings.class);
    }
}
