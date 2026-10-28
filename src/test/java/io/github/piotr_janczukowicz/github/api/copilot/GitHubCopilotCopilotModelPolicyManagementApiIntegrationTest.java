package io.github.piotr_janczukowicz.github.api.copilot;

import static io.github.piotr_janczukowicz.http.HttpMethod.GET;
import static io.github.piotr_janczukowicz.http.HttpMethod.PATCH;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.CODEC;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.copilotApi;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.jsonBytes;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.okJson;
import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.test.http.StubHttpServer;
import io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport;
import io.github.piotr_janczukowicz.testsupport.IntegrationTest;
import java.nio.charset.StandardCharsets;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

@IntegrationTest
class GitHubCopilotCopilotModelPolicyManagementApiIntegrationTest {

    private static final String MODEL_POLICIES = "/orgs/octo-org/copilot/model-policies";

    @RegisterExtension
    static final StubHttpServer HTTP = new StubHttpServer();

    @Test
    void getOrganizationModelPolicies_shouldReturnPolicies_whenResponseIsOk() throws Exception {
        HTTP.stub(
                GET,
                MODEL_POLICIES,
                okJson(jsonBytes(
                        GitHubCopilotCopilotModelPolicyManagementApiIntegrationTest.class,
                        "get-organization-model-policies-response.json")));

        final GitHubGetOrganizationModelPoliciesResponse response;
        try (final GitHubApiWireMockSupport.CopilotApiClient copilot = copilotApi(HTTP)) {
            response = copilot.api().copilotModelPolicyManagement().getOrganizationModelPolicies("octo-org");
        }

        assertThat(response).isEqualTo(CopilotCatalogFixtures.getOrganizationModelPoliciesResponse());
        HTTP.verifyRequested(GET, MODEL_POLICIES);
    }

    @Test
    void updateOrganizationModelPolicies_shouldPatchPayload_whenResponseIsOk() throws Exception {
        HTTP.stub(
                PATCH,
                MODEL_POLICIES,
                okJson(jsonBytes(
                        GitHubCopilotCopilotModelPolicyManagementApiIntegrationTest.class,
                        "update-organization-model-policies-response.json")));
        final GitHubUpdateOrganizationModelPoliciesPayload payload =
                CopilotCatalogFixtures.updateOrganizationModelPoliciesPayload();

        final GitHubUpdateOrganizationModelPoliciesResponse response;
        try (final GitHubApiWireMockSupport.CopilotApiClient copilot = copilotApi(HTTP)) {
            response =
                    copilot.api().copilotModelPolicyManagement().updateOrganizationModelPolicies("octo-org", payload);
        }

        assertThat(response).isEqualTo(CopilotCatalogFixtures.updateOrganizationModelPoliciesResponse());
        HTTP.verifyRequested(PATCH, MODEL_POLICIES);
        HTTP.verifyBody(PATCH, MODEL_POLICIES, new String(CODEC.encode(payload), StandardCharsets.UTF_8));
    }
}
