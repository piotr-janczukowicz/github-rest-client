package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import static io.github.piotr_janczukowicz.test.http.HttpResponses.response;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.assertj.core.api.SoftAssertions.assertSoftly;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import io.github.piotr_janczukowicz.github.client.GitHubManagementCredentials;
import io.github.piotr_janczukowicz.http.HttpHeaderNames;
import io.github.piotr_janczukowicz.http.HttpRequest;
import io.github.piotr_janczukowicz.http.HttpStatus;
import io.github.piotr_janczukowicz.http.transport.HttpTransport;
import io.github.piotr_janczukowicz.test.TestClients;
import io.github.piotr_janczukowicz.test.TestHosts;
import io.github.piotr_janczukowicz.test.http.HttpTransportMocks;
import io.github.piotr_janczukowicz.testsupport.UnitTest;
import io.github.piotr_janczukowicz.util.FileUtils;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

@UnitTest
class GitHubEnterpriseAdminManageGhesApiTest {

    private static final URI GHES_BASE = TestHosts.GHES;
    private static final String MANAGE = "https://ghe.example/manage/v1";
    private static final GitHubManagementCredentials CREDENTIALS = new GitHubManagementCredentials("secret");

    private HttpTransport transport;

    @BeforeEach
    void setUpTransport() {
        transport = HttpTransportMocks.mock();
    }

