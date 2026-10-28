package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.api.GhesVersions;
import io.github.piotr_janczukowicz.github.client.GitHubCaller;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.github.client.GitHubManagementCredentials;
import io.github.piotr_janczukowicz.github.client.GitHubResourceApi;
import io.github.piotr_janczukowicz.github.client.exception.GitHubApiException;
import io.github.piotr_janczukowicz.http.HttpMethod;
import io.github.piotr_janczukowicz.http.HttpStatus;
import io.github.piotr_janczukowicz.http.MultipartFormData;
import io.github.piotr_janczukowicz.http.client.HttpCall;
import io.github.piotr_janczukowicz.json.JsonType;
import io.github.piotr_janczukowicz.json.JsonTypes;
import io.github.piotr_janczukowicz.uri.PathTemplate;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

/**
 * GitHub Enterprise Server management console REST resource for API version {@code 2026-03-10}.
 *
 * <p>The console lives at {@code /manage/v1} on the host of the client, outside the API base path, and is authenticated
 * with the management console password: every method takes {@link GitHubManagementCredentials}, used for that one call
 * only.
 */
@SuppressWarnings({"PMD.LinguisticNaming", "PMD.AvoidDuplicateLiterals"})
public final class GitHubEnterpriseAdminManageGhesApi extends GitHubResourceApi {

    private static final PathTemplate SSH = PathTemplate.of("/manage/v1/access/ssh");
    private static final PathTemplate SYSTEM_REQUIREMENTS = PathTemplate.of("/manage/v1/checks/system-requirements");
    private static final PathTemplate CLUSTER_STATUS = PathTemplate.of("/manage/v1/cluster/status");
    private static final PathTemplate CONFIG_APPLY = PathTemplate.of("/manage/v1/config/apply");
    private static final PathTemplate CONFIG_APPLY_EVENTS = PathTemplate.of("/manage/v1/config/apply/events");
    private static final PathTemplate LICENSE = PathTemplate.of("/manage/v1/config/license");
    private static final PathTemplate LICENSE_CHECK = PathTemplate.of("/manage/v1/config/license/check");
    private static final PathTemplate NODES = PathTemplate.of("/manage/v1/config/nodes");
    private static final PathTemplate SETTINGS = PathTemplate.of("/manage/v1/config/settings");
    private static final PathTemplate MAINTENANCE = PathTemplate.of("/manage/v1/maintenance");
    private static final PathTemplate REPLICATION_STATUS = PathTemplate.of("/manage/v1/replication/status");
    private static final PathTemplate UPGRADE_APPLY = PathTemplate.of("/manage/v1/upgrade/apply");
    private static final PathTemplate UPGRADE_DOWNLOAD_STATUS = PathTemplate.of("/manage/v1/upgrade/download/status");
    private static final PathTemplate UPGRADE_STATUS = PathTemplate.of("/manage/v1/upgrade/status");
    private static final PathTemplate CONFIG_INIT = PathTemplate.of("/manage/v1/config/init");
    private static final PathTemplate UPGRADE_DOWNLOAD = PathTemplate.of("/manage/v1/upgrade/download");
    private static final PathTemplate VERSION = PathTemplate.of("/manage/v1/version");

