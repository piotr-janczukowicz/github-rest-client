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

import io.github.piotr_janczukowicz.test.http.StubHttpServer;
import io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport;
import io.github.piotr_janczukowicz.testsupport.IntegrationTest;
import java.nio.charset.StandardCharsets;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

@IntegrationTest
class GitHubCopilotTeamCustomizationsApiIntegrationTest {

    @RegisterExtension
    static final StubHttpServer HTTP = new StubHttpServer();

    @Test
    void getCustomizations_shouldCallEndpoint_whenStubbed() throws Exception {
        HTTP.stub(
                GET,
                "/orgs/octo-org/teams/justice-league/copilot/customizations",
                okJson(jsonBytes(
                        GitHubCopilotTeamCustomizationsApiIntegrationTest.class,
                        "get-copilot-team-customizations-response.json")));

        final java.util.Optional<GitHubGetCopilotTeamCustomizationsResponse> result;
        try (final GitHubApiWireMockSupport.CopilotApiClient copilot = copilotApi(HTTP)) {
            result = copilot.api().teamCustomizations().getCustomizations("octo-org", "justice-league");
        }

        assertThat(result).contains(CopilotCatalogFixtures.getCopilotTeamCustomizationsResponse());
        HTTP.verifyRequested(GET, "/orgs/octo-org/teams/justice-league/copilot/customizations");
    }

    @Test
    void setCustomizations_shouldCallEndpoint_whenStubbed() throws Exception {
        HTTP.stub(
                PUT,
                "/orgs/octo-org/teams/justice-league/copilot/customizations",
                okJson(jsonBytes(
                        GitHubCopilotTeamCustomizationsApiIntegrationTest.class,
                        "set-copilot-team-customizations-response.json")));
        final GitHubSetCopilotTeamCustomizationsPayload payload =
                CopilotCatalogFixtures.setCopilotTeamCustomizationsPayload();

        final GitHubSetCopilotTeamCustomizationsResponse result;
        try (final GitHubApiWireMockSupport.CopilotApiClient copilot = copilotApi(HTTP)) {
            result = copilot.api().teamCustomizations().setCustomizations("octo-org", "justice-league", payload);
        }

        assertThat(result).isEqualTo(CopilotCatalogFixtures.setCopilotTeamCustomizationsResponse());
        HTTP.verifyRequested(PUT, "/orgs/octo-org/teams/justice-league/copilot/customizations");
        HTTP.verifyBody(
                PUT,
                "/orgs/octo-org/teams/justice-league/copilot/customizations",
                new String(CODEC.encode(payload), StandardCharsets.UTF_8));
    }

    @Test
    void deleteCustomizations_shouldCallEndpoint_whenStubbed() throws Exception {
        HTTP.stub(DELETE, "/orgs/octo-org/teams/justice-league/copilot/customizations", noContent());

        try (final GitHubApiWireMockSupport.CopilotApiClient copilot = copilotApi(HTTP)) {
            copilot.api().teamCustomizations().deleteCustomizations("octo-org", "justice-league");
        }

        HTTP.verifyRequested(DELETE, "/orgs/octo-org/teams/justice-league/copilot/customizations");
    }
}