    @Test
    void constructor_shouldThrow_whenCallerNull() {
        final Throwable thrown = catchThrowable(() -> new GitHubEnterpriseAdminManageGhesApi(null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("caller");
    }

    @Test
    void getVersion_shouldSendBasicAuthOfTheConsole_whenCredentialsGiven() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), "[" + json("manage-version.json") + "]"));

        api().getVersion(CREDENTIALS);
        final HttpRequest request = captureSingleRequest();

        assertThat(request.headers().get(HttpHeaderNames.AUTHORIZATION)).contains("Basic YXBpX2tleTpzZWNyZXQ=");
    }

    @Test
    void getVersion_shouldThrow_whenCredentialsNull() {
        final GitHubEnterpriseAdminManageGhesApi api = api();

        final Throwable thrown = catchThrowable(() -> api.getVersion(null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("credentials");
    }

    @Test
    void getManageSsh_shouldReturnResult_whenResponseIsOk() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), "[" + json("manage-ssh-key.json") + "]"));

        final List<GitHubManageSshKey> result = api().getManageSsh(CREDENTIALS);

        assertThat(result).containsExactly(EnterpriseAdminCatalogFixtures.manageSshKey());
        assertThat(line(captureSingleRequest())).startsWith("GET " + MANAGE + "/access/ssh");
    }

    @Test
    void setManageSsh_shouldReturnResult_whenResponseIsOk() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), "[" + json("manage-set-ssh-result.json") + "]"));

        final List<GitHubManageSetSshResult> result =
                api().setManageSsh(CREDENTIALS, EnterpriseAdminCatalogFixtures.manageSetSshPayload());

        assertThat(result).containsExactly(EnterpriseAdminCatalogFixtures.manageSetSshResult());
        assertThat(line(captureSingleRequest())).startsWith("POST " + MANAGE + "/access/ssh");
    }

    @Test
    void setManageSsh_shouldThrow_whenPayloadNull() {
        final GitHubEnterpriseAdminManageGhesApi api = api();

        final Throwable thrown = catchThrowable(() -> api.setManageSsh(CREDENTIALS, null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("payload");
    }

    @Test
    void deleteManageSsh_shouldReturnResult_whenResponseIsOk() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), "[" + json("manage-delete-ssh-result.json") + "]"));

        final List<GitHubManageDeleteSshResult> result =
                api().deleteManageSsh(CREDENTIALS, EnterpriseAdminCatalogFixtures.manageDeleteSshPayload());

        assertThat(result).containsExactly(EnterpriseAdminCatalogFixtures.manageDeleteSshResult());
        assertThat(line(captureSingleRequest())).startsWith("DELETE " + MANAGE + "/access/ssh");
    }

    @Test
    void deleteManageSsh_shouldThrow_whenPayloadNull() {
        final GitHubEnterpriseAdminManageGhesApi api = api();

        final Throwable thrown = catchThrowable(() -> api.deleteManageSsh(CREDENTIALS, null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("payload");
    }

    @Test
    void getChecksSystemRequirements_shouldReturnResult_whenResponseIsOk() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), json("manage-system-requirements.json")));

        final GitHubManageSystemRequirements result = api().getChecksSystemRequirements(CREDENTIALS);

        assertThat(result).isEqualTo(EnterpriseAdminCatalogFixtures.manageSystemRequirements());
        assertThat(line(captureSingleRequest())).startsWith("GET " + MANAGE + "/checks/system-requirements");
    }

    @Test
    void getClusterStatus_shouldReturnResult_whenResponseIsOk() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("manage-cluster-status.json")));

        final GitHubManageClusterStatus result = api().getClusterStatus(CREDENTIALS);

        assertThat(result).isEqualTo(EnterpriseAdminCatalogFixtures.manageClusterStatus());
        assertThat(line(captureSingleRequest())).startsWith("GET " + MANAGE + "/cluster/status");
    }

    @Test
    void getManageConfigApplyStatus_shouldReturnResult_whenResponseIsOk() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), json("manage-config-apply-status.json")));

        final GitHubManageConfigApplyStatus result = api().getManageConfigApplyStatus(CREDENTIALS);

        assertThat(result).isEqualTo(EnterpriseAdminCatalogFixtures.manageConfigApplyStatus());
        assertThat(line(captureSingleRequest())).startsWith("GET " + MANAGE + "/config/apply");
    }

    @Test
    void getManageConfigApplyStatus_shouldSendFilter_whenFilterGiven() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), json("manage-config-apply-status.json")));

        api().getManageConfigApplyStatus(
                        CREDENTIALS, EnterpriseAdminCatalogFixtures.getManageConfigApplyStatusFilter());
        final String requestLine = line(captureSingleRequest());

        assertThat(requestLine).contains("?");
    }

    @Test
    void getManageConfigApplyStatus_shouldThrow_whenFilterNull() {
        final GitHubEnterpriseAdminManageGhesApi api = api();

        final Throwable thrown = catchThrowable(() -> api.getManageConfigApplyStatus(CREDENTIALS, null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("filter");
    }

    @Test
    void postManageRunConfigApply_shouldReturnResult_whenResponseIsOk() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), json("manage-run-config-apply-result.json")));

        final GitHubManageRunConfigApplyResult result = api().postManageRunConfigApply(CREDENTIALS);

        assertThat(result).isEqualTo(EnterpriseAdminCatalogFixtures.manageRunConfigApplyResult());
        assertThat(line(captureSingleRequest())).startsWith("POST " + MANAGE + "/config/apply");
    }

    @Test
    void postManageRunConfigApply_shouldSendPayload_whenPayloadGiven() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), json("manage-run-config-apply-result.json")));

        api().postManageRunConfigApply(CREDENTIALS, EnterpriseAdminCatalogFixtures.manageRunConfigApplyPayload());
        final HttpRequest request = captureSingleRequest();

        assertThat(request.body().isAbsent()).isFalse();
    }

    @Test
    void postManageRunConfigApply_shouldThrow_whenPayloadNull() {
        final GitHubEnterpriseAdminManageGhesApi api = api();

        final Throwable thrown = catchThrowable(() -> api.postManageRunConfigApply(CREDENTIALS, null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("payload");
    }

    @Test
    void getManageConfigApplyEvents_shouldReturnResult_whenResponseIsOk() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), json("manage-config-apply-events.json")));

        final GitHubManageConfigApplyEvents result = api().getManageConfigApplyEvents(CREDENTIALS);

        assertThat(result).isEqualTo(EnterpriseAdminCatalogFixtures.manageConfigApplyEvents());
        assertThat(line(captureSingleRequest())).startsWith("GET " + MANAGE + "/config/apply/events");
    }

    @Test
    void getManageConfigApplyEvents_shouldSendFilter_whenFilterGiven() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), json("manage-config-apply-events.json")));

        api().getManageConfigApplyEvents(
                        CREDENTIALS, EnterpriseAdminCatalogFixtures.getManageConfigApplyEventsFilter());
        final String requestLine = line(captureSingleRequest());

        assertThat(requestLine).contains("?");
    }

    @Test
    void getManageConfigApplyEvents_shouldThrow_whenFilterNull() {
        final GitHubEnterpriseAdminManageGhesApi api = api();

        final Throwable thrown = catchThrowable(() -> api.getManageConfigApplyEvents(CREDENTIALS, null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("filter");
    }

    @Test
    void licenseInfo_shouldReturnResult_whenResponseIsOk() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("manage-license-info.json")));

        final GitHubManageLicenseInfo result = api().licenseInfo(CREDENTIALS);

        assertThat(result).isEqualTo(EnterpriseAdminCatalogFixtures.manageLicenseInfo());
        assertThat(line(captureSingleRequest())).startsWith("GET " + MANAGE + "/config/license");
    }

    @Test
    void licenseCheck_shouldReturnResult_whenResponseIsOk() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("manage-license-check.json")));

        final GitHubManageLicenseCheck result = api().licenseCheck(CREDENTIALS);

        assertThat(result).isEqualTo(EnterpriseAdminCatalogFixtures.manageLicenseCheck());
        assertThat(line(captureSingleRequest())).startsWith("GET " + MANAGE + "/config/license/check");
    }

    @Test
    void getConfigNodes_shouldReturnResult_whenResponseIsOk() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("manage-config-nodes.json")));

        final GitHubManageConfigNodes result = api().getConfigNodes(CREDENTIALS);

        assertThat(result).isEqualTo(EnterpriseAdminCatalogFixtures.manageConfigNodes());
        assertThat(line(captureSingleRequest())).startsWith("GET " + MANAGE + "/config/nodes");
    }

    @Test
    void getConfigNodes_shouldSendFilter_whenFilterGiven() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("manage-config-nodes.json")));

        api().getConfigNodes(CREDENTIALS, EnterpriseAdminCatalogFixtures.getConfigNodesFilter());
        final String requestLine = line(captureSingleRequest());

        assertThat(requestLine).contains("?");
    }

    @Test
    void getConfigNodes_shouldThrow_whenFilterNull() {
        final GitHubEnterpriseAdminManageGhesApi api = api();

        final Throwable thrown = catchThrowable(() -> api.getConfigNodes(CREDENTIALS, null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("filter");
    }

    @Test
    void getManageSettings_shouldReturnResult_whenResponseIsOk() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("manage-settings.json")));

        final GitHubManageSettings result = api().getManageSettings(CREDENTIALS);

        assertThat(result).isEqualTo(EnterpriseAdminCatalogFixtures.manageSettings());
        assertThat(line(captureSingleRequest())).startsWith("GET " + MANAGE + "/config/settings");
    }

    @Test
    void setManageSettings_shouldPutSettings_whenResponseIsNoContent() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NO_CONTENT.code(), null));

        api().setManageSettings(CREDENTIALS, Map.of("public_pages", true));
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(line(request)).isEqualTo("PUT " + MANAGE + "/config/settings");
            softly.assertThat(new String(request.body().bytes(), StandardCharsets.UTF_8))
                    .contains("public_pages");
        });
    }

    @Test
    void setManageSettings_shouldThrow_whenSettingsNull() {
        final GitHubEnterpriseAdminManageGhesApi api = api();

        final Throwable thrown = catchThrowable(() -> api.setManageSettings(CREDENTIALS, null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("settings");
    }

    @Test
    void setManageSettings_shouldThrowNullPointerException_whenSettingsHoldsNullValue() {
        final GitHubEnterpriseAdminManageGhesApi api = api();
        final Map<String, Object> settings = new HashMap<>();
        settings.put("public_pages", null);

        final Throwable thrown = catchThrowable(() -> api.setManageSettings(CREDENTIALS, settings));

        assertThat(thrown).isInstanceOf(NullPointerException.class);
    }

    @Test
    void getManageMaintenance_shouldReturnResult_whenResponseIsOk() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), "[" + json("manage-maintenance-status.json") + "]"));

        final List<GitHubManageMaintenanceStatus> result = api().getManageMaintenance(CREDENTIALS);

        assertThat(result).containsExactly(EnterpriseAdminCatalogFixtures.manageMaintenanceStatus());
        assertThat(line(captureSingleRequest())).startsWith("GET " + MANAGE + "/maintenance");
    }

    @Test
    void getManageMaintenance_shouldSendFilter_whenFilterGiven() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), "[" + json("manage-maintenance-status.json") + "]"));

        api().getManageMaintenance(CREDENTIALS, EnterpriseAdminCatalogFixtures.getManageMaintenanceFilter());
        final String requestLine = line(captureSingleRequest());

        assertThat(requestLine).contains("?");
    }

    @Test
    void getManageMaintenance_shouldThrow_whenFilterNull() {
        final GitHubEnterpriseAdminManageGhesApi api = api();

        final Throwable thrown = catchThrowable(() -> api.getManageMaintenance(CREDENTIALS, null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("filter");
    }

    @Test
    void setManageMaintenance_shouldReturnResult_whenResponseIsOk() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), "[" + json("manage-set-maintenance-result.json") + "]"));

        final List<GitHubManageSetMaintenanceResult> result =
                api().setManageMaintenance(CREDENTIALS, EnterpriseAdminCatalogFixtures.manageSetMaintenancePayload());

        assertThat(result).containsExactly(EnterpriseAdminCatalogFixtures.manageSetMaintenanceResult());
        assertThat(line(captureSingleRequest())).startsWith("POST " + MANAGE + "/maintenance");
    }

    @Test
    void setManageMaintenance_shouldThrow_whenPayloadNull() {
        final GitHubEnterpriseAdminManageGhesApi api = api();

        final Throwable thrown = catchThrowable(() -> api.setManageMaintenance(CREDENTIALS, null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("payload");
    }

    @Test
    void getReplicationStatus_shouldReturnResult_whenResponseIsOk() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), json("manage-replication-status.json")));

        final GitHubManageReplicationStatus result = api().getReplicationStatus(CREDENTIALS);

        assertThat(result).isEqualTo(EnterpriseAdminCatalogFixtures.manageReplicationStatus());
        assertThat(line(captureSingleRequest())).startsWith("GET " + MANAGE + "/replication/status");
    }

    @Test
    void getReplicationStatus_shouldSendFilter_whenFilterGiven() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), json("manage-replication-status.json")));

        api().getReplicationStatus(CREDENTIALS, EnterpriseAdminCatalogFixtures.getReplicationStatusFilter());
        final String requestLine = line(captureSingleRequest());

        assertThat(requestLine).contains("?");
    }

    @Test
    void getReplicationStatus_shouldThrow_whenFilterNull() {
        final GitHubEnterpriseAdminManageGhesApi api = api();

        final Throwable thrown = catchThrowable(() -> api.getReplicationStatus(CREDENTIALS, null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("filter");
    }

    @Test
    void applyManageUpgrade_shouldReturnResult_whenResponseIsOk() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), "[" + json("manage-apply-upgrade-result.json") + "]"));

        final List<GitHubManageApplyUpgradeResult> result =
                api().applyManageUpgrade(CREDENTIALS, EnterpriseAdminCatalogFixtures.manageApplyUpgradePayload());

        assertThat(result).containsExactly(EnterpriseAdminCatalogFixtures.manageApplyUpgradeResult());
        assertThat(line(captureSingleRequest())).startsWith("POST " + MANAGE + "/upgrade/apply");
    }

    @Test
    void applyManageUpgrade_shouldThrow_whenPayloadNull() {
        final GitHubEnterpriseAdminManageGhesApi api = api();

        final Throwable thrown = catchThrowable(() -> api.applyManageUpgrade(CREDENTIALS, null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("payload");
    }

    @Test
    void getManageUpgradeDownloadStatus_shouldReturnResult_whenResponseIsOk() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), json("manage-upgrade-download-status.json")));

        final GitHubManageUpgradeDownloadStatus result = api().getManageUpgradeDownloadStatus(CREDENTIALS);

        assertThat(result).isEqualTo(EnterpriseAdminCatalogFixtures.manageUpgradeDownloadStatus());
        assertThat(line(captureSingleRequest())).startsWith("GET " + MANAGE + "/upgrade/download/status");
    }

    @Test
    void getManageUpgradeDownloadStatus_shouldSendFilter_whenFilterGiven() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), json("manage-upgrade-download-status.json")));

        api().getManageUpgradeDownloadStatus(
                        CREDENTIALS, EnterpriseAdminCatalogFixtures.getManageUpgradeDownloadStatusFilter());
        final String requestLine = line(captureSingleRequest());

        assertThat(requestLine).contains("?");
    }

    @Test
    void getManageUpgradeDownloadStatus_shouldThrow_whenFilterNull() {
        final GitHubEnterpriseAdminManageGhesApi api = api();

        final Throwable thrown = catchThrowable(() -> api.getManageUpgradeDownloadStatus(CREDENTIALS, null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("filter");
    }

    @Test
    void getManageUpgradeStatus_shouldReturnResult_whenResponseIsOk() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), "[" + json("manage-upgrade-status.json") + "]"));

        final List<GitHubManageUpgradeStatus> result = api().getManageUpgradeStatus(CREDENTIALS);

        assertThat(result).containsExactly(EnterpriseAdminCatalogFixtures.manageUpgradeStatus());
        assertThat(line(captureSingleRequest())).startsWith("GET " + MANAGE + "/upgrade/status");
    }

    @Test
    void getManageUpgradeStatus_shouldSendFilter_whenFilterGiven() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), "[" + json("manage-upgrade-status.json") + "]"));

        api().getManageUpgradeStatus(CREDENTIALS, EnterpriseAdminCatalogFixtures.getManageUpgradeStatusFilter());
        final String requestLine = line(captureSingleRequest());

        assertThat(requestLine).contains("?");
    }

    @Test
    void getManageUpgradeStatus_shouldThrow_whenFilterNull() {
        final GitHubEnterpriseAdminManageGhesApi api = api();

        final Throwable thrown = catchThrowable(() -> api.getManageUpgradeStatus(CREDENTIALS, null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("filter");
    }

    @Test
    void getVersion_shouldReturnResult_whenResponseIsOk() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), "[" + json("manage-version.json") + "]"));

        final List<GitHubManageVersion> result = api().getVersion(CREDENTIALS);

        assertThat(result).containsExactly(EnterpriseAdminCatalogFixtures.manageVersion());
        assertThat(line(captureSingleRequest())).startsWith("GET " + MANAGE + "/version");
    }

    @Test
    void getVersion_shouldSendFilter_whenFilterGiven() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), "[" + json("manage-version.json") + "]"));

        api().getVersion(CREDENTIALS, EnterpriseAdminCatalogFixtures.getVersionFilter());
        final String requestLine = line(captureSingleRequest());

        assertThat(requestLine).contains("?");
    }

    @Test
    void getVersion_shouldThrow_whenFilterNull() {
        final GitHubEnterpriseAdminManageGhesApi api = api();

        final Throwable thrown = catchThrowable(() -> api.getVersion(CREDENTIALS, null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("filter");
    }

    @Test
    void initializeInstanceConfiguration_shouldSendMultipartWithLicenseAndPassword_whenPayloadGiven() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.ACCEPTED.code(), null));

        api().initializeInstanceConfiguration(CREDENTIALS, EnterpriseAdminCatalogFixtures.manageConfigInitPayload());
        final HttpRequest request = captureSingleRequest();
        final String body = new String(request.body().bytes(), StandardCharsets.UTF_8);

        assertSoftly(softly -> {
            softly.assertThat(line(request)).isEqualTo("POST " + MANAGE + "/config/init");
            softly.assertThat(
                            request.headers().get(HttpHeaderNames.CONTENT_TYPE).orElse(""))
                    .startsWith("multipart/form-data; boundary=");
            softly.assertThat(body).contains("name=\"license\"; filename=\"license.ghl\"");
            softly.assertThat(body).contains("name=\"password\"");
        });
    }

    @Test
    void initializeInstanceConfiguration_shouldThrow_whenPayloadNull() {
        final GitHubEnterpriseAdminManageGhesApi api = api();

        final Throwable thrown = catchThrowable(() -> api.initializeInstanceConfiguration(CREDENTIALS, null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("payload");
    }

    @Test
    void licenseUpload_shouldSendMultipartLicense_whenPayloadGiven() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.CREATED.code(), null));

        api().licenseUpload(CREDENTIALS, EnterpriseAdminCatalogFixtures.manageUploadLicensePayload());
        final HttpRequest request = captureSingleRequest();
        final String body = new String(request.body().bytes(), StandardCharsets.UTF_8);

        assertSoftly(softly -> {
            softly.assertThat(line(request)).isEqualTo("PUT " + MANAGE + "/config/license");
            softly.assertThat(body).contains("name=\"license\"; filename=\"license.ghl\"");
        });
    }

    @Test
    void licenseUpload_shouldSendApply_whenFilterGiven() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.CREATED.code(), null));

        api().licenseUpload(
                        CREDENTIALS,
                        EnterpriseAdminCatalogFixtures.manageUploadLicensePayload(),
                        EnterpriseAdminCatalogFixtures.licenseUploadFilter());
        final HttpRequest request = captureSingleRequest();

        assertThat(line(request)).isEqualTo("PUT " + MANAGE + "/config/license?apply=true");
    }

    @Test
    void licenseUpload_shouldThrow_whenPayloadNull() {
        final GitHubEnterpriseAdminManageGhesApi api = api();

        final Throwable thrown = catchThrowable(() -> api.licenseUpload(CREDENTIALS, null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("payload");
    }

    @Test
    void licenseUpload_shouldThrow_whenFilterNull() {
        final GitHubEnterpriseAdminManageGhesApi api = api();
        final GitHubManageUploadLicensePayload payload = EnterpriseAdminCatalogFixtures.manageUploadLicensePayload();

        final Throwable thrown = catchThrowable(() -> api.licenseUpload(CREDENTIALS, payload, null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("filter");
    }

    @Test
    void downloadManageUpgrade_shouldReturnNodes_whenResponseIsOk() throws Exception {
        when(transport.execute(any()))
                .thenReturn(
                        response(HttpStatus.OK.code(), "[" + json("manage-download-upgrade-node-result.json") + "]"));

        final GitHubManageDownloadUpgradeResult result = api().downloadManageUpgrade(CREDENTIALS);

        assertThat(result)
                .isEqualTo(new GitHubManageDownloadUpgradeNodes(
                        List.of(EnterpriseAdminCatalogFixtures.manageDownloadUpgradeNodeResult())));
    }

    @Test
    void downloadManageUpgrade_shouldReturnOrchestration_whenResponseIsAccepted() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.ACCEPTED.code(), json("manage-download-upgrade-orchestration.json")));

        final GitHubManageDownloadUpgradeResult result = api().downloadManageUpgrade(CREDENTIALS);

        assertThat(result).isEqualTo(EnterpriseAdminCatalogFixtures.manageDownloadUpgradeOrchestration());
    }

    @Test
    void downloadManageUpgrade_shouldSendPayload_whenPayloadGiven() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), "[]"));

        api().downloadManageUpgrade(CREDENTIALS, EnterpriseAdminCatalogFixtures.manageDownloadUpgradePayload());
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(line(request)).isEqualTo("POST " + MANAGE + "/upgrade/download");
            softly.assertThat(request.body().isAbsent()).isFalse();
        });
    }

    @Test
    void downloadManageUpgrade_shouldThrow_whenPayloadNull() {
        final GitHubEnterpriseAdminManageGhesApi api = api();

        final Throwable thrown = catchThrowable(() -> api.downloadManageUpgrade(CREDENTIALS, null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("payload");
    }

    private GitHubEnterpriseAdminManageGhesApi api() {
        return new GitHubEnterpriseAdminManageGhesApi(TestClients.caller(GHES_BASE, transport));
    }

    private static String json(final String fileName) {
        return FileUtils.readString(GitHubEnterpriseAdminManageGhesApiTest.class, fileName);
    }

    private HttpRequest captureSingleRequest() throws Exception {
        final ArgumentCaptor<HttpRequest> captor = ArgumentCaptor.forClass(HttpRequest.class);
        verify(transport).execute(captor.capture());
        return captor.getValue();
    }

    private static String line(final HttpRequest request) {
        return request.method() + " " + request.uri();
    }
}