    private static final JsonType<List<GitHubManageDownloadUpgradeNodeResult>> DOWNLOAD_UPGRADE_NODE_RESULT_TYPE =
            JsonTypes.listOf(GitHubManageDownloadUpgradeNodeResult.class);
    private static final String LICENSE_PART = "license";
    private static final String LICENSE_FILE_NAME = "license.ghl";
    private static final String OCTET_STREAM = "application/octet-stream";
    private static final String PASSWORD_PART = "password";
    private static final JsonType<List<GitHubManageSshKey>> SSH_KEY_TYPE = JsonTypes.listOf(GitHubManageSshKey.class);
    private static final JsonType<List<GitHubManageSetSshResult>> SET_SSH_RESULT_TYPE =
            JsonTypes.listOf(GitHubManageSetSshResult.class);
    private static final JsonType<List<GitHubManageDeleteSshResult>> DELETE_SSH_RESULT_TYPE =
            JsonTypes.listOf(GitHubManageDeleteSshResult.class);
    private static final JsonType<List<GitHubManageMaintenanceStatus>> MAINTENANCE_STATUS_TYPE =
            JsonTypes.listOf(GitHubManageMaintenanceStatus.class);
    private static final JsonType<List<GitHubManageSetMaintenanceResult>> SET_MAINTENANCE_RESULT_TYPE =
            JsonTypes.listOf(GitHubManageSetMaintenanceResult.class);
    private static final JsonType<List<GitHubManageApplyUpgradeResult>> APPLY_UPGRADE_RESULT_TYPE =
            JsonTypes.listOf(GitHubManageApplyUpgradeResult.class);
    private static final JsonType<List<GitHubManageUpgradeStatus>> UPGRADE_STATUS_TYPE =
            JsonTypes.listOf(GitHubManageUpgradeStatus.class);
    private static final JsonType<List<GitHubManageVersion>> VERSION_TYPE = JsonTypes.listOf(GitHubManageVersion.class);

    /**
     * Resource over {@code caller}.
     *
     * @param caller executes and decodes the calls of this resource
     * @throws NullPointerException if {@code caller} is {@code null}
     */
    public GitHubEnterpriseAdminManageGhesApi(final GitHubCaller caller) {
        super(caller);
    }

    /**
     * Get the SSH access keys ({@code GET /manage/v1/access/ssh}).
     *
     * @param credentials management console credentials
     * @return the result per node
     * @throws NullPointerException if {@code credentials} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHES}
     */
    @Flavors(GitHubFlavor.GHES)
    public List<GitHubManageSshKey> getManageSsh(final GitHubManagementCredentials credentials) throws IOException {
        requireFlavor(GitHubFlavor.GHES);
        final GitHubCaller console = management();
        return console.fetch(console.managementCall(HttpMethod.GET, SSH.expand(), credentials), SSH_KEY_TYPE);
    }

    /**
     * Authorize an SSH key ({@code POST /manage/v1/access/ssh}).
     *
     * @param credentials management console credentials
     * @param payload request body
     * @return the result per node
     * @throws NullPointerException if {@code credentials} is {@code null}
     * @throws NullPointerException if {@code payload} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHES}
     */
    @Flavors(GitHubFlavor.GHES)
    public List<GitHubManageSetSshResult> setManageSsh(
            final GitHubManagementCredentials credentials, final GitHubManageSetSshPayload payload) throws IOException {
        requireFlavor(GitHubFlavor.GHES);
        requireNonNull(payload, PARAM_PAYLOAD);

        final GitHubCaller console = management();
        return console.fetch(
                console.json(console.managementCall(HttpMethod.POST, SSH.expand(), credentials), payload),
                SET_SSH_RESULT_TYPE);
    }

    /**
     * Delete an authorized SSH key ({@code DELETE /manage/v1/access/ssh}).
     *
     * @param credentials management console credentials
     * @param payload request body
     * @return the result per node
     * @throws NullPointerException if {@code credentials} is {@code null}
     * @throws NullPointerException if {@code payload} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHES}
     */
    @Flavors(GitHubFlavor.GHES)
    public List<GitHubManageDeleteSshResult> deleteManageSsh(
            final GitHubManagementCredentials credentials, final GitHubManageDeleteSshPayload payload)
            throws IOException {
        requireFlavor(GitHubFlavor.GHES);
        requireNonNull(payload, PARAM_PAYLOAD);

        final GitHubCaller console = management();
        return console.fetch(
                console.json(console.managementCall(HttpMethod.DELETE, SSH.expand(), credentials), payload),
                DELETE_SSH_RESULT_TYPE);
    }

