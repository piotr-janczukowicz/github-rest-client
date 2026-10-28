package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import static io.github.piotr_janczukowicz.http.HttpMethod.GET;
import static io.github.piotr_janczukowicz.http.HttpMethod.POST;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.enterpriseAdminSecurityAdvisoriesApi;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.jsonBytes;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.notFound;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.okJson;
import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.test.http.StubHttpServer;
import io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport;
import io.github.piotr_janczukowicz.testsupport.IntegrationTest;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

@IntegrationTest
class GitHubEnterpriseAdminSecurityAdvisoriesApiIntegrationTest {

    private static final String ENTERPRISE = "octo-enterprise";
    private static final Class<?> RESOURCES = GitHubEnterpriseAdminSecurityAdvisoriesApiIntegrationTest.class;
    private static final String SYNC_PATH = "/enterprises/octo-enterprise/innersource-vulnerabilities/sync";
    private static final String SYNC_STATUS_PATH = SYNC_PATH + "/status/job-1";
    private static final String ACCEPTED_JSON = "external-vulnerability-sync-accepted.json";
    private static final String RESULT_JSON =
            "get-enterprise-innersource-vulnerability-sync-status-response-result.json";

    @RegisterExtension
    static final StubHttpServer HTTP = new StubHttpServer();

    @Test
    void syncEnterpriseInnersourceVulnerabilities_shouldReturnAcceptedJob_whenResponseIsAccepted() throws Exception {
        HTTP.stub(POST, SYNC_PATH, okJson(jsonBytes(RESOURCES, ACCEPTED_JSON)));

        final GitHubExternalVulnerabilitySyncAccepted accepted;
        try (final GitHubApiWireMockSupport.EnterpriseAdminSecurityAdvisoriesApiClient api =
                enterpriseAdminSecurityAdvisoriesApi(HTTP)) {
            accepted = api.api()
                    .syncEnterpriseInnersourceVulnerabilities(
                            ENTERPRISE,
                            EnterpriseAdminCatalogFixtures.syncEnterpriseInnersourceVulnerabilitiesPayload());
        }

        assertThat(accepted).isEqualTo(EnterpriseAdminCatalogFixtures.externalVulnerabilitySyncAccepted());
        HTTP.verifyRequested(POST, SYNC_PATH);
    }

    @Test
    void getEnterpriseInnersourceVulnerabilitySyncStatus_shouldReturnStatus_whenResponseIsOk() throws Exception {
        HTTP.stub(GET, SYNC_STATUS_PATH, okJson(jsonBytes(RESOURCES, RESULT_JSON)));

        final Optional<GitHubGetEnterpriseInnersourceVulnerabilitySyncStatusResponse> status;
        try (final GitHubApiWireMockSupport.EnterpriseAdminSecurityAdvisoriesApiClient api =
                enterpriseAdminSecurityAdvisoriesApi(HTTP)) {
            status = api.api().getEnterpriseInnersourceVulnerabilitySyncStatus(ENTERPRISE, "job-1");
        }

        assertThat(status)
                .contains(
                        EnterpriseAdminCatalogFixtures.getEnterpriseInnersourceVulnerabilitySyncStatusResponseResult());
        HTTP.verifyRequested(GET, SYNC_STATUS_PATH);
    }

    @Test
    void getEnterpriseInnersourceVulnerabilitySyncStatus_shouldReturnEmpty_whenResponseIsNotFound() throws Exception {
        HTTP.stub(GET, SYNC_STATUS_PATH, notFound());

        final Optional<GitHubGetEnterpriseInnersourceVulnerabilitySyncStatusResponse> status;
        try (final GitHubApiWireMockSupport.EnterpriseAdminSecurityAdvisoriesApiClient api =
                enterpriseAdminSecurityAdvisoriesApi(HTTP)) {
            status = api.api().getEnterpriseInnersourceVulnerabilitySyncStatus(ENTERPRISE, "job-1");
        }

        assertThat(status).isEmpty();
    }
}
