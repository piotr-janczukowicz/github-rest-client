package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import static io.github.piotr_janczukowicz.http.HttpMethod.GET;
import static io.github.piotr_janczukowicz.http.HttpMethod.PATCH;
import static io.github.piotr_janczukowicz.http.HttpMethod.POST;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.enterpriseAdminCodeSecurityAndAnalysisApi;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.jsonBytes;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.noContent;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.okJson;
import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.test.http.StubHttpServer;
import io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport;
import io.github.piotr_janczukowicz.testsupport.IntegrationTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

@IntegrationTest
class GitHubEnterpriseAdminCodeSecurityAndAnalysisApiIntegrationTest {

    private static final String ENTERPRISE = "octo-enterprise";
    private static final Class<?> RESOURCES = GitHubEnterpriseAdminCodeSecurityAndAnalysisApiIntegrationTest.class;
    private static final String SECURITY_ANALYSIS_PATH = "/enterprises/octo-enterprise/code_security_and_analysis";
    private static final String ENABLEMENT_PATH = "/enterprises/octo-enterprise/advanced_security/enable_all";
    private static final String SECURITY_ANALYSIS_JSON = "enterprise-security-analysis-settings.json";

    @RegisterExtension
    static final StubHttpServer HTTP = new StubHttpServer();

    @Test
    void getSecurityAnalysisSettingsForEnterprise_shouldReturnSettings_whenResponseIsOk() throws Exception {
        HTTP.stub(GET, SECURITY_ANALYSIS_PATH, okJson(jsonBytes(RESOURCES, SECURITY_ANALYSIS_JSON)));

        final GitHubEnterpriseSecurityAnalysisSettings settings;
        try (final GitHubApiWireMockSupport.EnterpriseAdminCodeSecurityAndAnalysisApiClient api =
                enterpriseAdminCodeSecurityAndAnalysisApi(HTTP)) {
            settings = api.api().getSecurityAnalysisSettingsForEnterprise(ENTERPRISE);
        }

        assertThat(settings).isEqualTo(EnterpriseAdminCatalogFixtures.enterpriseSecurityAnalysisSettings());
        HTTP.verifyRequested(GET, SECURITY_ANALYSIS_PATH);
    }

    @Test
    void patchSecurityAnalysisSettingsForEnterprise_shouldSendPatch_whenResponseIsNoContent() throws Exception {
        HTTP.stub(PATCH, SECURITY_ANALYSIS_PATH, noContent());

        try (final GitHubApiWireMockSupport.EnterpriseAdminCodeSecurityAndAnalysisApiClient api =
                enterpriseAdminCodeSecurityAndAnalysisApi(HTTP)) {
            api.api()
                    .patchSecurityAnalysisSettingsForEnterprise(
                            ENTERPRISE,
                            EnterpriseAdminCatalogFixtures.patchSecurityAnalysisSettingsForEnterprisePayload());
        }

        HTTP.verifyRequested(PATCH, SECURITY_ANALYSIS_PATH);
    }

    @Test
    void postSecurityProductEnablementForEnterprise_shouldSendPost_whenResponseIsNoContent() throws Exception {
        HTTP.stub(POST, ENABLEMENT_PATH, noContent());

        try (final GitHubApiWireMockSupport.EnterpriseAdminCodeSecurityAndAnalysisApiClient api =
                enterpriseAdminCodeSecurityAndAnalysisApi(HTTP)) {
            api.api()
                    .postSecurityProductEnablementForEnterprise(
                            ENTERPRISE,
                            GitHubEnterpriseSecurityProduct.ADVANCED_SECURITY,
                            GitHubEnterpriseSecurityProductEnablement.ENABLE_ALL);
        }

        HTTP.verifyRequested(POST, ENABLEMENT_PATH);
    }
}
