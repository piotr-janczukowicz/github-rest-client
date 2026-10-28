package io.github.piotr_janczukowicz.github.api.copilot;

import static io.github.piotr_janczukowicz.http.HttpHeaderNames.CONTENT_TYPE;
import static io.github.piotr_janczukowicz.http.HttpMethod.GET;
import static io.github.piotr_janczukowicz.http.HttpMethod.PUT;
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
class GitHubCopilotCopilotContentExclusionManagementApiIntegrationTest {

    private static final String CONTENT_EXCLUSION = "/orgs/octo-org/copilot/content_exclusion";

    private static final String ENTERPRISE_CONTENT_EXCLUSION = "/enterprises/octo-enterprise/copilot/content_exclusion";

    @RegisterExtension
    static final StubHttpServer HTTP = new StubHttpServer();

    @Test
    void getForOrganization_shouldReturnDetails_whenResponseIsOk() throws Exception {
        HTTP.stub(
                GET,
                CONTENT_EXCLUSION,
                okJson(jsonBytes(
                        GitHubCopilotCopilotContentExclusionManagementApiIntegrationTest.class,
                        "copilot-organization-content-exclusion-details.json")));

        final GitHubCopilotOrganizationContentExclusionDetails details;
        try (final GitHubApiWireMockSupport.CopilotApiClient copilot = copilotApi(HTTP)) {
            details = copilot.api().copilotContentExclusionManagement().getForOrganization("octo-org");
        }

        assertThat(details).isEqualTo(CopilotCatalogFixtures.copilotOrganizationContentExclusionDetails());
        HTTP.verifyRequested(GET, CONTENT_EXCLUSION);
    }

    @Test
    void updateForOrganization_shouldSendPayload_whenResponseIsOk() throws Exception {
        HTTP.stub(
                PUT,
                CONTENT_EXCLUSION,
                okJson(jsonBytes(
                        GitHubCopilotCopilotContentExclusionManagementApiIntegrationTest.class,
                        "set-copilot-content-exclusion-for-organization-response.json")));
        final GitHubSetCopilotContentExclusionForOrganizationPayload payload =
                CopilotCatalogFixtures.setCopilotContentExclusionForOrganizationPayload();

        final GitHubSetCopilotContentExclusionForOrganizationResponse response;
        try (final GitHubApiWireMockSupport.CopilotApiClient copilot = copilotApi(HTTP)) {
            response = copilot.api().copilotContentExclusionManagement().updateForOrganization("octo-org", payload);
        }

        assertThat(response).isEqualTo(CopilotCatalogFixtures.setCopilotContentExclusionForOrganizationResponse());
        HTTP.verifyRequested(PUT, CONTENT_EXCLUSION);
        HTTP.verifyHeader(PUT, CONTENT_EXCLUSION, CONTENT_TYPE, "application/json");
        HTTP.verifyBody(PUT, CONTENT_EXCLUSION, new String(CODEC.encode(payload), StandardCharsets.UTF_8));
    }

    @Test
    void getForEnterprise_shouldReturnRules_whenResponseIsOk() throws Exception {
        HTTP.stub(
                GET,
                ENTERPRISE_CONTENT_EXCLUSION,
                okJson(jsonBytes(
                        GitHubCopilotCopilotContentExclusionManagementApiIntegrationTest.class,
                        "copilot-enterprise-content-exclusion-details.json")));

        final GitHubCopilotEnterpriseContentExclusionDetails details;
        try (final GitHubApiWireMockSupport.CopilotApiClient copilot = copilotApi(HTTP)) {
            details = copilot.api().copilotContentExclusionManagement().getForEnterprise("octo-enterprise");
        }

        assertThat(details).isEqualTo(CopilotCatalogFixtures.copilotEnterpriseContentExclusionDetails());
        HTTP.verifyRequested(GET, ENTERPRISE_CONTENT_EXCLUSION);
    }

    @Test
    void updateForEnterprise_shouldPutPayload_whenResponseIsOk() throws Exception {
        HTTP.stub(
                PUT,
                ENTERPRISE_CONTENT_EXCLUSION,
                okJson(jsonBytes(
                        GitHubCopilotCopilotContentExclusionManagementApiIntegrationTest.class,
                        "set-copilot-content-exclusion-for-enterprise-response.json")));
        final GitHubSetCopilotContentExclusionForEnterprisePayload payload =
                CopilotCatalogFixtures.setCopilotContentExclusionForEnterprisePayload();

        final GitHubSetCopilotContentExclusionForEnterpriseResponse response;
        try (final GitHubApiWireMockSupport.CopilotApiClient copilot = copilotApi(HTTP)) {
            response =
                    copilot.api().copilotContentExclusionManagement().updateForEnterprise("octo-enterprise", payload);
        }

        assertThat(response).isEqualTo(CopilotCatalogFixtures.setCopilotContentExclusionForEnterpriseResponse());
        HTTP.verifyRequested(PUT, ENTERPRISE_CONTENT_EXCLUSION);
        HTTP.verifyBody(PUT, ENTERPRISE_CONTENT_EXCLUSION, new String(CODEC.encode(payload), StandardCharsets.UTF_8));
    }
}
