package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import static io.github.piotr_janczukowicz.http.HttpMethod.DELETE;
import static io.github.piotr_janczukowicz.http.HttpMethod.GET;
import static io.github.piotr_janczukowicz.http.HttpMethod.POST;
import static io.github.piotr_janczukowicz.http.HttpMethod.PUT;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.enterpriseAdminAuditLogApi;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.jsonBytes;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.noContent;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.notFound;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.okJson;
import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.github.api.organizations.GitHubAuditLogEvent;
import io.github.piotr_janczukowicz.github.api.organizations.OrganizationsCatalogFixtures;
import io.github.piotr_janczukowicz.github.pagination.GitHubPage;
import io.github.piotr_janczukowicz.github.pagination.GitHubPageRequest;
import io.github.piotr_janczukowicz.test.http.StubHttpServer;
import io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport;
import io.github.piotr_janczukowicz.testsupport.IntegrationTest;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

@IntegrationTest
class GitHubEnterpriseAdminAuditLogApiIntegrationTest {

    private static final String ENTERPRISE = "octo-enterprise";
    private static final Class<?> RESOURCES = GitHubEnterpriseAdminAuditLogApiIntegrationTest.class;
    private static final String AUDIT_LOG_PATH = "/enterprises/octo-enterprise/audit-log";
    private static final String STREAM_KEY_PATH = "/enterprises/octo-enterprise/audit-log/stream-key";
    private static final String STREAMS_PATH = "/enterprises/octo-enterprise/audit-log/streams";
    private static final String STREAM_PATH = STREAMS_PATH + "/1";
    private static final String AUDIT_LOG_JSON = "audit-log-event-list.json";
    private static final String STREAM_KEY_JSON = "audit-log-stream-key.json";
    private static final String STREAMS_JSON = "get-audit-log-stream-configs.json";
    private static final String STREAM_JSON = "get-audit-log-stream-config.json";

    @RegisterExtension
    static final StubHttpServer HTTP = new StubHttpServer();

    @Test
    void getAuditLog_shouldReturnPage_whenResponseIsOk() throws Exception {
        HTTP.stub(GET, AUDIT_LOG_PATH, okJson(jsonBytes(RESOURCES, AUDIT_LOG_JSON)));

        final GitHubPage<GitHubAuditLogEvent> page;
        try (final GitHubApiWireMockSupport.EnterpriseAdminAuditLogApiClient api = enterpriseAdminAuditLogApi(HTTP)) {
            page = api.api().getAuditLog(ENTERPRISE);
        }

        assertThat(page.items()).containsExactly(OrganizationsCatalogFixtures.auditLogEvent());
        HTTP.verifyRequested(GET, AUDIT_LOG_PATH);
    }

    @Test
    void getAuditLogWithFilter_shouldReturnPage_whenResponseIsOk() throws Exception {
        HTTP.stub(GET, AUDIT_LOG_PATH, okJson(jsonBytes(RESOURCES, AUDIT_LOG_JSON)));

        final GitHubPage<GitHubAuditLogEvent> page;
        try (final GitHubApiWireMockSupport.EnterpriseAdminAuditLogApiClient api = enterpriseAdminAuditLogApi(HTTP)) {
            page = api.api()
                    .getAuditLog(
                            ENTERPRISE, EnterpriseAdminCatalogFixtures.getAuditLogFilter(), GitHubPageRequest.byPage());
        }

        assertThat(page.items()).containsExactly(OrganizationsCatalogFixtures.auditLogEvent());
        HTTP.verifyRequested(GET, AUDIT_LOG_PATH);
    }

    @Test
    void getAuditLogStreamKey_shouldReturnKey_whenResponseIsOk() throws Exception {
        HTTP.stub(GET, STREAM_KEY_PATH, okJson(jsonBytes(RESOURCES, STREAM_KEY_JSON)));

        final GitHubAuditLogStreamKey key;
        try (final GitHubApiWireMockSupport.EnterpriseAdminAuditLogApiClient api = enterpriseAdminAuditLogApi(HTTP)) {
            key = api.api().getAuditLogStreamKey(ENTERPRISE);
        }

        assertThat(key).isEqualTo(EnterpriseAdminCatalogFixtures.auditLogStreamKey());
        HTTP.verifyRequested(GET, STREAM_KEY_PATH);
    }