    /**
     * Get the system requirements check ({@code GET /manage/v1/checks/system-requirements}).
     *
     * @param credentials management console credentials
     * @return the result
     * @throws NullPointerException if {@code credentials} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHES}
     */
    @Flavors(GitHubFlavor.GHES)
    public GitHubManageSystemRequirements getChecksSystemRequirements(final GitHubManagementCredentials credentials)
            throws IOException {
        requireFlavor(GitHubFlavor.GHES);
        final GitHubCaller console = management();
        return console.fetch(
                console.managementCall(HttpMethod.GET, SYSTEM_REQUIREMENTS.expand(), credentials),
                GitHubManageSystemRequirements.class);
    }

    /**
     * Get the cluster status ({@code GET /manage/v1/cluster/status}).
     *
     * @param credentials management console credentials
     * @return the result
     * @throws NullPointerException if {@code credentials} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHES}
     */
    @Flavors(GitHubFlavor.GHES)
    public GitHubManageClusterStatus getClusterStatus(final GitHubManagementCredentials credentials)
            throws IOException {
        requireFlavor(GitHubFlavor.GHES);
        final GitHubCaller console = management();
        return console.fetch(
                console.managementCall(HttpMethod.GET, CLUSTER_STATUS.expand(), credentials),
                GitHubManageClusterStatus.class);
    }

    /**
     * Get the configuration apply status ({@code GET /manage/v1/config/apply}).
     *
     * @param credentials management console credentials
     * @return the result
     * @throws NullPointerException if {@code credentials} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHES}
     */
    @Flavors(GitHubFlavor.GHES)
    public GitHubManageConfigApplyStatus getManageConfigApplyStatus(final GitHubManagementCredentials credentials)
            throws IOException {
        return getManageConfigApplyStatus(credentials, GitHubGetManageConfigApplyStatusFilter.empty());
    }

    /**
     * Get the configuration apply status ({@code GET /manage/v1/config/apply}).
     *
     * @param credentials management console credentials
     * @param filter query filters
     * @return the result
     * @throws NullPointerException if {@code credentials} is {@code null}
     * @throws NullPointerException if {@code filter} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHES}
     */
    @Flavors(GitHubFlavor.GHES)
    public GitHubManageConfigApplyStatus getManageConfigApplyStatus(
            final GitHubManagementCredentials credentials, final GitHubGetManageConfigApplyStatusFilter filter)
            throws IOException {
        requireFlavor(GitHubFlavor.GHES);
        requireNonNull(filter, PARAM_FILTER);

        final GitHubCaller console = management();
        return console.fetch(
                console.managementCall(HttpMethod.GET, CONFIG_APPLY.expand(), credentials)
                        .withQuery(filter.toQueryParameters()),
                GitHubManageConfigApplyStatus.class);
    }

    /**
     * Trigger a configuration apply run ({@code POST /manage/v1/config/apply}).
     *
     * @param credentials management console credentials
     * @return the result
     * @throws NullPointerException if {@code credentials} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHES}
     */
    @Flavors(GitHubFlavor.GHES)
    public GitHubManageRunConfigApplyResult postManageRunConfigApply(final GitHubManagementCredentials credentials)
            throws IOException {
        requireFlavor(GitHubFlavor.GHES);
        final GitHubCaller console = management();
        return console.fetch(
                console.managementCall(HttpMethod.POST, CONFIG_APPLY.expand(), credentials),
                GitHubManageRunConfigApplyResult.class);
    }

    /**
     * Trigger a configuration apply run ({@code POST /manage/v1/config/apply}).
     *
     * @param credentials management console credentials
     * @param payload request body
     * @return the result
     * @throws NullPointerException if {@code credentials} is {@code null}
     * @throws NullPointerException if {@code payload} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHES}
     */
    @Flavors(GitHubFlavor.GHES)
    public GitHubManageRunConfigApplyResult postManageRunConfigApply(
            final GitHubManagementCredentials credentials, final GitHubManageRunConfigApplyPayload payload)
            throws IOException {
        requireFlavor(GitHubFlavor.GHES);
        requireNonNull(payload, PARAM_PAYLOAD);

        final GitHubCaller console = management();
        return console.fetch(
                console.json(console.managementCall(HttpMethod.POST, CONFIG_APPLY.expand(), credentials), payload),
                GitHubManageRunConfigApplyResult.class);
    }

