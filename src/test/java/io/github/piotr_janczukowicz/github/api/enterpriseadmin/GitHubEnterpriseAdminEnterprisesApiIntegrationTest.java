package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import static io.github.piotr_janczukowicz.http.HttpMethod.POST;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.enterpriseAdminEnterprisesApi;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.jsonBytes;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.okJson;
import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.test.http.StubHttpServer;
import io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport;
import io.github.piotr_janczukowicz.testsupport.IntegrationTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

@IntegrationTest
class GitHubEnterpriseAdminEnterprisesApiIntegrationTest {

    private static final String ENTERPRISE = "octo-enterprise";
    private static final Class<?> RESOURCES = GitHubEnterpriseAdminEnterprisesApiIntegrationTest.class;
    private static final String DISABLE_PATH = "/enterprises/octo-enterprise/access-restrictions/disable";
    private static final String ENABLE_PATH = "/enterprises/octo-enterprise/access-restrictions/enable";
    private static final String ACCESS_RESTRICTIONS_JSON = "enterprise-access-restrictions.json";

    @RegisterExtension
    static final StubHttpServer HTTP = new StubHttpServer();

    @Test
    void disableAccessRestrictions_shouldReturnHeader_whenResponseIsOk() throws Exception {
        HTTP.stub(POST, DISABLE_PATH, okJson(jsonBytes(RESOURCES, ACCESS_RESTRICTIONS_JSON)));

        final GitHubEnterpriseAccessRestrictions restrictions;
        try (final GitHubApiWireMockSupport.EnterpriseAdminEnterprisesApiClient api =
                enterpriseAdminEnterprisesApi(HTTP)) {
            restrictions = api.api().disableAccessRestrictions(ENTERPRISE);
        }

        assertThat(restrictions).isEqualTo(EnterpriseAdminCatalogFixtures.enterpriseAccessRestrictions());
        HTTP.verifyRequested(POST, DISABLE_PATH);
    }

    @Test
    void enableAccessRestrictions_shouldReturnHeader_whenResponseIsOk() throws Exception {
        HTTP.stub(POST, ENABLE_PATH, okJson(jsonBytes(RESOURCES, ACCESS_RESTRICTIONS_JSON)));

        final GitHubEnterpriseAccessRestrictions restrictions;
        try (final GitHubApiWireMockSupport.EnterpriseAdminEnterprisesApiClient api =
                enterpriseAdminEnterprisesApi(HTTP)) {
            restrictions = api.api().enableAccessRestrictions(ENTERPRISE);
        }

        assertThat(restrictions).isEqualTo(EnterpriseAdminCatalogFixtures.enterpriseAccessRestrictions());
        HTTP.verifyRequested(POST, ENABLE_PATH);
    }
}
