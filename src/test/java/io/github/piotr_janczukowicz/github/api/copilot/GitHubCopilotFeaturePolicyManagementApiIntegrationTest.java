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
class GitHubCopilotFeaturePolicyManagementApiIntegrationTest {

    private static final String FEATURE_POLICIES = "/orgs/octo-org/copilot/feature-policies";

    @RegisterExtension
    static final StubHttpServer HTTP = new StubHttpServer();

    @Test
    void getOrganizationFeaturePolicies_shouldReturnPolicies_whenResponseIsOk() throws Exception {
        HTTP.stub(
                GET,
                FEATURE_POLICIES,
                okJson(jsonBytes(
                        GitHubCopilotFeaturePolicyManagementApiIntegrationTest.class,
                        "get-organization-feature-policies-response.json")));

        final GitHubGetOrganizationFeaturePoliciesResponse response;
        try (final GitHubApiWireMockSupport.CopilotApiClient copilot = copilotApi(HTTP)) {
            response = copilot.api().featurePolicyManagement().getOrganizationFeaturePolicies("octo-org");
        }

        assertThat(response).isEqualTo(CopilotCatalogFixtures.getOrganizationFeaturePoliciesResponse());
        HTTP.verifyRequested(GET, FEATURE_POLICIES);
    }

    @Test
    void updateOrganizationFeaturePolicies_shouldPatchPayload_whenResponseIsOk() throws Exception {
        HTTP.stub(
                PATCH,
                FEATURE_POLICIES,
                okJson(jsonBytes(
                        GitHubCopilotFeaturePolicyManagementApiIntegrationTest.class,
                        "update-organization-feature-policies-response.json")));
        final GitHubUpdateOrganizationFeaturePoliciesPayload payload =
                CopilotCatalogFixtures.updateOrganizationFeaturePoliciesPayload();

        final GitHubUpdateOrganizationFeaturePoliciesResponse response;
        try (final GitHubApiWireMockSupport.CopilotApiClient copilot = copilotApi(HTTP)) {
            response = copilot.api().featurePolicyManagement().updateOrganizationFeaturePolicies("octo-org", payload);
        }

        assertThat(response).isEqualTo(CopilotCatalogFixtures.updateOrganizationFeaturePoliciesResponse());
        HTTP.verifyRequested(PATCH, FEATURE_POLICIES);
        HTTP.verifyBody(PATCH, FEATURE_POLICIES, new String(CODEC.encode(payload), StandardCharsets.UTF_8));
    }
}