    /**
     * Get the configuration apply events ({@code GET /manage/v1/config/apply/events}).
     *
     * @param credentials management console credentials
     * @return the result
     * @throws NullPointerException if {@code credentials} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHES}
     */
    @Flavors(GitHubFlavor.GHES)
    public GitHubManageConfigApplyEvents getManageConfigApplyEvents(final GitHubManagementCredentials credentials)
            throws IOException {
        return getManageConfigApplyEvents(credentials, GitHubGetManageConfigApplyEventsFilter.empty());
    }

    /**
     * Get the configuration apply events ({@code GET /manage/v1/config/apply/events}).
     *
     * @param credentials management console credentials
     * @param filter query filters
     * @return the result
     * @throws NullPointerException if {@code credentials} is {@code null}
     * @throws NullPointerException if {@code filter} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHES}
     */
    @Flavors(GitHubFlavor.GHES)
    public GitHubManageConfigApplyEvents getManageConfigApplyEvents(
            final GitHubManagementCredentials credentials, final GitHubGetManageConfigApplyEventsFilter filter)
            throws IOException {
        requireFlavor(GitHubFlavor.GHES);
        requireNonNull(filter, PARAM_FILTER);

        final GitHubCaller console = management();
        return console.fetch(
                console.managementCall(HttpMethod.GET, CONFIG_APPLY_EVENTS.expand(), credentials)
                        .withQuery(filter.toQueryParameters()),
                GitHubManageConfigApplyEvents.class);
    }

    /**
     * Get the license information ({@code GET /manage/v1/config/license}).
     *
     * @param credentials management console credentials
     * @return the result
     * @throws NullPointerException if {@code credentials} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHES}
     */
    @Flavors(GitHubFlavor.GHES)
    public GitHubManageLicenseInfo licenseInfo(final GitHubManagementCredentials credentials) throws IOException {
        requireFlavor(GitHubFlavor.GHES);
        final GitHubCaller console = management();
        return console.fetch(
                console.managementCall(HttpMethod.GET, LICENSE.expand(), credentials), GitHubManageLicenseInfo.class);
    }

    /**
     * Check the license ({@code GET /manage/v1/config/license/check}).
     *
     * @param credentials management console credentials
     * @return the result
     * @throws NullPointerException if {@code credentials} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHES}
     */
    @Flavors(GitHubFlavor.GHES)
    public GitHubManageLicenseCheck licenseCheck(final GitHubManagementCredentials credentials) throws IOException {
        requireFlavor(GitHubFlavor.GHES);
        final GitHubCaller console = management();
        return console.fetch(
                console.managementCall(HttpMethod.GET, LICENSE_CHECK.expand(), credentials),
                GitHubManageLicenseCheck.class);
    }

    /**
     * Get the configuration nodes ({@code GET /manage/v1/config/nodes}).
     *
     * @param credentials management console credentials
     * @return the result
     * @throws NullPointerException if {@code credentials} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHES}
     */
    @Flavors(GitHubFlavor.GHES)
    public GitHubManageConfigNodes getConfigNodes(final GitHubManagementCredentials credentials) throws IOException {
        return getConfigNodes(credentials, GitHubGetConfigNodesFilter.empty());
    }

    /**
     * Get the configuration nodes ({@code GET /manage/v1/config/nodes}).
     *
     * @param credentials management console credentials
     * @param filter query filters
     * @return the result
     * @throws NullPointerException if {@code credentials} is {@code null}
     * @throws NullPointerException if {@code filter} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHES}
     */
    @Flavors(GitHubFlavor.GHES)
    public GitHubManageConfigNodes getConfigNodes(
            final GitHubManagementCredentials credentials, final GitHubGetConfigNodesFilter filter) throws IOException {
        requireFlavor(GitHubFlavor.GHES);
        requireNonNull(filter, PARAM_FILTER);

        final GitHubCaller console = management();
        return console.fetch(
                console.managementCall(HttpMethod.GET, NODES.expand(), credentials)
                        .withQuery(filter.toQueryParameters()),
                GitHubManageConfigNodes.class);
    }

