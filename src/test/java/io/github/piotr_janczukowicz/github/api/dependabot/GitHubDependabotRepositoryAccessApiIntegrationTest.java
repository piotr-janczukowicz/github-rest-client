package io.github.piotr_janczukowicz.github.api.dependabot;

import static io.github.piotr_janczukowicz.http.HttpHeaderNames.CONTENT_TYPE;
import static io.github.piotr_janczukowicz.http.HttpMethod.GET;
import static io.github.piotr_janczukowicz.http.HttpMethod.PATCH;
import static io.github.piotr_janczukowicz.http.HttpMethod.PUT;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.CODEC;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.dependabotApi;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.jsonBytes;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.noContent;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.notFound;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.okJson;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.github.client.exception.GitHubNotFoundException;
import io.github.piotr_janczukowicz.test.http.StubHttpServer;
import io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport;
import io.github.piotr_janczukowicz.testsupport.IntegrationTest;
import java.nio.charset.StandardCharsets;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

@IntegrationTest
class GitHubDependabotRepositoryAccessApiIntegrationTest {

    private static final String ENTERPRISE = "octo-enterprise";
    private static final String ORG = "octo-org";

    private static final String ENTERPRISE_ACCESS_PATH = "/enterprises/octo-enterprise/dependabot/repository-access";
    private static final String ENTERPRISE_DEFAULT_LEVEL_PATH = ENTERPRISE_ACCESS_PATH + "/default-level";
    private static final String ORG_ACCESS_PATH = "/orgs/octo-org/dependabot/repository-access";
    private static final String ORG_DEFAULT_LEVEL_PATH = ORG_ACCESS_PATH + "/default-level";

    @RegisterExtension
    static final StubHttpServer HTTP = new StubHttpServer();

    @Test
    void repositoryAccessForEnterprise_shouldReturnDetails_whenResponseIsOk() throws Exception {
        HTTP.stub(
                GET,
                ENTERPRISE_ACCESS_PATH,
                okJson(jsonBytes(
                        GitHubDependabotRepositoryAccessApiIntegrationTest.class,
                        "dependabot-repository-access-details.json")));

        final GitHubDependabotRepositoryAccessDetails details;
        try (final GitHubApiWireMockSupport.DependabotApiClient dependabot = dependabotApi(HTTP)) {
            details = dependabot.api().repositoryAccess().repositoryAccessForEnterprise(ENTERPRISE);
        }

        final GitHubDependabotRepositoryAccessDetails expected = CODEC.decode(
                jsonBytes(
                        GitHubDependabotRepositoryAccessApiIntegrationTest.class,
                        "dependabot-repository-access-details.json"),
                GitHubDependabotRepositoryAccessDetails.class);

        assertThat(details).isEqualTo(expected);
        HTTP.verifyRequested(GET, ENTERPRISE_ACCESS_PATH);
        HTTP.verifyQueryParam(GET, ENTERPRISE_ACCESS_PATH, "page", "1");
        HTTP.verifyQueryParam(GET, ENTERPRISE_ACCESS_PATH, "per_page", "100");
    }

    @Test
    void repositoryAccessForEnterprise_shouldThrowNotFound_whenResponseIsNotFound() throws Exception {
        HTTP.stub(GET, ENTERPRISE_ACCESS_PATH, notFound());

        final Throwable thrown;
        try (final GitHubApiWireMockSupport.DependabotApiClient dependabot = dependabotApi(HTTP)) {
            final GitHubDependabotRepositoryAccessApi repositoryAccess =
                    dependabot.api().repositoryAccess();

            thrown = catchThrowable(() -> repositoryAccess.repositoryAccessForEnterprise(ENTERPRISE));
        }

        assertThat(thrown).isExactlyInstanceOf(GitHubNotFoundException.class);
    }

    @Test
    void updateRepositoryAccessForEnterprise_shouldSendPayload_whenResponseIsNoContent() throws Exception {
        HTTP.stub(PATCH, ENTERPRISE_ACCESS_PATH, noContent());
        final GitHubUpdateRepositoryAccessPayload payload = DependabotCatalogFixtures.updateRepositoryAccessPayload();

        try (final GitHubApiWireMockSupport.DependabotApiClient dependabot = dependabotApi(HTTP)) {
            dependabot.api().repositoryAccess().updateRepositoryAccessForEnterprise(ENTERPRISE, payload);
        }

        HTTP.verifyRequested(PATCH, ENTERPRISE_ACCESS_PATH);
        HTTP.verifyHeader(PATCH, ENTERPRISE_ACCESS_PATH, CONTENT_TYPE, "application/json");
        HTTP.verifyBody(PATCH, ENTERPRISE_ACCESS_PATH, new String(CODEC.encode(payload), StandardCharsets.UTF_8));
    }

