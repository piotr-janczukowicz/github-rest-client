package io.github.piotr_janczukowicz.github.api.copilot;

import static io.github.piotr_janczukowicz.http.HttpMethod.DELETE;
import static io.github.piotr_janczukowicz.http.HttpMethod.GET;
import static io.github.piotr_janczukowicz.http.HttpMethod.PUT;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.CODEC;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.copilotApi;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.jsonBytes;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.noContent;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.okJson;
import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.github.pagination.GitHubPage;
import io.github.piotr_janczukowicz.github.pagination.GitHubPageRequest;
import io.github.piotr_janczukowicz.test.http.StubHttpServer;
import io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport;
import io.github.piotr_janczukowicz.testsupport.IntegrationTest;
import java.nio.charset.StandardCharsets;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

@IntegrationTest
class GitHubCopilotCopilotCustomAgentsApiIntegrationTest {

    private static final String CUSTOM_AGENTS = "/enterprises/octo-enterprise/copilot/custom-agents";
    private static final String CUSTOM_AGENTS_SOURCE = "/enterprises/octo-enterprise/copilot/custom-agents/source";

    @RegisterExtension
    static final StubHttpServer HTTP = new StubHttpServer();

    @Test
    void listCustomAgentsForEnterprise_shouldReturnAgents_whenResponseIsOk() throws Exception {
        HTTP.stub(
                GET,
                CUSTOM_AGENTS,
                okJson(jsonBytes(
                        GitHubCopilotCopilotCustomAgentsApiIntegrationTest.class,
                        "get-copilot-custom-agents-for-enterprise-response.json")));

        final GitHubPage<GitHubCopilotCustomAgent> page;
        try (final GitHubApiWireMockSupport.CopilotApiClient copilot = copilotApi(HTTP)) {
            page = copilot.api().copilotCustomAgents().listCustomAgentsForEnterprise("octo-enterprise");
        }

        assertThat(page.items())
                .isEqualTo(CopilotCatalogFixtures.getCopilotCustomAgentsForEnterpriseResponse()
                        .customAgents());
        HTTP.verifyRequested(GET, CUSTOM_AGENTS);
    }

    @Test
    void listCustomAgentsForEnterprise_shouldSendPageRequest_whenRequestGiven() throws Exception {
        HTTP.stub(
                GET,
                CUSTOM_AGENTS,
                okJson(jsonBytes(
                        GitHubCopilotCopilotCustomAgentsApiIntegrationTest.class,
                        "get-copilot-custom-agents-for-enterprise-response.json")));

        try (final GitHubApiWireMockSupport.CopilotApiClient copilot = copilotApi(HTTP)) {
            copilot.api()
                    .copilotCustomAgents()
                    .listCustomAgentsForEnterprise("octo-enterprise", GitHubPageRequest.byPage());
        }

        HTTP.verifyRequested(GET, CUSTOM_AGENTS);
    }

    @Test
    void getCustomAgentsSourceForEnterprise_shouldReturnSource_whenResponseIsOk() throws Exception {
        HTTP.stub(
                GET,
                CUSTOM_AGENTS_SOURCE,
                okJson(jsonBytes(
                        GitHubCopilotCopilotCustomAgentsApiIntegrationTest.class,
                        "get-copilot-custom-agents-source-for-enterprise-response.json")));

        final GitHubGetCopilotCustomAgentsSourceForEnterpriseResponse source;
        try (final GitHubApiWireMockSupport.CopilotApiClient copilot = copilotApi(HTTP)) {
            source = copilot.api().copilotCustomAgents().getCustomAgentsSourceForEnterprise("octo-enterprise");
        }

        assertThat(source).isEqualTo(CopilotCatalogFixtures.getCopilotCustomAgentsSourceForEnterpriseResponse());
        HTTP.verifyRequested(GET, CUSTOM_AGENTS_SOURCE);
    }

    @Test
    void updateCustomAgentsSourceForEnterprise_shouldPutPayload_whenResponseIsOk() throws Exception {
        HTTP.stub(
                PUT,
                CUSTOM_AGENTS_SOURCE,
                okJson(jsonBytes(
                        GitHubCopilotCopilotCustomAgentsApiIntegrationTest.class,
                        "set-copilot-custom-agents-source-for-enterprise-response.json")));
        final GitHubSetCopilotCustomAgentsSourceForEnterprisePayload payload =
                CopilotCatalogFixtures.setCopilotCustomAgentsSourceForEnterprisePayload();

        final GitHubSetCopilotCustomAgentsSourceForEnterpriseResponse response;
        try (final GitHubApiWireMockSupport.CopilotApiClient copilot = copilotApi(HTTP)) {
            response = copilot.api()
                    .copilotCustomAgents()
                    .updateCustomAgentsSourceForEnterprise("octo-enterprise", payload);
        }

        assertThat(response).isEqualTo(CopilotCatalogFixtures.setCopilotCustomAgentsSourceForEnterpriseResponse());
        HTTP.verifyRequested(PUT, CUSTOM_AGENTS_SOURCE);
        HTTP.verifyBody(PUT, CUSTOM_AGENTS_SOURCE, new String(CODEC.encode(payload), StandardCharsets.UTF_8));
    }

    @Test
    void deleteCustomAgentsSourceForEnterprise_shouldSendDelete_whenResponseIsNoContent() throws Exception {
        HTTP.stub(DELETE, CUSTOM_AGENTS_SOURCE, noContent());

        try (final GitHubApiWireMockSupport.CopilotApiClient copilot = copilotApi(HTTP)) {
            copilot.api().copilotCustomAgents().deleteCustomAgentsSourceForEnterprise("octo-enterprise");
        }

        HTTP.verifyRequested(DELETE, CUSTOM_AGENTS_SOURCE);
    }

    @Test
    void listCustomAgentsForEnterprise_shouldReturnEmpty_whenNoSourceConfigured() throws Exception {
        HTTP.stub(GET, CUSTOM_AGENTS, okJson("{\"custom_agents\":null}".getBytes(StandardCharsets.UTF_8)));

        final GitHubPage<GitHubCopilotCustomAgent> page;
        try (final GitHubApiWireMockSupport.CopilotApiClient copilot = copilotApi(HTTP)) {
            page = copilot.api().copilotCustomAgents().listCustomAgentsForEnterprise("octo-enterprise");
        }

        assertThat(page.items()).isEqualTo(List.of());
    }
}