    /**
     * Get the settings ({@code GET /manage/v1/config/settings}).
     *
     * @param credentials management console credentials
     * @return the result
     * @throws NullPointerException if {@code credentials} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHES}
     */
    @Flavors(GitHubFlavor.GHES)
    public GitHubManageSettings getManageSettings(final GitHubManagementCredentials credentials) throws IOException {
        requireFlavor(GitHubFlavor.GHES);
        final GitHubCaller console = management();
        return console.fetch(
                console.managementCall(HttpMethod.GET, SETTINGS.expand(), credentials), GitHubManageSettings.class);
    }

    /**
     * Set the settings ({@code PUT /manage/v1/config/settings}).
     *
     * @param credentials management console credentials
     * @param settings the settings to change; only these are modified. The map is copied shallowly: its values are not
     *     copied
     * @throws NullPointerException if {@code credentials} is {@code null}
     * @throws NullPointerException if {@code settings} is {@code null} or holds a {@code null} key or value
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHES}
     */
    @Flavors(GitHubFlavor.GHES)
    public void setManageSettings(final GitHubManagementCredentials credentials, final Map<String, ?> settings)
            throws IOException {
        requireFlavor(GitHubFlavor.GHES);
        requireNonNull(settings, "settings");

        final GitHubCaller console = management();
        console.send(console.json(
                console.managementCall(HttpMethod.PUT, SETTINGS.expand(), credentials), Map.copyOf(settings)));
    }

    /**
     * Get the maintenance status ({@code GET /manage/v1/maintenance}).
     *
     * @param credentials management console credentials
     * @return the result per node
     * @throws NullPointerException if {@code credentials} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHES}
     */
    @Flavors(GitHubFlavor.GHES)
    public List<GitHubManageMaintenanceStatus> getManageMaintenance(final GitHubManagementCredentials credentials)
            throws IOException {
        return getManageMaintenance(credentials, GitHubGetManageMaintenanceFilter.empty());
    }

    /**
     * Get the maintenance status ({@code GET /manage/v1/maintenance}).
     *
     * @param credentials management console credentials
     * @param filter query filters
     * @return the result per node
     * @throws NullPointerException if {@code credentials} is {@code null}
     * @throws NullPointerException if {@code filter} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHES}
     */
    @Flavors(GitHubFlavor.GHES)
    public List<GitHubManageMaintenanceStatus> getManageMaintenance(
            final GitHubManagementCredentials credentials, final GitHubGetManageMaintenanceFilter filter)
            throws IOException {
        requireFlavor(GitHubFlavor.GHES);
        requireNonNull(filter, PARAM_FILTER);

        final GitHubCaller console = management();
        return console.fetch(
                console.managementCall(HttpMethod.GET, MAINTENANCE.expand(), credentials)
                        .withQuery(filter.toQueryParameters()),
                MAINTENANCE_STATUS_TYPE);
    }

    /**
     * Set the maintenance mode ({@code POST /manage/v1/maintenance}).
     *
     * @param credentials management console credentials
     * @param payload request body
     * @return the result per node
     * @throws NullPointerException if {@code credentials} is {@code null}
     * @throws NullPointerException if {@code payload} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHES}
     */
    @Flavors(GitHubFlavor.GHES)
    public List<GitHubManageSetMaintenanceResult> setManageMaintenance(
            final GitHubManagementCredentials credentials, final GitHubManageSetMaintenancePayload payload)
            throws IOException {
        requireFlavor(GitHubFlavor.GHES);
        requireNonNull(payload, PARAM_PAYLOAD);

        final GitHubCaller console = management();
        return console.fetch(
                console.json(console.managementCall(HttpMethod.POST, MAINTENANCE.expand(), credentials), payload),
                SET_MAINTENANCE_RESULT_TYPE);
    }

