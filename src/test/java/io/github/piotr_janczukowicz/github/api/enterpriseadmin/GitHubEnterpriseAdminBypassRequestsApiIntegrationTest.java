package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import static io.github.piotr_janczukowicz.http.HttpMethod.GET;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.enterpriseAdminBypassRequestsApi;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.jsonBytes;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.okJson;
import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.github.api.secretscanning.GitHubPushRuleBypassRequest;
import io.github.piotr_janczukowicz.github.api.secretscanning.SecretScanningCatalogFixtures;
import io.github.piotr_janczukowicz.github.pagination.GitHubPage;
import io.github.piotr_janczukowicz.github.pagination.GitHubPageRequest;
import io.github.piotr_janczukowicz.test.http.StubHttpServer;
import io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport;
import io.github.piotr_janczukowicz.testsupport.IntegrationTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

@IntegrationTest
class GitHubEnterpriseAdminBypassRequestsApiIntegrationTest {

    private static final String ENTERPRISE = "octo-enterprise";
    private static final Class<?> RESOURCES = GitHubEnterpriseAdminBypassRequestsApiIntegrationTest.class;
    private static final String PUSH_RULES_PATH = "/enterprises/octo-enterprise/bypass-requests/push-rules";
    private static final String PUSH_RULES_JSON = "push-rule-bypass-request-list.json";

    @RegisterExtension
    static final StubHttpServer HTTP = new StubHttpServer();

    @Test
    void listPushBypassRequests_shouldReturnPage_whenResponseIsOk() throws Exception {
        HTTP.stub(GET, PUSH_RULES_PATH, okJson(jsonBytes(RESOURCES, PUSH_RULES_JSON)));

        final GitHubPage<GitHubPushRuleBypassRequest> page;
        try (final GitHubApiWireMockSupport.EnterpriseAdminBypassRequestsApiClient api =
                enterpriseAdminBypassRequestsApi(HTTP)) {
            page = api.api().listPushBypassRequests(ENTERPRISE);
        }

        assertThat(page.items()).containsExactly(SecretScanningCatalogFixtures.pushRuleBypassRequest());
        HTTP.verifyRequested(GET, PUSH_RULES_PATH);
    }

    @Test
    void listPushBypassRequestsWithFilter_shouldReturnPage_whenResponseIsOk() throws Exception {
        HTTP.stub(GET, PUSH_RULES_PATH, okJson(jsonBytes(RESOURCES, PUSH_RULES_JSON)));

        final GitHubPage<GitHubPushRuleBypassRequest> page;
        try (final GitHubApiWireMockSupport.EnterpriseAdminBypassRequestsApiClient api =
                enterpriseAdminBypassRequestsApi(HTTP)) {
            page = api.api()
                    .listPushBypassRequests(
                            ENTERPRISE,
                            EnterpriseAdminCatalogFixtures.listPushBypassRequestsFilter(),
                            GitHubPageRequest.byPage());
        }

        assertThat(page.items()).containsExactly(SecretScanningCatalogFixtures.pushRuleBypassRequest());
        HTTP.verifyRequested(GET, PUSH_RULES_PATH);
    }
}
