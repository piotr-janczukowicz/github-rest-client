package io.github.piotr_janczukowicz.github.api.projects;

import static io.github.piotr_janczukowicz.http.HttpHeaderNames.CONTENT_TYPE;
import static io.github.piotr_janczukowicz.http.HttpMethod.POST;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.CODEC;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.createdJson;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.jsonBytes;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.projectsApi;
import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.test.http.StubHttpServer;
import io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport;
import io.github.piotr_janczukowicz.testsupport.IntegrationTest;
import java.nio.charset.StandardCharsets;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

@IntegrationTest
class GitHubProjectsDraftsApiIntegrationTest {

    private static final String ORG = "octocat";
    private static final String USER_ID = "2";
    private static final long PROJECT_NUMBER = 1L;
    private static final String ORG_PATH = "/orgs/octocat/projectsV2/1/drafts";
    private static final String USER_PATH = "/user/2/projectsV2/1/drafts";

    @RegisterExtension
    static final StubHttpServer HTTP = new StubHttpServer();

    @Test
    void createForOrg_shouldReturnItem_whenResponseIsCreated() throws Exception {
        HTTP.stub(
                POST,
                ORG_PATH,
                createdJson(jsonBytes(GitHubProjectsDraftsApiIntegrationTest.class, "item-simple.json")));
        final GitHubCreateDraftItemPayload payload = ProjectsCatalogFixtures.createDraftItemPayload();

        final GitHubProjectItemSimple item;
        try (final GitHubApiWireMockSupport.ProjectsApiClient api = projectsApi(HTTP)) {
            item = api.api().drafts().createForOrg(ORG, PROJECT_NUMBER, payload);
        }

        assertThat(item).isEqualTo(expectedItem());
        HTTP.verifyRequested(POST, ORG_PATH);
        HTTP.verifyHeader(POST, ORG_PATH, CONTENT_TYPE, "application/json");
        HTTP.verifyBody(POST, ORG_PATH, new String(CODEC.encode(payload), StandardCharsets.UTF_8));
    }

    @Test
    void createForAuthenticatedUser_shouldReturnItem_whenResponseIsCreated() throws Exception {
        HTTP.stub(
                POST,
                USER_PATH,
                createdJson(jsonBytes(GitHubProjectsDraftsApiIntegrationTest.class, "item-simple.json")));
        final GitHubCreateDraftItemPayload payload = ProjectsCatalogFixtures.createDraftItemPayload();

        final GitHubProjectItemSimple item;
        try (final GitHubApiWireMockSupport.ProjectsApiClient api = projectsApi(HTTP)) {
            item = api.api().drafts().createForAuthenticatedUser(USER_ID, PROJECT_NUMBER, payload);
        }

        assertThat(item).isEqualTo(expectedItem());
        HTTP.verifyRequested(POST, USER_PATH);
        HTTP.verifyBody(POST, USER_PATH, new String(CODEC.encode(payload), StandardCharsets.UTF_8));
    }

    private static GitHubProjectItemSimple expectedItem() {
        return CODEC.decode(
                jsonBytes(GitHubProjectsDraftsApiIntegrationTest.class, "item-simple.json"),
                GitHubProjectItemSimple.class);
    }
}