    /**
     * Get the replication status ({@code GET /manage/v1/replication/status}).
     *
     * @param credentials management console credentials
     * @return the result
     * @throws NullPointerException if {@code credentials} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHES}
     */
    @Flavors(GitHubFlavor.GHES)
    public GitHubManageReplicationStatus getReplicationStatus(final GitHubManagementCredentials credentials)
            throws IOException {
        return getReplicationStatus(credentials, GitHubGetReplicationStatusFilter.empty());
    }

    /**
     * Get the replication status ({@code GET /manage/v1/replication/status}).
     *
     * @param credentials management console credentials
     * @param filter query filters
     * @return the result
     * @throws NullPointerException if {@code credentials} is {@code null}
     * @throws NullPointerException if {@code filter} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHES}
     */
    @Flavors(GitHubFlavor.GHES)
    public GitHubManageReplicationStatus getReplicationStatus(
            final GitHubManagementCredentials credentials, final GitHubGetReplicationStatusFilter filter)
            throws IOException {
        requireFlavor(GitHubFlavor.GHES);
        requireNonNull(filter, PARAM_FILTER);

        final GitHubCaller console = management();
        return console.fetch(
                console.managementCall(HttpMethod.GET, REPLICATION_STATUS.expand(), credentials)
                        .withQuery(filter.toQueryParameters()),
                GitHubManageReplicationStatus.class);
    }

    /**
     * Apply an upgrade ({@code POST /manage/v1/upgrade/apply}).
     *
     * @param credentials management console credentials
     * @param payload request body
     * @return the result per node
     * @throws NullPointerException if {@code credentials} is {@code null}
     * @throws NullPointerException if {@code payload} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHES}
     */
    @Flavors(GitHubFlavor.GHES)
    @GhesVersions(since = "3.22")
    public List<GitHubManageApplyUpgradeResult> applyManageUpgrade(
            final GitHubManagementCredentials credentials, final GitHubManageApplyUpgradePayload payload)
            throws IOException {
        requireFlavor(GitHubFlavor.GHES);
        requireGhesVersion("3.22", "");
        requireNonNull(payload, PARAM_PAYLOAD);

        final GitHubCaller console = management();
        return console.fetch(
                console.json(console.managementCall(HttpMethod.POST, UPGRADE_APPLY.expand(), credentials), payload),
                APPLY_UPGRADE_RESULT_TYPE);
    }

    /**
     * Get the upgrade download status ({@code GET /manage/v1/upgrade/download/status}).
     *
     * @param credentials management console credentials
     * @return the result
     * @throws NullPointerException if {@code credentials} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHES}
     */
    @Flavors(GitHubFlavor.GHES)
    @GhesVersions(since = "3.22")
    public GitHubManageUpgradeDownloadStatus getManageUpgradeDownloadStatus(
            final GitHubManagementCredentials credentials) throws IOException {
        return getManageUpgradeDownloadStatus(credentials, GitHubGetManageUpgradeDownloadStatusFilter.empty());
    }

    /**
     * Get the upgrade download status ({@code GET /manage/v1/upgrade/download/status}).
     *
     * @param credentials management console credentials
     * @param filter query filters
     * @return the result
     * @throws NullPointerException if {@code credentials} is {@code null}
     * @throws NullPointerException if {@code filter} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHES}
     */
    @Flavors(GitHubFlavor.GHES)
    @GhesVersions(since = "3.22")
    public GitHubManageUpgradeDownloadStatus getManageUpgradeDownloadStatus(
            final GitHubManagementCredentials credentials, final GitHubGetManageUpgradeDownloadStatusFilter filter)
            throws IOException {
        requireFlavor(GitHubFlavor.GHES);
        requireGhesVersion("3.22", "");
        requireNonNull(filter, PARAM_FILTER);

        final GitHubCaller console = management();
        return console.fetch(
                console.managementCall(HttpMethod.GET, UPGRADE_DOWNLOAD_STATUS.expand(), credentials)
                        .withQuery(filter.toQueryParameters()),
                GitHubManageUpgradeDownloadStatus.class);
    }

