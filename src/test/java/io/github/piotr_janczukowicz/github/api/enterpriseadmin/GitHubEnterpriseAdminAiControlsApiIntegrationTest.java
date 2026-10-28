package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import static io.github.piotr_janczukowicz.http.HttpMethod.GET;
import static io.github.piotr_janczukowicz.http.HttpMethod.PATCH;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.enterpriseAdminAiControlsApi;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.jsonBytes;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.okJson;
import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.test.http.StubHttpServer;
import io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport;
import io.github.piotr_janczukowicz.testsupport.IntegrationTest;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

@IntegrationTest
class GitHubEnterpriseAdminAiControlsApiIntegrationTest {

    private static final String ENTERPRISE = "octo-enterprise";
    private static final Class<?> RESOURCES = GitHubEnterpriseAdminAiControlsApiIntegrationTest.class;
    private static final String APPS_PATH = "/enterprises/octo-enterprise/copilot/apps/model-policies";
    private static final String MODEL_PATH = "/enterprises/octo-enterprise/copilot/model-policies";
    private static final String TEAM_PATH = "/enterprises/octo-enterprise/teams/1/copilot/model-policies";

    @RegisterExtension
    static final StubHttpServer HTTP = new StubHttpServer();

    @Test
    void getEnterpriseAppsModelPolicies_shouldReturnPolicies_whenResponseIsOk() throws Exception {
        HTTP.stub(GET, APPS_PATH, okJson(jsonBytes(RESOURCES, "get-enterprise-apps-model-policies-response.json")));

        final GitHubGetEnterpriseAppsModelPoliciesResponse policies;
        try (final GitHubApiWireMockSupport.EnterpriseAdminAiControlsApiClient api =
                enterpriseAdminAiControlsApi(HTTP)) {
            policies = api.api().getEnterpriseAppsModelPolicies(ENTERPRISE);
        }

        assertThat(policies).isEqualTo(EnterpriseAdminCatalogFixtures.getEnterpriseAppsModelPoliciesResponse());
        HTTP.verifyRequested(GET, APPS_PATH);
    }

    @Test
    void updateEnterpriseAppsModelPolicies_shouldReturnPolicies_whenResponseIsOk() throws Exception {
        HTTP.stub(
                PATCH, APPS_PATH, okJson(jsonBytes(RESOURCES, "update-enterprise-apps-model-policies-response.json")));

        final GitHubUpdateEnterpriseAppsModelPoliciesResponse policies;
        try (final GitHubApiWireMockSupport.EnterpriseAdminAiControlsApiClient api =
                enterpriseAdminAiControlsApi(HTTP)) {
            policies = api.api()
                    .updateEnterpriseAppsModelPolicies(
                            ENTERPRISE, EnterpriseAdminCatalogFixtures.updateEnterpriseAppsModelPoliciesPayload());
        }

        assertThat(policies).isEqualTo(EnterpriseAdminCatalogFixtures.updateEnterpriseAppsModelPoliciesResponse());
        HTTP.verifyRequested(PATCH, APPS_PATH);
    }

    @Test
    void getEnterpriseModelPolicies_shouldReturnPolicies_whenResponseIsOk() throws Exception {
        HTTP.stub(GET, MODEL_PATH, okJson(jsonBytes(RESOURCES, "get-enterprise-model-policies-response.json")));

        final GitHubGetEnterpriseModelPoliciesResponse policies;
        try (final GitHubApiWireMockSupport.EnterpriseAdminAiControlsApiClient api =
                enterpriseAdminAiControlsApi(HTTP)) {
            policies = api.api().getEnterpriseModelPolicies(ENTERPRISE);
        }

        assertThat(policies).isEqualTo(EnterpriseAdminCatalogFixtures.getEnterpriseModelPoliciesResponse());
        HTTP.verifyRequested(GET, MODEL_PATH);
    }

    @Test
    void updateEnterpriseModelPolicies_shouldReturnPolicies_whenResponseIsOk() throws Exception {
        HTTP.stub(PATCH, MODEL_PATH, okJson(jsonBytes(RESOURCES, "update-enterprise-model-policies-response.json")));

        final GitHubUpdateEnterpriseModelPoliciesResponse policies;
        try (final GitHubApiWireMockSupport.EnterpriseAdminAiControlsApiClient api =
                enterpriseAdminAiControlsApi(HTTP)) {
            policies = api.api()
                    .updateEnterpriseModelPolicies(
                            ENTERPRISE, EnterpriseAdminCatalogFixtures.updateEnterpriseModelPoliciesPayload());
        }

        assertThat(policies).isEqualTo(EnterpriseAdminCatalogFixtures.updateEnterpriseModelPoliciesResponse());
        HTTP.verifyRequested(PATCH, MODEL_PATH);
    }

    @Test
    void getEnterpriseTeamModelPolicies_shouldReturnPolicies_whenResponseIsOk() throws Exception {
        HTTP.stub(GET, TEAM_PATH, okJson(jsonBytes(RESOURCES, "get-enterprise-team-model-policies-response.json")));

        final Optional<GitHubGetEnterpriseTeamModelPoliciesResponse> policies;
        try (final GitHubApiWireMockSupport.EnterpriseAdminAiControlsApiClient api =
                enterpriseAdminAiControlsApi(HTTP)) {
            policies = api.api().getEnterpriseTeamModelPolicies(ENTERPRISE, 1L);
        }

        assertThat(policies).contains(EnterpriseAdminCatalogFixtures.getEnterpriseTeamModelPoliciesResponse());
        HTTP.verifyRequested(GET, TEAM_PATH);
    }

    @Test
    void updateEnterpriseTeamModelPolicies_shouldReturnPolicies_whenResponseIsOk() throws Exception {
        HTTP.stub(
                PATCH, TEAM_PATH, okJson(jsonBytes(RESOURCES, "update-enterprise-team-model-policies-response.json")));

        final GitHubUpdateEnterpriseTeamModelPoliciesResponse policies;
        try (final GitHubApiWireMockSupport.EnterpriseAdminAiControlsApiClient api =
                enterpriseAdminAiControlsApi(HTTP)) {
            policies = api.api()
                    .updateEnterpriseTeamModelPolicies(
                            ENTERPRISE, 1L, EnterpriseAdminCatalogFixtures.updateEnterpriseTeamModelPoliciesPayload());
        }

        assertThat(policies).isEqualTo(EnterpriseAdminCatalogFixtures.updateEnterpriseTeamModelPoliciesResponse());
        HTTP.verifyRequested(PATCH, TEAM_PATH);
    }
}