    @Test
    void getAuditLogStreams_shouldReturnStreams_whenResponseIsOk() throws Exception {
        HTTP.stub(GET, STREAMS_PATH, okJson(jsonBytes(RESOURCES, STREAMS_JSON)));

        final List<GitHubGetAuditLogStreamConfigsItem> streams;
        try (final GitHubApiWireMockSupport.EnterpriseAdminAuditLogApiClient api = enterpriseAdminAuditLogApi(HTTP)) {
            streams = api.api().getAuditLogStreams(ENTERPRISE);
        }

        assertThat(streams).containsExactly(EnterpriseAdminCatalogFixtures.getAuditLogStreamConfigsItem());
        HTTP.verifyRequested(GET, STREAMS_PATH);
    }

    @Test
    void createAuditLogStream_shouldReturnStream_whenResponseIsOk() throws Exception {
        HTTP.stub(POST, STREAMS_PATH, okJson(jsonBytes(RESOURCES, STREAM_JSON)));

        final GitHubGetAuditLogStreamConfig stream;
        try (final GitHubApiWireMockSupport.EnterpriseAdminAuditLogApiClient api = enterpriseAdminAuditLogApi(HTTP)) {
            stream = api.api()
                    .createAuditLogStream(ENTERPRISE, EnterpriseAdminCatalogFixtures.createAuditLogStreamPayload());
        }

        assertThat(stream).isEqualTo(EnterpriseAdminCatalogFixtures.getAuditLogStreamConfig());
        HTTP.verifyRequested(POST, STREAMS_PATH);
    }

    @Test
    void getAuditLogStream_shouldReturnStream_whenResponseIsOk() throws Exception {
        HTTP.stub(GET, STREAM_PATH, okJson(jsonBytes(RESOURCES, STREAM_JSON)));

        final Optional<GitHubGetAuditLogStreamConfig> stream;
        try (final GitHubApiWireMockSupport.EnterpriseAdminAuditLogApiClient api = enterpriseAdminAuditLogApi(HTTP)) {
            stream = api.api().getAuditLogStream(ENTERPRISE, 1L);
        }

        assertThat(stream).contains(EnterpriseAdminCatalogFixtures.getAuditLogStreamConfig());
        HTTP.verifyRequested(GET, STREAM_PATH);
    }

    @Test
    void getAuditLogStream_shouldReturnEmpty_whenResponseIsNotFound() throws Exception {
        HTTP.stub(GET, STREAM_PATH, notFound());

        final Optional<GitHubGetAuditLogStreamConfig> stream;
        try (final GitHubApiWireMockSupport.EnterpriseAdminAuditLogApiClient api = enterpriseAdminAuditLogApi(HTTP)) {
            stream = api.api().getAuditLogStream(ENTERPRISE, 1L);
        }

        assertThat(stream).isEmpty();
    }

    @Test
    void updateAuditLogStream_shouldReturnStream_whenResponseIsOk() throws Exception {
        HTTP.stub(PUT, STREAM_PATH, okJson(jsonBytes(RESOURCES, STREAM_JSON)));

        final GitHubGetAuditLogStreamConfig stream;
        try (final GitHubApiWireMockSupport.EnterpriseAdminAuditLogApiClient api = enterpriseAdminAuditLogApi(HTTP)) {
            stream = api.api()
                    .updateAuditLogStream(ENTERPRISE, 1L, EnterpriseAdminCatalogFixtures.updateAuditLogStreamPayload());
        }

        assertThat(stream).isEqualTo(EnterpriseAdminCatalogFixtures.getAuditLogStreamConfig());
        HTTP.verifyRequested(PUT, STREAM_PATH);
    }

    @Test
    void deleteAuditLogStream_shouldSendDelete_whenResponseIsNoContent() throws Exception {
        HTTP.stub(DELETE, STREAM_PATH, noContent());

        try (final GitHubApiWireMockSupport.EnterpriseAdminAuditLogApiClient api = enterpriseAdminAuditLogApi(HTTP)) {
            api.api().deleteAuditLogStream(ENTERPRISE, 1L);
        }

        HTTP.verifyRequested(DELETE, STREAM_PATH);
    }
}