    @Test
    void setRepositoryAccessDefaultLevelForEnterprise_shouldSendPayload_whenResponseIsNoContent() throws Exception {
        HTTP.stub(PUT, ENTERPRISE_DEFAULT_LEVEL_PATH, noContent());
        final GitHubSetRepositoryAccessDefaultLevelPayload payload =
                DependabotCatalogFixtures.setRepositoryAccessDefaultLevelPayload();

        try (final GitHubApiWireMockSupport.DependabotApiClient dependabot = dependabotApi(HTTP)) {
            dependabot.api().repositoryAccess().setRepositoryAccessDefaultLevelForEnterprise(ENTERPRISE, payload);
        }

        HTTP.verifyRequested(PUT, ENTERPRISE_DEFAULT_LEVEL_PATH);
        HTTP.verifyBody(PUT, ENTERPRISE_DEFAULT_LEVEL_PATH, new String(CODEC.encode(payload), StandardCharsets.UTF_8));
    }

    @Test
    void repositoryAccessForOrg_shouldReturnDetails_whenResponseIsOk() throws Exception {
        HTTP.stub(
                GET,
                ORG_ACCESS_PATH,
                okJson(jsonBytes(
                        GitHubDependabotRepositoryAccessApiIntegrationTest.class,
                        "dependabot-repository-access-details.json")));

        final GitHubDependabotRepositoryAccessDetails details;
        try (final GitHubApiWireMockSupport.DependabotApiClient dependabot = dependabotApi(HTTP)) {
            details = dependabot.api().repositoryAccess().repositoryAccessForOrg(ORG);
        }

        final GitHubDependabotRepositoryAccessDetails expected = CODEC.decode(
                jsonBytes(
                        GitHubDependabotRepositoryAccessApiIntegrationTest.class,
                        "dependabot-repository-access-details.json"),
                GitHubDependabotRepositoryAccessDetails.class);

        assertThat(details).isEqualTo(expected);
        HTTP.verifyRequested(GET, ORG_ACCESS_PATH);
    }

    @Test
    void repositoryAccessForOrg_shouldThrowNotFound_whenResponseIsNotFound() throws Exception {
        HTTP.stub(GET, ORG_ACCESS_PATH, notFound());

        final Throwable thrown;
        try (final GitHubApiWireMockSupport.DependabotApiClient dependabot = dependabotApi(HTTP)) {
            final GitHubDependabotRepositoryAccessApi repositoryAccess =
                    dependabot.api().repositoryAccess();

            thrown = catchThrowable(() -> repositoryAccess.repositoryAccessForOrg(ORG));
        }

        assertThat(thrown).isExactlyInstanceOf(GitHubNotFoundException.class);
    }

    @Test
    void updateRepositoryAccessForOrg_shouldSendPayload_whenResponseIsNoContent() throws Exception {
        HTTP.stub(PATCH, ORG_ACCESS_PATH, noContent());
        final GitHubUpdateRepositoryAccessPayload payload = DependabotCatalogFixtures.updateRepositoryAccessPayload();

        try (final GitHubApiWireMockSupport.DependabotApiClient dependabot = dependabotApi(HTTP)) {
            dependabot.api().repositoryAccess().updateRepositoryAccessForOrg(ORG, payload);
        }

        HTTP.verifyRequested(PATCH, ORG_ACCESS_PATH);
        HTTP.verifyBody(PATCH, ORG_ACCESS_PATH, new String(CODEC.encode(payload), StandardCharsets.UTF_8));
    }

    @Test
    void setRepositoryAccessDefaultLevel_shouldSendPayload_whenResponseIsNoContent() throws Exception {
        HTTP.stub(PUT, ORG_DEFAULT_LEVEL_PATH, noContent());
        final GitHubSetRepositoryAccessDefaultLevelPayload payload =
                DependabotCatalogFixtures.setRepositoryAccessDefaultLevelPayload();

        try (final GitHubApiWireMockSupport.DependabotApiClient dependabot = dependabotApi(HTTP)) {
            dependabot.api().repositoryAccess().setRepositoryAccessDefaultLevel(ORG, payload);
        }

        HTTP.verifyRequested(PUT, ORG_DEFAULT_LEVEL_PATH);
        HTTP.verifyBody(PUT, ORG_DEFAULT_LEVEL_PATH, new String(CODEC.encode(payload), StandardCharsets.UTF_8));
    }
}