    /**
     * Get the upgrade status ({@code GET /manage/v1/upgrade/status}).
     *
     * @param credentials management console credentials
     * @return the result per node
     * @throws NullPointerException if {@code credentials} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHES}
     */
    @Flavors(GitHubFlavor.GHES)
    @GhesVersions(since = "3.22")
    public List<GitHubManageUpgradeStatus> getManageUpgradeStatus(final GitHubManagementCredentials credentials)
            throws IOException {
        return getManageUpgradeStatus(credentials, GitHubGetManageUpgradeStatusFilter.empty());
    }

    /**
     * Get the upgrade status ({@code GET /manage/v1/upgrade/status}).
     *
     * @param credentials management console credentials
     * @param filter query filters
     * @return the result per node
     * @throws NullPointerException if {@code credentials} is {@code null}
     * @throws NullPointerException if {@code filter} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHES}
     */
    @Flavors(GitHubFlavor.GHES)
    @GhesVersions(since = "3.22")
    public List<GitHubManageUpgradeStatus> getManageUpgradeStatus(
            final GitHubManagementCredentials credentials, final GitHubGetManageUpgradeStatusFilter filter)
            throws IOException {
        requireFlavor(GitHubFlavor.GHES);
        requireGhesVersion("3.22", "");
        requireNonNull(filter, PARAM_FILTER);

        final GitHubCaller console = management();
        return console.fetch(
                console.managementCall(HttpMethod.GET, UPGRADE_STATUS.expand(), credentials)
                        .withQuery(filter.toQueryParameters()),
                UPGRADE_STATUS_TYPE);
    }

    /**
     * Get the release versions of all nodes ({@code GET /manage/v1/version}).
     *
     * @param credentials management console credentials
     * @return the result per node
     * @throws NullPointerException if {@code credentials} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHES}
     */
    @Flavors(GitHubFlavor.GHES)
    public List<GitHubManageVersion> getVersion(final GitHubManagementCredentials credentials) throws IOException {
        return getVersion(credentials, GitHubGetVersionFilter.empty());
    }

    /**
     * Get the release versions of all nodes ({@code GET /manage/v1/version}).
     *
     * @param credentials management console credentials
     * @param filter query filters
     * @return the result per node
     * @throws NullPointerException if {@code credentials} is {@code null}
     * @throws NullPointerException if {@code filter} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHES}
     */
    @Flavors(GitHubFlavor.GHES)
    public List<GitHubManageVersion> getVersion(
            final GitHubManagementCredentials credentials, final GitHubGetVersionFilter filter) throws IOException {
        requireFlavor(GitHubFlavor.GHES);
        requireNonNull(filter, PARAM_FILTER);

        final GitHubCaller console = management();
        return console.fetch(
                console.managementCall(HttpMethod.GET, VERSION.expand(), credentials)
                        .withQuery(filter.toQueryParameters()),
                VERSION_TYPE);
    }

    /**
     * Initialize the instance with a license and the root site administrator password ({@code POST
     * /manage/v1/config/init}), sent as {@code multipart/form-data}.
     *
     * @param credentials management console credentials
     * @param payload license content and root site administrator password
     * @throws NullPointerException if {@code credentials} or {@code payload} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHES}
     */
    @Flavors(GitHubFlavor.GHES)
    public void initializeInstanceConfiguration(
            final GitHubManagementCredentials credentials, final GitHubManageConfigInitPayload payload)
            throws IOException {
        requireFlavor(GitHubFlavor.GHES);
        requireNonNull(payload, PARAM_PAYLOAD);
        final MultipartFormData form = MultipartFormData.builder()
                .file(
                        LICENSE_PART,
                        LICENSE_FILE_NAME,
                        OCTET_STREAM,
                        payload.license().getBytes(StandardCharsets.UTF_8))
                .text(PASSWORD_PART, payload.password())
                .build();

        final GitHubCaller console = management();
        console.send(
                console.multipart(console.managementCall(HttpMethod.POST, CONFIG_INIT.expand(), credentials), form));
    }

