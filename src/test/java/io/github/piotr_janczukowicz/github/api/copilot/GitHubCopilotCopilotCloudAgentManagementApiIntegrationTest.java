package io.github.piotr_janczukowicz.github.api.copilot;

import static io.github.piotr_janczukowicz.http.HttpMethod.GET;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.copilotApi;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.jsonBytes;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.okJson;
import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.test.http.StubHttpServer;
import io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport;
import io.github.piotr_janczukowicz.testsupport.IntegrationTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

@IntegrationTest
class GitHubCopilotCopilotCloudAgentManagementApiIntegrationTest {

    private static final String CONFIGURATION_PATH = "/repos/octo-org/hello-world/copilot/cloud-agent/configuration";

    @RegisterExtension
    static final StubHttpServer HTTP = new StubHttpServer();

    @Test
    void getConfiguration_shouldReturnConfiguration_whenResponseIsOk() throws Exception {
        HTTP.stub(
                GET,
                CONFIGURATION_PATH,
                okJson(jsonBytes(
                        GitHubCopilotCopilotCloudAgentManagementApiIntegrationTest.class,
                        "get-copilot-cloud-agent-configuration-response.json")));

        final GitHubGetCopilotCloudAgentConfigurationResponse configuration;
        try (final GitHubApiWireMockSupport.CopilotApiClient copilot = copilotApi(HTTP)) {
            configuration = copilot.api().copilotCloudAgentManagement().getConfiguration("octo-org", "hello-world");
        }

        assertThat(configuration).isEqualTo(CopilotCatalogFixtures.getCopilotCloudAgentConfigurationResponse());
        HTTP.verifyRequested(GET, CONFIGURATION_PATH);
    }
}