    /**
     * Upload a license ({@code PUT /manage/v1/config/license}), sent as {@code multipart/form-data}.
     *
     * @param credentials management console credentials
     * @param payload license content
     * @throws NullPointerException if {@code credentials} or {@code payload} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHES}
     */
    @Flavors(GitHubFlavor.GHES)
    public void licenseUpload(
            final GitHubManagementCredentials credentials, final GitHubManageUploadLicensePayload payload)
            throws IOException {
        licenseUpload(credentials, payload, GitHubLicenseUploadFilter.empty());
    }

    /**
     * Upload a license ({@code PUT /manage/v1/config/license}), sent as {@code multipart/form-data}.
     *
     * @param credentials management console credentials
     * @param payload license content
     * @param filter whether to apply the license right away
     * @throws NullPointerException if {@code credentials}, {@code payload} or {@code filter} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHES}
     */
    @Flavors(GitHubFlavor.GHES)
    public void licenseUpload(
            final GitHubManagementCredentials credentials,
            final GitHubManageUploadLicensePayload payload,
            final GitHubLicenseUploadFilter filter)
            throws IOException {
        requireFlavor(GitHubFlavor.GHES);
        requireNonNull(payload, PARAM_PAYLOAD);
        requireNonNull(filter, PARAM_FILTER);
        final MultipartFormData form = MultipartFormData.builder()
                .file(
                        LICENSE_PART,
                        LICENSE_FILE_NAME,
                        OCTET_STREAM,
                        payload.license().getBytes(StandardCharsets.UTF_8))
                .build();

        final GitHubCaller console = management();
        console.send(console.multipart(
                console.managementCall(HttpMethod.PUT, LICENSE.expand(), credentials)
                        .withQuery(filter.toQueryParameters()),
                form));
    }

    /**
     * Download the upgrade package to the nodes ({@code POST /manage/v1/upgrade/download}).
     *
     * @param credentials management console credentials
     * @return the per-node results, or the orchestration of a parallel download
     * @throws NullPointerException if {@code credentials} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHES}
     */
    @Flavors(GitHubFlavor.GHES)
    @GhesVersions(since = "3.22")
    public GitHubManageDownloadUpgradeResult downloadManageUpgrade(final GitHubManagementCredentials credentials)
            throws IOException {
        requireFlavor(GitHubFlavor.GHES);
        requireGhesVersion("3.22", "");
        final GitHubCaller console = management();
        return fetchDownload(console, console.managementCall(HttpMethod.POST, UPGRADE_DOWNLOAD.expand(), credentials));
    }

    /**
     * Download the upgrade package to the nodes ({@code POST /manage/v1/upgrade/download}).
     *
     * @param credentials management console credentials
     * @param payload version, nodes and limits of the download
     * @return the per-node results, or the orchestration of a parallel download
     * @throws NullPointerException if {@code credentials} or {@code payload} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHES}
     */
    @Flavors(GitHubFlavor.GHES)
    @GhesVersions(since = "3.22")
    public GitHubManageDownloadUpgradeResult downloadManageUpgrade(
            final GitHubManagementCredentials credentials, final GitHubManageDownloadUpgradePayload payload)
            throws IOException {
        requireFlavor(GitHubFlavor.GHES);
        requireGhesVersion("3.22", "");
        requireNonNull(payload, PARAM_PAYLOAD);

        final GitHubCaller console = management();
        return fetchDownload(
                console,
                console.json(console.managementCall(HttpMethod.POST, UPGRADE_DOWNLOAD.expand(), credentials), payload));
    }

    private GitHubManageDownloadUpgradeResult fetchDownload(final GitHubCaller console, final HttpCall call)
            throws IOException {
        return console.<GitHubManageDownloadUpgradeResult>fetchByStatus(
                call,
                body -> new GitHubManageDownloadUpgradeNodes(body.as(DOWNLOAD_UPGRADE_NODE_RESULT_TYPE)),
                Map.of(HttpStatus.ACCEPTED, body -> body.as(GitHubManageDownloadUpgradeOrchestration.class)));
    }

    // The console view of the client: its guard accepts only the console, and its auth is never asked for a token.
    private GitHubCaller management() {
        return caller.management();
    }
}
